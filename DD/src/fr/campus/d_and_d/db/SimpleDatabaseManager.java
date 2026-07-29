package fr.campus.d_and_d.db;

import fr.campus.d_and_d.characters.Character;
import fr.campus.d_and_d.characters.ally.Warrior;
import fr.campus.d_and_d.characters.ally.Wizard;
import fr.campus.d_and_d.items.defensif.*;
import fr.campus.d_and_d.items.offensif.OffensiveEquipment;
import fr.campus.d_and_d.items.offensif.Spell;
import fr.campus.d_and_d.items.offensif.Weapon;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * A simplified database manager that only handles character and equipment saving/loading.
 * This version focuses only on what's needed: saving and loading player characters with their equipment.
 */
public class SimpleDatabaseManager {

    /**
     * Saves a character and their equipment to the database.
     *
     * @param character The character to save
     * @throws SQLException If a database error occurs
     */
    public void saveCharacter(Character character) throws SQLException {
        LinkDB linkDB = new LinkDB();
        linkDB.connect();

        try (Connection conn = linkDB.getConnection()) {
            conn.setAutoCommit(false);

            // Save character
            int characterId = saveCharacterInfo(conn, character);

            // Save offensive equipment
            saveOffensiveEquipment(conn, character.getOffensiveEquipment(), characterId);

            // Save defensive equipment
            if (character.getDefensiveEquipment() instanceof DefensiveEquipment) {
                saveDefensiveEquipment(conn, (DefensiveEquipment) character.getDefensiveEquipment(), characterId);
            }

            conn.commit();
            System.out.println("✅ Personnage sauvegardé dans la base de données.");
        } finally {
            linkDB.close();
        }
    }

