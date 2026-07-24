package fr.campus.d_and_d.characters;

import fr.campus.d_and_d.items.*;

/**
 * Represents a goblin enemy in the game.
 * Goblins are weak but numerous enemies.
 */
public class Goblin extends Enemy {
    /**
     * Creates a new goblin.
     * @param name The name of the goblin
     */
    public Goblin(String name) {
        super("Goblin", name, 5, 3,
              new Weapon("Goblin", "Rusty Dagger", 3),
              new Shield("Goblin", "Leather Armor", 2),
              false);
    }
    
    /**
     * Creates a new goblin with custom stats.
     * @param name The name of the goblin
     * @param healthPoints The health points of the goblin
     * @param attackPower The attack power of the goblin
     */
    public Goblin(String name, int healthPoints, int attackPower) {
        super("Goblin", name, healthPoints, attackPower,
              new Weapon("Goblin", "Rusty Dagger", attackPower),
              new Shield("Goblin", "Leather Armor", healthPoints/4),
              false);
    }
    
    @Override
    public String interact() {
        return "👹 Un gobelin apparaît ! Un adversaire faible mais rusé.";
    }
    
    @Override
    public String interact(Character character) {
        return super.interact(character);
    }
}
