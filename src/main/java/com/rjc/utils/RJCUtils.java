package com.rjc.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.rjc.loggers.BLog;

public final class RJCUtils {

  private RJCUtils() {

  }

  private static final String CURRENT_DIRECTORY_STRING_MESSAGE = "Current working directory : %s. Use relative path to that working directory.";

  /**
   * @return <code>String<code> message showing the working directory.
   */
  public static String getWorkingDirectoryMessage() {
    String workingDirectory = System.getProperty("user.dir").trim();
    return String.format(CURRENT_DIRECTORY_STRING_MESSAGE, workingDirectory);
  }

  /**
   * @param triedDataSource
   * @return <code>String<code> returns a string representing the error of data
   *         source not found.
   */
  public static String getNoDataSourceFileFoundMessage(String triedDataSource) {
    return String.format("Not data source found. Tried data source : %s", triedDataSource);
  }

  /**
   * Reads application data from a file. This method ignores blank line in the
   * File to read.
   *
   * @param fileName with path relative to the working directory
   * @return <code>ArrayList</code> of <code>String</code> values, the lines of
   *         the input file
   */
  public static List<String> readFromFileLineByLine(String fileName)
      throws IllegalArgumentException, IOException {

    if (fileName == null) {
      throw new IllegalArgumentException("Filename can not be null.");
    }
    if (!new File(fileName).exists()) {
      throw new IllegalArgumentException(String.format("File %s does not exists", fileName));
    }
    List<String> data = new ArrayList<>();

    try {
      Scanner sn = new Scanner(new File(fileName));
      while (sn.hasNextLine()) {
        String line = sn.nextLine();
        if (line.isEmpty()) {
          continue;
        }
        data.add(line);
      }
      sn.close();
    } catch (IOException ex) {

      BLog.getLogger()
          .error(String.format("%s Failed to read the file %s",
              com.rjc.constants.Global.APPLICATION_NAME, fileName));
      BLog.getLogger().error(ex.toString());
      System.exit(1);
    }

    return data;
  }

  /**
   * @param directory
   * @return a set with all the files in the given directory
   */
  public static Set<String> getFilesInDirectory(String directory) {
    return Stream.of(new File(directory).listFiles()).filter(file -> !file.isDirectory())
        .map(File::getName)
        .collect(Collectors.toSet());
  }
}
