/**
 * Represents a dice used to determine the player's movement on the board.
 * This class generates a random number between 1 and 6.
 */
package fr.campus.d_and_d.gameLogic;

import java.util.Random;

/**
 * The Dice class simulates a six-sided dice for determining player movement.
 */
public class Dice {
	/**
	 * Rolls the dice and returns a random result.
	 * @return An integer between 1 and 6, representing the dice roll result.
	 */
	public int roll() {
		Random random = new Random();
		return random.nextInt(6) + 1;
	}
	@Override
	public String toString() {
		return "Dice roll from 1 to 6";
	}
}
