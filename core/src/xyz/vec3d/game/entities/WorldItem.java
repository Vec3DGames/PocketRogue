package xyz.vec3d.game.entities;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import xyz.vec3d.game.entities.components.CollideComponent;
import xyz.vec3d.game.entities.components.PositionComponent;
import xyz.vec3d.game.entities.components.TextureComponent;
import xyz.vec3d.game.model.ItemStack;
import xyz.vec3d.game.utils.Utils;

/**
 * Created by Daron on 9/17/2016.
 *
 * Represents an Item being drawn on the ground.
 */
public class WorldItem extends PocketRogueEntity {

    private ItemStack itemStack;

    public WorldItem(ItemStack itemStack, float x, float y) {
        this.itemStack = itemStack;
        TextureRegion textureRegion = Utils.getItemTexture(this.itemStack);
        add(new PositionComponent(x, y));
        add(new TextureComponent(textureRegion));
        add(new CollideComponent());
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    @Override
    public void update(Engine engine, float deltaTime) {
        super.update(engine, deltaTime);
    }

    @Override
    public float getSize() {
        return 0.75f;
    }
}
