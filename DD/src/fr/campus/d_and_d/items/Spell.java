/**
 * Represents a spell in the game, which is a type of offensive equipment.
 */
package fr.campus.d_and_d.items;

/**
 * A Spell is an offensive equipment that characters can use to cast magical attacks.
 * Examples include fireballs, lightning bolts, and ice shards.
 */
public class Spell extends OffensiveEquipment {
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
}
