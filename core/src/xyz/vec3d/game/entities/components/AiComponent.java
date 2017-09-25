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

    public AiComponent(PocketRogueEntity target) {
        this.target = target;
    }

    public PocketRogueEntity getTarget() {
        return target;
    }
}
