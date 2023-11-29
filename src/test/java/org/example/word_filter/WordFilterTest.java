package org.example.word_filter;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Test word filter class")
class WordFilterTest {

  @Test
  @DisplayName("Should return false if line has banned words")
  void shouldFalseIfAnyBannedWords() {
    var stringToFilter =
        "Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae.";
    var wordFilter = new WordFilter(List.of("ante", "ipsum"));

    assertFalse(wordFilter.filter(stringToFilter));
  }

  @Test
  @DisplayName("Should return true if line has no banned words")
  void shouldTrueIfNoBannedWords() {
    var stringToFilter =
        "Vestibulum primis in faucibus orci luctus et ultrices posuere cubilia Curae.";
    var wordFilter = new WordFilter(List.of("ante", "ipsum"));

    assertTrue(wordFilter.filter(stringToFilter));
  }
}
