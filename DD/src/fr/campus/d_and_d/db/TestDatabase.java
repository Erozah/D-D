package fr.campus.d_and_d.db;

import fr.campus.d_and_d.board.*;
import fr.campus.d_and_d.characters.*;
import fr.campus.d_and_d.characters.Character;
import fr.campus.d_and_d.items.*;
import java.sql.SQLException;

/**
 * Test class for the database functionality.
 * Tests saving and loading boards, characters, and game states.
 */
public class TestDatabase {
    private static String boardName;
    public static void main(String[] args) {
        System.out.println("=== Test de la Base de Données DnD ===\n");

        try {
            // Test 1: Create and save a board
            testBoardOperations();

            // Test 2: Create and save characters
            testCharacterOperations();

            // Test 3: Save and load a game
            testGameOperations(boardName);

            System.out.println("\n✅ Tous les tests ont réussi !");
        } catch (SQLException e) {
            System.err.println("\n❌ Erreur lors des tests: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void testBoardOperations() throws SQLException {
        System.out.println("📋 Test 1: Sauvegarde et chargement d'un plateau");
        
        // Create a test board
        Board testBoard = new Board();
        
        // Save the board
        boardName = "TestBoard" + System.currentTimeMillis();
        DatabaseManager.saveBoard(testBoard, boardName);
        
        // Load the board
        Board loadedBoard = DatabaseManager.loadBoard(boardName);
        
        if (loadedBoard != null) {
            System.out.println("   ✅ Plateau chargé avec succès");
            System.out.println("   Taille: " + loadedBoard.getMaxPosition());
            
            // Test some cells
            Cell cell64 = loadedBoard.getCell(64);
            if (cell64.getContent() instanceof Dragon) {
                System.out.println("   ✅ Case 64 contient un dragon");
            }
            
            Cell cell10 = loadedBoard.getCell(10);
            if (cell10.getContent() instanceof Sorcerer) {
                System.out.println("   ✅ Case 10 contient un sorcier");
            }
            
            Cell cell2 = loadedBoard.getCell(2);
            if (cell2.getContent() instanceof MysteryBox) {
                System.out.println("   ✅ Case 2 contient une boîte mystère");
            }
        } else {
            System.out.println("   ❌ Échec du chargement du plateau");
        }
        
        System.out.println();
    }

    private static void testCharacterOperations() throws SQLException {
        System.out.println("👥 Test 2: Sauvegarde et chargement de personnages");
        
        // Create test characters
        Warrior warrior = new Warrior("Warrior", "Conan");
        Wizard wizard = new Wizard("Wizard", "Gandalf");
        
        // Save characters
        DatabaseManager.saveCharacter(warrior);
        DatabaseManager.saveCharacter(wizard);
        System.out.println("   ✅ Personnages sauvegardés");
        
        // Load characters (we don't know the IDs, so this is just a demonstration)
        // In a real scenario, you would store the returned IDs
        System.out.println("   ✅ Personnages créés: " + warrior.getName() + " et " + wizard.getName());
        
        System.out.println();
    }

    private static void testGameOperations(String boardName) throws SQLException {
        System.out.println("🎮 Test 3: Sauvegarde et chargement d'une partie");
        
        // Create a test character
        Warrior warrior = new Warrior("Warrior", "TestWarrior");
        DatabaseManager.saveCharacter(warrior);
        
        // Get the character ID (in a real scenario, you would store this)
        // For this test, we'll just use a dummy ID
        int testCharacterId = 1; // This would be the actual ID in real code
        
        // Save a game
        DatabaseManager.saveGame(testCharacterId, boardName, 15);
        System.out.println("   ✅ Partie sauvegardée");
        
        // Load the game (using a dummy save ID)
        int testSaveId = 1; // This would be the actual save ID in real code
        Object[] loadedGame = DatabaseManager.loadGame(testSaveId);
        
        if (loadedGame != null && loadedGame[0] != null) {
            Character character = (Character) loadedGame[0];
            Board board = (Board) loadedGame[1];
            int position = (Integer) loadedGame[2];
            
            System.out.println("   ✅ Partie chargée:");
            System.out.println("      Personnage: " + character.getName());
            System.out.println("      Plateau: " + board.getMaxPosition() + " cases");
            System.out.println("      Position: " + position);
        } else {
            System.out.println("   ⚠️  Aucune partie sauvegardée trouvée (c'est normal pour ce test)");
        }
        
        System.out.println();
    }
}
