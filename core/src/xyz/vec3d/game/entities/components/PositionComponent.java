package xyz.vec3d.game.entities.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by darakelian on 7/7/2016.
 * Copyright vec3d.xyz 2016
 * All rights reserved
 *
 * Position component for entities. Defaults to a location of 0,0 in game units
 * unless a coordinate pair is passed to the constructor in which case the entity
 * will be placed at those coordinates.
 */
public class PositionComponent implements Component {

    /**
     * The {@link Vector2} that stores the position information.
     */
    private Vector2 position;

    /**
     * Creates a new position component representing 0,0 in world units.
     */
    public PositionComponent() {
        position = new Vector2(0, 0);
    }

    /**
     * Creates a new position component representing a given x,y coordinate pair
     * based on x and y float values.
     *
     * @param x The vector's x component.
     * @param y The vector's y component.
     */
    public PositionComponent(float x, float y) {
        position = new Vector2(x, y);
    }

    /**
     * Creates a new position component representing a given x,y coordinate pair
     * based on a float[] containing the x and y float values. This constructor
     * will most likely be used for creating position components based on json
     * data or other methods that return arrays of position information rather
     * than calling PositionComponent(x, y) individually.
     *
     * @param xy The float[] containing the vector's x and y components.
     */
    public PositionComponent(float[] xy) {
        position = new Vector2(xy[0], xy[1]);
    }

    /**
     * Gets the {@link Vector2} stored in this component. If the vector backing
     * this component is somehow null it will return a new vector created at the
     * position 0,0 to help avoid code breaking.
     *
     * @return The position vector.
     */
    public Vector2 getPosition() {
        if (position == null) {
            return new Vector2(0, 0);
        }
        return position;
    }
}
