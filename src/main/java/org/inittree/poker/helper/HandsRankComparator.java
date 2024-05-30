package org.inittree.poker.helper;

import org.inittree.poker.model.FiveCardsHand;

import java.util.Comparator;

public class HandsRankComparator implements Comparator<FiveCardsHand> {
    @Override
    public int compare(FiveCardsHand firstHand, FiveCardsHand secondHand) {
        int combinationDelta =
            firstHand.getRank().getCombination().ordinal() - secondHand.getRank().getCombination().ordinal();
        return (combinationDelta != 0)
            ? combinationDelta
            : firstHand.getRank().getStrength() - secondHand.getRank().getStrength();
    }

}
