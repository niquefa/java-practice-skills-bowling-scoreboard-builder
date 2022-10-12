package src.test.java.validators;

import static com.rjc.constants.Global.NEGATIVE_TEST_FILES_DIRECTORY;
import static com.rjc.constants.Global.POSITIVE_TEST_FILES_DIRECTORY;
import static com.rjc.utils.GameUtilities.buildBowlingGame;
import static com.rjc.utils.RJCUtils.readFromFileLineByLine;
import static com.rjc.validators.GameDataIntegrityValidator.validateIfValidGame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import com.rjc.exceptions.RJCException;
import com.rjc.exceptions.ToFewBallsInPlayerException;
import com.rjc.exceptions.WrongNumberOfBallsInLastFrameException;

/**
 * @author niquefa
 *
 */

class GameDataIntegrityValidatorTest {

  @Test
  void testInvalidRepresentationOfAGame() throws IllegalArgumentException, RJCException {

    assertThrows(RJCException.class, () -> {
      validateIfValidGame(buildBowlingGame(
          readFromFileLineByLine(NEGATIVE_TEST_FILES_DIRECTORY + "big-score.txt")));
    });

    assertThrows(RJCException.class, () -> {
      validateIfValidGame(
          buildBowlingGame(readFromFileLineByLine(NEGATIVE_TEST_FILES_DIRECTORY + "empty.txt")));
    });

    assertThrows(RJCException.class, () -> {
      validateIfValidGame(buildBowlingGame(
          readFromFileLineByLine(NEGATIVE_TEST_FILES_DIRECTORY + "extra-score.txt")));
    });

    assertThrows(ToFewBallsInPlayerException.class, () -> {
      validateIfValidGame(buildBowlingGame(
          readFromFileLineByLine(
              NEGATIVE_TEST_FILES_DIRECTORY + "incomplete-one-player-1-balls.txt")));
    });

    assertThrows(ToFewBallsInPlayerException.class, () -> {
      validateIfValidGame(buildBowlingGame(
          readFromFileLineByLine(
              NEGATIVE_TEST_FILES_DIRECTORY + "incomplete-one-player-9-balls.txt")));
    });

    assertThrows(WrongNumberOfBallsInLastFrameException.class, () -> {
      validateIfValidGame(buildBowlingGame(readFromFileLineByLine(
          NEGATIVE_TEST_FILES_DIRECTORY
              + "incomplete-one-player-no-extra-ball-in-last-frame.txt")));
    });

    assertThrows(RJCException.class, () -> {
      validateIfValidGame(buildBowlingGame(readFromFileLineByLine(
          NEGATIVE_TEST_FILES_DIRECTORY
              + "incomplete-two-players-no-extra-ball-in-last-frame.txt")));
    });

    assertThrows(RJCException.class, () -> {
      validateIfValidGame(
          buildBowlingGame(
              readFromFileLineByLine(NEGATIVE_TEST_FILES_DIRECTORY + "invalid-score.txt")));
    });

    assertThrows(RJCException.class, () -> {
      validateIfValidGame(buildBowlingGame(
          readFromFileLineByLine(NEGATIVE_TEST_FILES_DIRECTORY + "many-balls.txt")));
    });

    assertThrows(RJCException.class, () -> {
      validateIfValidGame(buildBowlingGame(
          readFromFileLineByLine(NEGATIVE_TEST_FILES_DIRECTORY + "many-frames.txt")));
    });

    assertThrows(RJCException.class, () -> {
      validateIfValidGame(
          buildBowlingGame(readFromFileLineByLine(NEGATIVE_TEST_FILES_DIRECTORY + "negative.txt")));
    });

  }

  @Test
  void testValidRepresentationOfAGame() throws IllegalArgumentException, RJCException, IOException {
    validateIfValidGame(
        buildBowlingGame(
            readFromFileLineByLine(POSITIVE_TEST_FILES_DIRECTORY + "5-players-perfect-score.txt")));
    validateIfValidGame(
        buildBowlingGame(
            readFromFileLineByLine(POSITIVE_TEST_FILES_DIRECTORY + "5-players-with-spares.txt")));

    validateIfValidGame(
        buildBowlingGame(readFromFileLineByLine(
            POSITIVE_TEST_FILES_DIRECTORY + "bad-player-perfect-player.txt")));

    validateIfValidGame(buildBowlingGame(
        readFromFileLineByLine(
            POSITIVE_TEST_FILES_DIRECTORY + "bad-player-perfect-player-inverted.txt")));

    validateIfValidGame(
        buildBowlingGame(readFromFileLineByLine(POSITIVE_TEST_FILES_DIRECTORY + "long-name.txt")));

    validateIfValidGame(
        buildBowlingGame(
            readFromFileLineByLine(POSITIVE_TEST_FILES_DIRECTORY + "max-posible-balls.txt")));

    validateIfValidGame(
        buildBowlingGame(readFromFileLineByLine(POSITIVE_TEST_FILES_DIRECTORY + "perfect.txt")));

    validateIfValidGame(
        buildBowlingGame(readFromFileLineByLine(POSITIVE_TEST_FILES_DIRECTORY + "scores.txt")));

    validateIfValidGame(
        buildBowlingGame(
            readFromFileLineByLine(POSITIVE_TEST_FILES_DIRECTORY + "spare-in-frame-10.txt")));

  }

}
