package com.rjc.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Encapsulates all the information necessary for a frame in order to build a
 * <code>ScoreBoard<code>
 *
 * @author niquefa
 *
 */
@AllArgsConstructor
@Getter
public class FrameInfo {
  private int frameIndex;
  private String playerName;
  private Frame frame;
  private int scoreUpToThisFrameInclusive;
  private int frameScore;

  public void setScoreUpToThisFrameInclusive(int scoreUpToThisFrameInclusive) {
    this.scoreUpToThisFrameInclusive = scoreUpToThisFrameInclusive;
  }

  public void setFrameScore(int frameScore) {
    this.frameScore = frameScore;
  }
}
