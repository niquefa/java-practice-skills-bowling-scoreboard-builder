package com.rjc.exceptions;

@SuppressWarnings("serial")
public class WrongTokenCountInRawDataEntry extends RJCException {
  public WrongTokenCountInRawDataEntry(String errorMessage) {
    super(errorMessage);
  }
}