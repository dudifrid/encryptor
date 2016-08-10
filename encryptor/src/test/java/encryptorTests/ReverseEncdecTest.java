package encryptorTests;


import org.junit.Test;
import encryptor.*;
import static junit.framework.Assert.*;

public class ReverseEncdecTest {
	
	private byte key = (byte) 2;
	private byte a = (byte) 14;
	private byte b = (byte) 12;
	
	
	private CaesarEncdec caesarEncdec = new CaesarEncdec(key);
	private ReverseEncdec<Encdec> reverseEncdec = new ReverseEncdec<Encdec>(caesarEncdec);

	
	
	@Test
	public void testEnc(){
		assertTrue(reverseEncdec.enc(a)==b);
	}
	
	@Test
	public void testDec(){
		assertTrue(reverseEncdec.dec(b)==a);
	}
	
}
