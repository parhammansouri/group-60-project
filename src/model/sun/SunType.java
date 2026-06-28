package model.sun;

/**
 * Types of sun pellets with different values
 */
enum SunType {
    NORMAL(25, 0.8), // 80% chance
    SPECIAL(100, 0.15), // 15% chance
    RADIOACTIVE(0, 0.05); // 5% chance - explodes

    private int value;
    private double probability;

    SunType(int value, double probability) {
        this.value = value;
        this.probability = probability;
    }

    public int getValue() {
        return value;
    }

    public double getProbability() {
        return probability;
    }
}
