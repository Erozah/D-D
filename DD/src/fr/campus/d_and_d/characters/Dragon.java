package fr.campus.d_and_d.characters;

import fr.campus.d_and_d.items.*;

/**
 * Represents a dragon enemy in the game.
 * Dragons are powerful boss enemies.
 */
public class Dragon extends Enemy {
    /**
     * Creates a new dragon.
     * @param name The name of the dragon
     */
    public Dragon(String name) {
        super("Dragon", name, 15, 8,
              new Weapon("Dragon", "Souffle de feu", 5),
              new Shield("Dragon", "Ecailles de dragon", 5),
              true);
    }

    /**
     * Creates a new dragon with custom stats.
     * @param name The name of the dragon
     * @param healthPoints The health points of the dragon
     * @param attackPower The attack power of the dragon
     */
    public Dragon(String name, int healthPoints, int attackPower) {
        super("Dragon", name, healthPoints, attackPower,
              new Weapon("Dragon", "Souffle de feu", attackPower),
              new Shield("Dragon", "Ecailles de dragon", healthPoints/2),
              true);
    }

    @Override
    public String interact() {
        return "🐉 Un dragon apparaît ! Préparez-vous à un combat épique !";
    }
    
    @Override
    public String interact(Character character) {
        return super.interact(character);
    }
    
    @Override
    public String getName() {
        return "Dragon(" + getHealthPoints() + " PV, " + getAttackPower() + " ATK)";
    }
}
