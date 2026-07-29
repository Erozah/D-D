package fr.campus.d_and_d.characters.enemy;

import fr.campus.d_and_d.characters.Enemy;
import fr.campus.d_and_d.items.defensif.Shield;
import fr.campus.d_and_d.items.offensif.Spell;

/**
 * Represents a sorcerer enemy in the game.
 * Sorcerers are magical enemies with powerful spells.
 */
public class Sorcerer extends Enemy {
    /**
     * Creates a new sorcerer.
     * @param name The name of the sorcerer
     */
    public Sorcerer(String name) {
        super("Sorcier", name, 8, 5,
              new Spell("Sorcier", "Magie noire", 3),
              new Shield("Sorcier", "Barrière magique", 2),
              false);
    }
    
    /**
     * Creates a new sorcerer with custom stats.
     * @param name The name of the sorcerer
     * @param healthPoints The health points of the sorcerer
     * @param attackPower The attack power of the sorcerer
     */
    public Sorcerer(String name, int healthPoints, int attackPower) {
        super("Sorcier", name, healthPoints, attackPower,
              new Spell("Sorcier", "Magie noire", attackPower),
              new Shield("Sorcier", "Barrière magique", healthPoints/3),
              false);
    }
    
    @Override
    public String interact() {
        return "🧙 Un sorcier apparaît ! Attention à ses sorts puissants.";
    }
}
