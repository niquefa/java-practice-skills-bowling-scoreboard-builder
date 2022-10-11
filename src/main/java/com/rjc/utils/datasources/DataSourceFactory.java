package com.rjc.utils.datasources;

public final class DataSourceFactory {

  private DataSourceFactory() {
  }

  public static DataRepository create() {
    return new LocalFileRepository();
  }

}
