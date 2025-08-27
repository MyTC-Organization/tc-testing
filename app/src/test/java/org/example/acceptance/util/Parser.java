package org.example.acceptance.util;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.example.logic.Logic;
import org.example.utils.Position;

import java.util.Optional;
import java.util.function.BiConsumer;

public final class Parser {
    private Parser() {}

    @NonNull
    public static Position parseBoard(@NonNull final String board,
                                       @NonNull final BiConsumer<Logic, Position> consumer,
                                       @NonNull Logic logic) {
        Position lastHit = null;
        final int nRows = board.split("\n").length;
        final String[] rows = board.split("\n");
        for (int row = 0; row < nRows; row++) {
            final String line = rows[row];
            final String[] cols = line.split(" ");
            final int nCols = line.split(" ").length;
            for (int col = 0; col < nCols; col++) {
                final String cell = cols[col];
                if (!cell.equals("0")) {
                    lastHit = new Position(row, col);
                    consumer.accept(logic, lastHit);
                }
            }
        }
        assert lastHit != null;
        return lastHit;
    }

}
