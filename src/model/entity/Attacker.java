package model.entity;

/**
 * Interface for entities that can perform attacks
 */
public interface Attacker {
    /**
     * Get the attack damage
     */
    int getAttackDamage();

    /**
     * Get the attack range
     */
    int getAttackRange();

    /**
     * Perform an attack
     */
    void attack();

    /**
     * Check if can attack right now
     */
    boolean canAttack();
}
