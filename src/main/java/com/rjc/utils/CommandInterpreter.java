package com.rjc.utils;

import java.io.FileNotFoundException;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import com.rjc.constants.Global;
import com.rjc.loggers.BLog;
import com.rjc.utils.datasources.DataRepository;
import com.rjc.utils.datasources.LocalFileRepository;

/**
 * Class to read the application command line parameters and interpret them
 *
 * @author niquefa
 */
public class CommandInterpreter {

  private static final CommandInterpreter INSTANCE = new CommandInterpreter();

  private Options options;

  private CommandInterpreter() {

    this.options = new Options();
    options.addOption("f", Global.FILE_NAME_OPTION, true,
        RJCUtils.getWorkingDirectoryMessage());

    options.addOption("help", Global.FILE_NAME_OPTION, true,
        "This app only reads data from file with the -\"f\" option.");
  }

  /**
   * The command line retriever for the singleton instance of the
   * CommandLineInterpreter
   *
   * @return
   */
  public static CommandInterpreter getInstance() {
    return INSTANCE;
  }

  /**
   * Runs the application
   *
   * @param args an array of String arguments to be parsed
   * @throws FileNotFoundException
   */
  public DataRepository run(String[] args) throws FileNotFoundException {

    CommandLine line = parseArguments(args);
    if (line.hasOption(Global.HELP_NAME_OPTION)) {
      printApplicationHelp();
      System.exit(0);
    } else if (line.hasOption(Global.FILE_NAME_OPTION)) {
      String fileName = line.getOptionValue(Global.FILE_NAME_OPTION);
      return new LocalFileRepository(fileName);
    }
    BLog.getLogger().error("The application must receive a file as argument to work. Try again.");
    printApplicationHelp();
    throw new FileNotFoundException("Data file not given.");

  }

  /**
   * Prints application help
   */
  public void printApplicationHelp() {
    new HelpFormatter().printHelp(Global.APPLICATION_NAME, getOptions(), true);
  }

  /**
   * Parses application arguments
   *
   * @param args application arguments
   * @return <code>CommandLine</code> which represents a list of application
   *         arguments.
   */
  private CommandLine parseArguments(String[] args) {

    Options commandLineOptions = getOptions();
    CommandLine line = null;
    CommandLineParser parser = new DefaultParser();

    try {
      line = parser.parse(commandLineOptions, args);
    } catch (ParseException ex) {
      BLog.getLogger().error("Failed to parse command line arguments");
      BLog.getLogger().error(ex.toString());
      printApplicationHelp();
      System.exit(1);
    }

    return line;
  }

  /**
   * Generates application command line options
   *
   * @return application <code>Options</code>
   */
  private Options getOptions() {
    return this.options;
  }
}