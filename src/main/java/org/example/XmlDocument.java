package org.example;

import java.util.List;
import java.util.Map;


public class XmlDocument {

    private String tagName;

    private String value;

    private Map<String, String> attributes;

    private List<XmlDocument> childs;

    public XmlDocument() {
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Map<String, String> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, String> attributes) {
        this.attributes = attributes;
    }

    public List<XmlDocument> getChilds() {
        return childs;
    }

    public void setChilds(List<XmlDocument> childs) {
        this.childs = childs;
    }
}
