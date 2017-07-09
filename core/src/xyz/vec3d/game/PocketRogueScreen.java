package xyz.vec3d.game;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;

import java.util.ArrayList;

import xyz.vec3d.game.gui.Gui;
import xyz.vec3d.game.messages.IMessageReceiver;
import xyz.vec3d.game.messages.IMessageSender;
import xyz.vec3d.game.messages.Message;

/**
 * Created by Daron on 8/16/2016.
 *
 * Provides a set of common methods for all of the game screens.
 */
public class PocketRogueScreen implements Screen, IMessageReceiver, IMessageSender {

    /**
     * A non-hud GUI. The hud GUI is the collection of actors created at game start
     * where as a GUI overlay is a separate {@link Stage} that draws it's own
     * actors and other UI elements. A GUI overlay is different than custom actors
     * in that the GUI can have these custom actors but the actors must be placed
     * in a GUI in order to be used.
     */
    private Gui guiOverlay;

    /**
     * List of {@link IMessageReceiver receivers} that get notified of messages
     * from this screen.
     */
    private ArrayList<IMessageReceiver> messageReceivers = new ArrayList<>();

    public Gui getGuiOverlay() {
        return guiOverlay;
    }

    /**
     * Closes the GUI overlay if one exists by setting the variable to null and
     * calls dispose on it as well as decouples the message receiver/sender
     * relationship.
     */
    public void closeGuiOverlay() {
        if (this.guiOverlay == null) {
            return;
        }
        this.guiOverlay.dispose();
        this.guiOverlay.deregisterMessageReceiver(this);
        this.deregisterMessageReceiver(this.guiOverlay);
        this.guiOverlay = null;
    }

    public void resizeOverlay(int width, int height) {
        if (this.guiOverlay == null) {
            return;
        }
        this.guiOverlay.resize(width, height);
    }

    public void openGui(String guiName, Object... parameters) {
        Gui gui = Gui.openGui(guiName);
        if (gui == null) {
            return;
        }
        gui.setParameters(parameters);
        gui.setParentScreen(this);
        gui.setup();
        //Set up message receiver/sender relationship between the GUI.
        gui.registerMessageReceiver(this);
        this.registerMessageReceiver(gui);
        this.guiOverlay = gui;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

    @Override
    public void onMessageReceived(Message message) {

    }

    @Override
    public void registerMessageReceiver(IMessageReceiver messageReceiver) {
        messageReceivers.add(messageReceiver);
    }

    @Override
    public void deregisterMessageReceiver(IMessageReceiver messageReceiver) {
        messageReceivers.remove(messageReceiver);
    }

    @Override
    public void notifyMessageReceivers(Message message) {
        for (IMessageReceiver messageReceiver : messageReceivers) {
            messageReceiver.onMessageReceived(message);
        }
    }
}
