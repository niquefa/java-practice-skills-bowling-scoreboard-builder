package com.rjc.exceptions;

@SuppressWarnings("serial")
public class WrongValueInSecondColumnException extends RJCException {
  public WrongValueInSecondColumnException(String errorMessage) {
    super(errorMessage);
  }
}