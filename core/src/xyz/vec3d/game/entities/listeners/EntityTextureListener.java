package xyz.vec3d.game.entities.listeners;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import xyz.vec3d.game.entities.Enemy;
import xyz.vec3d.game.entities.Player;
import xyz.vec3d.game.entities.PocketRogueEntity;
import xyz.vec3d.game.entities.WorldItem;
import xyz.vec3d.game.entities.components.AiComponent;
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

    private Engine engine;

    /**
     * Default constructor.
     */
    public EntityTextureListener(Engine engine) {
        this.engine = engine;
    }

    @Override
    public void entityAdded(Entity entity) {
        if (entity instanceof PocketRogueEntity) {
            if (entity instanceof WorldItem) {
                return;
            }
            String name = ((PocketRogueEntity) entity).getName();
            if (name == null || name.equals("")) {
                name = entity.getClass().getSimpleName().toLowerCase();
            }
            TextureRegion region = Utils.getEntityTexture(name);
            entity.add(new TextureComponent(region));
            AiComponent aiComponent = entity.getComponent(AiComponent.class);
            if (aiComponent != null) {
                aiComponent.setEngine(engine);
            }
            if (entity instanceof Player || entity instanceof Enemy) {
                ((PocketRogueEntity) entity).createCombatSystem(engine);
            }
        }
    }

    @Override
    public void entityRemoved(Entity entity) {

    }
}
