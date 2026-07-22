/**
 * Represents the game board for the Dungeons and Dragons game.
 * The board contains 64 cells, and the player moves by rolling a dice.
 */
package fr.campus.d_and_d.board;
import fr.campus.d_and_d.gameLogic.OutOfBoardException;
import java.util.ArrayList;

/**
 * The Board class manages the game board, including player position and cell interactions.
 */
public class Board {
	private int currentPosition = 1;
	private final int MAX_POSITION = 64;
	private final ArrayList<Cell> cells;

	/**
	 * Constructs a new Board and initializes it with cells.
	 */
	public Board() {
		this.cells = new ArrayList<>();
		initializeBoard();
	}

	/**
	 * Initializes the board by adding cells one by one.
	 * This method sets up the board with a mix of empty cells, enemy cells, weapon cells, potion cells, and a boss cell.
	 */
	public void initializeBoard() {
		cells.add(new EmptyCell());  // Case 1
		cells.add(new EnemyCell());  // Case 2
		cells.add(new WeaponCell()); // Case 3
		cells.add(new PotionCell()); // Case 4
		cells.add(new EmptyCell());  // Case 5
		cells.add(new EnemyCell());  // Case 6
		cells.add(new WeaponCell()); // Case 7
		cells.add(new PotionCell()); // Case 8
		cells.add(new EmptyCell());  // Case 9
		cells.add(new EnemyCell());  // Case 10
		cells.add(new WeaponCell()); // Case 11
		cells.add(new PotionCell()); // Case 12
		cells.add(new EmptyCell());  // Case 13
		cells.add(new EnemyCell());  // Case 14
		cells.add(new WeaponCell()); // Case 15
		cells.add(new PotionCell()); // Case 16
		cells.add(new EmptyCell());  // Case 17
		cells.add(new EnemyCell());  // Case 18
		cells.add(new WeaponCell()); // Case 19
		cells.add(new PotionCell()); // Case 20
		cells.add(new EmptyCell());  // Case 21
		cells.add(new EnemyCell());  // Case 22
		cells.add(new WeaponCell()); // Case 23
		cells.add(new PotionCell()); // Case 24
		cells.add(new EmptyCell());  // Case 25
		cells.add(new EnemyCell());  // Case 26
		cells.add(new WeaponCell()); // Case 27
		cells.add(new PotionCell()); // Case 28
		cells.add(new EmptyCell());  // Case 29
		cells.add(new EnemyCell());  // Case 30
		cells.add(new WeaponCell()); // Case 31
		cells.add(new PotionCell()); // Case 32
		cells.add(new EmptyCell());  // Case 33
		cells.add(new EnemyCell());  // Case 34
		cells.add(new WeaponCell()); // Case 35
		cells.add(new PotionCell()); // Case 36
		cells.add(new EmptyCell());  // Case 37
		cells.add(new EnemyCell());  // Case 38
		cells.add(new WeaponCell()); // Case 39
		cells.add(new PotionCell()); // Case 40
		cells.add(new EmptyCell());  // Case 41
		cells.add(new EnemyCell());  // Case 42
		cells.add(new WeaponCell()); // Case 43
		cells.add(new PotionCell()); // Case 44
		cells.add(new EmptyCell());  // Case 45
		cells.add(new EnemyCell());  // Case 46
		cells.add(new WeaponCell()); // Case 47
		cells.add(new PotionCell()); // Case 48
		cells.add(new EmptyCell());  // Case 49
		cells.add(new EnemyCell());  // Case 50
		cells.add(new WeaponCell()); // Case 51
		cells.add(new PotionCell()); // Case 52
		cells.add(new EmptyCell());  // Case 53
		cells.add(new EnemyCell());  // Case 54
		cells.add(new WeaponCell()); // Case 55
		cells.add(new PotionCell()); // Case 56
		cells.add(new EmptyCell());  // Case 57
		cells.add(new EnemyCell());  // Case 58
		cells.add(new WeaponCell()); // Case 59
		cells.add(new PotionCell()); // Case 60
		cells.add(new EmptyCell());  // Case 61
		cells.add(new EnemyCell());  // Case 62
		cells.add(new WeaponCell()); // Case 63
		cells.add(new BossCell());   // Case 64
	}
	/**
	 * Gets the current position of the player on the board.
	 * @return The current position (between 1 and MAX_POSITION).
	 */
	public int getCurrentPosition() {
		return currentPosition;
	}
	/**
	 * Sets the current position of the player on the board.
	 * @param currentPosition The new position of the player. If the value is less than 1,
	 *                       it is set to 1. If it exceeds MAX_POSITION, an exception is thrown.
	 * @throws OutOfBoardException If the position exceeds the board's maximum position.
	 */
	public void setCurrentPosition(int currentPosition) throws OutOfBoardException {
		if (currentPosition < 1) {
			this.currentPosition = 1;
		} else if (currentPosition > MAX_POSITION) {
			throw new OutOfBoardException("Position " + currentPosition + " exceeds the board limit of " + MAX_POSITION + ".");
		} else {
			this.currentPosition = currentPosition;
		}
	}
	/**
	 * Gets the maximum number of positions on the board.
	 * @return The maximum position (64).
	 */
	public int getMaxPosition() {
		return MAX_POSITION;
	}

	/**
	 * Gets the current cell based on the player's position.
	 * Translates the 1-based position to a 0-based array index.
	 * @return The current Cell object.
	 */
	public Cell getCurrentCell() {
		return cells.get(currentPosition - 1);
	}

	@Override
	public String toString() {
		return "You are on cell " + currentPosition + " / " + MAX_POSITION;
	}
}
