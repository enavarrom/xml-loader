package org.example;

import java.util.HashMap;
import java.util.Map;

public class PropertyList {

  private Map<String, String> properties;

  public PropertyList() {
    properties = new HashMap<>();
  }

  public void setProperty(String key, String value) {
    properties.put(key, value);
  }

  @Override
  public String toString() {
    return "PropertyList{" +
        "properties=" + properties +
        '}';
  }
}
