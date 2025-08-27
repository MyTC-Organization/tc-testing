package org.example.logic;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.example.utils.Position;

import java.util.Optional;

public interface Logic {

    @NonNull
    Optional<Integer> hit(@NonNull Position position);

    @NonNull
    Optional<Integer> getMark(@NonNull Position position);

    boolean isOver();
    boolean areNeighbours(@NonNull Position p1, @NonNull Position p2);
}
