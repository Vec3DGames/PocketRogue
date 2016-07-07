package xyz.vec3d.game.messages;

import com.badlogic.gdx.InputProcessor;

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
public class RogueInputProcessor implements InputProcessor {

    private GameScreen gameScreen;

    /**
     * Creates a new {@link RogueInputProcessor} instance with a given {@link GameScreen}
     * instance to communicate with the screen if needed.
     *
     * @param gameScreen
     */
    public RogueInputProcessor(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
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
}
