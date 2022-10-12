package com.rjc.exceptions;

/**
 * For when the data source contains no data
 *
 * @author niquefa
 *
 */

@SuppressWarnings("serial")
public class EmptyDataSourceException extends RJCException {
  public EmptyDataSourceException(String errorMessage) {
    super(errorMessage);
  }
}