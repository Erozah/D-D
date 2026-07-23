/**
 * Manages the main game logic, including player movement on the board.
 * This class uses a Board and a Dice to simulate the game.
 */
package fr.campus.d_and_d.gameLogic;
import fr.campus.d_and_d.board.*;
import fr.campus.d_and_d.characters.Character;
import fr.campus.d_and_d.characters.Warrior;
import fr.campus.d_and_d.characters.Wizard;
import fr.campus.d_and_d.items.*;
import fr.campus.d_and_d.db.DatabaseManager;
import fr.campus.d_and_d.db.LinkDB;
import java.util.Scanner;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The Game class handles the core game logic, including character creation,
 * player movement, and game state management.
 */
public class Game {
	private Character player;
	private Board board;
	private boolean gameOver = false;
	private Menu menu = new Menu();
	
	/**
	 * Sets the player character (used for testing).
	 * @param player The character to set as player
	 */
	public void setPlayer(Character player) {
		this.player = player;
	}
	
	/**
	 * Gets the current player character (used for testing).
	 * @return The current player character
	 */
	public Character getPlayer() {
		return this.player;
	}
	
	public void displayTitle() {
		System.out.println("""
				
				[0;37;40m█[0;91;1;47m░░[0;97;1;47m░▒▓▒░[0;91;1;47m░[0;37;40m▄                         [0;97;1;47m░[0;37;40m██[0;97;1;47m░[0;37;40m                                       ▄[0;91;1;47m░░[0;97;1;47m░▒▓▒░[0;91;1;47m░[0;37;40m▄      █[0;91;1;47m░░[0;97;1;47m░▒▓▒░[0;91;1;47m░[0;37;40m▄                                                                  [0m
				[0;91;1;47m░▒▒[0;91;1;40m▓[0;37;40m▀▀[0;91;1;40m▓[0;91;1;47m░░░[0;37;40m                                                                    [0;91;1;47m░▒▒[0;91;1;40m▓[0;37;40m▀▀[0;91;1;40m▓[0;91;1;47m▒▒░[0;37;40m      [0;91;1;47m░▒▒[0;91;1;40m▓[0;37;40m▀▀[0;91;1;40m▓[0;91;1;47m░░░[0;37;40m                                                                  [0m
				[0;91;1;47m▒▓▓[0;91;1;40m▒[0;37;40m  [0;91;1;40m▒[0;91;1;47m░░▒[0;37;40m ▄[0;91;1;47m░░[0;97;1;47m░▒▓▒░[0;37;40m█▄ ▄[0;91;1;47m░░[0;97;1;47m░▒▓▒░[0;37;40m█▄   █▓▓█ ▄[0;91;1;47m░░[0;97;1;47m░▒▓▒░[0;37;40m█▄ ▄[0;91;1;47m░░[0;97;1;47m░▒▓▒░[0;37;40m█▄ ▄█[0;97;1;47m░▒▓▒░[0;91;1;47m░░[0;37;40m▄      [0;91;1;47m▒▓▓[0;91;1;40m▒[0;37;40m  [0;91;1;40m▒[0;91;1;47m▓▓▒[0;37;40m      [0;91;1;47m▒▓▓[0;91;1;40m▒[0;37;40m  [0;91;1;40m▒[0;91;1;47m░░▒[0;37;40m █[0;91;1;47m░░[0;97;1;47m░▒▓▒░[0;37;40m█▄ ▄[0;91;1;47m░░[0;97;1;47m░▒▓▒░[0;37;40m█▓ ▄█[0;97;1;47m░▒▓▒░[0;91;1;47m░░[0;37;40m▄ ▄[0;91;1;47m░░[0;97;1;47m░▒▓▒░[0;37;40m█▄ ▄[0;91;1;47m░░[0;97;1;47m░▒▓▒░[0;37;40m█▄ ▄█[0;97;1;47m░▒▓▒░[0;91;1;47m░░[0;37;40m▄[0m
				[0;91;1;47m▓▓▓[0;91;1;40m░[0;37;40m  [0;91;1;40m░[0;91;1;47m▒▒▓[0;37;40m [0;91;1;47m░▒▒[0;91;1;40m▓[0;37;40m▀▀[0;91;1;40m▓[0;91;1;47m▒▒░[0;37;40m [0;91;1;47m░▒▒[0;91;1;40m▓[0;37;40m▀▀[0;91;1;40m▓[0;91;1;47m▒▒░[0;37;40m   [0;91;1;47m░▒▒░[0;37;40m [0;91;1;47m░▒▒[0;91;1;40m▓[0;37;40m▀▀[0;91;1;40m▓[0;91;1;47m▒▒░[0;37;40m [0;91;1;47m░▒▒[0;91;1;40m▓[0;37;40m▀▀[0;91;1;40m▓[0;91;1;47m▒▒░[0;37;40m [0;91;1;47m░▒▒[0;91;1;40m▓[0;37;40m▀▀[0;91;1;40m▓[0;91;1;47m▒▒░[0;37;40m      [0;31;40m▀[0;91;1;41m▀[0;91;1;40m██[0;31;40m▄[0;91;1;41m▄[0;91;1;40m█[0;91;1;47m▓[0;91;1;40m▀[0;31;40m▀[0;37;40m      [0;91;1;47m▓▓▓[0;91;1;40m░[0;37;40m  [0;91;1;40m░[0;91;1;47m▒▒▓[0;37;40m [0;91;1;47m░▒▒[0;91;1;40m▓[0;37;40m▀▀[0;91;1;40m▓[0;91;1;47m▒▒░[0;37;40m [0;91;1;47m░▒▒[0;91;1;40m▓[0;37;40m▀▀[0;91;1;40m▓[0;91;1;47m▒▒░[0;37;40m [0;91;1;47m░▒▒[0;91;1;40m▓[0;37;40m▀▀[0;91;1;40m▓[0;91;1;47m▒▒░[0;37;40m [0;91;1;47m░▒▒[0;91;1;40m▓[0;37;40m▀▀[0;91;1;40m▓[0;91;1;47m▒▒░[0;37;40m [0;91;1;47m░▒▒[0;91;1;40m▓[0;37;40m▀▀[0;91;1;40m▓[0;91;1;47m▒▒░[0;37;40m [0;91;1;47m░▒▒[0;91;1;40m▓[0;37;40m▀▀[0;91;1;40m▓[0;91;1;47m▒▒░[0m
				[0;91;1;40m█[0;91;1;47m▓▓[0;31;40m▓[0;37;40m  [0;91;1;41m░[0;91;1;47m▓▓▓[0;37;40m [0;91;1;47m▒▓▓[0;91;1;40m▒[0;37;40m  [0;91;1;40m▒[0;91;1;47m▓▓▒[0;37;40m [0;91;1;47m▒▓▓[0;91;1;40m▒[0;37;40m  [0;91;1;40m▒[0;91;1;47m▓▓▒[0;37;40m   ▓[0;91;1;47m▓▓▒[0;37;40m [0;91;1;47m▒▓▓[0;91;1;40m▒[0;37;40m  [0;91;1;40m▒[0;91;1;47m▓▓▒[0;37;40m [0;91;1;47m▒▓▓[0;91;1;40m▒[0;37;40m  [0;91;1;40m▒[0;91;1;47m▓▓▒[0;37;40m [0;91;1;41m▀[0;91;1;47m▓▓▓[0;33;40m▄[0;91;1;40m▄[0;31;40m▄[0;37;40m           [0;91;1;41m▄[0;91;1;40m█▓█▓[0;37;40m [0;91;1;40m▄▒[0;37;40m      [0;91;1;40m█[0;91;1;47m▓▓[0;31;40m▓[0;37;40m  [0;91;1;41m░[0;91;1;47m▓▓▓[0;37;40m [0;91;1;47m▒▓▓[0;91;1;40m▒[0;37;40m  [0;31;40m▀▀▀▀[0;37;40m [0;91;1;47m▒▓▓[0;91;1;40m▒[0;37;40m  [0;91;1;40m▒[0;91;1;47m▓▓▒[0;37;40m [0;91;1;47m▒▓▓[0;91;1;40m▒[0;37;40m  [0;91;1;40m▒[0;91;1;47m▓▓▒[0;37;40m [0;91;1;47m▒▓▓[0;91;1;40m▒[0;37;40m  [0;91;1;40m▒[0;91;1;47m▓▓▒[0;37;40m [0;91;1;47m▒▓▓[0;91;1;40m▒[0;37;40m  [0;91;1;40m▒[0;91;1;47m▓▓▒[0;37;40m [0;91;1;41m▀[0;91;1;47m▓▓▓[0;33;40m▄[0;91;1;40m▄[0;31;40m▄[0;37;40m   [0m
				[0;91;1;41m▒▓█░[0;37;40m  [0;91;1;40m▒[0;91;1;41m▓▓▒[0;37;40m [0;91;1;40m█[0;91;1;47m▓▓[0;91;1;40m▒[0;37;40m  [0;91;1;40m▒[0;91;1;47m▓▓[0;91;1;40m█[0;37;40m [0;91;1;40m█[0;91;1;47m▓▓[0;91;1;40m▒[0;37;40m  [0;91;1;40m▒[0;91;1;47m▓▓[0;91;1;40m█[0;37;40m   [0;91;1;40m▓[0;91;1;47m▓▓▓[0;37;40m [0;91;1;40m█[0;91;1;47m▓▓[0;91;1;40m▒[0;37;40m  [0;91;1;40m▒[0;91;1;47m▓▓[0;91;1;40m█[0;37;40m [0;91;1;40m█[0;91;1;47m▓▓[0;91;1;40m▒[0;37;40m  [0;91;1;40m▒[0;91;1;47m▓▓[0;91;1;40m█[0;37;40m  [0;31;40m▀▀[0;91;1;40m▀[0;33;41m▀[0;91;1;47m▓▓▓[0;91;1;41m▄[0;31;40m▄[0;37;40m      [0;91;1;40m▄[0;91;1;41m▓▓[0;91;1;40m▀[0;31;40m▀[0;91;1;41m▀[0;91;1;40m█[0;91;1;41m▓▀[0;31;40m▀[0;37;40m      [0;91;1;41m▒▓█░[0;37;40m  [0;91;1;40m▒[0;91;1;41m▓▓▒[0;37;40m [0;91;1;40m█[0;91;1;47m▓▓[0;91;1;40m▒[0;37;40m       [0;91;1;40m█[0;91;1;47m▓▓[0;91;1;40m▒[0;37;40m  [0;91;1;40m▒[0;91;1;47m▓▓[0;91;1;40m█[0;37;40m [0;91;1;40m█[0;91;1;47m▓▓[0;91;1;40m▒[0;37;40m  [0;91;1;40m▒[0;91;1;47m▓▓[0;91;1;40m█[0;37;40m [0;91;1;40m█[0;91;1;47m▓▓[0;91;1;40m▒[0;37;40m  [0;91;1;40m▒[0;91;1;47m▓▓[0;91;1;40m█[0;37;40m [0;91;1;40m█[0;91;1;47m▓▓[0;91;1;40m▒[0;37;40m  [0;91;1;40m▒[0;91;1;47m▓▓[0;91;1;40m█[0;37;40m  [0;31;40m▀▀[0;91;1;40m▀[0;33;41m▀[0;91;1;47m▓▓▓[0;91;1;41m▄[0;31;40m▄[0m
				[0;91;1;41m▒▓▓[0;91;1;40m▒[0;37;40m  [0;91;1;40m▒[0;91;1;41m▓▓▒[0;37;40m [0;91;1;41m▒▓▓[0;91;1;40m░[0;37;40m  [0;91;1;40m▒[0;91;1;41m▓▓▒[0;37;40m [0;91;1;40m█[0;91;1;41m▓▓[0;91;1;40m░[0;37;40m  [0;91;1;40m▒[0;91;1;41m▓▓▒[0;37;40m   [0;91;1;40m▒[0;91;1;47m▓▓[0;91;1;40m█[0;37;40m [0;91;1;41m▒▓▓[0;91;1;40m░[0;37;40m  [0;91;1;40m▒[0;91;1;41m▓▓▒[0;37;40m [0;91;1;40m█[0;91;1;41m▓▓[0;91;1;40m░[0;37;40m  [0;91;1;40m▒[0;91;1;41m▓▓▒[0;37;40m [0;91;1;41m▄▄▄▄[0;37;40m [0;31;40m▀[0;91;1;40m░[0;91;1;41m▓▓▒[0;37;40m      [0;91;1;41m▒▓▓[0;31;40m▓[0;37;40m [0;91;1;40m▄▓▓[0;91;1;41m▓▄[0;37;40m      [0;91;1;41m▒▓▓[0;91;1;40m▒[0;37;40m  [0;91;1;40m▒[0;91;1;41m▓▓▒[0;37;40m [0;91;1;40m█[0;91;1;41m▓▓[0;91;1;40m░[0;37;40m       [0;91;1;41m▒▓▓[0;91;1;40m░[0;37;40m  [0;91;1;40m▒[0;91;1;41m▓▓▒[0;37;40m [0;91;1;41m▒▓▓[0;91;1;40m▒[0;37;40m  [0;91;1;40m░[0;91;1;41m▓▓[0;91;1;40m█[0;37;40m [0;91;1;41m▒▓▓[0;91;1;40m░[0;37;40m  [0;91;1;40m▒[0;91;1;41m▓▓▒[0;37;40m [0;91;1;40m█[0;91;1;41m▓▓[0;91;1;40m░[0;37;40m  [0;91;1;40m▒[0;91;1;41m▓▓▒[0;37;40m [0;91;1;41m▄▄▄▄[0;37;40m [0;31;40m▀[0;91;1;40m░[0;91;1;41m▓▓▒[0m
				[0;91;1;41m░▒▒[0;91;1;40m▓▓▓▓[0;91;1;41m▒▒▀[0;37;40m [0;91;1;41m▀▒▒▓▓▓▓[0;91;1;40m▓▓[0;91;1;41m▀[0;37;40m [0;91;1;40m▓[0;91;1;41m▒▒[0;91;1;40m░[0;37;40m  [0;91;1;40m▒▓▓▒[0;37;40m   [0;91;1;40m░[0;91;1;41m▓▓[0;91;1;40m▓[0;37;40m [0;91;1;41m▀▒▒▓▓▓▓[0;91;1;40m▓▓[0;91;1;41m▀[0;37;40m [0;91;1;40m▓[0;91;1;41m▒▒[0;91;1;40m░[0;37;40m  [0;91;1;40m▒▓▓▒[0;37;40m [0;91;1;40m▓▓▓[0;91;1;41m▓▓▓▓▒▒▀[0;37;40m      [0;91;1;40m░[0;91;1;41m▒▒▓▓▓[0;91;1;40m▓▒[0;91;1;41m▀[0;91;1;40m▒[0;37;40m      [0;91;1;41m░▒▒[0;91;1;40m▓▓▓▓[0;91;1;41m▒▒▀[0;37;40m [0;91;1;40m▓[0;91;1;41m▒▒[0;91;1;40m░[0;37;40m       [0;91;1;41m▀▒▒▓▓▓▓[0;91;1;40m▓▓▓[0;37;40m [0;91;1;41m▀[0;91;1;40m▓▓[0;91;1;41m▓▓▓▓[0;91;1;40m▒▒▓[0;37;40m [0;91;1;41m▀▒▒▓▓▓▓[0;91;1;40m▓▓[0;91;1;41m▀[0;37;40m [0;91;1;40m▓[0;91;1;41m▒▒[0;91;1;40m░[0;37;40m  [0;91;1;40m▒▓▓▒[0;37;40m [0;91;1;40m▓▓▓[0;91;1;41m▓▓▓▓▒▒▀[0m
				[0;31;40m▀▀▀▀▀▀▀▀▀[0;37;40m   [0;31;40m▀▀▀▀▀▀▀▀[0;37;40m  [0;31;40m▀▀▀▀[0;37;40m  [0;31;40m▀▀▀▀[0;37;40m [0;91;1;41m▓▓▓[0;91;1;40m▓▓[0;91;1;41m▀[0;37;40m  [0;31;40m▀▀▀▀▀▀▀▀[0;37;40m  [0;31;40m▀▀▀▀[0;37;40m  [0;31;40m▀▀▀▀[0;37;40m [0;31;40m▀▀▀▀▀▀▀▀▀[0;37;40m        [0;31;40m▀▀▀▀▀▀▀[0;37;40m [0;31;40m▀[0;37;40m      [0;31;40m▀▀▀▀▀▀▀▀▀[0;37;40m  [0;31;40m▀▀▀▀[0;37;40m        [0;31;40m▀▀▀▀▀▀▀▀▀[0;37;40m  [0;31;40m▀▀▀▀▀[0;91;1;40m░▒▒▓[0;37;40m  [0;31;40m▀▀▀▀▀▀▀▀[0;37;40m  [0;31;40m▀▀▀▀[0;37;40m  [0;31;40m▀▀▀▀[0;37;40m [0;31;40m▀▀▀▀▀▀▀▀▀[0;37;40m [0m
				[0;37;40m                                 [0;31;40m▀▀▀▀▀[0;37;40m                                                                                         [0;31;40m▒[0;91;1;40m░░░░░░░[0;90;1;41m▒[0;91;1;40m▀[0;37;40m                                 [0m
				[0;37;40m                                                                                                                               [0;31;40m▀▀▀▀▀▀▀▀[0;37;40m                                   [0m
				""");
	}
	/**
	 * Displays the main menu and handles user choices for creating a character or starting the game.
	 */
	public void mainMenu() {
		displayTitle();
		Menu menu = new Menu();
		menu.beforeLine();
		menu.printCenteredLine("Bienvenue dans l'univers de donjons et dragons !");
		menu.afterLine();
		String choice = menu.askPlayerString("1. Nouveau personnage",
				"2. Charger un personnage",
				"3. Quitter");
		if (choice.equals("1")) {
			this.createCharacter();
		} else if (choice.equals("2")) {
			this.loadCharacter();
		} else {
			return; // Quitter
		}
		
		// Après avoir créé ou chargé un personnage, montrer le menu principal
		choice = menu.askPlayerString("1. Démarrer la partie",
				"2. Voir les statistiques du personnage",
				"3. Quitter");
		if (choice.equals("1")) {
			this.start();
		} else if (choice.equals("2")) {
			this.showCharacterStats();
		} // choice.equals("3") or other: just return to exit
	}
	/**
	 * Displays the current character's statistics.
	 */
	public void showCharacterStats() {
		if (player == null) {
			menu.printCenteredLine("Aucun personnage créé. Veuillez d'abord créer un personnage.");
			mainMenu(); // Return to main menu
			return;
		}
		
		menu.beforeLine();
		menu.printCenteredLine("Statistiques de votre personnage");
		menu.afterLine();
		System.out.println(player);
		
		// Show additional stats
		System.out.println("Équipement offensif: " + player.getOffensiveEquipment().getName());
		System.out.println("Équipement défensif: " + player.getDefensiveEquipment().getName());
		
		String choice = menu.askPlayerString("1. Retour au menu principal", "2. Quitter");
		if (choice.equals("1")) {
			mainMenu();
		} // choice.equals("2") or other: exit
	}
	
