package org.inittree.poker.model;

public class HandRank {

    public Combination getCombination() {
        return combination;
    }

    public int getStrength() {
        return strength;
    }

    public HandRank(final Combination combination, final int strength) {
        this.combination = combination;
        this.strength = strength;
    }

    private final Combination combination;
    private final int strength;
}
