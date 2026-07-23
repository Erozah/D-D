# Itération 7 - Projet D&D (Java POO)

## Objectifs de l'itération
Cette itération vise à implémenter la gestion des combats et de l'aléatoire dans le jeu. Les principaux objectifs sont :
- **Gestion du dé** : Création d'une classe `SixSidedDice` pour gérer les déplacements sur le plateau.
- **Combats simples** : Implémentation des règles de combat entre le personnage et les ennemis.
- **Combats au tour par tour** : Ajout de la possibilité de fuir ou d'attaquer à chaque tour.
- **Collections et import du jeu de données** : Utilisation d'un dé à 20 faces pour gérer les réussites et échecs critiques.
- **Interfaces** : Création d'une interface `Dice` pour mutualiser les fonctionnalités des dés.

---

## Étapes à suivre

### 1. Gestion du dé

#### 1.1. Créer une classe `SixSidedDice`
Créez une classe `SixSidedDice` qui retournera un résultat aléatoire entre 1 et 6 pour déterminer le déplacement du joueur sur le plateau.

```java
package fr.campus.d_and_d.gameLogic;

import java.util.Random;

/**
 * Represents a six-sided dice for determining player movement on the board.
 */
public class SixSidedDice {
    
    /**
     * Rolls the dice and returns a random result between 1 and 6.
     * @return An integer representing the dice roll result.
     */
    public int roll() {
        Random random = new Random();
        return random.nextInt(6) + 1;
    }
}
```

### 2. Combats simples

#### 2.1. Implémenter les règles de combat
Lorsqu'un personnage interagit avec un ennemi, implémentez les règles de combat suivantes :

1. **Attaque du personnage** : Le personnage frappe l'ennemi avec la force définie par son équipement, et le niveau de vie de l'ennemi diminue en conséquence.
2. **Vérification de la vie de l'ennemi** :
   - Si le niveau de vie de l'ennemi atteint 0, il meurt et est retiré du plateau.
   - Sinon, l'ennemi réplique et le niveau de vie du personnage diminue en fonction de la force de frappe de l'ennemi.

#### 2.2. Modifier la classe `EnemyCell`
Modifiez la méthode `interact()` de la classe `EnemyCell` pour implémenter les règles de combat :

```java
@Override
public String interact(Character character) {
    // L'ennemi attaque le personnage
    int damageToCharacter = enemy.getAttackPower();
    character.setHealthPoints(character.getHealthPoints() - damageToCharacter);
    
    // Vérifier si le personnage est toujours en vie
    if (character.getHealthPoints() <= 0) {
        return "Vous avez été vaincu par l'ennemi. Game Over.";
    }
    
    // Le personnage attaque l'ennemi
    int damageToEnemy = character.getAttackPower();
    enemy.setHealthPoints(enemy.getHealthPoints() - damageToEnemy);
    
    // Vérifier si l'ennemi est toujours en vie
    if (enemy.getHealthPoints() <= 0) {
        return "Vous avez vaincu l'ennemi !";
    } else {
        return "Vous avez combattu l'ennemi. Il vous reste " + character.getHealthPoints() + " points de vie.";
    }
}
```

#### 2.3. Gérer la fin de partie
Modifiez la classe `Game` pour gérer la fin de partie si le personnage est vaincu ou s'il atteint la dernière case du plateau :

```java
public void playTurn(Board board, Dice dice) {
    Scanner scanner = new Scanner(System.in);
    Menu menu = new Menu();
    menu.askPlayerString("Appuyez sur 'Entrée' pour lancer le dé...");
    int diceResult = dice.roll();
    try {
        int newPosition = board.getCurrentPosition() + diceResult;
        board.setCurrentPosition(newPosition);
        System.out.println(board.toString());
        Cell currentCell = board.getCurrentCell();
        String interactionResult = currentCell.interact(player);
        System.out.println(interactionResult);
        
        // Vérifier si le personnage est toujours en vie
        if (player.getHealthPoints() <= 0) {
            System.out.println("Game Over. Vous avez été vaincu.");
            System.exit(0);
        }
        
        // Vérifier si le joueur a atteint la dernière case
        if (board.getCurrentPosition() >= board.getMaxPosition()) {
            endGame();
            System.exit(0);
        }
    } catch (OutOfBoardException e) {
        System.out.println("Erreur : " + e.getMessage());
    }
}
```

### 3. Combats au tour par tour

#### 3.1. Implémenter les règles de combat au tour par tour
Modifiez la méthode `interact()` de la classe `EnemyCell` pour permettre au joueur de choisir entre attaquer et fuir :

