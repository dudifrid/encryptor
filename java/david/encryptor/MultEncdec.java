package david.encryptor;

import lombok.Data;

@Data
public class MultEncdec extends Encdec{
		
	final private byte key;
	private byte decKey; //not final, to avoid its appearing in the Lombok constructor
	
	public int encWithOverflow(byte toEnc){
		return toEnc*key;
	}
	
	public int decWithOverflow(byte toDec){
		for (decKey = Byte.MIN_VALUE; decKey < Byte.MAX_VALUE && treatOverflow(decKey*key)!=1; decKey++); 
		return toDec*decKey;
	}

}
