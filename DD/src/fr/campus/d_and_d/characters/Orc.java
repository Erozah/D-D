package fr.campus.d_and_d.characters;

import fr.campus.d_and_d.items.*;

/**
 * Represents an orc enemy in the game.
 * Orcs are stronger than goblins but still regular enemies.
 */
public class Orc extends Enemy {
    /**
     * Creates a new orc.
     * Orcs only attack Warriors and have 10 health points and 6 attack power.
     * @param name The name of the orc
     */
    public Orc(String name) {
        super("Orc", name, 10, 6,
              new Weapon("Orc", "Hache de guerre", 6),
              new Shield("Orc", "Bouclier en bois", 3),
              false);
    }
    
    /**
     * Creates a new orc with custom stats.
     * Orcs only attack Warriors.
     * @param name The name of the orc
     * @param healthPoints The health points of the orc
     * @param attackPower The attack power of the orc
     */
    public Orc(String name, int healthPoints, int attackPower) {
        super("Orc", name, healthPoints, attackPower,
              new Weapon("Orc", "Hache de guerre", attackPower),
              new Shield("Orc", "Bouclier en bois", healthPoints/3),
              false);
    }
    
    @Override
    public String interact() {
        return "👺 Un orc apparaît ! Un adversaire plus coriace.";
    }
    
    @Override
    public String interact(Character character) {
        if (character instanceof Warrior) {
            return super.interact(character);
        } else {
            return "L'orc ignore " + character.getName() + " car il n'est pas un guerrier.";
        }
    }
}
