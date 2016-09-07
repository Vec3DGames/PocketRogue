package xyz.vec3d.game.gui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Disposable;

import xyz.vec3d.game.PocketRogue;
import xyz.vec3d.game.model.ItemDefinitionLoader;
import xyz.vec3d.game.model.ItemDefinitionLoader.ItemDefinition;
import xyz.vec3d.game.model.ItemProperty;
import xyz.vec3d.game.model.ItemStack;
import xyz.vec3d.game.utils.Logger;

/**
 * Created by Daron on 8/24/2016.
 *
 * Graphical representation of an ItemStack to be drawn in a scroll pane.
 */
public class ItemStackDisplay extends Actor implements Disposable {

    /**
     * The ItemStack represented by the display.
     */
    private ItemStack itemStack;

    /**
     * Texture for the ItemStack.
     */
    private TextureRegion itemIcon;

    private BitmapFont font;

    /**
     * Creates a new ItemStackDisplay from an ItemStack.
     *
     * @param itemStack The ItemStack to represent.
     */
    public ItemStackDisplay(ItemStack itemStack) {
        this.itemStack = itemStack;
        //Get the texture.
        int itemId = this.itemStack.getItem().getId();
        ItemDefinition definition = ItemDefinitionLoader.getDefinition(itemId);
        int[] iconCoords = (int[]) definition.getProperty(ItemProperty.ICON);
        TextureRegion region = PocketRogue.getInstance().getSpriteSheet(itemId).
                getTextureFromSheet(iconCoords[0], iconCoords[1]);
        itemIcon = region;
        if (itemIcon == null) {
            Logger.log("No icon found.", this.getClass());
        }
        font = PocketRogue.getAssetManager().get("default.fnt", BitmapFont.class);
        this.setSize(200, 64);
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    @Override
    public void dispose() {
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(itemIcon, getX(), getY(), 64, 64);
        font.draw(batch, itemStack.getItem().getName(), getX() + 70, getY() + 64);
    }
}
