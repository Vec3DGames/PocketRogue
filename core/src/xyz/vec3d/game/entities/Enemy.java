package xyz.vec3d.game.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import xyz.vec3d.game.PocketRogue;
import xyz.vec3d.game.entities.components.PositionComponent;
import xyz.vec3d.game.entities.components.VelocityComponent;

/**
 * Created by Daron on 8/11/2016.
 *
 * Base enemy class for all game enemies.
 */
public class Enemy extends PocketRogueEntity {

    private int id;

    public Enemy(int id, float x, float y) {
        this.id = id;
        add(new PositionComponent(x, y));
        add(new VelocityComponent());
        //Set up animations here.
        Texture animationSheet = PocketRogue.getAssetManager()
                .get("animation_sheets/player_animation.png");
        TextureRegion[][] tmpRegions = TextureRegion.split(animationSheet, 32, 32);
        TextureRegion[] idle = new TextureRegion[3];
        System.arraycopy(tmpRegions[0], 0, idle, 0, idle.length);
        //Set the animations available
        setAnimations(new Animation[] {
                new Animation(1/60f, tmpRegions[3]), //Left
                new Animation(1/60f, tmpRegions[2]), //Right
                new Animation(1/60f, tmpRegions[1]), //Up
                new Animation(1/60f, tmpRegions[0]), //Down
                new Animation(1/10f, idle) //Idle
        });
    }

    public int getId() {
        return id;
    }

    public enum EntityType {
        OVERWORLD_GRASS, OVERWORLD_SAND, DUNGEON
    }
}
