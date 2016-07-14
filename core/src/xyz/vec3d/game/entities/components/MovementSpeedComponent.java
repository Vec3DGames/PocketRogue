package xyz.vec3d.game.entities.components;

import com.badlogic.ashley.core.Component;

/**
 * Created by darakelian on 7/14/2016.
 * Copyright vec3d.xyz 2016
 * All rights reserved
 */
public class MovementSpeedComponent implements Component{

    private float moveSpeed;

    public MovementSpeedComponent() {
        moveSpeed = 0.2f;
    }

    public MovementSpeedComponent(float moveSpeed) {
        this.moveSpeed = moveSpeed;
    }

    public float getMoveSpeed() {
        return moveSpeed;
    }

    public void setMoveSpeed(float moveSpeed) {
        this.moveSpeed = moveSpeed;
    }

    public void modifyMoveSpeed(float modifier) {
        this.moveSpeed *= modifier;
    }
}
