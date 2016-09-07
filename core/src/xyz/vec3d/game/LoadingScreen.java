package xyz.vec3d.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.loaders.SkinLoader;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import xyz.vec3d.game.gfx.SpriteSheet;
import xyz.vec3d.game.model.ItemDefinitionLoader;
import xyz.vec3d.game.utils.Utils;

/**
 * Created by Daron on 6/28/2016.
 * Copyright vec3d.xyz 2016
 * All rights reserved
 * All niggers should die.
 */
public class LoadingScreen implements Screen {

    private PocketRogue pocketRogue;

    private Stage uiStage;
    private Image logo;
    private Image loadingFrame;
    private Image loadingBarHidden;
    private Image screenBg;
    private Image loadingBg;
    private Label text;

    private float startX, endX;
    private LoadingBar loadingBar;
    private float percent;

    public LoadingScreen(PocketRogue pocketRogue) {
        this.pocketRogue = pocketRogue;
        PocketRogue.getAssetManager().load("loading.pack", TextureAtlas.class);
        PocketRogue.getAssetManager().load("default.fnt", BitmapFont.class);
        PocketRogue.getAssetManager().finishLoading();

        uiStage = new Stage(new StretchViewport(Settings.WIDTH, Settings.HEIGHT));

        TextureAtlas atlas = PocketRogue.getAssetManager().get("loading.pack", TextureAtlas.class);
        BitmapFont font = PocketRogue.getAssetManager().get("default.fnt", BitmapFont.class);
        font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        Label.LabelStyle style = new Label.LabelStyle();
        style.font = font;
        text = new Label("Touch to start!", style);
        text.setFontScale(2);
        text.setSize(text.getWidth() * 2, text.getHeight() * 2);
        text.addAction(Actions.alpha(0));
        text.act(0);
        text.addAction(Actions.forever(Actions.sequence(Actions.fadeIn(1f), Actions.fadeOut(1f))));

        logo = new Image(atlas.findRegion("libgdx-logo"));
        loadingFrame = new Image(atlas.findRegion("loading-frame"));
        loadingBarHidden = new Image(atlas.findRegion("loading-bar-hidden"));
        screenBg = new Image(atlas.findRegion("screen-bg"));
        loadingBg = new Image(atlas.findRegion("loading-frame-bg"));
        Animation anim = new Animation(0.05f, atlas.findRegions("loading-bar-anim"));
        anim.setPlayMode(Animation.PlayMode.LOOP);
        loadingBar = new LoadingBar(anim);

        uiStage.addActor(screenBg);
        uiStage.addActor(loadingBar);
        uiStage.addActor(loadingBg);
        uiStage.addActor(loadingBarHidden);
        uiStage.addActor(loadingFrame);
        uiStage.addActor(logo);
        uiStage.addActor(text);

        //Load normal assets here
        loadNormalAssets();
    }

    /**
     * Loads all game assets that don't need to be immediately loaded for the
     * loading screen. Currently everything is manually loaded but it should
     * probably be changed to load the entirety of the file structure so that
     * it would be hard/impossible for an asset to not be loaded due to not being
     * on the list.
     */
    private void loadNormalAssets() {
        PocketRogue.getAssetManager().load("uiskin.atlas", TextureAtlas.class);
        PocketRogue.getAssetManager().load("badlogic.jpg", Texture.class);
        PocketRogue.getAssetManager().setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
        PocketRogue.getAssetManager().load("map.tmx", TiledMap.class);
        PocketRogue.getAssetManager().load("tilesheet_1.png", Texture.class);
        PocketRogue.getAssetManager().setLoader(Skin.class, new SkinLoader(new InternalFileHandleResolver()));
        PocketRogue.getAssetManager().load("uiskin.json", Skin.class);
        PocketRogue.getAssetManager().load("player.png", Texture.class);
        PocketRogue.getAssetManager().load("frameBorder.png", Texture.class);
        PocketRogue.getAssetManager().load("playerIcon.png", Texture.class);
        PocketRogue.getAssetManager().load("barBackground.png", Texture.class);
        PocketRogue.getAssetManager().load("healthBar.png", Texture.class);
        PocketRogue.getAssetManager().load("manaBar.png", Texture.class);
        PocketRogue.getAssetManager().load("spritesheet_1.png", Texture.class);
        PocketRogue.getAssetManager().load("animation_sheets/player_animation.png", Texture.class);
        new ItemDefinitionLoader().loadItemDefinitions();
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
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (PocketRogue.getAssetManager().update()) {
            text.setVisible(true);
            if (Gdx.input.isTouched()) {
                pocketRogue.setScreen(new MenuScreen(pocketRogue));
            }
        }

        percent = Interpolation.linear.apply(percent, PocketRogue.getAssetManager().getProgress(), 0.2f);

        loadingBarHidden.setX(startX + endX * percent);
        loadingBg.setX(loadingBarHidden.getX() + 30);
        loadingBg.setWidth(450 - 450 * percent);
        loadingBg.invalidate();

        uiStage.act();
        uiStage.draw();
    }

    /**
     * @param width Width of the new target screen size.
     * @param height Height of the new target screen size.
     * @see com.badlogic.gdx.ApplicationListener#resize(int, int)
     */
    @Override
    public void resize(int width, int height) {
        //uiStage.getViewport().update(width, height, true);

        screenBg.setSize(width, height);

        //Place the logo in the middle of the screen and 100 px up
        logo.setX(Utils.getPosCenterX(logo.getWidth(), uiStage.getWidth()));
        logo.setY(Utils.getPosCenterY(logo.getHeight(), uiStage.getHeight(), 100));

        //Place the loading frame in the middle of the screen
        loadingFrame.setX((uiStage.getWidth() - loadingFrame.getWidth()) / 2);
        loadingFrame.setY((uiStage.getHeight() - loadingFrame.getHeight()) / 2);

        //Place the loading bar at the same spot as the frame, adjusted a few px
        loadingBar.setX(loadingFrame.getX() + 15);
        loadingBar.setY(loadingFrame.getY() + 5);

        //Place the image that will hide the bar on top of the bar, adjusted a few px
        loadingBarHidden.setX(loadingBar.getX() + 35);
        loadingBarHidden.setY(loadingBar.getY() - 3);
        //The start position and how far to move the hidden loading bar
        startX = loadingBarHidden.getX();
        endX = 440;

        //The rest of the hidden bar
        loadingBg.setSize(450, 50);
        loadingBg.setX(loadingBarHidden.getX() + 30);
        loadingBg.setY(loadingBarHidden.getY() + 3);

        //Text Label
        text.setX((uiStage.getWidth() - text.getWidth()) / 2);
        text.setY(loadingFrame.getY() - 50);
        text.setVisible(false);

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
        PocketRogue.getAssetManager().unload("loading.pack");
    }

    /**
     * Called when this screen should release all resources.
     */
    @Override
    public void dispose() {
        uiStage.dispose();
    }

    public class LoadingBar extends Actor {

        Animation animation;
        TextureRegion reg;
        float stateTime = 0;

        public LoadingBar(Animation animation) {
            this.animation = animation;
            reg = animation.getKeyFrame(0);
        }

        @Override
        public void act(float delta) {
            stateTime += delta;
            reg = animation.getKeyFrame(stateTime);
        }

        @Override
        public void draw(Batch batch, float parentAlpha) {
            batch.draw(reg, getX(), getY());
        }
    }
}
