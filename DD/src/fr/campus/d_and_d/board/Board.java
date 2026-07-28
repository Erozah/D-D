package fr.campus.d_and_d.board;

import fr.campus.d_and_d.characters.*;
import fr.campus.d_and_d.items.*;
import fr.campus.d_and_d.gameLogic.OutOfBoardException;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents the game board with a unified cell system.
 * Each cell can contain various types of content (enemies, weapons, potions, etc.)
 */
public class Board {
    private int currentPosition = 1;
    private final int MAX_POSITION = 64;
    private final List<Cell> cells;
    
    /**
     * Constructs a new Board and initializes it with cells.
     */
    public Board() {
        this.cells = new ArrayList<>();
        initializeBoard();
    }
    
    /**
     * Constructs a board with a specific size.
     * @param size The size of the board
     */
    public Board(int size) {
        this.cells = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            cells.add(new Cell());
        }
    }
    
    /**
     * Initializes the board by adding cells with predefined content.
     */
    public void initializeBoard() {
        for (int i = 0; i < MAX_POSITION; i++) {
            cells.add(new Cell(getContentForPosition(i + 1)));
        }
    }
    
    /**
     * Gets the content for a specific position on the board.
     * This implements the predefined board layout with enemies, weapons, potions, etc.
     * @param position The position on the board (1-64)
     * @return The content for that position, or null if empty
     */
    private CellContent getContentForPosition(int position) {
        return switch (position) {
            case 64 -> new Dragon("Smaug");
            case 10, 20, 25, 32, 35, 36, 37, 40, 44, 47 -> new Sorcerer("Sorcier noir");
            case 50, 55 -> new Orc("Orc");
            case 54, 59 -> new EvilSpirit("Esprit");
            case 3, 6, 9, 12, 15, 18, 21, 24, 27, 30 -> new Goblin("Goblin");
            case 2, 11, 5, 22, 38 -> new MysteryBox(new Weapon("Arme", "Massue", 5));
            case 19, 26, 42, 53 -> new MysteryBox(new Weapon("Arme", "Epée", 7));
            case 1, 4, 8, 17, 23 -> new MysteryBox(new Spell("Sort", "Eclair", 5));
            case 48, 49 -> new MysteryBox(new Spell("Sort", "Boule de feu", 8));
            case 7, 13, 31, 33, 39, 43 -> new MysteryBox(new Potion("Potion", "Petite potion", 5));
            case 28, 41 -> new MysteryBox(new Potion("Potion", "Grande potion", 10));
            default -> null;
        };
    }
    
    /**
     * Gets the current position of the player on the board.
     * @return The current position (between 1 and MAX_POSITION)
     */
    public int getCurrentPosition() {
        return currentPosition;
    }
    
    /**
     * Sets the current position of the player on the board.
     * @param currentPosition The new position of the player
     * @throws OutOfBoardException If the position exceeds the board's maximum position
     */
    public void setCurrentPosition(int currentPosition) throws OutOfBoardException {
        if (currentPosition < 1) {
            this.currentPosition = 1;
        } else if (currentPosition > MAX_POSITION) {
            throw new OutOfBoardException("La position " + currentPosition + " dépasse la limite du plateau de " + MAX_POSITION + ".");
        } else {
            this.currentPosition = currentPosition;
        }
    }
    
    /**
     * Gets the maximum number of positions on the board.
     * @return The maximum position (64)
     */
    public int getMaxPosition() {
        return MAX_POSITION;
    }
    
    /**
     * Gets the current cell based on the player's position.
     * @return The current Cell object
     */
    public Cell getCurrentCell() {
        return cells.get(currentPosition - 1);
    }
    
    /**
     * Gets a specific cell by position.
     * @param position The position of the cell (1-64)
     * @return The Cell at the specified position
     */
    public Cell getCell(int position) {
        if (position >= 1 && position <= MAX_POSITION) {
            return cells.get(position - 1);
        }
        return null;
    }
    
    /**
     * Sets the content of a specific cell.
     * @param position The position of the cell (1-64)
     * @param content The content to set
     */
    public void setCellContent(int position, CellContent content) {
        if (position >= 1 && position <= MAX_POSITION) {
            cells.get(position - 1).setContent(content);
        }
    }
    
    /**
     * Returns a string representation of the board's current state.
     * The string includes the current position and the maximum position.
     * 
     * @return A string in the format "Vous êtes sur la case X / Y" where X is the current position
     *         and Y is the maximum position (64)
     */
    @Override
    public String toString() {
        return "Vous êtes sur la case " + currentPosition + " / " + MAX_POSITION;
    }
}
