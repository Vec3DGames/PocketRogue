package xyz.vec3d.game.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;

import xyz.vec3d.game.entities.PocketRogueEntity;

/**
 * Created by Daron on 9/21/2016.
 *
 * System for updating entity that does not fit into movement or rendering.
 */
public class UpdateSystem extends IteratingSystem {

    /**
     * Engine used for removing/adding entities.
     */
    private Engine engine;

    public UpdateSystem() {
        super(Family.all().get());
    }

    @Override
    public void addedToEngine(Engine engine) {
        this.engine = engine;
        super.addedToEngine(engine);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        ((PocketRogueEntity) entity).update(engine, deltaTime);
    }
}
