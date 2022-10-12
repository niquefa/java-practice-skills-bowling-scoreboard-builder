package com.rjc.view;

import com.rjc.entities.ScoreBoard;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 *
 * Abstract viewer class
 *
 * @author niquefa
 *
 */

@AllArgsConstructor
@Getter
public abstract class Viewer {

  private ScoreBoard scoreboard;

  public abstract void showScoreBoard();
}
