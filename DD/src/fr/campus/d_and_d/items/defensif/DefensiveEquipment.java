/**
 * Represents defensive equipment in the game, such as a shield or potion.
 * This equipment allows the character to protect themselves or heal.
 */
package fr.campus.d_and_d.items.defensif;

/**
 * Abstract class representing defensive equipment that can be used by characters.
 * Subclasses include Shield and Potion.
 */
public abstract class DefensiveEquipment {
	private String equipmentType = "Armor";
	private String name = "LeatherArmor";
	private int defensePoints = 1;

	/**
	 * Constructs a new DefensiveEquipment with specified attributes.
	 * @param equipmentType The type of the equipment (e.g., "Shield" or "Potion").
	 * @param name The name of the equipment.
	 * @param defensePoints The defense points of the equipment.
	 */
	public DefensiveEquipment(String equipmentType, String name, int defensePoints) {
		this.equipmentType = equipmentType;
		this.name = name;
		this.defensePoints = defensePoints;
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
	 * Gets the defense points of the equipment.
	 * @return The defense points.
	 */
	public int getDefensePoints() {
		return defensePoints;
	}

	/**
	 * Sets the defense points of the equipment.
	 * @param defensePoints The new defense points.
	 */
	public void setDefensePoints(int defensePoints) {
		this.defensePoints = defensePoints;
	}

	/**
	 * Returns a string representation of the equipment.
	 * @return A string describing the equipment.
	 */
	public String toString() {
		return "[Type : " + equipmentType + " ] " + name + ": " + defensePoints + " dégats réduits.";
	}
}
