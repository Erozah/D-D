/**
 * Represents an Evil Spirit enemy that only attacks Wizards.
 * Evil Spirits have 15 health points and 4 attack power.
 * They will only interact with Wizard characters, ignoring other character types.
 */
package fr.campus.d_and_d.characters.enemy;

import fr.campus.d_and_d.characters.Character;
import fr.campus.d_and_d.characters.Enemy;
import fr.campus.d_and_d.characters.ally.Wizard;
import fr.campus.d_and_d.items.offensif.Spell;
import fr.campus.d_and_d.items.defensif.Shield;

/**
 * EvilSpirit is a special enemy that only attacks Wizard characters.
 * This enemy has specific behavior where it will only engage in combat
 * with Wizards, making it a targeted threat.
 */
public class EvilSpirit extends Enemy {
    
    /**
     * Creates a new EvilSpirit with default statistics.
     * Evil Spirits have 15 health points and 4 attack power.
     * 
     * @param name The name of the Evil Spirit
     */
    public EvilSpirit(String name) {
        super("Evil Spirit", name, 15, 4, 
              new Spell("Spirit", "Evil Touch", 4),
              new Shield("Spirit", "Ethereal Shield", 2),
              false);
    }
    
    /**
     * Creates a new EvilSpirit with custom statistics.
     * 
     * @param name The name of the Evil Spirit
     * @param healthPoints The health points of the Evil Spirit
     * @param attackPower The attack power of the Evil Spirit
     */
    public EvilSpirit(String name, int healthPoints, int attackPower) {
        super("Evil Spirit", name, healthPoints, attackPower,
              new Spell("Spirit", "Evil Touch", attackPower),
              new Shield("Spirit", "Ethereal Shield", 2),
              false);
    }
    
    /**
     * Interacts with a character. Evil Spirits only attack Wizard characters.
     * If the character is not a Wizard, the Evil Spirit will not engage in combat.
     * 
     * @param character The character to interact with
     * @return A message describing the interaction result
     */
    @Override
    public String interact(Character character) {
        // Evil Spirits only attack Wizards
        if (character instanceof Wizard) {
            return super.interact(character);
        } else {
            return "L'esprit ignore " + character.getName() + " qui n'est pas un magicien..";
        }
    }
}