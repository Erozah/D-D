/**
 * Represents a cell with a weapon on the game board.
 * Landing on this cell allows the player to pick up a weapon.
 */
package fr.campus.d_and_d.board;

/**
 * A cell containing a weapon. When a player lands on this cell, they can pick up the weapon
 * to increase their attack power.
 */
public class WeaponCell extends Cell {
	/**
	 * Constructs a new WeaponCell.
	 */
	public WeaponCell() {
	}

	@Override
	public String interact() {
		return "Vous trouvez une arme ! Votre puissance d'attaque augmente.";
	}
}
