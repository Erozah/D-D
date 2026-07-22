package fr.campus.d_and_d.board;

/**
 * A cell containing an enemy boss. When a player lands on this cell, a combat is initiated.
 */
public class BossCell extends Cell {
	/**
	 * Constructs a new BossCell.
	 */
	public BossCell() {
	}

	@Override
	public String interact() {
		return "Attention, un boss apparaît ! Préparez-vous au combat.";
	}
}
