package fr.campus.d_and_d.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class LinkDB {

	private static final String URL =
			"jdbc:mysql://localhost:3306/Characters";

	private static final String USER = "root@localhost";
	private static final String PASSWORD = "";

	private Connection connection;

	public void connect() throws SQLException {

		connection = DriverManager.getConnection(
				URL,
				USER,
				PASSWORD
		);

		System.out.println("Connexion MySQL réussie !");
	}

	public Connection getConnection() {
		return connection;
	}

	public void close() throws SQLException {
		if (connection != null) {
			connection.close();
		}
	}
}
