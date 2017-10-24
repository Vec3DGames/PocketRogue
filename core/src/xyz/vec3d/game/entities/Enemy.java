package xyz.vec3d.game.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import xyz.vec3d.game.PocketRogue;
import xyz.vec3d.game.entities.components.HealthComponent;
import xyz.vec3d.game.entities.components.MovementSpeedComponent;
import xyz.vec3d.game.entities.components.PositionComponent;
import xyz.vec3d.game.entities.components.VelocityComponent;
import xyz.vec3d.game.model.DefinitionLoader;
import xyz.vec3d.game.model.DefinitionProperty;

/**
 * Created by Daron on 8/11/2016.
 *
 * Base enemy class for all game enemies.
 */
public class Enemy extends PocketRogueEntity {

    private int id;

    public Enemy(int id, float x, float y) {
        super();
        this.id = id;
        add(new PositionComponent(x, y));
        add(new VelocityComponent());
        //Load up properties
        this.health = (float)(DefinitionLoader.getEntityDefinition(id).getProperty(DefinitionProperty.HEALTH));
        add(new HealthComponent(this.health));
        float moveSpeed = (float)(DefinitionLoader.getEntityDefinition(id).getProperty(DefinitionProperty.MOVE_SPEED));
        add(new MovementSpeedComponent(moveSpeed));
        //Set up animations here.
        Texture animationSheet = PocketRogue.getAsset("animation_sheets/enemy_animation" + id + ".png");
        TextureRegion[][] tmpRegions = TextureRegion.split(animationSheet, 32, 32);
        TextureRegion[] idle = new TextureRegion[3];
        System.arraycopy(tmpRegions[0], 0, idle, 0, idle.length);
        //Set the animations available
        setAnimations(new Animation[] {
                new Animation(1/10f, tmpRegions[3]), //Left
                new Animation(1/10f, tmpRegions[2]), //Right
                new Animation(1/10f, tmpRegions[1]), //Up
                new Animation(1/10f, tmpRegions[0]), //Down
                new Animation(1/10f, idle) //Idle
        });
    }

    public int getId() {
        return id;
    }

    @Override
    public void doCollision(PocketRogueEntity otherEntity) {
        if (otherEntity instanceof Projectile) {
            float damage = (float) ((Projectile) otherEntity).getProperty("dmg");
            applyDamage(damage);
        }
    }

    @Override
    public void doHit(PocketRogueEntity entityHitting, float damage) {
        if (entityHitting instanceof Player) {
            applyDamage(damage);
        }
    }
}
