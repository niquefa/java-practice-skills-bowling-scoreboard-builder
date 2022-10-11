package com.rjc.view;

import static com.rjc.view.ConsoleViewUtilities.buildFirstRow;
import static com.rjc.view.ConsoleViewUtilities.buildPinFallsForPlayer;
import static com.rjc.view.ConsoleViewUtilities.buildScoreString;
import static com.rjc.view.ConsoleViewUtilities.getFirstColumnWidth;

import java.nio.CharBuffer;
import java.util.List;
import java.util.stream.Collectors;

import com.rjc.entities.Player;
import com.rjc.entities.ScoreBoard;
import com.rjc.loggers.BLog;

public class ConsoleView extends Viewer {

  private static final int RECOMENDED_SCREEN_WIDTH = 80;

  public ConsoleView(ScoreBoard scoreBoard) {
    super(scoreBoard);
  }

  /**
   * Prints the builded <code>ScoreBoard<code> to the standard output.
   */
  @Override
  public void showScoreBoard() {

    System.out.println();
    System.out.println(
        CharBuffer.allocate(ConsoleView.RECOMENDED_SCREEN_WIDTH).toString().replace('\0', '_'));
    System.out.println();
    System.out.println(getTxtScoreBoard());
    System.out.println();
  }

  private String getTxtScoreBoard() {
    StringBuilder txtGameBoard = new StringBuilder();
    List<String> playersNames = super.getScoreboard().getPlayers().stream().map(Player::getName)
        .collect(Collectors.toList());
    int firstColumnWidth = getFirstColumnWidth(playersNames);

    txtGameBoard.append(buildFirstRow(firstColumnWidth) + "\n");
    for (Player player : super.getScoreboard().getPlayers()) {
      txtGameBoard.append(player.getName() + "\n");
      try {
        txtGameBoard.append(buildPinFallsForPlayer(player, firstColumnWidth) + "\n");
        txtGameBoard.append(buildScoreString(player, firstColumnWidth) + "\n");

      } catch (Exception e) {
        BLog.getLogger().error("ERROR: Unforseen behaivior");
        e.printStackTrace();
      }

    }

    return txtGameBoard.toString();
  }

}
