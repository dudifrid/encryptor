package encryptor;

public class Enums {
	
	public static enum Type{
		FILE, DIR
	}
	
	public static enum Sync{
		SYNC, ASYNC
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
