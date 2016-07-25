package xyz.vec3d.game.entities.listeners;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import com.badlogic.gdx.graphics.Texture;

import xyz.vec3d.game.PocketRogue;
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
     * Default constructor.
     */
    public EntityTextureListener() {

    }

    @Override
    public void entityAdded(Entity entity) {
        String entityName = entity.getClass().getSimpleName().toLowerCase() + ".png";
        Texture texture = PocketRogue.getInstance().getAssetManager().get(entityName, Texture.class);
        entity.add(new TextureComponent(texture));
    }

    @Override
    public void entityRemoved(Entity entity) {

    }
}
