package xyz.vec3d.game.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import xyz.vec3d.game.GameScreen;
import xyz.vec3d.game.PocketRogue;
import xyz.vec3d.game.entities.PocketRogueEntity;
import xyz.vec3d.game.entities.components.AnimationComponent;
import xyz.vec3d.game.entities.components.HealthComponent;
import xyz.vec3d.game.entities.components.PositionComponent;
import xyz.vec3d.game.entities.components.RotationComponent;
import xyz.vec3d.game.entities.components.TextureComponent;

/**
 * Created by darakelian on 7/7/2016.
 * Copyright vec3d.xyz 2016
 * All rights reserved
 *
 * The rendering system is responsible for drawing entities in a batch with a
 * viewport set by the game's camera. This class extends {@link IteratingSystem}
 * which means it implements a method that gets called for each entity that the
 * system is responsible for. That method gets the relevant components from the
 * entity representing position and the {@link Texture} to draw for the entity.
 */
public class RenderingSystem extends IteratingSystem {

    /**
     * A {@link ComponentMapper} for {@link TextureComponent}s that entities have.
     */
    private ComponentMapper<TextureComponent> tm = ComponentMapper.getFor(TextureComponent.class);

    /**
     * A {@link ComponentMapper} for {@link PositionComponent}s that entities have.
     */
    private ComponentMapper<PositionComponent> pm = ComponentMapper.getFor(PositionComponent.class);

    /**
     * A {@link ComponentMapper} for {@link AnimationComponent}s that entities have.
     */
    private ComponentMapper<AnimationComponent> am = ComponentMapper.getFor(AnimationComponent.class);

    /**
     * A {@link ComponentMapper} for {@link RotationComponent}s that entities have.
     */
    private ComponentMapper<RotationComponent> rm = ComponentMapper.getFor(RotationComponent.class);

    private ComponentMapper<HealthComponent> hm = ComponentMapper.getFor(HealthComponent.class);

    /**
     * The {@link SpriteBatch} being used to draw entities.
     */
    private SpriteBatch batch;
    private ShapeRenderer shapeRenderer;

    /**
     * Creates a new {@link RenderingSystem} for the game engine from a provided
     * {@link SpriteBatch}.
     *
     * @param batch The SpriteBatch provided from the {@link xyz.vec3d.game.GameScreen}.
     */
    public RenderingSystem(SpriteBatch batch, ShapeRenderer shapeRenderer) {
        super(Family.all(PositionComponent.class).one(TextureComponent.class,
                AnimationComponent.class).get());
        this.batch = batch;
        this.shapeRenderer = shapeRenderer;
    }

    /**
     * Called for each entity in the system each game loop. Gets the position
     * component and texture component in order to draw the texture on the batch
     * that was provided at creation time. Projection matrices are set in
     * {@link xyz.vec3d.game.GameScreen}'s render() method.
     *
     * @param entity The entity being drawn.
     * @param deltaTime The delta time of the engine.
     */
    protected void processEntity(Entity entity, float deltaTime) {
        //Get texture and position components from the component mappers.
        TextureComponent textureComponent = tm.get(entity);
        PositionComponent positionComponent = pm.get(entity);
        AnimationComponent animationComponent = am.get(entity);
        HealthComponent healthComponent = hm.get(entity);

        float x = positionComponent.getPosition().x;
        float y = positionComponent.getPosition().y;

        //If in debug mode, draw a red square around hitbox
        if (GameScreen.IS_DEBUG) {
            shapeRenderer.rect(x, y, 1, 1);
        }

        //Try to draw a health bar for entities that have health.
        if (healthComponent != null) {
            float healthBarScale = healthComponent.getPercentHealthRemainingScale();
            Texture healthBar = PocketRogue.getAsset("healthBar.png");
            batch.draw(healthBar, x, y + 1.1f, 1 * healthBarScale, 0.125f);
        }

        //First see if the entity has an animation that needs to play.
        if (animationComponent != null) {
            boolean moving = ((PocketRogueEntity) entity).isMoving();
            animationComponent.addAnimationTime(deltaTime);
            /*float animationTime = animationComponent.getAnimationTime();
            TextureRegion animationFrame = animationComponent.getAnimation()
                    .getKeyFrame(moving ? animationTime : 0, true);
            batch.draw(animationFrame, x, y, 1, 1);*/
            float animationTime = animationComponent.getAnimationTime();
            for (Animation animation : animationComponent.getAnimations()) {
                TextureRegion animationFrame = animation.getKeyFrame(moving ?
                        animationTime : 0, true);
                batch.draw(animationFrame, x, y, ((PocketRogueEntity) entity).getSize(), ((PocketRogueEntity) entity).getSize());
            }
            return;
        }

        //Otherwise, get the actual texture object from the texture component.
        TextureRegion texture = textureComponent.getTexture();

        //Draw the texture at the position specified. A width/height of 1 is used
        //to represent the fact that the texture should be 1 world unit (32px) in
        //size.
        RotationComponent rotationComponent = rm.get(entity);
        if (rotationComponent != null) {
            batch.draw(texture, x, y, 0.5f, 0.5f, 1, 1, 1, 1,
                    rotationComponent.getRotationAngle() - 90f, false);
            return;
        }
        batch.draw(texture, x, y, ((PocketRogueEntity) entity).getSize(), ((PocketRogueEntity) entity).getSize());
    }

}
