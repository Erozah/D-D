/**
 * Represents offensive equipment in the game, such as a weapon or spell.
 * This equipment allows the character to inflict damage.
 */
package fr.campus.d_and_d.items;

/**
 * Abstract class representing offensive equipment that can be used by characters.
 * Subclasses include Weapon and Spell.
 */
public abstract class OffensiveEquipment {
	private String equipmentType = "Hand";
	private String name = "Fist";
	private int attackPower = 1;

	/**
	 * Constructs a new OffensiveEquipment with specified attributes.
	 * @param equipmentType The type of the equipment (e.g., "Weapon" or "Spell").
	 * @param name The name of the equipment.
	 * @param attackPower The attack power of the equipment.
	 */
	public OffensiveEquipment(String equipmentType, String name, int attackPower) {
		this.equipmentType = equipmentType;
		this.name = name;
		this.attackPower = attackPower;
	}

	/**
	 * Gets the type of the equipment.
	 * @return The equipment type.
	 */
	public String getEquipmentType() {
		return equipmentType;
	}

	/**
	 * Sets the type of the equipment.
	 * @param equipmentType The new equipment type.
	 */
	public void setEquipmentType(String equipmentType) {
		this.equipmentType = equipmentType;
	}

	/**
	 * Gets the attack power of the equipment.
	 * @return The attack power.
	 */
	public int getAttackPower() {
		return attackPower;
	}

	/**
	 * Sets the attack power of the equipment.
	 * @param attackPower The new attack power.
	 */
	public void setAttackPower(int attackPower) {
		this.attackPower = attackPower;
	}

	/**
	 * Gets the name of the equipment.
	 * @return The equipment name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name of the equipment.
	 * @param name The new equipment name.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Returns a string representation of the equipment.
	 * @return A string describing the equipment.
	 */
	public String toString() {
		return "[Type : " + equipmentType + " ] " + name + ": " + attackPower + " dégats.";
	}
}
