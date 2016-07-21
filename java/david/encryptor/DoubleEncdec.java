package david.encryptor;

import lombok.Data;
import lombok.NonNull;

@Data public class DoubleEncdec <S extends Encdec, T extends Encdec> extends Encdec{
	
	@NonNull private S s;
	@NonNull private T t;
	
	
	@Override
	public int encWithOverflow(byte toEnc) {
		return t.enc(s.enc(toEnc));
	}

	@Override
	public int decWithOverflow(byte toDec) {
		return s.dec(t.dec(toDec));
	}
}
