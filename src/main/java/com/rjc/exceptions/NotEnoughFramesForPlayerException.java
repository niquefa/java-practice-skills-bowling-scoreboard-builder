package com.rjc.exceptions;

/**
 * For when there is frames missing form at least one player
 *
 * @author niquefa
 *
 */
@SuppressWarnings("serial")
public class NotEnoughFramesForPlayerException extends RJCException {
  public NotEnoughFramesForPlayerException(String errorMessage) {
    super(errorMessage);
  }
}