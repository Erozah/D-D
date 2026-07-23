package fr.campus.d_and_d.db;

import java.sql.*;

public class Test {

	public static void main(String[] args) {
		System.out.println("=== Test de Connexion à la Base de Données DnD ===\n");

		LinkDB db = new LinkDB();

		try {
			// Étape 1: Connexion
			db.connect();
			Connection conn = db.getConnection();

			// Étape 2: Vérifier les métadonnées
			DatabaseMetaData metaData = conn.getMetaData();
			System.out.println("📊 Informations de connexion:");
			System.out.println("   - URL: " + metaData.getURL());
			System.out.println("   - Utilisateur: " + metaData.getUserName());
			System.out.println("   - Version MySQL: " + metaData.getDatabaseProductVersion());

			// Étape 3: Lister les tables
			System.out.println("\n📋 Tables disponibles:");
			try (Statement stmt = conn.createStatement();
				 ResultSet tables = stmt.executeQuery("SHOW TABLES")) {
				boolean hasTables = false;
				while (tables.next()) {
					System.out.println("   - " + tables.getString(1));
					hasTables = true;
				}
				if (!hasTables) {
					System.out.println("   (Aucune table trouvée)");
				}
			}

			// Étape 4: Afficher les personnages
			System.out.println("\n👥 Personnages enregistrés:");
			try (Statement stmt = conn.createStatement();
				 ResultSet rs = stmt.executeQuery("SELECT * FROM characters")) {
				if (!rs.isBeforeFirst()) {
					System.out.println("   (Aucun personnage trouvé)");
				} else {
					while (rs.next()) {
						System.out.printf("   ID: %d | Nom: %-15s | Type: %-10s | PV: %d | Force: %d\n",
							rs.getInt("id"),
							rs.getString("name"),
							rs.getString("type"),
							rs.getInt("LifePoints"),
							rs.getInt("Strength")
						);
					}
				}
			}

			db.close();

		} catch (SQLException e) {
			System.err.println("\n❌ Erreur SQL: " + e.getMessage());
			System.err.println("\n🔍 Vérifiez les points suivants:");
			System.err.println("   1. Le serveur MySQL est-il démarré ? (sudo systemctl status mysql)");
			System.err.println("   2. Le pilote JDBC est-il dans lib/ ? (ls lib/)");
			System.err.println("   3. Le nom de la base est-il 'DnD' ?");
			System.err.println("   4. L'utilisateur 'root' a-t-il les droits ?");
			System.err.println("\n📝 Commande de compilation:");
			System.err.println("   javac -cp \"lib/*\" -d bin src/fr/campus/d_and_d/db/*.java");
			System.err.println("\n🚀 Commande d'exécution:");
			System.err.println("   java -cp \"bin:lib/*\" fr.campus.d_and_d.db.Test");
		}
	}
}