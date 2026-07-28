# Donjons et Dragons - Jeu de Plateau

**Un jeu de plateau inspiré de l'univers Donjons et Dragons, développé en Java.**

---

## 📖 Description

Ce projet est une implémentation simplifiée d'un jeu de plateau de type Donjons et Dragons. Les joueurs peuvent créer un personnage (Guerrier ou Magicien), puis avancer sur un plateau de 64 cases en lançant un dé. Le but est d'atteindre la dernière case pour gagner la partie. Le plateau contient des cases spéciales comme des ennemis, des armes, des potions et un boss final.

---

## 🎮 Fonctionnalités

- **Création de personnage** : Choisissez entre un Guerrier ou un Magicien, chacun avec des caractéristiques uniques.
- **Plateau de jeu** : Un plateau de 64 cases où le joueur avance en lançant un dé.
- **Système de dé** : Un dé virtuel génère un nombre aléatoire entre 1 et 6 pour déterminer le déplacement.
- **Interface utilisateur** : Menu interactif pour naviguer dans le jeu.
- **Cases spéciales** :
  - **MysteryBox** : Cases mystérieuses contenant des équipements ou des ennemis
  - **EnemyCell** : Case avec un ennemi à combattre (Gobelin, Orc, Sorcier, Dragon)
  - **WeaponCell** : Case avec une arme à ramasser
  - **PotionCell** : Case avec une potion pour restaurer des points de vie
  - **BossCell** : Case avec un boss final à vaincre pour gagner la partie.

### Fonctionnalités avancées :
- **Système de sauvegarde** : Sauvegarde automatique des personnages dans une base de données MySQL
- **Chargement de personnage** : Possibilité de charger un personnage précédemment sauvegardé
- **Gestion des combats** : Système de combat au tour par tour avec possibilité de fuir
- **Validation des entrées** : Gestion robuste des erreurs et validation des données
- **Documentation complète** : JavaDoc exhaustive pour toutes les classes et méthodes principales

---

## 🚀 Démarrage

### Prérequis

- Java 8 ou supérieur
- Un IDE (IntelliJ IDEA, Eclipse, etc.) ou un terminal pour compiler et exécuter le code.

### Installation

1. Clone ce dépôt :
   ```bash
   git clone https://github.com/Erozah/D-D.git
   ```

2. Accède au répertoire du projet :
   ```bash
   cd D-D
   ```

3. Compile le projet :
   ```bash
   javac -d bin src/fr/campus/d_and_d/Main.java src/fr/campus/d_and_d/characters/*.java src/fr/campus/d_and_d/board/*.java src/fr/campus/d_and_d/items/*.java src/fr/campus/d_and_d/gameLogic/*.java src/fr/campus/d_and_d/db/*.java
   ```

4. Exécute le jeu :
   ```bash
   java -cp bin fr.campus.d_and_d.Main
   ```

---

## 📂 Structure du Projet

```
DD/
├── src/
│   ├── fr/campus/d_and_d/
│   │   ├── Main.java              # Point d'entrée du jeu
│   │   ├── gameLogic/
│   │   │   ├── Game.java          # Logique principale du jeu
│   │   │   ├── Menu.java          # Gestion du menu utilisateur
│   │   │   ├── Dice.java          # Interface pour les dés
│   │   │   ├── SixSidedDice.java  # Dé à 6 faces pour le déplacement
│   │   │   ├── TwentySidedDice.java # Dé à 20 faces pour les critiques
│   │   │   └── OutOfBoardException.java # Exception pour les limites du plateau
│   │   ├── board/
│   │   │   ├── Board.java         # Plateau de jeu
│   │   │   ├── Cell.java          # Classe de base pour les cellules
│   │   │   ├── EmptyCell.java     # Case vide
│   │   │   ├── EnemyCell.java     # Case avec un ennemi
│   │   │   ├── WeaponCell.java    # Case avec une arme
│   │   │   ├── PotionCell.java    # Case avec une potion
│   │   │   ├── SpellCell.java     # Case avec un sort
│   │   │   └── MysteryBox.java    # Case mystère
│   │   ├── characters/
│   │   │   ├── Character.java     # Classe de base pour les personnages
│   │   │   ├── Warrior.java       # Classe pour les guerriers
│   │   │   ├── Wizard.java        # Classe pour les magiciens
│   │   │   ├── Enemy.java         # Classe de base pour les ennemis
│   │   │   ├── Dragon.java        # Ennemi Dragon
│   │   │   ├── Goblin.java        # Ennemi Gobelin
│   │   │   ├── Orc.java           # Ennemi Orc
│   │   │   └── Sorcerer.java      # Ennemi Sorcier
│   │   ├── items/
│   │   │   ├── OffensiveEquipment.java  # Équipement offensif (abstrait)
│   │   │   ├── Weapon.java        # Arme (héritée de OffensiveEquipment)
│   │   │   ├── Spell.java         # Sort (héritée de OffensiveEquipment)
│   │   │   ├── DefensiveEquipment.java # Équipement défensif (abstrait)
│   │   │   ├── Shield.java        # Bouclier (héritée de DefensiveEquipment)
│   │   │   └── Potion.java        # Potion (héritée de DefensiveEquipment)
│   │   └── db/
│   │       ├── SimpleDatabaseManager.java # Gestion simplifiée de la base de données
│   │       ├── LinkDB.java         # Connexion à la base de données
│   │       └── Test.java           # Tests pour la base de données
├── schema.sql                     # Schéma de base de données simplifié
├── doc/
│   └── ...                       # Documentation JavaDoc générée
├── lib/
│   └── ...                       # Bibliothèques externes
└── README.md                     # Ce fichier
```

