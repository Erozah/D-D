# Itération 5 - Projet D&D (Java POO)

## Objectifs de l'itération
Cette itération vise à ajouter la persistance des données en utilisant une base de données MySQL. Les principaux objectifs sont :
- **Création d'une base de données MySQL** : Pour stocker les personnages et leurs attributs.
- **Connexion à la base de données** : Utilisation du driver JDBC pour interagir avec MySQL.
- **Implémentation des méthodes CRUD** : Pour gérer les personnages et le plateau de jeu.
- **Utilisation de GSON (optionnel)** : Pour sauvegarder des objets complexes au format JSON.

---

## Étapes à suivre

### 1. Installation de MySQL

#### 1.1. Installer MySQL sur Ubuntu
Si MySQL n'est pas déjà installé, suivez les instructions pour l'installer :
- [Guide d'installation de MySQL sur Ubuntu](https://www.digitalocean.com/community/tutorials/how-to-install-mysql-on-ubuntu-20-04)

#### 1.2. Créer la base de données et la table `Character`
Une fois MySQL installé, créez une base de données et une table pour stocker les personnages :

```sql
CREATE DATABASE IF NOT EXISTS dnd_game;
USE dnd_game;

CREATE TABLE IF NOT EXISTS Character (
    Id INT AUTO_INCREMENT PRIMARY KEY,
    Type VARCHAR(20) NOT NULL,
    Name VARCHAR(50) NOT NULL,
    LifePoints INT NOT NULL,
    Strength INT NOT NULL,
    OffensiveEquipment VARCHAR(255) NOT NULL,
    DefensiveEquipment VARCHAR(255) NOT NULL
);
```

### 2. Configuration du projet pour utiliser JDBC

#### 2.1. Télécharger le driver JDBC pour MySQL
Téléchargez le driver JDBC pour MySQL depuis le lien suivant :
- [MySQL JDBC Driver](https://dbschema.com/jdbc-drivers/MySqlJdbcDriver.zip)

#### 2.2. Ajouter le driver JDBC au projet
1. Créez un dossier `lib` à la racine de votre projet.
2. Déposez le fichier `.jar` du driver JDBC dans ce dossier.
3. Ajoutez le fichier `.jar` comme dépendance dans votre projet IntelliJ :
   - [Guide pour ajouter une dépendance externe dans IntelliJ](https://www.geeksforgeeks.org/how-to-add-external-jar-file-to-an-intellij-idea-project/)

#### 2.3. Configurer la connexion à la base de données
Créez un fichier de configuration (optionnel) pour stocker les informations de connexion à la base de données. Vous pouvez utiliser un fichier `config.properties` :

```properties
# config.properties
db.url=jdbc:mysql://localhost:3306/dnd_game
db.user=votre_utilisateur
db.password=votre_mot_de_passe
```

### 3. Créer une classe pour gérer la connexion à la base de données

#### 3.1. Créer un package `db`
Créez un nouveau package `db` dans votre projet pour y placer les classes liées à la base de données.

#### 3.2. Créer une classe `DatabaseConnection`
Cette classe gérera la connexion à la base de données :

```java
package fr.campus.d_and_d.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/dnd_game";
    private static final String USER = "votre_utilisateur";
    private static final String PASSWORD = "votre_mot_de_passe";
    
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
```

#### 3.3. Tester la connexion
Créez une méthode pour tester la connexion à la base de données :

```java
public static void testConnection() {
    try (Connection connection = getConnection()) {
        System.out.println("Connexion à la base de données réussie !");
    } catch (SQLException e) {
        System.err.println("Erreur de connexion à la base de données : " + e.getMessage());
    }
}
```

### 4. Implémenter les méthodes CRUD pour les personnages

#### 4.1. Créer une classe `CharacterDAO`
La classe `CharacterDAO` (Data Access Object) contiendra les méthodes pour interagir avec la table `Character` :

```java
package fr.campus.d_and_d.db;

import fr.campus.d_and_d.characters.Character;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CharacterDAO {
    
    // Méthode pour récupérer tous les personnages
    public List<Character> getHeroes() {
        List<Character> characters = new ArrayList<>();
        String query = "SELECT * FROM Character";
        
        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            
            while (resultSet.next()) {
                // Récupérer les données de la base de données
                String type = resultSet.getString("Type");
                String name = resultSet.getString("Name");
                int lifePoints = resultSet.getInt("LifePoints");
                int strength = resultSet.getInt("Strength");
                String offensiveEquipment = resultSet.getString("OffensiveEquipment");
                String defensiveEquipment = resultSet.getString("DefensiveEquipment");
                
                // Créer un nouveau personnage et l'ajouter à la liste
                Character character = new Character(type, name, lifePoints, strength, offensiveEquipment, defensiveEquipment);
                characters.add(character);
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des personnages : " + e.getMessage());
        }
        
        return characters;
    }
    
    // Méthode pour créer un nouveau personnage
    public void createHero(Character character) {
        String query = "INSERT INTO Character (Type, Name, LifePoints, Strength, OffensiveEquipment, DefensiveEquipment) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            
            preparedStatement.setString(1, character.getType());
            preparedStatement.setString(2, character.getName());
            preparedStatement.setInt(3, character.getHealthPoints());
            preparedStatement.setInt(4, character.getAttackPower());
            preparedStatement.setString(5, character.getOffensiveEquipment().toString());
            preparedStatement.setString(6, character.getDefensiveEquipment().toString());
            
            preparedStatement.executeUpdate();
            System.out.println("Personnage créé avec succès !");
        } catch (SQLException e) {
            System.err.println("Erreur lors de la création du personnage : " + e.getMessage());
        }
    }
    
    // Méthode pour modifier un personnage existant
    public void editHero(Character character) {
        String query = "UPDATE Character SET Type = ?, Name = ?, LifePoints = ?, Strength = ?, OffensiveEquipment = ?, DefensiveEquipment = ? WHERE Id = ?";
        
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            
            preparedStatement.setString(1, character.getType());
            preparedStatement.setString(2, character.getName());
            preparedStatement.setInt(3, character.getHealthPoints());
            preparedStatement.setInt(4, character.getAttackPower());
            preparedStatement.setString(5, character.getOffensiveEquipment().toString());
            preparedStatement.setString(6, character.getDefensiveEquipment().toString());
            preparedStatement.setInt(7, character.getId());
            
            preparedStatement.executeUpdate();
            System.out.println("Personnage modifié avec succès !");
        } catch (SQLException e) {
            System.err.println("Erreur lors de la modification du personnage : " + e.getMessage());
        }
    }
    
    // Méthode pour mettre à jour les points de vie d'un personnage
    public void changeLifePoints(int characterId, int newLifePoints) {
        String query = "UPDATE Character SET LifePoints = ? WHERE Id = ?";
        
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            
            preparedStatement.setInt(1, newLifePoints);
            preparedStatement.setInt(2, characterId);
            
            preparedStatement.executeUpdate();
            System.out.println("Points de vie mis à jour avec succès !");
        } catch (SQLException e) {
            System.err.println("Erreur lors de la mise à jour des points de vie : " + e.getMessage());
        }
    }
}
```

### 5. Utiliser GSON pour sauvegarder des objets complexes (optionnel)

#### 5.1. Télécharger la librairie GSON
Téléchargez le fichier `.jar` de la librairie GSON depuis le lien suivant :
- [GSON 2.10.1](https://repo1.maven.org/maven2/com/google/code/gson/gson/2.10.1/gson-2.10.1.jar)

Ajoutez le fichier `.jar` au dossier `lib` et configurez-le comme dépendance dans IntelliJ.

#### 5.2. Utiliser GSON pour convertir des objets en JSON
Vous pouvez utiliser GSON pour convertir des objets complexes en JSON et les stocker dans la base de données :

```java
package fr.campus.d_and_d.db;

import com.google.gson.Gson;
import fr.campus.d_and_d.items.OffensiveEquipment;
import fr.campus.d_and_d.items.DefensiveEquipment;

public class GsonUtils {
    private static final Gson gson = new Gson();
    
    // Convertir un objet en JSON
    public static String toJson(Object object) {
        return gson.toJson(object);
    }
    
    // Convertir un JSON en objet
    public static <T> T fromJson(String json, Class<T> classOfT) {
        return gson.fromJson(json, classOfT);
    }
}
```

#### 5.3. Mettre à jour les méthodes pour utiliser GSON
Modifiez les méthodes `createHero` et `editHero` pour utiliser GSON :

```java
// Dans la méthode createHero
preparedStatement.setString(5, GsonUtils.toJson(character.getOffensiveEquipment()));
preparedStatement.setString(6, GsonUtils.toJson(character.getDefensiveEquipment()));

// Dans la méthode editHero
preparedStatement.setString(5, GsonUtils.toJson(character.getOffensiveEquipment()));
preparedStatement.setString(6, GsonUtils.toJson(character.getDefensiveEquipment()));
```

### 6. Sauvegarder et récupérer le plateau de jeu

#### 6.1. Créer une table pour le plateau de jeu
Créez une table pour stocker le plateau de jeu et ses cellules :

```sql
CREATE TABLE IF NOT EXISTS Board (
    Id INT AUTO_INCREMENT PRIMARY KEY,
    CurrentPosition INT NOT NULL,
    MaxPosition INT NOT NULL
);

CREATE TABLE IF NOT EXISTS Cell (
    Id INT AUTO_INCREMENT PRIMARY KEY,
    BoardId INT NOT NULL,
    Position INT NOT NULL,
    Type VARCHAR(20) NOT NULL,
    Content VARCHAR(255),
    FOREIGN KEY (BoardId) REFERENCES Board(Id)
);
```

#### 6.2. Créer une classe `BoardDAO`
Créez une classe `BoardDAO` pour gérer la sauvegarde et la récupération du plateau de jeu :

```java
package fr.campus.d_and_d.db;

import fr.campus.d_and_d.board.Board;
import fr.campus.d_and_d.board.Cell;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BoardDAO {
    
    // Méthode pour sauvegarder le plateau de jeu
    public void saveBoard(Board board) {
        String boardQuery = "INSERT INTO Board (CurrentPosition, MaxPosition) VALUES (?, ?)";
        String cellQuery = "INSERT INTO Cell (BoardId, Position, Type, Content) VALUES (?, ?, ?, ?)";
        
        try (Connection connection = DatabaseConnection.getConnection()) {
            connection.setAutoCommit(false);
            
            // Sauvegarder le plateau
            try (PreparedStatement boardStatement = connection.prepareStatement(boardQuery, Statement.RETURN_GENERATED_KEYS)) {
                boardStatement.setInt(1, board.getCurrentPosition());
                boardStatement.setInt(2, board.getMaxPosition());
                boardStatement.executeUpdate();
                
                // Récupérer l'ID du plateau
                try (ResultSet generatedKeys = boardStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int boardId = generatedKeys.getInt(1);
                        
                        // Sauvegarder les cellules
                        try (PreparedStatement cellStatement = connection.prepareStatement(cellQuery)) {
                            for (int i = 0; i < board.getMaxPosition(); i++) {
                                Cell cell = board.getCells().get(i);
                                cellStatement.setInt(1, boardId);
                                cellStatement.setInt(2, i + 1);
                                cellStatement.setString(3, cell.getClass().getSimpleName());
                                cellStatement.setString(4, GsonUtils.toJson(cell));
                                cellStatement.addBatch();
                            }
                            cellStatement.executeBatch();
                        }
                    }
                }
            }
            
            connection.commit();
            System.out.println("Plateau sauvegardé avec succès !");
        } catch (SQLException e) {
            System.err.println("Erreur lors de la sauvegarde du plateau : " + e.getMessage());
            try {
                if (connection != null) {
                    connection.rollback();
                }
            } catch (SQLException ex) {
                System.err.println("Erreur lors du rollback : " + ex.getMessage());
            }
        }
    }
    
    // Méthode pour récupérer le plateau de jeu
    public Board getBoard(int boardId) {
        String boardQuery = "SELECT * FROM Board WHERE Id = ?";
        String cellQuery = "SELECT * FROM Cell WHERE BoardId = ? ORDER BY Position";
        
        try (Connection connection = DatabaseConnection.getConnection()) {
            Board board = null;
            
            // Récupérer le plateau
            try (PreparedStatement boardStatement = connection.prepareStatement(boardQuery)) {
                boardStatement.setInt(1, boardId);
                try (ResultSet boardResult = boardStatement.executeQuery()) {
                    if (boardResult.next()) {
                        board = new Board();
                        board.setCurrentPosition(boardResult.getInt("CurrentPosition"));
                        // Initialisez le reste du plateau ici
                    }
                }
            }
            
            // Récupérer les cellules
            if (board != null) {
                try (PreparedStatement cellStatement = connection.prepareStatement(cellQuery)) {
                    cellStatement.setInt(1, boardId);
                    try (ResultSet cellResult = cellStatement.executeQuery()) {
                        while (cellResult.next()) {
                            String cellType = cellResult.getString("Type");
                            String cellContent = cellResult.getString("Content");
                            Cell cell = GsonUtils.fromJson(cellContent, Class.forName("fr.campus.d_and_d.board." + cellType));
                            board.getCells().add(cell);
                        }
                    }
                }
            }
            
            return board;
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Erreur lors de la récupération du plateau : " + e.getMessage());
            return null;
        }
    }
}
```

### 7. Intégrer la base de données dans le jeu

#### 7.1. Modifier la classe `Game` pour utiliser la base de données
Modifiez la classe `Game` pour utiliser les méthodes de `CharacterDAO` et `BoardDAO` :

- **Récupérer les personnages depuis la base de données** : Utilisez `getHeroes()` pour afficher la liste des personnages disponibles.
- **Sauvegarder un nouveau personnage** : Utilisez `createHero()` pour sauvegarder un personnage créé par l'utilisateur.
- **Mettre à jour les points de vie** : Utilisez `changeLifePoints()` pour mettre à jour les points de vie d'un personnage après un combat.

#### 7.2. Modifier la classe `Menu` pour afficher les personnages depuis la base de données
Modifiez la méthode `mainMenu()` pour afficher les personnages récupérés depuis la base de données :

```java
public void mainMenu() {
    CharacterDAO characterDAO = new CharacterDAO();
    List<Character> characters = characterDAO.getHeroes();
    
    System.out.println("Liste des personnages disponibles :");
    for (Character character : characters) {
        System.out.println(character);
    }
    
    // Reste du code...
}
```

---

## Livrables

1. **Code source** :
   - Classe `DatabaseConnection` pour gérer la connexion à la base de données.
   - Classe `CharacterDAO` avec les méthodes `getHeroes()`, `createHero()`, `editHero()`, et `changeLifePoints()`.
   - Classe `BoardDAO` pour sauvegarder et récupérer le plateau de jeu.
   - Mise à jour des classes `Game` et `Menu` pour utiliser les nouvelles méthodes.

2. **Documentation** :
   - Mise à jour de la documentation JavaDoc pour les nouvelles classes.
   - Ajout d'un glossaire des syntaxes utilisées (JDBC, GSON, etc.).

3. **Base de données** :
   - Script SQL pour créer la base de données et les tables.
   - Données de test pour peupler la base de données.

---

## Ressources utiles

### JDBC
- [Tutoriel JDBC - TutorialsPoint](https://www.tutorialspoint.com/jdbc/)
- [Utiliser le modèle DAO - OpenClassrooms](https://openclassrooms.com/)
- [JDBC Using Model Object and Singleton Class - GeeksforGeeks](https://www.geeksforgeeks.org/)

### GSON
- [GSON - GitHub](https://github.com/google/gson)
- [GSON User Guide](https://github.com/google/gson/blob/master/UserGuide.md)

### MySQL
- [MySQL JDBC Driver](https://dbschema.com/jdbc-driver/MySql.html)
- [MySQL Documentation](https://dev.mysql.com/doc/)

---

## Notes supplémentaires

- **Gestion des erreurs** : Assurez-vous de gérer correctement les exceptions SQL pour éviter les plantages.
- **Sécurité** : Utilisez des requêtes préparées pour éviter les injections SQL.
- **Tests** : Testez chaque méthode pour vous assurer qu'elle fonctionne correctement avec la base de données.
