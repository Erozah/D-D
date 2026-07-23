# Itération 6 - Projet D&D (Java POO)

## Objectifs de l'itération
Cette itération vise à enrichir le plateau de jeu avec une variété d'ennemis et d'équipements, ainsi qu'à implémenter des interactions fines entre les personnages et les cases. Les principaux objectifs sont :
- **Diversification des ennemis et des équipements** : Ajout de nouveaux types d'ennemis (Dragons, Sorciers, Gobelins) et d'équipements (Massues, Épées, Éclairs, Boules de feu, Potions standards, Grandes potions).
- **Interaction entre personnages et cases** : Implémentation de la méthode `interact()` pour gérer les interactions spécifiques en fonction du type de personnage (Guerrier ou Magicien).
- **Plateau complet** : Création d'un plateau de 64 cases avec une répartition spécifique des ennemis et des équipements.
- **Cases aléatoires** : Placement aléatoire des cases tout en conservant le nombre d'ennemis et d'équipements.

---

## Étapes à suivre

### 1. Diversification des ennemis et des caisses surprises

#### 1.1. Créer de nouvelles classes pour les ennemis
Créez les classes suivantes en utilisant l'héritage approprié :

- **Dragon** : Un ennemi puissant avec des points de vie et une attaque élevés.
  - Hérite de `Enemy`.
  - Implémentez la méthode `toString()` pour afficher les caractéristiques du dragon.

- **Sorcier** : Un ennemi avec des attaques magiques.
  - Hérite de `Enemy`.
  - Implémentez la méthode `toString()` pour afficher les caractéristiques du sorcier.

- **Gobelin** : Un ennemi faible mais nombreux.
  - Hérite de `Enemy`.
  - Implémentez la méthode `toString()` pour afficher les caractéristiques du gobelin.

#### 1.2. Créer de nouvelles classes pour les équipements
Créez les classes suivantes en utilisant l'héritage approprié :

- **Massue** : Une arme puissante mais lente.
  - Hérite de `Weapon`.
  - Implémentez la méthode `toString()` pour afficher les caractéristiques de la massue.

- **Épée** : Une arme équilibrée.
  - Hérite de `Weapon`.
  - Implémentez la méthode `toString()` pour afficher les caractéristiques de l'épée.

- **Éclair** : Un sort de foudre.
  - Hérite de `Spell`.
  - Implémentez la méthode `toString()` pour afficher les caractéristiques de l'éclair.

- **Boule de feu** : Un sort de feu puissant.
  - Hérite de `Spell`.
  - Implémentez la méthode `toString()` pour afficher les caractéristiques de la boule de feu.

- **Potion standard** : Une potion qui restaure un nombre modéré de points de vie.
  - Hérite de `Potion`.
  - Implémentez la méthode `toString()` pour afficher les caractéristiques de la potion standard.

- **Grande potion** : Une potion qui restaure un grand nombre de points de vie.
  - Hérite de `Potion`.
  - Implémentez la méthode `toString()` pour afficher les caractéristiques de la grande potion.

#### 1.3. Mettre à jour le diagramme de classe
Ajoutez les nouvelles classes au diagramme de classe et mettez à jour les relations d'héritage.

### 2. Agrandir le plateau à 10 cases

#### 2.1. Modifier la classe `Board`
Modifiez la méthode `initializeBoard()` pour créer un plateau de 10 cases avec une instance de chaque nouvelle classe :

```java
public void initializeBoard() {
    cells.add(new EmptyCell());          // Case 1
    cells.add(new WeaponCell(new Massue("Massue", "Massue en bois", 8)));  // Case 2
    cells.add(new EnemyCell(new Gobelin("Gobelin", 10, 3)));  // Case 3
    cells.add(new WeaponCell(new Épée("Épée", "Épée en acier", 6)));  // Case 4
    cells.add(new EnemyCell(new Sorcier("Sorcier", 15, 5)));  // Case 5
    cells.add(new PotionCell(new PotionStandard("Potion", "Potion de vie", 10)));  // Case 6
    cells.add(new EnemyCell(new Dragon("Dragon", 30, 10)));  // Case 7
    cells.add(new SpellCell(new Éclair("Sort", "Éclair", 7)));  // Case 8
    cells.add(new PotionCell(new GrandePotion("Potion", "Grande potion de vie", 20)));  // Case 9
    cells.add(new SpellCell(new BouleDeFeu("Sort", "Boule de feu", 9)));  // Case 10
}
```

