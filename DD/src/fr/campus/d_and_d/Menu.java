/**
 * Gère l'interface utilisateur du jeu, permettant aux joueurs de créer un personnage
 * et de démarrer une partie.
 */
package fr.campus.d_and_d;

import fr.campus.d_and_d.characters.Warrior;
import fr.campus.d_and_d.characters.Wizard;

import java.util.Scanner;

public class Menu {
	/**
	 * Affiche le menu principal du jeu et gère les choix de l'utilisateur.
	 * Permet de créer un nouveau personnage ou de quitter le jeu.
	 */
	public void displayMenu() {
		System.out.println("1. Nouveau personnage");
		System.out.println("2. Quitter");
		Scanner scanner = new Scanner(System.in);
		int choice = scanner.nextInt();
		if (choice == 1) {
			System.out.println("Quel est votre nom ?");
			String characterName = scanner.next();
			System.out.println("Choisissez votre classe :");
			System.out.println("1. Guerrier");
			System.out.println("2. Magicien");
			choice = scanner.nextInt();
			if (choice == 1) {
				Warrior warrior = new Warrior("Guerrier", characterName);
				System.out.println(warrior);
			} else {
				Wizard wizard = new Wizard("Magicien", characterName);
				System.out.println(wizard);
			}
			System.out.println("Démarer la partie ? 1. Oui / 2. Non");
			choice = scanner.nextInt();
			if (choice == 1)
				new Game().start();
		}
	}
	public Menu() {

	}


	@Override
	public String toString() {
		return super.toString();
	}
}
