package xyz.vec3d.game.entities.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.Animation;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Daron on 9/9/2016.
 *
 * Represents an Animation container for an entity. The design choice of using a
 * Map instead of creating a bunch of different components is because it is much
 * easier to interact with a single AnimationComponent rather than several types
 * of AnimationComponents. Without a map, there would most likely have to be
 * a component for every type of animation. Say a TemporaryAnimationComponent and
 * a WeaponAnimationComponent. Instead of cluttering the code base with a bunch
 * of classes that are all the same functionality wise, we just keep track of
 * the animations with keys. The issue with this currently is a Map doesn't ensure
 * order so there will probably be issues with layering of the animations but
 * that bridge can be crossed when it gets there.
 */
public class AnimationComponent implements Component {

    /*private List<Animation> animations;

    private float animationTime;

    public AnimationComponent(Animation animation) {
        this(new Animation[] {animation});
    }

    public AnimationComponent(Animation... animations) {
        this.animations = Arrays.asList(animations);
    }

    public Animation[] getAnimations() {
        return animations.toArray(new Animation[animations.size()]);
    }

    public void addAnimation(Animation animation) {
        this.animations.add(animation);
    }

    public void removeAnimation(Animation animation) {
        this.animations.remove(animation);
    }

    public void addAnimationTime(float deltaTime) {
        animationTime += deltaTime;
    }

    public float getAnimationTime() {
        return animationTime;
    }*/

    /**
     * A map of all animations that should be updated for an entity. In general,
     * something like the movement animation will probably never be removed but
     * temporary animations will be added/removed often.
     */
    private Map<String, Animation> animationMap;

    /**
     * The animation time used for all the various animations in the component.
     */
    private float animationTime;

    public AnimationComponent() {
        animationMap = new HashMap<>();
    }

    /**
     * Updates the specific animation type.
     *
     * @param animationType The animation type being set.
     * @param animation The animation that corresponds to the type.
     */
    public void setAnimation(String animationType, Animation animation) {
        animationMap.put(animationType, animation);
    }

    /**
     * Updates the time used to get frames of the animations.
     *
     * @param deltaTime The amount of time to add to the counter.
     */
    public void addAnimationTime(float deltaTime) {
        this.animationTime += deltaTime;
    }

    /**
     * Used to return all the active animations attached to the component. Note
     * that we don't return the keys because it is useless to have them. The map
     * is private so can't actually get them outside of the class without making
     * a new method.
     *
     * @return An array of AnimationComponents.
     */
    public Animation[] getAnimations() {
        return animationMap.values().toArray(new Animation[animationMap.size()]);
    }

    public float getAnimationTime() {
        return animationTime;
    }
}
