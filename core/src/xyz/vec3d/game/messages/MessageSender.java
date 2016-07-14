package xyz.vec3d.game.messages;

import java.util.ArrayList;

/**
 * Created by darakelian on 7/14/2016.
 * Copyright vec3d.xyz 2016
 * All rights reserved
 */
public interface MessageSender {

    ArrayList<MessageReceiver> messageReceivers = new ArrayList<MessageReceiver>();

    /**
     * Registers a {@link MessageReceiver} to be notified of messages from this
     * sender.
     *
     * @param messageReceiver The MessageReceiver getting messages.
     */
    void registerMessageReceiver(MessageReceiver messageReceiver);

    /**
     * De-registers a {@link MessageReceiver} so that it is no longer notified of
     * messages from this sender.
     *
     * @param messageReceiver The MessageReceiver being de-registered.
     */
    void deregisterMessageReceiver(MessageReceiver messageReceiver);

    void notifyMessageReceivers(Message message);
}
