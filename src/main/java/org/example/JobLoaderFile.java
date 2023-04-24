package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class JobLoaderFile<I, O> {

  private String jobName;

  private Reader<I> reader;

  private Processor<I, O> processor;

  private Writer<O> writer;

  private int fetchSize;

  private int chunkSize;

  public void launch() {
    List<I> items = new ArrayList<>();

    while (true) {
      I item = reader.read();

      if (item == null) {
        break;
      }

      items.add(item);

      if (items.size() == fetchSize) {
        AtomicInteger counter = new AtomicInteger();
        items.stream()
            .map(processor::process)
            .collect(Collectors.groupingBy(o -> counter.getAndIncrement() / chunkSize))
            .values()
            .forEach(writer::write);
        items.clear();
      }

    }

    if (items.size() > 0) {
      AtomicInteger counter = new AtomicInteger();
      items.stream()
              .map(processor::process)
              .collect(Collectors.groupingBy(o -> counter.getAndIncrement() / chunkSize))
              .values()
              .forEach(writer::write);
      items.clear();
    }
  }


}
