package fr.campus.d_and_d.board;

import fr.campus.d_and_d.characters.Character;

/**
 * Represents a mystery box that contains random equipment.
 */
public class MysteryBox implements CellContent {
    private CellContent content;
    
    /**
     * Creates a mystery box with specific content.
     * @param content The equipment inside the box (can be offensive or defensive)
     */
    public MysteryBox(CellContent content) {
        this.content = content;
    }
    
    /**
     * Gets the content of the mystery box.
     * @return The equipment inside the box
     */
    public CellContent getContent() {
        return content;
    }
    
    @Override
    public String interact() {
        return "Vous avez trouvé une boîte mystère contenant: " + content.getName() + "!";
    }
    
    @Override
    public String interact(Character character) {
        if (character == null) {
            return interact();
        }
        return content.interact(character);
    }
    
    @Override
    public String getName() {
        return "MysteryBox(" + content.getName() + ")";
    }
    
    @Override
    public String toString() {
        return getName();
    }
}
