package com.rjc.main;

import com.rjc.controller.BowlingController;
import com.rjc.exceptions.RJCException;
import com.rjc.loggers.BLog;
import com.rjc.utils.CommandInterpreter;

/**
 * Starter class. Entry Point
 *
 * @author niquefa
 */

public class Main {
  /**
   * Application entry point.
   *
   * @param args application command line arguments
   * @throws RJCException
   */
  public static void main(String[] args) {
    try {
      new BowlingController().run(args);
    } catch (Exception e) {
      BLog.getLogger()
          .fatal("Something went wrong, check your input data and input arguments and try again.");
      CommandInterpreter.getInstance().printApplicationHelp();
    }
  }
}
