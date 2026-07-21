package fr.campus.d_and_d.gameLogic;

/**
 * Exception levée quand le joueur dépasse la dernière case
 */
public class OutOfBoardException extends Exception {
	/**
	 * crée une nouvelle exception avec un message personnalisé.
	 * @param message Le message décrivant l'erreur.
	 */
	public OutOfBoardException(String message) {
		super(message);
	}
}
