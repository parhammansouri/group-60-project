package model.entity;

/**
 * Interface for entities that can move on the board
 */
public interface Moveable {
    /**
     * Get the current X position
     */
    int getX();

    /**
     * Get the current Y position
     */
    int getY();

    /**
     * Move to a new position
     */
    void moveTo(int x, int y);

    /**
     * Get the movement speed
     */
    float getSpeed();
}
