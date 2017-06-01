package xyz.vec3d.game.entities.components;

import com.badlogic.ashley.core.Component;

/**
 * Created by Daron on 10/3/2016.
 *
 * Health component represents the health present for an entity.
 */

public class HealthComponent implements Component {

    /**
     * Value representing the amount of health the entity currently has.
     */
    private float currentHealth;

    /**
     * Value representing the max amount of health the entity can have.
     */
    private float maxHealth;

    /**
     * Create a HealthComponent object in which the current health is the same
     * as the max health.
     *
     * @param maxHealth Current/Max health of the entity.
     */
    public HealthComponent(float maxHealth) {
        this(maxHealth, maxHealth);
    }

    /**
     * Create a HealthComponent object in which the current health is not the
     * same as the max health.
     *
     * @param currentHealth Current health of entity.
     * @param maxHealth Max health of entity.
     */
    public HealthComponent(float currentHealth, float maxHealth) {
        this.currentHealth = currentHealth;
        this.maxHealth = maxHealth;
    }

    public float getCurrentHealth() {
        return currentHealth;
    }

    public float getMaxHealth() {
        return maxHealth;
    }

    /**
     * Returns the percent of health left ranging from 0.0-100.0.
     *
     * @return The floating point percent of health left.
     */
    public float getPercentHealthRemaining() {
        return getPercentHealthRemainingScale() * 100;
    }

    /**
     * Returns the percent of health left ranging from 0.0-1.0 for UI scaling
     * things.
     *
     * @return The scale 0.0-1.0 of health left.
     */
    public float getPercentHealthRemainingScale() {
        return currentHealth / maxHealth;
    }

    public void removeHealth(float health) {
        this.currentHealth -= health;
    }
}
