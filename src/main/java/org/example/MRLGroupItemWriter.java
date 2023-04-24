package org.example;

import java.util.List;

public class MRLGroupItemWriter implements Writer<MRLGroup>{
    @Override
    public void write(List<MRLGroup> outputs) {
        outputs.forEach(mrlGroup -> System.out.println(mrlGroup));
    }
}
