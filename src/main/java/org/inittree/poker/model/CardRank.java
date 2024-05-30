package org.inittree.poker.model;

import java.util.Arrays;
import java.util.Optional;

public enum CardRank {

    DEUCE('2'),
    TREY('3'),
    FOUR('4'),
    FIVE('5'),
    SIX('6'),
    SEVEN('7'),
    EIGHT('8'),
    NINE('9'),
    TEN('T'),
    JACK('J'),
    QUEEN('Q'),
    KING('K'),
    ACE('A');

    public static Optional<CardRank> valueByLabel(final char label) {
        return Arrays.stream(values())
            .filter(cardRank -> cardRank.label == label)
            .findFirst();
    }
    private final char label;

    CardRank(final char label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return String.valueOf(this.label);
    }

}
