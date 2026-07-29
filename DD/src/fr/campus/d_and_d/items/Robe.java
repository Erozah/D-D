/**
 * Represents a robe in the game, which is a type of defensive equipment.
 */
package fr.campus.d_and_d.items;

import fr.campus.d_and_d.board.CellContent;
import fr.campus.d_and_d.characters.Character;

/**
 * A Robe is a defensive equipment that characters can use to protect themselves.
 * Examples include silk robes, leather robes, and magical robes.
 */
public class Robe extends DefensiveEquipment implements CellContent {
    /**
     * Constructs a new Robe with specified attributes.
     * @param robeType The type of the robe (e.g., "Silk" or "Leather").
     * @param name The name of the robe.
     * @param defensePoints The defense points provided by the robe.
     */
    public Robe(String robeType, String name, int defensePoints) {
        super(robeType, name, defensePoints);
    }

    @Override
    public String toString() {
        return getName();
    }

    @Override
    public String interact() {
        return "Vous avez trouvé une robe: " + getName() + "! Votre défense augmente.";
    }

    @Override
    public String interact(Character character) {
        if (character == null) {
            return interact();
        }

        // Any character can pick up robes
        character.setDefensiveEquipment(this);
        return "Vous avez équipé la robe: " + getName() + "! Votre défense est maintenant de " + getDefensePoints() + ".";
    }
}
