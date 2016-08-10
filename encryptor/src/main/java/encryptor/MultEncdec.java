package encryptor;


public class MultEncdec extends Encdec{
	
	final private byte key;
	private byte decKey;
	private boolean validDecKey=false;//initialized to false, and after decKey is calculated, it turns true
	
	public MultEncdec(byte key) throws Exception{
		if (key%2==0){
			throw new Exception("ERROR: key is even");
		}
		else
			this.key=key;
	}
	
	public byte enc(byte toEnc){ 
		return (byte)(toEnc*key);
	}
	
	public byte dec(byte toDec){
		if (!validDecKey)
		{
			for (decKey = Byte.MIN_VALUE; decKey < Byte.MAX_VALUE && (byte)(decKey*key)!=1; decKey++);	
			validDecKey=true;
		}
		
		return (byte)(toDec*decKey);
	}

}
