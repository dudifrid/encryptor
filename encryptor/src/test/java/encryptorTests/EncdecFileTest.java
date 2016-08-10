package encryptorTests;


import java.nio.file.Paths;
import java.io.InputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.time.Instant;
import java.util.Arrays;
import static org.mockito.Mockito.*;
import encryptor.Encdec;
import encryptor.EncdecFile;
import encryptor.EncdecState;
import encryptor.EncdecState.State;
import encryptor.ReportAbstract;
import encryptor.StateObserver;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Clock;
import static junit.framework.Assert.*;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.mockito.InjectMocks;


public class EncdecFileTest {

	@ClassRule public static TemporaryFolder tempFolder = new TemporaryFolder();
	
	public static final String logName = "report.log";
	public StateObserver observer;

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
		
		reset(clock);
		when(clock.instant()).thenReturn(first, second, third, fourth);
		
		
		resetFile(logName);
		
		

		//setup report
		//doNothing().when(report).setAll(anyString(), Enums.Status.SUCCESS, any(Long.class), null, null,null);
		//doNothing().when(report).setAll(any(), any(), any(), any(), any(),any());

		
		
		
		observer = new StateObserver(State.BEFORE, State.BEFORE);
		EncdecState state = new EncdecState();
		state.addObserver(observer);
		//encdecFile.setState(state);
		


		//spying
		//when(spy(EncdecFile.class).getDestFILE(anyBoolean(), anyString())).thenReturn("");
		//doNothing().when(spy(EncdecFile.class)).encdecFile(anyBoolean(), anyString(), anyString());

		
		
		
		doThrow(new IOException()).when(inputStreamBadMock).read();
		when(inputStreamBadMock.available()).thenReturn(3);
		
		
		
		encdecFile = new EncdecFile(encdec, report, clock);
		
		encdecFile.setState(state);
		encdecFile.setClock(clock);
		
		
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ByteArrayOutputStream err = new ByteArrayOutputStream();
		
		System.setOut(new PrintStream(out));
		System.setErr(new PrintStream(err));
		
	}

	
	
	public static void resetFile(String fileName) throws Exception{
		FileOutputStream writer = new FileOutputStream(fileName);
		writer.close();
	}
	
	

	public static void createTempFile(int value, String fileName) throws Exception{

		final File file = tempFolder.newFile(fileName);
		PrintStream filePS = new PrintStream(file);

		for (int i=0;i<3; i++)
			filePS.write(value);

		filePS.close();
	}

		
	
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
		for(int i = 0; i < bytes.length; i++)
			input += (char)bytes[i];
	        
		ps.print(input);
		
		return in;
	}
	
	public static byte[] createBytes(int value, int size) {
		byte[] bytes = new byte[size];
		for (int i=0; i<size; i++)
			bytes[i]=(byte) value;

		return bytes;
	}
	
	public void assertLog(Instant instant) throws Exception{
		DateFormat sdf = new SimpleDateFormat("dd-M-yyyy");
		String date = sdf.format(java.util.Date.from(instant));
		String myData = "["+date+"] - Operation succeeded. Time elapsed: 1000000000"+String.format("%n");
		
		System.out.println("log     ="+readContent(logName));
		System.out.println("my data ="+myData);
		assertTrue(contentsEqual(myData, logName));
		System.out.println("pass");
	}
	
	
	public void assertObserver(StateObserver observer){
		
		/*
		System.out.println(observer.getPrevState());
		System.out.println(observer.getCurState());
		//assertTrue(observer.getPrevState()==State.DURING);
		assertTrue(observer.getCurState()==State.AFTER);
		*/
	}

	
	@Test
	//test the case where encryption == true
	public void testEncdecFileStringArgsValid() throws Exception{
		//resetFile(logName);
		createTempFile(10, "source2");
		createTempFile(77, "encrypted");
		tempFolder.newFile("target2");
		encdecFile.encdecFile(true, toFile("source2"), toFile("target2"));
		assertTrue(filesEqual(toFile("encrypted"), toFile("target2")));
		assertLog(first);
		
		assertObserver(observer);
		
	}
	
	
	@Test
	//test the case where encryption == false
	public void testEncdecFileStringArgsValid2() throws Exception{
		//resetClockAndLog();
		createTempFile(77, "source1");
		createTempFile(10, "decrypted");
		tempFolder.newFile("target1");
		encdecFile.encdecFile(false, toFile("source1"), toFile("target1"));
		assertTrue(filesEqual(toFile("decrypted"), toFile("target1")));
		assertLog(first);
		
		assertObserver(observer);
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
		
		assertObserver(observer);
	
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
		
		assertDir(true,  true,  "dir1", "file1","file2");
		assertDir(true,  false, "dir2", "file1","file2");
		assertDir(false, true,  "dir3", "file1","file2");
		assertDir(false, false, "dir4", "file1","file2");
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
