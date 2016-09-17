package xyz.vec3d.game.entities.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.Animation;

import xyz.vec3d.game.utils.Logger;

/**
 * Created by Daron on 9/9/2016.
 *
 * Represents an Animation for an entity.
 */
public class AnimationComponent implements Component {

    private Animation animation;

    private float animationTime;

    public AnimationComponent(Animation animation) {
        this.animation = animation;
    }

    public Animation getAnimation() {
        return animation;
    }

    public void setAnimation(Animation animation) {
        this.animation = animation;
    }

    public void addAnimationTime(float deltaTime) {
        animationTime += deltaTime;
    }

    public float getAnimationTime() {
        return animationTime;
    }
}
