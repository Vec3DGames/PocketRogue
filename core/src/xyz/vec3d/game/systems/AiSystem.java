package xyz.vec3d.game.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;

import xyz.vec3d.game.entities.PocketRogueEntity;
import xyz.vec3d.game.entities.components.AiComponent;
import xyz.vec3d.game.entities.components.PositionComponent;
import xyz.vec3d.game.entities.components.VelocityComponent;
import xyz.vec3d.game.model.combat.CombatSystem;
import xyz.vec3d.game.utils.Utils;

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
        VelocityComponent currentVelocity = entity.getComponent(VelocityComponent.class);
        AiComponent.AiBehavior behavior = aiComponent.getBehavior();

        if (behavior == AiComponent.AiBehavior.HOSTILE) {
            float x = currentPosition.getPosition().x;
            float y = currentPosition.getPosition().y;
            float x2 = target.getPosition().x;
            float y2 = target.getPosition().y;

            currentVelocity.getVelocity().x = Utils.isLessThanWithRange(x, x2 - 1) ? 0.1f : Utils.isMoreThanWithRange(x, x2 + 1) ? -0.1f : 0;
            currentVelocity.getVelocity().y = Utils.isLessThanWithRange(y, y2 - 1) ? 0.1f : Utils.isMoreThanWithRange(y, y2 + 1) ? -0.1f : 0;

            //Attack player if in range
            CombatSystem combatSystem = ((PocketRogueEntity)entity).getCombatSystem();
            combatSystem.update(deltaTime);
            combatSystem.doPlayerAttack();
        }
    }

}
