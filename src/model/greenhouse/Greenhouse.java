package model.greenhouse;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Manages the player's greenhouse where plants can be grown to gain rewards.
 * Contains 20 plant pots arranged in 4 rows and 5 columns.
 * Pots can be unlocked with coins and grow plants over time.
 */
public class Greenhouse {
    private static final int ROWS = 4;
    private static final int COLS = 5;
    private static final int TOTAL_POTS = 20;

    private GreenhousePot[][] pots;
    private Map<Integer, Integer> unlockedPots; // potId -> true if unlocked
    private final Random random = new Random();

    public Greenhouse() {
        pots = new GreenhousePot[ROWS][COLS];
        unlockedPots = new HashMap<>();
        for (int y = 0; y < ROWS; y++) {
            for (int x = 0; x < COLS; x++) {
                boolean unlocked = y == 0;
                pots[y][x] = new GreenhousePot(x, y, unlocked);
                unlockedPots.put(potId(x, y), unlocked ? 1 : 0);
            }
        }
    }

    public GreenhousePot getPot(int x, int y) {
        if (!isInside(x, y)) {
            return null;
        }
        return pots[y][x];
    }

    public boolean isPotUnlocked(int x, int y) {
        return isInside(x, y) && pots[y][x].isUnlocked();
    }

    /**
     * Unlock a pot with coins
     */
    public boolean unlockPot(int x, int y, int coinsAvailable) {
        if (!isInside(x, y) || pots[y][x].isUnlocked() || coinsAvailable < 2000) {
            return false;
        }
        pots[y][x].unlock();
        unlockedPots.put(potId(x, y), 1);
        return true;
    }

    /**
     * Plant a random plant in a pot
     */
    public boolean plantInPot(int x, int y) {
        if (!isInside(x, y)) {
            return false;
        }
        String plantType = random.nextBoolean() ? "marigold" : "basic";
        return pots[y][x].plant(plantType);
    }

    /**
     * Collect a fully grown plant from a pot
     */
    public Integer collectPlant(int x, int y) {
        if (!isInside(x, y)) {
            return null;
        }
        String plantType = pots[y][x].collect();
        if (plantType == null) {
            return null;
        }
        return "marigold".equals(plantType) ? 50 : 1;
    }

    /**
     * Grow a pot instantly for gems
     */
    public boolean growInstant(int x, int y, int gemsAvailable) {
        if (!isInside(x, y) || pots[y][x].isEmpty() || gemsAvailable < 1) {
            return false;
        }
        pots[y][x].makeReady();
        return true;
    }

    /**
     * Update greenhouse state (called periodically based on system time)
     */
    public void update() {
        for (GreenhousePot[] row : pots) {
            for (GreenhousePot pot : row) {
                pot.updateGrowth();
            }
        }
    }

    /**
     * Get overall greenhouse status for display
     */
    public String getStatus() {
        update();
        StringBuilder builder = new StringBuilder();
        for (int y = 0; y < ROWS; y++) {
            builder.append(y + 1).append(" ");
            for (int x = 0; x < COLS; x++) {
                GreenhousePot pot = pots[y][x];
                if (!pot.isUnlocked()) {
                    builder.append("#");
                } else if (pot.isEmpty()) {
                    builder.append(".");
                } else if (pot.isReady()) {
                    builder.append("R");
                } else {
                    builder.append("G");
                }
                builder.append(" ");
            }
            builder.append(System.lineSeparator());
        }
        builder.append("  1 2 3 4 5");
        return builder.toString();
    }

    public String serialize() {
        update();
        StringBuilder builder = new StringBuilder();
        for (int y = 0; y < ROWS; y++) {
            for (int x = 0; x < COLS; x++) {
                GreenhousePot pot = pots[y][x];
                if (builder.length() > 0) {
                    builder.append(";");
                }
                builder.append(x).append(",")
                        .append(y).append(",")
                        .append(pot.isUnlocked()).append(",")
                        .append(pot.getPlantType() == null ? "" : pot.getPlantType()).append(",")
                        .append(pot.getTimeRemaining());
            }
        }
        return builder.toString();
    }

    public void restore(String state) {
        if (state == null || state.isBlank()) {
            return;
        }
        for (String record : state.split(";")) {
            String[] fields = record.split(",", -1);
            if (fields.length != 5) {
                continue;
            }
            try {
                int x = Integer.parseInt(fields[0]);
                int y = Integer.parseInt(fields[1]);
                if (!isInside(x, y)) {
                    continue;
                }
                boolean unlocked = Boolean.parseBoolean(fields[2]);
                String plantType = fields[3];
                long remainingSeconds = Long.parseLong(fields[4]);
                pots[y][x].restore(unlocked, plantType, remainingSeconds);
                unlockedPots.put(potId(x, y), unlocked ? 1 : 0);
            } catch (NumberFormatException exception) {
                // Ignore malformed runtime data and keep the default pot state.
            }
        }
    }

    private boolean isInside(int x, int y) {
        return x >= 0 && x < COLS && y >= 0 && y < ROWS;
    }

    private int potId(int x, int y) {
        return y * COLS + x;
    }
}
