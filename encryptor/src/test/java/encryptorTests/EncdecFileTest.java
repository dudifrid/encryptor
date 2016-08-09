package encryptorTests;


import java.nio.file.Paths;
import java.io.InputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

import static org.mockito.Mockito.when;
import java.time.Instant;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalField;
import java.time.temporal.TemporalUnit;
import java.util.Arrays;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.*;

import org.mockito.internal.matchers.Any.*;

import encryptor.Encdec;
import encryptor.EncdecFile;
import encryptor.Enums;
import encryptor.Report;
import encryptor.ReportAbstract;

import javax.net.ssl.SSLEngineResult.Status;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.FileSystemNotFoundException;
import java.nio.file.Files;
import java.nio.file.ReadOnlyFileSystemException;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Clock;

import org.apache.logging.log4j.core.util.FileUtils;

import static encryptor.Encdec.*;
import static encryptor.Enums.*;
import static junit.framework.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import junit.framework.Assert;
import lombok.Generated;


public class EncdecFileTest {

	
	private static final int KB = 1000;
	private static final int MB = KB*KB;

	@ClassRule public static TemporaryFolder tempFolder = new TemporaryFolder();

	//@InjectMocks private static EncdecFile encdecFile; 

	//	@Mock private static Encdec encdec;
	//	@Mock private static Clock clock;
	//	@Mock private static ReportAbstract report;
	
	public static final String logName = "report.log";

	public Encdec encdec = mock(Encdec.class);
	public Clock clock = mock(Clock.class);
	public ReportAbstract report = mock(ReportAbstract.class);

	public File file1Mock = mock(File.class);
	public File file2Mock = mock(File.class);
	
	
	public PrintStream printStreamMock = mock(PrintStream.class);
	
	public InputStream inputStreamBadMock = mock(InputStream.class);
	public InputStream inputStreamGoodMock = mock(InputStream.class);
	

	//encdecFile =  new EncdecFile(null);

	@InjectMocks private EncdecFile encdecFile = new EncdecFile(encdec, report, clock); 

	
	Instant first; 
	Instant second;
	Instant third;
	Instant fourth;
	

	@Before
	public void setUp() throws Exception{

		//setup encdec 
		byte a = (byte) 0;
		byte b = (byte) 2;
		byte c = (byte) 10;
		byte d = (byte) 77;

		when(encdec.enc(a)).thenReturn(b);
		when(encdec.dec(b)).thenReturn(a);

		when(encdec.enc(c)).thenReturn(d);
		when(encdec.dec(d)).thenReturn(c);


		//setup clock
		first = Instant.now();
		second = first.plusSeconds(1);
		third = second.plusSeconds(1);
		fourth = third.plusSeconds(1);
		when(clock.instant()).thenReturn(first, second, third, fourth);



		//setup report
		//doNothing().when(report).setAll(anyString(), Enums.Status.SUCCESS, any(Long.class), null, null,null);
		doNothing().when(report).setAll(any(), any(), any(), any(), any(),any());


		encdecFile = new EncdecFile(encdec, report, clock);

		//doNothing().when(report).setAll(any(), any(), any(), any(), any(),any());

		//spying
		//when(spy(EncdecFile.class).getDestFILE(anyBoolean(), anyString())).thenReturn("");
		//doNothing().when(spy(EncdecFile.class)).encdecFile(anyBoolean(), anyString(), anyString());

		//when(printStreamBadMock.write(anyInt())).th
		
		
		
		//doThrow(new IOException()).when(printStreamBadMock).write(anyInt());
		doThrow(new IOException()).when(inputStreamBadMock).read();
		when(inputStreamBadMock.available()).thenReturn(3);

	}
	
	//important before each method. Otherwise, the order of the methods impacts the clock behavior, and thus, makes the code some unclear 
	public void resetClock(){
		//reset(clock);
		when(clock.instant()).thenReturn(first, second, third, fourth);
		
	}
	
	
	/*
	@Test
	public void testClockReset(){
		System.out.println("first =        "+first);
		System.out.println("Please notice: "+clock.instant());
		resetClock();
		System.out.println("Please notice: "+clock.instant());
	}*/
	
	public static void resetFile(String fileName) throws Exception{
		FileOutputStream writer = new FileOutputStream(fileName);
		writer.close();
	}
	
	public void resetClockAndLog() throws Exception{
		resetClock();
		resetFile(logName);
	}
	
	

	public static void createTempFile(int value, String fileName) throws Exception{

		final File file = tempFolder.newFile(fileName);
		PrintStream filePS = new PrintStream(file);

		byte[] inputBytes = new byte[KB];


		for (int i=0; i<KB; i++)
			inputBytes[i]=(byte)value;

		ByteArrayInputStream bt = new ByteArrayInputStream(inputBytes);

		//making 3MB files. The iterations were chosen to maximize efficiency
		for (int i=0;i<3; i++){
			filePS.write(value);
			//	filePS.flush();
		}


		filePS.close();

	}
/*
	ByteArrayOutputStream out = new ByteArrayOutputStream();
	ByteArrayOutputStream err = new ByteArrayOutputStream();

	
	@Before
	public void setSystemOut(){
		System.setOut(new PrintStream(out));
	}

	@Before
	public void setSystemErr(){
		System.setErr(new PrintStream(err));
	}
	 */

	
	
	public static String readContent(String fileName) throws Exception{
		return new String(Files.readAllBytes(Paths.get(fileName)), "UTF-8");
	}
	   
