/**
 * Represents a warrior in the Dungeons and Dragons game.
 * This class extends Character and defines warrior-specific attributes.
 */
package fr.campus.d_and_d.characters;
import fr.campus.d_and_d.items.Shield;
import fr.campus.d_and_d.items.Weapon;

/**
 * A Warrior is a character type with high health points and strong offensive equipment.
 */
public class Warrior extends Character {
	/**
	 * Creates a new warrior with specified type and name.
	 * @param type The type of the character (should be "Warrior").
	 * @param name The name of the warrior.
	 */
	public Warrior(String type, String name) {
		super(type,
				name,
				10,
				7,
				new Weapon("Arme", "Epée", 1),
				new Shield("Bouclier", "Bouclier en bois", 5));
	}

	@Override
	public String toString() {
		return super.toString();
	}
}
