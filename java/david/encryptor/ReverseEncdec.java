package david.encryptor;

import lombok.Data;
import lombok.NonNull;

@Data public class ReverseEncdec <T extends Encdec> extends Encdec{
	
	@NonNull private T t;
	
	
	@Override
	public int encWithOverflow(byte toEnc) {
		return t.dec(toEnc);
	}

	@Override
	public int decWithOverflow(byte toDec) {
		return t.enc(toDec);
	}
}
