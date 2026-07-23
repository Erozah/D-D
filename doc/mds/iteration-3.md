# Itération 3 - Projet D&D (Java POO)

## Objectifs de l'itération
Cette itération introduit des concepts avancés de la programmation orientée objet (POO) en Java, notamment :
- **L'héritage** : Pour spécialiser les classes `Character`, `OffensiveEquipment`, et `DefensiveEquipment`.
- **Les classes abstraites** : Pour structurer et clarifier le code.
- **La gestion des exceptions** : Pour gérer les comportements inattendus (par exemple, lorsque le joueur dépasse les limites du plateau).
- **La documentation avec Javadoc** : Pour générer une documentation claire et accessible du code.

---

## Structure du projet

### 1. Réorganisation des classes avec l'héritage

#### Classe abstraite `Character`
- **Rôle** : Classe de base pour tous les types de personnages.
- **Attributs** :
  - `name` (String) : Nom du personnage.
  - `health` (int) : Niveau de vie.
  - `attack` (int) : Niveau d'attaque.
  - `offensiveEquipment` (OffensiveEquipment) : Équipement offensif.
  - `defensiveEquipment` (DefensiveEquipment) : Équipement défensif.
- **Méthodes** :
  - Constructeur, `toString()`, getters et setters.
  - Méthodes abstraites (si nécessaire) pour les comportements spécifiques.

#### Classe `Warrior` (héritée de `Character`)
- **Rôle** : Représenter un personnage de type Guerrier.
- **Attributs spécifiques** :
  - `strength` (int) : Niveau de force (optionnel).
- **Méthodes** :
  - Redéfinition de `toString()` pour afficher des informations spécifiques au Guerrier.
  - Méthodes spécifiques (par exemple, `useWeapon()`).

#### Classe `Wizard` (héritée de `Character`)
- **Rôle** : Représenter un personnage de type Magicien.
- **Attributs spécifiques** :
  - `mana` (int) : Niveau de mana (optionnel).
- **Méthodes** :
  - Redéfinition de `toString()` pour afficher des informations spécifiques au Magicien.
  - Méthodes spécifiques (par exemple, `castSpell()`).

#### Classe abstraite `OffensiveEquipment`
- **Rôle** : Classe de base pour les équipements offensifs.
- **Attributs** :
  - `attackLevel` (int) : Niveau d'attaque.
  - `name` (String) : Nom de l'équipement.
- **Méthodes** :
  - Constructeur, `toString()`, getters et setters.

#### Classe `Weapon` (héritée de `OffensiveEquipment`)
- **Rôle** : Représenter une arme.
- **Attributs spécifiques** :
  - `weaponType` (String) : Type d'arme (par exemple, "Épée", "Hache").
- **Méthodes** :
  - Redéfinition de `toString()` pour afficher des informations spécifiques à l'arme.

#### Classe `Spell` (héritée de `OffensiveEquipment`)
- **Rôle** : Représenter un sort.
- **Attributs spécifiques** :
  - `spellType` (String) : Type de sort (par exemple, "Feu", "Glace").
- **Méthodes** :
  - Redéfinition de `toString()` pour afficher des informations spécifiques au sort.

#### Classe abstraite `DefensiveEquipment`
- **Rôle** : Classe de base pour les équipements défensifs.
- **Attributs** :
  - `defenseLevel` (int) : Niveau de défense.
  - `name` (String) : Nom de l'équipement.
- **Méthodes** :
  - Constructeur, `toString()`, getters et setters.

#### Classe `Shield` (héritée de `DefensiveEquipment`)
- **Rôle** : Représenter un bouclier.
- **Attributs spécifiques** :
  - `shieldType` (String) : Type de bouclier (par exemple, "Bouclier en bois", "Bouclier en métal").
- **Méthodes** :
  - Redéfinition de `toString()` pour afficher des informations spécifiques au bouclier.

#### Classe `Potion` (héritée de `DefensiveEquipment`)
- **Rôle** : Représenter une potion.
- **Attributs spécifiques** :
  - `potionType` (String) : Type de potion (par exemple, "Potion de vie", "Potion de mana").
