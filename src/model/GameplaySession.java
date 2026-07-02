package model;

import model.entity.plant.Plant;
import model.entity.projectile.Projectile;
import model.entity.zombie.Zombie;
import model.enums.TileType;
import model.factory.PlantFactory;
import model.factory.ZombieFactory;
import model.special.SpecialMechanic;
import model.special.SpecialMechanicFactory;
import model.sun.SunSystem;

import java.util.ArrayList;
import java.util.Iterator;
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
    private SunSystem sunSystem;
    private int plantFoodCount;
    private List<Plant> selectedPlants;
    private List<Projectile> activeProjectiles;
    private List<Zombie> activeZombies;
    private int coins;
    private int gems;
    private int pots;
    private int playerScore;
    private int defeatedZombies;
    private String lastDropMessage;
    private boolean[] lawnMowersUsed;
    private int nextSpawnRow;
    private boolean lost;
    private SpecialMechanic specialMechanic;

    public GameplaySession() {
        this(null, new Level(), List.of(PlantFactory.create("basic")));
    }

    public GameplaySession(Chapter chapter, Level level, List<Plant> selectedPlants) {
        this.chapter = chapter;
        this.level = level == null ? new Level() : level;
        this.board = createBoard(this.level.getRows(), this.level.getCols());
        this.currentWave = new Wave(1, this.level.getWaveCost(1));
        this.totalWaves = this.level.getNumWaves();
        this.tickCount = 0;
        this.sunAmount = 150;
        this.sunSystem = new SunSystem(sunAmount);
        this.plantFoodCount = 0;
        this.selectedPlants = new ArrayList<>(selectedPlants);
        this.activeProjectiles = new ArrayList<>();
        this.activeZombies = new ArrayList<>();
        this.coins = 0;
        this.gems = 0;
        this.pots = 0;
        this.playerScore = 0;
        this.defeatedZombies = 0;
        this.lastDropMessage = "";
        this.lawnMowersUsed = new boolean[this.level.getRows()];
        this.nextSpawnRow = 0;
        this.lost = false;
        this.specialMechanic = SpecialMechanicFactory.create(this.level.getType(), this.level);
        this.specialMechanic.init();
        this.specialMechanic.onWaveStart(currentWave.getWaveNumber());
    }

    public Chapter getChapter() {
        return chapter;
    }

    public Level getLevel() {
        return level;
    }

    public Tile[][] getBoard() {
        return board;
    }

    public Wave getCurrentWave() {
        return currentWave;
    }

    public String getSpecialMechanicKey() {
        return specialMechanic.getKey();
    }

    /**
     * Progress to next wave
     */
    public void nextWave() {
        specialMechanic.onWaveEnd(currentWave.getWaveNumber());
        int nextWaveNumber = currentWave.getWaveNumber() + 1;
        if (nextWaveNumber <= totalWaves) {
            currentWave = new Wave(nextWaveNumber, level.getWaveCost(nextWaveNumber));
            specialMechanic.onWaveStart(nextWaveNumber);
            spawnWave();
        } else {
            currentWave = new Wave(totalWaves + 1, 0);
        }
    }

    /**
     * Check if all waves are complete
     */
    public boolean isComplete() {
        return currentWave.getWaveNumber() > totalWaves;
    }

    public int getTotalWaves() {
        return totalWaves;
    }

    public boolean isFinalWave() {
        return currentWave.getWaveNumber() == totalWaves;
    }

    /**
     * Check if wave should progress to next
     */
    public boolean shouldProgressToNextWave(int totalZombieHealthDefeated) {
        return activeZombies.isEmpty() || totalZombieHealthDefeated >= currentWave.getWaveCost() * 7;
    }

    public int getTickCount() {
        return tickCount;
    }

    /**
     * Advance game time by specified ticks
     */
    public void advanceTime(int ticks) {
        for (int i = 0; i < ticks && !hasWon() && !hasLost(); i++) {
            tickCount++;
            specialMechanic.onTick();
            currentWave.update();
            sunSystem.update(tickCount);
            sunAmount = sunSystem.getTotalSun();
            if (tickCount == 1 || tickCount % 5 == 0) {
                spawnWave();
            }
            plantsAttack();
            updateProjectiles();
            moveAndAttackZombies();
            cleanupDeadZombies();
            if (activeZombies.isEmpty() && currentWave.getWaveNumber() <= totalWaves && tickCount > 1) {
                nextWave();
            }
        }
    }

    public int getSunAmount() {
        return sunAmount;
    }

    public void addSun(int amount) {
        if (amount > 0) {
            sunAmount += amount;
            sunSystem = new SunSystem(sunAmount);
        }
    }

    public boolean removeSun(int amount) {
        if (!sunSystem.consumeSun(amount)) {
            return false;
        }
        sunAmount = sunSystem.getTotalSun();
        return true;
    }

    public int getPlantFoodCount() {
        return plantFoodCount;
    }

    public int getCoins() {
        return coins;
    }

    public int getGems() {
        return gems;
    }

    public int getPots() {
        return pots;
    }

    public int getPlayerScore() {
        return playerScore;
    }

    /**
     * Add plant food to inventory
     */
    public void addPlantFood(int amount) {
        if (amount > 0) {
            plantFoodCount = Math.min(3, plantFoodCount + amount);
        }
    }

    /**
     * Use plant food on a plant
     */
    public boolean usePlantFood(int x, int y) {
        if (!isInsideBoard(x, y) || plantFoodCount <= 0 || board[y][x].getPlant() == null) {
            return false;
        }
        board[y][x].getPlant().feedPlantFood();
        plantFoodCount--;
        return true;
    }

    public List<Plant> getSelectedPlants() {
        return selectedPlants;
    }

    public Plant getSelectedPlant(String type) {
        String normalized = normalizePlantType(type);
        for (Plant plant : selectedPlants) {
            if (normalizePlantType(plant.getName()).equals(normalized)
                    || normalizePlantType(plant.getName()).contains(normalized)) {
                return plant;
            }
        }
        return null;
    }

    public void removePlacementCooldowns() {
        for (Plant plant : selectedPlants) {
            plant.makePlacementReady();
        }
    }

    public List<Zombie> getActiveZombies() {
        return activeZombies;
    }

    public boolean spawnZombie(String type, int x, int y) {
        if (!isInsideBoard(x, y)) {
            return false;
        }
        Zombie zombie = ZombieFactory.create(type);
        board[y][x].addZombie(zombie);
        activeZombies.add(zombie);
        return true;
    }

    /**
     * Plant a plant at the specified location
     */
    public boolean plantAt(Plant plant, int x, int y) {
        if (plant == null || !isInsideBoard(x, y) || !board[y][x].canPlacePlant()) {
            return false;
        }
        if (!plant.isReady()) {
            return false;
        }
        if (!removeSun(plant.getSunCost())) {
            return false;
        }
        board[y][x].setPlant(createBoardPlant(plant));
        plant.resetPlacementCooldown();
        return true;
    }

    /**
     * Pluck/remove a plant at the specified location
     */
    public boolean pluckAt(int x, int y) {
        if (!isInsideBoard(x, y) || board[y][x].getPlant() == null) {
            return false;
        }
        board[y][x].removePlant();
        return true;
    }

    /**
     * Collect sun from the specified location
     */
    public boolean collectSun(int x, int y) {
        if (!isInsideBoard(x, y)) {
            return false;
        }
        boolean collected = sunSystem.collectSun(x, y);
        sunAmount = sunSystem.getTotalSun();
        return collected;
    }

    /**
     * Check if the player has won
     */
    public boolean hasWon() {
        return isComplete() && activeZombies.isEmpty() && !lost;
    }

    /**
     * Check if the player has lost
     */
    public boolean hasLost() {
        return lost;
    }

    /**
     * Get the session state as a string representation
     */
    public String getSessionState() {
        StringBuilder builder = new StringBuilder();
        builder.append("tick=").append(tickCount)
                .append(" wave=").append(Math.min(currentWave.getWaveNumber(), totalWaves)).append("/").append(totalWaves)
                .append(" sun=").append(sunAmount)
                .append(" drops=").append(sunSystem.getDroppingSunCount())
                .append(" plantFood=").append(plantFoodCount)
                .append(" coins=").append(coins)
                .append(" gems=").append(gems)
                .append(" pots=").append(pots)
                .append(" defeated=").append(defeatedZombies)
                .append(" projectiles=").append(activeProjectiles.size())
                .append(" zombies=").append(activeZombies.size())
                .append(" mechanic=").append(getSpecialMechanicKey())
                .append(System.lineSeparator());
        if (!lastDropMessage.isBlank()) {
            builder.append(lastDropMessage).append(System.lineSeparator());
        }
        for (int y = 0; y < board.length; y++) {
            builder.append(y + 1).append(" ");
            for (int x = 0; x < board[y].length; x++) {
                boolean hasPlant = board[y][x].getPlant() != null;
                boolean hasZombie = !board[y][x].getZombies().isEmpty();
                boolean hasProjectile = hasProjectileAt(x, y);
                if (hasPlant && hasZombie) {
                    builder.append("X");
                } else if (sunSystem.hasSunAt(x, y)) {
                    builder.append("S");
                } else if (hasProjectile) {
                    builder.append("*");
                } else if (hasPlant) {
                    builder.append("P");
                } else if (hasZombie) {
                    builder.append("Z");
                } else {
                    builder.append(".");
                }
                builder.append(" ");
            }
            builder.append(System.lineSeparator());
        }
        builder.append("  1 2 3 4 5 6 7 8 9");
        if (hasWon()) {
            builder.append(System.lineSeparator()).append("You won!");
        } else if (hasLost()) {
            builder.append(System.lineSeparator()).append("You lost!");
        }
        return builder.toString();
    }

    private Tile[][] createBoard(int rows, int cols) {
        Tile[][] tiles = new Tile[rows][cols];
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {
                tiles[y][x] = new Tile(x, y, TileType.GRASS);
            }
        }
        return tiles;
    }

    private void spawnWave() {
        List<Zombie> zombies = currentWave.spawnWave();
        for (Zombie zombie : zombies) {
            int row = nextSpawnRow % board.length;
            nextSpawnRow++;
            int col = board[row].length - 1;
            board[row][col].addZombie(zombie);
            activeZombies.add(zombie);
        }
    }

    private void plantsAttack() {
        for (Tile[] row : board) {
            for (Tile tile : row) {
                Plant plant = tile.getPlant();
                if (plant == null) {
                    continue;
                }
                plant.update();
                Zombie target = firstZombieInRowAtOrAfter(tile.getY(), tile.getX());
                if (target != null && plant.canAttack()) {
                    activeProjectiles.add(plant.createProjectile());
                }
            }
        }
    }

    private void updateProjectiles() {
        Iterator<Projectile> iterator = activeProjectiles.iterator();
        while (iterator.hasNext()) {
            Projectile projectile = iterator.next();
            projectile.update();
            Zombie target = firstZombieInRowAtOrAfter(projectile.getY(), projectile.getX());
            if (target != null && projectile.isAt(target.getX(), target.getY())) {
                target.takeDamage(projectile.getDamage());
                projectile.deactivate();
            }
            if (!projectile.isActive() || projectile.getX() >= level.getCols()) {
                iterator.remove();
            }
        }
    }

    private boolean hasProjectileAt(int x, int y) {
        for (Projectile projectile : activeProjectiles) {
            if (projectile.isActive() && projectile.getX() == x && projectile.getY() == y) {
                return true;
            }
        }
        return false;
    }

    private Zombie firstZombieInRowAtOrAfter(int row, int col) {
        for (int x = col; x < board[row].length; x++) {
            if (!board[row][x].getZombies().isEmpty()) {
                return board[row][x].getZombies().get(0);
            }
        }
        return null;
    }

    private void moveAndAttackZombies() {
        List<Zombie> snapshot = new ArrayList<>(activeZombies);
        for (Zombie zombie : snapshot) {
            if (!zombie.isAlive()) {
                continue;
            }
            int oldX = zombie.getX();
            int y = zombie.getY();
            if (!isInsideBoard(oldX, y)) {
                lost = true;
                continue;
            }
            Plant plant = board[y][oldX].getPlant();
            if (plant != null) {
                plant.takeDamage(zombie.getDamage());
                if (!plant.isAlive()) {
                    board[y][oldX].removePlant();
                }
                continue;
            }
            board[y][oldX].removeZombie(zombie);
            zombie.update();
            if (zombie.getX() < 0) {
                if (lawnMowersUsed[y]) {
                    lost = true;
                } else {
                    lawnMowersUsed[y] = true;
                    activeZombies.remove(zombie);
                }
            } else {
                board[y][zombie.getX()].addZombie(zombie);
            }
        }
    }

    private void cleanupDeadZombies() {
        Iterator<Zombie> iterator = activeZombies.iterator();
        while (iterator.hasNext()) {
            Zombie zombie = iterator.next();
            if (zombie.isAlive()) {
                continue;
            }
            if (isInsideBoard(zombie.getX(), zombie.getY())) {
                board[zombie.getY()][zombie.getX()].removeZombie(zombie);
            }
            iterator.remove();
            defeatedZombies++;
            coins += 5;
            lastDropMessage = "A zombie dropped a coin; you have " + coins + " coins now.";
            if (zombie.isGlowing()) {
                addPlantFood(1);
                lastDropMessage = "The glowing zombie dropped a plant food; you have "
                        + plantFoodCount + " plant foods now.";
            } else if (defeatedZombies % 5 == 0) {
                pots++;
                lastDropMessage = "A zombie dropped a pot; you have " + pots + " pots now.";
            } else if (defeatedZombies % 3 == 0) {
                gems++;
                lastDropMessage = "A zombie dropped a diamond; you have " + gems + " diamonds now.";
            }
            playerScore += 10;
        }
    }

    private boolean isInsideBoard(int x, int y) {
        return y >= 0 && y < board.length && x >= 0 && x < board[y].length;
    }

    private Plant createBoardPlant(Plant template) {
        Plant plant = PlantFactory.create(typeFromName(template.getName()));
        for (int level = 1; level < template.getLevel(); level++) {
            plant.upgrade();
        }
        return plant;
    }

    private String typeFromName(String name) {
        String normalized = normalizePlantType(name);
        if (normalized.contains("shooter")) {
            return "shooter";
        }
        if (normalized.contains("slow")) {
            return "slow";
        }
        return "basic";
    }

    private String normalizePlantType(String plantType) {
        return plantType == null ? "" : plantType.trim().toLowerCase().replace(" plant", "");
    }
}
