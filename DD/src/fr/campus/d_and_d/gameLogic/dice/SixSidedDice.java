/**
 * Represents a six-sided dice for determining the player's movement on the board.
 * This class generates a random number between 1 and 6.
 */
package fr.campus.d_and_d.gameLogic.dice;

import java.util.Random;

/**
 * The SixSidedDice class simulates a six-sided dice for determining player movement.
 */
public class SixSidedDice implements Dice {
	/**
	 * Rolls the six-sided dice and returns a random result between 1 and 6.
	 * @return An integer between 1 and 6, representing the dice roll result.
	 */
	@Override
	public int roll() {
		Random random = new Random();
		return random.nextInt(6) + 1;
	}
	
	@Override
	public String toString() {
		return "Six-sided Dice roll from 1 to 6";
	}
}