package com.rjc.entities;

import java.util.Objects;

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
public class BallResult implements Comparable<BallResult> {
  private boolean isFaul;
  private int value;

  @Override
  public int compareTo(BallResult o) {

    if (this.isFaul == o.isFaul && this.value == o.value) {
      return 0;
    }
    if (this.value < o.value)
      return -1;
    if (o.value < this.value)
      return 1;
    return this.isFaul ? -1 : 1;
  }

  @Override
  public int hashCode() {
    return Objects.hash(isFaul, value);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if ((obj == null) || (getClass() != obj.getClass()))
      return false;
    BallResult other = (BallResult) obj;
    return isFaul == other.isFaul && value == other.value;
  }

}