	/**
	 * Guides the player through character creation, allowing them to choose between a Warrior or Wizard.
	 */
	public void createCharacter() {
		Menu menu = new Menu();
		String characterName = menu.askPlayerString("Quel est votre nom ?");
		String choice = menu.askPlayerString("Choisissez votre classe :",
				"1. Guerrier",
				"2. Magicien");
		if (choice.equals("1")) {
			this.player = new Warrior("Guerrier", characterName);
			System.out.println(player);
			return;
		}
		if (choice.equals("2")) {
			this.player = new Wizard("Magicien", characterName);
			System.out.println(player);
			return;
		}
	}

	/**
	 * Loads a character from the database.
	 */
	public void loadCharacter() {
		try {
			Menu menu = new Menu();
			String characterIdStr = menu.askPlayerString("Entrez l'ID du personnage à charger :");
			int characterId = Integer.parseInt(characterIdStr);
			
			Character loadedCharacter = DatabaseManager.loadCharacter(characterId);
			
			if (loadedCharacter != null) {
				this.player = loadedCharacter;
				menu.printCenteredLine("Personnage chargé avec succès !");
				System.out.println(player);
			} else {
				menu.printCenteredLine("Aucun personnage trouvé avec cet ID.");
			}
		} catch (NumberFormatException e) {
			menu.printCenteredLine("ID invalide. Veuillez entrer un nombre.");
		} catch (SQLException e) {
			menu.printCenteredLine("Erreur lors du chargement du personnage : " + e.getMessage());
		}
	}

