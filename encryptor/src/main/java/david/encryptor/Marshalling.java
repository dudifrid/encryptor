package david.encryptor;

import java.io.File;
import javax.xml.bind.annotation.*;

import david.encryptor.Report;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import java.util.ArrayList;

@XmlSeeAlso({Reports.class})
public class Marshalling {
	
	public static void report(String XMLPath, Reports reports){
		try {
			
			File xmlfile = new File(XMLPath);
			Class[] classes = {Reports.class, Report.class};
			JAXBContext jaxbContext = JAXBContext.newInstance(classes);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			jaxbMarshaller.marshal(reports, xmlfile);
			

		} catch (JAXBException e) {
			e.printStackTrace();
		}
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
		try {

			File xmlfile = new File(XMLPath);
			JAXBContext jaxbContext = JAXBContext.newInstance(Configuration.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			jaxbMarshaller.marshal(config, xmlfile);

		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}
		
}
