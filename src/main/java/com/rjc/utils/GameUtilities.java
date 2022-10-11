package com.rjc.utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.SortedMap;
import java.util.TreeMap;

import com.rjc.constants.Global;
import com.rjc.entities.Ball;
import com.rjc.entities.BallResult;
import com.rjc.entities.BowlingGame;
import com.rjc.entities.Frame;
import com.rjc.entities.FrameInfo;
import com.rjc.validators.GameDataIntegrityValidator;

/**
 * Utilities to get information from the bowling game data.
 *
 * @author coderniquefa
 *
 */
public final class GameUtilities {

  private GameUtilities() {
  }

  /**
   * @param <code>BowlingGame<code> The given bowling game
   * @return a map, in which each key is a player name, and the amount of pins
   *         knocked down in the turn. he made.
   */
  public static SortedMap<String, List<BallResult>> getPlayersGame(BowlingGame game) {
    SortedMap<String, List<BallResult>> mapToReturn = new TreeMap<>();

    for (Ball ball : game.getBalls()) {
      String player = ball.getName();
      int points = getPinsKnocedDownOnRoll(ball);
      mapToReturn.putIfAbsent(player, new ArrayList<>());
      mapToReturn.get(player).add(new BallResult(isFaul(ball.getResult()), points));
    }

    return mapToReturn;
  }

  /**
   * Builds a BowlingGame instance from the given input List
   *
   * @param <code>ArrayList<String><code> The list to build the bowling game from
   * @return <BowlingGrame> instance of the BowlingGame builded
   */
  public static BowlingGame buildBowlingGame(List<String> game) {
    ArrayList<Ball> turns = new ArrayList<>();
    game.stream().filter(Objects::nonNull).filter(entryString -> entryString.trim().length() > 0)
        .forEach(entryString -> turns.add(buildFrames(entryString)));

    return new BowlingGame(turns);
  }

  /**
   * @param <code>BowlingGame<code>
   * @return an <code>ArrayList<code> with the order in which the players should
   *         shoot
   */
  public static List<String> getPlayersShootingOrder(BowlingGame game) {
    ArrayList<String> playersOrder = new ArrayList<>();
    HashSet<String> playersDiscovered = new HashSet<>();

    for (Ball roll : game.getBalls()) {
      String player = roll.getName();
      if (!playersDiscovered.contains(player)) {
        playersOrder.add(player);
        playersDiscovered.add(player);
      }
    }

    return playersOrder;
  }

  /**
   * @param playerName
   * @param rolls
   * @return <code>ArrayList<code> of <code>Frame<code> when the given balls are
   *         considered a valid sequences of balls for a single player, null
   *         otherwise
   */
  public static List<Frame> getFrames(List<BallResult> rolls) {

    ArrayList<Frame> frames = new ArrayList<>();

    for (int indexCurrentBall = 0; indexCurrentBall < rolls.size(); ++indexCurrentBall) {

      // Only one last turn remains for all the rest of rolls
      if (frames.size() == GameDataIntegrityValidator.MAX_POSSIBLE_FRAMES - 1) {

        ArrayList<BallResult> frameBalls = new ArrayList<>();
        for (int i = indexCurrentBall; i < rolls.size(); ++i) {
          frameBalls.add(rolls.get(i));
        }

        frames.add(new Frame(frameBalls));

        if (!GameDataIntegrityValidator.validLastTurn(frameBalls)) {
          return new ArrayList<>();
        }
        return frames;
      }

      BallResult pins = rolls.get(indexCurrentBall);
      if (pins.getValue() == GameDataIntegrityValidator.MAX_POSSIBLE_PINS_KNOCKED_BY_A_BALL) {
        ArrayList<BallResult> frameBalls = new ArrayList<>();
        frameBalls.add(new BallResult(pins.isFaul(), pins.getValue()));
        frames.add(new Frame(frameBalls));
        continue;
      }

      // if Current throw is the last one. but is a game without finish in the input
      // for this player
      if (indexCurrentBall + 1 == rolls.size()) {
        ArrayList<BallResult> frameBalls = new ArrayList<>();
        frameBalls.add(pins);
        frames.add(new Frame(frameBalls));
        continue;
      }

      ArrayList<BallResult> frameBalls = new ArrayList<>();
      frameBalls.add(pins);

      BallResult nextPins = rolls.get(++indexCurrentBall);

      if (nextPins.getValue() > GameDataIntegrityValidator.MAX_POSSIBLE_PINS_KNOCKED_BY_A_BALL) {
        return new ArrayList<>();
      }
      frameBalls.add(nextPins);
      Frame currentFrame = new Frame(frameBalls);
      frames.add(currentFrame);

      if (currentFrame
          .getTotalPinsInTurn() > GameDataIntegrityValidator.MAX_POSSIBLE_PINS_KNOCKED_BY_A_BALL) {
        return new ArrayList<>();
      }
    }

    return frames.size() <= GameDataIntegrityValidator.MAX_POSSIBLE_FRAMES ? frames
        : new ArrayList<>();

  }

  /**
   * Check if the given frame has a strike.
   *
   * @param currentFrame The frame to check
   * @return true if is a strike
   */
  public static boolean isStrike(FrameInfo currentFrame) {
    return currentFrame.getFrame().getBalls().size() == 1 && currentFrame.getFrameScore() == 10;
  }

  /**
   * Check if the given frame has a spare.
   *
   * @param currentFrame The frame to check
   * @return true if the frame is a spare
   */
  public static boolean isSpare(FrameInfo currentFrame) {
    return currentFrame.getFrame().getBalls().size() == 2 && currentFrame.getFrameScore() == 10;
  }

  /**
   * Computes the score of a single frame.
   *
   * @param frameInfo The frame to get the score from
   * @return the score of the given frame
   */
  public static int getFrameScore(FrameInfo frameInfo) {
    return frameInfo.getFrame().getBalls().stream().map(BallResult::getValue)
        .mapToInt(Integer::intValue).sum();
  }

  private static boolean isFaul(String ball) {
    return "F".equalsIgnoreCase(ball);
  }

  private static int getPinsKnocedDownOnRoll(Ball ball) {
    return isFaul(ball.getResult().trim()) ? 0 : Integer.parseInt(ball.getResult().trim());
  }

  private static Ball buildFrames(String stringEntry) {
    String[] arr = stringEntry.split(Global.VALID_TOKEN_SEPARATOR);
    return new Ball(arr[0].trim(), arr[1].trim());
  }
}
