package encryptor;

import javax.xml.bind.JAXBElement;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static encryptor.Enums.*;
import static encryptor.Main.*;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.lang.Object;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.TimeZone;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.sql.Date;


/* 
 * encdecFILEScope(encryption, source) = calculates dest, and does some more stuff (such as prints etc.). To be applied when scope == Scope.FILE
 * encdecASYNC_DIRScope(encryption, source) = just the same, but applied when scope == Scope.ASYNC_DIR
 * encdecSYNC_DIRScope(encryption, source) = just the same, but applied when scope == Scope.SYNC_DIR
 * encdecFile(encryption, source, dest) = encdecs file according
 * encdecByte(encryption, source, dest)
 * 
 * DO NOT CONFUSE between encdecFILE and encdecFile
 * 
 */





@Data
public class EncdecFile{


	private ReportAbstract report;
	private static final Logger logger = LogManager.getLogger(EncdecFile.class);
	private Reports reports = new Reports();
	private Clock clock;
	private Encdec encdec;


	static ExecutorService exec = Executors.newCachedThreadPool();

	public EncdecFile(Encdec encdec){//default constructor
		report = new Report();
		clock = new MyClock();
		this.encdec=encdec;
	}

	public EncdecFile(Encdec encdec, ReportAbstract report, Clock clock){//constructor for unit testing
		this.report=report;
		this.clock=clock;
		this.encdec=encdec;
	}



	/*
	 * works properly even when the source has no extension at all.
	 * works properly even for paths consist of a "." as "current directory".
	 * presumes path doesn't consist of ".." (directory's parent).
	 */
	
	public static String getDestFILE(boolean encryption, String source){
		String dest=null;
		if (encryption)
			dest = source+ ".encrypted";
		else{
			int extensionLocation = source.lastIndexOf(".");
			if (extensionLocation>0)
				dest = source.substring(0, extensionLocation)+"_decrypted."+source.substring(extensionLocation+1);

			else
				dest = source + "_decrypted";
			/*
			 * else
			{
				System.err.println("ERROR: invalid source name (no extension)");
				System.exit(1);
			}*/
		}
		return dest;
	}

	public void encdecFILE(boolean encryption, File s){
		encdecFILE(encryption,s.getName());
	}

	
	public void printSummary(String goal){

	}

	public void encdecFILE(boolean encryption, String source){
		String dest = getDestFILE(encryption, source);

		String goal = encryption ? "encryption" : "decryption";

		System.out.println("Starting "+goal+"..");

		encdecFile(encryption, source, dest);

		
		
		if (report.getStatus()==Status.SUCCESS){
			System.out.println("Ending "+goal+"..");
			System.out.println("The time elapsed during "+goal+": " + report.getTime()+" nanoseconds.");
		}
		else
			System.out.println("Operation failed. Reason:"+report.getExceptionMessage());
			
		
		//printSummary(goal);
	}

	@Data
	public class EncdecTask implements Runnable{
		final private boolean encryption;
		@NonNull private String source;
		@NonNull private String target;

		@Override
		public void run() {
			EncdecFile.this.encdecFile(encryption, source, target);
		}

	}


	public void encdecDIR(boolean async, boolean encryption, String dirPath) throws Exception{
		Instant before = clock.instant();
		File dir = new File(dirPath);
		File[] files = dir.listFiles();
		String newDir;
		if (files != null) {
			newDir = encryption ? dirPath + "\\encrypted" : dirPath + "\\decrypted";
			new File(newDir).mkdir();

			if (async){
				for (File file : files) {
					if (file.isFile() && !file.getName().equals("key.bin"))
						exec.submit(new EncdecTask(encryption, file.getPath(), newDir +"\\"+ file.getName())).get();
				}
			}
			else{
				for (File file : files) {
					if (file.isFile() && !file.getName().equals("key.bin"))
						encdecFile(encryption, file.getPath(), newDir +"\\"+ file.getName());
				}
			}

			String XMLPath = dirPath+"\\report.xml";
			Marshalling.report(XMLPath, reports);

		}
		
		Instant after = clock.instant();
		long time = Duration.between(before, after).toNanos();
		String goal = encryption ? "encryption" : "decryption";
		System.out.println("The whole "+goal+" operation took "+time+" nanoseconds.");
		
		//else {
		//}

	}

	public void encdecDIR_ASYNC(boolean encryption, String dirPath) throws Exception{
		encdecDIR(true, encryption, dirPath);
	}


	public void encdecDIR_SYNC(boolean encryption, String dirPath) throws Exception{
		encdecDIR(false, encryption, dirPath);
	}



	public void encdecFile(boolean encryption, InputStream in, PrintStream out) throws IOException{
		int c;
		if (encryption){

			while (in.available()!=0){
				c=in.read();
				out.write(encdec.enc((byte)c));
			}
		}
		else{
			while ((c = in.read()) != -1) {
				out.write(encdec.dec((byte)c));
			}
		}

	}


	public void encdecFile(boolean encryption, String source, String dest) {
		//InputStream in = null;
		//FileOutputStream out = null;
		InputStream in;
		PrintStream out;

		try {

			if (source==null)
			{
				System.err.println("ERROR: null argument");
				System.exit(1);
			}
			in = new FileInputStream(source);
			new File(dest);
			out = new PrintStream(dest);


			Instant before = clock.instant();
			encdecFile(encryption, in, out);
			Instant after = clock.instant();
			long time = Duration.between(before, after).toNanos();
			report.setAll(source, Status.SUCCESS, time, null, null, null);
			reports.getReports().add(report);
			logger.info("Operation succeded. Time elapsed: "+time);

		} catch (Exception e){
			report.setAll(source, Status.FAILURE, null, e.getClass().getName(), e.getMessage(), e.getStackTrace());
			reports.getReports().add(report);
			logger.error("Error occured. Reason: "+e.getMessage());
		}

	}

}
