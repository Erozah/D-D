package fr.campus.d_and_d.gameLogic;

/**
 * Tracks the game state, including boss status and other game-wide information.
 * This class helps manage game progression and victory conditions.
 */
public class GameState {
    private static GameState instance;
    private boolean bossDefeated;
    private String lastEnemyName;
    
    /**
     * Private constructor for singleton pattern
     */
    private GameState() {
        this.bossDefeated = false;
        this.lastEnemyName = "";
    }
    
    /**
     * Get the singleton instance
     * @return The GameState instance
     */
    public static GameState getInstance() {
        if (instance == null) {
            instance = new GameState();
        }
        return instance;
    }
    
    /**
     * Check if the boss has been defeated
     * @return true if boss is defeated, false otherwise
     */
    public boolean isBossDefeated() {
        return bossDefeated;
    }
    
    /**
     * Set the boss defeated status
     * @param defeated true if boss is defeated, false otherwise
     */
    public void setBossDefeated(boolean defeated) {
        this.bossDefeated = defeated;
    }
    
    /**
     * Get the name of the last enemy encountered
     * @return The last enemy name
     */
    public String getLastEnemyName() {
        return lastEnemyName;
    }
    
    /**
     * Set the name of the last enemy encountered
     * @param enemyName The enemy name to set
     */
    public void setLastEnemyName(String enemyName) {
        this.lastEnemyName = enemyName;
    }

    /**
     * Reset the game state
     */
    public void reset() {
        this.bossDefeated = false;
        this.lastEnemyName = "";
    }
}