    /**
     * Saves character information.
     *
     * @param conn The database connection
     * @param character The character to save
     * @return The ID of the saved character
     * @throws SQLException If a database error occurs
     */
    private int saveCharacterInfo(Connection conn, Character character) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(
                "INSERT INTO characters (character_type, name, health_points, attack_power) " +
                        "VALUES (?, ?, ?, ?)",
                Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, character.getType());
            stmt.setString(2, character.getName());
            stmt.setInt(3, character.getHealthPoints());
            stmt.setInt(4, character.getBaseAttackPower()); // Save base attack power, not total
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    int generatedId = rs.getInt(1);
                    character.setDatabaseId(generatedId); // Set the ID on the character
                    return generatedId;
                }
            }
        }
        throw new SQLException("Failed to save character info");
    }

    /**
     * Saves offensive equipment.
     *
     * @param conn The database connection
     * @param equipment The equipment to save
     * @param ownerId The ID of the character who owns this equipment
     * @throws SQLException If a database error occurs
     */
    private void saveOffensiveEquipment(Connection conn, OffensiveEquipment equipment, int ownerId) throws SQLException {
        String equipType = equipment instanceof Weapon ? "WEAPON" : "SPELL";

        try (PreparedStatement stmt = conn.prepareStatement(
                "INSERT INTO equipment (equipment_type, name, power, owner_id) VALUES (?, ?, ?, ?)")) {
            stmt.setString(1, equipType);
            stmt.setString(2, equipment.getName());
            stmt.setInt(3, equipment.getAttackPower());
            stmt.setInt(4, ownerId);
            stmt.executeUpdate();
        }
    }

    /**
     * Saves defensive equipment.
     *
     * @param conn The database connection
     * @param equipment The equipment to save
     * @param ownerId The ID of the character who owns this equipment
     * @throws SQLException If a database error occurs
     */
    private void saveDefensiveEquipment(Connection conn, DefensiveEquipment equipment, int ownerId) throws SQLException {
        String equipType = equipment instanceof Shield ? "SHIELD" : "ROBE";

        try (PreparedStatement stmt = conn.prepareStatement(
                "INSERT INTO equipment (equipment_type, name, power, owner_id) VALUES (?, ?, ?, ?)")) {
            stmt.setString(1, equipType);
            stmt.setString(2, equipment.getName());
            stmt.setInt(3, equipment.getDefensePoints());
            stmt.setInt(4, ownerId);
            stmt.executeUpdate();
        }
    }

    /**
     * Gets a list of all characters in the database.
     *
     * @return List of character information (id, name, type, health, weapon, shield)
     * @throws SQLException If a database error occurs
     */
    public List<String> listCharacters() throws SQLException {
        List<String> characters = new ArrayList<>();
        LinkDB linkDB = new LinkDB();
        linkDB.connect();

        try (Connection conn = linkDB.getConnection()) {
            // Get all player characters (not enemies)
            try (PreparedStatement stmt = conn.prepareStatement(
                "SELECT id, character_type, name, health_points, attack_power FROM characters ORDER BY id")) {

                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        int id = rs.getInt("id");
                        String type = rs.getString("character_type");
                        String name = rs.getString("name");
                        int health = rs.getInt("health_points");
                        int attack = rs.getInt("attack_power");

                        // Get equipment for this character
                        String weaponName = "Aucune";
                        String shieldName = "Aucun";

                        try (PreparedStatement equipStmt = conn.prepareStatement(
                            "SELECT name, equipment_type FROM equipment WHERE owner_id = ?")) {
                            equipStmt.setInt(1, id);
                            try (ResultSet equipRs = equipStmt.executeQuery()) {
                                while (equipRs.next()) {
                                    String equipType = equipRs.getString("equipment_type");
                                    String equipName = equipRs.getString("name");
                                    if (equipType.equals("WEAPON") || equipType.equals("SPELL")) {
                                        weaponName = equipName;
                                    } else if (equipType.equals("SHIELD") || equipType.equals("POTION")) {
                                        shieldName = equipName;
                                    }
                                }
                            }
                        }

                        String line1 = String.format("ID %d - %s (%s),  %d PV, Attaque: %d ", id, name, type, health, attack);
                        String line2 = String.format("Arme: %s, Défense: %s",
                            weaponName, shieldName);
                        characters.add(line1);
                        characters.add(line2);
                    }
                }
            }
        } finally {
            linkDB.close();
        }
        return characters;
    }

    /**
     * Loads a character from the database.
     *
     * @param characterId The ID of the character to load
     * @return The loaded character, or null if not found
     * @throws SQLException If a database error occurs
     */
    public Character loadCharacter(int characterId) throws SQLException {
        LinkDB linkDB = new LinkDB();
        linkDB.connect();

        try (Connection conn = linkDB.getConnection()) {
            // Load character info
            Character character = loadCharacterInfo(conn, characterId);
            if (character == null) {
                return null;
            }

            // Load offensive equipment
            OffensiveEquipment weapon = loadOffensiveEquipment(conn, characterId);
            character.setOffensiveEquipment(weapon);

            // Load defensive equipment
            DefensiveEquipment shield = loadDefensiveEquipment(conn, characterId);
            character.setDefensiveEquipment(shield);

            return character;
        } finally {
            linkDB.close();
        }
    }

    /**
     * Loads character information.
     *
     * @param conn The database connection
     * @param characterId The ID of the character
     * @return The loaded character, or null if not found
     * @throws SQLException If a database error occurs
     */
    private Character loadCharacterInfo(Connection conn, int characterId) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(
                "SELECT character_type, name, health_points, attack_power FROM characters WHERE id = ?")) {
            stmt.setInt(1, characterId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String type = rs.getString("character_type");
                    String name = rs.getString("name");
                    int health = rs.getInt("health_points");
                    int attack = rs.getInt("attack_power");

                    // Create appropriate character type - handle both French and English types
                    String normalizedType = type.toUpperCase();
                    Character character = switch (normalizedType) {
                        case "WARRIOR", "GUERRIER" -> {
                            Character warrior = new Warrior("Warrior", name);
                            yield warrior;
                        }
                        case "WIZARD", "MAGICIEN" -> {
                            Character wizard = new Wizard("Wizard", name);
                            yield wizard;
                        }
                        default -> null;
                    };
                    if (character != null) {
                        character.setDatabaseId(characterId); // Set the database ID
                        // Set base attack power (will be used when equipment is loaded)
                        character.setBaseAttackPower(attack);
                    }
                    return character;
                }
            }
        }
        return null;
    }

    /**
     * Loads offensive equipment from the database.
     *
     * @param conn The database connection
     * @param ownerId The ID of the owner
     * @return The loaded equipment, or null if not found
     * @throws SQLException If a database error occurs
     */
    private OffensiveEquipment loadOffensiveEquipment(Connection conn, int ownerId) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(
                "SELECT equipment_type, name, power FROM equipment WHERE owner_id = ? AND equipment_type IN ('WEAPON', 'SPELL') LIMIT 1")) {
            stmt.setInt(1, ownerId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String type = rs.getString("equipment_type");
                    String name = rs.getString("name");
                    int power = rs.getInt("power");

                    return type.equals("WEAPON") ?
                            new Weapon(type, name, power) :
                            new Spell(type, name, power);
                }
            }
        }
        // Default equipment if none found
        return new Weapon("Hand", "Fist", 1);
    }

    /**
     * Loads defensive equipment from the database.
     *
     * @param conn The database connection
     * @param ownerId The ID of the owner
     * @return The loaded equipment, or null if not found
     * @throws SQLException If a database error occurs
     */
    private DefensiveEquipment loadDefensiveEquipment(Connection conn, int ownerId) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(
                "SELECT equipment_type, name, power FROM equipment WHERE owner_id = ? AND equipment_type IN ('SHIELD', 'ROBE') LIMIT 1")) {
            stmt.setInt(1, ownerId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String type = rs.getString("equipment_type");
                    String name = rs.getString("name");
                    int power = rs.getInt("power");

                    return type.equals("SHIELD") ?
                            new Shield(type, name, power) :
                            new Robe(type, name, power);
                }
            }
        }
        return new Shield("None", "Skin", 0);
    }
    /**
     * Updates an existing character and their equipment in the database.
     * Unlike saveCharacter (which INSERTs a new row), this UPDATEs the character's
     * stats in place and replaces their equipment.
     *
     * @param character The character to update (must already have a valid databaseId)
     * @throws SQLException If a database error occurs
     */
    public void updateCharacter(Character character) throws SQLException {
        LinkDB linkDB = new LinkDB();
        linkDB.connect();

        try (Connection conn = linkDB.getConnection()) {
            conn.setAutoCommit(false);

            int characterId = character.getDatabaseId();

            updateCharacterInfo(conn, character, characterId);
            deleteEquipment(conn, characterId);
            saveOffensiveEquipment(conn, character.getOffensiveEquipment(), characterId);
            saveDefensiveEquipment(conn, character.getDefensiveEquipment(), characterId);

            conn.commit();
            System.out.println("✅ Personnage mis à jour dans la base de données.");
        } finally {
            linkDB.close();
        }
    }

    /**
     * Updates a character's stats (health and base attack power) in the database.
     *
     * @param conn The database connection
     * @param character The character whose stats to save
     * @param characterId The ID of the character to update
     * @throws SQLException If a database error occurs
     */
    private void updateCharacterInfo(Connection conn, Character character, int characterId) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(
                "UPDATE characters SET health_points = ?, attack_power = ? WHERE id = ?")) {
            stmt.setInt(1, character.getHealthPoints());
            stmt.setInt(2, character.getBaseAttackPower()); // Save base attack power, not total
            stmt.setInt(3, characterId);
            stmt.executeUpdate();
        }
    }

    /**
     * Deletes all equipment belonging to a character.
     *
     * @param conn The database connection
     * @param characterId The ID of the character whose equipment to delete
     * @throws SQLException If a database error occurs
     */
    private void deleteEquipment(Connection conn, int characterId) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(
                "DELETE FROM equipment WHERE owner_id = ?")) {
            stmt.setInt(1, characterId);
            stmt.executeUpdate();
        }
    }

    /**
     * Deletes a character and all their equipment from the database.
     *
     * @param characterId The ID of the character to delete
     * @throws SQLException If a database error occurs
     */
    public void deleteCharacter(int characterId) throws SQLException {
        LinkDB linkDB = new LinkDB();
        linkDB.connect();

        try (Connection conn = linkDB.getConnection()) {
            // Delete equipment first (foreign key constraint)
            deleteEquipment(conn, characterId);

            // Then delete the character
            try (PreparedStatement stmt = conn.prepareStatement(
                    "DELETE FROM characters WHERE id = ?")) {
                stmt.setInt(1, characterId);
                stmt.executeUpdate();
            }

            System.out.println("✅ Personnage supprimé de la base de données.");
        } finally {
            linkDB.close();
        }
    }

}
