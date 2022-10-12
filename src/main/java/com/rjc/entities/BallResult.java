package com.rjc.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * Represent the information of a ball, the pins knocked down and a flag that is
 * true when the value is 0 and was a foul
 *
 * @author niquefa
 */
@AllArgsConstructor
@Getter
@ToString
public class BallResult {
  private boolean isFaul;
  private int value;
}