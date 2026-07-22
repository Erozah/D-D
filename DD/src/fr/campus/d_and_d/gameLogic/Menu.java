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
		System.out.println(textMessage + "\n>");
		Scanner scanner = new Scanner(System.in);
		return scanner.nextLine();
	}

	@Override
	public String toString() {
		return super.toString();
	}
}
