package com.lab7;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException, TransformerException {

        String inFile = args[0];
        String outFile = args[1];

        Student student = readStudent(inFile);
        double avr = student.getSubjects().stream().map(s ->  (double)s.getMark()).reduce(0.0, Double::sum)/student.getSubjects().size();
        if (student.getAverage() != avr) {
            System.out.println("average from xml is not right");
            student.setAverage(avr);
        }
        writeStudent(student, outFile);
    }

    public static Student readStudent(String filePath) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document document = db.parse(new File(filePath));

        Element element = document.getDocumentElement();
        NodeList subjects = element.getElementsByTagName("subject");
        String lastname = element.getAttribute("lastname");
        NodeList nl = element.getElementsByTagName("average");
        double avr = 0;
        if (nl.getLength() != 0) {
            avr = Double.parseDouble(nl.item(0).getTextContent());
        }

        List<Student.Subject> marks = new ArrayList<>();
        for(int i = 0; i < subjects.getLength(); i++) {
            Node node = subjects.item(i);
            NamedNodeMap attributes = node.getAttributes();
            int mark = Integer.parseInt(attributes.getNamedItem("mark").getNodeValue());
            String title = attributes.getNamedItem("title").getNodeValue();
            marks.add(new Student.Subject(mark, title));
        }
        return new Student(marks, avr, lastname);
    }

    public static void writeStudent(Student student, String filePath) throws ParserConfigurationException,
            IOException, TransformerException {

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

        Document doc = builder.newDocument();
        Element root = doc.createElement("student");
        root.setAttribute("lastname", student.getLastName());

        for (Student.Subject subject : student.getSubjects()) {
            Element s = doc.createElement("subject");
            s.setAttribute("title", subject.getTitle());
            s.setAttribute("mark", Integer.toString(subject.getMark()));
            root.appendChild(s);
        }

        Element avr = doc.createElement("average");
        avr.setTextContent(student.getAverage().toString());
        root.appendChild(avr);

        doc.appendChild(root);

        try (FileOutputStream fos = new FileOutputStream(filePath)) {
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, "student.dtd");
            DOMSource source = new DOMSource(doc);
            transformer.transform(source, new StreamResult(fos));
        }
    }
}
