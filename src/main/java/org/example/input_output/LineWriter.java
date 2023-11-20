package org.example.input_output;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class LineWriter {
  public static void writeToFile(String path, List<Character> charList) throws IOException {
    try (FileWriter writer = new FileWriter(path)) {

      for (char character : charList) {
        writer.write(character);
      }

      writer.flush();
    }
  }
}
