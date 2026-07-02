package model.entity.projectile;

public class Projectile {
    private int x;
    private int y;
    private int speed;
    private int damage;
    private boolean active;

    public Projectile(int x, int y, int speed, int damage) {
        this.x = x;
        this.y = y;
        this.speed = Math.max(1, speed);
        this.damage = Math.max(0, damage);
        this.active = true;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getSpeed() {
        return speed;
    }

    public int getDamage() {
        return damage;
    }

    public boolean isActive() {
        return active;
    }

    public void update() {
        x += speed;
    }

    public boolean isAt(int targetX, int targetY) {
        return active && y == targetY && x >= targetX;
    }

    public void deactivate() {
        active = false;
    }
}
