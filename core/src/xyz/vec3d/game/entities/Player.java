package xyz.vec3d.game.entities;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;

import xyz.vec3d.game.entities.components.MovementSpeedComponent;
import xyz.vec3d.game.entities.components.PositionComponent;
import xyz.vec3d.game.entities.components.VelocityComponent;

/**
 * Created by darakelian on 7/6/2016.
 * Copyright vec3d.xyz 2016
 * All rights reserved
 *
 * The {@link Player} entity class. Creating a new instance of this will cause
 */
public class Player extends Entity {

    /**
     * Creates a new player at the specified position.
     *
     * @param startX Float value of starting x coordinate in world units.
     * @param startY Float value of starting y coordinate in world units.
     */
    public Player(float startX, float startY) {
        add(new PositionComponent(startX, startY));
        add(new VelocityComponent());
        add(new MovementSpeedComponent());
    }

    public void setVelocity(Vector2 velocity) {
        MovementSpeedComponent movementSpeedComponent =
                getComponent(MovementSpeedComponent.class);
        getComponent(VelocityComponent.class).getVelocity()
                .set(velocity.scl(movementSpeedComponent.getMoveSpeed()));
    }
}
