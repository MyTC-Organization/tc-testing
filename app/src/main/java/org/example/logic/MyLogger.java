package org.example.logic;

import org.checkerframework.checker.nullness.qual.NonNull;

public interface MyLogger {
    void info(@NonNull String message);
    void error(@NonNull String message);
}
