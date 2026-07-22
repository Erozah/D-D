/**
 * The Cell class represents a cell on the game board.
 * It is an abstract class that must be extended by specific cell types.
 */
package fr.campus.d_and_d.board;

/**
 * Abstract class representing a cell on the game board.
 * Subclasses must implement the interact() method to define cell-specific behavior.
 */
public abstract class Cell {
	/**
	 * Defines the interaction behavior when a player lands on this cell.
	 * @return A message describing the interaction.
	 */
	public abstract String interact();

	@Override
	public String toString() {
		return "Cell{}";
	}
}
