package org.example;

import lombok.*;

import java.util.List;
import java.util.Map;
import lombok.ToString.Include;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
public class XmlDocument {

    @Include
    private String tagName;

    @Include
    private String value;

    private Map<String, String> attributes;

    @Include
    private List<XmlDocument> childs;
}
