package xyz.vec3d.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

/**
 * Created by darakelian on 6/28/2016.
 * Copyright vec3d.xyz 2016
 * All rights reserved
 *
 * Menu state representation. Manages a Stage for UI components.
 */
public class MenuScreen implements Screen {

    /**
     * The {@link Stage} for UI components.
     */
    private Stage uiStage;

    private Skin skin;

    /**
     * The {@link PocketRogue} game instance.
     */
    private PocketRogue pocketRogue;

    /**
     * The background color constant for the menu screen. Ignore this for when
     * there is a proper background image to draw.
     */
    private final float BACK_COLOR = 63f/255f;

    /**
     * Creates a new {@link MenuScreen} instance and sets up the UI components.
     */
    public MenuScreen(PocketRogue pocketRogue) {
        this.pocketRogue = pocketRogue;
        uiStage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(uiStage);
        skin = this.pocketRogue.getAssetManager().get("uiskin.json");

        //Set up UI components here.
        Label label = new Label("Menu", skin, "default");

        //Add UI components to stage.
        uiStage.addActor(label);
    }
    /**
     * Called when this screen becomes the current screen for a {@link com.badlogic.gdx.Game}.
     */
    @Override
    public void show() {

    }

    /**
     * Called when the screen should render itself.
     *
     * @param delta The time in seconds since the last render.
     */
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(BACK_COLOR, BACK_COLOR, BACK_COLOR, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        uiStage.draw();
        update(delta);
    }

    private void update(float delta) {
        uiStage.act(delta);
    }

    /**
     * @param width
     * @param height
     * @see com.badlogic.gdx.ApplicationListener#resize(int, int)
     */
    @Override
    public void resize(int width, int height) {
        uiStage.getViewport().update(width, height, true);
    }

    /**
     * @see com.badlogic.gdx.ApplicationListener#pause()
     */
    @Override
    public void pause() {

    }

    /**
     * @see com.badlogic.gdx.ApplicationListener#resume()
     */
    @Override
    public void resume() {

    }

    /**
     * Called when this screen is no longer the current screen for a {@link com.badlogic.gdx.Game}.
     */
    @Override
    public void hide() {

    }

    /**
     * Called when this screen should release all resources.
     */
    @Override
    public void dispose() {
        uiStage.dispose();
    }
}
