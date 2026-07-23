package fr.campus.d_and_d.characters;

import fr.campus.d_and_d.board.CellContent;
import fr.campus.d_and_d.gameLogic.Dice;
import fr.campus.d_and_d.gameLogic.Menu;
import fr.campus.d_and_d.gameLogic.SixSidedDice;
import fr.campus.d_and_d.gameLogic.TwentySidedDice;
import fr.campus.d_and_d.items.*;
import java.util.Scanner;

/**
 * Abstract class representing an enemy character in the game.
 * Enemies can be fought by players and may drop equipment when defeated.
 */
public class Enemy extends Character implements CellContent {
    private boolean isBoss;
    
    /**
     * Creates a new enemy.
     * @param type The type of enemy
     * @param name The name of the enemy
     * @param healthPoints The health points of the enemy
     * @param attackPower The attack power of the enemy
     * @param offensiveEquipment The offensive equipment of the enemy
     * @param defensiveEquipment The defensive equipment of the enemy
     * @param isBoss Whether this enemy is a boss
     */
    public Enemy(String type, String name, int healthPoints, int attackPower,
                OffensiveEquipment offensiveEquipment, DefensiveEquipment defensiveEquipment, boolean isBoss) {
        super(type, name, healthPoints, attackPower, offensiveEquipment, defensiveEquipment);
        this.isBoss = isBoss;
    }
    
    /**
     * Checks if this enemy is a boss.
     * @return true if this is a boss enemy, false otherwise
     */
    public boolean isBoss() {
        return isBoss;
    }
    
    @Override
    public String interact() {
        return "Un " + getType() + " apparaît ! Préparez-vous au combat.";
    }
    
    @Override
    public String interact(fr.campus.d_and_d.characters.Character character) {
        if (character == null) {
            return interact();
        }
        
        // Combat logic with critical hits
        Menu menu = new Menu();
        Scanner scanner = new Scanner(System.in);
        TwentySidedDice criticalDice = new TwentySidedDice();
        
        while (getHealthPoints() > 0 && character.getHealthPoints() > 0) {
            String choice = menu.askPlayerString("Que voulez-vous faire ?",
                    "1. Attaquer",
                    "2. Fuir");
            
            if (choice.equals("1")) {
                // Roll for critical hit
                int criticalRoll = criticalDice.roll();
                int damageToEnemy = character.getAttackPower();
                
                if (criticalRoll == 20) {
                    // Critical hit: +2 damage
                    damageToEnemy += 2;
                    System.out.println("Coup critique ! Vous infligez " + damageToEnemy + " dégâts à l'ennemi.");
                } else if (criticalRoll == 1) {
                    // Critical miss: 0 damage
                    damageToEnemy = 0;
                    System.out.println("Échec critique ! Vous ratez votre attaque et infligez 0 dégâts.");
                } else {
                    System.out.println("Vous infligez " + damageToEnemy + " dégâts à l'ennemi.");
                }
                
                setHealthPoints(getHealthPoints() - damageToEnemy);
                
                if (getHealthPoints() <= 0) {
                    // Enemy defeated
                    OffensiveEquipment droppedEquipment = defeat();
                    String equipmentMessage = droppedEquipment != null 
                        ? "L'ennemi a laissé tomber : " + droppedEquipment.getName() + "." 
                        : "";
                    return "Vous avez vaincu l'ennemi ! " + equipmentMessage;
                }
                
                // Enemy attacks character with possible critical
                criticalRoll = criticalDice.roll();
                int damageToCharacter = getAttackPower();
                
                if (criticalRoll == 20) {
                    // Enemy critical hit: +2 damage
                    damageToCharacter += 2;
                    System.out.println("L'ennemi porte un coup critique ! Il vous inflige " + damageToCharacter + " dégâts.");
                } else if (criticalRoll == 1) {
                    // Enemy critical miss: 0 damage
                    damageToCharacter = 0;
                    System.out.println("L'ennemi rate son attaque et vous inflige 0 dégâts !");
                } else {
                    System.out.println("L'ennemi vous inflige " + damageToCharacter + " dégâts.");
                }
                
                character.setHealthPoints(character.getHealthPoints() - damageToCharacter);
                
                if (character.getHealthPoints() <= 0) {
                    return "Vous avez été vaincu par l'ennemi.";
                }
            } else if (choice.equals("2")) {
                // Flee logic
                SixSidedDice fleeDice = new SixSidedDice();
                int fleeSteps = fleeDice.roll();
                return "Vous avez fui le combat et pourriez reculer de " + fleeSteps + " cases.";
            }
        }
        
        return "Combat terminé.";
    }
    
    @Override
    public String getName() {
        return getType() + " (" + getHealthPoints() + " PV)";
    }
    
    /**
     * Simulates the enemy being defeated.
     * @return The equipment dropped by the enemy, or null if none
     */
    public OffensiveEquipment defeat() {
        // Bosses drop their equipment when defeated
        if (isBoss) {
            return getOffensiveEquipment();
        }
        // Regular enemies have a 50% chance to drop equipment
        return Math.random() > 0.5 ? getOffensiveEquipment() : null;
    }
}
