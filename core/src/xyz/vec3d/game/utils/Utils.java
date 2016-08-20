package xyz.vec3d.game.utils;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.JsonValue;

import xyz.vec3d.game.entities.components.PositionComponent;

/**
 * Created by darakelian on 6/30/2016.
 * Copyright vec3d.xyz 2016
 * All rights reserved
 *
 * Utils class provides static methods for various repeat functionality such as
 * math functions.
 */
public class Utils {


    /**
     * Gets the x coordinate to draw an object at when being centered in a container
     * in which point 0,0 is the lower left corner of the object.
     *
     * @param objectWidth The width of the object being centered.
     * @param containerWidth The width of the container that object is centered in.
     *
     * @return The x coordinate to draw the lower left corner of the object.
     *
     * @see Utils#getPosCenterX(float, float, float)
     */
    public static float getPosCenterX(float objectWidth, float containerWidth) {
        return getPosCenterX(objectWidth, containerWidth, 0);
    }

    /**
     * Gets the x coordinate to draw an object at based on a given object width
     * and container width such that it is drawn in the center of the container.
     *
     * @param objectWidth The width of the object being centered.
     * @param containerWidth The width of the container that object is centered in.
     * @param containerPos The position of the container (0 if container is the screen).
     *
     * @return The x coordinate to draw the lower left corner of the object.
     */
    public static float getPosCenterX(float objectWidth, float containerWidth, float containerPos) {
        return getPosCenter(objectWidth, containerWidth, containerPos, false);
    }

    /**
     * Gets the y coordinate to draw an object at when being centered in a container
     * in which point 0,0 is the lower left corner of the object.
     *
     * @param objectHeight The height of the object being centered.
     * @param containerHeight The wight of the container that object is centered in.
     *
     * @return The y coordinate to draw the lower left corner of the object.
     *
     * @see Utils#getPosCenterY(float, float, float)
     */
    public static float getPosCenterY(float objectHeight, float containerHeight) {
        return getPosCenterY(objectHeight, containerHeight, 0);
    }

    /**
     * Gets the y coordinate to draw an object at based on a given object height
     * and container height such that it is drawn in the center of the container.
     *
     * @param objectHeight The height of the object being centered.
     * @param containerHeight The height of the container that object is centered in.
     * @param containerPos The position of the container (0 if the container is the screen).
     *
     * @return The y coordinate to draw the lower left corner of the object.
     */
    public static float getPosCenterY(float objectHeight, float containerHeight, float containerPos) {
        return getPosCenter(objectHeight, containerHeight, containerPos, true);
    }

    /**
     * Performs the actual calculation to determine the coordinate that will draw
     * the object centered in the container. Private because this method should
     * not be called be anyone other than the wrapper methods in the Utils class.
     *
     * @param objectDimension Dimension of the object being centered (either width/height).
     * @param containerDimension Dimension of the container (either width/height).
     * @param containerPos Starting x or y coordinate of the container.
     *
     * @return
     */
    private static float getPosCenter(float objectDimension, float containerDimension,
                                      float containerPos, boolean negate) {
        return containerPos + ((containerDimension - objectDimension) / 2);
    }

    public static void printArray(Object[] array) {
        printArray(array, true);
    }

    public static void printArray(Object[] array, boolean oneLine) {
        String stringToPrint = "";
        for (Object o : array) {
            stringToPrint += oneLine ? o.toString() + " " : o.toString() + "\n";
        }
        System.out.println(stringToPrint.trim());
    }

    public static void centerCamera(OrthographicCamera worldCamera, Entity entity, float mapWidth,
                                    float mapHeight) {
        PositionComponent position = entity.getComponent(PositionComponent.class);
        if (position == null) {
            return;
        }
        float camViewportHalfX = worldCamera.viewportWidth / 2;
        float camViewportHalfY = worldCamera.viewportHeight / 2;
        worldCamera.position.x = position.getPosition().x;
        worldCamera.position.y = position.getPosition().y;
        //Clamp camera first on x, then on y.
        worldCamera.position.x = MathUtils.clamp(worldCamera.position.x,
                camViewportHalfX, mapWidth - camViewportHalfX);
        worldCamera.position.y = MathUtils.clamp(worldCamera.position.y,
                camViewportHalfY, mapHeight - camViewportHalfY);
    }

    /**
     * Returns the appropriate Java object type for the JsonValue.
     *
     * @param value The JsonValue object.
     *
     * @return Returns the value as its intended type.
     */
    public static Object getJsonTypeValue(JsonValue value) {
        switch (value.type()) {
            case longValue:
                return value.asInt();
            case doubleValue:
                return value.asDouble();
            case booleanValue:
                return value.asBoolean();
            case object:
                return value;
            case array:
                return value.asStringArray();
            case stringValue:
            default:
                return value.asString();
        }
    }
}
