package com.rjc.entities;

import java.util.ArrayList;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * Represents the preprocessed input data, basically all the lines of the input
 *
 * @author niquefa
 */
@AllArgsConstructor
@Getter
@ToString
public class BowlingGame {
  private ArrayList<Ball> balls;
}
