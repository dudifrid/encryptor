package encryptor;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
public class SimpleEncdec <T extends Encdec> extends Encdec{
	
	@NonNull private T t;
	private T s;
	
	
	@Override
	public byte enc(byte toEnc) {
		return t.enc(toEnc);
	}

	@Override
	public byte dec(byte toDec) {
		return t.dec(toDec);
	}
}
