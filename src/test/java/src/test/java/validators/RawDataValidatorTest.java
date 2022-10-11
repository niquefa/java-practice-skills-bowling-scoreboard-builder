/**
 *
 */
package src.test.java.validators;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.rjc.constants.Global;
import com.rjc.exceptions.EmptyDataSourceException;
import com.rjc.exceptions.RJCException;
import com.rjc.exceptions.WrongValueInSecondColumnException;
import com.rjc.utils.RJCUtils;
import com.rjc.validators.RawDataValidator;

/**
 * @author niquefa
 *
 */
class RawDataValidatorTest {

  @Test
  void testWhenInvalidParameterThenThrowsException() {
    assertThrows(IllegalArgumentException.class, () -> {
      RawDataValidator.validateRawData(null);
    });
    assertThrows(EmptyDataSourceException.class, () -> {
      RawDataValidator.validateRawData(new ArrayList<String>());
    });
  }

  @Test
  void testWhenRetrievingEmptyTxtFileThrowsEmptyDataSourceException()
      throws IllegalArgumentException, IOException, RJCException {
    List<String> data = RJCUtils
        .readFromFileLineByLine(Global.NEGATIVE_TEST_FILES_DIRECTORY + "empty.txt");
    assertThrows(EmptyDataSourceException.class, () -> {
      RawDataValidator.validateRawData(data);
    });
  }

  @Test
  void testWhenRetrievingNegativeTxtFileThrowsWrongValueInSecondColumnException()
      throws IllegalArgumentException, IOException, RJCException {
    List<String> data = RJCUtils
        .readFromFileLineByLine(Global.NEGATIVE_TEST_FILES_DIRECTORY + "negative.txt");
    assertThrows(WrongValueInSecondColumnException.class, () -> {
      RawDataValidator.validateRawData(data);
    });
  }

  @Test
  void testWhenRetrievingFreeTextTxtFileThrowsWrongTokenCountInRawDataEntry()
      throws IllegalArgumentException, IOException, RJCException {
    List<String> data = RJCUtils
        .readFromFileLineByLine(Global.NEGATIVE_TEST_FILES_DIRECTORY + "free-text.txt");
    assertThrows(Exception.class, () -> {
      RawDataValidator.validateRawData(data);
    });
  }

  @Test
  void testWhenRetrievingInvalidScoreTxtFileThrowsWrongValueInSecondColumnException()
      throws IllegalArgumentException, IOException, RJCException {
    List<String> data = RJCUtils
        .readFromFileLineByLine(Global.NEGATIVE_TEST_FILES_DIRECTORY + "invalid-score.txt");
    assertThrows(WrongValueInSecondColumnException.class, () -> {
      RawDataValidator.validateRawData(data);
    });
  }
}
