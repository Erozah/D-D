/**
 * Represents an empty cell on the game board.
 * Landing on this cell has no effect on the player.
 */
package fr.campus.d_and_d.board;

/**
 * A cell that has no effect when landed on.
 */
public class EmptyCell extends Cell {

	@Override
	public String interact() {
		return "Cette case est vide. Rien ne se passe.";
	}
}
