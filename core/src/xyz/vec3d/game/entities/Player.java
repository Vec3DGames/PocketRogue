package xyz.vec3d.game.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import xyz.vec3d.game.PocketRogue;
import xyz.vec3d.game.entities.components.HealthComponent;
import xyz.vec3d.game.entities.components.InventoryComponent;
import xyz.vec3d.game.entities.components.ManaComponent;
import xyz.vec3d.game.entities.components.MovementSpeedComponent;
import xyz.vec3d.game.entities.components.PositionComponent;
import xyz.vec3d.game.entities.components.VelocityComponent;
import xyz.vec3d.game.model.Inventory;
import xyz.vec3d.game.model.ItemStack;
import xyz.vec3d.game.model.SpellManager;
import xyz.vec3d.game.model.combat.ProjectileFiringSystem;

/**
 * Created by darakelian on 7/6/2016.
 * Copyright vec3d.xyz 2016
 * All rights reserved
 *
 * The {@link Player} entity class. Creating a new instance of this will cause
 */
public class Player extends PocketRogueEntity {

    /**
     * Creates a new player at the specified position.
     *
     * @param startX Float value of starting x coordinate in world units.
     * @param startY Float value of starting y coordinate in world units.
     */
    public Player(float startX, float startY) {
        super();
        add(new PositionComponent(startX, startY));
        add(new VelocityComponent());
        add(new MovementSpeedComponent());
        add(new InventoryComponent());
        add(new HealthComponent(100));
        add(new ManaComponent(100));
        //Set up animations here.
        Texture animationSheet = PocketRogue.getAsset("animation_sheets/player_animation.png");
        TextureRegion[][] tmpRegions = TextureRegion.split(animationSheet, 32, 32);
        //Set the animations available
        setAnimations(new Animation[] {
                new Animation(1/10f, tmpRegions[3]), //Left
                new Animation(1/10f, tmpRegions[2]), //Right
                new Animation(1/10f, tmpRegions[1]), //Up
                new Animation(1/10f, tmpRegions[0]), //Down
                new Animation(1/10f, tmpRegions[0]) //Idle
        });
        this.projectileFiringSystem = new ProjectileFiringSystem(this);
        this.spellManager = new SpellManager();
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

    @Override
    public void doCollision(PocketRogueEntity otherEntity) {
        if (otherEntity instanceof WorldItem) {
            //Make sure the item hasn't been picked up somehow so we don't get
            //duplicates.
            if (otherEntity.isDead()) {
                return;
            }
            ItemStack itemStack = ((WorldItem) otherEntity).getItemStack();
            otherEntity.kill();
            getInventory().addItem(itemStack);
        }
    }

}
