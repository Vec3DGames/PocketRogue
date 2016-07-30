package xyz.vec3d.game.gui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;

import xyz.vec3d.game.PocketRogue;
import xyz.vec3d.game.messages.Message;
import xyz.vec3d.game.messages.MessageReceiver;

/**
 * Created by Daron on 7/24/2016.
 * Copyright vec3d.xyz 2016
 * All rights reserved
 *
 *
 */
public class PlayerInfoDisplay extends Actor implements MessageReceiver{

    private Texture playerIcon;
    private Texture frameBorder;
    private Texture barBackground;
    private Texture manaBar;
    private Texture healthBar;

    private BitmapFont font;

    private float maxHealth, maxMana;
    private float health, mana;

    public PlayerInfoDisplay() {
        playerIcon = PocketRogue.getAssetManager().get("playerIcon.png", Texture.class);
        frameBorder = PocketRogue.getAssetManager().get("frameBorder.png", Texture.class);
        barBackground = PocketRogue.getAssetManager().get("barBackground.png", Texture.class);
        manaBar = PocketRogue.getAssetManager().get("manaBar.png", Texture.class);
        healthBar = PocketRogue.getAssetManager().get("healthBar.png", Texture.class);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(frameBorder, this.getX(), this.getY());
        batch.draw(playerIcon, this.getX() + 4, this.getY() + 4);
        batch.draw(barBackground, this.getX() + frameBorder.getWidth(),
                this.getY() + frameBorder.getHeight() - barBackground.getHeight());
        batch.draw(barBackground, this.getX() + frameBorder.getWidth(),
                this.getY() + frameBorder.getHeight() - (5 + 2 * barBackground.getHeight()));

    }

    @Override
    public void onMessageReceived(Message message) {
        switch (message.getMessageType()) {
            case PLAYER_INFO_MAX_CHANGED:
                //Update the info of max values for health/mana.
                maxHealth = (Integer) message.getPayload()[0];
                maxMana = (Integer) message.getPayload()[1];
                break;
            case PLAYER_INFO_HEALTH_CHANGED:
                //Update health bar value.
                health = (Integer) message.getPayload()[0];
                break;
            case PLAYER_INFO_MANA_CHANGED:
                //Update mana bar value.
                mana = (Integer) message.getPayload()[0];
                break;
        }
    }

}
