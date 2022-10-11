package com.rjc.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Encapsulates all the information necessary for a frame in order to build a
 * <code>ScoreBoard<code>
 *
 * @author niquefa
 *
 */
@AllArgsConstructor
@Getter
@Setter
public class FrameInfo {
  private int frameIndex;
  private String playerName;
  private Frame frame;
  private int scoreUpToThisFrameInclusive;
  private int frameScore;
}
