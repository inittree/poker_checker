package org.inittree.poker;

import org.inittree.poker.helper.HandsAlphabeticComparator;
import org.inittree.poker.helper.HandsRankComparator;
import org.inittree.poker.helper.Parser;
import org.inittree.poker.model.FiveCardsHand;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class FiveCardDrawSolver {
    static public String process(final String[] rawHands) {

        List<FiveCardsHand> hands = Arrays.stream(rawHands)
            .map(rawHand -> new FiveCardsHand(Parser.stringToCards(rawHand)))
            .sorted(new HandsRankComparator())
            .collect(Collectors.toList());

//        return hands.stream()
//            .map(FiveCardsHand::toString)
//            .collect(Collectors.joining(" "));
        return toOutputView(hands);
    }

    //todo: reimplement
    private static String toOutputView(final List<FiveCardsHand> hands) {
        StringBuilder result = new StringBuilder();
        FiveCardsHand currentHand = hands.get(0);
        List<FiveCardsHand> sameStrengthHands = new ArrayList<>(List.of(currentHand));
        HandsRankComparator handsRankComparator = new HandsRankComparator();

        for (int i = 1; i < hands.size(); i++) {
            if (handsRankComparator.compare(currentHand, hands.get(i)) == 0) {
                sameStrengthHands.add(hands.get(i));
            } else {
                String sameHands = sameStrengthHands.stream()
                    .sorted(new HandsAlphabeticComparator())
                    .map(FiveCardsHand::toString)
                    .collect(Collectors.joining("="));
                result.append(" ").append(sameHands);
                currentHand = hands.get(i);
                sameStrengthHands = new ArrayList<>(List.of(currentHand));
            }
        }

        if (!sameStrengthHands.isEmpty()) {
            String sameHands = sameStrengthHands.stream()
                .sorted(new HandsAlphabeticComparator())
                .map(FiveCardsHand::toString)
                .collect(Collectors.joining("="));
            result.append(" ").append(sameHands);
        }

        return result.toString().trim();
    }
}
