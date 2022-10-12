package com.rjc.validators;

import java.util.List;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.TreeMap;

import com.rjc.entities.Ball;
import com.rjc.entities.BallResult;
import com.rjc.entities.BowlingGame;
import com.rjc.entities.Frame;
import com.rjc.exceptions.InvalidGameDataException;
import com.rjc.exceptions.NotEnoughFramesForPlayerException;
import com.rjc.exceptions.RJCException;
import com.rjc.exceptions.ToFewBallsInPlayerException;
import com.rjc.exceptions.WrongInfoInDataInput;
import com.rjc.exceptions.WrongNumberOfBallsInLastFrameException;
import com.rjc.exceptions.WrongPinNumbersInFrame;
import com.rjc.utils.GameUtilities;

/**
 * Class to validate Game data information, utilities to check whether or not
 * the input data represents a valid Bowling game finished
 *
 * @author niquefa
 *
 */
public class GameDataIntegrityValidator {

  private GameDataIntegrityValidator() {
  }

  public static final int MAX_POSSIBLE_BALLS = 21;
  public static final int MAX_POSSIBLE_FRAMES = 10;
  public static final int MAX_POSSIBLE_PINS_KNOCKED_BY_A_PLAYER_IN_A_GAME = 120;
  public static final int MAX_POSSIBLE_PINS_KNOCKED_BY_A_BALL = 10;
  public static final int MAX_POSSIBLE_BALLS_IN_LAST_FRAME = 3;
  public static final int MAX_POSSIBLE_BALLS_IN_FRAME_DIFFERENT_TO_LAST_ONE = 2;

  /**
   * Validates if the input data is a valid complete bowling game
   *
   * @param game
   * @return true when the given <code>BowlingGame<code> is consider a valid
   *         complete list of balls for a bowling game
   * @throws RJCException
   */
  public static void validateIfValidGame(BowlingGame game) throws RJCException {

    if (!allBallTokensAreValidValues(game)) {
      throw new InvalidGameDataException(
          String.format("Error: Not all ball tokens are valid values. Game data: %s",
              game.toString()));
    }

    SortedMap<String, List<BallResult>> playersToBallsListmap = GameUtilities.getPlayersGame(game);

    if (!atLeastOnePlayerAndMinimumBallsForPlayerOK(playersToBallsListmap)
        || !allMeetMaxPossiblePinsKnockedByBall(playersToBallsListmap)
        || !allMeetMaxPossiblePinsKnocked(playersToBallsListmap)
        || !allMeetMaxPossibleBalls(playersToBallsListmap)) {
      throw new InvalidGameDataException(
          String.format("Error: Invalid game data. Check players data. Game data: %s",
              game.toString()));
    }

    if (!allMeetMaxPossibleFrames(playersToBallsListmap)) {
      throw new InvalidGameDataException(
          String.format("Error: Invalid game data. Do not meet max possible frames. Game data: %s",
              game.toString()));
    }

    validateAllPlayersHaveThrownAllBalls(game, playersToBallsListmap);

    if (!correctFrameIntercalation(game)) {
      throw new InvalidGameDataException(
          String.format("Error: Invalid game data. Do not meet player intercalation. Game data: %s",
              game.toString()));
    }
  }

  /**
   * This method assumes all the input data are the balls given in chronological
   * order.
   *
   * @param game The actual game
   * @return true if the intercalation of players taking turns is correct, false
   *         otherwise
   * @throws InvalidGameDataException when the given data has incorrect
   *                                  intercalation of players taking turns
   */
  public static boolean correctFrameIntercalation(BowlingGame game)
      throws InvalidGameDataException {

    try {
      List<Ball> balls = game.getBalls();

      List<String> playerOrder = GameUtilities.getPlayersShootingOrder(game);

      SortedMap<String, String> previusPlayer = buildPreviusPlayerMap(playerOrder);

      return turnsAreCorrect(balls, playerOrder, previusPlayer);
    } catch (Exception unespectedError) {
      unespectedError.printStackTrace();
      throw new InvalidGameDataException(
          String.format("Error: Invalid game data. Do not meet player intercalation. Game data: %s",
              game.toString()));
    }
  }

  private static boolean turnsAreCorrect(List<Ball> balls, List<String> playerOrder,
      SortedMap<String, String> previusPlayer) throws RJCException {
    String currentPreviusPlayer = previusPlayer.get(playerOrder.get(0));

    int ballIndex = 0;
    // Frames 1 to one before last
    for (int frame = 1; frame < MAX_POSSIBLE_FRAMES; frame++) {
      for (String currentPlayer : playerOrder) {
        if (!currentPreviusPlayer.equals(previusPlayer.get(currentPlayer))) {
          return false;
        }

        Ball playerFirstBall = balls.get(ballIndex);
        if (!playerFirstBall.getName().equals(currentPlayer)) {
          return false;
        }

        if (playerFirstBall.getResult()
            .equalsIgnoreCase(MAX_POSSIBLE_PINS_KNOCKED_BY_A_BALL + "")) {
          ballIndex++;
          currentPreviusPlayer = currentPlayer;
          continue;
        }
        Ball playerSecondBall = balls.get(++ballIndex);
        if (!playerSecondBall.getName().equals(currentPlayer)) {
          return false;
        }

        ++ballIndex;
        currentPreviusPlayer = currentPlayer;
      }
    }

    return getTurnsHelper(ballIndex, previusPlayer, currentPreviusPlayer, playerOrder, balls);
  }

