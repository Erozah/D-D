# Annexe - Le jeu Donjons et Dragons

## Introduction

Le but de ce projet est de créer un jeu inspiré des jeux de plateau de l'univers « Donjons et Dragons », en utilisant le langage Java. Ce projet se découpe en plusieurs itérations, au cours desquelles de nouvelles fonctionnalités sont ajoutées. Pour chaque itération, il est important de bien lire les fonctionnalités et livrables demandés. Il est fortement conseillé de passer par une phase de réflexion sur "papier" avant de se lancer dans le développement.

**Important** : Les classes, méthodes et variables doivent être nommées correctement et en anglais, et formatées correctement (PascalCase pour les classes et camelCase pour les variables et méthodes).

Le jeu fonctionnera via la console de l'IDE. Le jeu affichera les informations dans la console, et le joueur pourra y écrire ses instructions (choix de la classe, nommer le personnage, lancer de dé, quitter, etc.).

Les règles présentées ici sont une base de réflexion. Vous êtes libres d'ajouter des classes ou des fonctionnalités supplémentaires (par exemple, un Archer, un ennemi Squelette, changer les valeurs de vie du dragon, ou encore modifier la répartition des ennemis).

---

## Table des matières

1. [Règles du jeu basique](#1--règles-du-jeu-basique)
   - [Le plateau de jeu](#12--le-plateau-de-jeu)
   - [Les personnages](#13--les-personnages)
   - [Les caisses surprises](#14--les-caisses-surprises)
   - [Les équipements offensifs](#15--les-équipements-offensifs)
   - [Les ennemis](#16--les-ennemis)
   - [Déroulement du jeu](#17--déroulement-du-jeu)
   - [Règle des combats](#18--règle-des-combats)
   - [Règle de fin de partie](#19--règle-de-fin-de-partie)

2. [Version avancée](#2--version-avancée-pour-sinspirer-ou-aller-plus-loin-après-litération-7)
   - [Nouvelles règles des combats](#21--nouvelles-règles-des-combats)
   - [Ennemis supplémentaires](#22--ennemis-supplementaires)
   - [Équipements supplémentaires](#23--équipements-supplementaires)
   - [Gestion de l'inventaire](#24--gestion-de-linventaire)
   - [Gestion des difficultés](#25--gestion-des-difficultés)
   - [Gestion des niveaux](#26--gestion-des-niveaux)
   - [Gestion des marchands](#27--gestion-des-marchands)
   - [Multi parties](#28--multi-parties)
   - [Multi joueur](#29--multi-joueur)
   - [Usure des objets](#210--usure-des-objets)
   - [Interface graphique](#211--interface-graphique)

---

## 1 — Règles du jeu basique

Le but du jeu est de faire traverser le plateau par le personnage. Sur le chemin, il rencontrera des ennemis, des armes et des bonus. Il doit sortir vivant de l'aventure !

### 1.2 — Le plateau de jeu

Le plateau de jeu est constitué de 64 cases. Chaque case peut :
- Être vide.
- Contenir un ennemi.
- Contenir une caisse surprise.

### 1.3 — Les personnages

Au début de chaque partie, l'utilisateur choisit son personnage (Guerrier ou Magicien) et lui donne un nom. Chaque type de personnage est caractérisé par les attributs suivants :

| Personnage  | Points de vie | Force d'attaque |
|-------------|---------------|-----------------|
| Guerrier    | 10            | 5               |
| Magicien    | 7             | 7               |

### 1.4 — Les caisses surprises

Les caisses surprises peuvent contenir des équipements offensifs (armes ou sorts) ou des potions. Lorsqu'un personnage arrive sur une case contenant une caisse surprise, il reçoit automatiquement son contenu si les conditions sont remplies.

### 1.5 — Les équipements offensifs

Les armes/sorts sont caractérisés par un niveau d'attaque, qui lorsqu'ils sont utilisés, augmentent d'autant le niveau d'attaque du personnage.

**Armes (spécifiques au Guerrier)** :
- **Massue** : Augmente l'attaque de 3 points.
- **Épée** : Augmente l'attaque de 5 points.

**Sorts (spécifiques au Magicien)** :
- **Éclair** : Augmente l'attaque de 2 points.
- **Boule de feu** : Augmente l'attaque de 7 points.

**Potions (utilisables par tous les personnages)** :
- **Potion de vie standard** : Rend 2 points de vie.
- **Grande potion de vie** : Rend 5 points de vie.

### 1.6 — Les ennemis

Les personnages peuvent être confrontés à des ennemis au cours du jeu.

| Ennemi     | Points de vie | Force d'attaque |
|------------|---------------|-----------------|
| Gobelin    | 5             | 3               |
| Sorcier    | 8             | 5               |
| Dragon     | 15            | 8               |

### 1.7 — Déroulement du jeu

Ce jeu se déroule en mode tour par tour. À chaque tour, le joueur lance un dé virtuel à 6 faces pour connaître le nombre de cases dont il avance :
- **Case vide** : On passe au tour suivant.
- **Caisse surprise** :
  - **Équipement** : L'équipement est ajouté au personnage si et seulement si le personnage est compatible avec cet équipement et que l'équipement actuel est moins avantageux que le nouveau.
  - **Potions** : Le personnage récupère le nombre de points de vie défini par le type de potion.
- **Ennemi** : Le combat s'engage.

### 1.8 — Règle des combats

Le personnage frappe l'ennemi avec la force définie par son équipement (arme ou sorts) et le niveau de vie de l'ennemi diminue en conséquence.
- Si le niveau de vie de l'ennemi atteint 0, il meurt.
- Sinon, l'ennemi réplique et le niveau de vie du personnage diminue en fonction de la force de frappe de l'ennemi. Ce dernier s'enfuit lorsqu'il vous a frappé.

**Note** : Le niveau de vie d'un même ennemi (sur une même case) doit être persistant d'un tour à l'autre. Si le joueur parvient à vaincre totalement un ennemi (niveau de vie à 0), cet ennemi doit disparaître du plateau.

### 1.9 — Règle de fin de partie

- **Victoire** : La partie est gagnée si le joueur arrive au bout du plateau.
- **Défaite** : Si le joueur perd tous ses points de vie, la partie est perdue.

---

## 2 — Version avancée (Pour s'inspirer ou aller plus loin après l'itération 7)

Le jeu, jusqu'à maintenant, est plutôt linéaire. En effet, le joueur ne dispose pas vraiment de choix. Dans cette version, nous proposons donc de changer quelques règles et de rajouter quelques fonctionnalités pour ajouter de la complexité et de la profondeur au jeu. Ces idées sont optionnelles et peuvent être implémentées après l'itération 7.

### 2.1 — Nouvelles règles des combats

Les ennemis ne fuient plus le combat, ils se battent jusqu'à la mort. Ainsi, si le joueur frappe un monstre et que les points de vie de ce dernier sont supérieurs à 0, il inflige alors ses points d'attaque au joueur.

Le joueur peut alors choisir de :
- **Attaquer de nouveau** : Un nouveau tour de combat est lancé.
- **Fuir** : Il recule alors de deux cases.

### 2.2 — Ennemis supplémentaires

- **Orcs** : Ne s'attaquent qu'aux Guerriers.
  - Points de vie : 10
  - Force d'attaque : 6

- **Mauvais esprits** : Ne s'attaquent qu'aux Magiciens.
  - Points de vie : 15
  - Force d'attaque : 4

### 2.3 — Équipements supplémentaires

**Armes (spécifiques au Guerrier)** :
- **Arc** : Augmente l'attaque de 6 points contre les Dragons, et de 4 points contre les autres ennemis.

**Sorts (spécifiques au Magicien)** :
- **Invisibilité** : Augmente l'attaque de 8 points contre les Mauvais esprits et de 5 points contre les autres ennemis.

**Potions (utilisables par tous les personnages)** :
- **Coup de tonnerre** : Double la puissance d'attaque du personnage pour le prochain combat uniquement.

### 2.4 — Gestion de l'inventaire

Le personnage possède maintenant un inventaire ne pouvant contenir que deux sorts pour les Magiciens et deux armes pour les Guerriers. Ainsi, lorsque le personnage arrive sur une case contenant un équipement, il peut décider de ramasser ou non cet objet.

Lors des phases de combat, le joueur pourra choisir l'équipement offensif à utiliser en fonction de son inventaire et de l'adversaire.

### 2.5 — Gestion des difficultés

Le joueur peut choisir la difficulté de sa partie. En fonction de ce choix, la population des bonus, malus et ennemis est adaptée.

### 2.6 — Gestion des niveaux

Ajout de la notion d'expérience du personnage et du montant d'expérience octroyée par la défaite d'un ennemi.

- **Montée de niveau** : Le personnage peut monter de niveau, ce qui lui donne plus de points de vie et de dégâts.
- **Équipements avec niveau minimum** : Les armes et armures peuvent suivre le même modèle et nécessiter un niveau minimum pour être équipées.

### 2.7 — Gestion des marchands

Ajout de la notion d'argent.

- **Marchands** : Sur certaines cases, vous pouvez tomber sur des marchands qui vous permettent de vendre vos objets et d'en acheter d'autres.

### 2.8 — Multi parties

- **Réutilisation du personnage** : Pouvoir réutiliser son personnage pour relancer une autre partie.
- **Déplacement du marchand** : Le marchand se déplace entre les parties.
- **Auberges** : Prévoir des auberges qui ont un coût et qui permettent de restaurer la vie du personnage.

### 2.9 — Multi joueur

- **Plusieurs personnages** : Pouvoir utiliser plusieurs personnages.
  - **Compétition** : Chaque personnage fait sa route, et c'est une sorte de compétition.
  - **Coopération** : Tous les personnages avancent en bande, font les combats ensemble et se répartissent les bonus.

### 2.10 — Usure des objets

- **Durabilité** : Les objets ont une durabilité (par exemple, l'épée s'émousse au bout de 10 coups).
  - **Efficacité réduite** : L'efficacité de l'objet est réduite.
  - **Disparition** : L'objet disparaît.
- **Forgeron** : Notion de forgeron pour réparer les objets avant qu'ils ne cassent.

### 2.11 — Interface graphique

Si votre code est bien architecturé, la partie interaction (classe Menu) qui sert à l'affichage et à la récupération des entrées peut être remplacée par une vraie interface graphique. Vous pouvez utiliser :

- **JavaSwing** : Permet de créer une interface graphique en Java.
- **JavaFX** : Plus moderne que JavaSwing.

**Exemples d'éléments graphiques** :
- **JTextArea** : Pour afficher les infos de la console.
- **JButton** : Boutons "Start Game" et "Exit".
- **Images (Sprites)** : Représentant le joueur et les objets dans son sac.
- **Popup** : Pour saisir les informations attendues.

---

## Notes supplémentaires

- **Créativité** : Vous avez une grande latitude sur l'interprétation des règles. Utilisez-la et prenez des initiatives !
- **Architecture** : Une bonne architecture de code permettra d'ajouter facilement de nouvelles fonctionnalités.
- **Tests** : Testez chaque fonctionnalité pour vous assurer qu'elle fonctionne correctement.

Si vous avez des questions ou besoin de clarifications, n'hésitez pas à demander !
