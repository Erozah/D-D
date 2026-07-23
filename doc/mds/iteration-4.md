# Itération 4 - Projet D&D (Java POO)

## Objectifs de l'itération
Cette itération se concentre sur la mise en place du plateau de jeu et des interactions entre le joueur et les différentes cases. Les principaux objectifs sont :
- **Création d'un plateau de jeu** : Représenté par une `ArrayList` de cases (`Cell`).
- **Gestion des interactions** : Le joueur interagit avec les cases (ennemis, bonus, cases vides).
- **Organisation des couches d'abstraction** : Utilisation de classes abstraites pour représenter les différentes types de cases.
- **Simplification initiale** : Commencer avec un plateau simplifié (4 cases) et un dé pipé (toujours 1).

---

## Structure du projet

### 1. Classe `Game`
La classe `Game` est au cœur de cette itération. Elle gère le plateau de jeu et les interactions du joueur.

#### Attributs de la classe `Game`
- `playerPosition` (int) : Position actuelle du joueur sur le plateau.
- `board` (ArrayList<Cell>) : Représente le plateau de jeu.
- `player` (Character) : Le personnage contrôlé par le joueur.
- `gameStatus` (String) : État du jeu (par exemple, "en cours", "terminé", "personnage mort").

#### Méthodes de la classe `Game`
- `playTurn()` : Méthode principale pour gérer un tour de jeu.
  - Lance le dé.
  - Déplace le joueur.
  - Gère les interactions avec la case actuelle.
  - Met à jour l'état du jeu.
- `initializeBoard()` : Initialise le plateau de jeu avec les cases prédéfinies.
- `movePlayer(int steps)` : Déplace le joueur d'un nombre de cases donné.
- `interactWithCell(Cell cell)` : Gère l'interaction entre le joueur et une case.

---

### 2. Classe abstraite `Cell`
La classe abstraite `Cell` représente une case du plateau de jeu. Elle permet de définir un comportement commun pour tous les types de cases.

#### Attributs de la classe `Cell`
- `position` (int) : Position de la case sur le plateau.

#### Méthodes de la classe `Cell`
- `interact(Character player)` : Méthode abstraite pour gérer l'interaction avec le joueur.
- `toString()` : Retourne une description de la case.

---

### 3. Classes concrètes héritant de `Cell`

#### Classe `EmptyCell`
- **Rôle** : Représente une case vide.
- **Méthodes** :
  - `interact(Character player)` : Affiche un message indiquant que la case est vide.
  - `toString()` : Retourne "Case vide".

#### Classe `EnemyCell`
- **Rôle** : Représente une case avec un ennemi.
- **Attributs** :
  - `enemy` (Enemy) : L'ennemi présent sur la case.
- **Méthodes** :
  - `interact(Character player)` : Lance un combat entre le joueur et l'ennemi.
  - `toString()` : Retourne "Case avec un ennemi : [nom de l'ennemi]".

#### Classe `WeaponCell`
- **Rôle** : Représente une case avec une arme.
- **Attributs** :
  - `weapon` (Weapon) : L'arme présente sur la case.
- **Méthodes** :
  - `interact(Character player)` : Permet au joueur de ramasser l'arme.
  - `toString()` : Retourne "Case avec une arme : [nom de l'arme]".

#### Classe `PotionCell`
- **Rôle** : Représente une case avec une potion.
- **Attributs** :
  - `potion` (Potion) : La potion présente sur la case.
- **Méthodes** :
  - `interact(Character player)` : Permet au joueur de ramasser la potion.
  - `toString()` : Retourne "Case avec une potion : [nom de la potion]".

---

### 4. Classe `Enemy`
- **Rôle** : Représente un ennemi sur le plateau.
- **Attributs** :
  - `name` (String) : Nom de l'ennemi.
  - `health` (int) : Niveau de vie de l'ennemi.
  - `attack` (int) : Niveau d'attaque de l'ennemi.
