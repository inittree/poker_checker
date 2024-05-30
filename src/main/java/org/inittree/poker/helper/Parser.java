package org.inittree.poker.helper;

import org.inittree.poker.model.CardRank;
import org.inittree.poker.model.CardSuit;
import org.inittree.poker.model.GameCard;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Parser {

    static public List<GameCard> stringToCards(final String rawCards) {
        var cardRanks = IntStream.range(0, rawCards.length())
            .filter(i -> i % 2 == 0)
            .mapToObj(i -> CardRank.valueByLabel(rawCards.charAt(i)).orElseThrow())
            .collect(Collectors.toList());

        var cardSuits = IntStream.range(0, rawCards.length())
            .filter(i -> i % 2 == 1)
            .mapToObj(i -> CardSuit.valueByLabel(rawCards.charAt(i)).orElseThrow())
            .collect(Collectors.toList());

        return IntStream.range(0, rawCards.length() / 2)
            .mapToObj(i -> new GameCard(cardRanks.get(i), cardSuits.get(i)))
            .collect(Collectors.toList());
    }
}
