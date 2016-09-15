package xyz.vec3d.game.entities;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import xyz.vec3d.game.PocketRogue;
import xyz.vec3d.game.entities.components.AnimationComponent;
import xyz.vec3d.game.entities.components.InventoryComponent;
import xyz.vec3d.game.entities.components.MovementSpeedComponent;
import xyz.vec3d.game.entities.components.PositionComponent;
import xyz.vec3d.game.entities.components.VelocityComponent;
import xyz.vec3d.game.model.Inventory;

/**
 * Created by darakelian on 7/6/2016.
 * Copyright vec3d.xyz 2016
 * All rights reserved
 *
 * The {@link Player} entity class. Creating a new instance of this will cause
 */
public class Player extends Entity {

    public AnimationComponent leftAnimation;
    public AnimationComponent rightAnimation;
    public AnimationComponent upAnimation;
    public AnimationComponent downAnimation;
    public AnimationComponent idleAnimation;

    private boolean isMovingLeft, isMovingRight, isMovingUp, isMovingDown, isIdle;

    /**
     * Creates a new player at the specified position.
     *
     * @param startX Float value of starting x coordinate in world units.
     * @param startY Float value of starting y coordinate in world units.
     */
    public Player(float startX, float startY) {
        add(new PositionComponent(startX, startY));
        add(new VelocityComponent());
        add(new MovementSpeedComponent());
        add(new InventoryComponent());
        //Set up animations here.
        Texture animationSheet = PocketRogue.getAssetManager()
                .get("animation_sheets/player_animation.png");
        TextureRegion[][] tmpRegions = TextureRegion.split(animationSheet, 32, 32);
        downAnimation = new AnimationComponent(new Animation(1/30f, tmpRegions[0]));
        upAnimation = new AnimationComponent(new Animation(1/30f, tmpRegions[1]));
        rightAnimation = new AnimationComponent(new Animation(1/30f, tmpRegions[2]));
        leftAnimation = new AnimationComponent(new Animation(1/30f, tmpRegions[3]));
        TextureRegion[] idle = new TextureRegion[3];
        System.arraycopy(tmpRegions[0], 0, idle, 0, idle.length);
        idleAnimation = new AnimationComponent(new Animation(1/30f, idle));
    }

    /**
     * Sets the current animation to be used by the rendering system based on
     * the direction specified.
     * <br>
     * 0 - left
     * <br>
     * 1 - right
     * <br>
     * 2 - up
     * <br>
     * 3 - down
     *
     * @param direction Int representing the direction being moved.
     */
    public void setAnimation(int direction) {
        switch (direction) {
            case 0: //left
                //add(leftAnimation);
                isMovingLeft = !isMovingLeft;
                break;
            case 1: //right
                //add(rightAnimation);
                isMovingRight = !isMovingRight;
                break;
            case 2: //up
                //add(upAnimation);
                isMovingUp = !isMovingUp;
                break;
            case 3: //down
                //add(downAnimation);
                isMovingDown = !isMovingDown;
                break;
            case 4: //idle
                //add(idleAnimation);
                isIdle = true;
                break;
        }
    }

    public void setVelocity(Vector2 velocity) {
        getVelocity().set(velocity.scl(getMoveSpeed()));
    }

    public Vector2 getVelocity() {
        return getComponent(VelocityComponent.class).getVelocity();
    }

    public Vector2 getPosition() {
        return getComponent(PositionComponent.class).getPosition();
    }

    public float getMoveSpeed() {
        return getComponent(MovementSpeedComponent.class).getMoveSpeed();
    }

    public boolean isMovingLeft() {
        return isMovingLeft;
    }

    public boolean isMovingRight() {
        return isMovingRight;
    }

    public boolean isMovingUp() {
        return isMovingUp;
    }

    public boolean isMovingDown() {
        return isMovingDown;
    }

    public boolean isIdle() {
        return isIdle;
    }

    /**
     * Returns the player's {@link Inventory} taken from the {@link InventoryComponent}
     * of the player. This is a convenience method to avoid having to access the
     * component manually each time the inventory is required.
     *
     * @return The player's inventory.
     */
    public Inventory getInventory() {
        return getComponent(InventoryComponent.class).getInventory();
    }
}
