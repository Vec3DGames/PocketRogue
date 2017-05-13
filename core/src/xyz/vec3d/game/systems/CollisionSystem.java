package xyz.vec3d.game.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;

import xyz.vec3d.game.entities.PocketRogueEntity;
import xyz.vec3d.game.entities.components.CollideComponent;
import xyz.vec3d.game.entities.components.PositionComponent;
import xyz.vec3d.game.utils.Logger;
import xyz.vec3d.game.utils.Utils;

/**
 * Created by Daron on 10/5/2016.
 *
 * Collision system that handles entity on entity interaction.
 */

public class CollisionSystem extends IteratingSystem {

    /**
     * The max range that entity collision checks are performed at. AKA the
     * higher this value, the more entities that will be swept up in the culling
     * process of the processEntity method. Lowering this value would technically
     * improve performance however since the absolute minimum run time is O(n)
     * there isn't much of a benefit from changing this value.
     */
    private static final float COLLISION_MAX_RANGE = 15.0f;

    /**
     * Creates a new collision system that tracks all entities with a position
     * and collide component.
     */
    public CollisionSystem() {
        super(Family.all(PositionComponent.class, CollideComponent.class).get());
    }

    /**
     * Called each tick for all the entities being tracked by this system. First
     * the system culls entities and gets all the entities within a certain
     * range. Then, from those entities found, we check if any of them collide
     * with the entity that is being referenced in this tick update.
     *
     * By culling the entities to a certain range we can limit the number of checks
     * performed from worst case O(n^2) to O(n) + O(k) where k is the number
     * of entities found in the new region.
     *
     * @param entity The entity that we are performing collision checks for.
     * @param deltaTime The time in ms since last update.
     */
    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        //Get entities within a certain range of the entity.
        ArrayList<PocketRogueEntity> entitiesInRange = Utils.
                getEntitiesWithinRange(getEntities(), (PocketRogueEntity) entity,
                        COLLISION_MAX_RANGE);
        for (PocketRogueEntity otherEntity : entitiesInRange) {
            if (Utils.entitiesCollide((PocketRogueEntity) entity, otherEntity)) {
                ((PocketRogueEntity) entity).doCollision(otherEntity);
            }
        }
    }

}
