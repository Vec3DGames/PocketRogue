package xyz.vec3d.game.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;

import xyz.vec3d.game.entities.PocketRogueEntity;
import xyz.vec3d.game.entities.components.PositionComponent;
import xyz.vec3d.game.entities.components.VelocityComponent;

/**
 * Created by darakelian on 7/14/2016.
 * Copyright vec3d.xyz 2016
 * All rights reserved
 */
public class MovementSystem extends IteratingSystem {

    /**
     * A {@link ComponentMapper} for {@link PositionComponent}s that entities have.
     */
    private ComponentMapper<PositionComponent> pm = ComponentMapper.getFor(PositionComponent.class);

    /**
     * A {@link ComponentMapper} for {@link VelocityComponent}s that entities have.
     */
    private ComponentMapper<VelocityComponent> vm = ComponentMapper.getFor(VelocityComponent.class);

    /**1
     * Create a new MovementSystem composed of entities that have both a velocity
     * component as well as a position component.
     */
    public MovementSystem() {
        super(Family.all(VelocityComponent.class, PositionComponent.class).get());
    }

    protected void processEntity(Entity entity, float deltaTime) {
        PositionComponent positionComponent = pm.get(entity);
        VelocityComponent velocityComponent = vm.get(entity);
        positionComponent.getPosition().add(velocityComponent.getVelocity());
        if (entity instanceof PocketRogueEntity) {
            //Update animation here.
            ((PocketRogueEntity) entity).setAnimationFromVelocity(velocityComponent.getVelocity());
        }
        if (positionComponent.getPosition().x <= 0) {
            positionComponent.getPosition().x = 0;
        }
    }

}
