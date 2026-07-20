# Donjons et Dragons - Jeu de Plateau

**Un jeu de plateau inspiré de l'univers Donjons et Dragons, développé en Java.**

---

## 📖 Description

Ce projet est une implémentation simplifiée d'un jeu de plateau de type Donjons et Dragons. Les joueurs peuvent créer un personnage (Guerrier ou Magicien), puis avancer sur un plateau de 64 cases en lançant un dé. Le but est d'atteindre la dernière case pour gagner la partie.

---

## 🎮 Fonctionnalités

- **Création de personnage** : Choisissez entre un Guerrier ou un Magicien, chacun avec des caractéristiques uniques.
- **Plateau de jeu** : Un plateau de 64 cases où le joueur avance en lançant un dé.
- **Système de dé** : Un dé virtuel génère un nombre aléatoire entre 1 et 6 pour déterminer le déplacement.
- **Interface utilisateur** : Menu interactif pour naviguer dans le jeu.

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
   javac src/fr/campus/d_and_d/*.java src/fr/campus/d_and_d/characters/*.java src/fr/campus/d_and_d/items/*.java
   ```

4. Exécute le jeu :
   ```bash
   java fr.campus.d_and_d.Main
   ```

---

## 📂 Structure du Projet

```
DD/
├── src/
│   ├── fr/campus/d_and_d/
│   │   ├── Main.java              # Point d'entrée du jeu
│   │   ├── Game.java              # Logique principale du jeu
│   │   ├── Menu.java              # Gestion du menu utilisateur
│   │   ├── Board.java             # Plateau de jeu
│   │   ├── Dice.java              # Dé virtuel
│   │   ├── characters/
│   │   │   ├── Character.java     # Classe de base pour les personnages
│   │   │   ├── Warrior.java       # Classe pour les guerriers
│   │   │   └── Wizard.java        # Classe pour les magiciens
│   │   └── items/
│   │       ├── OffensiveEquipment.java  # Équipement offensif
│   │       └── DefensiveEquipment.java # Équipement défensif
```

---

## 🧪 Tests

Pour l'instant, les tests sont manuels. Tu peux exécuter le jeu et vérifier que :
- La création de personnage fonctionne correctement.
- Le déplacement sur le plateau est cohérent avec les lancers de dé.
- Le jeu se termine correctement lorsque le joueur atteint la case 64.

---

## 🛠 Technologies

- **Java** : Langage de programmation principal.
- **POO** : Programmation Orientée Objet pour une structure claire et modulaire.

---

## 📝 Documentation

La documentation JavaDoc est incluse dans le code. Pour la générer :

```bash
javadoc -d doc/ src/fr/campus/d_and_d/*.java src/fr/campus/d_and_d/characters/*.java src/fr/campus/d_and_d/items/*.java
```

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
