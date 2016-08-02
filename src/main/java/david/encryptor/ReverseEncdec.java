package david.encryptor;

import lombok.Data;
import lombok.NonNull;

@Data public class ReverseEncdec <T extends Encdec> extends Encdec{
	
	@NonNull private T t;
	
	
	@Override
	public int encWithOverflow(byte toEnc) {
		return t.decByte(toEnc);
	}

	@Override
	public int decWithOverflow(byte toDec) {
		return t.encByte(toDec);
	}
}
