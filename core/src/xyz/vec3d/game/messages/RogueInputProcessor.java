package xyz.vec3d.game.messages;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import java.util.ArrayList;
import java.util.Locale;

import xyz.vec3d.game.GameScreen;
import xyz.vec3d.game.entities.Projectile;
import xyz.vec3d.game.utils.Logger;
import xyz.vec3d.game.messages.Message.MessageType;

/**
 * Created by Daron on 7/6/2016.
 * Edited By Bobby
 * Copyright vec3d.xyz 2016
 * All rights reserved
 *
 * Handles all input from the player and will create messages to entities or
 * entity systems based on the input provided. There should only ever be one
 * instance of this class used in an {@link com.badlogic.gdx.InputMultiplexer}.
 */
public class RogueInputProcessor extends ChangeListener implements InputProcessor, IMessageSender  {

    private GameScreen gameScreen;
    private ArrayList<IMessageReceiver> messageReceivers = new ArrayList<>();

    /**
     * Method called when the object registered with it is changed.
     * @param event The specific change event. Unused in this case.
     * @param actor The actor the change happened on. Needs to be cast as the touchpad.
     */
    @Override
    public void changed(ChangeListener.ChangeEvent event, Actor actor) {
        if( actor.getClass() == Touchpad.class) {
            mov.set(((Touchpad) actor).getKnobPercentX(), ((Touchpad) actor).getKnobPercentY());
        }
        if (actor.getClass() == TextButton.class){
            if (actor.getName().equals("D")){
                //TODO: DODGING
                return;
            }
            if (actor.getName().equals("A")){
                //TODO: ATTACKING
                return;
            }
        }
    }

    /**
     * The {@link Vector2} that stores the movement updating vector. This gets
     * normalized then scaled by a movement factor in order to calculate the
     * player's velocity.
     */
    private Vector2 mov = new Vector2(0, 0);

    /**
     * Creates a new {@link RogueInputProcessor} instance with a given {@link GameScreen}
     * instance to communicate with the screen if needed.
     *
     * @param gameScreen the gamescreen nigga
     */
    public RogueInputProcessor(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
    }

    /**
     * Processes the movement input vector before sending it in a message to the
     * {@link xyz.vec3d.game.systems.MovementSystem}.
     * @return nvm am idiot
     */
    private Vector2 getMov() {
        return mov.cpy().nor();
    }

    public void update() {
        Vector2 velocity = getMov();
        //gameScreen.getPlayer().setAnimationFromVelocity(velocity);
        gameScreen.getPlayer().setVelocity(velocity);
    }

    @Override
    public boolean keyDown(int keycode) {
        switch(keycode) {
            case Keys.W:
                mov.add(0, 1);
                return true;
            case Keys.S:
                mov.add(0, -1);
                return true;
            case Keys.A:
                mov.add(-1, 0);
                return true;
            case Keys.D:
                mov.add(1, 0);
                return true;
            //Fire projectile code here.
            case Keys.SPACE:
                Projectile projectile = gameScreen.getPlayer().getFiringSystem().fireProjectile();
                if (projectile != null) {
                    notifyMessageReceivers(new Message(MessageType.ENTITY_SPAWNED, projectile));
                }
                return true;
            default:
                notifyMessageReceivers(new Message(MessageType.KEY_TYPED, keycode));
                return true;
        }
    }

    @Override
    public boolean keyUp(int keycode) {
        switch(keycode) {
            case Keys.W:
                mov.add(0, -1);
                return true;
            case Keys.S:
                mov.add(0, 1);
                return true;
            case Keys.A:
                mov.add(1, 0);
                return true;
            case Keys.D:
                mov.add(-1, 0);
                return true;
        }
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    /**
     * Do click stuff here.
     *
     * @param screenX X coordinate in untranslated screen coordinates.
     * @param screenY Y coordinate in untranslated screen coordinates.
     * @param pointer The pointer used for the event.
     * @param button The mouse button clicked (left, right, middle etc).
     *
     * @return True if the event was handled.
     */
    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (gameScreen.getGuiOverlay() == null) {
            //Do combat action here
            gameScreen.getCombatSystem().doPlayerAttack();
            return true;
        }
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

    @Override
    public void registerMessageReceiver(IMessageReceiver messageReceiver) {
        messageReceivers.add(messageReceiver);
        Logger.log("Registered receiver: " + messageReceiver + " id: " +
                messageReceivers.size(), this.getClass());
    }

    @Override
    public void deregisterMessageReceiver(IMessageReceiver messageReceiver) {
        messageReceivers.remove(messageReceiver);
    }

    @Override
    public void notifyMessageReceivers(Message message) {
       for (int i = 0; i < messageReceivers.size(); i++) {
           messageReceivers.get(i).onMessageReceived(message);
           Logger.log(String.format(Locale.US, "Sent message %s to receiver %d",
                   message.getMessageType(), i), this.getClass());
       }
    }
}
