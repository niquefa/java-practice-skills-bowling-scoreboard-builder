package com.rjc.utils.datasources;

/**
 * The factory for the app's data repository
 *
 * @author niquefa
 *
 */

public final class DataSourceFactory {

  private DataSourceFactory() {
  }

  public static DataRepository create() {
    return new LocalFileRepository();
  }

}
