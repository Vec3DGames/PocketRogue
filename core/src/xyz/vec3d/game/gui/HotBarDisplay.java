package xyz.vec3d.game.gui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import xyz.vec3d.game.PocketRogue;
import xyz.vec3d.game.entities.Player;
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

    public HotBarDisplay(Stage stage) {
        hotBarTexture = PocketRogue.getAsset("hotbar.png");
        setSize(hotBarTexture.getWidth(), hotBarTexture.getHeight());
        addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                int slot = getActionBoxForX(x);
                HotBarItem hotBarItem = hotBarItems[slot];
                if (hotBarItem == null) {
                    hotBarItems[slot] = ShowHotBarItemSelectionPrompt();
                }
            }
        });
        this.stage = stage;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(hotBarTexture, getX(), getY());
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

    public HotBarItem ShowHotBarItemSelectionPrompt() {
        Window window = GetItemSelectionWindow();
        stage.addActor(window);

        return null;
    }

    private Window GetItemSelectionWindow() {
        Window window = new Window("Choose an Item", (Skin) PocketRogue.getAsset("uiskin.json"));
        window.setSize(400, 300);
        Utils.centerActor(window, stage);

        return window;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    private class HotBarItem {

    }

    private class SpellHotBarItem extends HotBarItem {

    }

    private class ItemHotBarItem extends HotBarItem {

    }
}
