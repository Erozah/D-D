/**
 * Gère l'interface utilisateur du jeu, permettant aux joueurs de créer un personnage
 * et de démarrer une partie.
 */
package fr.campus.d_and_d;

import fr.campus.d_and_d.characters.Warrior;
import fr.campus.d_and_d.characters.Wizard;

import java.util.Scanner;

public class Menu {
	public Menu() {

	}
	public String askPlayerString(String textMessage) {
		System.out.println(textMessage + "\n>");
		Scanner scanner = new Scanner(System.in);
		return scanner.nextLine();
	}

	@Override
	public String toString() {
		return super.toString();
	}
}
