package xyz.vec3d.game.entities.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Engine;

import xyz.vec3d.game.entities.PocketRogueEntity;

/**
 * Created by Daron on 7/12/2017.
 * Copyright vec3d.xyz 2016
 * All rights reserved
 */

public class AiComponent implements Component {

    private PocketRogueEntity target;
    private AiBehavior behavior;

    private Engine engine;

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

    /**
     * Sets the engine instance for the component and then also initializes a new combat system.
     *
     * @param engine Engine to use for combat system
     */
    public void setEngine(Engine engine) {
        this.engine = engine;
    }

    public Engine getEngine() {
        return engine;
    }

    public enum AiBehavior {
        HOSTILE
    }
}
