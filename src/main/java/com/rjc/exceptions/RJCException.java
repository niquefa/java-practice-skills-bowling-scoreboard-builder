package com.rjc.exceptions;

/**
 * For exception specific for this app
 *
 * @author niquefa
 *
 */

@SuppressWarnings("serial")
public abstract class RJCException extends Exception {

  protected RJCException(String errorMessage) {
    super(errorMessage);
  }

}
