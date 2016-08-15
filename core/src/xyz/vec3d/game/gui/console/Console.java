package xyz.vec3d.game.gui.console;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import xyz.vec3d.game.Settings;

/**
 * Created by Daron on 8/13/2016.
 *
 * Shitty attempt at creating an in-game console.
 */
public class Console implements Disposable {

    private ConsoleDisplay display;
    private Window consoleWindow;
    private Stage stage;

    private boolean active = false;

    public Console(String title, Skin skin) {
        stage = new Stage(new StretchViewport(Settings.UI_WIDTH, Settings.UI_HEIGHT));
        display = new ConsoleDisplay(title, skin);
        display.pad(4);
        display.padTop(22);
        display.setFillParent(true);
        consoleWindow = new Window(title, skin);
        consoleWindow.addActor(display);
        consoleWindow.setMovable(false);
        consoleWindow.setResizable(false);
        consoleWindow.setSize(Settings.UI_WIDTH, 175);
        consoleWindow.setPosition(0, 0);
        consoleWindow.setVisible(false);

        stage.addActor(consoleWindow);

        InputMultiplexer im = (InputMultiplexer) Gdx.input.getInputProcessor();
        im.addProcessor(stage);
        Gdx.input.setInputProcessor(im);
    }

    public void toggle() {
        consoleWindow.setVisible(!consoleWindow.isVisible());
        display.toggle();
        active = !active;
    }

    @Override
    public void dispose() {
        InputMultiplexer im = (InputMultiplexer) Gdx.input.getInputProcessor();
        im.removeProcessor(stage);
        stage.dispose();
        Gdx.input.setInputProcessor(im);
    }

    public void draw() {
        if (!active) {
            return;
        }
        stage.act();
        if (!consoleWindow.isVisible()) {
            return;
        }
        stage.draw();
    }

    public void resize(int width, int height) {
        stage.getViewport().update(width, height);
    }

    public boolean isActive() {
        return active;
    }

    public ConsoleDisplay getDisplay() {
        return display;
    }
}
