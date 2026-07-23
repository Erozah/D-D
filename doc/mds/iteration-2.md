# Itération 2 - Projet D&D (Java POO)

## Objectifs de l'itération
Cette itération vise à structurer le projet en suivant les bonnes pratiques de la programmation orientée objet (POO) en Java. Les principaux objectifs sont :
- Mise en place d'une architecture de classes cohérente.
- Création des personnages (Guerrier et Magicien) avec leurs équipements.
- Implémentation d'un menu interactif pour la création et la gestion des personnages.
- Ajout d'un plateau de jeu et gestion des déplacements du joueur.

---

## Structure du projet

### 1. Nom du projet et packages
- **Nom du projet** : À définir (exemple : `DungeonCrawler`).
- **Package principal** : `fr.campus.dungeoncrawler` (ou un autre nom cohérent).

### 2. Classes principales

#### Classe `Main`
- **Rôle** : Point d'entrée du programme.
- **Responsabilités** :
  - Lancer le jeu en instanciant la classe `Game`.
  - Gérer le flux principal du programme.
- **Contraintes** : La méthode `main` doit rester concise (environ 15 lignes).

#### Classe `Game`
- **Rôle** : Gérer la logique interne du jeu.
- **Responsabilités** :
  - Initialiser les joueurs et le plateau de jeu.
  - Coordonner les interactions entre les différentes classes (Menu, Character, Board, etc.).
  - Gérer le déroulement d'une partie (déplacements, tours de jeu).

#### Classe `Character`
- **Attributs** :
  - `type` (String) : "Warrior" ou "Wizard".
  - `name` (String) : Nom du personnage.
  - `health` (int) : Niveau de vie.
  - `attack` (int) : Niveau d'attaque.
  - `offensiveEquipment` (OffensiveEquipment) : Équipement offensif par défaut (arme ou sort).
  - `defensiveEquipment` (DefensiveEquipment) : Équipement défensif (bouclier ou potion).
- **Méthodes** :
  - Constructeur, `toString()`, getters et setters.
  - Méthodes pour afficher et modifier les informations du personnage.

#### Classe `OffensiveEquipment`
- **Attributs** :
  - `type` (String) : "Weapon" ou "Spell".
  - `attackLevel` (int) : Niveau d'attaque.
  - `name` (String) : Nom de l'équipement.
- **Méthodes** : Constructeur, `toString()`, getters et setters.

#### Classe `DefensiveEquipment`
- **Attributs** :
  - `type` (String) : "Shield" ou "Potion".
  - `defenseLevel` (int) : Niveau de défense.
  - `name` (String) : Nom de l'équipement.
- **Méthodes** : Constructeur, `toString()`, getters et setters.

#### Classe `Menu`
- **Rôle** : Gérer les interactions avec l'utilisateur.
- **Responsabilités** :
  - Afficher les messages à l'utilisateur.
  - Récupérer les saisies utilisateur (via `Scanner`).
  - Proposer des options pour :
    - Créer un nouveau personnage (choix du type et du nom).
    - Afficher/modifier les informations du personnage.
    - Démarrer une partie.
    - Quitter le jeu.

#### Classe `Board` (à créer)
- **Rôle** : Représenter le plateau de jeu.
- **Attributs** :
  - `size` (int) : Taille du plateau (par exemple, 64 cases).
  - `currentPosition` (int) : Position actuelle du joueur.
- **Méthodes** :
  - `movePlayer(int steps)` : Déplacer le joueur d'un nombre de cases donné.
  - `displayBoard()` : Afficher l'état actuel du plateau.

#### Classe `Dice` (à créer)
- **Rôle** : Simuler un dé pour les déplacements.
- **Méthodes** :
  - `roll()` : Retourne un nombre aléatoire (par exemple, entre 1 et 6).

---

## Fonctionnalités à implémenter

### 1. Menu principal
- **Options** :
  1. Créer un nouveau personnage.
  2. Afficher/modifier les informations du personnage.
  3. Démarrer une partie.
  4. Quitter le jeu.

### 2. Création de personnage
- L'utilisateur choisit entre "Warrior" et "Wizard".
- Saisie du nom du personnage.
- Attribution automatique des équipements par défaut (arme pour le Guerrier, sort pour le Magicien).

### 3. Plateau de jeu et déplacements
- Le joueur commence sur la case 1.
- À chaque tour, un dé est lancé pour déterminer le nombre de cases à avancer.
- Affichage de la progression (exemple : "Case 5 / 64").
- Fin de la partie lorsque le joueur atteint la dernière case.

---

## Bonnes pratiques

### 1. Encapsulation
- Tous les attributs doivent être `private`.
- Utiliser des getters et setters pour accéder aux attributs.
- Éviter le mot-clé `static` (sauf pour la méthode `main`).

### 2. Documentation
- **Diagramme de classe UML** : À créer et mettre à jour avec draw.io ou umletino.
- **Commentaires** : Documenter chaque classe et méthode.
- **Glossaire** : Lister les syntaxes utilisées (à déposer sur GitHub).

### 3. Organisation du code
- Chaque classe doit avoir une responsabilité unique.
- Éviter les classes trop longues ou trop complexes.
- Utiliser des noms de méthodes et d'attributs clairs et explicites.

---

## Livrables

1. **Diagramme de classe UML** : Représentant toutes les classes et leurs relations.
2. **Code source** : Classes `Main`, `Game`, `Character`, `OffensiveEquipment`, `DefensiveEquipment`, `Menu`, `Board`, et `Dice`.
3. **Glossaire** : Liste des syntaxes et concepts utilisés dans le projet.

---

## Ressources utiles
- [OpenClassrooms - Diagramme de classe](https://openclassrooms.com/)
- [Développez - Diagramme de classe](https://www.developpez.com/)
- [Lucidchart - Diagramme de classe UML](https://www.lucidchart.com/)
- [Visual Paradigm - Tutoriel UML](https://www.visual-paradigm.com/)

---

## Notes supplémentaires
- Cette itération ne comprend pas encore de combats ou d'ennemis. L'objectif est de poser les bases du jeu.
- Le code doit être propre, bien structuré et facile à maintenir pour les itérations suivantes.
