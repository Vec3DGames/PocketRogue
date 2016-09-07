package xyz.vec3d.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import xyz.vec3d.game.gfx.SpriteSheet;

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
    public MenuScreen(final PocketRogue pocketRogue) {
        this.pocketRogue = pocketRogue;
        uiStage = new Stage(new StretchViewport(Settings.WIDTH, Settings.HEIGHT));
        Gdx.input.setInputProcessor(uiStage);
        skin = PocketRogue.getAssetManager().get("uiskin.json");

        //Set up UI components here.
        Table uiTable = new Table(skin);
        Label label = new Label("Menu", skin, "default");
        TextButton playButton = new TextButton("Play", skin);
        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                pocketRogue.setScreen(new GameScreen(pocketRogue));
            }
        });
        TextButton statsButton = new TextButton("Stats", skin);
        TextButton optionsButton = new TextButton("Options", skin);
        uiTable.add(label).padBottom(40).row();
        uiTable.add(playButton).width(200).padBottom(40).row();
        uiTable.add(statsButton).width(200).padBottom(40).row();
        uiTable.add(optionsButton).width(200);
        uiTable.setFillParent(true);

        //Add UI components to stage.
        uiStage.addActor(uiTable);

        //Load sprite sheets
        SpriteSheet sheet1 = new SpriteSheet("spritesheet_1.png");
        SpriteSheet[] sheets = new SpriteSheet[] {sheet1};
        PocketRogue.getInstance().setSpriteSheets(sheets);
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
     * @param width Width of the new target screen size.
     * @param height Height of the new target screen size.
     * @see com.badlogic.gdx.ApplicationListener#resize(int, int)
     */
    @Override
    public void resize(int width, int height) {
        uiStage.getViewport().update(width, height, true);
    }

    /**
     * @see com.badlogic.gdx.ApplicationListener#pause().
     */
    @Override
    public void pause() {

    }

    /**
     * @see com.badlogic.gdx.ApplicationListener#resume().
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
