/**
 * Représente un équipement défensif dans le jeu, tel qu'un bouclier ou une potion.
 * Cet équipement permet au personnage de se protéger ou de se soigner.
 */
package fr.campus.d_and_d.items;

public class DefensiveEquipment {
	private String defensiveType = "Armor";
	private String name = "LeatherArmor";
	private int defensivePoints = 1;

	public DefensiveEquipment(String defensiveType, String name, int defensivePoints) {
		this.defensiveType = defensiveType;
		this.name = name;
		this.defensivePoints = defensivePoints;
	}

	public String getDefensiveType() {
		return defensiveType;
	}

	public void setDefensiveType(String defensiveType) {
		this.defensiveType = defensiveType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getDefensivePoints() {
		return defensivePoints;
	}

	public void setDefensivePoints(int defensivePoints) {
		this.defensivePoints = defensivePoints;
	}

	public String toString() {
		return "[Type : " + defensiveType + " ] " + name + ": " + defensivePoints + " dégats.";
	}
}