#### 2.2. Tester le plateau
Parcourez le plateau et appelez la méthode `toString()` de chaque case pour vérifier que les résultats sont cohérents.

### 3. Interaction fine entre les personnages et les cases

#### 3.1. Modifier la signature de la méthode `interact()`
Modifiez la signature de la méthode `interact()` dans la classe `Cell` pour accepter un paramètre `Character` :

```java
public abstract String interact(Character character);
```

#### 3.2. Mettre à jour les classes filles
Mettez à jour les méthodes `interact()` dans toutes les classes filles de `Cell` pour accepter un paramètre `Character` :

- **EmptyCell** : Aucune interaction, mais affichez un message.
- **EnemyCell** : Gestion du combat entre le personnage et l'ennemi.
- **WeaponCell** : Le personnage ramasse l'arme (uniquement si c'est un Guerrier).
- **SpellCell** : Le personnage ramasse le sort (uniquement si c'est un Magicien).
- **PotionCell** : Le personnage utilise la potion pour restaurer des points de vie.

#### 3.3. Utiliser `instanceof` pour gérer les interactions
Utilisez le mot-clé `instanceof` pour adapter le comportement en fonction du type de personnage :

```java
@Override
public String interact(Character character) {
    if (character instanceof Warrior) {
        // Logique pour un Guerrier
        return "Le guerrier ramasse l'arme.";
    } else if (character instanceof Wizard) {
        // Logique pour un Magicien
        return "Le magicien ne peut pas ramasser cette arme.";
    } else {
        return "Aucune interaction.";
    }
}
```

#### 3.4. Implémenter les comportements spécifiques
Implémentez les comportements appropriés pour chaque type de case :

- **EmptyCell** : Aucune action.
- **EnemyCell** : Combat entre le personnage et l'ennemi.
- **WeaponCell** : Augmentation de l'attaque du Guerrier.
- **SpellCell** : Augmentation de l'attaque du Magicien.
- **PotionCell** : Restauration des points de vie.

### 4. Créer le plateau complet

#### 4.1. Définir la répartition des cases
Créez un plateau de 64 cases avec la répartition suivante :

- **24 cases avec des ennemis** :
  - 4 Dragons (cases 45, 52, 56 et 62)
  - 10 Sorciers (cases 10, 20, 25, 32, 35, 36, 37, 40, 44 et 47)
  - 10 Gobelins (cases 3, 6, 9, 12, 15, 18, 21, 24, 27 et 30)

- **24 cases bonus** :
  - 5 Massues (cases 2, 11, 5, 22, 38)
  - 4 Épées (cases 19, 26, 42 et 53)
  - 5 Sorts "éclair" (cases 1, 4, 8, 17 et 23)
  - 2 Sorts "boules de feu" (cases 48 et 49)
  - 6 Potions standards (cases 7, 13, 31, 33, 39, 43)
  - 2 Grandes potions (cases 28, 41)

- **16 cases vides** : Les cases restantes sont vides.

#### 4.2. Implémenter la méthode `initializeBoard()`
Modifiez la méthode `initializeBoard()` pour créer le plateau complet :

```java
public void initializeBoard() {
    // Initialiser toutes les cases à EmptyCell
    for (int i = 0; i < MAX_POSITION; i++) {
        cells.add(new EmptyCell());
    }
    
    // Ajouter les ennemis
    cells.set(2, new EnemyCell(new Gobelin("Gobelin", 10, 3)));  // Case 3
    cells.set(5, new EnemyCell(new Gobelin("Gobelin", 10, 3)));  // Case 6
    cells.set(8, new EnemyCell(new Gobelin("Gobelin", 10, 3)));  // Case 9
    cells.set(11, new EnemyCell(new Gobelin("Gobelin", 10, 3))); // Case 12
    cells.set(14, new EnemyCell(new Gobelin("Gobelin", 10, 3))); // Case 15
    cells.set(17, new EnemyCell(new Gobelin("Gobelin", 10, 3))); // Case 18
    cells.set(20, new EnemyCell(new Gobelin("Gobelin", 10, 3))); // Case 21
    cells.set(23, new EnemyCell(new Gobelin("Gobelin", 10, 3))); // Case 24
    cells.set(26, new EnemyCell(new Gobelin("Gobelin", 10, 3))); // Case 27
    cells.set(29, new EnemyCell(new Gobelin("Gobelin", 10, 3))); // Case 30
    cells.set(9, new EnemyCell(new Sorcier("Sorcier", 15, 5)));   // Case 10
    cells.set(19, new EnemyCell(new Sorcier("Sorcier", 15, 5)));  // Case 20
    cells.set(24, new EnemyCell(new Sorcier("Sorcier", 15, 5)));  // Case 25
    cells.set(31, new EnemyCell(new Sorcier("Sorcier", 15, 5)));  // Case 32
    cells.set(34, new EnemyCell(new Sorcier("Sorcier", 15, 5)));  // Case 35
    cells.set(35, new EnemyCell(new Sorcier("Sorcier", 15, 5)));  // Case 36
    cells.set(36, new EnemyCell(new Sorcier("Sorcier", 15, 5)));  // Case 37
    cells.set(39, new EnemyCell(new Sorcier("Sorcier", 15, 5)));  // Case 40
    cells.set(43, new EnemyCell(new Sorcier("Sorcier", 15, 5)));  // Case 44
    cells.set(46, new EnemyCell(new Sorcier("Sorcier", 15, 5)));  // Case 47
    cells.set(44, new EnemyCell(new Dragon("Dragon", 30, 10)));   // Case 45
    cells.set(51, new EnemyCell(new Dragon("Dragon", 30, 10)));   // Case 52
    cells.set(55, new EnemyCell(new Dragon("Dragon", 30, 10)));   // Case 56
    cells.set(61, new EnemyCell(new Dragon("Dragon", 30, 10)));   // Case 62
    
    // Ajouter les équipements
    cells.set(1, new WeaponCell(new Massue("Massue", "Massue en bois", 8)));  // Case 2
    cells.set(4, new SpellCell(new Éclair("Sort", "Éclair", 7)));            // Case 5
    cells.set(10, new WeaponCell(new Massue("Massue", "Massue en bois", 8))); // Case 11
    cells.set(18, new WeaponCell(new Épée("Épée", "Épée en acier", 6)));    // Case 19
    cells.set(21, new SpellCell(new Éclair("Sort", "Éclair", 7)));            // Case 22
    cells.set(25, new WeaponCell(new Massue("Massue", "Massue en bois", 8))); // Case 26
    cells.set(30, new PotionCell(new PotionStandard("Potion", "Potion de vie", 10))); // Case 31
    cells.set(32, new PotionCell(new PotionStandard("Potion", "Potion de vie", 10))); // Case 33
    cells.set(37, new WeaponCell(new Massue("Massue", "Massue en bois", 8))); // Case 38
    cells.set(41, new PotionCell(new GrandePotion("Potion", "Grande potion de vie", 20))); // Case 42
    cells.set(45, new SpellCell(new Éclair("Sort", "Éclair", 7)));            // Case 46
    cells.set(48, new SpellCell(new BouleDeFeu("Sort", "Boule de feu", 9))); // Case 49
    cells.set(52, new WeaponCell(new Épée("Épée", "Épée en acier", 6)));    // Case 53
}
```

#### 4.3. Tester le plateau
Parcourez le plateau et appelez la méthode `interact()` de chaque case pour vérifier que les interactions fonctionnent comme prévu.

### 5. Cases aléatoires

#### 5.1. Modifier la méthode `initializeBoard()` pour un placement aléatoire
Modifiez la méthode `initializeBoard()` pour placer les cases de manière aléatoire tout en conservant le nombre d'ennemis et d'équipements :

```java
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

public void initializeBoard() {
    // Créer une liste de toutes les cases
    List<Cell> allCells = new ArrayList<>();
    
    // Ajouter les ennemis
    for (int i = 0; i < 4; i++) {
        allCells.add(new EnemyCell(new Dragon("Dragon", 30, 10)));
    }
    for (int i = 0; i < 10; i++) {
        allCells.add(new EnemyCell(new Sorcier("Sorcier", 15, 5)));
    }
    for (int i = 0; i < 10; i++) {
        allCells.add(new EnemyCell(new Gobelin("Gobelin", 10, 3)));
    }
    
    // Ajouter les équipements
    for (int i = 0; i < 5; i++) {
        allCells.add(new WeaponCell(new Massue("Massue", "Massue en bois", 8)));
    }
    for (int i = 0; i < 4; i++) {
        allCells.add(new WeaponCell(new Épée("Épée", "Épée en acier", 6)));
    }
    for (int i = 0; i < 5; i++) {
        allCells.add(new SpellCell(new Éclair("Sort", "Éclair", 7)));
    }
    for (int i = 0; i < 2; i++) {
        allCells.add(new SpellCell(new BouleDeFeu("Sort", "Boule de feu", 9)));
    }
    for (int i = 0; i < 6; i++) {
        allCells.add(new PotionCell(new PotionStandard("Potion", "Potion de vie", 10)));
    }
    for (int i = 0; i < 2; i++) {
        allCells.add(new PotionCell(new GrandePotion("Potion", "Grande potion de vie", 20)));
    }
    
    // Ajouter les cases vides
    for (int i = 0; i < 16; i++) {
        allCells.add(new EmptyCell());
    }
    
    // Mélanger les cases
    Collections.shuffle(allCells);
    
    // Ajouter les cases au plateau
    cells.addAll(allCells);
}
```

#### 5.2. Mettre à jour le diagramme de classe
Mettez à jour le diagramme de classe pour inclure les nouvelles classes et leurs relations.

---

## Livrables

1. **Code source** :
   - Classes `Dragon`, `Sorcier`, et `Gobelin` pour les ennemis.
   - Classes `Massue`, `Épée`, `Éclair`, `BouleDeFeu`, `PotionStandard`, et `GrandePotion` pour les équipements.
   - Mise à jour de la classe `Board` pour inclure le plateau complet.
   - Mise à jour des méthodes `interact()` pour gérer les interactions entre les personnages et les cases.

2. **Diagramme de classe** :
   - Mise à jour du diagramme de classe pour inclure les nouvelles classes et leurs relations.

3. **Glossaire** :
   - Ajout des nouvelles syntaxes utilisées (par exemple, `instanceof`).

---

## Ressources utiles

### Java
- [CodeCademy - Data Structures](https://www.codecademy.com/en/courses/learn-java/lessons/data-structures)
- [Collections d'objets - Zeste de Savoir](https://zestedesavoir.com/)
- [Java instanceof - GeeksforGeeks](https://www.geeksforgeeks.org/java-instanceof-and-its-applications/)

### UML
- [Tutoriel UML - Lucidchart](https://www.lucidchart.com/)
- [UML Class Diagrams - Oracle](https://www.oracle.com/)

---

## Notes supplémentaires

- **Héritage** : Utilisez l'héritage pour éviter la duplication de code.
- **Polymorphisme** : Utilisez le polymorphisme pour gérer les interactions de manière générique.
- **Tests** : Testez chaque méthode pour vous assurer qu'elle fonctionne correctement.
