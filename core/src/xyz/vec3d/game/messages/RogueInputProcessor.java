package xyz.vec3d.game.messages;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import java.util.ArrayList;

import xyz.vec3d.game.GameScreen;

/**
 * Created by Daron on 7/6/2016.
 * Edited By Bobby
 * Copyright vec3d.xyz 2016
 * All rights reserved
 *
 * Handles all input from the player and will create messages to entities or
 * entity systems based on the input provided. There should only ever be one
 * instance of this class used in an {@link com.badlogic.gdx.InputMultiplexer}.
 */
public class RogueInputProcessor extends ChangeListener implements InputProcessor, MessageSender  {

    private GameScreen gameScreen;
    private ArrayList<MessageReceiver> messageReceivers = new ArrayList<MessageReceiver>();

    /**
     * Method called when the object registered with it is changed.
     * @param event The specific change event. Unused in this case.
     * @param actor The actor the change happened on. Needs to be cast as the touchpad.
     */
    @Override
    public void changed(ChangeListener.ChangeEvent event, Actor actor) {
        if(actor.getClass() == Touchpad.class) {
            mov.set(((Touchpad) actor).getKnobPercentX(), ((Touchpad) actor).getKnobPercentY());
        }
        if(actor.getClass() == TextButton.class){
            if(actor.getName().equals("D")){
                //TODO: DODGING
            }
            if(actor.getName().equals("A")){
                //TODO: ATTACKING
            }
        }
    }

    /**
     * The {@link Vector2} that stores the movement updating vector. This gets
     * normalized then scaled by a movement factor in order to
     */
    private Vector2 mov = new Vector2(0, 0);

    /**
     * Creates a new {@link RogueInputProcessor} instance with a given {@link GameScreen}
     * instance to communicate with the screen if needed.
     *
     * @param gameScreen the gamescreen nigga
     */
    public RogueInputProcessor(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
    }

    /**
     * Processes the movement input vector before sending it in a message to the
     * {@link xyz.vec3d.game.systems.MovementSystem}.
     * @return nvm am idiot
     */
    private Vector2 getMov() {
        return mov.cpy().nor();
    }

    public void update() {
        gameScreen.getPlayer().setVelocity(getMov());
    }

    @Override
    public boolean keyDown(int keycode) {
        switch(keycode) {
            case Keys.W:
                mov.add(0, 1);
                return true;
            case Keys.S:
                mov.add(0, -1);
                return true;
            case Keys.A:
                mov.add(-1, 0);
                return true;
            case Keys.D:
                mov.add(1, 0);
                return true;
            case Keys.SPACE:
                notifyMessageReceivers(new Message(Message.MessageType.PLAYER_INFO_HEALTH_CHANGED, -10));
                return true;
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        switch(keycode) {
            case Keys.W:
                mov.add(0, -1);
                return true;
            case Keys.S:
                mov.add(0, 1);
                return true;
            case Keys.A:
                mov.add(1, 0);
                return true;
            case Keys.D:
                mov.add(-1, 0);
                return true;
        }
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    @Override
    public void registerMessageReceiver(MessageReceiver messageReceiver) {
        messageReceivers.add(messageReceiver);
        System.out.println("Registered receiver: " + messageReceiver + " id: " + messageReceivers.size());
    }

    @Override
    public void deregisterMessageReceiver(MessageReceiver messageReceiver) {

    }

    @Override
    public void notifyMessageReceivers(Message message) {
        int i = 0;
        for (MessageReceiver messageReceiver : messageReceivers) {
            messageReceiver.onMessageReceived(message);
            System.out.println("sent message type: " + message.getMessageType() + " to receiver: " + (i += 1));
            if (i + 1 == messageReceivers.size()) {
                i = 0;
            }
        }
    }
}
