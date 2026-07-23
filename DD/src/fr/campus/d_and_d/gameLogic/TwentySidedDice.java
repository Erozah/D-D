/**
 * Represents a twenty-sided dice for determining critical hits and misses.
 */
package fr.campus.d_and_d.gameLogic;

import java.util.Random;

/**
 * The TwentySidedDice class simulates a twenty-sided dice for determining
 * critical hits (rolling 20) and critical misses (rolling 1).
 */
public class TwentySidedDice implements Dice {
	/**
	 * Rolls the twenty-sided dice and returns a random result between 1 and 20.
	 * @return An integer between 1 and 20, representing the dice roll result.
	 */
	@Override
	public int roll() {
		Random random = new Random();
		return random.nextInt(20) + 1;
	}
	
	@Override
	public String toString() {
		return "Twenty-sided Dice roll from 1 to 20";
	}
}