package org.example;

import java.util.Map;
import java.util.stream.Collectors;

public class MRLGroupItemProcessor implements Processor<XmlDocument, MRLGroup>{

    @Override
    public MRLGroup process(XmlDocument input) {
        return new MRLGroup(getData(input));
    }

    private Map<String, String> getData(XmlDocument document) {
        if (!document.getChilds().isEmpty()) {
            return document.getChilds().stream()
                .filter(xmlDocument -> xmlDocument.getValue() != null)
                .collect(Collectors.toMap(XmlDocument::getTagName, XmlDocument::getValue));
        }
        return null;
    }

}
