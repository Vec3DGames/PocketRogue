package xyz.vec3d.game.entities.listeners;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import com.badlogic.gdx.graphics.Texture;

import xyz.vec3d.game.GameScreen;
import xyz.vec3d.game.entities.components.TextureComponent;

/**
 * Created by Daron on 7/7/2016.
 * Copyright vec3d.xyz 2016
 * All rights reserved
 *
 * Listens for when an entity is added to the engine and then creates a texture
 * component for it.
 */
public class EntityTextureListener implements EntityListener {

    /**
     * The {@link GameScreen} instance that contains an instance of the AssetManager
     * so that textures can be loaded.
     */
    private GameScreen gameScreen;

    public EntityTextureListener(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
    }

    @Override
    public void entityAdded(Entity entity) {
        String entityName = entity.getClass().getSimpleName().toLowerCase() + ".png";
        Texture texture = gameScreen.getPocketRogue().getAssetManager().get(entityName, Texture.class);
        entity.add(new TextureComponent(texture));
    }

    @Override
    public void entityRemoved(Entity entity) {

    }
}
