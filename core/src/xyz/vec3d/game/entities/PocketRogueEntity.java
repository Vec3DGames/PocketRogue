package xyz.vec3d.game.entities;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.Vector2;

import xyz.vec3d.game.entities.components.AnimationComponent;

/**
 * Created by Daron on 9/15/2016.
 *
 * Custom Ashley {@link Entity} implementation with common methods for all entities that
 * pertain to the PocketRogue system.
 */
public class PocketRogueEntity extends Entity {

    /**
     * Array of {@link Animation}s that the entity can use.
     */
    private Animation[] possibleAnimations;

    /**
     * Sets the animation array to the specified array. Entities that need
     * animations must call this method. Animation direction convention is that
     * 0 = left, 1 = right, 2 = up, 3 = down, 4 = idle.
     *
     * @param possibleAnimations Animation[] of entity's animations.
     */
    public void setAnimations(Animation[] possibleAnimations) {
        this.possibleAnimations = possibleAnimations;
    }

    /**
     * Returns an {@link Animation} object that is based off of the entity's
     * current velocity. Direction convention is specified in
     * {@link PocketRogueEntity#setAnimations(Animation[])}.
     *
     * @param velocity Vector2 representing the entity's velocity.
     *
     * @return Animation to be drawn.
     */
    public Animation getAnimationForVelocity(Vector2 velocity) {
        if (possibleAnimations == null) {
            return null;
        }
        if (velocity == null) {
            return possibleAnimations[0];
        }
        float xMag = velocity.x;
        float yMag = velocity.y;
        //Logger.log("X velocity: " + xMag + " Y velocity: " + yMag);
        if (velocity.isZero()) {
            return possibleAnimations[possibleAnimations.length - 1];
        }
        if (yMag < 1 || yMag > -1) {
            //Moving up or down.
            if (xMag > 0) {
                //Right.
                return possibleAnimations[1];
            } else if (xMag < 0) {
                //Left.
                return possibleAnimations[0];
            }
        }
        if (xMag < 1 || xMag > -1) {
            //Moving up or down.
            if (yMag > 0) {
                //Up.
                return possibleAnimations[2];
            } else if (yMag < 0) {
                //Down.
                return possibleAnimations[3];
            }
        }
        return possibleAnimations[2];
    }

    /**
     * Sets the {@link AnimationComponent} of the entity based on it's velocity.
     *
     * @param velocity Vector2 representing the entity's velocity.
     */
    public void setAnimationFromVelocity(Vector2 velocity) {
        Animation animation = getAnimationForVelocity(velocity);
        if (animation != null) {
            AnimationComponent animationComponent = getComponent(AnimationComponent.class);
            if (animationComponent != null) {
                animationComponent.setAnimation(animation);
            } else {
                add(new AnimationComponent(animation));
            }
        }
    }
}
