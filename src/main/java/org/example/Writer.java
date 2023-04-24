package org.example;

import java.util.List;

public interface Writer<O>{
  void write(List<O> outputs);
}
