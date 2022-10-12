package com.rjc.exceptions;

/**
 * for when there are to few balls in the last frame for at least one player
 *
 * @author niquefa
 *
 */
@SuppressWarnings("serial")
public class WrongNumberOfBallsInLastFrameException extends RJCException {
  public WrongNumberOfBallsInLastFrameException(String errorMessage) {
    super(errorMessage);
  }
}