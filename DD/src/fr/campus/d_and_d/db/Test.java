package fr.campus.d_and_d.db;

import java.sql.*;

public class Test {

	public static void main(String[] args) {

		LinkDB db = new LinkDB();

		try {
			db.connect();

			Connection conn = db.getConnection();

			Statement stmt = conn.createStatement();

			ResultSet rs = stmt.executeQuery(
					"SELECT * FROM characters"
			);

			while (rs.next()) {

				System.out.println(
						rs.getInt("id") + " "
								+ rs.getString("name")
				);
			}

			rs.close();
			stmt.close();
			db.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
