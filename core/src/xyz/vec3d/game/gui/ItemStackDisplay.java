package xyz.vec3d.game.gui;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Disposable;

import xyz.vec3d.game.PocketRogue;
import xyz.vec3d.game.model.ItemStack;
import xyz.vec3d.game.utils.Utils;

/**
 * Created by Daron on 8/24/2016.
 *
 * Graphical representation of an ItemStack to be drawn in a scroll pane.
 */
class ItemStackDisplay extends Actor implements Disposable {

    /**
     * The ItemStack represented by the display.
     */
    private ItemStack itemStack;

    /**
     * Texture for the ItemStack.
     */
    private TextureRegion itemIcon;

    /**
     * Font used for drawing item name and quantity.
     */
    private BitmapFont font;

    /**
     * True if the stack has been selected in the inventory.
     */
    private boolean selected;

    /**
     * Creates a new ItemStackDisplay from an ItemStack.
     *
     * @param itemStack The ItemStack to represent.
     */
    ItemStackDisplay(ItemStack itemStack) {
        this.itemStack = itemStack;
        //Get the texture.
        itemIcon = Utils.getItemTexture(itemStack);
        font = PocketRogue.getAssetManager().get("default.fnt", BitmapFont.class);
        this.setSize(200, 64);
    }

    /**
     * Returns the {@link ItemStack} that the display represents.
     *
     * @return ItemStack object stored in this display.
     */
    ItemStack getItemStack() {
        return itemStack;
    }

    void select() {
        selected = true;
        this.setDebug(true);
    }

    void deselect() {
        selected = false;
        this.setDebug(false);
    }

    boolean isSelected() {
        return selected;
    }

    @Override
    public void dispose() {
        font.dispose();
        itemIcon.getTexture().dispose();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(itemIcon, getX(), getY(), 64, 64);
        font.draw(batch, itemStack.toString(), getX() + 70, getY() + 64);
    }

}
