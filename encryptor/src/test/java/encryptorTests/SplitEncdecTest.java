package encryptorTests;

import org.junit.Test;
import encryptor.*;
import static junit.framework.Assert.*;

public class SplitEncdecTest {
	
	private byte key = (byte) 2;
	private byte a = (byte) 10;
	private byte b = (byte) 12;
	private byte c = (byte) 14;
	
	
	private CaesarEncdec caesarEncdec = new CaesarEncdec(key);
	private XorEncdec xorEncdec = new XorEncdec(key);
	private SplitEncdec<Encdec, Encdec> splitEncdec = new SplitEncdec<Encdec, Encdec>(caesarEncdec, xorEncdec);

	
	
	@Test
	public void testEnc(){
		splitEncdec.resetCounting();
		assertTrue(splitEncdec.enc(a)==b);
		assertTrue(splitEncdec.enc(b)==c);
	}
	
	@Test
	public void testDec(){
		splitEncdec.resetCounting();
		assertTrue(splitEncdec.dec(b)==a);
		assertTrue(splitEncdec.dec(c)==b);
	}
	
}
