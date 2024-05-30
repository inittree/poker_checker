package org.inittree.poker.model;

import java.util.Arrays;
import java.util.Optional;

public enum GameType {

    TEXAS_HOLDEM("texas-holdem"),
    OMAHA_HOLDEM("omaha-holdem"),
    FIVE_CARD_DRAW("five-card-draw");

    public static Optional<GameType> valueByLabel(final String label) {
        return Arrays.stream(values())
            .filter(cardRank -> cardRank.label.equals(label))
            .findFirst();
    }

    private final String label;

    GameType(final String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return this.label;
    }

}
