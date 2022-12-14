package com.rjc.controller;

import java.io.IOException;

import com.rjc.exceptions.RJCException;
import com.rjc.service.BowlingService;
import com.rjc.utils.CommandInterpreter;
import com.rjc.utils.datasources.DataRepository;

/**
 *
 * The App controller that should be the entry point for the integration tests
 *
 * @author niquefa
 *
 */

public class BowlingController {

  public void run(String[] args) throws IOException, RJCException {
    DataRepository dataRepository = CommandInterpreter.getInstance().run(args);
    dataRepository.create();
    new BowlingService(dataRepository).process();
  }

}
