/**
 * Represents a potion in the game, which is a type of defensive equipment.
 */
package fr.campus.d_and_d.items;

import fr.campus.d_and_d.board.CellContent;
import fr.campus.d_and_d.characters.Character;

/**
 * A Potion is a defensive equipment that characters can use to restore health or gain temporary buffs.
 * Examples include health potions, mana potions, and strength potions.
 */
public class Potion extends DefensiveEquipment implements CellContent {
	/**
	 * Constructs a new Potion with specified attributes.
	 * @param potionType The type of the potion (e.g., "Health" or "Mana").
	 * @param name The name of the potion.
	 * @param defensePoints The defense points or healing points provided by the potion.
	 */
	public Potion(String potionType, String name, int defensePoints) {
		super(potionType, name, defensePoints);
	}

	@Override
	public String toString() {
		return getName();
	}

	@Override
	public String interact() {
		return "Vous avez trouvé une potion: " + getName() + "! Vos points de vie augmentent.";
	}
	
	@Override
	public String interact(Character character) {
		if (character == null) {
			return interact();
		}
		
		// Any character can use potions
		int healAmount = getDefensePoints();
		if ((character.getHealthPoints() + healAmount) > character.getMaxHealth()) {
			character.setHealthPoints(character.getMaxHealth());
			return "Vous avez utilisé " + getName() + "! Vous essayez de récupérez " + healAmount + " points de vie. Total: " + character.getHealthPoints() + " PV.";
		} else {
			character.setHealthPoints(character.getHealthPoints() + healAmount);
			return "Vous avez utilisé " + getName() + "! Vous récupérez " + healAmount + " points de vie. Total: " + character.getHealthPoints() + " PV.";
		}
	}

	@Override
	public String getName() {
		return super.getName();
	}
}
