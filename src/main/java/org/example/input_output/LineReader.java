package org.example.input_output;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import org.example.collectors.CharCodeListToStringListCollector;

public class LineReader {
  public static List<String> readCharCodes(String path) throws FileNotFoundException {
    try (FileReader fileReader = new FileReader(path)) {
      List<Integer> charCodeList = new ArrayList<>();
      int charCode;

      while ((charCode = fileReader.read()) != -1) {
        charCodeList.add(charCode);
      }

      return charCodeList.stream().collect(new CharCodeListToStringListCollector());
    } catch (IOException e) {
      throw new FileNotFoundException(String.format("File with path \"%s\" not found", path));
    }
  }
}
