package xyz.vec3d.game.entities;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.math.Vector2;

import xyz.vec3d.game.entities.components.PositionComponent;
import xyz.vec3d.game.entities.components.RotationComponent;
import xyz.vec3d.game.entities.components.VelocityComponent;

/**
 * Created by Daron on 10/3/2016.
 *
 * Base projectile class used for anything that is fired from an entity and has
 * to interact with the world.
 */

public class Projectile extends PocketRogueEntity {

    /**
     * Object that references the entity that created the projectile. If the
     * entity was a player, it will be the player object. If the entity was a
     * regular entity, it will point back to the entity that fired it (enemy,
     * moving object or whatever it might be).
     */
    private PocketRogueEntity source;

    /**
     * How long the entity has been in the world. When this hits a certain
     * threshold the entity will remove itself from the engine.
     */
    private float life;

    /**
     * Time till the entity is dead.
     */
    private float lifeThreshold = 5;

    public Projectile(PocketRogueEntity source, float x, float y, float velocityX,
                      float velocityY, String name) {
        this(source, new Vector2(x, y), new Vector2(velocityX, velocityY), name);
    }

    public Projectile(PocketRogueEntity source, Vector2 position, Vector2 velocity,
                      String name) {
        super();
        this.source = source;
        add(new PositionComponent(position));
        add(new VelocityComponent(velocity));
        add(new RotationComponent(velocity.angle()));
        setName(name);
    }

    public PocketRogueEntity getSource() {
        return source;
    }

    @Override
    public void update(Engine engine, float deltaTime) {
        super.update(engine, deltaTime);
        life += deltaTime;
        if ((life >= lifeThreshold) && !isDead) {
            this.kill();
        }

        if (getVelocity().isZero() && !isDead) {
            this.kill();
        }
    }

    @Override
    public void doCollision(PocketRogueEntity otherEntity) {
        this.kill();
    }
}
