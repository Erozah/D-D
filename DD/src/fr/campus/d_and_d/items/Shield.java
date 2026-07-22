/**
 * Represents a shield in the game, which is a type of defensive equipment.
 */
package fr.campus.d_and_d.items;

/**
 * A Shield is a defensive equipment that characters can use to protect themselves.
 * Examples include wooden shields, metal shields, and magical shields.
 */
public class Shield extends DefensiveEquipment {
	/**
	 * Constructs a new Shield with specified attributes.
	 * @param shieldType The type of the shield (e.g., "Wooden" or "Metal").
	 * @param name The name of the shield.
	 * @param defensePoints The defense points provided by the shield.
	 */
	public Shield(String shieldType, String name, int defensePoints) {
		super(shieldType, name, defensePoints);
	}

	@Override
	public String toString() {
		return getName();
	}
}
