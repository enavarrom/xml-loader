package org.example;

import java.util.HashMap;
import java.util.Map;

public class PropertyListItemProcessor implements Processor<XmlDocument, PropertyList>{

    private static final Map<String, String> COLUMNS_MAP;

    static {
        COLUMNS_MAP = new HashMap<>();
        COLUMNS_MAP.put("IndexCommodityID", "AAA");
        COLUMNS_MAP.put("MarketName", "BBB");
    }

    @Override
    public PropertyList process(XmlDocument input) {
        PropertyList propertyList = new PropertyList();
        propertyList.setProperty("AddSDI.PROPERTY_SDCID", "lmr");

        if (!input.getChilds().isEmpty()) {
            input.getChilds()
                .parallelStream()
                .filter(xmlDocument -> xmlDocument.getValue() != null)
                .forEach(xmlDocument -> {
                    String columnName = COLUMNS_MAP.get(xmlDocument.getTagName());
                    if (columnName != null) {
                        propertyList.setProperty(columnName, xmlDocument.getValue());
                    }
                });
        }

        return propertyList;
    }

}
