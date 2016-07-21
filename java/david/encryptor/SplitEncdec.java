package david.encryptor;

import lombok.Data;
import lombok.NonNull;

@Data public class SplitEncdec <S extends Encdec, T extends Encdec> extends Encdec{
	
	@NonNull private S s;
	@NonNull private T t;
	private boolean isEven =true;
	
	
	@Override
	public int encWithOverflow(byte toEnc) {
		if (isEven){
			isEven =false;
			return s.enc(toEnc); 
		}
		else {
			isEven = true;
			return t.enc(toEnc);
		}
	}

	@Override
	public int decWithOverflow(byte toDec) {
		if (isEven){
			isEven =false;
			return s.enc(toDec); 
		}
		else {
			isEven = true;
			return t.enc(toDec);
		}
	}
}
