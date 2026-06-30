package model.sun;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Manages the sun resource system in the game
 */
public class SunSystem {
    private int totalSunAmount;
    private int ticksSinceLastDrop;
    private final List<Sun> droppingSuns;

    public SunSystem() {
        this(150);
    }

    public SunSystem(int initialSun) {
        this.totalSunAmount = Math.max(0, initialSun);
        this.ticksSinceLastDrop = 0;
        this.droppingSuns = new ArrayList<>();
    }

    public void addSunFromPlant(int x, int y, int amount) {
        SunType type = amount >= SunType.SPECIAL.getValue() ? SunType.SPECIAL : SunType.NORMAL;
        droppingSuns.add(new Sun(x, y, type));
    }

    public void dropSun(int x, int y, SunType type) {
        droppingSuns.add(new Sun(x, y, type));
    }

    public boolean collectSun(int x, int y) {
        Iterator<Sun> iterator = droppingSuns.iterator();
        while (iterator.hasNext()) {
            Sun sun = iterator.next();
            if (sun.getX() == x && sun.getY() == y && sun.canCollect()) {
                totalSunAmount += sun.getValue();
                iterator.remove();
                return true;
            }
        }
        return false;
    }

    /**
     * Update sun drop timing
     */
    public void update(int currentTick) {
        ticksSinceLastDrop++;
        for (Sun sun : droppingSuns) {
            sun.update();
        }
        droppingSuns.removeIf(sun -> !sun.canCollect());
        int dropInterval = 6;
        if (ticksSinceLastDrop >= dropInterval) {
            int x = currentTick % 9;
            int y = currentTick % 5;
            dropSun(x, y, SunType.NORMAL);
            ticksSinceLastDrop = 0;
        }
    }

    public int getTotalSun() {
        return totalSunAmount;
    }

    public boolean consumeSun(int amount) {
        if (amount < 0 || totalSunAmount < amount) {
            return false;
        }
        totalSunAmount -= amount;
        return true;
    }

    public int getDroppingSunCount() {
        return droppingSuns.size();
    }

    public boolean hasSunAt(int x, int y) {
        for (Sun sun : droppingSuns) {
            if (sun.getX() == x && sun.getY() == y && sun.canCollect()) {
                return true;
            }
        }
        return false;
    }
}
