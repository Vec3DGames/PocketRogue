package xyz.vec3d.game.utils;

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
     *
     * @param objectWidth The width of the object being centered.
     * @param containerWidth The width of the container that object is centered in.
     *
     * @return The x position to draw the centered object.
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
     * @return
     */
    public static float getPosCenterX(float objectWidth, float containerWidth, float containerPos) {
        return getPosCenter(objectWidth, containerWidth, containerPos);
    }

    /**
     *
     * @param objectHeight The height of the object being centered.
     * @param containerHeight The widht of the container that object is centered in.
     *
     * @return The y position to draw the centered object.
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
     * @return
     */
    public static float getPosCenterY(float objectHeight, float containerHeight, float containerPos) {
        return getPosCenter(objectHeight, containerHeight, containerPos);
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
    private static float getPosCenter(float objectDimension, float containerDimension, float containerPos) {
        return containerPos + ((containerDimension - objectDimension) / 2);
    }
}
