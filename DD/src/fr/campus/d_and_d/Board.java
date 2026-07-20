/**
 * Représente le plateau de jeu du jeu Donjons et Dragons.
 * Le plateau contient 64 cases, et le joueur avance en lançant un dé.
 */
package fr.campus.d_and_d;

public class Board {
	/** Position actuelle du joueur sur le plateau. */
	private int currentCase = 1;

	/** Nombre total de cases sur le plateau. */
	private final int MAX_CASE = 64;
	public Board() {

	}

	/**
	 * Retourne la position actuelle du joueur.
	 * @return La position actuelle (entre 1 et MAX_CASE).
	 */
	public int getCurrentCase() {
		return currentCase;
	}

	/**
	 * Définit la position actuelle du joueur sur le plateau.
	 * @param currentCase La nouvelle position du joueur. Si la valeur est inférieure à 1,
	 *                    elle est définie à 1. Si elle est supérieure à MAX_CASE, elle est définie à MAX_CASE.
	 */
	public void setCurrentCase(int currentCase) {
		if (currentCase < 1)
			this.currentCase = 1;
		else if (currentCase > MAX_CASE)
			this.currentCase = MAX_CASE;
		else 
			this.currentCase = currentCase;
	}
	/**
	 * Retourne le nombre total de cases sur le plateau.
	 * @return Le nombre total de cases (64).
	 */
	public int getMaxCase() {
		return MAX_CASE;
	}

	@Override
	public String toString() {
		return "Vous êtes sur la case " + getCurrentCase() + " / " + MAX_CASE;
	}
}
