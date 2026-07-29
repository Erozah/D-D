package fr.campus.d_and_d.gameLogic;

import fr.campus.d_and_d.characters.Character;
import fr.campus.d_and_d.characters.Enemy;

public class HandleBattle {

    private final Character player;
    private final Enemy enemy;
    private final Menu menu = new Menu();
    private final TwentySidedDice criticalDice = new TwentySidedDice();
    public HandleBattle(Character player, Enemy enemy) {
        this.player = player;
        this.enemy = enemy;
    }

    public String startBattle() {
        while (enemy.getHealthPoints() > 0 && player.getHealthPoints() > 0) {
            String choice = menu.askPlayerString(
                    "Que voulez-vous faire ?",
                    "1. Attaquer",
                    "2. Fuir",
                    "3. Voir mes stats",
                    "4. Voir les stats de l'ennemi"
            );
            switch (choice) {
                case "1":
                    String result = attackTurn();
                    if (result != null) {
                        return result;
                    }
                    break;
                case "2":
                    String flee = flee();
                    if (flee != null) {
                        return flee;
                    }
                    break;

                case "3":
                    displayPlayerStats();
                    break;
                case "4":
                    displayEnemyStats();
                    break;
            }
        }
        return "Combat terminé.";
    }

    private String attackTurn() {
        attack(player, enemy);
        if (enemy.getHealthPoints() <= 0) {
            if (enemy.isBoss()) {
                GameState.getInstance().setBossDefeated(true);
                return "Vous avez vaincu le boss !";
            }
            return "Vous avez vaincu l'ennemi !";
        }
        attack(enemy, player);
        if (player.getHealthPoints() <= 0) {
            return "Vous avez été vaincu par l'ennemi.";
        }
        return null;
    }

    private void attack(Character attacker, Character defender) {
        int baseAttack = attacker.getBaseAttackPower();
        int equipmentAttack = attacker.getOffensiveEquipment().getAttackPower();
        int defense = defender.getDefensiveEquipment().getDefensePoints();

        int criticalRoll = criticalDice.roll();

        int totalAttack = baseAttack + equipmentAttack;
        int criticalBonus = 0;

        System.out.println("\n=== " + attacker.getName() + " attaque " + defender.getName() + " ===");
        System.out.println("Attaque de base : " + baseAttack);
        System.out.println("Bonus équipement offensif : +" + equipmentAttack);
        System.out.println("Attaque totale avant défense : " + totalAttack);
        System.out.println("Jet critique (D20) : " + criticalRoll);

        if (criticalRoll == 20) {
            criticalBonus = 2;
            System.out.println("Coup critique ! Bonus dégâts : +" + criticalBonus);
        } else if (criticalRoll == 1) {
            totalAttack = 0;
            System.out.println("Échec critique ! Les dégâts sont annulés.");
        }

        int damageBeforeDefense = totalAttack + criticalBonus;

        System.out.println("Dégâts avant défense : " + damageBeforeDefense);
        System.out.println("Défense équipement : -" + defense);

        int finalDamage = Math.max(0, damageBeforeDefense - defense);

        System.out.println("Dégâts infligés : " + finalDamage);

        int remainingHealth = Math.max(0, defender.getHealthPoints() - finalDamage);
        defender.setHealthPoints(remainingHealth);

        System.out.println("PV restants de " + defender.getName() + " : "
                + defender.getHealthPoints());
        System.out.println("==============================\n");
    }

    private void displayPlayerStats() {
        System.out.println("\n=== Vos Stats ===");
        System.out.println("Nom : " + player.getName());
        System.out.println("PV : " + player.getHealthPoints());
        System.out.println("Attaque : " + player.getAttackPower());
        System.out.println("=================\n");
    }

    private void displayEnemyStats() {
        System.out.println("\n=== Ennemi ===");
        System.out.println("Nom : " + enemy.getName());
        System.out.println("PV : " + enemy.getHealthPoints());
        System.out.println("Attaque : " + enemy.getAttackPower());
        System.out.println("=================\n");
    }

    private String flee() {
        if (enemy.isBoss()) {
            System.out.println("Impossible de fuir un boss !");
            return null;
        }
        SixSidedDice dice = new SixSidedDice();
        return "FUITE:" + dice.roll();
    }
}