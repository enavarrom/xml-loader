package org.example;

import java.io.FileNotFoundException;
import javax.xml.stream.XMLStreamException;

public class Main {
    public static void main(String[] args) throws XMLStreamException, FileNotFoundException {
        String path = "/home/edernavarro/Projects/xml-loader/xml-loader-app/src/main/resources/example.xml";
        Reader<XmlDocument> reader = new CustomXMLItemReader(path);
        Processor<XmlDocument, PropertyList> processor = new PropertyListItemProcessor();
        Writer<PropertyList> writer = new PropertyListItemWriter();
        JobLoaderFile<XmlDocument, PropertyList> job = new JobLoaderFile<>();
        job.setJobName("jobMRL");
        job.setReader(reader);
        job.setProcessor(processor);
        job.setWriter(writer);
        job.setFetchSize(1);
        job.setChunkSize(2);
        job.launch();

    }
}