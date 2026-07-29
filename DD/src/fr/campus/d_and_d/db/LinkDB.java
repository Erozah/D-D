/**
 * Manages database connections for the Dungeons and Dragons game.
 * This class provides methods to connect to and close the MySQL database connection.
 */
package fr.campus.d_and_d.db;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * The LinkDB class handles database connections for the game.
 * It provides methods to establish and close connections to the MySQL database.
 * Connection credentials are loaded from a "db.properties" file rather than
 * being hardcoded, to avoid committing secrets to version control.
 */
public class LinkDB {

	/** Path to the properties file, relative to the working directory where the app is launched. */
	private static final String CONFIG_PATH = "db.properties";

	private final String url;
	private final String user;
	private final String password;

	private Connection connection;

	/**
	 * Constructs a LinkDB and loads connection settings from db.properties.
	 *
	 * @throws SQLException If the properties file is missing or malformed.
	 */
	public LinkDB() throws SQLException {
		Properties props = new Properties();
		try (FileInputStream input = new FileInputStream(CONFIG_PATH)) {
			props.load(input);
		} catch (IOException e) {
			throw new SQLException(
					"Impossible de lire le fichier de configuration '" + CONFIG_PATH +
					"'. Assurez-vous qu'il existe à la racine du projet et qu'il n'est pas dans .gitignore " +
					"seulement pour Git (le fichier doit rester présent localement).", e);
		}

		this.url = props.getProperty("db.url");
		this.user = props.getProperty("db.user");
		this.password = props.getProperty("db.password");

		if (url == null || user == null || password == null) {
			throw new SQLException(
					"Le fichier '" + CONFIG_PATH + "' doit contenir les clés db.url, db.user et db.password.");
		}
	}

	/**
	 * Establishes a connection to the MySQL database.
	 * 
	 * @throws SQLException If a database access error occurs or the JDBC driver is not found.
	 */
	public void connect() throws SQLException {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection(
					url,
					user,
					password
			);
			System.out.println("✅ Connexion MySQL réussie !");
		} catch (ClassNotFoundException e) {
			System.err.println("❌ Pilote JDBC introuvable");
			throw new SQLException("Pilote JDBC manquant", e);
		} catch (SQLException e) {
			System.err.println("❌ Erreur de connexion MySQL: " + e.getMessage());
			System.err.println("   URL: " + url);
			System.err.println("   Utilisateur: " + user);
			throw e;
		}
	}

	/**
	 * Gets the current database connection.
	 * 
	 * @return The active database connection, or null if not connected.
	 */
	public Connection getConnection() {
		return connection;
	}

	/**
	 * Closes the current database connection.
	 * 
	 * @throws SQLException If an error occurs while closing the connection.
	 */
	public void close() throws SQLException {
		if (connection != null) {
			connection.close();
			System.out.println("🔌 Connexion fermée");
		}
	}
}
