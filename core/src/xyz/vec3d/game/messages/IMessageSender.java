package xyz.vec3d.game.messages;

/**
 * Created by darakelian on 7/14/2016.
 * Copyright vec3d.xyz 2016
 * All rights reserved
 */
public interface IMessageSender {

    /**
     * Registers a {@link IMessageReceiver} to be notified of messages from this
     * sender.
     *
     * @param messageReceiver The MessageReceiver getting messages.
     */
    void registerMessageReceiver(IMessageReceiver messageReceiver);

    /**
     * De-registers a {@link IMessageReceiver} so that it is no longer notified of
     * messages from this sender.
     *
     * @param messageReceiver The MessageReceiver being de-registered.
     */
    void deregisterMessageReceiver(IMessageReceiver messageReceiver);

    void notifyMessageReceivers(Message message);

}
