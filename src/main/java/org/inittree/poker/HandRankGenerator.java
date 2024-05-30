package org.inittree.poker;

import org.inittree.poker.helper.CardsComparator;
import org.inittree.poker.model.CardRank;
import org.inittree.poker.model.CardSuit;
import org.inittree.poker.model.Combination;
import org.inittree.poker.model.GameCard;
import org.inittree.poker.model.HandRank;

import java.util.List;
import java.util.stream.Collectors;

import static org.inittree.poker.model.FiveCardsHand.CARDS_COUNT;

/*
 * Hand Value      |Unique  |Distinct
 * ----------------------------------
 * Straight Flush  |40      |10
 * Four of a Kind  |624     |156
 * Full Houses     |3744    |156
 * Flush           |5108    |1277
 * Straight        |10200   |10
 * Three of a Kind |54912   |858
 * Two Pair        |123552  |858
 * One Pair        |1098240 |2860
 * High Card       |1302540 |1277
 * ----------------------------------
 * TOTAL           |2598960 |7462
 *
 * Source: http://suffe.cool/poker/evaluator.html
 */
public class HandRankGenerator {

    static public final int STRENGTH_BASE = 13; //cards rank count + 1

    static public HandRank getRank(final List<GameCard> cards) {
        List<GameCard> sortedCards = cards.stream()
            .sorted(new CardsComparator())
            .collect(Collectors.toList());

        boolean isStraight = isStraight(sortedCards);
        boolean isFlush = isFlush(sortedCards);

        if (isStraight && isFlush) {
            int strength = getStraightStrength(sortedCards);
            return new HandRank(Combination.STRAIGHT_FLUSH, strength);
        }

        if (isFourOfAKind(sortedCards)) {
            int strength = getFourOfAKindStrength(sortedCards);
            return new HandRank(Combination.FOUR_OF_A_KIND, strength);
        }

        if (isFullHouse(sortedCards)) {
            int strength = getFullHouseStrength(sortedCards);
            return new HandRank(Combination.FULL_HOUSE, strength);
        }

        if (isFlush) {
            int strength = getFlushStrength(sortedCards);
            return new HandRank(Combination.FLUSH, strength);
        }

        if (isStraight) {
            int strength = getStraightStrength(sortedCards);
            return new HandRank(Combination.STRAIGHT, strength);
        }

        if (isThreeOfAKind(sortedCards)) {
            int strength = getThreeOfAKindStrength(sortedCards);
            return new HandRank(Combination.THREE_OF_A_KIND, strength);
        }

        if (isTwoPair(sortedCards)) {
            int strength = getTwoPairStrength(sortedCards);
            return new HandRank(Combination.TWO_PAIR, strength);
        }

        if (isOnePair(sortedCards)) {
            int strength = getOnePairStrength(sortedCards);
            return new HandRank(Combination.ONE_PAIR, strength);
        }

        return new HandRank(Combination.HIGH_CARD, getHighCardStrength(sortedCards));
    }

    static private boolean isStraight(final List<GameCard> sortedCards) {
        var firstCardRankOrder = getCardRankOrder(sortedCards.get(0));
        var secondCardRankOrder = getCardRankOrder(sortedCards.get(1));
        var thirdCardRankOrder = getCardRankOrder(sortedCards.get(2));
        var fourthCardRankOrder = getCardRankOrder(sortedCards.get(3));
        var fifthCardRankOrder = getCardRankOrder(sortedCards.get(4));

        return (
            firstCardRankOrder + 1 == secondCardRankOrder
            && firstCardRankOrder + 2 == thirdCardRankOrder
            && firstCardRankOrder + 3 == fourthCardRankOrder
            && firstCardRankOrder + 4 == fifthCardRankOrder
        ) || (
            sortedCards.get(4).getCardRank() == CardRank.ACE
            && sortedCards.get(0).getCardRank() == CardRank.DEUCE
            && sortedCards.get(1).getCardRank() == CardRank.TREY
            && sortedCards.get(2).getCardRank() == CardRank.FOUR
            && sortedCards.get(3).getCardRank() == CardRank.FIVE
        );
    }

    static private boolean isFlush(final List<GameCard> cards) {
        CardSuit firstCardSuit = cards.get(0).getCardSuit();
        return cards.stream().allMatch(card -> card.getCardSuit() == firstCardSuit);
    }

    static private boolean isFourOfAKind(final List<GameCard> sortedCards) {
        return isHeadFourCardsTheSame(sortedCards)
        || isHeadFourCardsTheSame(sortedCards.subList(1, CARDS_COUNT));
    }

