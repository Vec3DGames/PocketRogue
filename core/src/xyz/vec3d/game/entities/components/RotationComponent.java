package xyz.vec3d.game.entities.components;

import com.badlogic.ashley.core.Component;

/**
 * Created by Daron on 10/5/2016.
 *
 * Component representing the rotation of an entity relative to the X-axis.
 */

public class RotationComponent implements Component {

    private float rotationAngle;

    public RotationComponent(float rotationAngle) {
        this.rotationAngle = rotationAngle;
    }

    public float getRotationAngle() {
        return this.rotationAngle;
    }

    public void setRotationAngle(float rotationAngle) {
        this.rotationAngle = rotationAngle;
    }
}
