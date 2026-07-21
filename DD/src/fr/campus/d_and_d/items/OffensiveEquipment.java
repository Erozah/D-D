/**
 * Représente un équipement offensif dans le jeu, tel qu'une épée ou un sort.
 * Cet équipement permet au personnage d'infliger des dégâts.
 */
package fr.campus.d_and_d.items;

public abstract class OffensiveEquipment {
	private String weaponType = "Hand";
	private String name = "Fist";
	private int weaponDamage = 1;



	public OffensiveEquipment(String weaponType, String name, int weaponDamage) {
		this.weaponType = weaponType;
		this.name = name;
		this.weaponDamage = weaponDamage;
	}

	public String getWeaponType() {
		return weaponType;
	}

	public void setWeaponType(String weaponType) {
		this.weaponType = weaponType;
	}

	public int getWeaponDamage() {
		return weaponDamage;
	}

	public void setWeaponDamage(int weaponDamage) {
		this.weaponDamage = weaponDamage;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public String toString() {
		return "[Type : " + weaponType + " ] " + name + ": " + weaponDamage + " dégats.";
	}
}
