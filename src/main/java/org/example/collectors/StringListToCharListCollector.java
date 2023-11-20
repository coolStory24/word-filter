package org.example.collectors;

import java.util.*;
import java.util.function.*;
import java.util.stream.Collector;

public class StringListToCharListCollector
    implements Collector<String, List<Character>, List<Character>> {

  @Override
  public Supplier<List<Character>> supplier() {
    return ArrayList::new;
  }

  @Override
  public BiConsumer<List<Character>, String> accumulator() {
    return (charList, str) -> {
      for (char c : str.toCharArray()) {
        charList.add(c);
      }
      charList.add('\n'); // Add '\n' at the end of each string
    };
  }

  @Override
  public BinaryOperator<List<Character>> combiner() {
    return (list1, list2) -> {
      list1.addAll(list2);
      return list1;
    };
  }

  @Override
  public Function<List<Character>, List<Character>> finisher() {
    return Function.identity();
  }

  @Override
  public Set<Characteristics> characteristics() {
    return Collections.emptySet();
  }
}
