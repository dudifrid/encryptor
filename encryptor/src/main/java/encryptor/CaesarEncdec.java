package encryptor;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
//import lombok.experimental.Value;



//@EqualsAndHashCode(callSuper=false)
//implements Encdec

@Data
//public class CaesarEncdec extends Encdec{
public class CaesarEncdec extends Encdec{
	
	private final byte key;
	
	public byte enc(byte toEnc){
		return (byte)(toEnc+ key);
		//return (byte) (toEnc+key > Byte.MAX_VALUE ? toEnc+key-(Byte.MAX_VALUE+1)+Byte.MIN_VALUE : toEnc+key);
	}
	public byte dec(byte toDec){
		return (byte)(toDec-key);
		//return (byte) (toDec-key < Byte.MIN_VALUE ? toDec-key-(Byte.MIN_VALUE-1)+Byte.MAX_VALUE : toDec-key);
	}

}
