package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class JobLoaderFile<I, O> {

  private String jobName;

  private Reader<I> reader;

  private Processor<I, O> processor;

  private Writer<O> writer;

  private int fetchSize;

  private int chunkSize;

  public JobLoaderFile() {
  }

  public String getJobName() {
    return jobName;
  }

  public void setJobName(String jobName) {
    this.jobName = jobName;
  }

  public Reader<I> getReader() {
    return reader;
  }

  public void setReader(Reader<I> reader) {
    this.reader = reader;
  }

  public Processor<I, O> getProcessor() {
    return processor;
  }

  public void setProcessor(Processor<I, O> processor) {
    this.processor = processor;
  }

  public Writer<O> getWriter() {
    return writer;
  }

  public void setWriter(Writer<O> writer) {
    this.writer = writer;
  }

  public int getFetchSize() {
    return fetchSize;
  }

  public void setFetchSize(int fetchSize) {
    this.fetchSize = fetchSize;
  }

  public int getChunkSize() {
    return chunkSize;
  }

  public void setChunkSize(int chunkSize) {
    this.chunkSize = chunkSize;
  }

  public void launch() {
    List<I> itemsReaded = new ArrayList<>();
    List<I> itemsToWrite = new ArrayList<>();
    boolean finishJob = false;

    while (!finishJob) {

      while (itemsReaded.size() < fetchSize) {
        I item = reader.read();
        if (item != null) {
          itemsReaded.add(item);
        }
        else {
          finishJob = true;
          break;
        }
      }

      if (finishJob) {
        break;
      }

      if (itemsReaded.size() == fetchSize && fetchSize >= chunkSize) {
        write(itemsReaded);
      }

      if (itemsReaded.size() == fetchSize && fetchSize < chunkSize) {
        itemsToWrite.addAll(itemsReaded);
        itemsReaded.clear();
      }

      if (itemsToWrite.size() >= chunkSize) {
        write(itemsToWrite);
      }

    }

    if (itemsReaded.size() > 0) {
      write(itemsReaded);
    }

    if (itemsToWrite.size() > 0) {
      write(itemsToWrite);
    }

  }

  private void write(List<I> items) {
    if (!items.isEmpty()) {
      List<O> outputs = items.stream()
          .map(processor::process)
          .collect(Collectors.toList());

      if (outputs.size() > chunkSize) {
        AtomicInteger counter = new AtomicInteger();
        outputs.stream()
            .collect(Collectors.groupingBy(o -> counter.getAndIncrement() / chunkSize))
            .values()
            .parallelStream()
            .forEach(writer::write);
      }
      else {
        writer.write(outputs);
      }

      items.clear();
    }
  }


}
