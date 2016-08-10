package encryptor;

import lombok.AllArgsConstructor;
import lombok.NonNull;


@AllArgsConstructor
public class DoubleEncdec <S extends Encdec,T extends Encdec> extends Encdec{

	
	
	@NonNull private S s;
	@NonNull private T t;
	
	
	@Override
	public byte enc(byte toEnc) {
		return t.enc(s.enc(toEnc));
	}

	@Override
	public byte dec(byte toDec) {
		return s.dec(t.dec(toDec));
	}
}