    private static boolean isFullHouse(final List<GameCard> sortedCards) {
        return (
            isHeadThreeCardsTheSame(sortedCards)
            && isTailTwoCardsTheSame(sortedCards)
        ) || (
            isHeadTwoCardsTheSame(sortedCards)
            && isTailThreeCardsTheSame(sortedCards)
        );
    }

    private static boolean isThreeOfAKind(final List<GameCard> sortedCards) {
        return isHeadThreeCardsTheSame(sortedCards)
            || isHeadThreeCardsTheSame(sortedCards.subList(1, CARDS_COUNT))
            || isHeadThreeCardsTheSame(sortedCards.subList(2, CARDS_COUNT));
    }

    private static boolean isTwoPair(final List<GameCard> sortedCards) {
        return (
            isHeadTwoCardsTheSame(sortedCards)
            && isHeadTwoCardsTheSame(sortedCards.subList(2, CARDS_COUNT))
        ) || (
            isHeadTwoCardsTheSame(sortedCards)
            && isTailTwoCardsTheSame(sortedCards)
        ) || (
            isHeadTwoCardsTheSame(sortedCards.subList(1, CARDS_COUNT))
            && isTailTwoCardsTheSame(sortedCards)
        );
    }

    private static boolean isOnePair(final List<GameCard> sortedCards) {

        return isHeadTwoCardsTheSame(sortedCards)
            || isHeadTwoCardsTheSame(sortedCards.subList(1, CARDS_COUNT))
            || isHeadTwoCardsTheSame(sortedCards.subList(2, CARDS_COUNT))
            || isHeadTwoCardsTheSame(sortedCards.subList(3, CARDS_COUNT));
    }

    private static boolean isHeadFourCardsTheSame(final List<GameCard> cards) {
        return cards.get(0).getCardRank() == cards.get(1).getCardRank()
            && cards.get(0).getCardRank() == cards.get(2).getCardRank()
            && cards.get(0).getCardRank() == cards.get(3).getCardRank();
    }

    private static boolean isHeadThreeCardsTheSame(final List<GameCard> cards) {
        return cards.get(0).getCardRank() == cards.get(1).getCardRank()
            && cards.get(0).getCardRank() == cards.get(2).getCardRank();
    }

    private static boolean isHeadTwoCardsTheSame(final List<GameCard> cards) {
        return cards.get(0).getCardRank() == cards.get(1).getCardRank();
    }

    private static boolean isTailThreeCardsTheSame(final List<GameCard> sortedCards) {
        int count = sortedCards.size();
        return sortedCards.get(count-1).getCardRank() == sortedCards.get(count-2).getCardRank()
            && sortedCards.get(count-1).getCardRank() == sortedCards.get(count-3).getCardRank();
    }

    private static boolean isTailTwoCardsTheSame(final List<GameCard> sortedCards) {
        int count = sortedCards.size();
        return sortedCards.get(count-1).getCardRank() == sortedCards.get(count-2).getCardRank();
    }

    static private int getCardRankOrder(final GameCard card) {
        return card.getCardRank().ordinal();
    }

    /**
     * returns straight's higher card rank order
     */
    private static int getStraightStrength(final List<GameCard> sortedCards) {
        return (
            sortedCards.get(0).getCardRank() == CardRank.DEUCE
            && sortedCards.get(4).getCardRank() == CardRank.ACE
        )
            ? getCardRankOrder(sortedCards.get(3)) //equals CardRank.FOUR.ordered
            : getCardRankOrder(sortedCards.get(4)); //min CardRank.FIVE (ordinal 3)
    }

    /**
     * returns same cards rank order
     */
    private static int getFourOfAKindStrength(final List<GameCard> sortedCards) {
        if (sortedCards.get(0) == sortedCards.get(1)) {
            return getCardRankOrder(sortedCards.get(0)) * STRENGTH_BASE
                + getCardRankOrder(sortedCards.get(4));
        } else {
            return getCardRankOrder(sortedCards.get(1)) * STRENGTH_BASE
                + getCardRankOrder(sortedCards.get(0));
        }
    }

    private static int getFullHouseStrength(final List<GameCard> sortedCards) {
        return isHeadThreeCardsTheSame(sortedCards)
            ? getCardRankOrder(sortedCards.get(0)) * STRENGTH_BASE + getCardRankOrder(sortedCards.get(3))
            : getCardRankOrder(sortedCards.get(2)) * STRENGTH_BASE + getCardRankOrder(sortedCards.get(0));
    }

