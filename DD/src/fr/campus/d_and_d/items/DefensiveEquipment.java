/**
 * Représente un équipement défensif dans le jeu, tel qu'un bouclier ou une potion.
 * Cet équipement permet au personnage de se protéger ou de se soigner.
 */
package fr.campus.d_and_d.items;

public class DefensiveEquipment {
	private String defensiveType;
	private String name;
	private int defensivePoints;
	public DefensiveEquipment() {

	}
	public String toString() {
		return "[Type : " + defensiveType + " ] " + name + ": " + defensivePoints + " dégats.";
	}
}
