package xyz.vec3d.game.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;

import xyz.vec3d.game.entities.PocketRogueEntity;
import xyz.vec3d.game.entities.components.AiComponent;
import xyz.vec3d.game.entities.components.PositionComponent;

/**
 * Created by Daron on 9/25/2017.
 * Copyright vec3d.xyz 2016
 * All rights reserved
 */

public class AiSystem extends IteratingSystem {

    public AiSystem() {
        super(Family.all(AiComponent.class, PositionComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        AiComponent aiComponent = entity.getComponent(AiComponent.class);
        PocketRogueEntity target = aiComponent.getTarget();
        PositionComponent currentPosition = entity.getComponent(PositionComponent.class);
        AiComponent.AiBehavior behavior = aiComponent.getBehavior();

        if (behavior == AiComponent.AiBehavior.HOSTILE) {
            if (target.getPosition().x < currentPosition.getPosition().x) {

            }
        }
    }

}