	/**
	 * Handles a single turn of the game, including rolling the dice and moving the player.
	 * @param board The game board.
	 * @param dice The dice used to determine movement.
	 */
	public void playTurn(Board board, Dice dice) {
		Menu menu = new Menu();
		menu.askPlayerString("Appuyez sur 'Entrée' pour lancer le dé...");
		int diceResult = dice.roll();
		try {
			int newPosition = board.getCurrentPosition() + diceResult;
			board.setCurrentPosition(newPosition);
			System.out.println(board.toString());
			Cell currentCell = board.getCurrentCell();
			String interactionResult = currentCell.interact(player);
			System.out.println(interactionResult);
			
			// Check if player is still alive
			if (player.getHealthPoints() <= 0) {
				gameOver = true;
				System.out.println("Game Over. Vous avez été vaincu.");
			}
			
			// Check if player reached the end
			if (board.getCurrentPosition() >= board.getMaxPosition()) {
				gameOver = true;
				endGame();
			}
		} catch (OutOfBoardException e) {
			System.out.println("Erreur : " + e.getMessage());
		}
	}

	/**
	 * Ends the game and displays a congratulatory message.
	 */
	public void endGame() {
		System.out.println("Félicitations ! Vous avez terminé le plateau.");
	}
	/**
	 * Starts the main game loop. The player moves on the board by rolling the dice
	 * until they reach the last cell (64). Displays the player's position each turn.
	 */
	public void start() {
		this.board = new Board();
		SixSidedDice dice = new SixSidedDice();
		Scanner scanner = new Scanner(System.in);
		
		while (!gameOver && board.getCurrentPosition() < board.getMaxPosition()) {
			playTurn(board, dice);
			if (!gameOver) {
				String choice = menu.askPlayerString("1. Continuer", "2. Quitter");
				if (choice.equals("2")) {
					break;
				}
			}
		}
		
		if (player.getHealthPoints() > 0 && board.getCurrentPosition() >= board.getMaxPosition()) {
			endGame();
		}
		scanner.close();
	}

	@Override
	public String toString() {
		return super.toString();
	}
}
