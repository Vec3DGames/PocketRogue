package xyz.vec3d.game.model;

/**
 * Created by Daron on 8/20/2016.
 *
 * Enum representing different types of item properties present in the JSON file.
 */
public enum DefinitionProperty {

    NAME, ICON, STACKABLE, SLOT, ID, HEALTH, BONUSES, MOVE_SPEED;

    public static DefinitionProperty value(String name) {
        return DefinitionProperty.valueOf(name.toUpperCase());
    }
}
