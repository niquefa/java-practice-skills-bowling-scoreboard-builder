package com.rjc.exceptions;

@SuppressWarnings("serial")
public class InvalidGameDataException extends RJCException {
  public InvalidGameDataException(String errorMessage) {
    super(errorMessage);
  }
}