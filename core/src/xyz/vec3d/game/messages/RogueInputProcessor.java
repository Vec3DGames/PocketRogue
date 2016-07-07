package xyz.vec3d.game.messages;

import com.badlogic.gdx.InputProcessor;

import xyz.vec3d.game.PocketRogue;

/**
 * Created by Daron on 7/6/2016.
 * Copyright vec3d.xyz 2016
 * All rights reserved
 */
public class RogueInputProcessor implements InputProcessor {

    private PocketRogue pocketRogue;

    public RogueInputProcessor(PocketRogue pocketRogue) {
        this.pocketRogue = pocketRogue;
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