  private static boolean getTurnsHelper(int ballIndex, SortedMap<String, String> previusPlayer,
      String currentPreviusPlayer, List<String> playerOrder, List<Ball> balls)
      throws WrongPinNumbersInFrame, WrongInfoInDataInput {
    for (String currentPlayer : playerOrder) {
      if (!currentPreviusPlayer.equals(previusPlayer.get(currentPlayer))) {
        return false;
      }

      Ball playerFirstBall = balls.get(ballIndex);
      if (!playerFirstBall.getName().equals(currentPlayer)) {
        return false;
      }

      if (playerFirstBall.getResult().equalsIgnoreCase(MAX_POSSIBLE_PINS_KNOCKED_BY_A_BALL + "")) {
        ballIndex += 3;
        currentPreviusPlayer = currentPlayer;
        continue;
      }
      Ball playerSecondBall = balls.get(++ballIndex);
      if (!playerSecondBall.getName().equals(currentPlayer)) {
        return false;
      }

      if (playerEarnExtraBall(playerFirstBall, playerSecondBall)) {
        ++ballIndex;
      }

      ++ballIndex;
      currentPreviusPlayer = currentPlayer;
    }
    return true;
  }

  /**
   * @param frameBalls The balls of the frame
   * @return true if the balls in the argument represent a valid last turn of a
   *         player. the only possible values for a valid last turn are: One ball
   *         with value in [0,10] Two balls, the first a 10 and the second a value
   *         in [0,10] Three balls, the first 2 both in 10 and the third one with
   *         a value in [0,10]
   */
  public static boolean validLastFrame(List<BallResult> frameBalls) {

    if (frameBalls.stream().anyMatch(
        ballResult -> ballResult.getValue() < 0
            || ballResult.getValue() > MAX_POSSIBLE_PINS_KNOCKED_BY_A_BALL)) {
      return false;
    }

    switch (frameBalls.size()) {
      case 1:
        return true;
      case 2:
        return (frameBalls.get(0).getValue() == MAX_POSSIBLE_PINS_KNOCKED_BY_A_BALL
            && frameBalls.get(1).getValue() == MAX_POSSIBLE_PINS_KNOCKED_BY_A_BALL)
            || frameBalls.get(0).getValue()
                + frameBalls.get(1).getValue() <= MAX_POSSIBLE_PINS_KNOCKED_BY_A_BALL;

      case 3:
        return frameBalls.get(0).getValue() == MAX_POSSIBLE_PINS_KNOCKED_BY_A_BALL
            || (frameBalls.get(0).getValue() == MAX_POSSIBLE_PINS_KNOCKED_BY_A_BALL
                && frameBalls.get(1).getValue() == MAX_POSSIBLE_PINS_KNOCKED_BY_A_BALL)
            || frameBalls.get(0).getValue()
                + frameBalls.get(1).getValue() == MAX_POSSIBLE_PINS_KNOCKED_BY_A_BALL;

      default:
        return false;
    }
  }

  private static boolean allBallTokensAreValidValues(BowlingGame game) {

    return game.getBalls().stream().allMatch(GameDataIntegrityValidator::isValidBall);
  }

  private static boolean isValidBall(Ball ball) {

    if (ball == null || ball.getResult().trim().length() == 0) {
      return false;
    }

    return RawDataValidator.isOnlyDigitsOrFAndMeetsMaxPinLimit(ball.getResult().trim());
  }

  private static boolean atLeastOnePlayerAndMinimumBallsForPlayerOK(
      SortedMap<String, List<BallResult>> playersToBallsListmap)
      throws ToFewBallsInPlayerException {
    if (playersToBallsListmap == null || playersToBallsListmap.size() == 0) {
      return false;
    }
    for (Entry<String, List<BallResult>> player : playersToBallsListmap.entrySet()) {
      List<BallResult> balls = playersToBallsListmap.get(player.getKey());
      if (balls == null || balls.isEmpty() || balls.size() < 10) {
        throw new ToFewBallsInPlayerException(
            String.format("Insuficient balls for player %s. Found: %s", player, balls));
      }
    }
    return true;
  }

