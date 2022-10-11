package com.rjc.entities;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * All the information of the game
 *
 * @author niquefa
 */
@AllArgsConstructor
@Getter
public class ScoreBoard {
  private List<Player> players;
}
