package com.rjc.exceptions;

@SuppressWarnings("serial")
public abstract class RJCException extends Exception {

  protected RJCException(String errorMessage) {
    super(errorMessage);
  }

}