  private static boolean playerEarnExtraBall(Ball playerFirstBall, Ball playerSecondBall)
      throws WrongPinNumbersInFrame, WrongInfoInDataInput {
    int playerTotalBalls = getPlayerBallPins(playerFirstBall) + getPlayerBallPins(playerSecondBall);
    if (playerTotalBalls < 0 || playerTotalBalls > MAX_POSSIBLE_PINS_KNOCKED_BY_A_BALL) {
      throw new WrongPinNumbersInFrame(
          String.format("Wrong pin knocked number for players two balls: %s and %s",
              playerFirstBall, playerSecondBall));
    }

    return playerTotalBalls == MAX_POSSIBLE_PINS_KNOCKED_BY_A_BALL;
  }

  private static int getPlayerBallPins(Ball ball) throws WrongInfoInDataInput {
    if (!RawDataValidator.isOnlyDigitsOrFAndMeetsMaxPinLimit(ball.getResult())) {
      throw new WrongInfoInDataInput(
          String.format("Wrong info in data input: %s and %s", ball.getName(), ball.getResult()));
    }
    if (ball.getResult().trim().equalsIgnoreCase("F")) {
      return 0;
    }
    return Integer.parseInt(ball.getResult().trim());
  }

  private static SortedMap<String, String> buildPreviusPlayerMap(List<String> playerOrder) {
    SortedMap<String, String> previusPlayer = new TreeMap<>();
    for (int i = 0; i < playerOrder.size(); i++) {
      previusPlayer.put(playerOrder.get(i),
          playerOrder.get((playerOrder.size() + i - 1) % playerOrder.size()));
    }
    return previusPlayer;
  }

  private static boolean allMeetMaxPossiblePinsKnockedByBall(
      SortedMap<String, List<BallResult>> map) {
    for (Entry<String, List<BallResult>> player : map.entrySet()) {
      if (player.getValue().stream().anyMatch(
          ballResult -> ballResult.getValue() < 0
              || ballResult.getValue() > MAX_POSSIBLE_PINS_KNOCKED_BY_A_BALL)) {
        return false;
      }
    }
    return true;
  }

  private static boolean allMeetMaxPossiblePinsKnocked(SortedMap<String, List<BallResult>> map) {

    return map.keySet().stream()
        .allMatch(playerName -> (map.get(playerName).stream().map(BallResult::getValue)
            .mapToInt(Integer::intValue).sum() <= MAX_POSSIBLE_PINS_KNOCKED_BY_A_PLAYER_IN_A_GAME));
  }

  private static boolean allMeetMaxPossibleFrames(SortedMap<String, List<BallResult>> map) {

    return map.keySet().stream()
        .allMatch(playerName -> null != GameUtilities.getFrames(map.get(playerName)));

  }

  private static boolean allMeetMaxPossibleBalls(SortedMap<String, List<BallResult>> map) {
    for (Entry<String, List<BallResult>> player : map.entrySet()) {
      if (player.getValue().size() > MAX_POSSIBLE_BALLS) {
        return false;
      }
    }
    return true;
  }

  private static void validateAllPlayersHaveThrownAllBalls(BowlingGame game,
      SortedMap<String, List<BallResult>> playersToBallsListmap) throws RJCException {

    for (Entry<String, List<BallResult>> player : playersToBallsListmap.entrySet()) {
      List<Frame> playerFrames = GameUtilities.getFrames(player.getValue());

      if (playerFrames.size() != MAX_POSSIBLE_FRAMES) {
        throw new NotEnoughFramesForPlayerException(
            String.format("Wrong number of frames for player %s. Expected %d, found %d", player,
                MAX_POSSIBLE_FRAMES,
                playerFrames.size()));
      }
      Frame lastFrame = playerFrames.get(MAX_POSSIBLE_FRAMES - 1);
      if (!isValidLastFrame(lastFrame)) {
        throw new WrongNumberOfBallsInLastFrameException(
            String.format(
                "Wrong number of balls in last frame for player %s. found %s. game info: %s",
                player,
                lastFrame.getBalls(), game.toString()));
      }
    }
  }

  private static boolean isValidLastFrame(Frame lastFrame) {

    List<BallResult> balls = lastFrame.getBalls();
    if (balls == null || balls.isEmpty() || balls.size() > MAX_POSSIBLE_BALLS_IN_LAST_FRAME
        || balls.stream().anyMatch(
            ballResult -> ballResult.getValue() < 0
                || ballResult.getValue() > MAX_POSSIBLE_PINS_KNOCKED_BY_A_BALL)) {
      return false;
    }

    int firstTwoBallsPins = balls.get(0).getValue() + balls.get(1).getValue();

    if (balls.size() == MAX_POSSIBLE_BALLS_IN_LAST_FRAME) {
      // Should had earn extra ball
      return firstTwoBallsPins >= MAX_POSSIBLE_PINS_KNOCKED_BY_A_BALL;
    }
    return firstTwoBallsPins <= MAX_POSSIBLE_PINS_KNOCKED_BY_A_BALL;
  }
}
