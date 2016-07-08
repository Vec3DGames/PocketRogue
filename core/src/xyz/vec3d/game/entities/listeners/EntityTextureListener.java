package xyz.vec3d.game.entities.listeners;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import xyz.vec3d.game.GameScreen;

/**
 * Created by Daron on 7/7/2016.
 * Copyright vec3d.xyz 2016
 * All rights reserved
 *
 * Listens for when an entity is added to the engine and then creates a texture
 * component for it.
 */
public class EntityTextureListener implements EntityListener {

    private GameScreen gameScreen;

    public EntityTextureListener(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
    }

    @Override
    public void entityAdded(Entity entity) {
        String entityName = entity.getClass().getSimpleName();
        TextureRegion textureRegion = gameScreen.getPocketRogue().getAssetManager().get(entityName, TextureRegion.class);
    }

    @Override
    public void entityRemoved(Entity entity) {

    }
}