- **Méthodes** :
  - Redéfinition de `toString()` pour afficher des informations spécifiques à la potion.

---

## Gestion des exceptions

### 1. Exception `OutOfBoardException`
- **Rôle** : Gérer le cas où le joueur dépasse les limites du plateau.
- **Implémentation** :
  - Créer une classe `OutOfBoardException` qui hérite de `Exception`.
  - Lever cette exception dans la méthode `movePlayer(int steps)` de la classe `Board` si la position du joueur dépasse la taille du plateau.
  - Capturer et gérer cette exception dans la classe `Game` pour afficher un message approprié à l'utilisateur.

---

## Documentation avec Javadoc

### 1. Commentaires Javadoc
- **Rôle** : Documenter le code pour générer une documentation accessible.
- **Syntaxe** :
  - Utiliser `/** ... */` pour les commentaires Javadoc.
  - Documenter chaque classe, méthode et attribut public.
  - Inclure des informations sur :
    - Le rôle de la classe/méthode.
    - Les paramètres (avec `@param`).
    - La valeur de retour (avec `@return`).
    - Les exceptions levées (avec `@throws`).

### 2. Génération de la Javadoc
- **Outils** :
  - Utiliser l'IDE (IntelliJ, Eclipse) pour générer la Javadoc.
  - Commande en ligne de commande : `javadoc -d doc/ src/*.java`.
- **Livrable** : La documentation générée doit être accessible et déposée sur GitHub.

---

## Bonnes pratiques

### 1. Organisation du code
- **Packages** : Organiser les classes en petits packages cohérents (par exemple, `fr.campus.dungeoncrawler.characters`, `fr.campus.dungeoncrawler.equipment`).
- **Responsabilité unique** : Chaque classe doit avoir une responsabilité claire et unique.

### 2. Encapsulation
- **Visibilité** : Tous les attributs doivent être `private`.
- **Accès** : Utiliser des getters et setters pour accéder aux attributs.

### 3. Polymorphisme
- **Utilisation** : Profiter de l'héritage pour utiliser des objets de manière polymorphe (par exemple, traiter un `Warrior` et un `Wizard` comme des `Character`).

---

## Livrables

1. **Code source** :
   - Classes `Warrior`, `Wizard`, `Weapon`, `Spell`, `Shield`, et `Potion`.
   - Classes abstraites `Character`, `OffensiveEquipment`, et `DefensiveEquipment`.
   - Exception `OutOfBoardException`.
   - Mise à jour des classes existantes (`Game`, `Board`, `Menu`, etc.).

2. **Documentation** :
   - Javadoc générée et accessible.
   - Commentaires Javadoc dans le code.

3. **Glossaire** : Liste des syntaxes et concepts utilisés (héritage, classes abstraites, exceptions, Javadoc).

---

## Ressources utiles

### Héritage et classes abstraites
- [OpenClassrooms - Héritage et polymorphisme](https://openclassrooms.com/)
- [Tutoriel Oracle - Héritage](https://docs.oracle.com/javase/tutorial/java/IandI/index.html)
- [Vidéo EPFL - Classes et méthodes abstraites](https://www.epfl.ch/)

### Exceptions
- [OpenClassrooms - Gestion des exceptions](https://openclassrooms.com/)
- [Tutoriel Zeste de Savoir - Les exceptions](https://zestedesavoir.com/)

### Javadoc
- [Tutoriel Oracle - Javadoc](https://www.oracle.com/technetwork/java/javase/documentation/index-jsp-135444.html)
- [Javadoc dans IntelliJ](https://www.jetbrains.com/help/idea/working-with-code-documentation.html)

---

## Notes supplémentaires
- Cette itération vise à améliorer la structure du code et à le rendre plus maintenable et extensible.
- La documentation est essentielle pour faciliter la collaboration et la compréhension du code.
- Les exceptions permettent de gérer les erreurs de manière élégante et robuste.
