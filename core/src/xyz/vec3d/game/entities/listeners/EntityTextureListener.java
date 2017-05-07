package xyz.vec3d.game.entities.listeners;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import xyz.vec3d.game.entities.PocketRogueEntity;
import xyz.vec3d.game.entities.WorldItem;
import xyz.vec3d.game.entities.components.TextureComponent;
import xyz.vec3d.game.utils.Utils;

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
        if (entity instanceof PocketRogueEntity) {
            if (entity instanceof WorldItem) {
                return;
            }
            String name = ((PocketRogueEntity) entity).getName();
            if (name == null) {
                name = entity.getClass().getSimpleName().toLowerCase();
            }
            TextureRegion region = Utils.getEntityTexture(name);
            entity.add(new TextureComponent(region));
        }
    }

    @Override
    public void entityRemoved(Entity entity) {

    }
}
