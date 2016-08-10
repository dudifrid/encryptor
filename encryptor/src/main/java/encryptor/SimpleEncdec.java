package encryptor;

import lombok.AllArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
public class SimpleEncdec <T extends Encdec> extends Encdec{
	
	@NonNull private T t;
	
	
	@Override
	public byte enc(byte toEnc) {
		return t.enc(toEnc);
	}

	@Override
	public byte dec(byte toDec) {
		return t.dec(toDec);
	}
}
