package xyz.vec3d.game.entities.components;

import com.badlogic.ashley.core.Component;

/**
 * Created by Daron on 10/3/2016.
 *
 * Mana component represents the mana present for an entity.
 */

public class ManaComponent implements Component {

    /**
     * Value representing the amount of mana the entity currently has.
     */
    private float currentMana;

    /**
     * Value representing the max amount of mana the entity can have.
     */
    private float maxMana;

    /**
     * Create a HealthComponent object in which the current health is the same
     * as the max health.
     *
     * @param maxMana Current/Max mana of the entity.
     */
    public ManaComponent(float maxMana) {
        this(maxMana, maxMana);
    }

    /**
     * Create a ManaComponent object in which the current mana is not the same as
     * the max mana.
     *
     * @param currentMana Current mana of entity.
     * @param maxMana Max mana of entity.
     */
    public ManaComponent(float currentMana, float maxMana) {
        this.currentMana = currentMana;
        this.maxMana = maxMana;
    }

    public float getCurrentMana() {
        return currentMana;
    }

    public float getMaxMana() {
        return maxMana;
    }

    public float getPercentManaRemaining() {
        return currentMana / maxMana;
    }

}
