package com.rjc.builders;

import static com.rjc.utils.GameUtilities.getFrameScore;
import static com.rjc.utils.GameUtilities.getFrames;
import static com.rjc.utils.GameUtilities.getPlayersGame;
import static com.rjc.utils.GameUtilities.getPlayersShootingOrder;
import static com.rjc.utils.GameUtilities.isSpare;
import static com.rjc.utils.GameUtilities.isStrike;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;

import com.rjc.entities.BallResult;
import com.rjc.entities.BowlingGame;
import com.rjc.entities.Frame;
import com.rjc.entities.FrameInfo;
import com.rjc.entities.Player;
import com.rjc.entities.ScoreBoard;

public final class Builders {

  private Builders() {
  }

  /**
   * Build the <code>ScoreBoard<code> entity from the <code>BowlingGame<code>
   * entity
   *
   * @param game
   * @return The <code>ScoreBoard<code> entity
   */
  public static ScoreBoard buildScoreBoard(BowlingGame game) {

    List<Player> players = new ArrayList<>();

    SortedMap<String, List<BallResult>> gameMapNameToPins = getPlayersGame(game);

    /*
     * Spare / : 10 points + next ball Strike X : When strike is 10 pins + two balls
     * Each frame has some points (frameScore), but in the view it shows the
     * accumulate (scoreUpToThisFrameInclusive)
     */

    for (String playerName : getPlayersShootingOrder(game)) {

      Player player = buildEmptyPlayer(playerName, getFrames(gameMapNameToPins.get(playerName)));
      for (FrameInfo frameInfo : player.getFrameInfoList()) {
        int frameIndividualScore = getFrameScore(frameInfo);
        frameInfo.setFrameScore(frameIndividualScore);
      }
      List<FrameInfo> frameInfoList = player.getFrameInfoList();

      // Set total frame score
      for (int i = 0; i < frameInfoList.size() - 1; ++i) {

        FrameInfo currentFrame = frameInfoList.get(i);
        if (isSpare(currentFrame)) {
          int pinsInNextBall = frameInfoList.get(i + 1).getFrame().getBalls().get(0).getValue();
          currentFrame.setFrameScore(currentFrame.getFrameScore() + pinsInNextBall);
        } else if (isStrike(currentFrame)) {
          int sumOfNextTwoBalls = getSumOfNextTwoBalls(frameInfoList, i + 1);
          currentFrame.setFrameScore(currentFrame.getFrameScore() + sumOfNextTwoBalls);
        }
      }

      // Set total score up to frame i
      FrameInfo firstFrame = frameInfoList.get(0);
      firstFrame.setScoreUpToThisFrameInclusive(firstFrame.getFrameScore());
      for (int i = 1; i < frameInfoList.size(); i++) {
        FrameInfo currentFrame = frameInfoList.get(i);
        FrameInfo previusFrame = frameInfoList.get(i - 1);
        currentFrame.setScoreUpToThisFrameInclusive(
            currentFrame.getFrameScore() + previusFrame.getScoreUpToThisFrameInclusive());
      }

      players.add(player);

    }
    return new ScoreBoard(players);
  }

  private static int getSumOfNextTwoBalls(List<FrameInfo> frameInfoList, int startingIndex) {
    ArrayList<BallResult> ballsList = new ArrayList<>();
    for (int i = startingIndex; i < frameInfoList.size() && ballsList.size() < 2; i++) {
      ballsList.addAll(frameInfoList.get(i).getFrame().getBalls());
    }
    return ballsList.get(0).getValue() + ballsList.get(1).getValue();
  }

  private static Player buildEmptyPlayer(String name, List<Frame> frames) {
    ArrayList<FrameInfo> frameInfoList = new ArrayList<>();
    int frameIndex = 1;
    for (Frame frame : frames) {
      frameInfoList.add(new FrameInfo(frameIndex++, name, frame, 0, 0));
    }
    return new Player(name, frameInfoList);
  }

}
