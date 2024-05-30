package org.inittree.poker;

import org.inittree.poker.model.GameType;

import java.util.Arrays;

class Solver {
  static String process(final String line) {

    if (line == null) {
      throw new IllegalArgumentException("Input is not correct");
    }

    String[] args = line.split(" ");
    GameType gameType = GameType.valueByLabel(args[0])
        .orElseThrow(() -> new IllegalArgumentException("Input is not correct"));

    String[] rawCards = Arrays.copyOfRange(args, 1/*except game type*/, args.length);

    switch (gameType) {
      case FIVE_CARD_DRAW:
        return FiveCardDrawSolver.process(rawCards);
      case TEXAS_HOLDEM:
      case OMAHA_HOLDEM:
      default:
        throw new IllegalStateException("This game type currently is not supported");
    }

  }

}
