/**
 * Represents a spell in the game, which is a type of offensive equipment.
 */
package fr.campus.d_and_d.items.offensif;

import fr.campus.d_and_d.board.CellContent;
import fr.campus.d_and_d.characters.Character;
import fr.campus.d_and_d.characters.ally.Wizard;

/**
 * A Spell is an offensive equipment that characters can use to cast magical attacks.
 * Examples include fireballs, lightning bolts, and ice shards.
 */
public class Spell extends OffensiveEquipment implements CellContent {
	/**
	 * Constructs a new Spell with specified attributes.
	 * @param spellType The type of the spell (e.g., "Fire" or "Ice").
	 * @param name The name of the spell.
	 * @param spellDamage The damage inflicted by the spell.
	 */
	public Spell(String spellType, String name, int spellDamage) {
		super(spellType, name, spellDamage);
	}

	@Override
	public String toString() {
		return getName();
	}

	@Override
	public String interact() {
		return "Vous avez trouvé un sort: " + getName() + "! Votre puissance magique augmente.";
	}
	
	@Override
	public String interact(Character character) {
		if (character == null) {
			return interact();
		}
		
		// Only wizards can pick up spells
		if (character instanceof Wizard) {
			character.setOffensiveEquipment(this);
			return "Vous avez appris le sort: " + getName() + "! Votre puissance magique est maintenant de " + character.getAttackPower() + ".";
		} else {
			return "En tant que guerrier, vous ne pouvez pas utiliser ce sort. Vous laissez " + getName() + " sur place.";
		}
	}
}
