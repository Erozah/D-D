package fr.campus.d_and_d.db;

import fr.campus.d_and_d.board.*;
import fr.campus.d_and_d.characters.*;
import fr.campus.d_and_d.characters.Character;
import fr.campus.d_and_d.items.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Manages all database operations for the game.
 * Handles saving and loading boards, characters, and game state.
 */
public class DatabaseManager {

    /**
     * Saves a board to the database.
     * @param board The board to save
     * @param boardName The name of the board
     * @throws SQLException If a database error occurs
     */
    public static void saveBoard(Board board, String boardName) throws SQLException {
        LinkDB linkDB = new LinkDB();
        linkDB.connect();
        try (Connection conn = linkDB.getConnection()) {
            conn.setAutoCommit(false);

            // Save the board
            int boardId = saveBoardInfo(conn, boardName, board.getMaxPosition());

            // Save all cells
            saveCells(conn, boardId, board);

            conn.commit();
            System.out.println("✅ Plateau sauvegardé dans la base de données.");
        }
    }

    /**
     * Saves board information.
     * @param conn The database connection
     * @param boardName The name of the board
     * @param size The size of the board
     * @return The ID of the saved board
     * @throws SQLException If a database error occurs
     */
    private static int saveBoardInfo(Connection conn, String boardName, int size) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(
                "INSERT INTO boards (name, size) VALUES (?, ?)",
                Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, boardName);
            stmt.setInt(2, size);
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        throw new SQLException("Failed to save board info");
    }

    /**
     * Saves all cells of a board.
     * @param conn The database connection
     * @param boardId The ID of the board
     * @param board The board to save
     * @throws SQLException If a database error occurs
     */
    private static void saveCells(Connection conn, int boardId, Board board) throws SQLException {
        for (int i = 1; i <= board.getMaxPosition(); i++) {
            Cell cell = board.getCell(i);
            saveCell(conn, boardId, i, cell);
        }
    }

    /**
     * Saves a single cell.
     * @param conn The database connection
     * @param boardId The ID of the board
     * @param position The position of the cell
     * @param cell The cell to save
     * @throws SQLException If a database error occurs
     */
    private static void saveCell(Connection conn, int boardId, int position, Cell cell) throws SQLException {
        String contentType = getContentType(cell);
        int cellId = saveCellInfo(conn, boardId, position, contentType);

        if (cell.getContent() != null) {
            saveCellContent(conn, cellId, cell.getContent());
            
            // For non-enemy, non-mystery box content, we need to update the cell_id in equipment
            if (!(cell.getContent() instanceof Enemy) && !(cell.getContent() instanceof MysteryBox)) {
                try (PreparedStatement updateStmt = conn.prepareStatement(
                        "UPDATE equipment SET cell_id = ? WHERE owner_id = ?")) {
                    updateStmt.setInt(1, cellId);
                    updateStmt.setInt(2, cellId);
                    updateStmt.executeUpdate();
                }
            }
        }
    }

    /**
     * Saves cell information.
     * @param conn The database connection
     * @param boardId The ID of the board
     * @param position The position of the cell
     * @param contentType The type of content
     * @return The ID of the saved cell
     * @throws SQLException If a database error occurs
     */
    private static int saveCellInfo(Connection conn, int boardId, int position, String contentType) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(
                "INSERT INTO cells (board_id, position, content_type) VALUES (?, ?, ?)",
                Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, boardId);
            stmt.setInt(2, position);
            stmt.setString(3, contentType);
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        throw new SQLException("Failed to save cell info");
    }

    /**
     * Saves cell content.
     * @param conn The database connection
     * @param cellId The ID of the cell
     * @param content The content to save
     * @throws SQLException If a database error occurs
     */
    private static void saveCellContent(Connection conn, int cellId, CellContent content) throws SQLException {
        if (content instanceof Enemy) {
            saveEnemy(conn, (Enemy) content, cellId);
        } else if (content instanceof MysteryBox) {
            saveMysteryBox(conn, (MysteryBox) content, cellId);
        } else if (content instanceof OffensiveEquipment) {
            saveEquipment(conn, (OffensiveEquipment) content, cellId);
            // Update cell with equipment ID
            try (PreparedStatement updateStmt = conn.prepareStatement(
                    "UPDATE cells SET content_id = (SELECT id FROM equipment WHERE owner_id = ? LIMIT 1) WHERE id = ?")) {
                updateStmt.setInt(1, cellId);
                updateStmt.setInt(2, cellId);
                updateStmt.executeUpdate();
            }
        } else if (content instanceof DefensiveEquipment) {
            saveDefensiveEquipment(conn, (DefensiveEquipment) content, cellId);
            // Update cell with equipment ID
            try (PreparedStatement updateStmt = conn.prepareStatement(
                    "UPDATE cells SET content_id = (SELECT id FROM equipment WHERE owner_id = ? LIMIT 1) WHERE id = ?")) {
                updateStmt.setInt(1, cellId);
                updateStmt.setInt(2, cellId);
                updateStmt.executeUpdate();
            }
        }
    }
    /**
     * Saves an enemy.
     * @param conn The database connection
     * @param enemy The enemy to save
     * @param cellId The ID of the cell containing the enemy
     * @throws SQLException If a database error occurs
     */
    private static void saveEnemy(Connection conn, Enemy enemy, int cellId) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(
                "INSERT INTO characters (character_type, name, health_points, attack_power, is_enemy, is_boss, cell_id) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)",
                Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, enemy.getType());
            stmt.setString(2, enemy.getName());
            stmt.setInt(3, enemy.getHealthPoints());
            stmt.setInt(4, enemy.getAttackPower());
            stmt.setBoolean(5, true);
            stmt.setBoolean(6, enemy.isBoss());
            stmt.setInt(7, cellId);
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    int characterId = rs.getInt(1);
                    saveEquipment(conn, enemy.getOffensiveEquipment(), characterId);
                    if (enemy.getDefensiveEquipment() instanceof DefensiveEquipment) {
                        saveDefensiveEquipment(conn, (DefensiveEquipment) enemy.getDefensiveEquipment(), characterId);
                    }
                    
                    // Update cell with character ID
                    try (PreparedStatement updateStmt = conn.prepareStatement(
                            "UPDATE cells SET content_id = ? WHERE id = ?")) {
                        updateStmt.setInt(1, characterId);
                        updateStmt.setInt(2, cellId);
                        updateStmt.executeUpdate();
                    }
                }
            }
        }
    }

    /**
     * Saves a mystery box.
     * @param conn The database connection
     * @param box The mystery box to save
     * @param cellId The ID of the cell containing the box
     * @throws SQLException If a database error occurs
     */
    private static void saveMysteryBox(Connection conn, MysteryBox box, int cellId) throws SQLException {
        CellContent content = box.getContent();
        
        // For mystery boxes, we save the equipment with cell_id but no owner_id
        if (content instanceof OffensiveEquipment) {
            saveEquipmentForCell(conn, (OffensiveEquipment) content, cellId);
        } else if (content instanceof DefensiveEquipment) {
            saveDefensiveEquipmentForCell(conn, (DefensiveEquipment) content, cellId);
        }
        
        // Update cell with equipment ID
        try (PreparedStatement stmt = conn.prepareStatement(
                "UPDATE cells SET content_id = (SELECT id FROM equipment WHERE cell_id = ? LIMIT 1) WHERE id = ?")) {
            stmt.setInt(1, cellId);
            stmt.setInt(2, cellId);
            stmt.executeUpdate();
        }
    }

    /**
     * Saves offensive equipment.
     * @param conn The database connection
     * @param equipment The equipment to save
     * @param ownerId The ID of the owner (character or cell)
     * @throws SQLException If a database error occurs
     */
    private static void saveEquipment(Connection conn, OffensiveEquipment equipment, int ownerId) throws SQLException {
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
     * @param conn The database connection
     * @param equipment The equipment to save
     * @param ownerId The ID of the owner (character)
     * @throws SQLException If a database error occurs
     */
    private static void saveDefensiveEquipment(Connection conn, DefensiveEquipment equipment, int ownerId) throws SQLException {
        String equipType = equipment instanceof Shield ? "SHIELD" : "POTION";
        
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
     * Saves offensive equipment for a cell (no owner).
     * @param conn The database connection
     * @param equipment The equipment to save
     * @param cellId The ID of the cell
     * @throws SQLException If a database error occurs
     */
    private static void saveEquipmentForCell(Connection conn, OffensiveEquipment equipment, int cellId) throws SQLException {
        String equipType = equipment instanceof Weapon ? "WEAPON" : "SPELL";
        
        try (PreparedStatement stmt = conn.prepareStatement(
                "INSERT INTO equipment (equipment_type, name, power, cell_id) VALUES (?, ?, ?, ?)")) {
            stmt.setString(1, equipType);
            stmt.setString(2, equipment.getName());
            stmt.setInt(3, equipment.getAttackPower());
            stmt.setInt(4, cellId);
            stmt.executeUpdate();
        }
    }

    /**
     * Saves defensive equipment for a cell (no owner).
     * @param conn The database connection
     * @param equipment The equipment to save
     * @param cellId The ID of the cell
     * @throws SQLException If a database error occurs
     */
    private static void saveDefensiveEquipmentForCell(Connection conn, DefensiveEquipment equipment, int cellId) throws SQLException {
        String equipType = equipment instanceof Shield ? "SHIELD" : "POTION";
        
        try (PreparedStatement stmt = conn.prepareStatement(
                "INSERT INTO equipment (equipment_type, name, power, cell_id) VALUES (?, ?, ?, ?)")) {
            stmt.setString(1, equipType);
            stmt.setString(2, equipment.getName());
            stmt.setInt(3, equipment.getDefensePoints());
            stmt.setInt(4, cellId);
            stmt.executeUpdate();
        }
    }

    /**
     * Loads a board from the database.
     * @param boardName The name of the board to load
     * @return The loaded board, or null if not found
     * @throws SQLException If a database error occurs
     */
    public static Board loadBoard(String boardName) throws SQLException {
        try (Connection conn = getConnection()) {
            // Load board info
            int boardId = getBoardId(conn, boardName);
            if (boardId == -1) return null;

            int size = getBoardSize(conn, boardId);
            Board board = new Board(size);

            // Load all cells
            loadCells(conn, boardId, board);

            System.out.println("✅ Plateau chargé depuis la base de données.");
            return board;
        }
    }

    /**
     * Gets the ID of a board by name.
     * @param conn The database connection
     * @param boardName The name of the board
     * @return The board ID, or -1 if not found
     * @throws SQLException If a database error occurs
     */
    private static int getBoardId(Connection conn, String boardName) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(
                "SELECT id FROM boards WHERE name = ?")) {
            stmt.setString(1, boardName);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        return -1;
    }

    /**
     * Gets the size of a board.
     * @param conn The database connection
     * @param boardId The ID of the board
     * @return The board size
     * @throws SQLException If a database error occurs
     */
    private static int getBoardSize(Connection conn, int boardId) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(
                "SELECT size FROM boards WHERE id = ?")) {
            stmt.setInt(1, boardId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        return 64; // Default size
    }

    /**
     * Loads all cells of a board.
     * @param conn The database connection
     * @param boardId The ID of the board
     * @param board The board to populate
     * @throws SQLException If a database error occurs
     */
    private static void loadCells(Connection conn, int boardId, Board board) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(
                "SELECT position, content_type, content_id FROM cells WHERE board_id = ? ORDER BY position")) {
            stmt.setInt(1, boardId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int position = rs.getInt("position");
                    String contentType = rs.getString("content_type");
                    int contentId = rs.getInt("content_id");
                    
                    CellContent content = loadCellContent(conn, contentType, contentId);
                    board.setCellContent(position, content);
                }
            }
        }
    }

    /**
     * Loads cell content based on type.
     * @param conn The database connection
     * @param contentType The type of content
     * @param contentId The ID of the content
     * @return The loaded content, or null if not found
     * @throws SQLException If a database error occurs
     */
    private static CellContent loadCellContent(Connection conn, String contentType, int contentId) throws SQLException {
        return switch (contentType) {
            case "ENEMY", "BOSS" -> loadEnemy(conn, contentId);
            case "MYSTERY_BOX" -> loadMysteryBox(conn, contentId);
            case "WEAPON", "SPELL", "SHIELD", "POTION" -> loadEquipment(conn, contentId);
            default -> null;
        };
    }

    /**
     * Loads an enemy from the database.
     * @param conn The database connection
     * @param characterId The ID of the character
     * @return The loaded enemy, or null if not found
     * @throws SQLException If a database error occurs
     */
    private static Enemy loadEnemy(Connection conn, int characterId) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(
                "SELECT character_type, name, health_points, attack_power, is_boss FROM characters WHERE id = ?")) {
            stmt.setInt(1, characterId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String type = rs.getString("character_type");
                    String name = rs.getString("name");
                    int health = rs.getInt("health_points");
                    int attack = rs.getInt("attack_power");
                    boolean isBoss = rs.getBoolean("is_boss");
                    
                    OffensiveEquipment weapon = loadOffensiveEquipment(conn, characterId);
                    DefensiveEquipment shield = loadDefensiveEquipment(conn, characterId);
                    
                    return switch (type) {
                        case "DRAGON" -> new Dragon(name, health, attack);
                        case "SORCERER" -> new Sorcerer(name, health, attack);
                        case "GOBLIN" -> new Goblin(name, health, attack);
                        case "ORC" -> new Orc(name, health, attack);
                        default -> null;
                    };
                }
            }
        }
        return null;
    }

    /**
     * Loads offensive equipment from the database.
     * @param conn The database connection
     * @param ownerId The ID of the owner
     * @return The loaded equipment, or null if not found
     * @throws SQLException If a database error occurs
     */
    private static OffensiveEquipment loadOffensiveEquipment(Connection conn, int ownerId) throws SQLException {
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
        return new Weapon("Hand", "Fist", 1); // Default
    }

    /**
     * Loads defensive equipment from the database.
     * @param conn The database connection
     * @param ownerId The ID of the owner
     * @return The loaded equipment, or null if not found
     * @throws SQLException If a database error occurs
     */
    private static DefensiveEquipment loadDefensiveEquipment(Connection conn, int ownerId) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(
                "SELECT equipment_type, name, power FROM equipment WHERE owner_id = ? AND equipment_type IN ('SHIELD', 'POTION') LIMIT 1")) {
            stmt.setInt(1, ownerId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String type = rs.getString("equipment_type");
                    String name = rs.getString("name");
                    int power = rs.getInt("power");
                    
                    return type.equals("SHIELD") ? 
                        new Shield(type, name, power) : 
                        new Potion(type, name, power);
                }
            }
        }
        return new Shield("Armor", "Leather Armor", 1); // Default
    }

    /**
     * Loads equipment from the database.
     * @param conn The database connection
     * @param equipmentId The ID of the equipment
     * @return The loaded equipment, or null if not found
     * @throws SQLException If a database error occurs
     */
    private static CellContent loadEquipment(Connection conn, int equipmentId) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(
                "SELECT equipment_type, name, power FROM equipment WHERE id = ?")) {
            stmt.setInt(1, equipmentId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String type = rs.getString("equipment_type");
                    String name = rs.getString("name");
                    int power = rs.getInt("power");
                    
                    return switch (type) {
                        case "WEAPON" -> new Weapon(type, name, power);
                        case "SPELL" -> new Spell(type, name, power);
                        case "SHIELD" -> new Shield(type, name, power);
                        case "POTION" -> new Potion(type, name, power);
                        default -> null;
                    };
                }
            }
        }
        return null;
    }

    /**
     * Loads a mystery box from the database.
     * @param conn The database connection
     * @param cellId The ID of the cell
     * @return The loaded mystery box, or null if not found
     * @throws SQLException If a database error occurs
     */
    private static MysteryBox loadMysteryBox(Connection conn, int cellId) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(
                "SELECT equipment_type, name, power FROM equipment WHERE cell_id = ? LIMIT 1")) {
            stmt.setInt(1, cellId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String type = rs.getString("equipment_type");
                    String name = rs.getString("name");
                    int power = rs.getInt("power");
                    
                    CellContent content = switch (type) {
                        case "WEAPON" -> new Weapon(type, name, power);
                        case "SPELL" -> new Spell(type, name, power);
                        case "SHIELD" -> new Shield(type, name, power);
                        case "POTION" -> new Potion(type, name, power);
                        default -> new Weapon(type, name, power);
                    };
                    
                    return new MysteryBox(content);
                }
            }
        }
        return null;
    }

    /**
     * Gets a database connection.
     * @return A database connection
     * @throws SQLException If a database error occurs
     */
    private static Connection getConnection() throws SQLException {
        LinkDB linkDB = new LinkDB();
        linkDB.connect();
        return linkDB.getConnection();
    }

    /**
     * Gets the content type for a cell.
     * @param cell The cell
     * @return The content type as a string
     */
    private static String getContentType(Cell cell) {
        if (cell.getContent() == null) {
            return "EMPTY";
        }
        
        CellContent content = cell.getContent();
        if (content instanceof MysteryBox) {
            return "MYSTERY_BOX";
        } else if (content instanceof Dragon || content instanceof Sorcerer || 
                   content instanceof Goblin || content instanceof Orc) {
            return ((Enemy) content).isBoss() ? "BOSS" : "ENEMY";
        } else if (content instanceof OffensiveEquipment) {
            return content instanceof Weapon ? "WEAPON" : "SPELL";
        } else if (content instanceof DefensiveEquipment) {
            return content instanceof Shield ? "SHIELD" : "POTION";
        }
        return "EMPTY";
    }

    /**
     * Saves a character to the database.
     * @param character The character to save
     * @throws SQLException If a database error occurs
     */
    public static void saveCharacter(Character character) throws SQLException {
        try (Connection conn = getConnection()) {
            conn.setAutoCommit(false);
            
            // Save character
            int characterId = saveCharacterInfo(conn, character);
            
            // Save equipment
            saveEquipment(conn, character.getOffensiveEquipment(), characterId);
            if (character.getDefensiveEquipment() instanceof DefensiveEquipment) {
                saveDefensiveEquipment(conn, (DefensiveEquipment) character.getDefensiveEquipment(), characterId);
            }
            
            conn.commit();
            System.out.println("✅ Personnage sauvegardé dans la base de données.");
        }
    }

    /**
     * Saves character information.
     * @param conn The database connection
     * @param character The character to save
     * @return The ID of the saved character
     * @throws SQLException If a database error occurs
     */
    private static int saveCharacterInfo(Connection conn, Character character) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(
                "INSERT INTO characters (character_type, name, health_points, attack_power, is_enemy) " +
                "VALUES (?, ?, ?, ?, ?)",
                Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, character.getType());
            stmt.setString(2, character.getName());
            stmt.setInt(3, character.getHealthPoints());
            stmt.setInt(4, character.getAttackPower());
            stmt.setBoolean(5, false); // Not an enemy
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        throw new SQLException("Failed to save character info");
    }

    /**
     * Loads a character from the database.
     * @param characterId The ID of the character to load
     * @return The loaded character, or null if not found
     * @throws SQLException If a database error occurs
     */
    public static Character loadCharacter(int characterId) throws SQLException {
        try (Connection conn = getConnection()) {
            try (PreparedStatement stmt = conn.prepareStatement(
                    "SELECT character_type, name, health_points, attack_power FROM characters WHERE id = ?")) {
                stmt.setInt(1, characterId);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        String type = rs.getString("character_type");
                        String name = rs.getString("name");
                        int health = rs.getInt("health_points");
                        int attack = rs.getInt("attack_power");
                        
                        OffensiveEquipment weapon = loadOffensiveEquipment(conn, characterId);
                        DefensiveEquipment shield = loadDefensiveEquipment(conn, characterId);
                        
                        Character charInstance = switch (type) {
                            case "WARRIOR" -> new Warrior("Warrior", name);
                            case "WIZARD" -> new Wizard("Wizard", name);
                            default -> null;
                        };
                        
                        if (charInstance != null) {
                            charInstance.setOffensiveEquipment(weapon);
                            charInstance.setDefensiveEquipment(shield);
                        }
                        
                        return charInstance;
                    }
                }
            }
        }
        return null;
    }

    /**
     * Saves a game state to the database.
     * @param characterId The ID of the player character
     * @param boardName The name of the board
     * @param currentPosition The current position on the board
     * @throws SQLException If a database error occurs
     */
    public static void saveGame(int characterId, String boardName, int currentPosition) throws SQLException {
        try (Connection conn = getConnection()) {
            int boardId = getBoardId(conn, boardName);
            if (boardId == -1) {
                // Create the board if it doesn't exist
                boardId = saveBoardInfo(conn, boardName, 64);
                saveCells(conn, boardId, new Board());
            }
            
            try (PreparedStatement stmt = conn.prepareStatement(
                    "INSERT INTO saved_games (player_id, board_id, current_position) VALUES (?, ?, ?)")) {
                stmt.setInt(1, characterId);
                stmt.setInt(2, boardId);
                stmt.setInt(3, currentPosition);
                stmt.executeUpdate();
                System.out.println("✅ Partie sauvegardée.");
            }
        }
    }

    /**
     * Loads a saved game from the database.
     * @param saveId The ID of the saved game
     * @return An array containing: [character, board, currentPosition]
     * @throws SQLException If a database error occurs
     */
    public static Object[] loadGame(int saveId) throws SQLException {
        try (Connection conn = getConnection()) {
            try (PreparedStatement stmt = conn.prepareStatement(
                    "SELECT player_id, board_id, current_position FROM saved_games WHERE id = ?")) {
                stmt.setInt(1, saveId);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        int playerId = rs.getInt("player_id");
                        int boardId = rs.getInt("board_id");
                        int currentPosition = rs.getInt("current_position");
                        
                        Character character = loadCharacter(playerId);
                        Board board = loadBoard(getBoardName(conn, boardId));
                        
                        return new Object[]{character, board, currentPosition};
                    }
                }
            }
        }
        return null;
    }

    /**
     * Gets the name of a board by ID.
     * @param conn The database connection
     * @param boardId The ID of the board
     * @return The board name, or null if not found
     * @throws SQLException If a database error occurs
     */
    private static String getBoardName(Connection conn, int boardId) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(
                "SELECT name FROM boards WHERE id = ?")) {
            stmt.setInt(1, boardId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString(1);
                }
            }
        }
        return null;
    }
}
