package fr.campus.d_and_d.board;

public class EnemyCell extends Cell {
	public EnemyCell() {
	}

	@Override
	public String interact() {
		return "Un ennemi apparaît ! Préparez-vous au combat.";
	}
}
