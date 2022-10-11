package src.test.java.utils;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import com.rjc.entities.BallResult;
import com.rjc.utils.GameUtilities;

class GameUtilitiesTest {

  @Test
  void testMeetsMaxNumberOfTurnsWhenShouldReturnTrue() {

    assertFalse(GameUtilities
        .getFrames(ListOf(new int[] { 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 9, 0 })).isEmpty());

    assertFalse(GameUtilities
        .getFrames(ListOf(new int[] { 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 8, 1 })).isEmpty());

    assertFalse(
        GameUtilities
            .getFrames(ListOf(new int[] { 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10 }))
            .isEmpty());

    assertFalse(GameUtilities
        .getFrames(ListOf(new int[] { 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 0 })).isEmpty());

    assertFalse(GameUtilities
        .getFrames(ListOf(new int[] { 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 9 })).isEmpty());

    assertFalse(GameUtilities
        .getFrames(ListOf(new int[] { 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10 })).isEmpty());

    assertFalse(GameUtilities
        .getFrames(ListOf(new int[] { 10, 10, 10, 10, 10, 10, 10, 10, 10, 9, 1, 9 })).isEmpty());

    assertFalse(GameUtilities
        .getFrames(ListOf(new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }))
        .isEmpty());

    assertFalse(GameUtilities
        .getFrames(ListOf(new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 10 }))
        .isEmpty());

    assertFalse(GameUtilities
        .getFrames(ListOf(new int[] { 1, 9, 10, 10, 10, 10, 10, 10, 10, 10, 9, 1, 9 })).isEmpty());

    assertFalse(GameUtilities
        .getFrames(ListOf(new int[] { 10, 10, 10, 10, 10, 10, 10, 10, 1, 1, 1, 1 })).isEmpty());

    assertFalse(GameUtilities.getFrames(ListOf(new int[] { 10, 10 })).isEmpty());

    assertFalse(GameUtilities
        .getFrames(ListOf(new int[] { 1, 9, 2, 8, 10, 10, 10, 10, 10, 10, 10, 1, 9 })).isEmpty());

    assertFalse(GameUtilities
        .getFrames(ListOf(new int[] { 0, 9, 2, 8, 10, 10, 10, 10, 10, 10, 10, 0, 9 })).isEmpty());

    assertFalse(GameUtilities
        .getFrames(ListOf(new int[] { 10, 10, 10, 10, 10, 10, 10, 10, 10, 5, 5, 0 })).isEmpty());

    assertFalse(GameUtilities
        .getFrames(ListOf(new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 5, 5 }))
        .isEmpty());

    assertFalse(GameUtilities
        .getFrames(
            ListOf(new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 5, 5, 10 }))
        .isEmpty());

    assertFalse(GameUtilities
        .getFrames(ListOf(new int[] { 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1 }))
        .isEmpty());
  }

  @Test
  void testMeetsMaxNumberOfTurnsWhenShouldReturnFalse() {

    assertTrue(GameUtilities
        .getFrames(ListOf(new int[] { 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, -1 })).isEmpty());

    assertTrue(GameUtilities
        .getFrames(ListOf(new int[] { 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 11 })).isEmpty());

    assertTrue(GameUtilities
        .getFrames(ListOf(new int[] { 10, 10, 10, 10, 10, 10, 10, 10, 10, 3, 7, -1 })).isEmpty());

    assertTrue(GameUtilities
        .getFrames(ListOf(new int[] { 10, 10, 10, 10, 10, 10, 10, 10, 10, 3, 7, 11 })).isEmpty());

    assertTrue(GameUtilities
        .getFrames(ListOf(new int[] { 10, 10, 10, 10, 10, 10, 10, 10, 10, 1, 8, 10 })).isEmpty());

    assertTrue(GameUtilities
        .getFrames(ListOf(new int[] { 10, 10, 10, 10, 10, 10, 10, 10, 10, 0, 9, 0 })).isEmpty());

    assertTrue(GameUtilities
        .getFrames(ListOf(new int[] { 10, 10, 10, 10, 10, 10, 10, 10, 10, 9, 0, 9 })).isEmpty());

    assertTrue(GameUtilities
        .getFrames(ListOf(new int[] { 10, 10, 10, 10, 10, 10, 10, 10, 1, 1, 1, 1, 1 })).isEmpty());

    assertTrue(GameUtilities
        .getFrames(ListOf(new int[] { 10, 10, 10, 10, 10, 10, 10, 10, 10, 8, 1, 9 })).isEmpty());

    assertTrue(GameUtilities
        .getFrames(
            ListOf(new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }))
        .isEmpty());

    assertTrue(GameUtilities
        .getFrames(ListOf(new int[] { 10, 10, 1, 10, 10, 10, 10, 10, 10, 9, 1, 9 })).isEmpty());

    assertTrue(GameUtilities
        .getFrames(ListOf(new int[] { 1, 9, 2, 8, 10, 10, 10, 10, 10, 10, 10, 2, 9 })).isEmpty());

    assertTrue(GameUtilities
        .getFrames(ListOf(new int[] { 2, 9, 2, 8, 10, 10, 10, 10, 10, 10, 10, 0, 9 })).isEmpty());

  }

  private ArrayList<BallResult> ListOf(int a[]) {
    // TODO Check if this could be done in one line way to delete this method.
    ArrayList<BallResult> answer = new ArrayList<>();
    for (int x : a) {
      answer.add(new BallResult(false, x));
    }
    return answer;
  }

}