    private static int getFlushStrength(final List<GameCard> sortedCards) {
        return getHighCardStrength(sortedCards);
    }

    private static int getThreeOfAKindStrength(final List<GameCard> sortedCards) {
        int doubleBase = STRENGTH_BASE * STRENGTH_BASE;

        if (isHeadThreeCardsTheSame(sortedCards)) {
            return getHighCardStrength(List.of(sortedCards.get(3), sortedCards.get(4)))
                + getCardRankOrder(sortedCards.get(0)) * doubleBase;
        }

        if (isHeadThreeCardsTheSame(sortedCards.subList(1, CARDS_COUNT))) {
            return getHighCardStrength(List.of(sortedCards.get(0), sortedCards.get(4)))
                + getCardRankOrder(sortedCards.get(1)) * doubleBase;
        }

        if (isHeadThreeCardsTheSame(sortedCards.subList(2, CARDS_COUNT))) {
            return getHighCardStrength(List.of(sortedCards.get(0), sortedCards.get(1)))
                + getCardRankOrder(sortedCards.get(2)) * doubleBase;
        }

        throw new IllegalStateException("Incorrect cards: " + sortedCards);
    }

    private static int getTwoPairStrength(final List<GameCard> sortedCards) {
        int doubleBase = STRENGTH_BASE * STRENGTH_BASE;

        if (isHeadTwoCardsTheSame(sortedCards)
            && isHeadTwoCardsTheSame(sortedCards.subList(2, CARDS_COUNT))
        ) {
            return getCardRankOrder(sortedCards.get(4))
                + getCardRankOrder(sortedCards.get(0)) * STRENGTH_BASE
                + getCardRankOrder(sortedCards.get(2)) * doubleBase;
        }

        if (isHeadTwoCardsTheSame(sortedCards)
            && isTailTwoCardsTheSame(sortedCards)
        ) {
            return getCardRankOrder(sortedCards.get(2))
                + getCardRankOrder(sortedCards.get(0)) * STRENGTH_BASE
                + getCardRankOrder(sortedCards.get(3)) * doubleBase;
        }

        if (isHeadTwoCardsTheSame(sortedCards.subList(1, CARDS_COUNT))
            && isTailTwoCardsTheSame(sortedCards)
        ) {
            return getCardRankOrder(sortedCards.get(0))
                + getCardRankOrder(sortedCards.get(1)) * STRENGTH_BASE
                + getCardRankOrder(sortedCards.get(3)) * doubleBase;
        }

        throw new IllegalStateException("Incorrect cards: " + sortedCards);
    }

    private static int getOnePairStrength(final List<GameCard> sortedCards) {
        int tripleBase = STRENGTH_BASE * STRENGTH_BASE * STRENGTH_BASE;
        if (isHeadTwoCardsTheSame(sortedCards)) {
            return getHighCardStrength(sortedCards.subList(2, CARDS_COUNT))
                + getCardRankOrder(sortedCards.get(0)) * tripleBase;
        }
        if (isHeadTwoCardsTheSame(sortedCards.subList(1, CARDS_COUNT))) {
            return getHighCardStrength(List.of(sortedCards.get(0), sortedCards.get(3), sortedCards.get(4)))
                + getCardRankOrder(sortedCards.get(1)) * tripleBase;
        }
        if (isHeadTwoCardsTheSame(sortedCards.subList(2, CARDS_COUNT))) {
            return getHighCardStrength(List.of(sortedCards.get(0), sortedCards.get(1), sortedCards.get(4)))
                + getCardRankOrder(sortedCards.get(2)) * tripleBase;
        }
        if (isHeadTwoCardsTheSame(sortedCards.subList(3, CARDS_COUNT))) {
            return getHighCardStrength(sortedCards.subList(0, 3))
                + getCardRankOrder(sortedCards.get(3)) * tripleBase;
        }
        throw new IllegalStateException("Incorrect cards: " + sortedCards);
    }

    /**
     * returns int that means the strength of high card combination, bigger stranger
     * the lowest card rank order * 13^0, next card rank order * 13^1,
     * next card rank order * 13^2 etc.
     */
    private static int getHighCardStrength(final List<GameCard> sortedCards) {
        int basePower = 1;
        int result = 0;
        for (GameCard card : sortedCards) {
            result += getCardRankOrder(card) * basePower;
            basePower *= STRENGTH_BASE;
        }
        return result;
    }
}
