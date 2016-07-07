package xyz.vec3d.game.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import xyz.vec3d.game.entities.components.PositionComponent;
import xyz.vec3d.game.entities.components.TextureComponent;

/**
 * Created by darakelian on 7/7/2016.
 * Copyright vec3d.xyz 2016
 * All rights reserved
 *
 * The rendering system is responsible for drawing
 */
public class RenderingSystem extends IteratingSystem {

    private SpriteBatch batch;

    public RenderingSystem(SpriteBatch batch) {
        super(Family.all(TextureComponent.class, PositionComponent.class).get());
        this.batch = batch;
    }

    protected void processEntity(Entity entity, float deltaTime) {
    }
}
