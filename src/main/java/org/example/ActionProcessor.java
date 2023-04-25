package org.example;

public class ActionProcessor {

  public void processAction(String actionId, String versionId, PropertyList propertyList) {
    System.out.println(String.join(actionId, versionId, propertyList.toString()));
  }

}
