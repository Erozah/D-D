/**
 * Manages the main game logic, including player movement on the board.
 * This class uses a Board and a Dice to simulate the game.
 */
package fr.campus.d_and_d.gameLogic;
import fr.campus.d_and_d.board.*;
import fr.campus.d_and_d.characters.*;

import java.util.Scanner;

/**
 * The Game class handles the core game logic, including character creation,
 * player movement, and game state management.
 */
public class Game {
	/**
	 * Displays the main menu and handles user choices for creating a character or starting the game.
	 */
	public void mainMenu() {
		Menu menu = new Menu();
		String choice = menu.askPlayerString("1. Nouveau personnage\n2. Quitter");
		if (choice.equals("1") )
			this.createCharacter();
		choice = menu.askPlayerString("Démarrer la partie ? 1. Oui / 2. Non");
		if (choice.equals("1"))
			this.start();
		return;
	}
	/**
	 * Guides the player through character creation, allowing them to choose between a Warrior or Wizard.
	 */
	public void createCharacter() {
		Menu menu = new Menu();
		String characterName = menu.askPlayerString("Quel est votre nom ?");
		String choice = menu.askPlayerString("Choisissez votre classe :\n1. Guerrier\n2. Magicien");

		if (choice.equals("1")) {
			Warrior warrior = new Warrior("Guerrier", characterName);
			System.out.println(warrior);
			return;
		}
		if (choice.equals("2")) {
			Wizard wizard = new Wizard("Magicien", characterName);
			System.out.println(wizard);
			return;
		}


	}

	/**
	 * Handles a single turn of the game, including rolling the dice and moving the player.
	 * @param board The game board.
	 * @param dice The dice used to determine movement.
	 */
	public void playTurn(Board board, Dice dice) {
		Scanner scanner = new Scanner(System.in);
		Menu menu = new Menu();
		menu.askPlayerString("Appuyez sur 'Entrée' pour lancer le dé...");
		int diceResult = dice.roll();
		try {
			int newPosition = board.getCurrentPosition() + diceResult;
			board.setCurrentPosition(newPosition);
			System.out.println(board.toString());
			Cell currentCell = board.getCurrentCell();
			System.out.println(currentCell.interact());
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
		Board board = new Board();
		Dice dice = new Dice();
		Scanner scanner = new Scanner(System.in);
		while (board.getCurrentPosition() < board.getMaxPosition())
			playTurn(board, dice);
		endGame();
		scanner.close();
	}

	@Override
	public String toString() {
		return super.toString();
	}
}
