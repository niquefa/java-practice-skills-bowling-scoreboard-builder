package com.rjc.main;

import java.io.IOException;

import com.rjc.controller.BowlingController;
import com.rjc.exceptions.RJCException;

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
   * @throws IOException
   * @throws RJCException
   */
  public static void main(String[] args) throws RJCException, IOException {
    new BowlingController().run(args);
  }
}
