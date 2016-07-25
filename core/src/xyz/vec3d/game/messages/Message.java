package xyz.vec3d.game.messages;

/**
 * Created by darakelian on 7/14/2016.
 * Copyright vec3d.xyz 2016
 * All rights reserved
 */
public class Message {

    private MessageType messageType;
    private Object[] payload;

    public Message(MessageType type, Object... payload) {
        this.messageType = type;
        this.payload = payload;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public Object[] getPayload() {
        return payload;
    }

    public enum MessageType {
        PLAYER_MOVEMENT, PLAYER_INFO_HEALTH_CHANGED, PLAYER_INFO_MANA_CHANGED
    }
}
