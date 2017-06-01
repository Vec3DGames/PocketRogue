package xyz.vec3d.game.utils;

/**
 * Created by Daron on 8/20/2016.
 *
 * Wrapper methods for various logging features.
 */
public class Logger {

    public static void log(String message) {
        log(message, Logger.class);
    }
    public static void log(String message, Class c) {
        log(message, c, LogLevel.NORMAL);
    }

    public static void log(String message, Class c, LogLevel level) {
        String className = c.getSimpleName();
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("[%s]", className));
        switch (level) {
            case NORMAL:
                sb.append(String.format("[%s] ", "Message"));
                sb.append(message);
                System.out.println(sb.toString());
                break;
            case WARNING:
                sb.append(String.format("[%s] ", "Warning"));
                sb.append(message);
                System.out.println(sb.toString());
                break;
            case ERROR:
                sb.append(String.format("[%s] ", "Error"));
                sb.append(message);
                System.err.println(sb.toString());
                break;
        }
    }

    public enum LogLevel {
        NORMAL, WARNING, ERROR
    }
}
