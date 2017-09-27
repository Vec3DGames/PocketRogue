package xyz.vec3d.game.entities.components;

import com.badlogic.ashley.core.Component;

import xyz.vec3d.game.entities.PocketRogueEntity;

/**
 * Created by Daron on 7/12/2017.
 * Copyright vec3d.xyz 2016
 * All rights reserved
 */

public class AiComponent implements Component {

    private PocketRogueEntity target;
    private AiBehavior behavior;

    public AiComponent(PocketRogueEntity target) {
        this.target = target;
        behavior = AiBehavior.HOSTILE;
    }

    public PocketRogueEntity getTarget() {
        return target;
    }

    public AiBehavior getBehavior() {
        return behavior;
    }

    public enum AiBehavior {
        HOSTILE
    }
}
