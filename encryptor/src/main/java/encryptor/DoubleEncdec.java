package encryptor;

import lombok.Data;
import lombok.NonNull;

@Data 
//public class DoubleEncdec <S extends Encdec, T extends Encdec> extends Encdec{
public class DoubleEncdec <S extends Encdec,T extends Encdec> extends Encdec{

//public class DoubleEncdec implements EncdecInterface{
	
	//@NonNull private EncdecInterface first;
	//@NonNull private EncdecInterface second;

	
	
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
