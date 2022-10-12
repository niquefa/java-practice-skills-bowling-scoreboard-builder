package com.rjc.view;

import static com.rjc.validators.GameDataIntegrityValidator.MAX_POSSIBLE_BALLS_IN_LAST_FRAME;
import static com.rjc.validators.GameDataIntegrityValidator.MAX_POSSIBLE_FRAMES;
import static com.rjc.validators.GameDataIntegrityValidator.MAX_POSSIBLE_PINS_KNOCKED_BY_A_BALL;

import java.util.ArrayList;
import java.util.List;

import com.rjc.entities.BallResult;
import com.rjc.entities.FrameInfo;
import com.rjc.entities.Player;
import com.rjc.exceptions.UnforseenGameBuildingException;
import com.rjc.validators.GameDataIntegrityValidator;

/**
 * Utility methods and constants to build the console view of the scoreboard for
 * the bowling game in the input data
 *
 * @author niquefa
 *
 */

public final class ConsoleViewUtilities {

  private ConsoleViewUtilities() {
  }

  public static final String STRIKE = "X";

  private static final String PIN_FALLS_IDENTIFICATION = "Pinfalls";
  private static final String SCORE_IDENTIFICATION = "Score";
  private static final String FRAME_IDENTIFICATION = "Frame";

  private static final int MINIMAL_COLUMN_ZERO_WIDTH = Math.max(FRAME_IDENTIFICATION.length(),
      Math.max(PIN_FALLS_IDENTIFICATION.length(), SCORE_IDENTIFICATION.length()));
  private static final int MAX_WIDTH_IN_FRAME = 6;

  private static final String EMPTY_SPACE = " ";
  private static final String COLUMN_FIRST_SYMBOLS = " ";
  private static final String SPARE = "/";
  private static final String ZERO_BALLS = "0";
  private static final String FAUL = "F";

  /**
   * Build a String which is the graphic representation of the line in which the
   * frames scores are for a given player
   *
   * @param player
   * @param columnZeroWidth
   * @return
   */
  public static String buildScoreString(Player player, int columnZeroWidth) {

    StringBuilder sb = new StringBuilder(
        aliginLeft(SCORE_IDENTIFICATION, columnZeroWidth) + COLUMN_FIRST_SYMBOLS);
    ArrayList<FrameInfo> frameInfoList = player.getFrameInfoList();
    for (FrameInfo element : frameInfoList) {
      sb.append(
          aliginLeft(String.valueOf(element.getScoreUpToThisFrameInclusive()), MAX_WIDTH_IN_FRAME)
              + COLUMN_FIRST_SYMBOLS);
    }
    return sb.toString().trim();
  }

  /**
   * Returns an int which is the size the first column should have
   *
   * @param playersNames
   * @return
   */
  public static int getFirstColumnWidth(List<String> playersNames) {

    return Math.max(
        playersNames.stream().mapToInt(String::length).max().orElse(MINIMAL_COLUMN_ZERO_WIDTH),
        MINIMAL_COLUMN_ZERO_WIDTH);
  }

  /**
   * Build the first row of the input, basically the columns names and the word
   * Frame
   *
   * @param columnZeroWidth
   * @return
   */
  public static String buildFirstRow(int columnZeroWidth) {
    StringBuilder sb = new StringBuilder(
        aliginLeft(FRAME_IDENTIFICATION, columnZeroWidth) + COLUMN_FIRST_SYMBOLS);
    for (int i = 1; i <= MAX_POSSIBLE_FRAMES; i++) {
      sb.append(aliginLeft(String.valueOf(i), MAX_WIDTH_IN_FRAME) + COLUMN_FIRST_SYMBOLS);
    }
    return sb.toString();
  }

  /**
   * Build a line of input, the one where the pins/spares/strikes are shown
   *
   * @param player
   * @param firstColumnWidth
   * @return
   * @throws UnforseenGameBuildingException
   */
  public static String buildPinFallsForPlayer(Player player, int firstColumnWidth)
      throws UnforseenGameBuildingException {

    StringBuilder sb = new StringBuilder(aliginLeft(PIN_FALLS_IDENTIFICATION, firstColumnWidth));
    ArrayList<FrameInfo> frameInfoList = player.getFrameInfoList();
    for (FrameInfo element : frameInfoList) {
      sb.append(aliginLeft(buildPinFallsString(element), MAX_WIDTH_IN_FRAME));
    }
    return sb.toString();
  }

  private static String buildOtherThanLastFrameString(ArrayList<BallResult> balls,
      String playerName)
      throws UnforseenGameBuildingException {
    if (balls == null || balls.isEmpty() || balls.size() > MAX_POSSIBLE_BALLS_IN_LAST_FRAME - 1) {
      throw new UnforseenGameBuildingException(String.format(
          "Error: Other than Last frame with wrong number of ball for player %s. Balls: %s",
          playerName, balls));
    }

    String answer = COLUMN_FIRST_SYMBOLS;

    if (balls.get(0).getValue() == MAX_POSSIBLE_PINS_KNOCKED_BY_A_BALL) { // Strike in first ball
      answer += EMPTY_SPACE + EMPTY_SPACE + EMPTY_SPACE + STRIKE + EMPTY_SPACE + EMPTY_SPACE;
      return answer;
    } else {
      answer += getBallString(balls.get(0)) + EMPTY_SPACE + EMPTY_SPACE;
    }

    if (balls.size() == 1) {
      return answer;
    }

    if (balls.get(1).getValue() == MAX_POSSIBLE_PINS_KNOCKED_BY_A_BALL
        && balls.get(0).getValue() < 0) {
      throw new UnforseenGameBuildingException(String.format(
          "Error: Other than Last frame with strike as second ball and no 0 pins for first frame ball for player %s. Balls: %s",
          playerName, balls));
    } else if (balls.get(1).getValue() == 0) {
      answer += getBallString(balls.get(1)) + EMPTY_SPACE + EMPTY_SPACE;
    } else if (balls.get(0).getValue()
        + balls.get(1).getValue() == MAX_POSSIBLE_PINS_KNOCKED_BY_A_BALL) {
      answer += SPARE + EMPTY_SPACE + EMPTY_SPACE;
    } else {
      answer += getBallString(balls.get(1)) + EMPTY_SPACE + EMPTY_SPACE;
    }

    return answer;

  }

