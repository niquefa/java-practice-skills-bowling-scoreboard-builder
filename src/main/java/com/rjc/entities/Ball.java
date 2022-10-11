package com.rjc.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * Represent a Ball, as taken from the input, the name of a player, and what
 * ever goes after some space or tab.
 *
 * @author niquefa
 *
 */
@AllArgsConstructor
@Getter
@ToString
public class Ball {
  private String name;
  private String result;
}
