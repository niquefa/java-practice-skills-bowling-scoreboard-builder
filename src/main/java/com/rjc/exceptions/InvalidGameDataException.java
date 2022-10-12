package com.rjc.exceptions;

/**
 * For when the Data is invalid, it does not represent a valid finished bowling
 * game
 *
 * @author niquefa
 *
 */
@SuppressWarnings("serial")
public class InvalidGameDataException extends RJCException {
  public InvalidGameDataException(String errorMessage) {
    super(errorMessage);
  }
}