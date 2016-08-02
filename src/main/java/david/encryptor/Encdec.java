package david.encryptor;

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
import java.util.TimeZone;
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
//	@NonNull private String source;
//	@NonNull private String dest;
	
	
	
	
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
			Clock clock = new MyClock();
			Instant before = clock.instant();
			encdecFile(encryption, source, dest);
			Instant after = clock.instant();
			System.out.println("Time elapsed during "+goal+": " + Duration.between(before, after).toNanos());
			System.out.println("Ending "+goal+"..");

	}
	
	
	public void encdecDIR_ASYNC(boolean encryption, String source){
		if (encryption)
			serializeKey();
		else
			deserializeKey();
		
		
	}
	
	
	public void encdecDIR_SYNC(boolean encryption, String dirPath){
		 File dir = new File(dirPath);
		  File[] directoryListing = dir.listFiles();
		  if (directoryListing != null) {
		    for (File file : directoryListing) {
		      if (file.isFile()){
		    	  if (encryption)
		    		  encdecFile(encryption, file.getName(), "encrypted\\"+file.getName());
		    	  else
		    		  encdecFile(encryption, file.getName(), "decrypted\\"+file.getName());
		      }	  
		    }
		  } 
		  //else {
		  //}
	}
	
	
	
	public void encdecFile(boolean encryption, String source, String dest){
		
		 FileInputStream in = null;
	        FileOutputStream out = null;

	        try {
	        	if (source==null)
	        	{
	        		System.err.println("ERROR: null argument");
	        		System.exit(1);
	        	}
	            in = new FileInputStream(source);
	            out = new FileOutputStream(dest);
	            int c;
	            if (encryption){
		            while ((c = in.read()) != -1) {
		            	System.out.println("before: "+c+" after: "+encByte((byte)c));
		                out.write(encByte((byte)c));
		            }
	            }
	            else{
		            while ((c = in.read()) != -1) {
		                out.write(decByte((byte)c));
		            }
	            }

	        } catch (IOException e){
	        	
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
	
	public static byte treatOverflow(int b){
		int range = Byte.MAX_VALUE-Byte.MIN_VALUE;
		return  (byte) (((b-Byte.MIN_VALUE) % range)+Byte.MIN_VALUE);
	}
	
	public byte encByte(byte toEnc){
		return treatOverflow(encWithOverflow(toEnc));
	}
	
	public byte decByte(byte toDec){
		return treatOverflow(encWithOverflow(toDec));
	}
	
	public void serializeKey(){
		try
	      {
	         FileOutputStream fileOut =
	         new FileOutputStream("key.bin");
	         ObjectOutputStream out = new ObjectOutputStream(fileOut);
	         out.writeObject(key);
	         out.close();
	         fileOut.close();
	      }catch(IOException e)
	      {
	          e.printStackTrace();
	      }
	}
	
	public void deserializeKey(){
		try
	      {
	         FileInputStream fileIn = new FileInputStream("key.bin");
	         ObjectInputStream in = new ObjectInputStream(fileIn);
	         key = (byte) in.readObject();
	         in.close();
	         fileIn.close();
	      }catch(IOException e)
	      {
	         e.printStackTrace();
	         return;
	      }catch(ClassNotFoundException e)
	      {
	         System.out.println("Employee class not found");
	         e.printStackTrace();
	         return;
	      }
	}
	
}
