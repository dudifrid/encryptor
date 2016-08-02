package david.encryptor;

import lombok.Data;

@Data
public class MultEncdec extends Encdec{
		
	final private byte key;
	private byte decKey;
	private boolean validDecKey=false;//initialized to false, and after decKey is calculated, it turns true
	
	public int encWithOverflow(byte toEnc){ 
		return toEnc*key;
	}
	
	public int decWithOverflow(byte toDec){
		if (!validDecKey)
		{
			for (decKey = Byte.MIN_VALUE; decKey < Byte.MAX_VALUE && treatOverflow(decKey*key)!=1; decKey++);
			validDecKey=true;
		}
		return toDec*decKey;
	}

}
