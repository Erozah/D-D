/**
 * Represents a cell with an enemy on the game board.
 * Landing on this cell triggers a combat sequence.
 */
package fr.campus.d_and_d.board;

/**
 * A cell containing an enemy. When a player lands on this cell, a combat is initiated.
 */
public class EnemyCell extends Cell {
	/**
	 * Constructs a new EnemyCell.
	 */
	public EnemyCell() {
	}

	@Override
	public String interact() {
		return "Un ennemi apparaît ! Préparez-vous au combat.";
	}
}
