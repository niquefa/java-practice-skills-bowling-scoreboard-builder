package com.rjc.view;

import com.rjc.entities.ScoreBoard;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@Setter
@ToString
public abstract class Viewer {

  private ScoreBoard scoreboard;

  public abstract void showScoreBoard();
}
