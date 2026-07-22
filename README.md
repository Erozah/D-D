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
  - **EmptyCell** : Case vide sans effet.
  - **EnemyCell** : Case avec un ennemi à combattre.
  - **WeaponCell** : Case avec une arme à ramasser.
  - **PotionCell** : Case avec une potion pour restaurer des points de vie.
  - **BossCell** : Case avec un boss final à vaincre pour gagner la partie.

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
   javac -d bin src/fr/campus/d_and_d/Main.java src/fr/campus/d_and_d/characters/*.java src/fr/campus/d_and_d/board/*.java src/fr/campus/d_and_d/items/*.java src/fr/campus/d_and_d/gameLogic/*.java
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
│   │   │   ├── Dice.java          # Dé virtuel
│   │   │   └── OutOfBoardException.java # Exception pour les limites du plateau
│   │   ├── board/
│   │   │   ├── Board.java         # Plateau de jeu
│   │   │   ├── Cell.java          # Classe de base pour les cellules
│   │   │   ├── EmptyCell.java     # Case vide
│   │   │   ├── EnemyCell.java     # Case avec un ennemi
│   │   │   ├── WeaponCell.java    # Case avec une arme
│   │   │   ├── PotionCell.java    # Case avec une potion
│   │   │   └── BossCell.java      # Case avec un boss final
│   │   ├── characters/
│   │   │   ├── Character.java     # Classe de base pour les personnages
│   │   │   ├── Warrior.java       # Classe pour les guerriers
│   │   │   └── Wizard.java        # Classe pour les magiciens
│   │   └── items/
│   │       ├── OffensiveEquipment.java  # Équipement offensif (abstrait)
│   │       ├── Weapon.java        # Arme (héritée de OffensiveEquipment)
│   │       ├── Spell.java         # Sort (héritée de OffensiveEquipment)
│   │       ├── DefensiveEquipment.java # Équipement défensif (abstrait)
│   │       ├── Shield.java        # Bouclier (héritée de DefensiveEquipment)
│   │       └── Potion.java        # Potion (héritée de DefensiveEquipment)
├── doc/
│   └── ...                       # Documentation JavaDoc générée
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

- **Java** : Langage de programmation principal.
- **POO** : Programmation Orientée Objet pour une structure claire et modulaire.
- **UML** : Diagrammes de classe pour la conception et la documentation.

---

## 📝 Documentation

La documentation JavaDoc est incluse dans le code et est entièrement en anglais. Pour la générer :

```bash
javadoc -d doc -cp bin -subpackages fr.campus.d_and_d
```

La documentation générée sera disponible dans le dossier `doc/`.

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
