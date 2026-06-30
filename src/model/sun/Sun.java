package model.sun;

/**
 * Represents a collectible sun pellet on the board
 */
class Sun {
    private int x;
    private int y;
    private SunType type;
    private int value;
    private int ticksSinceSpawn;

    public Sun(int x, int y, SunType type) {
        this.x = x;
        this.y = y;
        this.type = type == null ? SunType.NORMAL : type;
        this.value = this.type.getValue();
        this.ticksSinceSpawn = 0;
    }

    public boolean canCollect() {
        return ticksSinceSpawn < 30;
    }

    public int getValue() {
        return value;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public SunType getType() {
        return type;
    }

    public void update() {
        ticksSinceSpawn++;
    }
}
