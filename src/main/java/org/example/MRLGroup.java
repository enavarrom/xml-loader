package org.example;

import java.util.Map;

public class MRLGroup {

    private Map<String, String> data;

    public MRLGroup(Map<String, String> data) {
        this.data = data;
    }

    public Map<String, String> getData() {
        return data;
    }

    public void setData(Map<String, String> data) {
        this.data = data;
    }
}
