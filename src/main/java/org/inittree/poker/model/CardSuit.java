package org.inittree.poker.model;

import java.util.Arrays;
import java.util.Optional;

public enum CardSuit {

    HEARTS('h'),
    DIAMONDS('d'),
    CLUBS('c'),
    SPADES('s');

    public static Optional<CardSuit> valueByLabel(final char label) {
        return Arrays.stream(values())
            .filter(cardRank -> cardRank.label == label)
            .findFirst();
    }

    private final char label;

    CardSuit(final char label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return String.valueOf(this.label);
    }

}
