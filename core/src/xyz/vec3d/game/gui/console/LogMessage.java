package xyz.vec3d.game.gui.console;

import com.badlogic.gdx.graphics.Color;

/**
 * Created by Daron on 8/16/2016.
 *
 * Represents a console log message.
 */
public class LogMessage {

    private String message;

    private LogLevel level;

    public LogMessage(String message, LogLevel level) {
        this.message = message;
        this.level = level;
    }

    public String getMessage() {
        return message;
    }

    public LogLevel getLevel() {
        return level;
    }

    public Color getColor() {
        switch (getLevel()) {
            case WARNING:
                return Color.YELLOW;
            case ERROR:
                return Color.RED;
            default:
                return Color.WHITE;
        }
    }

    public enum LogLevel {
        NORMAL, WARNING, ERROR;
    }
}
