package xyz.vec3d.game;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import xyz.vec3d.game.entities.Enemy;
import xyz.vec3d.game.entities.Player;
import xyz.vec3d.game.entities.PocketRogueEntity;
import xyz.vec3d.game.entities.WorldItem;
import xyz.vec3d.game.entities.listeners.EntityTextureListener;
import xyz.vec3d.game.gui.GuiDebug;
import xyz.vec3d.game.gui.OSTouchpad;
import xyz.vec3d.game.gui.PlayerInfoDisplay;
import xyz.vec3d.game.gui.console.Console;
import xyz.vec3d.game.gui.console.LogMessage;
import xyz.vec3d.game.messages.Message;
import xyz.vec3d.game.messages.RogueInputProcessor;
import xyz.vec3d.game.model.Item;
import xyz.vec3d.game.model.Item.ItemType;
import xyz.vec3d.game.model.DefinitionLoader;
import xyz.vec3d.game.model.DefinitionLoader.Definition;
import xyz.vec3d.game.model.DefinitionProperty;
import xyz.vec3d.game.model.ItemStack;
import xyz.vec3d.game.model.combat.CombatSystem;
import xyz.vec3d.game.systems.CollisionSystem;
import xyz.vec3d.game.systems.MovementSystem;
import xyz.vec3d.game.systems.RenderingSystem;
import xyz.vec3d.game.systems.UpdateSystem;
import xyz.vec3d.game.utils.Utils;

/**
 * Created by Daron on 7/5/2016.
 * Copyright vec3d.xyz 2016
 * All rights reserved
 *
 * Game state representation. Has a stage for UI and an Ashley engine for entity
 * related matters. Also manages the messaging system between the UI and engine.
 */
public class GameScreen extends PocketRogueScreen {

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
    private ShapeRenderer shapeRenderer;

    /**
     * The {@link Skin} used for UI stuff.
     */
    private Skin skin;

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
     * The in-game {@link Console console} that will process commands.
     */
    private Console console;

    /**
     * Debug overlay containing various tidbits of information about the state
     * of the game.
     */
    private GuiDebug debugOverlay;
    private boolean renderDebugOverlay = false;
    public static boolean IS_DEBUG = false;

    /**
     * The {@link CombatSystem combat system} used for the game screen.
     */
    private CombatSystem combatSystem;

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
        this.shapeRenderer = new ShapeRenderer();
        this.shapeRenderer.setAutoShapeType(true);
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
        skin = (Skin) PocketRogue.getAsset("uiskin.json");

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
        console = new Console("Pocket Rogue Console", skin);
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
        TiledMap map = PocketRogue.getAsset("map.tmx");
        Settings.MAX_WORLD_WIDTH = map.getProperties().get("width", Integer.class);
        Settings.MAX_WORLD_HEIGHT = map.getProperties().get("height", Integer.class);
        tiledMapRenderer = new OrthogonalTiledMapRenderer(map, Settings.WORLD_SCALE);
        worldCamera = new OrthographicCamera();
        worldCamera.setToOrtho(false, 25, 14);
        worldCamera.update();

        //Set up map and camera viewport properties.
        mapWidth = map.getProperties().get("width", Integer.class);
        mapHeight = map.getProperties().get("height", Integer.class);

        //Create engine instance, attach listeners and systems.
        engine = new Engine();
        UpdateSystem updateSystem = new UpdateSystem();
        RenderingSystem renderingSystem = new RenderingSystem(spriteBatch, shapeRenderer);
        MovementSystem movementSystem = new MovementSystem();
        engine.addSystem(updateSystem);
        engine.addSystem(new CollisionSystem());
        engine.addSystem(movementSystem);
        engine.addSystem(renderingSystem);
        engine.addEntityListener(new EntityTextureListener());
        player = new Player(10, 10);
        engine.addEntity(player);
        notifyMessageReceivers(new Message(Message.MessageType.PLAYER_INFO_MAX_CHANGED, 100, 100));
        this.combatSystem = new CombatSystem(engine, player);
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

