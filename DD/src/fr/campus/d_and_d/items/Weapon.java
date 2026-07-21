package fr.campus.d_and_d.items;

public class Weapon extends OffensiveEquipment {
	public Weapon(String weaponType, String name, int weaponDamage) {
		super(weaponType, name, weaponDamage);
	}

	@Override
	public String toString() {
		return getName();
	}
}
