package org.example;

import java.util.List;

public class MRLGroupItemProcessor implements Processor<XmlDocument, MRLGroup>{
    @Override
    public MRLGroup process(XmlDocument input) {
        return MRLGroup.builder()
                .marketId(getValue("MarketID", input))
                .marketName(getValue("MarketName", input))
                .build();
    }

    private String getValue(String tagName, XmlDocument document) {
        if (!document.getChilds().isEmpty()) {
            return document.getChilds().stream()
                    .filter(xmlDocument -> xmlDocument.getTagName().equals(tagName))
                    .map(XmlDocument::getValue)
                    .findFirst()
                    .orElse(null);
        }

        return null;
    }

}
