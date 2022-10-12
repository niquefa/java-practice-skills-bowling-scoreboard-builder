package com.rjc.view;

import com.rjc.entities.ScoreBoard;

/**
 * Viewer factory for the app. Manage the view for the scoreboard to use
 *
 * @author niquefa
 *
 */
public final class ViewerFactory {

  private ViewerFactory() {

  }

  public static Viewer getView(ScoreBoard scoreboard) {
    return new ConsoleView(scoreboard);
  }
}
