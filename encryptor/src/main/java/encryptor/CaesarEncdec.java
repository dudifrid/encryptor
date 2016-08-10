package encryptor;


import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CaesarEncdec extends Encdec{
	
	private final byte key;
	

	public byte enc(byte toEnc){
		return (byte)(toEnc+ key);
	}
	public byte dec(byte toDec){
		return (byte)(toDec-key);
	}

}
