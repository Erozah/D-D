package fr.campus.d_and_d.board;

/**
 * Interface for all types of cell content in the game.
 * This allows different types of content (enemies, weapons, potions, etc.)
 * to be treated uniformly by the Cell class.
 */
public interface CellContent {
    /**
     * Defines what happens when a player interacts with this content.
     * @return A message describing the interaction
     */
    String interact();
    
    /**
     * Defines what happens when a specific character interacts with this content.
     * @param character The character interacting with this content
     * @return A message describing the interaction
     */
    String interact(fr.campus.d_and_d.characters.Character character);
    
    /**
     * Gets the name of this content.
     * @return The name of the content
     */
    String getName();
}
