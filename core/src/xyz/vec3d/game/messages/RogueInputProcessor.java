package xyz.vec3d.game.messages;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;

import xyz.vec3d.game.GameScreen;

/**
 * Created by Daron on 7/6/2016.
 * Copyright vec3d.xyz 2016
 * All rights reserved
 *
 * Handles all input from the player and will create messages to entities or
 * entity systems based on the input provided. There should only ever be one
 * instance of this class used in an {@link com.badlogic.gdx.InputMultiplexer}.
 */
public class RogueInputProcessor implements InputProcessor, MessageSender {

    private GameScreen gameScreen;

    /**
     * The {@link Vector2} that stores the movement updating vector. This gets
     * normalized then scaled by a movement factor in order to
     */
    private Vector2 mov = new Vector2(0, 0);

    /**
     * Creates a new {@link RogueInputProcessor} instance with a given {@link GameScreen}
     * instance to communicate with the screen if needed.
     *
     * @param gameScreen
     */
    public RogueInputProcessor(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
    }

    /**
     * Processes the movement input vector before sending it in a message to the
     * {@link xyz.vec3d.game.systems.MovementSystem}.
     * @return
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
    }

    @Override
    public void deregisterMessageReceiver(MessageReceiver messageReceiver) {
        messageReceivers.remove(messageReceiver);
    }

    @Override
    public void notifyMessageReceivers(Message message) {
        for(MessageReceiver messageReceiver : messageReceivers) {
            messageReceiver.onMessageReceived(message);
        }
    }
}
