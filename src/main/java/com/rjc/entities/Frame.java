package com.rjc.entities;

import java.util.ArrayList;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Representation of a Frame of a player, it contains between 1 and 3 elements
 * <code>BallResult<code> when the game is complete.
 *
 * @author niquefa
 *
 */
@AllArgsConstructor
@Getter
public class Frame {

  private ArrayList<BallResult> balls;

  public int getTotalPinsInTurn() {
    return balls.stream().map(BallResult::getValue).mapToInt(Integer::intValue).sum();
  }

}
