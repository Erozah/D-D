package fr.campus.d_and_d.board;

public class EmptyCell extends Cell {

	@Override
	public String interact() {
		return "Cette case est vide. Ren ne se passe";
	}
}
