package encryptorTests;

import org.apache.logging.log4j.core.appender.SyslogAppender;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.mockito.Mock;

import encryptor.*;

import static junit.framework.Assert.*;

public class DoubleEncdecTest {
	
	private byte key = (byte) 2;
	private byte a = (byte) 10;
	private byte b = (byte) 14;
	
	
	private CaesarEncdec caesarEncdec = new CaesarEncdec(key);
	private XorEncdec xorEncdec = new XorEncdec(key);
	private DoubleEncdec<Encdec, Encdec> doubleEncdec = new DoubleEncdec<Encdec, Encdec>(caesarEncdec, xorEncdec);

	
	
	@Test
	public void testEnc(){
		assertTrue(doubleEncdec.enc(a)==b);
	}
	
	@Test
	public void testDec(){
		assertTrue(doubleEncdec.dec(b)==a);
	}
	
}
