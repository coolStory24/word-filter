package org.example;

import java.io.IOException;
import java.util.logging.Logger;
import org.example.collectors.StringListToCharListCollector;
import org.example.input_output.LineReader;
import org.example.input_output.LineWriter;
import org.example.word_filter.WordFilter;

public class Main {
  private static final Logger logger = Logger.getLogger(Main.class.getSimpleName());
  private static final int NUMBER_OF_ARGUMENTS = 3;
  private static final String INPUT_PATH = "input/";
  private static final String OUTPUT_PATH = "output/";

  public static void main(String[] args) throws IOException {
    if (args.length < NUMBER_OF_ARGUMENTS) {
      logger.warning(NUMBER_OF_ARGUMENTS + " arguments must be provided");
      return;
    }

    String inputFileName = args[0];
    String bannedWordsListFileName = args[1];
    String outputFileName = args[2];

    var lines = LineReader.readCharCodes(INPUT_PATH + inputFileName);
    var bannedWords = LineReader.readCharCodes(INPUT_PATH + bannedWordsListFileName);
    var wordFilter = new WordFilter(bannedWords);

    var filteredLines = lines.stream().filter(wordFilter::filter).toList();
    var filteredChars = filteredLines.stream().collect(new StringListToCharListCollector());
    LineWriter.writeToFile(OUTPUT_PATH + outputFileName, filteredChars);

    logger.info("Words successfully filtered");
  }
}