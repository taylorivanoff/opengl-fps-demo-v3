package com.example.utils;

public class Logger {
    public enum Level {
        DEBUG, INFO, WARN, ERROR
    }

    private static Level currentLevel = Level.INFO;

    public static void log(Level level, String message) {
        if (level.ordinal() >= currentLevel.ordinal()) {
            System.out.println("[" + level + "] " + message);
        }
    }

    public static void setLevel(Level level) {
        currentLevel = level;
    }
}