	public boolean contentsEqual(String cont1, String fileName) throws Exception{
		return cont1.equals(readContent(fileName));
	}
	public static String toFile(String fileName){
		return tempFolder.getRoot()+"\\"+fileName;
	}
	
	public static PrintStream createPrintStream(int size){
		return new PrintStream(new ByteArrayOutputStream(size));
	}
	
	public static InputStream createInputStream(int size, int value) throws Exception{
		PipedOutputStream out = new PipedOutputStream();
		InputStream in = new PipedInputStream(out);
		PrintStream ps = new PrintStream(out);
		
		
		byte[] bytes = createBytes(value, size);
		
		String input ="";
		for(int i = 0; i < bytes.length; i++){
			input += (char)bytes[i];
		}
	        
		
		
		ps.print(input);
//		ps.print((byte)-1);
		return in;
	}
	
	public static byte[] createBytes(int value, int size) {
		byte[] bytes = new byte[size];
		for (int i=0; i<size; i++)
			bytes[i]=(byte) value;
		//bytes[size-1]=(byte)-1;//to properly simulate file that ends with EOF
		return bytes;
	}
	
	public void assertLog(Instant instant) throws Exception{
		DateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
		String date = sdf.format(java.util.Date.from(instant));
		String myData = "["+date+"] Operation succeded. Time elapsed: 1000000000"+String.format("%n");
		
		assertTrue(contentsEqual(myData, logName));
	}
	
	
	
	@Test
	//test the case where encryption == true
	public void testEncdecFileStringArgsValid() throws Exception{
		//resetFile(logName);
		resetClockAndLog();
		createTempFile(10, "source2");
		createTempFile(77, "encrypted");
		tempFolder.newFile("target2");
		encdecFile.encdecFile(true, toFile("source2"), toFile("target2"));
		assertTrue(filesEqual(toFile("encrypted"), toFile("target2")));
		assertLog(first);
		
		
	}
	
	@Test
	//test the case where encryption == false
	public void testEncdecFileStringArgsValid2() throws Exception{
		resetClockAndLog();
		createTempFile(77, "source1");
		createTempFile(10, "decrypted");
		tempFolder.newFile("target1");
		encdecFile.encdecFile(false, toFile("source1"), toFile("target1"));
		assertTrue(filesEqual(toFile("decrypted"), toFile("target1")));
		assertLog(first);
	}
	
	@Test
	public void testEncdecFileStreamArgsValid() throws Exception{
		
		int size = 3;
		InputStream in = createInputStream(size, 10);
		ByteArrayOutputStream out = new ByteArrayOutputStream(size);
		PrintStream ps = new PrintStream(out);
		
		encdecFile.encdecFile(true, in, ps);
		
		assertTrue(Arrays.equals(out.toByteArray(), createBytes(77, 3)));
		assertTrue(in.available()==0);
		
	
	}
	
	
	@Test(expected=IOException.class)
	public void testEncdecFileStreamArgsInvalid() throws Exception{
		
		encdecFile.encdecFile(true, inputStreamBadMock, printStreamMock);
		
//		try{
//			encdecFile.encdecFile(true, inputStreamBadMock, printStreamMock);
//			assertTrue(false);
//		} catch(Exception e){}
		
	}
	
	
	
	@Test
	public void testEncdecDIR() throws Exception{
		//encryption==true
		assertDir(true, true, "dir1", "file1","file2");
		
		//encryption==false
		assertDir(true, false, "dir2", "file1","file2");
	
	}
	

	public static boolean filesEqual(String file1, String file2){
		try{
			FileInputStream f1 = new FileInputStream(file1);
			FileInputStream f2 = new FileInputStream(file2);
			int c,d;
			while ((c=f1.read())==(d=f2.read()) && c!=-1);
			f1.close();
			f2.close();
			return c==d;
		}
		catch (Exception e){
			if (e instanceof FileNotFoundException)
				return false;
			System.err.println("Error in checking file equality");
			System.exit(1);
		}

		return true;//unreachable
	}
	
	
	public void assertFile(boolean encryption, String sourceName, String targetName) throws Exception{
		tempFolder.newFile(sourceName);
		encdecFile.encdecFILE(encryption,toFile(sourceName));
		assertTrue(new File(toFile(targetName)).exists());
	}
	
	//public void assertDir(boolean encryption, String dirName, String[] oldFiles, String[] newFiles) throws Exception{
	public void assertDir(boolean async, boolean encryption, String dirName, String... fileNames) throws Exception{
		tempFolder.newFolder(dirName);
		for (String name: fileNames)
			tempFolder.newFile(dirName +"\\" + name);
			
		
		encdecFile.encdecDIR(async, encryption,toFile(dirName));

		String goal = encryption ? "encrypted" : "decrypted";
		String subdir = toFile(dirName) +"\\"+ goal;
		
		assertTrue(new File(toFile(dirName)).exists());
		assertTrue(new File(subdir).exists());
		for (String name: fileNames)
			assertTrue(new File(subdir +"\\"+ name).exists());
		
	}
	
	
	
	@Test
	public void testEncdecFILE() throws Exception{
		//encryption==true
		assertFile(true, "f2","f2.encrypted");
	
		//encryption==false
		assertFile(false, "f5.encrypted", "f5_decrypted.encrypted");
		
		//getDestFILE(true, "");
		//assertTrue(false);//test if System.exit is called

	}
}
