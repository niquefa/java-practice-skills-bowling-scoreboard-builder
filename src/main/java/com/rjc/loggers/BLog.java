package com.rjc.loggers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * App logger
 *
 * @author niquefa
 *
 */
public final class BLog {
  private static final Logger instance = LogManager.getRootLogger();

  private BLog() {
  }

  public static Logger getLogger() {

    return instance;
  }
}
