package david.encryptor;


import java.io.IOException;
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
	
	@NonNull private String source;
	@NonNull private String dest;
	
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
	
	public void enc(){
		System.out.println("Starting encryption..");
		dest = source+ ".encrypted";
		//long time = System.currentTimeMillis();
		//Clock clock = new DefaultClock();
		Clock clock = new MyClock();
		Instant before = clock.instant();
		encdec(true);
		Instant after = clock.instant();
		System.out.println("Time elapsed=" + Duration.between(before, after).toNanos());
		//System.out.println("Time elapsed=" + (System.currentTimeMillis() - time));
		System.out.println("Ending encryption..");
	}
	
	public void dec(){
		System.out.println("Starting decryption..");
		int lastPeriod = source.lastIndexOf(".");
		if (lastPeriod>=0)
			dest = source.substring(lastPeriod+1)+"_decrypted."+source.substring(0, lastPeriod);
		else
		{
			System.err.println("ERROR: invalid source name (no extension)");
			System.exit(1);
		}
		encdec(false);
		System.out.println("Ending decryption..");
	}
	
	public void encdec(boolean encryption){
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
