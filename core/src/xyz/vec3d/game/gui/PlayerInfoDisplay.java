package xyz.vec3d.game.gui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import java.util.ArrayList;

import xyz.vec3d.game.PocketRogue;
import xyz.vec3d.game.entities.Player;
import xyz.vec3d.game.entities.components.HealthComponent;
import xyz.vec3d.game.entities.components.ManaComponent;
import xyz.vec3d.game.messages.IMessageReceiver;
import xyz.vec3d.game.messages.IMessageSender;
import xyz.vec3d.game.messages.Message;
import xyz.vec3d.game.utils.Utils;

/**
 * Created by Daron on 7/24/2016.
 * Copyright vec3d.xyz 2016
 * All rights reserved
 *
 * The PlayerInfoDisplay is a custom UI element that displays quick information
 * about the player such as their level and current health/mana levels. This is
 * a scene2d.ui Actor and thus is used on the Stage.
 */
public class PlayerInfoDisplay extends Actor implements IMessageReceiver, IMessageSender {

    private Texture playerIcon;
    private Texture frameBorder;
    private Texture barBackground;
    private Texture manaBar;
    private Texture healthBar;

    private BitmapFont font;

    //private float maxHealth, maxMana;
    //private float health, mana;
    private Player player;

    private GlyphLayout layout;

    private ArrayList<IMessageReceiver> messageReceivers = new ArrayList<>();

    public PlayerInfoDisplay(Player player) {
        playerIcon = PocketRogue.getAsset("playerIcon.png");
        frameBorder = PocketRogue.getAsset("frameBorder.png");
        barBackground = PocketRogue.getAsset("barBackground.png");
        manaBar = PocketRogue.getAsset("manaBar.png");
        healthBar = PocketRogue.getAsset("healthBar.png");
        font = PocketRogue.getAsset("default.fnt", false);
        layout = new GlyphLayout();
        addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {
                Actor actorHit = hit(x, y, true);
                if (actorHit == event.getListenerActor()) {
                    Message message = new Message(Message.MessageType.UI_ELEMENT_CLICKED, "player_info_display");
                    notifyMessageReceivers(message);
                }
            }

        });

        this.player = player;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        HealthComponent healthComponent = player.getComponent(HealthComponent.class);
        ManaComponent manaComponent = player.getComponent(ManaComponent.class);

        float health = healthComponent.getCurrentHealth();
        float maxHealth = healthComponent.getMaxHealth();
        float mana = manaComponent.getCurrentMana();
        float maxMana = manaComponent.getMaxMana();

        //Draw the frames of everything.
        batch.draw(frameBorder, this.getX(), this.getY());
        batch.draw(playerIcon, this.getX() + 4, this.getY() + 4);
        batch.draw(barBackground, this.getX() + frameBorder.getWidth(),
                this.getY() + frameBorder.getHeight() - barBackground.getHeight());
        batch.draw(barBackground, this.getX() + frameBorder.getWidth(),
                this.getY() + frameBorder.getHeight() - (5 + 2 * barBackground.getHeight()));

        //Draw health/mana bars at their % left values.
        batch.draw(healthBar, this.getX() + frameBorder.getWidth() + 2,
                this.getY() + frameBorder.getHeight() - barBackground.getHeight() + 2,
                healthBar.getWidth() * (health / maxHealth), healthBar.getHeight());
        batch.draw(manaBar, this.getX() + frameBorder.getWidth() + 2,
                this.getY() + frameBorder.getHeight() - (11 + 2 * manaBar.getHeight()),
                manaBar.getWidth() * (mana / maxMana), manaBar.getHeight());

        float stringX, stringY;

        String drawnString = health + "/" + maxHealth;

        layout.setText(font, drawnString);
        stringX = Utils.getPosCenterX(layout.width,
                barBackground.getWidth(), this.getX() + frameBorder.getWidth());
        stringY = Utils.getPosCenterY(layout.height, barBackground.getHeight(),
                this.getY() + frameBorder.getHeight() - barBackground.getHeight()
                        + font.getCapHeight());
        font.draw(batch, drawnString, stringX, stringY);

        drawnString = mana + "/" + maxMana;
        stringX = Utils.getPosCenterX(layout.width,
                barBackground.getWidth(), this.getX() + frameBorder.getWidth());
        stringY = Utils.getPosCenterY(layout.height, barBackground.getHeight(),
                this.getY() + frameBorder.getHeight() - (13 + 2 * manaBar.getHeight())
                        + font.getCapHeight());
        layout.setText(font, drawnString);
        font.draw(batch, drawnString, stringX, stringY);
    }

    @Override
    public void onMessageReceived(Message message) {
        switch (message.getMessageType()) {
            case PLAYER_CHANGED:
                this.player = (Player)message.getPayload()[0];
                break;
        }
    }

    @Override
    public Actor hit(float x, float y, boolean touchable) {
        if (x >= 0 && x <= frameBorder.getWidth() && y >= 0 && y <= frameBorder.getHeight()) {
            return this;
        }
        return null;
    }

    @Override
    public void registerMessageReceiver(IMessageReceiver messageReceiver) {
        messageReceivers.add(messageReceiver);
    }

    @Override
    public void deregisterMessageReceiver(IMessageReceiver messageReceiver) {

    }

    @Override
    public void notifyMessageReceivers(Message message) {
        for (IMessageReceiver messageReceiver : messageReceivers) {
            messageReceiver.onMessageReceived(message);
        }
    }
}
