package encryptor;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class XorEncdec extends Encdec{
	final private byte key;
	
	public byte enc(byte toEnc){
		return (byte)(toEnc^key);
	}
	
	public byte dec(byte toDec){
		return (byte)(toDec^key);
	}

}
