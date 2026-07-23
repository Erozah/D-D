/**
 * Interface for all types of dice in the game.
 */
package fr.campus.d_and_d.gameLogic;

/**
 * The Dice interface defines the contract for all dice types in the game.
 */
public interface Dice {
	/**
	 * Rolls the dice and returns a random result.
	 * @return An integer representing the dice roll result.
	 */
	int roll();
}
