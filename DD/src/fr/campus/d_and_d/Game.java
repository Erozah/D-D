/**
 * Gère la logique principale du jeu, incluant le déplacement du joueur sur le plateau.
 * Cette classe utilise un plateau (Board) et un dé (Dice) pour simuler le jeu.
 */
package fr.campus.d_and_d;

import fr.campus.d_and_d.characters.Warrior;
import fr.campus.d_and_d.characters.Wizard;

import java.util.Scanner;

public class Game {
	public void mainMenu() {
		Menu menu = new Menu();
		String choice = menu.askPlayerString("1. Nouveau personnage\n2. Quitter");
		if (choice.equals("1") )
			this.createCharacter();
		choice = menu.askPlayerString("Démarer la partie ? 1. Oui / 2. Non");
		if (choice.equals("1"))
			this.start();
		return;
	}
	public void createCharacter() {
		Menu menu = new Menu();
		String characterName = menu.askPlayerString("Quel est votre nom ?");
		String choice = menu.askPlayerString("Choisissez votre classe :\n1. Guerrier\n2. Magicien");

		if (choice.equals("1")) {
			Warrior warrior = new Warrior("Guerrier", characterName);
			System.out.println(warrior);
			return;
		}
		if (choice.equals("2")) {
			Wizard wizard = new Wizard("Magicien", characterName);
			System.out.println(wizard);
			return;
		}


	}
	/**
	 * Lance la boucle principale du jeu. Le joueur avance sur le plateau en lançant le dé
	 * jusqu'à atteindre la dernière case (64). Affiche la position du joueur à chaque tour.
	 */
	public void start() {
		Board board = new Board();
		Dice dice = new Dice();
		Scanner scanner = new Scanner(System.in);

		while (board.getCurrentCase() < board.getMaxCase()) {
			Menu menu = new Menu();
			menu.askPlayerString("Appuyez sur 'Entrée' pour lancer le dé...");
			int diceResult = dice.roll();
			try {
				board.setCurrentCase(board.getCurrentCase() + diceResult);
				System.out.println(board.toString());
			} catch (OutOfBoardException e) {
				System.out.println("Erreur : " + e.getMessage());
			}
		}
		scanner.close();
		System.out.println("Félicitations ! Vous avez terminé le plateau.");
	}

	@Override
	public String toString() {
		return super.toString();
	}
}
