package org.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MRLGroupItemWriter implements Writer<MRLGroup>{

    private static final Map<String, String> COLUMNS_MAP;

    private static final String DELIMITER = ",";

    static {
        COLUMNS_MAP = new HashMap<>();
        COLUMNS_MAP.put("IndexCommodityID", "AAA");
        COLUMNS_MAP.put("MarketName", "BBB");
    }
    @Override
    public void write(List<MRLGroup> outputs) {
        PropertyList propertyList = new PropertyList();
        propertyList.setProperty("AddSDI.PROPERTY_SDCID", "lmr");
        propertyList.setProperty("AddSDI.PROPERTY_COPIES", String.valueOf("this.numberRowsAdded"));

        COLUMNS_MAP.forEach((xmlName, columnName) -> {
            List<String> values = new ArrayList<>();

            outputs.parallelStream().forEach(mrlGroup -> {
                mrlGroup.getData().forEach((key, value) -> {
                    if (key.equals(xmlName)) {
                        values.add(value);
                    }
                });
            });

            propertyList.setProperty(columnName, String.join(DELIMITER, values));
        });

        (new ActionProcessor()).processAction("AddSDI", "1", propertyList);
    }
}
