/**
 * Represents a weapon in the game, which is a type of offensive equipment.
 */
package fr.campus.d_and_d.items;

/**
 * A Weapon is an offensive equipment that characters can use to attack enemies.
 * Examples include swords, axes, and bows.
 */
public class Weapon extends OffensiveEquipment {
	/**
	 * Constructs a new Weapon with specified attributes.
	 * @param weaponType The type of the weapon (e.g., "Sword" or "Axe").
	 * @param name The name of the weapon.
	 * @param weaponDamage The damage inflicted by the weapon.
	 */
	public Weapon(String weaponType, String name, int weaponDamage) {
		super(weaponType, name, weaponDamage);
	}

	@Override
	public String toString() {
		return getName();
	}
}
