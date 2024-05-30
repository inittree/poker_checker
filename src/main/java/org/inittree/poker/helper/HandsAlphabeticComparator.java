package org.inittree.poker.helper;

import org.inittree.poker.model.FiveCardsHand;

import java.util.Comparator;

public class HandsAlphabeticComparator implements Comparator<FiveCardsHand> {
    @Override
    public int compare(FiveCardsHand firstHand, FiveCardsHand secondHand) {
        return firstHand.toString().compareTo(secondHand.toString());
    }
}
