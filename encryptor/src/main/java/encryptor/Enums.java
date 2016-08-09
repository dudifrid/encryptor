package encryptor;

import java.lang.System;

import static encryptor.Main.*;

import java.io.PrintStream;
import java.lang.reflect.Field;
import java.security.GeneralSecurityException;

public class Enums {
	
	public static enum Type{
		FILE, DIR
	}
	
	public static enum Sync{
		SYNC, ASYNC
	}

	/*
	public static enum Scope{
		FILE, DIR_SYNC, DIR_ASYNC
	}
	*/

	public static enum Family{
		SIMPLE, DOUBLE, SPLIT, REVERSE
	}

	public static enum Algo {
		CAESAR, XOR, MULT
	}

	public static enum Goal{
		ENCRYPTION, DECRYPTION
	}
	
	public static enum Config{
		DEFAULT, IMPORT, MANUAL
	}
	
	public static enum Export{
		EXPORT, DONT_EXPORT
	}
	
	public static enum Status{
		SUCCESS, FAILURE
	}





}
