package xyz.vec3d.game.model;

/**
 * Created by Daron on 8/20/2016.
 *
 * Enum representing different types of item properties present in the JSON file.
 */
public enum ItemProperty {

    NAME, ICON, STACKABLE, SLOT;

    public static ItemProperty value(String name) {
        return ItemProperty.valueOf(name.toUpperCase());
    }
}
