package org.example;

import javax.xml.stream.XMLStreamException;
import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) throws XMLStreamException, FileNotFoundException {
        String path = "/home/edernavarro/Projects/xml-loader/xml-loader-app/src/main/resources/example.xml";
        Reader<XmlDocument> reader = new CustomXMLItemReader(path);
        Processor<XmlDocument, MRLGroup> processor = new MRLGroupItemProcessor();
        Writer<MRLGroup> writer = new MRLGroupItemWriter();

        JobLoaderFile<XmlDocument, MRLGroup> job
                = new JobLoaderFile<>("jobMRL", reader, processor,
                writer, 10, 1);

        job.launch();

        //System.out.println("Hello world!");
    }
}