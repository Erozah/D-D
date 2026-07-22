package fr.campus.d_and_d.gameLogic;

/**
 * Exception thrown when the player exceeds the board's maximum position.
 */
public class OutOfBoardException extends Exception {
	/**
	 * Creates a new exception with a custom message.
	 * @param message The message describing the error.
	 */
	public OutOfBoardException(String message) {
		super(message);
	}
}
