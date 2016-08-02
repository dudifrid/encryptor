package david.encryptor;
/*package david.encryptor;

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
import lombok.experimental.Value;
import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.util.TimeZone;
import java.sql.Date;

@Data
@NoArgsConstructor
public abstract class Encdec implements EncdecInterface{
	
	private byte key;
//	@NonNull private String source;
//	@NonNull private String dest;
	
	public static byte treatOverflow(int b){
		int range = Byte.MAX_VALUE-Byte.MIN_VALUE;
		return  (byte) (((b-Byte.MIN_VALUE) % range)+Byte.MIN_VALUE);
	}
	
	public byte enc(byte toEnc){
		return treatOverflow(encWithOverflow(toEnc));
	}
	
	public byte dec(byte toDec){
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
	
	public void enc(String source, String dest){
		System.out.println("Starting encryption..");
		serializeKey();
		dest = source+ ".encrypted";
		//long time = System.currentTimeMillis();
		//Clock clock = new DefaultClock();
		Clock clock = new MyClock();
		Instant before = clock.instant();
		encdec(true, source, dest);
		Instant after = clock.instant();
		System.out.println("Time elapsed during encryption: " + Duration.between(before, after).toNanos());
		//System.out.println("Time elapsed=" + (System.currentTimeMillis() - time));
		System.out.println("Ending encryption..");
	}
	
	public void dec(String source, String dest){
		System.out.println("Starting decryption..");
		deserializeKey();
		int extensionLocation = source.lastIndexOf(".");
		if (extensionLocation>=0)
			dest = source.substring(extensionLocation+1)+"_decrypted."+source.substring(0, extensionLocation);
		else
		{
			System.err.println("ERROR: invalid source name (no extension)");
			System.exit(1);
		}
		Clock clock = new MyClock();
		Instant before = clock.instant();
		encdec(false, source, dest);
		Instant after = clock.instant();
		System.out.println("Time elapsed during encryption: " + Duration.between(before, after).toNanos());
		System.out.println("Ending decryption..");
	}
	
	public void syncEncdec(boolean encryption, String dirPath){
		 File dir = new File(dirPath);
		  File[] directoryListing = dir.listFiles();
		  if (directoryListing != null) {
		    for (File file : directoryListing) {
		      if (file.isFile()){
		    	  if (encryption)
		    		  encdec(encryption, file.getName(), "encrypted\\"+file.getName());
		    	  else
		    		  encdec(encryption, file.getName(), "decrypted\\"+file.getName());
		      }	  
		    }
		  } 
		  //else {
		  //}
	}
	
	public void asyncEncdec(boolean encryption){
		System.err.println("Not yet implemented");
	}
	
	public void encdec(boolean encryption, String source, String dest){
		
		 FileInputStream in = null;
	        FileOutputStream out = null;

	        try {
	            in = new FileInputStream(source);
	            out = new FileOutputStream(dest);
	            int c;
	            if (encryption){
		            while ((c = in.read()) != -1) {
		                out.write(enc((byte)c));
		            }
	            }
	            else{
		            while ((c = in.read()) != -1) {
		                out.write(dec((byte)c));
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
}
*/