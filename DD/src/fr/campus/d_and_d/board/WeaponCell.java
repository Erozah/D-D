package fr.campus.d_and_d.board;

public class WeaponCell extends Cell {
	public WeaponCell() {
	}

	@Override
	public String interact() {
		return "Vous trouvez une arme ! Votre puissance d'attaque augmente.";
	}
}
