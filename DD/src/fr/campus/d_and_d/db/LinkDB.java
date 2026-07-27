/**
 * Manages database connections for the Dungeons and Dragons game.
 * This class provides methods to connect to and close the MySQL database connection.
 */
package fr.campus.d_and_d.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * The LinkDB class handles database connections for the game.
 * It provides methods to establish and close connections to the MySQL database.
 */
public class LinkDB {

	private static final String URL =
			"jdbc:mysql://localhost:3306/DnD";

	private static final String USER = "root";
	private static final String PASSWORD = "Admin123!!!";

	private Connection connection;

	/**
	 * Establishes a connection to the MySQL database.
	 * 
	 * @throws SQLException If a database access error occurs or the JDBC driver is not found.
	 */
	public void connect() throws SQLException {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection(
					URL,
					USER,
					PASSWORD
			);
			System.out.println("✅ Connexion MySQL réussie !");
		} catch (ClassNotFoundException e) {
			System.err.println("❌ Pilote JDBC introuvable");
			throw new SQLException("Pilote JDBC manquant", e);
		} catch (SQLException e) {
			System.err.println("❌ Erreur de connexion MySQL: " + e.getMessage());
			System.err.println("   URL: " + URL);
			System.err.println("   Utilisateur: " + USER);
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
