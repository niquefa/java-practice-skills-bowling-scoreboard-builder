/**
 *
 */
package src.test.service.integration;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import com.rjc.constants.Global;
import com.rjc.controller.BowlingController;
import com.rjc.exceptions.RJCException;
import com.rjc.utils.RJCUtils;

/**
 * @author niquefa
 *
 */
class ServiceIntegrationTest {

  private BowlingController service = new BowlingController();

  @Test
  void testWhenTakeAnyPositiveInputFileThenNoExecutionIsThrown() throws IOException, RJCException {

    for (String fileName : RJCUtils.getFilesInDirectory(Global.POSITIVE_TEST_FILES_DIRECTORY)) {
      assertDoesNotThrow(() -> service
          .run(new String[] { "-f", Global.POSITIVE_TEST_FILES_DIRECTORY + fileName }));
    }
  }

  @Test
  void testWhenTakeAnyInvalidInputDataThenThrowsAnRJCException()
      throws IllegalArgumentException, RJCException {

    for (String fileName : RJCUtils.getFilesInDirectory(Global.NEGATIVE_TEST_FILES_DIRECTORY)) {
      assertThrows(RJCException.class,
          () -> service
              .run(new String[] { "-f", Global.NEGATIVE_TEST_FILES_DIRECTORY + fileName }));
    }
  }
}
