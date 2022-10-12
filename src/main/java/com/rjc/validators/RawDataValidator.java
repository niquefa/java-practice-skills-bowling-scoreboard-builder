package com.rjc.validators;

import java.util.List;

import com.rjc.constants.Global;
import com.rjc.exceptions.EmptyDataSourceException;
import com.rjc.exceptions.WrongTokenCountInRawDataEntry;
import com.rjc.exceptions.WrongValueInSecondColumnException;

/**
 * Class with utilities to validate the data the app will take as input, just
 * basic validations. This class do not validates data integrity or coherence.
 *
 * @author niquefa
 *
 */

public final class RawDataValidator {

  private RawDataValidator() {

  }

  /**
   * Check if all the raw entry data is valid. An entry is considered valid, if it
   * has exactly two tokens, and the second token is composed by only digits.
   *
   * @param rawData
   * @throws EmptyDataSourceException
   * @throws WrongValueInSecondColumnException
   */

  public static void validateRawData(List<String> rawData)
      throws WrongTokenCountInRawDataEntry, EmptyDataSourceException,
      WrongValueInSecondColumnException {
    validateTokenCount(rawData);
    validateSecondColumnAlwaysNonNegativeNumbers(rawData);
  }

  /**
   *
   * @param token
   * @return true when the given token is a valid pins or faul representation
   */
  public static boolean isOnlyDigitsOrFAndMeetsMaxPinLimit(String token) {
    if (token == null || token.length() == 0)
      return false;

    for (char c : token.toCharArray())
      if (!isDigitOrF(c))
        return false;

    if (token.trim().equalsIgnoreCase("F"))
      return true;

    int value = Integer.parseInt(token);
    return 0 <= value && value <= GameDataIntegrityValidator.MAX_POSSIBLE_PINS_KNOCKED_BY_A_BALL;
  }

  private static void validateSecondColumnAlwaysNonNegativeNumbers(List<String> rawData)
      throws WrongValueInSecondColumnException {
    if (!rawData.stream()
        .allMatch(entry -> isOnlyDigitsOrFAndMeetsMaxPinLimit(
            entry.trim().split(Global.VALID_TOKEN_SEPARATOR)[1]))) {
      throw new WrongValueInSecondColumnException(
          "Wrong value in second column of data retrieved, it must be a number beween 0 and 10 inclusive or F.");
    }
  }

  private static void validateTokenCount(List<String> rawData)
      throws WrongTokenCountInRawDataEntry, EmptyDataSourceException {
    if (rawData == null) {
      throw new IllegalArgumentException("List should not be null or emptys");
    }
    if (rawData.isEmpty()) {
      throw new EmptyDataSourceException(
          "There is no data retrieved in the data source; the data source was empty");
    }
    if (!rawData.stream().allMatch(RawDataValidator::hasExactlyTwoTokens))
      throw new WrongTokenCountInRawDataEntry(
          "Wrong token count in one or more entries. Each data entry must have two "
              + "tokens and the second token must be a non negative integer");
  }

  private static boolean hasExactlyTwoTokens(String rawEntry) {
    if (rawEntry == null || rawEntry.length() == 0)
      return false;

    String[] arr = rawEntry.trim().split(Global.VALID_TOKEN_SEPARATOR);

    return arr.length == 2;

  }

  private static boolean isDigitOrF(char x) {

    return '0' <= x && x <= '9' || x == 'f' || x == 'F';
  }
}
