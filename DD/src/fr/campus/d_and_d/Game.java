/**
 * Gère la logique principale du jeu, incluant le déplacement du joueur sur le plateau.
 * Cette classe utilise un plateau (Board) et un dé (Dice) pour simuler le jeu.
 */
package fr.campus.d_and_d;

import java.util.Scanner;

public class Game {
	/**
	 * Lance la boucle principale du jeu. Le joueur avance sur le plateau en lançant le dé
	 * jusqu'à atteindre la dernière case (64). Affiche la position du joueur à chaque tour.
	 */
	public void start() {
		Board board = new Board();
		Dice dice = new Dice();
		Scanner scanner = new Scanner(System.in);

		while (board.getCurrentCase() < board.getMaxCase()) {
			System.out.println("Appuyez sur 'Entrée' pour lancer le dé...");
			scanner.nextLine();
			int diceResult = dice.roll();
			board.setCurrentCase(board.getCurrentCase() + diceResult);
			System.out.println(board.toString());
		}
		scanner.close();
		System.out.println("Félicitations ! Vous avez terminé le plateau.");
	}

	@Override
	public String toString() {
		return super.toString();
	}
}
