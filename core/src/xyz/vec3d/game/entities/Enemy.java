package xyz.vec3d.game.entities;

import com.badlogic.ashley.core.Entity;

import xyz.vec3d.game.entities.components.PositionComponent;

/**
 * Created by Daron on 8/11/2016.
 *
 * Base enemy class for all game enemies.
 */
public class Enemy extends Entity {

    public Enemy(float x, float y) {
        add(new PositionComponent(x, y));
    }

    public enum EntityType {
        OVERWORLD_GRASS, OVERWORLD_SAND, DUNGEON
    }
}
