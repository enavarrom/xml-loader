package org.example;

public interface Processor <I,O> {
  O process(I input);
}
