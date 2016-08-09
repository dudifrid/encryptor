package encryptor;

import java.io.File;
import javax.xml.bind.annotation.*;

import org.mockito.cglib.core.ClassEmitter;

import encryptor.Report;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import java.util.ArrayList;

@XmlSeeAlso({Reports.class})
public class Marshalling {
	
	public static void report(String XMLPath, Reports reports){
		marshall(XMLPath, reports, Reports.class, Report.class);
		/*
		try {
			
			File xmlfile = new File(XMLPath);
			
			JAXBContext jaxbContext = JAXBContext.newInstance(classes);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			jaxbMarshaller.marshal(reports, xmlfile);
			

		} catch (JAXBException e) {
			e.printStackTrace();
		}*/
	}
	
	public static Configuration importXML(String XMLPath){
		Configuration config =null;
		try {

			File xmlFile = new File(XMLPath);
			JAXBContext jaxbContext = JAXBContext.newInstance(Configuration.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			config = (Configuration) jaxbUnmarshaller.unmarshal(xmlFile);

		} catch (JAXBException e) {
			System.err.println("import xml failed");
		}
		return config;

	}
	
	public static void exportXML(String XMLPath, Configuration config){
		marshall(XMLPath, config, Configuration.class);
		/*
		try {

			File xmlfile = new File(XMLPath);
			JAXBContext jaxbContext = JAXBContext.newInstance(Configuration.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			jaxbMarshaller.marshal(config, xmlfile);

		} catch (JAXBException e) {
			e.printStackTrace();
		}*/
	}
	
	public static void marshall(String XMLPath, Object obj, Class... classes){
		try {
			
			File xmlfile = new File(XMLPath);
			JAXBContext jaxbContext = JAXBContext.newInstance(classes);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			jaxbMarshaller.marshal(obj, xmlfile);
			

		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}
	
	
		
}
