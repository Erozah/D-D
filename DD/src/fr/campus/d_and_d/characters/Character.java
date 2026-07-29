/**
 * Represents a generic character in the Dungeons and Dragons game.
 * This class defines the basic attributes of a character, such as type, name,
 * health points, and equipment.
 */
package fr.campus.d_and_d.characters;

import fr.campus.d_and_d.items.DefensiveEquipment;
import fr.campus.d_and_d.items.OffensiveEquipment;
import fr.campus.d_and_d.items.Shield;
import fr.campus.d_and_d.items.Weapon;

/**
 * Abstract class representing a character in the game.
 * Subclasses must implement specific character types (e.g., Warrior, Wizard).
 */
public abstract class Character {
	private String type = "Settler";
	private String name = "Juan Carlos";
	private int healthPoints = 1;
	private int baseAttackPower = 1; // Base attack power without equipment
	private int attackPower = 1; // Total attack power (base + equipment)
	private int databaseId = -1; // ID in database, -1 means not saved yet
	private OffensiveEquipment offensiveEquipment =
			new Weapon("Hand", "Fist", 1);
	private DefensiveEquipment defensiveEquipment =
			new Shield("Armor", "LeatherArmor", 1);
	private int maxHealth;

	/**
	 * Creates a new character with specified attributes.
	 * @param type The type of the character (e.g., "Warrior" or "Wizard").
	 * @param name The name of the character.
	 * @param healthPoints The health points of the character.
	 * @param attackPower The attack power of the character.
	 * @param offensiveEquipment The offensive equipment of the character.
	 * @param defensiveEquipment The defensive equipment of the character.
	 */
	public Character(String type,
	                 String name,
	                 int healthPoints,
	                 int attackPower,
	                 OffensiveEquipment offensiveEquipment,
	                 DefensiveEquipment defensiveEquipment) {
		this.type = type;
		this.name = name;
		this.healthPoints = healthPoints;
		this.baseAttackPower = attackPower;
		this.attackPower = attackPower + offensiveEquipment.getAttackPower();
		this.offensiveEquipment = offensiveEquipment;
		this.defensiveEquipment = defensiveEquipment;
		this.maxHealth = healthPoints;
	}

	/**
	 * Gets the type of the character.
	 * @return The character's type.
	 */
	public String getType() {
		return type;
	}
	/**
	 * Sets the type of the character.
	 * @param type The new type of the character.
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * Gets the name of the character.
	 * @return The character's name.
	 */
	public String getName() {
		return name;
	}
	/**
	 * Sets the name of the character.
	 * @param name The new name of the character.
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * Gets the health points of the character.
	 * @return The character's health points.
	 */
	public int getHealthPoints() {
		return healthPoints;
	}
	/**
	 * Sets the health points of the character.
	 * @param healthPoints The new health points of the character.
	 * @throws IllegalArgumentException If healthPoints is negative
	 */
	public void setHealthPoints(int healthPoints) {
		if (healthPoints < 0) {
			healthPoints = 0;
		}
		this.healthPoints = healthPoints;
	}
	/**
	 * Gets the attack power of the character.
	 * @return The character's attack power.
	 */
	public int getAttackPower() {
		return attackPower;
	}
	/**
	 * Sets the attack power of the character.
	 * @param attackPower The new attack power of the character.
	 */
	public void setAttackPower(int attackPower) {
		this.attackPower = attackPower;
	}
	/**
	 * Gets the database ID of the character.
	 * @return The character's database ID, or -1 if not saved yet.
	 */
	public int getDatabaseId() {
		return databaseId;
	}
	/**
	 * Sets the database ID of the character.
	 * @param databaseId The database ID of the character.
	 */
	public void setDatabaseId(int databaseId) {
		this.databaseId = databaseId;
	}
	/**
	 * Gets the base attack power of the character (without equipment).
	 * @return The character's base attack power.
	 */
	public int getBaseAttackPower() {
		return baseAttackPower;
	}
	/**
	 * Sets the base attack power of the character (without equipment).
	 * @param baseAttackPower The character's base attack power.
	 */
	public void setBaseAttackPower(int baseAttackPower) {
		this.baseAttackPower = baseAttackPower;
	}
	/**
	 * Gets the offensive equipment of the character.
	 * @return The character's offensive equipment.
	 */
	public OffensiveEquipment getOffensiveEquipment() {
		return offensiveEquipment;
	}
	/**
	 * Sets the offensive equipment of the character.
	 * @param offensiveEquipment The new offensive equipment of the character.
	 */
	public void setOffensiveEquipment(OffensiveEquipment offensiveEquipment) {
		this.offensiveEquipment = offensiveEquipment;
		// Update attack power based on the new equipment: base + weapon
		this.attackPower = this.baseAttackPower + offensiveEquipment.getAttackPower();
	}
	/**
	 * Gets the defensive equipment of the character.
	 * @return The character's defensive equipment.
	 */
	public DefensiveEquipment getDefensiveEquipment() {
		return defensiveEquipment;
	}
	/**
	 * Sets the defensive equipment of the character.
	 * @param defensiveEquipment The new defensive equipment of the character.
	 */
	public void setDefensiveEquipment(DefensiveEquipment defensiveEquipment) {
		this.defensiveEquipment = defensiveEquipment;
	}

	public int getMaxHealth() {
		return maxHealth;
	}

	public boolean canUse() {
		return true;
	}
	/**
	 * Returns a string representation of the character.
	 * @return A string describing the character's attributes.
	 */
	public String toString() {
		return "Le personnage est un "+ type +
				"\nIl s'appelle " + name + " et possède " + healthPoints +
				" points de vie. \nIl fait " + attackPower + " dégats grâce à son " + offensiveEquipment +
				".\nIl peut se protéger grâce à : " + defensiveEquipment;
	}
}
