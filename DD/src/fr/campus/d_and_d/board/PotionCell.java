package fr.campus.d_and_d.board;

public class PotionCell extends Cell {
	public PotionCell() {
	}

	@Override
	public String interact() {
		return "Vous trouvez une potion ! Vos points de vie augmentent.";
	}
}
