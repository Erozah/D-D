/**
 * Manages the user interface of the game, allowing players to create a character
 * and start a game.
 */
package fr.campus.d_and_d.gameLogic;
import java.util.Scanner;

/**
 * The Menu class handles user interactions, such as displaying messages and capturing user input.
 */
public class Menu {
	/**
	 * Constructs a new Menu.
	 */
	public Menu() {

	}
	/**
	 * Prompts the player with a message and captures their input.
	 * @param textMessage The message to display to the player.
	 * @return The player's input as a String.
	 */
	public String askPlayerString(String textMessage) {
		beforeLine();
		System.out.printf(textMessage);
		afterLine();
		System.out.print("> ");
		Scanner scanner = new Scanner(System.in);
		return scanner.nextLine();
	}
	public void beforeLine() {
		System.out.println("=".repeat(54) + "\n" + "||" + " ".repeat(50) + "||");
	}

	public void afterLine() {
		System.out.println("||                                                  ||\n" +
				"======================================================");
	}

	@Override
	public String toString() {
		return super.toString();
	}
}
