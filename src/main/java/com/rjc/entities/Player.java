package com.rjc.entities;

import java.util.ArrayList;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Represents all the information of a player necessary to build a
 * <code>ScoreBoard<code>
 *
 * @author niquefa
 *
 */

@AllArgsConstructor
@Getter
public class Player {
  private String name;
  private ArrayList<FrameInfo> frameInfoList;

}
