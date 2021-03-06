package encryptor;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SplitEncdec <S extends Encdec, T extends Encdec> extends Encdec{
	
	@NonNull private S s;
	@NonNull private T t;
	private boolean isEven =true;
	
	
	@Override
	public byte enc(byte toEnc) {
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
	public byte dec(byte toDec) {
		if (isEven){
			isEven =false;
			return s.dec(toDec); 
		}
		else {
			isEven = true;
			return t.dec(toDec);
		}
	}
	
	public void resetCounting(){
		isEven=true;
	}
}
