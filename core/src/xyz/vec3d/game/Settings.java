package xyz.vec3d.game;

/**
 * Created by Daron on 6/28/2016.
 * Copyright vec3d.xyz 2016
 * All rights reserved
 *
 * Static instances of settings values for use throughout the application.
 */
public class Settings {

    /**
     * Represents the width of the game window for desktop.
     */
    public static final int WIDTH = 800;

    /**
     * Represents the height of the game window for desktop.
     */
    public static final int HEIGHT = 450;

    /**
     * Represents the width used for the UI stages.
     */
    public static final int UI_WIDTH = 800;

    /**
     * Represents the height used for the UI stages.
     */
    public static final int UI_HEIGHT = 450;

    /**
     * Represents the world scale the map and camera operate on. The scale is
     * defined as X world units per Y pixels ex. 1/32f = 1 world unit -> 32 pixels.
     */
    public static final float WORLD_SCALE = 1/32f;

    public static int MAX_WORLD_WIDTH;

    public static int MAX_WORLD_HEIGHT;

}
