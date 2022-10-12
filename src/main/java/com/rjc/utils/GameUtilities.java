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
 * @author niquefa
 *
 */
public final class GameUtilities {

  private static final int WRONG_INDEX_FLAG_FOR_BALL_VALUE = -1;

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
      int points = getPinsKnocedDownOnBall(ball);
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
  public static BowlingGame buildBowlingGame(List<String> rawData) {
    ArrayList<Ball> balls = new ArrayList<>();
    rawData.stream().filter(Objects::nonNull).filter(entryString -> entryString.trim().length() > 0)
        .forEach(entryString -> balls.add(buildFrames(entryString)));

    return new BowlingGame(balls);
  }

  /**
   * @param <code>BowlingGame<code>
   * @return an <code>ArrayList<code> with the order in which the players should
   *         shoot
   */
  public static List<String> getPlayersShootingOrder(BowlingGame game) {
    ArrayList<String> playersOrder = new ArrayList<>();
    HashSet<String> playersDiscovered = new HashSet<>();

    for (Ball ball : game.getBalls()) {
      String player = ball.getName();
      if (!playersDiscovered.contains(player)) {
        playersOrder.add(player);
        playersDiscovered.add(player);
      }
    }

    return playersOrder;
  }

  /**
   * @param playerName
   * @param balls
   * @return <code>ArrayList<code> of <code>Frame<code> when the given balls are
   *         considered a valid sequences of balls for a single player, null
   *         otherwise
   */
  public static List<Frame> getFrames(List<BallResult> balls) {

    ArrayList<Frame> frames = new ArrayList<>();
    int indexCurrentBall = 0;
    while (indexCurrentBall < balls.size()) {

      // Only one last frame remains for all the rest of balls
      if (frames.size() == GameDataIntegrityValidator.MAX_POSSIBLE_FRAMES - 1) {
        return getLastFrame(indexCurrentBall, balls, frames);
      }

      BallResult pins = balls.get(indexCurrentBall);

      boolean currentFrameBuildingFinishedFlag = currentFrameBuildingCouldFinishInOneStep(
          indexCurrentBall,
          balls,
          pins);

      indexCurrentBall = doMinimalCurrentFrameBuilding(indexCurrentBall, balls, frames, pins);

      if (currentFrameBuildingFinishedFlag) {
        continue;
      }
      indexCurrentBall = addNextFrameIfPossibleAndGetSuccessFlag(indexCurrentBall, balls, frames,
          pins);

      if (indexCurrentBall == WRONG_INDEX_FLAG_FOR_BALL_VALUE)
        return new ArrayList<>();
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

  private static int addNextFrameIfPossibleAndGetSuccessFlag(int indexCurrentBall,
      List<BallResult> balls,
      ArrayList<Frame> frames, BallResult pins) {
    ArrayList<BallResult> frameBalls = new ArrayList<>();
    frameBalls.add(pins);

    BallResult nextPins = balls.get(++indexCurrentBall);

    if (nextPins.getValue() > GameDataIntegrityValidator.MAX_POSSIBLE_PINS_KNOCKED_BY_A_BALL) {
      return WRONG_INDEX_FLAG_FOR_BALL_VALUE;
    }
    frameBalls.add(nextPins);
    Frame currentFrame = new Frame(frameBalls);
    frames.add(currentFrame);

    if (currentFrame
        .getTotalPinsInFrame() > GameDataIntegrityValidator.MAX_POSSIBLE_PINS_KNOCKED_BY_A_BALL) {
      return WRONG_INDEX_FLAG_FOR_BALL_VALUE;
    }
    return indexCurrentBall + 1;
  }

  private static boolean currentFrameBuildingCouldFinishInOneStep(int indexCurrentBall,
      List<BallResult> balls, BallResult pins) {
    return (pins.getValue() == GameDataIntegrityValidator.MAX_POSSIBLE_PINS_KNOCKED_BY_A_BALL)
        || (indexCurrentBall + 1 == balls.size());

  }

  private static int doMinimalCurrentFrameBuilding(
      int indexCurrentBall, List<BallResult> balls, ArrayList<Frame> frames, BallResult pins) {

    // Current pin got a strike, so the current frame is finished
    if (pins.getValue() == GameDataIntegrityValidator.MAX_POSSIBLE_PINS_KNOCKED_BY_A_BALL) {
      ArrayList<BallResult> frameBalls = new ArrayList<>();
      frameBalls.add(new BallResult(pins.isFaul(), pins.getValue()));
      frames.add(new Frame(frameBalls));
      indexCurrentBall++;
      return indexCurrentBall;
    }

    // If Current throw is the last one. but is a game without finish in the input
    // for this player, this should never be possible for a complete and correct
    // game
    if (indexCurrentBall + 1 == balls.size()) {
      ArrayList<BallResult> frameBalls = new ArrayList<>();
      frameBalls.add(pins);
      frames.add(new Frame(frameBalls));
      indexCurrentBall++;
      return indexCurrentBall;
    }
    return indexCurrentBall;
  }

  private static List<Frame> getLastFrame(
      int indexCurrentBall, List<BallResult> balls, ArrayList<Frame> frames) {
    ArrayList<BallResult> frameBalls = new ArrayList<>();
    for (int i = indexCurrentBall; i < balls.size(); ++i) {
      frameBalls.add(balls.get(i));
    }

    frames.add(new Frame(frameBalls));

    if (!GameDataIntegrityValidator.validLastFrame(frameBalls)) {
      return new ArrayList<>();
    }
    return frames;
  }

  private static boolean isFaul(String ball) {
    return "F".equalsIgnoreCase(ball);
  }

  private static int getPinsKnocedDownOnBall(Ball ball) {
    return isFaul(ball.getResult().trim()) ? 0 : Integer.parseInt(ball.getResult().trim());
  }

  private static Ball buildFrames(String stringEntry) {
    String[] arr = stringEntry.split(Global.VALID_TOKEN_SEPARATOR);
    return new Ball(arr[0].trim(), arr[1].trim());
  }
}
