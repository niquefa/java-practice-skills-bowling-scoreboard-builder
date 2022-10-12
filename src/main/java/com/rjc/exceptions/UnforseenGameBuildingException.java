package com.rjc.exceptions;

/**
 * For when something went wrong during the GameBuilding process
 *
 * @author niquefa
 *
 */
@SuppressWarnings("serial")
public class UnforseenGameBuildingException extends RJCException {

  public UnforseenGameBuildingException(String errorMessage) {
    super(errorMessage);
  }

}
