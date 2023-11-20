package org.example.collectors;

import java.util.*;
import java.util.function.*;
import java.util.stream.Collector;

public class CharCodeListToStringListCollector
    implements Collector<Integer, List<StringBuilder>, List<String>> {
  @Override
  public Supplier<List<StringBuilder>> supplier() {
    return ArrayList::new;
  }

  @Override
  public BiConsumer<List<StringBuilder>, Integer> accumulator() {
    return (list, codePoint) -> {
      if (list.isEmpty() || codePoint == '\n') {
        list.add(new StringBuilder());
      }

      if (codePoint != '\n') {
        list.get(list.size() - 1).appendCodePoint(codePoint);
      }
    };
  }

  @Override
  public BinaryOperator<List<StringBuilder>> combiner() {
    return (list1, list2) -> {
      list1.addAll(list2);
      return list1;
    };
  }

  @Override
  public Function<List<StringBuilder>, List<String>> finisher() {
    return list -> list.stream().map(StringBuilder::toString).toList();
  }

  @Override
  public Set<Characteristics> characteristics() {
    return Collections.emptySet();
  }
}
