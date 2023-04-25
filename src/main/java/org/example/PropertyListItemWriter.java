package org.example;

import java.util.List;

public class PropertyListItemWriter implements Writer<PropertyList>{

    @Override
    public void write(List<PropertyList> outputs) {
        outputs.parallelStream()
            .forEach(propertyList
                -> (new ActionProcessor()).processAction("AddSDI", "1", propertyList));
    }
}