```java
@Override
public String interact(Character character) {
    Scanner scanner = new Scanner(System.in);
    Menu menu = new Menu();
    
    while (enemy.getHealthPoints() > 0 && character.getHealthPoints() > 0) {
        String choice = menu.askPlayerString("Que voulez-vous faire ?\n1. Attaquer\n2. Fuir");
        
        if (choice.equals("1")) {
            // Le personnage attaque l'ennemi
            int damageToEnemy = character.getAttackPower();
            enemy.setHealthPoints(enemy.getHealthPoints() - damageToEnemy);
            System.out.println("Vous avez infligé " + damageToEnemy + " dégâts à l'ennemi.");
            
            // Vérifier si l'ennemi est toujours en vie
            if (enemy.getHealthPoints() <= 0) {
                return "Vous avez vaincu l'ennemi !";
            }
            
            // L'ennemi attaque le personnage
            int damageToCharacter = enemy.getAttackPower();
            character.setHealthPoints(character.getHealthPoints() - damageToCharacter);
            System.out.println("L'ennemi vous a infligé " + damageToCharacter + " dégâts.");
            
            // Vérifier si le personnage est toujours en vie
            if (character.getHealthPoints() <= 0) {
                return "Vous avez été vaincu par l'ennemi. Game Over.";
            }
        } else if (choice.equals("2")) {
            // Le personnage fuit
            SixSidedDice fleeDice = new SixSidedDice();
            int fleeSteps = fleeDice.roll();
            int newPosition = board.getCurrentPosition() - fleeSteps;
            board.setCurrentPosition(newPosition);
            return "Vous avez fui le combat et reculé de " + fleeSteps + " cases.";
        }
    }
    
    return "Combat terminé.";
}
```

### 4. Collections et import du jeu de données

#### 4.1. Implémenter la logique des réussites et échecs critiques
Ajoutez un dé à 20 faces pour gérer les réussites et échecs critiques :

```java
package fr.campus.d_and_d.gameLogic;

import java.util.Random;

/**
 * Represents a twenty-sided dice for determining critical hits and misses.
 */
public class TwentySidedDice {
    
    /**
     * Rolls the dice and returns a random result between 1 and 20.
     * @return An integer representing the dice roll result.
     */
    public int roll() {
        Random random = new Random();
        return random.nextInt(20) + 1;
    }
}
```

#### 4.2. Modifier les règles de combat pour inclure les critiques
Modifiez la méthode `interact()` de la classe `EnemyCell` pour inclure les réussites et échecs critiques :

```java
@Override
public String interact(Character character) {
    Scanner scanner = new Scanner(System.in);
    Menu menu = new Menu();
    TwentySidedDice criticalDice = new TwentySidedDice();
    
    while (enemy.getHealthPoints() > 0 && character.getHealthPoints() > 0) {
        String choice = menu.askPlayerString("Que voulez-vous faire ?\n1. Attaquer\n2. Fuir");
        
        if (choice.equals("1")) {
            // Lancer le dé pour déterminer si c'est un coup critique
            int criticalRoll = criticalDice.roll();
            int damageToEnemy = character.getAttackPower();
            
            if (criticalRoll == 20) {
                // Réussite critique : +2 à la force d'attaque
                damageToEnemy += 2;
                System.out.println("Réussite critique ! Vous infligez " + damageToEnemy + " dégâts.");
            } else if (criticalRoll == 1) {
                // Échec critique : 0 dégâts
                damageToEnemy = 0;
                System.out.println("Échec critique ! Vous infligez 0 dégâts.");
            } else {
                System.out.println("Vous infligez " + damageToEnemy + " dégâts.");
            }
            
            enemy.setHealthPoints(enemy.getHealthPoints() - damageToEnemy);
            
            // Vérifier si l'ennemi est toujours en vie
            if (enemy.getHealthPoints() <= 0) {
                return "Vous avez vaincu l'ennemi !";
            }
            
            // Lancer le dé pour déterminer si l'ennemi a un coup critique
            criticalRoll = criticalDice.roll();
            int damageToCharacter = enemy.getAttackPower();
            
            if (criticalRoll == 20) {
                // Réussite critique : +2 à la force d'attaque
                damageToCharacter += 2;
                System.out.println("L'ennemi a une réussite critique ! Il vous inflige " + damageToCharacter + " dégâts.");
            } else if (criticalRoll == 1) {
                // Échec critique : 0 dégâts
                damageToCharacter = 0;
                System.out.println("L'ennemi a un échec critique ! Il vous inflige 0 dégâts.");
            } else {
                System.out.println("L'ennemi vous inflige " + damageToCharacter + " dégâts.");
            }
            
            character.setHealthPoints(character.getHealthPoints() - damageToCharacter);
            
            // Vérifier si le personnage est toujours en vie
            if (character.getHealthPoints() <= 0) {
                return "Vous avez été vaincu par l'ennemi. Game Over.";
            }
        } else if (choice.equals("2")) {
            // Le personnage fuit
            SixSidedDice fleeDice = new SixSidedDice();
            int fleeSteps = fleeDice.roll();
            int newPosition = board.getCurrentPosition() - fleeSteps;
            board.setCurrentPosition(newPosition);
            return "Vous avez fui le combat et reculé de " + fleeSteps + " cases.";
        }
    }
    
    return "Combat terminé.";
}
```

