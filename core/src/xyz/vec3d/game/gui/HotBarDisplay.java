package xyz.vec3d.game.gui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import xyz.vec3d.game.GameScreen;
import xyz.vec3d.game.PocketRogue;
import xyz.vec3d.game.entities.Player;
import xyz.vec3d.game.model.ItemStack;
import xyz.vec3d.game.utils.Utils;

/**
 * Created by Daron on 5/21/2017.
 * Copyright vec3d.xyz 2016
 * All rights reserved
 */

public class HotBarDisplay extends Actor {

    private Texture hotBarTexture;

    private HotBarItem[] hotBarItems = new HotBarItem[9];

    private Stage stage;
    private Player player;
    private GameScreen gameScreen;
    private Skin skin;

    private BitmapFont font;

    public HotBarDisplay(Stage stage, GameScreen gameScreen) {
        hotBarTexture = PocketRogue.getAsset("hotbar.png");
        setSize(hotBarTexture.getWidth(), hotBarTexture.getHeight());
        addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                int slot = getActionBoxForX(x);
                HotBarItem hotBarItem = hotBarItems[slot];
                if (hotBarItem == null) {
                    ShowHotBarItemSelectionPrompt(slot);
                }
            }
        });
        this.stage = stage;
        this.gameScreen = gameScreen;
        this.skin = PocketRogue.getAsset("uiskin.json");
        font = PocketRogue.getAsset("default.fnt", false);

        setName("hot_bar_display");
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(hotBarTexture, getX(), getY());

        for (int slot = 0; slot < hotBarItems.length; slot++) {
            HotBarItem hotBarItem = hotBarItems[slot];

        if (hotBarItem == null || hotBarItem.itemStack == null)
            continue;

        TextureRegion textureRegion = Utils.getItemTexture(hotBarItem.itemStack);
        if (textureRegion == null)
            continue;

        String amount = hotBarItem.itemStack.getQuantityAsString();

        batch.draw(textureRegion, getX() + 14 + (54 * (slot)), getY() + 14, 32, 32);
        font.draw(batch, amount, getX() + 15 + (54 * (slot)), getY() + 15);
    }
    }

    /**
     * Determines which hot bar slot was clicked based on the mouse's x coordinate.
     *
     * @param mouseX The mouse's x coordinate.
     *
     * @return The 0-indexed slot of the hot bar that was clicked.
     */
    private int getActionBoxForX(float mouseX) {
        return (int) (mouseX - 6) / 54;
    }

    private void ShowHotBarItemSelectionPrompt(int slot) {
        setName("hot_bar_display" + slot);
        gameScreen.openGui("player_inventory", player.getInventory(), skin, gameScreen.getCombatSystem(), this);
    }

    public void refreshHotBarDisplay() {
        for (int slot = 0; slot < player.getInventory().getHotBarItems().length; slot++) {
            ItemStack itemStack = player.getInventory().getHotBarItems()[slot];
            if (itemStack == null)
                continue;

            hotBarItems[slot] = new HotBarItem(itemStack);
        }
        setName("hot_bar_display");
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    private class HotBarItem {

        private ItemStack itemStack;

        HotBarItem(ItemStack itemStack) {
            this.itemStack = itemStack;
        }

    }
}
