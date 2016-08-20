package xyz.vec3d.game.utils;

/**
 * Created by Daron on 8/20/2016.
 *
 * Wrapper methods for various logging features.
 */
public class Logger {

    public static void log(String message, Class c) {
        log(message, c, LogLevel.NORMAL);
    }

    public static void log(String message, Class c, LogLevel level) {
        String className = c.getCanonicalName();
        switch (level) {
            case NORMAL:
                break;
            case WARNING:
                break;
            case ERROR:
                break;
        }
    }

    public enum LogLevel {
        NORMAL, WARNING, ERROR
    }
}
