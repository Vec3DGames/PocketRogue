package xyz.vec3d.game.messages;

/**
 * Created by darakelian on 7/14/2016.
 * Copyright vec3d.xyz 2016
 * All rights reserved
 *
 * A message is a way of communicating between classes without them having
 * instances of each other. Messages are sent by message senders and processed
 * by message receivers. Messages contain a type and a payload. The type is a
 * predefined enum that is the name of the message and the payload is an object
 * array containing the information being sent.
 */
public class Message {

    /**
     * The {@link MessageType} of the message.
     */
    private MessageType messageType;

    /**
     * The payload being sent with the message.
     */
    private Object[] payload;

    /**
     * Constructs a new message with a type and payload.
     *
     * @param type The type of the message.
     * @param payload The information being sent with the mesage.
     */
    public Message(MessageType type, Object... payload) {
        this.messageType = type;
        this.payload = payload;
    }

    /**
     * Retrieves the {@link MessageType} of the message.
     *
     * @return The type (name) of the message.
     */
    public MessageType getMessageType() {
        return messageType;
    }

    /**
     * Retrieves the payload ot the message.
     *
     * @return The information contained in the message.
     */
    public Object[] getPayload() {
        return payload;
    }

    public void setPayload(Object[] payload) {
        this.payload = payload;
    }

    /**
     * Enum constants representing types of movements. When a new message type
     * is desired, it must be added to this enum for message senders/receivers
     * to recognize the type.
     */
    public enum MessageType {
        PLAYER_MOVEMENT, PLAYER_INFO_HEALTH_CHANGED, PLAYER_INFO_MANA_CHANGED,
        PLAYER_INFO_MAX_CHANGED, UI_ELEMENT_CLICKED, KEY_TYPED, COMMAND,
        PLAYER_INVENTORY_CHANGED, ENTITY_SPAWNED
    }
}
