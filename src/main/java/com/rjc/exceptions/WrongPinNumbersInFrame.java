package com.rjc.exceptions;

/**
 * For when the second column in the input data contains an invalid value.
 *
 * @author niquefa
 *
 */
@SuppressWarnings("serial")
public class WrongPinNumbersInFrame extends RJCException {
  public WrongPinNumbersInFrame(String errorMessage) {
    super(errorMessage);
  }
}