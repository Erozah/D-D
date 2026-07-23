package fr.campus.d_and_d.characters;

import fr.campus.d_and_d.items.*;

/**
 * Represents an orc enemy in the game.
 * Orcs are stronger than goblins but still regular enemies.
 */
public class Orc extends Enemy {
    /**
     * Creates a new orc.
     * @param name The name of the orc
     */
    public Orc(String name) {
        super("Orc", name, 12, 8,
              new Weapon("Orc", "Battle Axe", 6),
              new Shield("Orc", "Wooden Shield", 4),
              false);
    }
    
    /**
     * Creates a new orc with custom stats.
     * @param name The name of the orc
     * @param healthPoints The health points of the orc
     * @param attackPower The attack power of the orc
     */
    public Orc(String name, int healthPoints, int attackPower) {
        super("Orc", name, healthPoints, attackPower,
              new Weapon("Orc", "Battle Axe", attackPower),
              new Shield("Orc", "Wooden Shield", healthPoints/3),
              false);
    }
    
    @Override
    public String interact() {
        return "👺 Un orc apparaît ! Un adversaire plus coriace.";
    }
    
    @Override
    public String interact(Character character) {
        return super.interact(character);
    }
}
