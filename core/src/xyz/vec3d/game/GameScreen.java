package xyz.vec3d.game;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import java.util.ArrayList;

import xyz.vec3d.game.entities.Player;
import xyz.vec3d.game.entities.listeners.EntityTextureListener;
import xyz.vec3d.game.gui.OSTouchpad;
import xyz.vec3d.game.gui.PlayerInfoDisplay;
import xyz.vec3d.game.gui.console.Console;
import xyz.vec3d.game.messages.Message;
import xyz.vec3d.game.messages.MessageReceiver;
import xyz.vec3d.game.messages.MessageSender;
import xyz.vec3d.game.messages.RogueInputProcessor;
import xyz.vec3d.game.systems.MovementSystem;
import xyz.vec3d.game.systems.RenderingSystem;
import xyz.vec3d.game.utils.Utils;

/**
 * Created by Daron on 7/5/2016.
 * Copyright vec3d.xyz 2016
 * All rights reserved
 *
 * Game state representation. Has a stage for UI and an Ashley engine for entity
 * related matters. Also manages the messaging system between the UI and engine.
 */
public class GameScreen implements Screen, MessageReceiver, MessageSender {

    /**
     * {@link PocketRogue} instance.
     */
    private PocketRogue pocketRogue;

    /**
     * The Ashley {@link com.badlogic.ashley.core.Engine} instance.
     */
    private Engine engine;

    /**
     * The {@link Stage} instance for UI.
     */
    private Stage uiStage;

    /**
     * The {@link TiledMapRenderer} responsible for drawing the world's map.
     */
    private TiledMapRenderer tiledMapRenderer;

    /**
     * The {@link OrthographicCamera} responsible for looking into the world map.
     */
    private OrthographicCamera worldCamera;

    /**
     * The {@link Player} of the game loaded for the game state. This is an
     * individual variable field so it can be readily accessed rather than having
     * to find it each time from the array of entities in the engine.
     */
    private Player player;

    /**
     * The {@link SpriteBatch} used to draw the entities. Each update tick the
     * projection matrix is set to that of the camera so that it is properly
     * drawing entities based on world units. This is passed to the
     * {@link RenderingSystem} to be used for drawing.
     */
    private SpriteBatch spriteBatch;

    /**
     * InputProcessor that handles key/mouse input.
     */
    private RogueInputProcessor rogueInputProcessor;

    /**
     * Width of the map in world units.
     */
    private float mapWidth;

    /**
     * Height of the map in world units.
     */
    private float mapHeight;

    /**
     * Half of the width of the camera viewport in world units.
     */
    private float camViewportHalfX;

    /**
     * Half of the height of the camera viewport in world units.
     */
    private float camViewportHalfY;

    /**
     * List of {@link MessageReceiver receivers} that get notified of messages
     * from this screen.
     */
    private ArrayList<MessageReceiver> messageReceivers = new ArrayList<MessageReceiver>();

    /**
     * The in-game {@link Console console} that will process commands.
     */
    private Console console;

    /**
     * Creates a new {@link GameScreen} object and sets up the stage, engine and
     * any other initialization needed.
     *
     * @param pocketRogue The {@link PocketRogue} instance.
     */
    public GameScreen(PocketRogue pocketRogue) {
        this.pocketRogue = pocketRogue;
        this.engine = new Engine();
        this.uiStage = new Stage();
        this.spriteBatch = new SpriteBatch();
        setUpGui();
        setUpEngine();
    }

    /**
     * Initializes all the UI components and registers listeners or handlers
     * for the components. Sets up UI components common to both desktop and
     * android then handles setting up the UI specific to each version.
     */
    private void setUpGui() {
        //Create the stage and viewport for the UI.
        uiStage = new Stage(new StretchViewport(Settings.UI_WIDTH, Settings.UI_HEIGHT));

        //Set up input multiplexer.
        rogueInputProcessor = new RogueInputProcessor(this);
        InputMultiplexer im = new InputMultiplexer(uiStage, rogueInputProcessor);
        Gdx.input.setInputProcessor(im);
        rogueInputProcessor.registerMessageReceiver(this);

        //Set up the player info display.
        PlayerInfoDisplay infoDisplay = new PlayerInfoDisplay();
        infoDisplay.setPosition(20, uiStage.getHeight() - 80);
        infoDisplay.registerMessageReceiver(this);
        this.registerMessageReceiver(infoDisplay);
        rogueInputProcessor.registerMessageReceiver(infoDisplay);
        uiStage.addActor(infoDisplay);

        //Set up console.
        console = new Console("Pocket Rogue Console", (Skin) PocketRogue.getAssetManager().get("uiskin.json"));
        console.getDisplay().registerMessageReceiver(this);

        switch (Gdx.app.getType()) {
            case Android:
                setUpAndroidUi();
                break;
            case Desktop:
                setUpDesktopUi();
                break;
        }
    }

