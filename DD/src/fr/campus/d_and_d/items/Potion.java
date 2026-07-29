/**
 * Represents a potion in the game, which is a type of defensive equipment.
 */
package fr.campus.d_and_d.items;

import fr.campus.d_and_d.board.CellContent;
import fr.campus.d_and_d.characters.Character;

/**
 * A Potion is a consumable that characters can use to restore health or gain temporary buffs.
 * Examples include health potions and strength potions.
 */
public class Potion extends Consumable implements CellContent {
	/**
	 * Constructs a new Potion with specified attributes.
	 * @param consumableType The type of the potion (e.g., "Health" or "Strength").
	 * @param name The name of the potion.
	 * @param consumablePoints The defense points or healing points provided by the potion.
	 */
	public Potion(String consumableType, String name, int consumablePoints) {
		super(consumableType, name, consumablePoints);
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
		int healAmount = getConsumablePoints();
		if ((character.getHealthPoints() + healAmount) > character.getMaxHealth()) {
			character.setHealthPoints(character.getMaxHealth());
			return "Vous avez utilisé " + getName() + "! Vous essayez de récupérez " + healAmount + " points de vie. Total: " + character.getHealthPoints() + " PV.";
		} else {
			character.setHealthPoints(character.getHealthPoints() + healAmount);
			return "Vous avez utilisé " + getName() + "! Vous récupérez " + healAmount + " points de vie. Total: " + character.getHealthPoints() + " PV.";
		}
	}
}
