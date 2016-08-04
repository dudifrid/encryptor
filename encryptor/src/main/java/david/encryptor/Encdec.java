package david.encryptor;

import javax.xml.bind.JAXBElement;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static david.encryptor.Enums.*;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
import static david.encryptor.Main.*;


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
@NoArgsConstructor
public abstract class Encdec implements EncdecInterface{

	private byte key;//notice that decKey is calculated form encKey
	private Report report;
	private static final Logger logger = LogManager.getLogger(Encdec.class);
	//long time;
	//private JAXBElement<ArrayList<Report>> reports = new JAXBElement<ArrayList<Report>>("report", declaredType, reports);
	//private MyArrayList<Report> reports = new MyArrayList<>();
	private Reports reports = new Reports();
	
	static ExecutorService exec = Executors.newCachedThreadPool();
	
	
	//	@NonNull private String source;
	//	@NonNull private String dest;

	public void setKey(byte key){
		this.key = key;
		System.out.println("KEY ="+key);
	}
	public byte getKey(byte key){
		System.out.println("HELLO THERE!!");
		System.exit(1);
		return key;
	}



	public String getDestFILE(boolean encryption, String source){
		String dest=null;
		if (encryption)
			dest = source+ ".encrypted";
		else{
			int extensionLocation = source.lastIndexOf(".");
			if (extensionLocation>=0)
				dest = source.substring(extensionLocation+1)+"_decrypted."+source.substring(0, extensionLocation);
			else
			{
				System.err.println("ERROR: invalid source name (no extension)");
				System.exit(1);
			}
		}
		return dest;
	}


	public void encdecFILE(boolean encryption, String source){
		String dest = getDestFILE(encryption, source);

		String goal;
		if (encryption)
			goal = "encryption";
		else
			goal ="decryption";

		System.out.println("Starting "+goal+"..");
		
		encdecFile(encryption, source, dest);
		
		if (report.getStatus()==Status.SUCCESS)
			System.out.println("Time elapsed during "+goal+": " + report.getTime());
		System.out.println("Ending "+goal+"..");
	}

	@Data
	public class EncdecTask implements Runnable{
		final private boolean encryption;
		@NonNull private String source;
		@NonNull private String target;
		
		@Override
		public void run() {
			Encdec.this.encdecFile(encryption, source, target);
		}
		
	}

	
	public void encdecDIR(boolean async, boolean encryption, String dirPath) throws Exception{
		File dir = new File(dirPath);
		File[] directoryListing = dir.listFiles();
		String newDir;
		if (directoryListing != null) {
			newDir = encryption ? dirPath + "\\encrypted" : dirPath + "\\decrypted"; 
			new File(newDir).mkdir();

			if (async){
				for (File file : directoryListing) {
					if (file.isFile() && !file.getName().equals("key.bin"))
						exec.submit(new EncdecTask(encryption, file.getPath(), newDir +"\\"+ file.getName())).get();
				}
			}
			else{
				for (File file : directoryListing) {
					if (file.isFile() && !file.getName().equals("key.bin"))
						encdecFile(encryption, file.getPath(), newDir +"\\"+ file.getName());
				}
			}
			
			String XMLPath = dirPath+"\\report.xml";
			Marshalling.report(XMLPath, reports);

		}
		//else {
		//}

	}
	
	public void encdecDIR_ASYNC(boolean encryption, String dirPath) throws Exception{
		encdecDIR(true, encryption, dirPath);
	}


	public void encdecDIR_SYNC(boolean encryption, String dirPath) throws Exception{
		encdecDIR(false, encryption, dirPath);
	}



	public void encdecFile(boolean encryption, String source, String dest){

		FileInputStream in = null;
		FileOutputStream out = null;

		try {
			Clock clock = new MyClock();
			Instant before = clock.instant();

			if (source==null)
			{
				System.err.println("ERROR: null argument");
				System.exit(1);
			}
			
			in = new FileInputStream(source);

			//new File(dest).getParentFile().mkdirs();
			
			out = new FileOutputStream(dest);
			
			int c;
			if (encryption){
				while ((c = in.read()) != -1) {
					//System.out.println("before: "+c+" after: "+enc((byte)c));
					out.write(enc((byte)c));
				}
			}
			else{
				while ((c = in.read()) != -1) {
					out.write(dec((byte)c));
				}
			}
			
			Instant after = clock.instant();
			long time = Duration.between(before, after).toNanos();
			report = new Report(source, Status.SUCCESS, time, null, null, null);
			reports.getReports().add(report);
			logger.info("Operation succeded. Time elapsed: "+time);
			
		} catch (IOException e){
			Report r = new Report(source, Status.FAILURE, null, e.getClass().getName(), e.getMessage(), e.getStackTrace());
			reports.getReports().add(r);
			logger.error("Error occured. Reason: "+e.getMessage());
		}
		finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
	}

	/*public static byte treatOverflow(int b){
		int range = Byte.MAX_VALUE-Byte.MIN_VALUE;
		return  (byte) (((b-Byte.MIN_VALUE) % range)+Byte.MIN_VALUE);
	}*/

	
}