### 5. Utilisation des interfaces

#### 5.1. Créer une interface `Dice`
Créez une interface `Dice` pour mutualiser les fonctionnalités des dés :

```java
package fr.campus.d_and_d.gameLogic;

/**
 * Interface representing a dice with a method to roll it.
 */
public interface Dice {
    
    /**
     * Rolls the dice and returns a random result.
     * @return An integer representing the dice roll result.
     */
    int roll();
}
```

#### 5.2. Implémenter l'interface `Dice`
Modifiez les classes `SixSidedDice` et `TwentySidedDice` pour qu'elles implémentent l'interface `Dice` :

```java
public class SixSidedDice implements Dice {
    
    @Override
    public int roll() {
        Random random = new Random();
        return random.nextInt(6) + 1;
    }
}
```

```java
public class TwentySidedDice implements Dice {
    
    @Override
    public int roll() {
        Random random = new Random();
        return random.nextInt(20) + 1;
    }
}
```

#### 5.3. Utiliser l'interface `Dice` dans le code
Modifiez le code pour utiliser l'interface `Dice` de manière générique :

```java
public void playTurn(Board board, Dice dice) {
    Scanner scanner = new Scanner(System.in);
    Menu menu = new Menu();
    menu.askPlayerString("Appuyez sur 'Entrée' pour lancer le dé...");
    int diceResult = dice.roll();
    
    try {
        int newPosition = board.getCurrentPosition() + diceResult;
        board.setCurrentPosition(newPosition);
        System.out.println(board.toString());
        Cell currentCell = board.getCurrentCell();
        String interactionResult = currentCell.interact(player);
        System.out.println(interactionResult);
        
        // Vérifier si le personnage est toujours en vie
        if (player.getHealthPoints() <= 0) {
            System.out.println("Game Over. Vous avez été vaincu.");
            System.exit(0);
        }
        
        // Vérifier si le joueur a atteint la dernière case
        if (board.getCurrentPosition() >= board.getMaxPosition()) {
            endGame();
            System.exit(0);
        }
    } catch (OutOfBoardException e) {
        System.out.println("Erreur : " + e.getMessage());
    }
}
```

---

## Livrables

1. **Code source** :
   - Classe `SixSidedDice` pour gérer les déplacements sur le plateau.
   - Classe `TwentySidedDice` pour gérer les réussites et échecs critiques.
   - Interface `Dice` pour mutualiser les fonctionnalités des dés.
   - Mise à jour de la classe `EnemyCell` pour implémenter les règles de combat.
   - Mise à jour de la classe `Game` pour gérer la fin de partie.

2. **Glossaire** :
   - Ajout des nouvelles syntaxes utilisées (interfaces, `instanceof`, etc.).

---

## Ressources utiles

### Java
- [CodeCademy - Data Structures](https://www.codecademy.com/en/courses/learn-java/lessons/data-structures)
- [Collections d'objets - Zeste de Savoir](https://zestedesavoir.com/)
- [Interfaces (w3c) - Java Interface](https://www.w3schools.com/java/java_interface.asp)
- [Baeldung - Interface vs Abstract](https://www.baeldung.com/java-interface-vs-abstract-class)
- [Tutoriel d'Oracle pour Java](https://docs.oracle.com/javase/tutorial/java/IandI/interfaceDef.html)
- [Java instanceof - GeeksforGeeks](https://www.geeksforgeeks.org/java-instanceof-and-its-applications/)

---

## Notes supplémentaires

- **Gestion des erreurs** : Assurez-vous de gérer correctement les exceptions pour éviter les plantages.
- **Tests** : Testez chaque méthode pour vous assurer qu'elle fonctionne correctement.
- **Architecture** : Une bonne architecture de code permettra d'ajouter facilement de nouvelles fonctionnalités.
