package encryptorTests;

import org.apache.logging.log4j.core.appender.SyslogAppender;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import encryptor.CaesarEncdec;
import encryptor.XorEncdec;

import static junit.framework.Assert.*;

public class XorEncdecTest {
	
	private byte key = (byte) 2;
	private byte a = (byte) 10;
	private byte b = (byte) 8;
	
	private XorEncdec xorEncdec = new XorEncdec(key);
	
	
	@Test
	public void testEnc(){
		assertTrue(xorEncdec.enc(a)==b);
	}
	
	@Test
	public void testDec(){
		assertTrue(xorEncdec.dec(b)==a);
	}
	
}
