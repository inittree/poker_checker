package org.inittree.poker.model;

import org.inittree.poker.HandRankGenerator;

import java.util.List;
import java.util.stream.Collectors;

public class FiveCardsHand {

    static public final int CARDS_COUNT = 5;

    public List<GameCard> getCards() {
        return cards;
    }

    public HandRank getRank() {
        return rank;
    }

    public FiveCardsHand(final List<GameCard> cards) {
        this.cards = cards;
        this.rank = HandRankGenerator.getRank(cards);
    }

    private final List<GameCard> cards;
    private final HandRank rank;

    @Override
    public String toString() {
        return this.cards.stream()
            .map(GameCard::toString)
            .collect(Collectors.joining());// + " rank: " + rank.getCombination() + " s: " + rank.getStrength();
    }
}
