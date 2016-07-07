package xyz.vec3d.game.entities.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by darakelian on 7/7/2016.
 * Copyright vec3d.xyz 2016
 * All rights reserved
 *
 * Velocity component for entities. Initially is 0,0 meaning the entity is not
 * moving in any direction. Positive x component values indicate moving to the
 * right and positive y component values indicate moving upwards.
 */
public class VelocityComponent implements Component {

    /**
     * The {@link Vector2} that stores the velocity information.
     */
    private Vector2 velocity;

    /**
     * Creates a new velocity component for an entity such that the entity is not
     * moving in any direction.
     */
    public VelocityComponent() {
        velocity = new Vector2(0, 0);
    }

    public Vector2 getVelocity() {
        if (velocity == null) {
            return new Vector2(0, 0);
        }
        return velocity;
    }
}
