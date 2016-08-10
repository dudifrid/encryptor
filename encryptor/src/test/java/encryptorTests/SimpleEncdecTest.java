package encryptorTests;

import org.junit.Test;
import encryptor.*;
import static junit.framework.Assert.*;


public class SimpleEncdecTest {
	
	private byte key = (byte) 2;
	private byte a = (byte) 12;
	private byte b = (byte) 14;
	
	
	private CaesarEncdec caesarEncdec = new CaesarEncdec(key);
	private SimpleEncdec<Encdec> simpleEncdec = new SimpleEncdec<Encdec>(caesarEncdec);

	
	
	@Test
	public void testEnc(){
		assertTrue(simpleEncdec.enc(a)==b);
	}
	
	@Test
	public void testDec(){
		assertTrue(simpleEncdec.dec(b)==a);
	}
	
}
