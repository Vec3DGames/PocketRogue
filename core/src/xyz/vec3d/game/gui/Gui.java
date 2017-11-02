package xyz.vec3d.game.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import java.util.ArrayList;
import java.util.HashMap;

import xyz.vec3d.game.PocketRogueScreen;
import xyz.vec3d.game.Settings;
import xyz.vec3d.game.messages.IMessageReceiver;
import xyz.vec3d.game.messages.IMessageSender;
import xyz.vec3d.game.messages.Message;
import xyz.vec3d.game.utils.Logger;
import xyz.vec3d.game.utils.Utils;

/**
 * Created by Daron on 8/16/2016.
 *
 * Represents a stage with a number of different actors making up a UI screen.
 */
public abstract class Gui implements Disposable, IMessageSender, IMessageReceiver {

    private Stage stage;
    private Object[] parameters = new Object[1];

    private ArrayList<IMessageReceiver> messageReceivers;

    private float x, y, width, height;

    /**
     * List of all the GUI's registerd
     */
    private static final HashMap<Object, Class<Gui>> handledGuis = new HashMap<>();
    PocketRogueScreen parentScreen;

    Gui() {
        stage = new Stage(new StretchViewport(Settings.UI_WIDTH, Settings.UI_HEIGHT));
        messageReceivers = new ArrayList<>();
        InputMultiplexer im = (InputMultiplexer) Gdx.input.getInputProcessor();
        im.addProcessor(0, stage);
        Gdx.input.setInputProcessor(im);
        stage.addCaptureListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                guiClicked(x, y);
            }
        });
    }

    public abstract void setup();

    public void draw() {
        if (stage == null) {
            return;
        }
        stage.act();
        stage.draw();
    }

    public Stage getStage() {
        return stage;
    }

    Object[] getParameters() {
        return parameters;
    }

    public void setParameters(Object[] parameters) {
        this.parameters = parameters;
    }

    public static Gui openGui(String guiName) {
        Class<Gui> classD = handledGuis.get(guiName);
        if (classD == null) {
            Logger.log(Gui.class, "Unable to find GUI.");
            return null;
        }
        try {
            return classD.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void dispose() {
        InputMultiplexer im = (InputMultiplexer) Gdx.input.getInputProcessor();
        im.removeProcessor(stage);
        stage.dispose();
        Gdx.input.setInputProcessor(im);
    }

    public void resize(int width, int height) {
        Logger.log("Resizing GUI to new dimensions: " + width + ", " + height);
        getStage().getViewport().update(width, height);
    }

    @Override
    public void notifyMessageReceivers(Message message) {
        for (IMessageReceiver messageReceiver : messageReceivers) {
            messageReceiver.onMessageReceived(message);
        }
    }

    @Override
    public void registerMessageReceiver(IMessageReceiver messageReceiver) {
        messageReceivers.add(messageReceiver);
    }

    @Override
    public void deregisterMessageReceiver(IMessageReceiver messageReceiver) {
        messageReceivers.remove(messageReceiver);
    }

    static {
        try {
            handledGuis.put("player_inventory",
                    (Class<Gui>) Class.forName(GuiInventory.class.getCanonicalName()));

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void setParentScreen(PocketRogueScreen parentScreen) {
        this.parentScreen = parentScreen;
    }

    void setHitbox(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    private void guiClicked(float x, float y) {
        if (!Utils.isInHitbox(x, y, this.x, this.y, width, height)) {
            this.dispose();
        }
    }
}
