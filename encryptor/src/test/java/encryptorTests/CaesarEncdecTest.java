package encryptorTests;

import org.junit.Test;
import encryptor.CaesarEncdec;

import static junit.framework.Assert.*;

public class CaesarEncdecTest {
	
	private byte key = (byte) 2;
	private byte a = (byte) 10;
	private byte b = (byte) 12;
	
	private CaesarEncdec caesar = new CaesarEncdec(key);
	
	
	@Test
	public void testEnc(){
		assertTrue(caesar.enc(a)==b);
	}
	
	@Test
	public void testDec(){
		assertTrue(caesar.dec(b)==a);
	}
	
}
