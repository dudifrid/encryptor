package david.encryptor;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
//import lombok.experimental.Value;



//@EqualsAndHashCode(callSuper=false)
//implements Encdec

@Data
public class CaesarEncdec extends Encdec{
	
	private final byte key;
	
	public int encWithOverflow(byte toEnc){
		return toEnc+ key;
		//return (byte) (toEnc+key > Byte.MAX_VALUE ? toEnc+key-(Byte.MAX_VALUE+1)+Byte.MIN_VALUE : toEnc+key);
	}
	public int decWithOverflow(byte toDec){
		return toDec+key;
		//return (byte) (toDec-key < Byte.MIN_VALUE ? toDec-key-(Byte.MIN_VALUE-1)+Byte.MAX_VALUE : toDec-key);
	}

}
