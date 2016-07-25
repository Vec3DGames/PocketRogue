package xyz.vec3d.game.gui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

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


    public PlayerInfoDisplay() {

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {

    }

    @Override
    public void onMessageReceived(Message message) {
        switch (message.getMessageType()) {
            case PLAYER_INFO_HEALTH_CHANGED:
                //Update health bar
                break;
            case PLAYER_INFO_MANA_CHANGED:
                //Update mana bar
                break;
        }
    }

}
