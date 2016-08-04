package david.encryptor;

import lombok.Data;
import java.util.function.*;

@Data
public class XorEncdec extends Encdec{
	final private byte key;
	
	//Function<Byte,Integer> ency = a->a^key;
	//int a = ency.apply((byte)2);
	
	public byte enc(byte toEnc){
		return (byte)(toEnc^key);
	}
	
	public byte dec(byte toDec){
		return (byte)(toDec^key);
	}

}
