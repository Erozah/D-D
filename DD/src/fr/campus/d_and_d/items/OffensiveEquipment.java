/**
 * Représente un équipement offensif dans le jeu, tel qu'une épée ou un sort.
 * Cet équipement permet au personnage d'infliger des dégâts.
 */
package fr.campus.d_and_d.items;

public class OffensiveEquipment {
	private String weaponType;
	private int weaponDamage;
	private String name;
	public OffensiveEquipment() {

	}
	public String toString() {
		return "[Type : " + weaponType + " ] " + name + ": " + weaponDamage + " dégats.";
	}
}
