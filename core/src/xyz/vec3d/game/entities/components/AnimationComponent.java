package xyz.vec3d.game.entities.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.Animation;

/**
 * Created by Daron on 9/9/2016.
 *
 * Represents an Animation for an entity.
 */
public class AnimationComponent implements Component {

    private Animation animation;

    public AnimationComponent(Animation animation) {
        this.animation = animation;
    }

    public Animation getAnimation() {
        return animation;
    }
}
