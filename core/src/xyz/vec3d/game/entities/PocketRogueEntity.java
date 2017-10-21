package xyz.vec3d.game.entities;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.Vector2;

import xyz.vec3d.game.entities.components.AnimationComponent;
import xyz.vec3d.game.entities.components.CollideComponent;
import xyz.vec3d.game.entities.components.HealthComponent;
import xyz.vec3d.game.entities.components.MovementSpeedComponent;
import xyz.vec3d.game.entities.components.PositionComponent;
import xyz.vec3d.game.entities.components.VelocityComponent;
import xyz.vec3d.game.model.SpellManager;
import xyz.vec3d.game.model.combat.CombatSystem;
import xyz.vec3d.game.model.combat.ProjectileFiringSystem;
import xyz.vec3d.game.utils.Logger;

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
     * Updated each time a new animation is selected.
     */
    private Animation lastUsedAnimation;

    /**
     * Updated each time the entity changes direction.
     */
    private Vector2 lastDirection = new Vector2(0, -1);

    /**
     * The entity's name.
     */
    private String name;

    /**
     * True if the entity is dead and should be removed from the engine.
     */
    private boolean isDead;

    float health;

    ProjectileFiringSystem projectileFiringSystem;
    SpellManager spellManager;
    private CombatSystem combatSystem;

    public PocketRogueEntity() {
        add(new CollideComponent());
    }

    /**
     * Sets the animation array to the specified array. Entities that need
     * animations must call this method. Animation direction convention is that
     * 0 = left, 1 = right, 2 = up, 3 = down, 4 = idle.
     *
     * @param possibleAnimations Animation[] of entity's animations.
     */
    void setAnimations(Animation[] possibleAnimations) {
        this.possibleAnimations = possibleAnimations;
        lastUsedAnimation = this.possibleAnimations[this.possibleAnimations.length - 1];
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
    private Animation getAnimationForVelocity(Vector2 velocity) {
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
            return lastUsedAnimation;
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
        lastUsedAnimation = animation;
        if (animation != null) {
            AnimationComponent animationComponent = getComponent(AnimationComponent.class);
            if (animationComponent != null) {
                animationComponent.setAnimation("movement", animation);
            } else {
                AnimationComponent aComponent = new AnimationComponent();
                aComponent.setAnimation("movement", animation);
                add(aComponent);
            }
        }
    }

    /**
     * Returns whether or not an entity is moving at the time of the method being
     * called.
     *
     * @return True if the velocity is non-zero.
     */
    public boolean isMoving() {
        VelocityComponent vc = getComponent(VelocityComponent.class);
        return !vc.getVelocity().isZero();
    }

    /**
     * Called whenever an entity should be removed from the engine.
     */
    public void kill() {
        Logger.log(PocketRogueEntity.class, "Killed entity: " + getName());
        isDead = true;
    }

    public void update(Engine engine, float delta) {
        if (isDead) {
            engine.removeEntity(this);
        }
        if (projectileFiringSystem != null) {
            projectileFiringSystem.update(delta);
        }
    }

    /**
     * Attempts to retrieve a PositionComponent for the entity.
     *
     * @return The position component or null if one doesn't exist.
     */
    public Vector2 getPosition() {
        return getComponent(PositionComponent.class).getPosition();
    }

    /**
     * Attempts to retrieve a VelocityComponent for the entity.
     *
     * @return The velocity component or null if one doesn't exist.
     */
    Vector2 getVelocity() {
        return getComponent(VelocityComponent.class).getVelocity();
    }

    /**
     * Sets the velocity of the entity to the new velocity.
     *
     * @param velocity The Vector2 representing entity's new velocity.
     */
    public void setVelocity(Vector2 velocity) {
        Vector2 vel = getVelocity();
        if (vel != null) {
            getVelocity().set(velocity.cpy().scl(getMoveSpeed()));
        } else {
            add(new VelocityComponent(velocity.cpy().scl(getMoveSpeed())));
        }
        if (!velocity.isZero()) {
            lastDirection = velocity.cpy();
        }
    }

    /**
     * Attempts to retrieve a MovespeedComponent for the entity.
     *
     * @return The movespeed component or null if one doesn't exist.
     */
    private float getMoveSpeed() {
        return getComponent(MovementSpeedComponent.class).getMoveSpeed();
    }

    public Vector2 getDirection() {
        return lastDirection;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * Called whenever the entity collides with another entity. Collision is
     * currently defined as the rectangles of the entities intersecting or not.
     *
     * @param entityCollidedWith This is the entity that collided with the entity
     *                           this method is called on. Say a projectile collides
     *                           with a mob, the mob would have doCollision called
     *                           where entityCollidedWith is the projectile object.
     */
    public void doCollision(PocketRogueEntity entityCollidedWith) {
        //Logger.log(String.format("Entity %s collided with entity %s", this, entityCollidedWith), PocketRogueEntity.class);
    }

    /**
     * Called whenever the entity is hit be another entity. This is different than
     * the doCollision method as this one is called by melee attack methods or
     * any other systems that need to apply an onHit effect without using entities
     * for collision.
     *
     * Note: doCollision could in theory call this method for various other on
     * hit effects as opposed to doing it in the doCollision method.
     *
     * @param entityHitting This is the entity that is applying the hit effect.
     *                      For example, if the player hits a mob, doHit would be
     *                      called on the mob and entityHitting would reference
     *                      the player.
     */
    public void doHit(PocketRogueEntity entityHitting, float damage) {
        applyDamage(damage);
    }

    public ProjectileFiringSystem getFiringSystem() {
        return projectileFiringSystem;
    }

    public SpellManager getSpellManager() {
        return spellManager;
    }

    public CombatSystem getCombatSystem() {
        return combatSystem;
    }

    public void createCombatSystem(Engine engine) {
        this.combatSystem = new CombatSystem(engine, this);
    }

    void applyDamage(float damage) {
        HealthComponent healthComponent = getComponent(HealthComponent.class);
        if (healthComponent != null) {
            healthComponent.removeHealth(damage);
        } else {
            this.health -= damage;
        }
        if (!isDead) {
            if (healthComponent != null && healthComponent.getCurrentHealth() <= 0) {
                this.kill();
            }
            if (healthComponent == null && this.health <= 0) {
                this.kill();
            }
        }
    }

    public float getSize() {
        return 1.0f;
    }

    boolean isDead() {
        return isDead;
    }
}
