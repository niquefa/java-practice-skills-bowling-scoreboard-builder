package com.rjc.exceptions;

@SuppressWarnings("serial")
public class NotEnoughFramesForPlayerException extends RJCException {
  public NotEnoughFramesForPlayerException(String errorMessage) {
    super(errorMessage);
  }
}