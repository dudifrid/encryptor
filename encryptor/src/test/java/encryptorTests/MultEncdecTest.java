package encryptorTests;

import org.apache.logging.log4j.core.appender.SyslogAppender;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import encryptor.CaesarEncdec;
import encryptor.MultEncdec;

import static junit.framework.Assert.*;

public class MultEncdecTest {
	
	private byte key = (byte) 3;
	private byte a = (byte) 10;
	private byte b = (byte) 30;
	
	
	@Test
	public void testEncValid() throws Exception{
		MultEncdec mult = new MultEncdec(key);
		assertTrue(mult.enc(a)==b);
	}
	
	@Test(expected=Exception.class)
	public void testEncInvalid() throws Exception{
		new MultEncdec((byte)34);
	}
	
	@Test
	public void testDec() throws Exception{
		MultEncdec mult = new MultEncdec(key);
		assertTrue(mult.dec(b)==a);
	}
	
}
