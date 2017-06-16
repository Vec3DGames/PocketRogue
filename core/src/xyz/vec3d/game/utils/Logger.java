package xyz.vec3d.game.utils;

/**
 * Created by Daron on 8/20/2016.
 *
 * Wrapper methods for various logging features.
 */
public class Logger {

    /**
     * Logs a message defaulting the class to Logger.
     *
     * @param message The message being logged.
     */
    public static void log(String message) {
        log(Logger.class, message);
    }

    /**
     * Logs a message coming from the specified class.
     *
     * @param c The class that the message is coming from.
     * @param message The message being logged.
     */
    public static void log(Class c, String message) {
        log(c, LogLevel.NORMAL, message);
    }

    /**
     * Logs a message coming from the specified class with the specified log level.
     *  @param c The class that the message is coming from.
     * @param level The level the message is being logged at.
     * @param message The message being logged.
     */
    public static void log(Class c, LogLevel level, String message) {
        String className = c.getSimpleName();
        String logMessage = String.format("[%s][%s] %s", className, level.toString(), message);

        System.out.println(logMessage);
    }

    public enum LogLevel {
        NORMAL, WARNING, ERROR
    }
}
