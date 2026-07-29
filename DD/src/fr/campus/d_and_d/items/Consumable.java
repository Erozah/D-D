/**
 * Represents consumable in the game, such as a potion.
 * This consumable allows the character to heal or boost stats.
 */

package fr.campus.d_and_d.items;

/**
 * Abstract class representing consumable that can be used by characters.
 * Subclasses include heal Potion and buff potion.
 */
public abstract class Consumable {

        private String consumableType = "Health";
        private String name = "Small heal potion";
        private int consumablePoints = 1;

        /**
         * Constructs a new consumable with specified attributes.
         * @param consumableType The type of the consumable (e.g., "Health" or "Strength").
         * @param name The name of the consumable.
         * @param consumablePoints The heal or buff points of the consumable.
         */
        public Consumable(String consumableType, String name, int consumablePoints) {
            this.consumableType = consumableType;
            this.name = name;
            this.consumablePoints = consumablePoints;
        }

        /**
         * Gets the type of the consumable.
         * @return The consumable type.
         */
        public String getConsumableType() {
            return consumableType;
        }

        /**
         * Sets the type of the consumable.
         * @param consumableType The new consumable type.
         */
        public void setConsumableType(String consumableType) {
            this.consumableType = consumableType;
        }

        /**
         * Gets the name of the consumable.
         * @return The consumable name.
         */
        public String getName() {
            return name;
        }

        /**
         * Sets the name of the consumable.
         * @param name The new consumable name.
         */
        public void setName(String name) {
            this.name = name;
        }

        /**
         * Gets the consumable points of the consumable.
         * @return The consumable points.
         */
        public int getConsumablePoints() {
            return consumablePoints;
        }

        /**
         * Sets the consumable points of the consumable.
         * @param consumablePoints The new consumable points.
         */
        public void setConsumablePoints(int consumablePoints) {
            this.consumablePoints = consumablePoints;
        }

        /**
         * Returns a string representation of the consumable.
         * @return A string describing the consumable.
         */
        public String toString() {
            return "[Type : " + consumableType + " ] " + name + ": " + consumablePoints + " dégats réduits.";
        }
}