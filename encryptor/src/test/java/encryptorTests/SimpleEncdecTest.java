package encryptorTests;

import org.apache.logging.log4j.core.appender.SyslogAppender;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.mockito.Mock;

import encryptor.*;

import static junit.framework.Assert.*;

public class SimpleEncdecTest {
	
	private byte key = (byte) 2;
	private byte a = (byte) 12;
	private byte b = (byte) 14;
	
	
	private CaesarEncdec caesarEncdec = new CaesarEncdec(key);
	private SimpleEncdec<Encdec> simpleEncdec = new SimpleEncdec<Encdec>(caesarEncdec, caesarEncdec);

	
	
	@Test
	public void testEnc(){
		assertTrue(simpleEncdec.enc(a)==b);
	}
	
	@Test
	public void testDec(){
		assertTrue(simpleEncdec.dec(b)==a);
	}
	
}
