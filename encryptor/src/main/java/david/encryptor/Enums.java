package david.encryptor;

import java.lang.reflect.Field;
import java.security.GeneralSecurityException;

import static david.encryptor.Main.*;

public class Enums {

	public static enum Scope{
		FILE, DIR_SYNC, DIR_ASYNC
	}

	public static enum Family{
		SIMPLE, DOUBLE, SPLIT, REVERSE
	}

	public static enum Algo {
		CAESAR, XOR, MULT
	}

	public static enum Goal{
		ENCRYPTION, DECRYPTION
	}
	
	enum Config{
		DEFAULT, IMPORT, MANUAL
	}
	
	enum Export{
		EXPORT, DONT_EXPORT
	}
	
	enum Status{
		SUCCESS, FAILURE
	}





}
