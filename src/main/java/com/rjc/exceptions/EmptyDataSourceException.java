package com.rjc.exceptions;

@SuppressWarnings("serial")
public class EmptyDataSourceException extends RJCException {
  public EmptyDataSourceException(String errorMessage) {
    super(errorMessage);
  }
}