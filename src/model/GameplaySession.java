package model;

import model.entity.plant.Plant;
import model.entity.zombie.Zombie;
import java.util.List;

/**
 * Represents a single active game session.
 * Manages the current game board, zombies, plants, resources, and game state
 * during gameplay.
 */
public class GameplaySession {
    private Chapter chapter;
    private Level level;
    private Tile[][] board;
    private Wave currentWave;
    private int totalWaves;
    private int tickCount;
    private int sunAmount;
    private int plantFoodCount;
    private List<Plant> selectedPlants;
    private List<Zombie> activeZombies;
    private int coins;
    private int gems;
    private int playerScore;
    private boolean[] lawnMowersUsed;

    public Chapter getChapter() {
        // TODO: Implementation
        return chapter;
    }

    public Level getLevel() {
        // TODO: Implementation
        return level;
    }

    public Tile[][] getBoard() {
        // TODO: Implementation
        return board;
    }

    public Wave getCurrentWave() {
        // TODO: Implementation
        return currentWave;
    }

    /**
     * Progress to next wave
     */
    public void nextWave() {
        // TODO: Implementation
    }

    /**
     * Check if all waves are complete
     */
    public boolean isComplete() {
        // TODO: Implementation
        return currentWave.getWaveNumber() > totalWaves;
    }

    public int getTotalWaves() {
        // TODO: Implementation
        return totalWaves;
    }

    public boolean isFinalWave() {
        // TODO: Implementation
        return currentWave.getWaveNumber() == totalWaves;
    }

    /**
     * Check if wave should progress to next
     */
    public boolean shouldProgressToNextWave(int totalZombieHealthDefeated) {
        // TODO: Implementation - 75% health destroyed
        return false;
    }

    public int getTickCount() {
        // TODO: Implementation
        return tickCount;
    }

    /**
     * Advance game time by specified ticks
     */
    public void advanceTime(int ticks) {
        // TODO: Implementation - Update all entities, spawn zombies, etc.
    }

    public int getSunAmount() {
        // TODO: Implementation
        return sunAmount;
    }

    public void addSun(int amount) {
        // TODO: Implementation
    }

    public boolean removeSun(int amount) {
        // TODO: Implementation
        return false;
    }

    public int getPlantFoodCount() {
        // TODO: Implementation
        return plantFoodCount;
    }

    /**
     * Add plant food to inventory
     */
    public void addPlantFood(int amount) {
        // TODO: Implementation - Max 3
    }

    /**
     * Use plant food on a plant
     */
    public boolean usePlantFood(int x, int y) {
        // TODO: Implementation
        return false;
    }

    public List<Plant> getSelectedPlants() {
        // TODO: Implementation
        return selectedPlants;
    }

    public List<Zombie> getActiveZombies() {
        // TODO: Implementation
        return activeZombies;
    }

    /**
     * Plant a plant at the specified location
     */
    public boolean plantAt(Plant plant, int x, int y) {
        // TODO: Implementation - Check costs, cooldowns, position validity
        return false;
    }

    /**
     * Pluck/remove a plant at the specified location
     */
    public boolean pluckAt(int x, int y) {
        // TODO: Implementation
        return false;
    }

    /**
     * Collect sun from the specified location
     */
    public boolean collectSun(int x, int y) {
        // TODO: Implementation
        return false;
    }

    /**
     * Check if the player has won
     */
    public boolean hasWon() {
        // TODO: Implementation - All waves completed and all zombies dead
        return false;
    }

    /**
     * Check if the player has lost
     */
    public boolean hasLost() {
        // TODO: Implementation - Lawn mower triggered a second time or zombie reached
        // end
        return false;
    }

    /**
     * Get the session state as a string representation
     */
    public String getSessionState() {
        // TODO: Implementation - Return formatted board state for display
        return "";
    }
}
