package xyz.vec3d.game;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;

import java.util.ArrayList;

import xyz.vec3d.game.gui.Gui;
import xyz.vec3d.game.messages.Message;
import xyz.vec3d.game.messages.MessageReceiver;
import xyz.vec3d.game.messages.MessageSender;

/**
 * Created by Daron on 8/16/2016.
 *
 * Provides a set of common methods for all of the game screens.
 */
public class PocketRogueScreen implements Screen, MessageReceiver, MessageSender {

    /**
     * A non-hud GUI. The hud GUI is the collection of actors created at game start
     * where as a GUI overlay is a separate {@link Stage} that draws it's own
     * actors and other UI elements. A GUI overlay is different than custom actors
     * in that the GUI can have these custom actors but the actors must be placed
     * in a GUI in order to be used.
     */
    private Gui guiOverlay;

    /**
     * List of {@link MessageReceiver receivers} that get notified of messages
     * from this screen.
     */
    private ArrayList<MessageReceiver> messageReceivers = new ArrayList<>();

    public Gui getGuiOverlay() {
        return guiOverlay;
    }

    public void openGui(String guiName, Object... parameters) {
        Gui gui = Gui.openGui(guiName);
        if (gui == null) {
            return;
        }
        gui.setParameters(parameters);
        gui.setup();
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
    public void registerMessageReceiver(MessageReceiver messageReceiver) {
        messageReceivers.add(messageReceiver);
    }

    @Override
    public void deregisterMessageReceiver(MessageReceiver messageReceiver) {
        messageReceivers.remove(messageReceiver);
    }

    @Override
    public void notifyMessageReceivers(Message message) {
        for (MessageReceiver messageReceiver : messageReceivers) {
            messageReceiver.onMessageReceived(message);
        }
    }
}
