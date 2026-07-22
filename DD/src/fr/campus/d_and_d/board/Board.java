/**
 * Représente le plateau de jeu du jeu Donjons et Dragons.
 * Le plateau contient 64 cases, et le joueur avance en lançant un dé.
 */
package fr.campus.d_and_d.board;
import fr.campus.d_and_d.gameLogic.OutOfBoardException;
import java.util.ArrayList;

public class Board {
	private int currentCase = 1;
	private final int MAX_CASE = 64;
	private ArrayList<Cell> cells;
	public Board() {
		this.cells = new ArrayList<>();
		initializeBoard();
	}

	/**
	 * Ajoute les cases 1 à 1 en brute force pour pouvoir modifier plus tard
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
		cells.add(new PotionCell()); // Case 64
	}
	/**
	 * @return La position actuelle du joueur(entre 1 et MAX_CASE).
	 */
	public int getCurrentCase() {
		return currentCase;
	}
	/**
	 * Définit la position actuelle du joueur sur le plateau.
	 * @param currentCase La nouvelle position du joueur. Si la valeur est inférieure à 1,
	 *                    elle est définie à 1. Si elle est supérieure à MAX_CASE, elle est définie à MAX_CASE.
	 */
	public void setCurrentCase(int currentCase) throws OutOfBoardException {
		if (currentCase < 1)
			this.currentCase = 1;
		else if (currentCase > MAX_CASE) {
			throw new OutOfBoardException("La position " + currentCase + " dépasse la limite du plateau de " + MAX_CASE + ".");
		}
		else
			this.currentCase = currentCase;
	}
	/**
	 * Retourne le nombre total de cases sur le plateau.
	 */
	public int getMaxCase() {
		return MAX_CASE;
	}

	/**
	 * Transpose les cases de 1 à 64 en array de 0 à 63
	 */
	public Cell getCurrentCell() {
		return cells.get(currentCase - 1);
	}

	@Override
	public String toString() {
		return "Vous êtes sur la case " + getCurrentCase() + " / " + MAX_CASE;
	}
}
