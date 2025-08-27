package org.example.logic;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.example.utils.Position;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class LogicImpl implements Logic {

    private static final Logger log = LogManager.getLogger(LogicImpl.class);
    private final int size;
    private final MyLogger logger;
    private List<Position> marks = new LinkedList<>();
    private boolean moving = false;

    public LogicImpl(final int size, final MyLogger logger) {
        this.size = size;
        this.logger = logger;
    }

    @Override
    public @NonNull Optional<Integer> hit(@NonNull Position position) {
        this.logger.info("Hit at position: " + position);
        if (!this.isValid(position)) {
            throw new IllegalArgumentException("Position is not valid");
        }
        if (this.isOver()){
            this.logger.info("The Game is Over");
            return Optional.empty();
        }
        if (this.moving || startMoving(position)){
            this.logger.info("Moving positions");
            this.moving = true;
            this.moveMarks();
            return Optional.empty();
        }
        this.logger.info("Add the hitted position");
        this.marks.add(position);
        return Optional.of(this.marks.size());
    }

    private boolean isValid(@NonNull final Position position) {
        return this.size > position.x() && this.size > position.y() &&
                position.x() >= 0 && position.y() >= 0 &&
                !this.marks.contains(position);
    }

    private boolean neighbours(Position p1, Position p2){
        logger.info("Checking if " + p1 + " and " + p2 + " are neighbours");
        return Math.abs(p1.x()-p2.x()) <= 1 && Math.abs(p1.y()-p2.y()) <= 1;
    }

    private boolean startMoving(Position position) {
        return this.marks.stream().anyMatch(p -> neighbours(p, position));
    }

    private void moveMarks() {
        this.marks = this.marks
                .stream()
                .map(p -> new Position(p.x()+1, p.y()-1))
                .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public boolean areNeighbours(@NonNull Position p1, @NonNull Position p2) {
        return this.neighbours(p1, p2);
    }

    @Override
    public @NonNull Optional<Integer> getMark(@NonNull Position position) {
        this.logger.info("Get mark for position: " + position);
        return Optional.of(this.marks.indexOf(position)).filter(i -> i>=0).map(i -> i+1);
    }

    @Override
    public boolean isOver() {
        this.logger.info("Check if the game is over");
        return this.marks.stream().anyMatch(p -> p.x() == this.size || p.y() == -1);
    }
}
