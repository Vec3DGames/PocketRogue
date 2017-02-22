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

    public CollisionSystem() {
        super(Family.all(PositionComponent.class, CollideComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        //Get entities within a certain range of the entity.
        ArrayList<PocketRogueEntity> entitiesInRange = getEntitiesWithinRange(entity, 15f);
        for (PocketRogueEntity otherEntity : entitiesInRange) {
            if (entitiesCollide((PocketRogueEntity) entity, otherEntity)) {
                ((PocketRogueEntity) entity).doCollision(otherEntity);
            }
        }
    }

    private ArrayList<PocketRogueEntity> getEntitiesWithinRange(Entity entity, float tileRange) {
        ArrayList<PocketRogueEntity> entities = new ArrayList<>();
        for (Entity e : getEntities()) {
            if (!e.equals(entity)) {
                if (Utils.inRange((PocketRogueEntity) e,
                        (PocketRogueEntity) entity, tileRange)) {
                    entities.add((PocketRogueEntity) e);
                }
            }
        }
        return entities;
    }

    private boolean entitiesCollide(PocketRogueEntity e1, PocketRogueEntity e2) {
        Rectangle e1r = new Rectangle(e1.getPosition().x, e1.getPosition().y, 1, 1);
        Rectangle e2r = new Rectangle(e2.getPosition().x, e2.getPosition().y, 1, 1);
        //Logger.log(String.format("Checking if entity %s collided with entity %s", e1, e2));
        return e1r.overlaps(e2r);
    }
}
