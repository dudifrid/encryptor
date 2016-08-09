package encryptor;

import lombok.Data;

@Data
public class MultEncdec extends Encdec{
	
	public MultEncdec(byte key) throws Exception{
		if (key%2==0){
			throw new Exception("ERROR: key is even");
		}
		else
			this.key=key;
	}
	
	final private byte key;
	private byte decKey;
	private boolean validDecKey=false;//initialized to false, and after decKey is calculated, it turns true
	
	public byte enc(byte toEnc){ 
		return (byte)(toEnc*key);
	}
	
	public byte dec(byte toDec){
		if (!validDecKey)
		{
			/*
			if (key%2==0){
				System.err.println("Error: non-invertible key");
				System.exit(1);
			}*/
			for (decKey = Byte.MIN_VALUE; decKey < Byte.MAX_VALUE && (byte)(decKey*key)!=1; decKey++);
			/*for (decKey = Byte.MIN_VALUE; decKey <= Byte.MAX_VALUE; decKey++){
				System.out.println(decKey);
				if (treatOverflow(decKey*key)==1)
					break;
			}*/
			validDecKey=true;
		}
		//System.out.println(treatOverflow(decKey)+" Notice: "+treatOverflow(decKey*key));
		return (byte)(toDec*decKey);
	}

}
