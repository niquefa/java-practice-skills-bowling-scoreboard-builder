package com.rjc.exceptions;

@SuppressWarnings("serial")
public class WrongNumberOfBallsInLastFrameException extends RJCException {
  public WrongNumberOfBallsInLastFrameException(String errorMessage) {
    super(errorMessage);
  }
}