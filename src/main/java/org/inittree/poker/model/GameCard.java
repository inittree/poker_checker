package org.inittree.poker.model;

final public class GameCard {

    public CardRank getCardRank() {
        return cardRank;
    }

    public CardSuit getCardSuit() {
        return cardSuit;
    }

    public GameCard(final CardRank cardRank, final CardSuit cardSuit) {
        this.cardRank = cardRank;
        this.cardSuit = cardSuit;
    }

    private final CardRank cardRank;
    private final CardSuit cardSuit;

    @Override
    public String toString() {
        return this.cardRank.toString() + this.cardSuit.toString();

    }
}
