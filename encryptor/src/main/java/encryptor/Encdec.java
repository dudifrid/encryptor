package encryptor;

import lombok.Data;

/*
 * Note: another classes override all of this data, so that this serves similar to an abstract class. 
 * However, I decided not to make it abstract though, for testability reasons, as explained in the README file.
 */

@Data
public abstract class Encdec {
	private byte key;
	public byte enc(byte toEnc){
		return toEnc;
	}
	public byte dec(byte toDec){
		return toDec;
	}
}
