package com.rjc.service;

import java.util.List;

import com.rjc.builders.Builders;
import com.rjc.entities.BowlingGame;
import com.rjc.entities.ScoreBoard;
import com.rjc.exceptions.RJCException;
import com.rjc.utils.GameUtilities;
import com.rjc.utils.datasources.DataRepository;
import com.rjc.validators.GameDataIntegrityValidator;
import com.rjc.validators.RawDataValidator;
import com.rjc.view.Viewer;
import com.rjc.view.ViewerFactory;

public class BowlingService {

  private DataRepository dataRepository;

  public BowlingService(DataRepository dataRepository) {
    this.dataRepository = dataRepository;
  }

  /**
   * Runs the program
   *
   * @throws RJCException
   */
  public void process() throws RJCException {
    List<String> rawData = dataRepository.getData();
    RawDataValidator.validateRawData(rawData);
    BowlingGame game = GameUtilities.buildBowlingGame(rawData);
    GameDataIntegrityValidator.validateIfValidGame(game);

    ScoreBoard scoreBoard = Builders.buildScoreBoard(game);

    Viewer viewer = ViewerFactory.getView(scoreBoard);

    viewer.showScoreBoard();
  }

}
