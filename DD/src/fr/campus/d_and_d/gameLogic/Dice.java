/**
 * Représente un dé utilisé pour déterminer le déplacement du joueur sur le plateau.
 * Cette classe génère un nombre aléatoire entre 1 et 6.
 */
package fr.campus.d_and_d.gameLogic;

import java.util.Random;

public class Dice {
	/**
	 * Lance le dé et retourne un résultat aléatoire.
	 * @return Un entier entre 1 et 6, représentant le résultat du lancer de dé.
	 */
	public int roll() {
		Random random = new Random();
		return random.nextInt(6) + 1;
	}
	@Override
	public String toString() {
		return "Dice roll from 1 to 6";
	}
}
