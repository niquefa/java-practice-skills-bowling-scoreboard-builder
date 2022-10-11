package com.rjc.exceptions;

@SuppressWarnings("serial")
public class ToFewBallsInPlayerException extends RJCException {
  public ToFewBallsInPlayerException(String errorMessage) {
    super(errorMessage);
  }
}