    public CombatSystem getCombatSystem() {
        return combatSystem;
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

        Utils.centerCamera(worldCamera, player, mapWidth, mapHeight);

        tiledMapRenderer.setView(worldCamera);
        tiledMapRenderer.render();

        spriteBatch.setProjectionMatrix(worldCamera.combined);
        shapeRenderer.setProjectionMatrix(worldCamera.combined);
        spriteBatch.begin();
        shapeRenderer.begin();
        rogueInputProcessor.update();
        engine.update(delta);
        combatSystem.update(delta);
        spriteBatch.end();
        shapeRenderer.end();

        uiStage.act(delta);
        uiStage.draw();

        if (getGuiOverlay() != null) {
            getGuiOverlay().draw();
        }

        console.draw();
        if (renderDebugOverlay) {
            debugOverlay.draw();
        }
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
        resizeOverlay(width, height);
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
                switch (uiName.toLowerCase()) {
                    case "player_info_display":
                        openGui("player_inventory", player.getInventory(), skin);
                        break;
                }
                break;
            case KEY_TYPED:
                int keycode = (Integer) message.getPayload()[0];
                if (keycode == Input.Keys.GRAVE) {
                    console.toggle();
                } else if (keycode == Input.Keys.ESCAPE) {
                    if (getGuiOverlay() != null) {
                        closeGuiOverlay();
                    }
                }
                break;
            case ENTITY_SPAWNED:
                PocketRogueEntity entitySpawned = (PocketRogueEntity) message.getPayload()[0];
                engine.addEntity(entitySpawned);
                break;
            case COMMAND:
                String[] tokens = (String[]) message.getPayload();
                String command = tokens[0];
                String[] args = new String[tokens.length - 1];
                System.arraycopy(tokens, 1, args, 0, tokens.length - 1);
                switch (command.toLowerCase()) {
                    case "gui":
                        if (console.checkNumArgs(args, 1)) {
                            String guiName = args[0];
                            console.toggle();
                            openGui(guiName, player.getInventory(), skin);
                        }
                        break;
                    case "additem":
                        if (console.checkNumArgs(args, 1)) {
                            int itemId = Integer.valueOf(args[0]);
                            int amount = args.length > 1 ? Integer.valueOf(args[1]) : 1;
                            Definition def = DefinitionLoader.getItemDefinition(itemId);
                            if (def == null) {
                                break;
                            }
                            String slot = (String) def.getProperty(DefinitionProperty.SLOT);
                            String name = (String) def.getProperty(DefinitionProperty.NAME);
                            ItemType type = ItemType.valueOf(slot);
                            Item item = new Item(itemId, type);
                            ItemStack stack = new ItemStack(item, amount);
                            player.getInventory().addItem(stack);
                            Message inventoryChangedMessage = new Message(Message.
                                    MessageType.PLAYER_INVENTORY_CHANGED,
                                    player.getInventory());
                            notifyMessageReceivers(inventoryChangedMessage);
                            console.log("Added item: " + name);
                        }
                        break;
                    case "entity":
                        if (console.checkNumArgs(args, 1)) {
                            int entityId = Integer.valueOf(args[0]);
                            if (DefinitionLoader.getEntityDefinition(entityId) == null) {
                                console.log("No definition exists for this entity ID!", LogMessage.LogLevel.ERROR );
                                return;
                            }
                            float x = player.getPosition().x;
                            float y = player.getPosition().y;
                            if (args.length == 3) {
                                x = Float.valueOf(args[1]);
                                y = Float.valueOf(args[2]);
                            }
                            Enemy enemy = new Enemy(entityId, x, y);
                            engine.addEntity(enemy);
                        }
                        break;
                    case "kill":
                        for (Entity entity : engine.getEntities()) {
                            if (entity instanceof Enemy) {
                                ((Enemy) entity).kill();
                            }
                        }
                        break;
                    case "dropitem":
                        if (console.checkNumArgs(args, 1)) {
                            int itemId = Integer.valueOf(args[0]);
                            Definition def = DefinitionLoader.getItemDefinition(itemId);
                            String slot = (String) def.getProperty(DefinitionProperty.SLOT);
                            ItemStack stack = new ItemStack(new Item(itemId, ItemType.valueOf(slot)), 1);
                            WorldItem worldItem = new WorldItem(stack,
                                    player.getPosition().x + 1, player.getPosition().y + 1);
                            engine.addEntity(worldItem);
                        }
                        break;
                    case "debug":
                        if (debugOverlay == null) {
                            this.debugOverlay = new GuiDebug(this.engine, this.player);
                            this.debugOverlay.setParameters(new Object[] {skin});
                            this.debugOverlay.setup();
                        }
                        renderDebugOverlay = !renderDebugOverlay;
                        IS_DEBUG = !IS_DEBUG;
                        break;
                    default:
                        console.log("Command: " + command + " not implemented yet.", LogMessage.LogLevel.WARNING);
                        break;
                }
                break;
        }
    }

}
