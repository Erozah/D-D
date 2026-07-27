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
import fr.campus.d_and_d.db.SimpleDatabaseManager;
import fr.campus.d_and_d.db.LinkDB;
import java.util.Scanner;
import java.util.List;
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
	 * Constructor - initializes the game state
	 */
	public Game() {
		GameState.getInstance().reset(); // Reset game state when starting a new game
	}
	
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
		Menu menu = new Menu();
		menu.beforeLine();
		menu.printCenteredLine("BIENVENUE - Menu Principal");
		menu.afterLine();
		
		while (true) {

			String choice = menu.askPlayerString(
				"1. Démarrer la partie",
				"2. Nouveau personnage",
				"3. Charger un personnage", 
				"4. Voir les statistiques",
				"5. Quitter");
			
			if (choice.equals("1")) {
				// Démarrer la partie
				if (this.player != null) {
					this.start();
					return; // Quitter après la partie
				} else {
					menu.beforeLine();
					menu.printCenteredLine("Pas encore de personnage ! Veuillez en créer ou charger un.");
					menu.afterLine();
				}
			} else if (choice.equals("2")) {
				// Nouveau personnage
				this.createCharacter();
				// Auto-save after creation
				this.autoSaveCharacter();
			} else if (choice.equals("3")) {
				// Charger un personnage
				this.loadCharacter();
			} else if (choice.equals("4")) {
				// Voir les statistiques
				if (this.player != null) {
					this.showCharacterStats();
				} else {
					menu.beforeLine();
					menu.printCenteredLine("Pas encore de personnage ! Veuillez en créer ou charger un.");
					menu.afterLine();
				}
			} else if (choice.equals("5")) {
				// Quitter
				return;
			}
		}
	}

	/**
	 * Auto-saves the character after creation
	 */
	private void autoSaveCharacter() {
		if (this.player != null) {
			try {
				SimpleDatabaseManager dbManager = new SimpleDatabaseManager();
				dbManager.saveCharacter(this.player);
				Menu menu = new Menu();
				menu.printCenteredLine("Personnage sauvegardé avec succès !");
				
				// Display character info with equipment names
				menu.printCenteredLine("Personnage créé : " + this.player.getName());
				menu.printCenteredLine("Type : " + this.player.getType());
				menu.printCenteredLine("Santé : " + this.player.getHealthPoints());
				menu.printCenteredLine("Attaque : " + this.player.getAttackPower());
				menu.printCenteredLine("Arme : " + this.player.getOffensiveEquipment().getName());
				menu.printCenteredLine("Défense : " + this.player.getDefensiveEquipment().getName());
			} catch (SQLException e) {
				System.err.println("Erreur lors de la sauvegarde : " + e.getMessage());
			}
		}
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
			// Return to main menu - now handled by the main menu loop
			return;
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
		}
		if (choice.equals("2")) {
			this.player = new Wizard("Magicien", characterName);
			System.out.println(player);
		}
	}

	/**
	 * Loads a character from the database with retry functionality.
	 */
	public void loadCharacter() {
		Menu menu = new Menu();
		SimpleDatabaseManager dbManager = new SimpleDatabaseManager();
		boolean keepTrying = true;
		
		while (keepTrying) {
			try {
				// Show available characters from database
				List<String> characters = dbManager.listCharacters();
				
				menu.beforeLine();
				menu.printCenteredLine("Personnages disponibles :");
				
				if (characters.isEmpty()) {
					menu.printCenteredLine("Aucun personnage trouvé dans la base de données.");
				} else {
					for (String characterInfo : characters) {
						menu.printCenteredLine(characterInfo);
					}
				}
				menu.afterLine();
				
				String characterIdStr = menu.askPlayerString(
					"Entrez l'ID du personnage à charger", "(ou 'm' pour revenir au menu) :");
				
				// Check if user wants to go back to menu
				if (characterIdStr != null && characterIdStr.equalsIgnoreCase("m")) {
					keepTrying = false;
					continue;
				}
				
				int characterId = Integer.parseInt(characterIdStr);
				
				Character loadedCharacter = dbManager.loadCharacter(characterId);
				
				if (loadedCharacter != null) {
					this.player = loadedCharacter;
					menu.printCenteredLine("Personnage chargé avec succès !");
					System.out.println(player);
					keepTrying = false; // Success, exit loop
				} else {
					menu.printCenteredLine("Aucun personnage trouvé avec cet ID.");
				}
			} catch (NumberFormatException e) {
				menu.printCenteredLine("ID invalide. Veuillez entrer un nombre.");
			} catch (SQLException e) {
				menu.printCenteredLine("Erreur lors du chargement du personnage : " + e.getMessage());
			}
		}
	}

	/**
	 * Handles a single turn of the game, including rolling the dice and moving the player.
	 * @param board The game board.
	 * @param dice The dice used to determine movement.
	 */
	public void playTurn(Board board, Dice dice) {
		Menu menu = new Menu();
		String userInput = menu.askPlayerString("Appuyez sur 'Entrée' pour lancer le dé ou q pour quitter");
		
		// Check if player wants to quit
		if (userInput != null && userInput.equalsIgnoreCase("q")) {
			gameOver = true;
			System.out.println("Partie terminée par l'utilisateur.");
			return;
		}
		
		int diceResult = dice.roll();
		try {
			int newPosition = board.getCurrentPosition() + diceResult;
			board.setCurrentPosition(newPosition);
			System.out.println(board.toString());
			Cell currentCell = board.getCurrentCell();
			String interactionResult = currentCell.interact(player);
			System.out.println(interactionResult);
			
			// Handle flee logic
			if (interactionResult != null && interactionResult.startsWith("FUITE:")) {
				int fleeSteps = Integer.parseInt(interactionResult.split(":")[1]);
				int fleeNewPosition = board.getCurrentPosition() - fleeSteps;
				if (fleeNewPosition < 1) fleeNewPosition = 1;
				board.setCurrentPosition(fleeNewPosition);
				System.out.println("Vous avez reculé de " + fleeSteps + " cases. Vous êtes maintenant à la position " + fleeNewPosition + ".");
				return; // End this turn, go to next turn
			}
			
			// Check if player is still alive
			if (player.getHealthPoints() <= 0) {
				gameOver = true;
				System.out.println("Game Over. Vous avez été vaincu.");
			}

			// Check if player reached the end AND boss is defeated
			if (board.getCurrentPosition() >= board.getMaxPosition()) {
				if (GameState.getInstance().isBossDefeated()) {
					gameOver = true;
				} else {
					System.out.println("Vous avez atteint la fin, mais le boss n'est pas vaincu !");
					System.out.println("Vous devez vaincre le boss pour gagner la partie.");
				}
			}
		} catch (OutOfBoardException e) {
			System.out.println("Erreur : " + e.getMessage());
		}
	}

	/**
	 * Ends the game and displays a congratulatory message.
	 */
	public void endGame() {
		System.out.println("""
				
				[0;97;1;40m█[0;97;1;47m▀[0;97;1;40m▀▀▀▀█[0;37;40m▄[0;90;1;40m▄[0;37;40m [0;97;1;40m█▀▀▀▀▀█[0;37;40m   [0;97;1;40m▄▄▄▄▄▄▄▄[0;37;40m▄▄[0;90;1;40m▄[0;37;40m    [0;97;1;40m▄[0;97;1;47m▀[0;97;1;40m▀▀▀▀▀[0;97;1;47m▄▄[0;90;1;47m▀[0;37;40m▄[0;90;1;40m▄[0;37;40m  [0;97;1;40m▄[0;97;1;47m▀[0;97;1;40m▀▀▀▀▀▀▀▀▀▀▀▀▀[0;97;1;47m▄▄[0;90;1;47m▀[0;37;40m▄[0;90;1;40m▄[0;37;40m     [0;97;1;40m▄▄[0;97;1;47m▀▀▀▀▀▄▄[0;90;1;47m▀[0;37;40m▄[0;90;1;40m▄[0;37;40m    [0;97;1;40m█[0;97;1;47m▀▀▀▀▀▀▀▀▀▀▄▄[0;90;1;47m▀[0;37;40m▄[0;90;1;40m▄[0;37;40m    [0;97;1;40m▄▄▄▄▄▄▄▄[0;37;40m▄[0;90;1;40m▄[0;97;1;40m▄▄▄▄▄▄▄▄[0;37;40m▄▄[0;90;1;40m▄[0;37;40m    [0m
				[0;97;1;40m█[0;37;40m█ [0;93;1;40m▄■[0;37;40m [0;97;1;40m█[0;37;40m█▓[0;90;1;40m▓[0;97;1;40m█[0;37;40m     [0;97;1;40m█[0;90;1;47m▀[0;90;1;40m▄[0;37;40m [0;97;1;40m█[0;37;40m█▀▀▀▀▀[0;97;1;40m█[0;37;40m██[0;90;1;40m█[0;37;40m  [0;97;1;40m▄[0;97;1;47m▀[0;37;40m▀        [0;97;1;40m█[0;37;40m█[0;90;1;47m▀[0;90;1;40m▄[0;97;1;40m█[0;37;40m█ [0;93;1;40m▄▀▀■·[0;37;40m  [0;93;1;40m·■▄[0;37;40m     [0;97;1;40m█[0;37;40m█[0;90;1;47m▀[0;90;1;40m▄[0;37;40m  [0;97;1;40m▄[0;97;1;47m▀[0;37;40m▀   [0;93;1;40m■[0;37;40m [0;93;1;40m▄▄[0;37;40m [0;97;1;40m▀[0;97;1;47m▄[0;37;40m█[0;90;1;47m▀[0;90;1;40m▄[0;37;40m  [0;97;1;40m█[0;37;40m█      [0;93;1;40m■[0;37;40m [0;93;1;40m▄▄[0;37;40m [0;97;1;40m▀[0;97;1;47m▄[0;37;40m█[0;90;1;47m▀[0;90;1;40m▄[0;37;40m  [0;97;1;40m█[0;37;40m█▀▀▀▀▀[0;97;1;40m█[0;37;40m█[0;90;1;40m█[0;97;1;40m█[0;37;40m█▀▀▀▀▀[0;97;1;40m█[0;37;40m██[0;90;1;40m█[0;37;40m    [0m
				[0;97;1;40m█[0;37;40m█[0;93;1;40m▐[0;37;40m   [0;97;1;40m█[0;37;40m█▒[0;90;1;40m▓[0;97;1;40m█[0;37;40m [0;93;1;40m▀▄[0;37;40m  [0;97;1;40m█[0;37;40m██[0;90;1;40m█[0;97;1;40m█[0;37;40m█ [0;93;1;40m▄■[0;37;40m  [0;97;1;40m█[0;37;40m██[0;90;1;40m█[0;37;40m [0;97;1;40m█[0;37;40m█  [0;93;1;40m▄■·[0;37;40m     [0;97;1;40m█[0;97;1;47m [0;37;40m█[0;90;1;40m█[0;97;1;40m█[0;37;40m█[0;93;1;40m·[0;37;40m          [0;93;1;40m▀■·[0;37;40m  [0;97;1;40m█[0;97;1;47m [0;37;40m█[0;90;1;40m█[0;37;40m [0;97;1;40m█[0;37;40m█         [0;93;1;40m▀▄[0;37;40m [0;97;1;40m█[0;37;40m█[0;90;1;47m▀[0;90;1;40m▄[0;37;40m [0;97;1;40m▀[0;97;1;47m▄[0;37;40m▄         [0;93;1;40m▀▄[0;37;40m [0;97;1;40m█[0;37;40m█[0;90;1;47m▀[0;90;1;40m▄[0;37;40m [0;97;1;40m█[0;37;40m█ [0;93;1;40m▄■[0;37;40m  [0;97;1;40m█[0;37;40m█[0;90;1;40m█[0;97;1;40m█[0;37;40m█ [0;93;1;40m▄■[0;37;40m  [0;97;1;40m█[0;37;40m██[0;90;1;40m█[0;37;40m    [0m
				[0;97;1;40m█[0;37;40m█[0;93;1;40m.[0;37;40m   [0;97;1;40m█[0;37;40m██[0;90;1;40m█[0;97;1;40m█[0;37;40m   [0;93;1;40m▌[0;37;40m [0;97;1;40m█[0;37;40m██[0;90;1;40m█[0;97;1;40m█[0;97;1;47m [0;93;1;40m▐[0;37;40m    [0;97;1;40m█[0;37;40m██[0;90;1;40m█[0;97;1;40m▐[0;97;1;47m▌[0;37;40m▌[0;93;1;40m▄▀[0;37;40m  [0;97;1;40m▄[0;97;1;47m▀▀[0;97;1;40m▄▄[0;97;1;47m▀[0;37;40m█[0;90;1;47m▄[0;90;1;40m▀[0;37;40m [0;97;1;40m▀[0;97;1;47m▄▄[0;97;1;40m▀▀█[0;97;1;47m▌[0;37;40m▌ [0;31;40m░[0;37;40m  [0;97;1;40m▄[0;97;1;47m▀▀▄[0;97;1;40m▄[0;97;1;47m▀[0;37;40m█[0;90;1;47m▄[0;90;1;40m▀[0;37;40m [0;97;1;40m▐[0;97;1;47m▌[0;37;40m▌    [0;97;1;40m▄[0;97;1;47m▀[0;97;1;40m▄[0;37;40m▄   [0;93;1;40m▌[0;97;1;40m▐[0;97;1;47m▌[0;37;40m█[0;90;1;47m▐[0;90;1;40m▌[0;37;40m [0;97;1;40m▐[0;97;1;47m▌[0;37;40m▌   [0;97;1;40m█[0;97;1;47m▀[0;97;1;40m▄[0;37;40m▄   [0;93;1;40m▌[0;97;1;40m▐[0;97;1;47m▌[0;37;40m█[0;90;1;47m▐[0;90;1;40m▌[0;97;1;40m█[0;97;1;47m [0;93;1;40m▐[0;37;40m    [0;97;1;40m█[0;37;40m█[0;90;1;40m█[0;97;1;40m█[0;97;1;47m [0;93;1;40m▐[0;37;40m    [0;97;1;40m█[0;37;40m██[0;90;1;40m█[0;37;40m    [0m
				[0;97;1;40m█[0;37;40m█    [0;97;1;40m█[0;37;40m██[0;90;1;40m█[0;97;1;40m█[0;37;40m   [0;93;1;40m.[0;37;40m [0;97;1;40m█[0;37;40m██[0;90;1;40m█[0;97;1;40m█[0;37;40m█  [0;91;1;40m▄▄[0;37;40m [0;97;1;40m█[0;37;40m██[0;90;1;40m█[0;97;1;40m█[0;37;40m█ [0;93;1;40m▌[0;37;40m  [0;97;1;40m█[0;37;40m██[0;90;1;47m▄[0;90;1;40m▀▀[0;37;40m          [0;97;1;40m█[0;37;40m█ [0;31;40m▒▓▒[0;37;40m [0;97;1;40m█[0;37;40m██[0;90;1;40m█[0;37;40m      [0;97;1;40m█[0;37;40m█    [0;97;1;40m█[0;37;40m██[0;90;1;40m█[0;97;1;40m█[0;37;40m     [0;97;1;40m█[0;37;40m██[0;90;1;40m█[0;37;40m  [0;97;1;40m█[0;37;40m█   [0;97;1;40m█[0;97;1;47m▄[0;97;1;40m▄▀[0;37;40m   [0;97;1;40m▄█[0;37;40m██[0;90;1;40m█[0;37;40m [0;97;1;40m█[0;37;40m█ [0;31;40m▄[0;91;1;40m▄▄[0;31;40m▄[0;97;1;40m█[0;37;40m█[0;90;1;40m█[0;97;1;40m█[0;37;40m█ [0;31;40m▄[0;91;1;40m▄▄[0;37;40m [0;97;1;40m█[0;37;40m██[0;90;1;40m█[0;37;40m    [0m
				[0;97;1;40m█[0;37;40m█ [0;31;40m▒░[0;37;40m [0;97;1;40m█[0;37;40m██[0;90;1;40m█[0;97;1;40m█[0;37;40m [0;31;40m█[0;91;1;41m░[0;31;40m█[0;37;40m [0;97;1;40m█[0;37;40m██[0;90;1;40m█[0;97;1;40m█[0;37;40m█ [0;91;1;40m▓[0;91;1;41m▓▒[0;37;40m [0;97;1;40m█[0;37;40m██[0;90;1;40m█[0;97;1;40m█[0;37;40m█    [0;97;1;40m█[0;37;40m█[0;90;1;40m█[0;37;40m             [0;97;1;40m█[0;37;40m█[0;31;40m██[0;91;1;41m░[0;91;1;40m▄▄[0;97;1;40m█[0;37;40m██[0;90;1;40m█[0;37;40m      [0;97;1;40m█[0;37;40m█    [0;97;1;40m█[0;37;40m██[0;90;1;40m█[0;97;1;40m█[0;37;40m     [0;97;1;40m█[0;37;40m██[0;90;1;40m█[0;37;40m  [0;97;1;40m█[0;37;40m█ [0;31;40m▓▒░[0;37;40m [0;31;40m░░[0;37;40m [0;97;1;40m▄█[0;97;1;47m▀[0;37;40m█[0;90;1;47m▄[0;37;40m   [0;97;1;40m█[0;37;40m█[0;31;40m█[0;91;1;40m▓[0;91;1;41m▓▒[0;31;40m█[0;97;1;40m█[0;97;1;47m▄▄[0;97;1;40m█[0;37;40m█[0;31;40m█[0;91;1;40m▓[0;91;1;41m▓▒░[0;97;1;40m█[0;37;40m██[0;90;1;40m█[0;37;40m    [0m
				[0;97;1;47m▐▌[0;37;40m [0;31;40m▓█[0;91;1;40m▄[0;97;1;40m▐[0;97;1;47m▌ [0;90;1;40m█[0;97;1;40m█[0;31;40m▐[0;91;1;40m█[0;91;1;41m▒▒[0;37;40m [0;97;1;40m█[0;97;1;47m [0;37;40m█[0;90;1;40m█[0;97;1;40m█[0;37;40m█ [0;91;1;41m▒░[0;31;40m█[0;37;40m [0;97;1;40m█[0;37;40m██[0;90;1;40m█[0;97;1;40m█[0;37;40m█ [0;91;1;40m▄██[0;97;1;40m▐[0;97;1;47m▌[0;37;40m█[0;90;1;40m█[0;37;40m            [0;97;1;40m█[0;37;40m█[0;91;1;41m▒▓[0;91;1;40m███[0;97;1;40m█[0;37;40m██[0;90;1;40m█[0;37;40m      [0;97;1;40m█[0;37;40m█  [0;31;40m▄[0;91;1;40m▄[0;97;1;40m█[0;37;40m█[0;97;1;47m [0;90;1;40m█[0;97;1;40m█[0;31;40m▐[0;91;1;40m██▄[0;37;40m [0;97;1;40m█[0;97;1;47m [0;37;40m█[0;90;1;40m█[0;37;40m [0;97;1;40m▐[0;97;1;47m▌[0;37;40m▌[0;31;40m█[0;91;1;40m▄▌[0;97;1;40m█[0;97;1;43m▀[0;97;1;40m▄[0;31;40m▒[0;91;1;41m░[0;91;1;40m█[0;97;1;43m▀[0;97;1;40m█[0;37;40m█[0;90;1;47m▀[0;90;1;40m▄[0;37;40m   [0;97;1;40m▀[0;97;1;47m▄[0;97;1;40m▄[0;37;41m▄[0;31;40m██▓█▓▒░▓[0;91;1;41m▒░[0;31;40m█[0;91;1;41m░[0;97;1;40m█[0;37;40m██[0;90;1;40m█[0;37;40m    [0m
				[0;37;40m [0;97;1;47m▀▄[0;37;40m▄[0;91;1;41m▒▓▒[0;97;1;40m▀[0;97;1;47m▄▀[0;37;40m▀[0;31;40m█[0;91;1;40m█▓[0;91;1;41m▒[0;97;1;40m▐[0;97;1;47m▌[0;37;40m█[0;90;1;47m▐[0;90;1;40m▌[0;97;1;40m█[0;37;40m█ [0;31;40m██▒[0;37;40m [0;97;1;40m█[0;37;40m██[0;90;1;40m█[0;97;1;40m█[0;37;40m█ [0;91;1;40m░[0;91;1;41m▒▓[0;31;40m▌[0;97;1;40m▀[0;97;1;47m▄▄[0;97;1;40m▀▀[0;97;1;47m▄[0;37;40m█[0;90;1;47m▀[0;90;1;40m▄[0;37;40m      [0;97;1;40m█[0;37;40m█[0;91;1;41m░[0;91;1;40m░[0;91;1;41m▒▓[0;91;1;40m█[0;97;1;40m█[0;37;40m██[0;90;1;40m█[0;37;40m      [0;97;1;40m▐[0;97;1;47m▌[0;37;40m▌[0;91;1;41m░▒▓▒[0;97;1;40m▀[0;97;1;47m▄▀[0;37;40m▀[0;31;40m█[0;91;1;40m█▓[0;91;1;41m▒[0;97;1;40m▐[0;97;1;47m▌[0;37;40m█[0;90;1;47m▐[0;90;1;40m▌[0;37;40m [0;97;1;40m▐[0;97;1;47m▌[0;37;40m▌[0;91;1;41m░▓▒[0;97;1;40m█[0;37;40m█[0;90;1;40m█[0;97;1;40m█[0;91;1;41m▒[0;91;1;40m█▓[0;91;1;41m▒[0;97;1;40m█[0;37;40m█[0;90;1;47m▐[0;90;1;40m▌[0;37;40m     [0;97;1;40m▀▀▀▀▀▀█[0;37;40m█[0;31;40m███▒▓[0;97;1;40m█[0;37;40m██[0;90;1;40m█[0;37;40m    [0m
				[0;37;40m  ▀[0;97;1;40m█[0;37;40m█[0;91;1;41m▒░[0;31;40m▄▄[0;37;40m [0;31;40m▓[0;91;1;41m░▒░[0;37;40m [0;97;1;40m█[0;37;40m██[0;90;1;40m█[0;37;40m [0;97;1;40m█[0;37;40m█ [0;31;40m░░░[0;37;40m [0;97;1;40m█[0;37;40m██[0;90;1;40m█[0;97;1;40m▐[0;97;1;47m▌[0;37;40m▌ [0;91;1;40m░▒[0;91;1;41m▒░[0;37;40m    [0;97;1;40m▀[0;97;1;47m▄[0;37;40m█[0;90;1;47m▀[0;90;1;40m▄[0;37;40m     [0;97;1;40m█[0;37;40m█[0;91;1;41m▒░▒[0;91;1;40m▒[0;91;1;41m▒[0;97;1;40m█[0;37;40m██[0;90;1;40m█[0;37;40m       [0;97;1;40m█[0;37;40m█[0;31;40m█[0;91;1;41m▓▒░[0;31;40m▄▄[0;37;40m [0;31;40m▓[0;91;1;41m░▒░[0;37;40m [0;97;1;40m█[0;37;40m██[0;90;1;40m█[0;37;40m  [0;97;1;40m█[0;37;40m█[0;91;1;41m░▓▒░[0;97;1;40m█[0;37;40m█[0;90;1;40m█[0;97;1;40m█[0;91;1;41m░▒░░[0;97;1;40m█[0;37;40m██[0;90;1;40m█[0;37;40m [0;97;1;40m▄▄▄▄▄▄▄▄[0;37;40m▄[0;97;1;40m▄[0;97;1;47m▀[0;90;1;47m▄[0;31;40m█▓▒▓[0;97;1;41m▄[0;97;1;47m▀[0;37;40m██[0;90;1;40m█[0;37;40m    [0m
				[0;37;40m    [0;97;1;40m▀[0;97;1;47m▄[0;37;40m▄[0;31;40m▒░[0;37;40m [0;31;40m▒░▀[0;97;1;40m▄[0;97;1;47m▀ [0;90;1;47m▄[0;90;1;40m▀[0;37;40m  [0;97;1;40m█[0;97;1;47m [0;37;40m [0;31;40m░░░[0;37;40m [0;97;1;40m█[0;37;40m██[0;90;1;40m█[0;37;40m [0;97;1;40m▀[0;97;1;47m▄[0;37;40m▄ [0;31;40m▀[0;91;1;41m░[0;31;40m██▓▒░[0;37;40m [0;97;1;40m█[0;97;1;47m  [0;90;1;40m█[0;37;40m     [0;97;1;40m█[0;37;40m█[0;91;1;41m░░░▒░[0;97;1;40m█[0;97;1;47m  [0;90;1;40m█[0;37;40m        [0;97;1;40m▀[0;97;1;47m▄[0;37;40m▄[0;31;40m█▓▒░[0;37;40m [0;31;40m▒░▓▀[0;97;1;40m▄[0;97;1;47m▀ [0;90;1;47m▄[0;90;1;40m▀[0;37;40m [0;97;1;40m▄[0;97;1;47m▀[0;37;40m▀[0;31;40m▄[0;91;1;41m░[0;31;40m█▓[0;97;1;40m█[0;37;40m█[0;90;1;40m█[0;97;1;40m█[0;31;40m░▓▀[0;97;1;40m▄[0;97;1;47m▀ [0;90;1;47m▄[0;90;1;40m▀[0;37;40m [0;97;1;40m█[0;97;1;47m [0;91;1;41m▓▒▒░░[0;97;1;41m▀▀[0;37;40m▀[0;90;1;40m▀[0;31;40m██▒▓[0;97;1;41m▄[0;97;1;47m▀[0;37;40m█[0;90;1;47m▄[0;90;1;40m▀[0;37;40m     [0m
				[0;37;40m      [0;97;1;40m▀▀[0;97;1;47m▄[0;97;1;40m▄▄[0;97;1;47m▀▀[0;90;1;47m▄[0;37;40m▀[0;90;1;40m▀[0;37;40m    [0;97;1;40m█[0;97;1;47m▄[0;97;1;40m▄▄▄▄▄█[0;37;40m██[0;90;1;40m█[0;37;40m   [0;97;1;40m▀▀[0;97;1;47m▄[0;97;1;40m▄▄▄▄▄[0;97;1;47m▀▀[0;90;1;47m▄[0;37;40m▀[0;90;1;40m▀[0;37;40m      [0;97;1;40m█[0;97;1;47m▄[0;97;1;40m▄▄▄▄▄█[0;37;40m██[0;90;1;40m█[0;37;40m          [0;97;1;40m▀▀[0;97;1;47m▄[0;97;1;40m▄▄▄▄▄[0;97;1;47m▀▀[0;90;1;47m▄[0;37;40m▀[0;90;1;40m▀[0;37;40m   [0;97;1;40m█[0;97;1;47m▄[0;97;1;40m▄▄▄▄▄▄█[0;37;40m█[0;97;1;40m█▄[0;97;1;47m▀▀[0;90;1;47m▄[0;37;40m▀[0;90;1;40m▀[0;37;40m   [0;97;1;40m█[0;97;1;47m▄[0;97;1;41m▄▄▄▄▄▄▄▄▄▄▄[0;97;1;47m▀▀[0;37;40m█[0;90;1;47m▄[0;90;1;40m▀[0;37;40m       [0m
				""");
	}
	/**
	 * Starts the main game loop. The player moves on the board by rolling the dice
	 * until they reach the last cell (64). Displays the player's position each turn.
	 */
	public void start() {
		this.board = new Board();
		SixSidedDice dice = new SixSidedDice();
		Scanner scanner = new Scanner(System.in);
		
		while (!gameOver) {
			playTurn(board, dice);
			
			// End game only if at max position AND boss is defeated
			if (board.getCurrentPosition() >= board.getMaxPosition() && GameState.getInstance().isBossDefeated()) {
				gameOver = true;
			}
		}

		if (player.getHealthPoints() > 0 && board.getCurrentPosition() >= board.getMaxPosition() && GameState.getInstance().isBossDefeated()) {
			endGame();
		}
		scanner.close();
	}

	@Override
	public String toString() {
		return super.toString();
	}
}
