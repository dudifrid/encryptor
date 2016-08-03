package david.encryptor;

import lombok.Data;
import java.util.function.*;

@Data
public class XorEncdec extends Encdec{
	final private byte key;
	
	//Function<Byte,Integer> ency = a->a^key;
	//int a = ency.apply((byte)2);
	
	public int encWithOverflow(byte toEnc){
		return toEnc^key;
	}
	
	public int decWithOverflow(byte toDec){
		return toDec^key;
	}

}