  private static String getBallString(BallResult ballResult) {
    if (ballResult.isFaul()) {
      return FAUL;
    }
    return ballResult.getValue() == 0 ? ZERO_BALLS : String.valueOf(ballResult.getValue());
  }

  private static String buildLastFrameString(ArrayList<BallResult> balls, String playerName)
      throws UnforseenGameBuildingException {

    if (balls == null || balls.isEmpty() || balls.size() > MAX_POSSIBLE_BALLS_IN_LAST_FRAME) {
      throw new UnforseenGameBuildingException(
          String.format("Error: Last frame with wrong number of ball for player %s. Balls: %s",
              playerName, balls));
    }

    String answer = COLUMN_FIRST_SYMBOLS;

    if (balls.get(0).getValue() == MAX_POSSIBLE_PINS_KNOCKED_BY_A_BALL) { // Strike in first ball
      answer += STRIKE + EMPTY_SPACE + EMPTY_SPACE;
    } else if (balls.get(0).getValue() == 0) {
      answer += getBallString(balls.get(0)) + EMPTY_SPACE + EMPTY_SPACE;
    }

    if (balls.size() < 2) {
      throw new UnforseenGameBuildingException(
          String.format("Error: Last frame with only one ball for player %s", playerName));
    }

    if (balls.get(1).getValue() == MAX_POSSIBLE_PINS_KNOCKED_BY_A_BALL) { // Strike in second ball
      answer += STRIKE + EMPTY_SPACE + EMPTY_SPACE;
    } else if (balls.get(1).getValue() == 0) {
      answer += ZERO_BALLS + EMPTY_SPACE + EMPTY_SPACE;
    } else if (balls.get(0).getValue()
        + balls.get(1).getValue() == MAX_POSSIBLE_PINS_KNOCKED_BY_A_BALL) {
      answer += SPARE + EMPTY_SPACE + EMPTY_SPACE;
    } else {
      answer += getBallString(balls.get(1)) + EMPTY_SPACE + EMPTY_SPACE;
    }

    if (balls.size() < 3) {
      return answer;
    }

    if (balls.get(2).getValue() == MAX_POSSIBLE_PINS_KNOCKED_BY_A_BALL) { // Strike in third ball
      answer += STRIKE + EMPTY_SPACE + EMPTY_SPACE;
    } else if (balls.get(2).getValue() == 0) {
      answer += getBallString(balls.get(2)) + EMPTY_SPACE + EMPTY_SPACE;
    } else if (balls.get(1).getValue()
        + balls.get(2).getValue() == MAX_POSSIBLE_PINS_KNOCKED_BY_A_BALL) {
      answer += SPARE + EMPTY_SPACE + EMPTY_SPACE;
    } else {
      answer += getBallString(balls.get(2)) + EMPTY_SPACE + EMPTY_SPACE;
    }

    return removeSpacesRight(answer);
  }

  private static String removeSpacesRight(String string) {
    return String.format("x%s", string).trim().substring(1);
  }

  private static boolean isLastFrame(FrameInfo frameInfo) {
    return frameInfo.getFrameIndex() == GameDataIntegrityValidator.MAX_POSSIBLE_FRAMES;
  }

  private static String aliginLeft(String string, int width) {

    StringBuilder sb = new StringBuilder(string);

    while (sb.length() < width) {
      sb.append(EMPTY_SPACE);
    }

    return sb.toString();

  }

  private static String buildPinFallsString(FrameInfo frameInfo)
      throws UnforseenGameBuildingException {

    ArrayList<BallResult> balls = frameInfo.getFrame().getBalls();

    if (balls == null) {
      throw new UnforseenGameBuildingException(
          String.format("Error: Frame with no null in balls list for player %s",
              frameInfo.getPlayerName()));
    }

    if (balls.size() > MAX_POSSIBLE_BALLS_IN_LAST_FRAME) {
      throw new UnforseenGameBuildingException(
          String.format("Error: Frame with to many balls (%d balls) list for player %s",
              balls.size(), frameInfo.getPlayerName()));
    }

    if (frameInfo.getFrameIndex() < MAX_POSSIBLE_FRAMES
        && balls.size() > MAX_POSSIBLE_BALLS_IN_LAST_FRAME - 1) {
      throw new UnforseenGameBuildingException(
          String.format("Error: Frame with to many balls (%d balls) for frame % list for player %s",
              balls.size(),
              frameInfo.getFrameIndex(), frameInfo.getPlayerName()));
    }

    if (balls.isEmpty()) {
      throw new UnforseenGameBuildingException(
          String.format("Error: Frame with zero balls in list for player %s",
              frameInfo.getPlayerName()));
    }
    // Last frame
    if (isLastFrame(frameInfo)) {

      return buildLastFrameString(balls, frameInfo.getPlayerName());
    }

    return buildOtherThanLastFrameString(balls, frameInfo.getPlayerName());
  }

}
