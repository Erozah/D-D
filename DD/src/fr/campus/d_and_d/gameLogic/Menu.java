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
	private final Scanner scanner = new Scanner(System.in);
	private final int INNER_WIDTH = 60;
	private final String BORDER = "=".repeat(INNER_WIDTH + 4);

	/**
	 * Prompts the player with a message and captures their input.
	 * @param textMessage The message to display to the player.
	 * @return The player's input as a String.
	 */
	public String askPlayerString(String... textMessage) {
		beforeLine();
		for (String line : textMessage)
			printCenteredLine(line);
		afterLine();
		System.out.print("> ");
		return scanner.nextLine();
	}
	private void printBorder() {
		System.out.println(BORDER);
	}
	private void printEmptyLine() {
		System.out.printf("||" + " ".repeat(INNER_WIDTH) + "||\n");
	}
	public void printCenteredLine(String textMessage) {
		if (textMessage.length() > INNER_WIDTH)
			textMessage = textMessage.substring(0, INNER_WIDTH);
		int leftPadding = (INNER_WIDTH - textMessage.length()) / 2;
		int rightPadding = INNER_WIDTH - textMessage.length() - leftPadding;
		System.out.println("||" + " ".repeat(leftPadding) + textMessage + " ".repeat(rightPadding) + "||");
	}
	public void beforeLine() {
		printBorder();
		printEmptyLine();
	}
	public void afterLine() {
		printEmptyLine();
		printBorder();
	}



	@Override
	public String toString() {
		return super.toString();
	}
}