    private void setUpAndroidUi() {
        OSTouchpad touchpad = new OSTouchpad();
        uiStage.addActor(touchpad.getTouchpad());
        touchpad.registerWith(rogueInputProcessor);

        //TODO: Find PNGs for buttons. Until then comment out.
        //OSButton dodge = new OSButton("D");
        //uiStage.addActor(dodge.getButton());
        //dodge.registerWith(rogueInputProcessor);
    }

    private void setUpDesktopUi() {

    }

    /**
     * Initializes the engine and registers systems for the engine as well as
     * loads the map and camera.
     */
    private void setUpEngine() {
        //Create camera and load map and bind them together.
        TiledMap map = PocketRogue.getAssetManager().get("map.tmx", TiledMap.class);
        tiledMapRenderer = new OrthogonalTiledMapRenderer(map, Settings.WORLD_SCALE);
        worldCamera = new OrthographicCamera();
        worldCamera.setToOrtho(false, 25, 14);
        worldCamera.update();

        //Set up map and camera viewport properties.
        mapWidth = map.getProperties().get("width", Integer.class);
        mapHeight = map.getProperties().get("height", Integer.class);
        camViewportHalfX = worldCamera.viewportWidth / 2;
        camViewportHalfY = worldCamera.viewportHeight / 2;

        //Create engine instance, attach listeners and systems.
        engine = new Engine();
        RenderingSystem renderingSystem = new RenderingSystem(spriteBatch);
        MovementSystem movementSystem = new MovementSystem();
        engine.addSystem(renderingSystem);
        engine.addSystem(movementSystem);
        engine.addEntityListener(new EntityTextureListener());
        player = new Player(10, 10);
        engine.addEntity(player);
        notifyMessageReceivers(new Message(Message.MessageType.PLAYER_INFO_MAX_CHANGED, 100, 100));
    }

    /**
     * Returns the instance of the {@link PocketRogue} that was passed to this
     * screen when it was created.
     *
     * @return The PocketRogue class.
     */
    public PocketRogue getPocketRogue() {
        return pocketRogue;
    }

    /**
     * Returns the instance of the {@link Player} that was added to the engine.
     * This is useful because rather than looking through the engine every time
     * we need the player, it is its own field.
     *
     * @return The player.
     */
    public Player getPlayer() {
        return player;
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
     * Renders the TiledMap, updates the UI stage, draws the UI stage then finally
     * updates the engine.
     *
     * @param delta The time in seconds since the last render.
     */
    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        worldCamera.update();

        //Move camera.
        worldCamera.position.x = player.getPosition().x;
        worldCamera.position.y = player.getPosition().y;
        //Clamp camera first on x, then on y.
        worldCamera.position.x = MathUtils.clamp(worldCamera.position.x,
                camViewportHalfX, mapWidth - camViewportHalfX);
        worldCamera.position.y = MathUtils.clamp(worldCamera.position.y,
                camViewportHalfY, mapHeight - camViewportHalfY);

        tiledMapRenderer.setView(worldCamera);
        tiledMapRenderer.render();

        spriteBatch.setProjectionMatrix(worldCamera.combined);
        spriteBatch.begin();
        rogueInputProcessor.update();
        engine.update(delta);
        spriteBatch.end();

        uiStage.act(delta);
        uiStage.draw();
        console.draw();
    }

    /**
     * Resize the UI stage only so that the UI remains consistent. This does not
     * touch the world camera.
     *
     * @param width The new width of the screen.
     * @param height The new height of the screen.
     * @see com.badlogic.gdx.ApplicationListener#resize(int, int)
     */
    @Override
    public void resize(int width, int height) {
        uiStage.getViewport().update(width, height);
        console.resize(width, height);
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

    }

    @Override
    public void onMessageReceived(Message message) {
        switch (message.getMessageType()) {
            case UI_ELEMENT_CLICKED:
                //Open player menu here.
                String uiName = (String) message.getPayload()[0];
                System.out.println(uiName);
                break;
            case KEY_TYPED:
                int keycode = (Integer) message.getPayload()[0];
                if (keycode == Input.Keys.GRAVE) {
                    console.toggle();
                    //RenderingSystem rs = engine.getSystem(RenderingSystem.class);
                }
                System.out.println("Key code: " + keycode + "|" + Input.Keys.toString(keycode) + " received.");
                break;
            case COMMAND:
                String[] tokens = (String[]) message.getPayload();
                String command = tokens[0];
                switch (command.toLowerCase()) {
                    default:
                        System.out.println("Command: " + command + " not implemented yet.");
                        break;
                }
                break;
        }
    }

    @Override
    public void registerMessageReceiver(MessageReceiver messageReceiver) {
        messageReceivers.add(messageReceiver);
    }

    @Override
    public void deregisterMessageReceiver(MessageReceiver messageReceiver) {

    }

    @Override
    public void notifyMessageReceivers(Message message) {
        for (MessageReceiver messageReceiver : messageReceivers) {
            messageReceiver.onMessageReceived(message);
        }
    }
}
