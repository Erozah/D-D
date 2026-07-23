package fr.campus.d_and_d.board;

import fr.campus.d_and_d.items.*;

/**
 * Represents a generic cell on the game board that can contain various types of content.
 * This unified cell class replaces the previous multiple cell type classes.
 */
public class Cell {
    private CellContent content;
    
    /**
     * Creates an empty cell.
     */
    public Cell() {
        this.content = null;
    }
    
    /**
     * Creates a cell with specific content.
     * @param content The content of the cell (enemy, weapon, potion, etc.)
     */
    public Cell(CellContent content) {
        this.content = content;
    }
    
    /**
     * Gets the content of the cell.
     * @return The cell content, or null if empty
     */
    public CellContent getContent() {
        return content;
    }
    
    /**
     * Sets the content of the cell.
     * @param content The new content for the cell
     */
    public void setContent(CellContent content) {
        this.content = content;
    }
    
    /**
     * Checks if the cell is empty.
     * @return true if the cell has no content, false otherwise
     */
    public boolean isEmpty() {
        return content == null;
    }
    
    /**
     * Interacts with the cell content.
     * @return A message describing the interaction
     */
    public String interact(fr.campus.d_and_d.characters.Character character) {
        if (content == null) {
            return "Cette case est vide. Rien ne se passe.";
        }
        return content.interact(character);
    }
    
    public String interact() {
        return interact(null);
    }
    
    @Override
    public String toString() {
        if (content == null) {
            return "Cell[Empty]";
        }
        return "Cell[" + content.getClass().getSimpleName() + ": " + content + "]";
    }
}
