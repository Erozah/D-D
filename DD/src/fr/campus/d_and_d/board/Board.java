package fr.campus.d_and_d.board;

import fr.campus.d_and_d.characters.enemy.*;
import fr.campus.d_and_d.gameLogic.OutOfBoardException;
import fr.campus.d_and_d.items.consumable.Potion;
import fr.campus.d_and_d.items.offensif.Spell;
import fr.campus.d_and_d.items.offensif.Weapon;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

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
     * Initializes the board by adding cells with random content.
     */
    public void initializeBoard() {
        cells.clear();

        List<CellContent> contents = createRandomContents();
        Collections.shuffle(contents);

        // Cases 1 à 63
        for (CellContent content : contents) {
            cells.add(new Cell(content));
        }

        // Case 64 : toujours le boss
        cells.add(new Cell(new Dragon("Smaug")));
    }
    
    /**
     * Gets the content for a specific position on the board.
     * This implements the predefined board layout with enemies, weapons, potions, etc.
     * @return The content for that position, or null if empty
     */
    private List<CellContent> createRandomContents() {
        List<CellContent> contents = new ArrayList<>();

        // Sorciers (10)
        for (int i = 0; i < 10; i++) {
            contents.add(new Sorcerer("Sorcier noir"));
        }
        // Orcs (4)
        for (int i = 0; i < 4; i++) {
            contents.add(new Orc("Orc"));
        }
        // Esprits (4)
        for (int i = 0; i < 4; i++) {
            contents.add(new EvilSpirit("Esprit"));
        }
        // Gobelins (10)
        for (int i = 0; i < 10; i++) {
            contents.add(new Goblin("Goblin"));
        }
        // Massues (5)
        for (int i = 0; i < 5; i++) {
            contents.add(new MysteryBox(new Weapon("Arme", "Massue", 5)));
        }
        // Épées (3)
        for (int i = 0; i < 4; i++) {
            contents.add(new MysteryBox(new Weapon("Arme", "Epée", 7)));
        }
        // Éclairs (5)
        for (int i = 0; i < 5; i++) {
            contents.add(new MysteryBox(new Spell("Sort", "Eclair", 5)));
        }
        // Boules de feu (3)
        for (int i = 0; i < 2; i++) {
            contents.add(new MysteryBox(new Spell("Sort", "Boule de feu", 8)));
        }
        // Petites potions (5)
        for (int i = 0; i < 6; i++) {
            contents.add(new MysteryBox(new Potion("Potion", "Petite potion", 5)));
        }
        // Grandes potions (3)
        for (int i = 0; i < 2; i++) {
            contents.add(new MysteryBox(new Potion("Potion", "Grande potion", 10)));
        }
        // Cases vides : 63 cases au total avant le Dragon
        while (contents.size() < MAX_POSITION - 1) {
            contents.add(null);
        }
        return contents;
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
