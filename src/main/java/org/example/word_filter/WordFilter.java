package org.example.word_filter;

import java.util.List;

public class WordFilter {
  private final List<String> bannedWords;

  public WordFilter(List<String> bannedWords) {
    this.bannedWords = bannedWords;
  }

  public boolean filter(String line) {
    return bannedWords.stream().noneMatch(word -> hasBannedWord(line, word));
  }

  private boolean hasBannedWord(String line, String word) {
    return line.toLowerCase().contains(word.toLowerCase());
  }
}
