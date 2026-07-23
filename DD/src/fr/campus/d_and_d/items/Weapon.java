/**
 * Represents a weapon in the game, which is a type of offensive equipment.
 */
package fr.campus.d_and_d.items;

import fr.campus.d_and_d.board.CellContent;
import fr.campus.d_and_d.characters.Character;
import fr.campus.d_and_d.characters.Warrior;

/**
 * A Weapon is an offensive equipment that characters can use to attack enemies.
 * Examples include swords, axes, and bows.
 */
public class Weapon extends OffensiveEquipment implements CellContent {
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

	@Override
	public String interact() {
		return "Vous avez trouvé une arme: " + getName() + "! Votre puissance d'attaque augmente.";
	}
	
	@Override
	public String interact(fr.campus.d_and_d.characters.Character character) {
		if (character == null) {
			return interact();
		}
		
		// Only warriors can pick up weapons
		if (character instanceof Warrior) {
			character.setOffensiveEquipment(this);
			return "Vous avez ramassé l'arme: " + getName() + "! Votre puissance d'attaque est maintenant de " + character.getAttackPower() + ".";
		} else {
			return "En tant que magicien, vous ne pouvez pas utiliser cette arme. Vous laissez " + getName() + " sur place.";
		}
	}

	@Override
	public String getName() {
		return super.getName();
	}
}
