package david.encryptor;

import static david.encryptor.Enums.*;

import java.io.File;
import java.util.Random;
import java.util.Scanner;

import javax.swing.JComboBox.KeySelectionManager;

import java.lang.reflect.Field;

public class Main {

	private static Scanner in;

	private static Scope scope=Scope.FILE;
	private static Family family=Family.DOUBLE;
	private static Algo first=Algo.CAESAR;
	private static Algo second=Algo.CAESAR;
	private static Goal goal=Goal.ENCRYPTION;
	
	private static String filePath;
	private static String dirPath;
	private static String keyPath;
	private static byte firstKey;
	private static byte secondKey;
	
	//sets the value to the *field* obj, according to the user's input
	//I found it preferable to use this function, in order to avoid four-folds code duplication
	public static void setValue(Object obj){
		if (obj==null)
		{
			System.err.println("ERROR: null argument");
			System.exit(1);
		}
		int input;
		Class<?> c = obj.getClass();
		int n = c.getFields().length;
		Field[] fields=null;
		do{
			System.out.println("Please choose an option");
			fields = c.getFields();
			for (int i=1; i<=n; i++)
				System.out.println(i + ". "+fields[i-1].getName());

			input = in.nextInt();

			if (input <1 || input >n){
				System.err.println("Invalid input");
				continue;
			}

		} while (false);
		//obj = c.getMethod("values", null)[input-1];
		try{
			obj = fields[input-1].get(obj);
		}
		catch (IllegalAccessException e){//never happens, unless code is buggy
			System.err.println("Illegal access");
			System.exit(1);
		}
	}

	public static void setKeys(){
		if (scope==Scope.FILE){
			if (goal==Goal.ENCRYPTION){
				byte[] keys = new byte[2];
				new Random().nextBytes(keys);
				firstKey=keys[0];
				secondKey=keys[0];
			}
		}
		else{
			//if (goal==Goal.ENCRYPTION)
				//serializeKey()
		}
	}

	public static void setFilePath(){
		//String filePath = null;
		
		while (true){
			System.out.println("Please enter a file path");
			filePath = in.next();
			System.out.println("path ="+filePath);
			File file = new File(filePath);

			if (goal==Goal.ENCRYPTION)
				System.out.println("encryption simulation of file "+filePath);
			else
				System.out.println("decryption simulation of file "+filePath);

			if (!file.exists()){
				System.err.println("File doesn't exist.");
				continue;
			}
			if (!file.isFile()){
				System.err.println("Not a file");
				continue;
			}
			break;
		}

	}
	
	
	public static void setDirPath(){
		//String dirPath = null;
		while (true){
			System.out.println("Please enter a directory path");
			dirPath = in.next();
			File file = new File(dirPath);


			if (!file.exists()){
				System.err.println("File doesn't exist.");
				continue;
			}
			if (!file.isDirectory()){
				System.err.println("Not a directory");
				continue;
			}
			break;
		} 
		
	}
	
	public static void setKeyPath(){
		//String dirPath = null;
		while (true){
			System.out.println("Please enter a key path");
			dirPath = in.nextLine();
			File file = new File(dirPath);

			if (!file.exists()){
				System.err.println("File doesn't exist.");
				continue;
			}
			if (!file.isFile()){
				System.err.println("Not a file");
				continue;
			}
			break;
		} 

	}
	
	public static Class toClass(Object obj) throws Exception{
		if (obj==null)
			return null;
		String classNameInLowerCase = obj.toString().toLowerCase();
		String className = "david.encryptor." + classNameInLowerCase.substring(0, 1).toUpperCase() + classNameInLowerCase.substring(1)+"Encdec";
		return Class.forName(className);
	}
	
	
	
	public static void call() throws Exception{
		boolean encryption = (goal == Goal.ENCRYPTION);
		
		
		setKeys();
		
		Encdec firstInstance = (Encdec) toClass(first).getConstructor(byte.class).newInstance(firstKey);
		Encdec secondInstance = (Encdec) toClass(second).getConstructor(byte.class).newInstance(secondKey);
		Encdec encdec = (Encdec) toClass(family).getConstructor(Encdec.class, Encdec.class).newInstance(firstInstance, secondInstance); 
		
		
		/*
		Class[] types = {boolean.class, String.class};
		Object[] args = {encryption, filePath};
		toClass(family).getMethod("encdec"+scope.toString(), types).invoke(encdec, args);
		*/
		
		
		
		switch (scope){
		case FILE:
			encdec.encdecFILE(encryption, filePath);
			break;
		case DIR_ASYNC:
			encdec.encdecDIR_SYNC(encryption, dirPath);
			break;
		case DIR_SYNC:
			encdec.encdecDIR_SYNC(encryption, dirPath);
			break;
		}
		
		
		
	}


	public static void main1(String[] args){
		Encdec encdec = new CaesarEncdec((byte)2);
		//Encdec encdec = new SplitEncdec<CaesarEncdec, CaesarEncdec>(new CaesarEncdec(), new CaesarEncdec());
		byte b = (byte) 10;
		System.out.println("before: "+encdec.getKey());
		encdec.setKey((byte)4);
		System.out.println("after: "+encdec.getKey());
		System.out.println("b: "+b+" enc(b): "+encdec.encWithOverflow(b));
	}
	
	public static void main(String[] args) throws Exception{
		
		in = new Scanner(System.in);

		setValue(goal);
		setValue(scope);
		
		if (scope == Scope.FILE)
			setFilePath();
		else
			setDirPath();
		
		setValue(family);
		
		setValue(first);
		
		if (family!=Family.REVERSE)
			setValue(second);
		
		if (goal==Goal.DECRYPTION && scope==Scope.FILE)
			setKeyPath();
		
		call();
		
		in.close();

	}

}
