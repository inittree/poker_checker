package org.inittree.poker.helper;

import org.inittree.poker.model.GameCard;

import java.util.Comparator;

public class CardsComparator implements Comparator<GameCard> {

    @Override
    public int compare(GameCard firstCard, GameCard secondCard) {
        return firstCard.getCardRank().ordinal() - secondCard.getCardRank().ordinal();
    }
}
