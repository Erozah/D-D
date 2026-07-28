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
import fr.campus.d_and_d.items.Weapon;
import fr.campus.d_and_d.items.Shield;
import fr.campus.d_and_d.db.SimpleDatabaseManager;
import fr.campus.d_and_d.db.LinkDB;
import java.util.Scanner;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * The Game class handles the core game logic, including character creation,
 * player movement, and game state management.
 */
public class Game {
	private Character player;
	public Board board;
	private boolean gameOver = false;
	private final Menu menu = new Menu();
	boolean running = true;

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
		while(running) {
			menu.printBlock("BIENVENUE - Menu Principal");
			String choice = menu.askPlayerString(
					"1. Démarrer la partie",
					"2. Nouveau personnage",
					"3. Charger un personnage",
					"4. Voir les statistiques",
					"5. Quitter");
			switch (choice) {
				case "1" -> handleStartGame();
				case "2" -> handleCreateCharacter();
				case "3" -> loadCharacter();
				case "4" -> handleShowStats();
				case "5" -> handleExit();
			}
		}
	}
	/**
	 * Handles the start game action from the main menu.
	 * Checks if a player character exists before starting the game.
	 * If no character exists, displays an error message.
	 */
	private void handleStartGame() {
		if (this.player != null) {
			start();
		} else {
			menu.printBlock("Pas encore de personnage ! Veuillez en créer ou charger un.");
		}
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
			// Update character in database before ending game
			updateCharacterInDatabase();
			endGame();
		}
		scanner.close();
	}
	/**
	 * Handles the character creation action from the main menu.
	 * Creates a new character and automatically saves it to the database.
	 */
	private void handleCreateCharacter() {
		// Nouveau personnage
		createCharacter();
		// Auto-save after creation
		autoSaveCharacter();
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
					menu.printBlock("Personnage chargé avec succès !");
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
	 * Handles the show statistics action from the main menu.
	 * Displays the current character's statistics if a character exists.
	 * Otherwise, displays an error message.
	 */
	private void handleShowStats() {
		// Voir les statistiques
		if (this.player != null) {
			showCharacterStats();
		} else {
			menu.printBlock("Pas encore de personnage ! Veuillez en créer ou charger un.");
		}
	}

	/**
	 * Handles the exit action from the main menu.
	 * Closes the scanner and sets the running flag to false to exit the game loop.
	 */
	private void handleExit() {
		gameOver = true;
		if (!GameState.getInstance().isBossDefeated() && player.getHealthPoints() > 0)
			menu.printBlock("Partie terminée par l'utilisateur.");
		menu.closeScanner();
		running = false;
	}

	/**
	 * Deletes the character from the database when they die
	 */
	private void deleteCharacterFromDatabase() {
		if (this.player != null && this.player.getDatabaseId() != -1) {
			try {
				LinkDB linkDB = new LinkDB();
				linkDB.connect();
				
				try (Connection conn = linkDB.getConnection()) {
					int characterId = this.player.getDatabaseId();
					
					// Delete equipment first (due to foreign key constraint)
					try (PreparedStatement stmt = conn.prepareStatement(
						"DELETE FROM equipment WHERE owner_id = ?")) {
						stmt.setInt(1, characterId);
						stmt.executeUpdate();
					}
					
					// Then delete character
					try (PreparedStatement stmt = conn.prepareStatement(
						"DELETE FROM characters WHERE id = ?")) {
						stmt.setInt(1, characterId);
						stmt.executeUpdate();
					}
					
					System.out.println("✅ Personnage supprimé de la base de données.");
				}
				linkDB.close();
			} catch (SQLException e) {
				System.err.println("Erreur lors de la suppression du personnage : " + e.getMessage());
			}
		}
	}
	
	/**
	 * Updates the character in the database when they finish the game alive
	 */
	private void updateCharacterInDatabase() {
		if (this.player != null && this.player.getDatabaseId() != -1) {
			try {
				LinkDB linkDB = new LinkDB();
				linkDB.connect();
				
				try (Connection conn = linkDB.getConnection()) {
					conn.setAutoCommit(false);
					
					int characterId = this.player.getDatabaseId();
					
					// Update character info (save base attack power, not total)
					try (PreparedStatement stmt = conn.prepareStatement(
						"UPDATE characters SET health_points = ?, attack_power = ? WHERE id = ?")) {
						stmt.setInt(1, player.getHealthPoints());
						stmt.setInt(2, player.getBaseAttackPower()); // Save base attack power
						stmt.setInt(3, characterId);
						stmt.executeUpdate();
					}
					
					// Delete old equipment
					try (PreparedStatement stmt = conn.prepareStatement(
						"DELETE FROM equipment WHERE owner_id = ?")) {
						stmt.setInt(1, characterId);
						stmt.executeUpdate();
					}
					
					// Save new offensive equipment
					String equipType = player.getOffensiveEquipment() instanceof Weapon ? "WEAPON" : "SPELL";
					try (PreparedStatement stmt = conn.prepareStatement(
						"INSERT INTO equipment (equipment_type, name, power, owner_id) VALUES (?, ?, ?, ?)")) {
						stmt.setString(1, equipType);
						stmt.setString(2, player.getOffensiveEquipment().getName());
						stmt.setInt(3, player.getOffensiveEquipment().getAttackPower());
						stmt.setInt(4, characterId);
						stmt.executeUpdate();
					}
					
					// Save new defensive equipment
					equipType = player.getDefensiveEquipment() instanceof Shield ? "SHIELD" : "POTION";
					try (PreparedStatement stmt = conn.prepareStatement(
						"INSERT INTO equipment (equipment_type, name, power, owner_id) VALUES (?, ?, ?, ?)")) {
						stmt.setString(1, equipType);
						stmt.setString(2, player.getDefensiveEquipment().getName());
						stmt.setInt(3, player.getDefensiveEquipment().getDefensePoints());
						stmt.setInt(4, characterId);
						stmt.executeUpdate();
					}
					
					conn.commit();
					System.out.println("✅ Personnage mis à jour dans la base de données.");
				}
				linkDB.close();
			} catch (SQLException e) {
				System.err.println("Erreur lors de la mise à jour du personnage : " + e.getMessage());
			}
		}
	}
	
	/**
	 * Automatically saves the character to the database after creation.
	 * This method is called after a new character is created to ensure it is persisted.
	 * Displays a confirmation message with character details upon successful save.
	 * 
	 * @throws SQLException If there is an error saving the character to the database
	 */
	private void autoSaveCharacter() {
		if (this.player != null) {
			try {
				SimpleDatabaseManager dbManager = new SimpleDatabaseManager();
				dbManager.saveCharacter(this.player);
				Menu menu = new Menu();
				menu.beforeLine();
				menu.printCenteredLine("Personnage sauvegardé avec succès !");
				
				// Display character info with equipment names
				menu.printCenteredLine("Personnage créé : " + this.player.getName());
				menu.printCenteredLine("Type : " + this.player.getType());
				menu.printCenteredLine("Santé : " + this.player.getHealthPoints());
				menu.printCenteredLine("Attaque : " + this.player.getAttackPower());
				menu.printCenteredLine("Arme : " + this.player.getOffensiveEquipment().getName());
				menu.printCenteredLine("Défense : " + this.player.getDefensiveEquipment().getName());
				menu.afterLine();
			} catch (SQLException e) {
				System.err.println("Erreur lors de la sauvegarde : " + e.getMessage());
			}
		}
	}
	/**
	 * Displays the current character's statistics in a formatted block.
	 * Shows the character's type, name, health points, attack power, and equipment.
	 * After displaying the statistics, provides options to return to the main menu or quit the game.
	 * 
	 * @throws NullPointerException If the player character is null
	 */
	public void showCharacterStats() {
		if (player == null) {
			menu.printCenteredLine("Aucun personnage créé. Veuillez d'abord créer un personnage.");
		}
		menu.printBlock("Statistiques de votre personnage");
		System.out.println(player);

		String choice = menu.askPlayerString("1. Retour au menu principal", "2. Quitter");
		switch (choice) {
			case "1" -> mainMenu();
			case "2" -> handleExit();
		}

	}

	/**
	 * Handles a single turn of the game, including rolling the dice and moving the player.
	 * The player can choose to quit by entering 'q'. After rolling the dice, the player's position
	 * is updated and the content of the current cell is processed.
	 * 
	 * @param board The game board where the player is moving
	 * @param dice The dice used to determine movement distance
	 */
	public void playTurn(Board board, Dice dice) {
		String userInput = menu.askPlayerString("Appuyez sur 'Entrée' pour lancer le dé ou q pour quitter");
		
		// Check if player wants to quit
		if (userInput != null && userInput.equalsIgnoreCase("q")) {
			handleExit();
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
			isDead();

			// Check if player reached the end AND boss is defeated
			isWin();

		} catch (OutOfBoardException e) {
			System.out.println("Erreur : " + e.getMessage());
		}
	}

	private void isDead() {
		if (player.getHealthPoints() <= 0) {
			System.out.println("""
						
						[0;37;40m█[0;97;1;47m▄▄[0;37;40m  [0;97;1;47m▄▄[0;37;40m█  ▄▀▀▄  █[0;97;1;47m▄▄[0;37;40m  [0;97;1;47m▄▄[0;37;40m█  [0;97;1;40m▄[0;97;1;47m▄▄▄[0;37;40m▄        ▄[0;97;1;47m▄▄▄[0;37;40m █[0;97;1;47m▄▄[0;37;40m▀▀[0;97;1;47m▄▄[0;37;40m  ▄[0;97;1;47m▄▄▄[0;37;40m  [0;97;1;40m▄[0;97;1;47m▄▄▄[0;37;40m▄       █[0;97;1;47m▄[0;37;40m▀█▄ ▄█▄   ▄▀▀▄  █[0;97;1;47m▄▄[0;37;40m▀▀▄  █[0;97;1;47m▄▄[0;37;40m▀▀[0;97;1;47m▄▄[0;37;40m      ▄[0;97;1;47m▄▄[0;37;40m▌[0m
						[0;37;40m [0;97;1;41m██[0;37;40m  [0;97;1;41m██[0;37;40m  [0;97;1;47m▄[0;97;1;41m█[0;37;40m  [0;97;1;41m█[0;97;1;47m▄[0;37;40m  [0;97;1;41m██[0;37;40m  [0;97;1;41m██[0;37;40m  [0;97;1;47m█[0;97;1;41m█[0;37;40m   [0;97;1;41m▒[0;37;40m█      [0;97;1;47m▄[0;97;1;41m█[0;37;40m [0;97;1;47m▀▀[0;37;40m   [0;97;1;41m█[0;97;1;47m▄[0;37;40m    [0;97;1;47m▄[0;97;1;41m█[0;37;40m [0;97;1;47m▀▀[0;37;40m [0;97;1;47m█[0;97;1;41m█[0;37;40m   [0;97;1;41m▒[0;37;40m█      ▓[0;97;1;41m█[0;37;40m  [0;97;1;41m█[0;97;1;47m▄[0;37;40m▌ [0;97;1;41m█[0;97;1;47m▄[0;37;40m [0;97;1;47m▄[0;97;1;41m█[0;37;40m  [0;97;1;41m█[0;97;1;47m▄[0;37;40m  [0;97;1;41m██[0;37;40m  [0;97;1;41m█[0;97;1;47m▄[0;37;40m   [0;97;1;41m█[0;97;1;47m▄[0;37;40m          [0;97;1;40m█[0;97;1;41m█[0;37;40m [0m
						[0;37;40m [0;97;1;41m██[0;37;40m  [0;97;1;41m██[0;37;40m  [0;97;1;41m██[0;37;40m  [0;97;1;41m██[0;37;40m  [0;97;1;41m██[0;37;40m  [0;97;1;41m██[0;37;40m  ▀[0;97;1;47m▀[0;97;1;40m▄▄▄[0;37;40m        [0;97;1;41m██[0;37;40m      [0;97;1;41m██[0;37;40m    [0;97;1;41m██[0;37;40m    ▀[0;97;1;47m▀[0;97;1;40m▄▄▄[0;37;40m        [0;97;1;41m██[0;37;40m  [0;97;1;41m██[0;37;40m  [0;97;1;41m██[0;37;40m [0;97;1;41m██[0;37;40m  [0;97;1;41m██[0;37;40m  [0;97;1;41m██[0;37;40m  [0;97;1;41m█[0;97;1;47m▀[0;37;40m   [0;97;1;41m██[0;37;40m          [0;97;1;41m█[0;97;1;40m█[0;37;40m [0m
						[0;37;40m [0;97;1;41m██[0;37;40m  [0;97;1;41m▒▒[0;37;40m  [0;97;1;41m██[0;37;40m  [0;97;1;41m██[0;37;40m  [0;97;1;41m██[0;37;40m  [0;97;1;41m▒▒[0;37;40m    ▀[0;97;1;40m▀▀[0;97;1;41m▒[0;37;40m▄      [0;97;1;41m▓▓[0;37;40m▄▄    [0;97;1;41m▒▒[0;37;40m    [0;97;1;41m▓▓[0;37;40m▄▄    ▀[0;97;1;40m▀▀[0;97;1;41m▒[0;37;40m▄      [0;97;1;41m██[0;37;40m  [0;97;1;41m█[0;97;1;47m█[0;37;40m  [0;97;1;41m█[0;97;1;47m█[0;37;40m [0;97;1;41m██[0;37;40m  [0;97;1;41m██[0;37;40m  [0;97;1;41m██[0;37;40m▄▄▀    [0;97;1;41m▒▒[0;37;40m         ▄[0;97;1;41m██[0;37;40m▌[0m
						[0;37;40m [0;91;1;41m▓▓[0;37;40m  [0;91;1;41m▓▓[0;37;40m  [0;91;1;41m▓▓[0;37;40m  [0;91;1;41m▓▓[0;37;40m  [0;91;1;41m▓▓[0;37;40m  [0;91;1;41m▓▓[0;37;40m  [0;91;1;47m▄▄[0;37;40m   [0;91;1;41m▓▓[0;37;40m      [0;91;1;41m▓▓[0;37;40m      [0;91;1;41m▓▓[0;37;40m    [0;91;1;41m▓▓[0;37;40m    [0;91;1;47m▄▄[0;37;40m   [0;91;1;41m▓▓[0;37;40m      [0;91;1;41m▓▓[0;37;40m  [0;91;1;41m▓▓[0;37;40m  [0;91;1;41m▓▓[0;37;40m [0;91;1;41m▓▓[0;37;40m  [0;91;1;41m▓▓[0;37;40m  [0;91;1;41m▓▓[0;37;40m  [0;91;1;47m▄[0;37;40m▄   [0;91;1;41m▓▓[0;37;40m          [0;31;40m▄▄[0;37;40m [0m
						[0;37;40m [0;31;40m▀[0;91;1;41m▀[0;31;40m▌[0;37;40m [0;91;1;41m▀[0;31;40m▀[0;37;40m  [0;91;1;41m▀█[0;37;40m  [0;91;1;41m█▀[0;37;40m  [0;91;1;41m▀█[0;37;40m  [0;91;1;41m█▀[0;37;40m  [0;91;1;41m▀█[0;37;40m   [0;91;1;41m██[0;37;40m      [0;91;1;41m▀█[0;37;40m      [0;91;1;41m██[0;31;40m▄[0;37;40m   [0;91;1;41m▀█[0;37;40m    [0;91;1;41m▀█[0;37;40m   [0;91;1;41m██[0;37;40m      [0;91;1;41m██[0;37;40m  [0;31;40m▀█[0;37;40m  [0;91;1;41m██[0;37;40m [0;91;1;41m▀█[0;37;40m  [0;91;1;41m█▀[0;37;40m  [0;91;1;41m██[0;37;40m  [0;91;1;41m██[0;37;40m   [0;91;1;41m██[0;31;40m▄[0;37;40m        [0;31;40m▄[0;91;1;41m██[0;31;40m▌[0m
						[0;37;40m  [0;31;40m▀█▄▀[0;37;40m    [0;31;40m▀▄▄▀[0;37;40m    [0;31;40m▀▄▄▀[0;37;40m    [0;31;40m▀[0;91;1;41m▀██[0;91;1;40m▀[0;37;40m        [0;31;40m▀[0;91;1;41m▀▀ [0;37;40m   [0;31;40m▀[0;91;1;41m▀[0;31;40m█[0;37;40m    [0;31;40m▀[0;91;1;41m▀▀ [0;37;40m  [0;31;40m▀[0;91;1;41m▀██[0;91;1;40m▀[0;37;40m       [0;91;1;41m▀[0;31;40m▀[0;37;40m      [0;31;40m█▀[0;37;40m  [0;31;40m▀▄▄▀[0;37;40m  [0;31;40m▄[0;91;1;41m▀▀[0;37;40m  [0;91;1;41m▀▀[0;37;40m   [0;31;40m▀[0;91;1;41m▀[0;31;40m█[0;37;40m         [0;31;40m▀[0;91;1;41m▀[0;37;40m [0m
						
						""");
			// Delete character from database when they die
			deleteCharacterFromDatabase();
			handleExit();
		}
	}

	/**
	 * Checks if the player has won the game.
	 * The player wins if they have reached the end of the board (position >= MAX_POSITION)
	 * and the boss has been defeated, while still having health points > 0.
	 * If the player has reached the end but the boss is not defeated, displays a message.
	 */
	private void isWin() {
		if (board.getCurrentPosition() >= board.getMaxPosition()
				&& player.getHealthPoints() > 0
				&& GameState.getInstance().isBossDefeated()) {
			handleExit();
		}
	}

	/**
	 * Ends the game and displays a congratulatory message with ASCII art.
	 * This method is called when the player successfully completes the game by reaching
	 * the end of the board and defeating the boss.
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

	@Override
	public String toString() {
		return super.toString();
	}
}