---

## 🧪 Tests

Pour l'instant, les tests sont manuels. Tu peux exécuter le jeu et vérifier que :
- La création de personnage fonctionne correctement.
- Le déplacement sur le plateau est cohérent avec les lancers de dé.
- Les interactions avec les différentes cases (ennemis, armes, potions, boss) fonctionnent comme prévu.
- Le jeu se termine correctement lorsque le joueur atteint la case 64 ou est vaincu par un ennemi.

---

## 🛠 Technologies

- **Java** : Langage de programmation principal (Java 8+).
- **POO** : Programmation Orientée Objet pour une structure claire et modulaire.
- **UML** : Diagrammes de classe pour la conception et la documentation.
- **SQL** : Base de données MySQL pour sauvegarder les personnages.
- **PlantUML** : Pour la génération de diagrammes UML.

## ✨ Améliorations récentes

### Architecture et Code :
- **Injection de dépendances** : Préparation pour une meilleure testabilité
- **Séparation des responsabilités** : Méthodes privées bien organisées dans la classe Game
- **Gestion d'erreurs améliorée** : Validation des entrées et gestion des exceptions
- **Documentation complète** : JavaDoc pour toutes les méthodes publiques et privées

### Base de données :
- **Sauvegarde automatique** : Les personnages sont automatiquement sauvegardés après création
- **Chargement flexible** : Possibilité de charger des personnages existants
- **Mise à jour des équipements** : Sauvegarde des équipements avec les personnages

### Expérience utilisateur :
- **Menu interactif** : Navigation claire entre les différentes options
- **Messages d'erreur clairs** : Guidage de l'utilisateur en cas d'erreur
- **Affichage des statistiques** : Visualisation complète des attributs du personnage

## 🗃 Base de Données Simplifiée

La version simplifiée se concentre uniquement sur la sauvegarde des personnages et de leur équipement :

- **Table `characters`** : Stocke les informations des personnages (type, nom, points de vie, puissance d'attaque)
- **Table `equipment`** : Stocke les équipements (armes, sorts, boucliers, potions) associés aux personnages

Le schéma SQL est disponible dans `schema.sql` et contient uniquement les tables essentielles pour sauvegarder et charger les personnages.

Cette approche simplifiée ignore :
- Les cases du plateau
- Les ennemis
- Les positions
- Les relations complexes

Cela rend le code plus facile à maintenir et à comprendre.

---

## 📝 Documentation

La documentation JavaDoc est incluse dans le code et couvre maintenant toutes les classes et méthodes principales. Pour la générer :

```bash
javadoc -d doc -sourcepath src -subpackages fr.campus.d_and_d
```

La documentation générée sera disponible dans le dossier `doc/`.

### Dernières mises à jour de la documentation :
- **JavaDoc complète** pour toutes les classes principales (Game, Character, Board, etc.)
- Ajout de documentation détaillée pour les méthodes privées dans `Game`
- Ajout de `@throws` pour documenter les exceptions possibles
- Amélioration de la documentation des classes de base de données (`LinkDB`, `SimpleDatabaseManager`)
- Mise à jour du diagramme UML (`uml_diagram.puml`) pour refléter la structure actuelle

### Diagramme UML

Un diagramme UML complet est disponible dans `DD/uml_diagram.puml` et inclut :
- Toutes les classes et interfaces
- Les relations d'héritage et d'implémentation
- Les associations entre classes
- Les méthodes et attributs principaux

Pour générer le diagramme :
```bash
java -jar plantuml.jar uml_diagram.puml
```

La documentation est maintenant complète et à jour avec toutes les dernières modifications du code.

---

## 🤝 Contribution

Les contributions sont les bienvenues ! Pour contribuer :
1. Fork le projet.
2. Crée une branche pour ta fonctionnalité (`git checkout -b feature/AmazingFeature`).
3. Commit tes changements (`git commit -m 'Add some AmazingFeature'`).
4. Push vers la branche (`git push origin feature/AmazingFeature`).
5. Ouvre une Pull Request.

---

## 📜 Licence

Ce projet est sous licence MIT. Voir le fichier [LICENCE](LICENCE) pour plus de détails.

---

## 📞 Contact

Pour toute question ou suggestion, n'hésite pas à ouvrir une issue ou à me contacter directement.

---

**Amuse-toi bien en jouant à Donjons et Dragons !** 🎲⚔️🔮
