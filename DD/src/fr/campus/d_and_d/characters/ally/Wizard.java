/**
 * Represents a wizard in the Dungeons and Dragons game.
 * This class extends Character and defines wizard-specific attributes.
 */
package fr.campus.d_and_d.characters.ally;
import fr.campus.d_and_d.characters.Character;
import fr.campus.d_and_d.items.defensif.Robe;
import fr.campus.d_and_d.items.offensif.Spell;

/**
 * A Wizard is a character type with magical abilities and potent spells.
 */
public class Wizard extends Character {
	/**
	 * Creates a new wizard with specified type and name.
	 * @param type The type of the character (should be "Wizard").
	 * @param name The name of the wizard.
	 */
	public Wizard(String type, String name) {
		super(type,
				name,
				7,
				7,
				new Spell("Sort", "Boule de feu", 1),
				new Robe("Leather", "Robe en cuir", 4));
	}

	@Override
	public String toString() {
		return super.toString();
	}
}
