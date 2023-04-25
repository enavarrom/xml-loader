package org.example;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

public class CustomXMLItemReader implements Reader<XmlDocument>{

    private XMLEventReader xmlEventReader;
    private String path;

    private List<String> discardTags;

    public CustomXMLItemReader(String path) throws FileNotFoundException, XMLStreamException {
        this.path = path;
        this.xmlEventReader = XMLInputFactory
            .newInstance()
            .createXMLEventReader(new FileInputStream(path));
        discardTags = new ArrayList<>();
    }

    public void addDiscardTags(String tagName) {
        discardTags.add(tagName);
    }

    @Override
    public XmlDocument read() {
        try {
            return getXmlDocument(xmlEventReader, null);
        } catch (XMLStreamException e) {
            throw new RuntimeException(e);
        }
    }

    private XmlDocument getXmlDocument(XMLEventReader xmlEventReader, XMLEvent xmlEvent) throws XMLStreamException {
        XmlDocument xmlDocument = null;
        while (xmlEventReader.hasNext()) {
            XMLEvent event = xmlEvent == null ? xmlEventReader.nextEvent() : xmlEvent;

            if (event.isStartDocument()) {
                continue;
            }

            if (event.isStartElement() && xmlDocument == null) {
                StartElement startElement = event.asStartElement();
                if (!discardTags.contains(startElement.getName().getLocalPart())) {
                    xmlDocument = new XmlDocument();
                    xmlDocument.setTagName(startElement.getName().getLocalPart());
                    Map<String, String> attributesValues = new HashMap<>();

                    startElement.getAttributes().forEachRemaining(action -> {
                        Attribute attribute = (Attribute) action;
                        attributesValues.put(attribute.getName().getLocalPart(), attribute.getValue());
                    });

                    if (!attributesValues.isEmpty()) {
                        xmlDocument.setAttributes(attributesValues);
                    }
                }
                xmlEvent = null;
                continue;
            }

            if (event.isStartElement() && xmlDocument != null) {
                addChildElement(xmlDocument, event, xmlEventReader);
                xmlEvent = null;
                continue;
            }

            if (event.isCharacters()) {
                if (xmlDocument != null) {
                    String value = event.asCharacters().getData();
                    if (value != null && !value.trim().isEmpty()) {
                        xmlDocument.setValue(value);
                    }
                }
                xmlEvent = null;
                continue;
            }

            if (event.isEndElement() && xmlDocument != null
                && xmlDocument.getTagName().equals(event.asEndElement().getName().getLocalPart())) {
                break;
            }

        }
        return xmlDocument;
    }



    private void addChildElement(XmlDocument xmlDocument, XMLEvent event, XMLEventReader xmlEventReader)
        throws XMLStreamException {
        if (event.isStartElement() && xmlDocument != null) {
            XmlDocument childDocument = getXmlDocument(xmlEventReader, event);
            if (xmlDocument.getChilds() == null) {
                xmlDocument.setChilds(new ArrayList<>());
            }
            xmlDocument.getChilds().add(childDocument);
        }
    }

}