- **Méthodes** :
  - `attack(Character player)` : Attaque le joueur.
  - `takeDamage(int damage)` : Réduit les points de vie de l'ennemi.
  - `isAlive()` : Retourne `true` si l'ennemi est encore en vie.

---

## Plateau de jeu simplifié

### 1. Initialisation du plateau
Le plateau est initialisé avec 4 cases pour simplifier les tests :

| Position | Type de case       | Contenu                     |
|----------|--------------------|-----------------------------|
| 1        | `EmptyCell`        | Case vide                   |
| 2        | `EnemyCell`        | Ennemi (par exemple, "Gobelin") |
| 3        | `WeaponCell`       | Arme (par exemple, "Épée en acier") |
| 4        | `PotionCell`       | Potion (par exemple, "Potion de vie") |

### 2. Dé pipé
Pour simplifier les tests, le dé renvoie toujours 1. Cela permet de contrôler précisément les déplacements du joueur.

---

## Fonctionnalités à implémenter

### 1. Déplacement du joueur
- Le joueur commence à la position 1.
- À chaque tour, le dé est lancé (toujours 1 pour cette itération).
- Le joueur avance d'une case et interagit avec la case actuelle.

### 2. Interactions avec les cases
- **Case vide** : Aucun effet.
- **Case avec un ennemi** : Lance un combat.
- **Case avec une arme** : Le joueur peut ramasser l'arme pour augmenter son niveau d'attaque.
- **Case avec une potion** : Le joueur peut ramasser la potion pour restaurer ses points de vie.

### 3. Combat simplifié
- Le combat se déroule en un tour :
  - Le joueur attaque l'ennemi.
  - L'ennemi attaque le joueur.
  - Si l'ennemi est vaincu, il est retiré de la case.
  - Si le joueur meurt, le jeu se termine.

---

## Bonnes pratiques

### 1. Organisation du code
- **Packages** : Organiser les classes en packages cohérents (par exemple, `fr.campus.dungeoncrawler.cells`, `fr.campus.dungeoncrawler.entities`).
- **Responsabilité unique** : Chaque classe doit avoir une responsabilité claire.

### 2. Encapsulation
- **Visibilité** : Tous les attributs doivent être `private`.
- **Accès** : Utiliser des getters et setters pour accéder aux attributs.

### 3. Polymorphisme
- **Utilisation** : Utiliser le polymorphisme pour traiter les différentes types de cases de manière uniforme.

---

## Livrables

1. **Code source** :
   - Classe `Game` mise à jour avec les méthodes `playTurn()`, `initializeBoard()`, et `interactWithCell()`.
   - Classe abstraite `Cell` et ses classes concrètes (`EmptyCell`, `EnemyCell`, `WeaponCell`, `PotionCell`).
   - Classe `Enemy`.
   - Mise à jour des classes existantes (`Main`, `Menu`, etc.).

2. **Diagramme UML** : Mise à jour du diagramme de classe pour refléter la nouvelle structure.

3. **Glossaire** : Liste des syntaxes et concepts utilisés (classes abstraites, `ArrayList`, polymorphisme).

---

## Ressources utiles

### ArrayList et collections
- [Java Programming MOOC - Class Diagrams](https://java-programming.mooc.fi/part-11/1-class-diagrams)
- [CodeCademy - Data Structures](https://www.codecademy.com/en/courses/learn-java/lessons/data-structures)
- [Zeste de Savoir - Collections d'objets](https://zestedesavoir.com/)

### UML
- [Tutoriel UML - Diagrammes de classe](https://www.lucidchart.com/pages/fr/diagramme-de-classes-uml)

---

## Notes supplémentaires
- Cette itération introduit des interactions basiques entre le joueur et le plateau.
- Le plateau est simplifié pour faciliter les tests et la compréhension.
- Les combats et les interactions seront enrichis dans les itérations suivantes.
