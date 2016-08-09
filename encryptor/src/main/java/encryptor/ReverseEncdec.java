package encryptor;

import lombok.Data;
import lombok.NonNull;

@Data public class ReverseEncdec <T extends Encdec> extends Encdec{
	
	@NonNull private T t;
	@NonNull private T s;
	
	
	@Override
	public byte enc(byte toEnc) {
		return t.dec(toEnc);
	}

	@Override
	public byte dec(byte toDec) {
		return t.enc(toDec);
	}
}
