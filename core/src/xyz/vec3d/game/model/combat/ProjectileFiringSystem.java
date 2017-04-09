package xyz.vec3d.game.model.combat;

import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.math.Vector2;

import xyz.vec3d.game.entities.PocketRogueEntity;
import xyz.vec3d.game.entities.Projectile;

/**
 * Created by Daron on 3/16/2017.
 * Copyright vec3d.xyz 2017
 * All rights reserved.
 */

public class ProjectileFiringSystem extends EntitySystem {

    /**
     * The entity that this system is attached to.
     */
    private PocketRogueEntity owner;

    private float timeSinceLastShot = 0f;

    /**
     * Time in seconds before another projectile can be fired.
     */
    private float delayBetweenShots = 3f;

    public ProjectileFiringSystem(PocketRogueEntity owner) {
        this.owner = owner;
    }

    /**
     *
     * @param delta
     */
    @Override
    public void update(float delta) {
        timeSinceLastShot += delta;
    }

    public Projectile fireProjectile() {
        if (timeSinceLastShot >= delayBetweenShots) {
            timeSinceLastShot = 0;
            //Get player's direction to use as base velocity.
            Vector2 velocity = owner.getDirection().cpy();
            //Scale to desired speed
            velocity.scl(0.4f);
            //Get player's position to use as base position.
            Vector2 position = owner.getPosition().cpy();
            //Get angle of range -180<=theta<=180
            float angle = velocity.angle();
            float xMod = (angle == 270 || angle == 90) ? 0 :
                    (angle == 135 || angle == 225 || angle == 180) ? -1 : 1;
            float yMod = (angle == 180 || angle == 0) ? 0 :
                    (angle == 225 || angle == 315 || angle == 270) ? -1 : 1;
            position.add(1.1f * xMod, 1.1f * yMod);
            //Spawn projectile.
            Projectile projectile = new Projectile(owner, position, velocity, "Bolt");
            projectile.putProperty("dmg", 10.0f);
            return projectile;
        }
        return null;
    }

    public void setDelayBetweenShots(float delayBetweenShots) {
        this.delayBetweenShots = delayBetweenShots;
    }
}
