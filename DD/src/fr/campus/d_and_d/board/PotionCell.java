/**
 * Represents a cell with a potion on the game board.
 * Landing on this cell allows the player to pick up a potion.
 */
package fr.campus.d_and_d.board;

/**
 * A cell containing a potion. When a player lands on this cell, they can pick up the potion
 * to restore their health points.
 */
public class PotionCell extends Cell {
	/**
	 * Constructs a new PotionCell.
	 */
	public PotionCell() {
	}

	@Override
	public String interact() {
		return "Vous trouvez une potion ! Vos points de vie augmentent.";
	}
}
