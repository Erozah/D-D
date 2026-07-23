# Base de Données pour Donjons et Dragons

Ce dossier contient tous les fichiers liés à la gestion de la base de données pour le jeu Donjons et Dragons.

## Structure de la Base de Données

Le schéma de la base de données est défini dans `schema.sql` et comprend les tables suivantes :

### 1. `boards`
Stocke les informations sur les plateaux de jeu.
- `id` : Identifiant unique
- `name` : Nom du plateau
- `size` : Taille du plateau (nombre de cases)
- `created_at` : Date de création
- `updated_at` : Date de dernière mise à jour

### 2. `cells`
Stocke le contenu de chaque case du plateau.
- `id` : Identifiant unique
- `board_id` : Référence au plateau
- `position` : Position sur le plateau (1-64)
- `content_type` : Type de contenu (ENEMY, WEAPON, POTION, etc.)
- `content_id` : Référence au contenu spécifique

### 3. `characters`
Stocke les personnages (joueurs et ennemis).
- `id` : Identifiant unique
- `character_type` : Type de personnage (WARRIOR, WIZARD, GOBLIN, etc.)
- `name` : Nom du personnage
- `health_points` : Points de vie
- `attack_power` : Puissance d'attaque
- `is_enemy` : Indique si c'est un ennemi
- `is_boss` : Indique si c'est un boss
- `cell_id` : Référence à la case où se trouve le personnage

### 4. `equipment`
Stocke les équipements (armes, sorts, boucliers, potions).
- `id` : Identifiant unique
- `equipment_type` : Type d'équipement
- `name` : Nom de l'équipement
- `power` : Puissance de l'équipement
- `owner_id` : Référence au propriétaire (personnage)
- `cell_id` : Référence à la case où se trouve l'équipement

### 5. `saved_games`
Stocke les parties sauvegardées.
- `id` : Identifiant unique
- `player_id` : Référence au personnage du joueur
- `board_id` : Référence au plateau
- `current_position` : Position actuelle sur le plateau
- `save_date` : Date de sauvegarde

## Configuration

### 1. Créer la base de données

```bash
# Se connecter à MySQL
mysql -u root -p

# Exécuter le schéma SQL
SOURCE /chemin/vers/DD/src/fr/campus/d_and_d/db/schema.sql;
```

### 2. Configurer la connexion

Dans `LinkDB.java`, mettre à jour les informations de connexion :

```java
private static final String URL = "jdbc:mysql://localhost:3306/DnD";
private static final String USER = "root";  // ou votre utilisateur
private static final String PASSWORD = "votre_mot_de_passe";  // votre mot de passe
```

### 3. Ajouter le pilote JDBC

Télécharger le pilote MySQL Connector/J et le placer dans `DD/lib/` :

```bash
wget https://dev.mysql.com/get/Downloads/Connector-J/mysql-connector-java-8.0.46.tar.gz
 tar -xzf mysql-connector-java-8.0.46.tar.gz
mkdir -p DD/lib
cp mysql-connector-java-8.0.46/mysql-connector-java-8.0.46.jar DD/lib/
```

## Utilisation

### Sauvegarder un plateau

```java
Board board = new Board();
DatabaseManager.saveBoard(board, "MonPlateau");
```

### Charger un plateau

```java
Board loadedBoard = DatabaseManager.loadBoard("MonPlateau");
```

### Sauvegarder un personnage

```java
Character character = new Warrior("Conan");
DatabaseManager.saveCharacter(character);
```

### Charger un personnage

```java
Character loadedCharacter = DatabaseManager.loadCharacter(characterId);
```

### Sauvegarder une partie

```java
DatabaseManager.saveGame(characterId, "MonPlateau", currentPosition);
```

### Charger une partie

```java
Object[] loadedGame = DatabaseManager.loadGame(saveId);
Character character = (Character) loadedGame[0];
Board board = (Board) loadedGame[1];
int position = (Integer) loadedGame[2];
```

## Tests

Exécuter les tests de la base de données :

```bash
# Compiler
javac -cp "DD/lib/*" -d DD/bin DD/src/fr/campus/d_and_d/db/*.java

# Exécuter les tests
java -cp "DD/bin:DD/lib/*" fr.campus.d_and_d.db.TestDatabase
```

## Modifications Récentes

### Iteration 5
- **Système de cellules unifié** : Remplacement des multiples classes de cellules par une seule classe `Cell` générique
- **Interface CellContent** : Nouvelle interface pour standardiser le contenu des cellules
- **Classes d'ennemis** : Ajout de `Enemy`, `Dragon`, `Goblin`, `Orc`, et `Sorcerer`
- **MysteryBox** : Nouvelle classe pour les boîtes mystérieuses contenant des équipements aléatoires
- **Base de données complète** : Nouveau schéma avec toutes les tables nécessaires
- **DatabaseManager** : Classe complète pour gérer toutes les opérations de base de données

### Iteration 4
- **Refactoring des cellules** : Simplification du système de cellules
- **Intégration des ennemis** : Les ennemis sont maintenant des personnages spéciaux
- **Gestion d'équipement** : Les personnages peuvent maintenant équiper et déséquiper des objets

## Problèmes Connus

1. **Connexion à la base de données** : Assurez-vous que MySQL est démarré et que les informations de connexion sont correctes dans `LinkDB.java`.

2. **Pilote JDBC manquant** : Si vous obtenez l'erreur "No suitable driver found", vérifiez que `mysql-connector-java-8.0.46.jar` est dans `DD/lib/`.

3. **Permissions MySQL** : L'utilisateur spécifié dans `LinkDB.java` doit avoir les droits nécessaires sur la base de données `DnD`.

## Auteurs

- **Erozah** : Développeur principal
- **Mistral Vibe** : Refactoring et intégration de la base de données

## Licence

Ce projet est sous licence MIT. Voir le fichier LICENCE pour plus de détails.
