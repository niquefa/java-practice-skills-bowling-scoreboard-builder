package com.rjc.view;

import com.rjc.entities.ScoreBoard;

public final class ViewerFactory {

  private ViewerFactory() {

  }

  public static Viewer getView(ScoreBoard scoreboard) {
    return new ConsoleView(scoreboard);
  }
}
