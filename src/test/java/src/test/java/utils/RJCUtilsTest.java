/**
 *
 */
package src.test.java.utils;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

import com.rjc.utils.RJCUtils;

/**
 * @author niquefa
 *
 */
class RJCUtilsTest {

  @Test
  void testWhenCallingWithNullThenThrowsIllegalArgumentException() {

    assertThrows(IllegalArgumentException.class, () -> {
      RJCUtils.readFromFileLineByLine(null);
    });

  }

  @Test
  void testWhenTryingToReadFromInexistentFileThenThrowsIllegalArgumentException() {

    assertThrows(IllegalArgumentException.class, () -> {
      RJCUtils.readFromFileLineByLine("SomeRandomFileNameThatDoNotExists.abc");
    });

  }

  @Test
  void testWhenCallingGetWorkingDirectoryMessageThenGetValidMessage() {

    String obtainedMessage = RJCUtils.getWorkingDirectoryMessage();
    assertTrue(StringUtils.containsIgnoreCase(obtainedMessage, "working directory"));

  }

  @Test
  void testWhenCallingGetNoDataSourceFileFoundMessageThenErrorMessage() {

    String obtainedMessage = RJCUtils
        .getNoDataSourceFileFoundMessage("SomeRandomDataSourceName.abc");
    assertTrue(StringUtils.containsIgnoreCase(obtainedMessage, "Not data source found"));

  }

}
