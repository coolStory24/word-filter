package org.example;

import static org.junit.jupiter.api.Assertions.*;

import java.io.*;
import org.junit.Before;
import org.junit.Rule;
import org.junit.jupiter.api.*;
import org.junit.rules.TemporaryFolder;

@DisplayName("Test main class")
class MainTest {
  private static final String INPUT_PATH = "input/";
  private static final String OUTPUT_PATH = "output/";

  File inputFile, bannedFile;

  @Rule public TemporaryFolder folder = new TemporaryFolder();

  @Before
  public void setUp() {
    try {
      folder.newFolder(INPUT_PATH);
      folder.newFolder(OUTPUT_PATH);

      inputFile = folder.newFile(INPUT_PATH + "in.txt");
      bannedFile = folder.newFile(INPUT_PATH + "banned.txt");
      bannedFile = folder.newFile(OUTPUT_PATH + "out.txt");

    } catch (IOException ioe) {
      System.err.println(
          "Error creating temporary test file in " + this.getClass().getSimpleName());
    }
  }
  @AfterEach
  public void cleanUp() {
    folder.delete();
  }

  @Test
  @DisplayName("Should filter words and write to file")
  void shouldFilterGivenWords() {
    try (var inputFileWriter = new FileWriter(INPUT_PATH + "in.txt");
        var bannedFileWriter = new FileWriter(INPUT_PATH + "banned.txt"); ) {

      inputFileWriter.write(
          """
          1. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
          2. Nulla facilisi. Mauris ac ex in turpis commodo aliquet.
          3. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Nunc ac sapien euismod, tristique ligula at, tempus elit.
          4. Quisque feugiat orci vel sagittis luctus.
          5. Duis id nisi nec odio convallis bibendum.
          6. Suspendisse vel velit nec nulla tincidunt malesuada.
          7. Proin dignissim metus eu arcu luctus, vel varius sapien feugiat.
          8. Sed eget erat eu sem fermentum gravida.
          9. Aenean in elit ac tellus gravida sagittis.
          10. Fusce in fermentum sapien. Curabitur quis ultrices lectus.
          11. Integer volutpat justo vitae orci feugiat, id euismod dolor ultricies.
          12. Vivamus nec turpis id lectus ullamcorper rhoncus.
          13. Aliquam euismod enim id dapibus consequat.
          14. Orange eu tellus vitae quam efficitur efficitur.
          15. Morbi auctor quam nec augue tristique, vel rhoncus nulla apple.""");

      bannedFileWriter.write("""
        elit
        morbi
        eu""");

      bannedFileWriter.close();
      inputFileWriter.close();
      Main.main(new String[] {"in.txt", "banned.txt", "out.txt"});
    } catch (IOException e) {
      fail("Cannot write test files", e);
    }

    try (var readResultFile = new FileReader(OUTPUT_PATH + "out.txt")) {
      char[] outputChars = new char[1000];
      readResultFile.read(outputChars);

      StringBuilder stringBuilder = new StringBuilder(1000);

      for (Character ch : outputChars) {
        if (ch == 0) break;
        stringBuilder.append(ch);
      }

      var outputString = stringBuilder.toString();

      assertEquals(
          """
        2. Nulla facilisi. Mauris ac ex in turpis commodo aliquet.
        5. Duis id nisi nec odio convallis bibendum.
        10. Fusce in fermentum sapien. Curabitur quis ultrices lectus.
        12. Vivamus nec turpis id lectus ullamcorper rhoncus.
        """
              .trim(),
          outputString.trim());

    } catch (IOException e) {
      fail("Cannot read test file", e);
    }
  }

  @Test
  @DisplayName("Should throw error when no file found")
  void throwErrorWhenFileNotFound() {
    Exception exception =
        assertThrows(
            FileNotFoundException.class,
            () -> {
              Main.main(new String[] {"i.txt", "banned.txt", "out.txt"});
            });

    assertEquals("File with path \"input/i.txt\" not found", exception.getMessage());
  }
}
