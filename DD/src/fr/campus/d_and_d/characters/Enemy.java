package fr.campus.d_and_d.characters;

import fr.campus.d_and_d.board.CellContent;
import fr.campus.d_and_d.gameLogic.HandleBattle;
import fr.campus.d_and_d.items.defensif.DefensiveEquipment;
import fr.campus.d_and_d.items.offensif.OffensiveEquipment;

/**
 * Abstract class representing an enemy character in the game.
 * Enemies can be fought by players and may drop equipment when defeated.
 */
public class Enemy extends Character implements CellContent {
    private boolean isBoss;

    /**
     * Creates a new enemy.
     *
     * @param type               The type of enemy
     * @param name               The name of the enemy
     * @param healthPoints       The health points of the enemy
     * @param attackPower        The attack power of the enemy
     * @param offensiveEquipment The offensive equipment of the enemy
     * @param defensiveEquipment The defensive equipment of the enemy
     * @param isBoss             Whether this enemy is a boss
     */
    public Enemy(String type, String name, int healthPoints, int attackPower,
                 OffensiveEquipment offensiveEquipment, DefensiveEquipment defensiveEquipment, boolean isBoss) {
        super(type, name, healthPoints, attackPower, offensiveEquipment, defensiveEquipment);
        this.isBoss = isBoss;
    }

    /**
     * Checks if this enemy is a boss.
     *
     * @return true if this is a boss enemy, false otherwise
     */
    public boolean isBoss() {
        return isBoss;
    }

    @Override
    public String interact() {
        return "Un " + getType() + " (" + getName() + ") apparaît ! Préparez-vous au combat.";
    }

    @Override
    public String interact(Character character) {
        if (character == null) {
            return interact();
        }

        System.out.println(interact());

        fr.campus.d_and_d.gameLogic.GameState.getInstance()
                .setLastEnemyName(getName());

        HandleBattle battle = new HandleBattle(character, this);
        return battle.startBattle();
    }

    @Override
    public String getName() {
        return super.getName() + " (" + getHealthPoints() + " PV)";
    }
}