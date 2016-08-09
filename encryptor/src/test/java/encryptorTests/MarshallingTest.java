package encryptorTests;

import static encryptor.Marshalling.*;
import static encryptorTests.EncdecFileTest.*;

import org.apache.logging.log4j.core.net.Facility;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import encryptor.Configuration;
import encryptor.Enums;
import encryptor.Report;
import encryptor.ReportAbstract;
import encryptor.Reports;
import encryptor.Enums.Algo;
import encryptor.Enums.Family;
import encryptor.Enums.Goal;
import encryptor.Enums.Status;
import encryptor.Enums.Sync;
import encryptor.Enums.Type;

import static junit.framework.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOError;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class MarshallingTest {
	
	//@ClassRule
	//private static TemporaryFolder tempFolder = new TemporaryFolder();

	@Test
	public void testMarhsall() throws Exception{
		/*
		 * the test is done while doing the following two calls below:
		 * testReport();
		 * testExportXML();
		 */
	}
	
	@Test
	public void testReport() throws IOException{
		Report report1 = new Report();
		Report report2 = new Report();
		Report report3 = new Report();
		
		
		Exception exception = new FileNotFoundException("The required file was not found");
		/*
		Exception exception=null;
		try{
			new Scanner(new File(".\\adfdf")) ;
		}
		catch(Exception e){
			exception=e;
		}*/

		report1.setAll("file1", Status.FAILURE, null, exception.getClass().getName(), exception.getMessage(), exception.getStackTrace());
		report2.setAll("file2", Status.SUCCESS, (long)23298, null, null, null);
		report3.setAll("file3", Status.SUCCESS, (long)9438948, null, null, null);
		
		
		Reports reports = new Reports();
		reports.getReports().add(report1);
		reports.getReports().add(report2);
		reports.getReports().add(report3);
		
		//String XMLPath = tempFolder.getRoot()+"actualReport.xml";
		String actual = ".\\actualReport.xml";
		String expected = ".\\expectedReport.xml";
		
		report(actual,reports);
		
		//File file = tempFolder.newFile("expected.xml");
		
		
		assertTrue(filesEqual(actual,expected));
	}
	
	@Test
	public void testImportXML(){
		Configuration config = importXML(".\\expectedConfig.xml");
		assertTrue(config.getGoal()==Goal.DECRYPTION);
		assertTrue(config.getType()==Type.DIR);
		assertTrue(config.getSync()==Sync.SYNC);
		assertTrue(config.getFamily()==Family.DOUBLE);
		assertTrue(config.getFirst()==Algo.CAESAR);
		assertTrue(config.getSecond()==Algo.XOR);
	}
	
	@Test
	public void testExportXML(){
		Configuration config = new Configuration(Goal.DECRYPTION, Sync.SYNC, Type.DIR, Family.DOUBLE, Algo.CAESAR, Algo.XOR);
		String actual = ".\\actualConfig.xml";
		String expected = ".\\expectedConfig.xml";
		exportXML(actual, config);
		
		assertTrue(filesEqual(actual, expected));
	}

}
