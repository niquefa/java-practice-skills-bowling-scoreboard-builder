package com.rjc.exceptions;

/**
 * For when there are one or more balls missing for one or more players
 *
 * @author niquefa
 *
 */
@SuppressWarnings("serial")
public class ToFewBallsInPlayerException extends RJCException {
  public ToFewBallsInPlayerException(String errorMessage) {
    super(errorMessage);
  }
}