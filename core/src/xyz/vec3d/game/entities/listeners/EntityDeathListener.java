package xyz.vec3d.game.entities.listeners;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;

import java.util.List;

import xyz.vec3d.game.entities.Enemy;
import xyz.vec3d.game.entities.WorldItem;
import xyz.vec3d.game.model.Item;
import xyz.vec3d.game.model.ItemStack;
import xyz.vec3d.game.model.drops.Drop;
import xyz.vec3d.game.model.drops.DropSystem;

/**
 * Created by Daron on 7/9/2017.
 * Copyright vec3d.xyz 2016
 * All rights reserved
 */

public class EntityDeathListener implements EntityListener {

    private Engine engine;

    public EntityDeathListener(Engine engine) {
        this.engine = engine;
    }

    @Override
    public void entityAdded(Entity entity) {

    }

    @Override
    public void entityRemoved(Entity entity) {
        if (entity instanceof Enemy) {
            List<Drop> drops = DropSystem.getDropsForNpc(((Enemy) entity).getId());
            for (Drop drop : drops) {
                float x = ((Enemy) entity).getPosition().x;
                float y = ((Enemy) entity).getPosition().y;
                int itemId = drop.getItemId();
                int amount = drop.getAmount();
                WorldItem worldItem = new WorldItem(new ItemStack(new Item(itemId), amount), x, y);

                engine.addEntity(worldItem);
            }
        }
    }
}
