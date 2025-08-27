package org.example.logic;


import org.checkerframework.checker.nullness.qual.NonNull;

public class LoggerImpl implements MyLogger {
    @Override
    public void info(@NonNull String message) {
        System.out.println("INFO: " + message);
    }

    @Override
    public void error(@NonNull String message) {
        System.err.println("ERROR: " + message);
    }
}
