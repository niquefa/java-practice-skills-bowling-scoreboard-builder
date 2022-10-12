package com.rjc.exceptions;

/**
 * For when the input contains a line with a number of tokens different to 2.
 * The token separator is in the App global constants
 *
 * @author niquefa
 *
 */

@SuppressWarnings("serial")
public class WrongTokenCountInRawDataEntry extends RJCException {
  public WrongTokenCountInRawDataEntry(String errorMessage) {
    super(errorMessage);
  }
}