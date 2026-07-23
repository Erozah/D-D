package fr.campus.d_and_d.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class LinkDB {

	private static final String URL =
			"jdbc:mysql://localhost:3306/DnD";

	private static final String USER = "root";
	private static final String PASSWORD = "Admin123!!!";

	private Connection connection;

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

	public Connection getConnection() {
		return connection;
	}

	public void close() throws SQLException {
		if (connection != null) {
			connection.close();
			System.out.println("🔌 Connexion fermée");
		}
	}
}
