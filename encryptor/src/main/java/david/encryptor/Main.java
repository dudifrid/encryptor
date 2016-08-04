package david.encryptor;

import static david.encryptor.Enums.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectInputStream.GetField;
import java.io.ObjectOutputStream;
import java.util.Random;
import java.util.Scanner;

import javax.swing.JComboBox.KeySelectionManager;

import lombok.Getter;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.Socket;


public class Main {

	private static Scanner in;

	private static Scope scope=Scope.FILE;
	private static Family family=Family.DOUBLE;
	private static Algo first=Algo.CAESAR;
	private static Algo second=Algo.CAESAR;
	private static Goal goal=Goal.ENCRYPTION;
	private static Config config =Config.DEFAULT;
	private static Export export = Export.EXPORT; 

	private static String filePath;
	private static String dirPath;
	private static String keyPath;
	private static String configPath; 
	private static byte firstKey;
	private static byte secondKey;
	private static Encdec firstInstance;
	private static Encdec secondInstance;

	//sets the value to the *field* obj, according to the user's input
	//I found it preferable to use this function, in order to avoid four-folds code duplication
	public static Object setValue(Object obj) throws Exception{
		if (obj==null)
		{
			System.err.println("ERROR: null argument");
			System.exit(1);
		}
		int input;
		Class<?> c = obj.getClass();
		int n = c.getFields().length;
		Field[] fields=null;
		while (true){
			System.out.println("Please choose an option");
			fields = c.getFields();
			for (int i=1; i<=n; i++)
				System.out.println(i + ". "+fields[i-1].getName());

			input = in.nextInt();

			if (input <1 || input >n){
				System.err.println("Invalid input");
				continue;
			}
			break;
		}

		return fields[input-1].get(obj);
	}



	public static void promptFILE(){
		if (goal==Goal.ENCRYPTION)
			System.out.println("encryption simulation of file "+filePath);
		else
			System.out.println("decryption simulation of file "+filePath);
	}


	public static void confingurationLoad(){
		System.out.println("Pleas");
	}


	//fix the keys for multEncdec that requires the key to be odd
	public static byte fixKey(byte key){
		if (key==0)
			return 17;
		while (key%2==0)
			key=(byte)(key/2);
		return key;
	}

	//checks if we use only one key or both keys (firstKey and secondKey)
	public static boolean oneAlgo(){
		return family==Family.SIMPLE || family==Family.REVERSE; 
	}

	public static void setKeys() throws Exception{
		if (goal==Goal.ENCRYPTION){
			byte[] keys = new byte[2];
			new Random().nextBytes(keys);

			firstKey=keys[0];
			secondKey=keys[1];

			if (first==Algo.MULT)
				firstKey = fixKey(firstKey);
			if (second==Algo.MULT)
				secondKey = fixKey(secondKey);

			if (oneAlgo())
				System.out.println("The selected random key is : "+firstKey);
			else
				System.out.println("The selected random keys are: "+firstKey+" and "+secondKey);
		}


		if (scope==Scope.FILE)
			keyPath = setPath("file", "key",goal==Goal.DECRYPTION);
		else
			keyPath = dirPath+"\\key.bin";


		if (goal==Goal.ENCRYPTION)
			serializeKey();
		else
			deserializeKey();

	}
	
	
	
	//for example, type="file", name="key"
		public static String setPath(String type, String name, boolean shouldExist) throws Exception{
			String Type = type.substring(0,1).toUpperCase()+type.substring(1).toLowerCase();//first letter of type in upper case. for example, if type="file", then Type="File"
			String path=null;
			while (true){
				System.out.println("Please enter a "+name+" path");
				path = in.next();
				System.out.println("path ="+path);
				File file = new File(path);
				
				if (shouldExist){
					if (!file.exists()){
						System.err.println("Error: "+Type+" doesn't exist.");
						continue;
					}

					Method isType = File.class.getMethod("is"+Type, null);//for example, isType="isFile"
					if (!(boolean)isType.invoke(file, null)){
						System.err.println("Error: Not a "+type);
						continue;
					}
				}
				else
					new File(path).getParentFile().mkdirs();
					
				break;
			}
			return path;

		}
	


	public static Class toClass(Object obj) throws Exception{
		if (obj==null)
			return null;
		String classNameInLowerCase = obj.toString().toLowerCase();
		String className = "david.encryptor." + classNameInLowerCase.substring(0, 1).toUpperCase() + classNameInLowerCase.substring(1)+"Encdec";
		return Class.forName(className);
	}


	public static void serializeKey(){
		try
		{
			FileOutputStream fileOut = new FileOutputStream(keyPath);
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			Key key = new Key(firstKey, secondKey);
			out.writeObject(key);
			out.close();
			fileOut.close();
		}catch(IOException e)
		{
			e.printStackTrace();
		}
	}

	public static void deserializeKey(){
		try
		{
			FileInputStream fileIn = new FileInputStream(keyPath);
			ObjectInputStream in = new ObjectInputStream(fileIn);
			Key key = (Key) in.readObject();
			firstKey = key.getFirstKey();
			secondKey = key.getSecondKey();
			in.close();
			fileIn.close();
		}catch(IOException e)
		{
			e.printStackTrace();
			return;
		}catch(ClassNotFoundException e)
		{
			System.out.println("Employee class not found");
			e.printStackTrace();
			return;
		}
	}



	public static void call() throws Exception{
		boolean encryption = (goal == Goal.ENCRYPTION);


		setKeys();
		firstInstance = (Encdec) toClass(first).getConstructor(byte.class).newInstance(firstKey);
		secondInstance = (Encdec) toClass(second).getConstructor(byte.class).newInstance(secondKey);


		System.out.println("first: "+firstInstance.getKey()+" second: "+secondInstance.getKey());

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

	public static void main3(String[] args){
		System.out.println(Main.class.getFields().length);
	}


	public static void main1(String[] args){

		Encdec encdec = new CaesarEncdec((byte)3);
		//Encdec encdec = new SplitEncdec<CaesarEncdec, CaesarEncdec>(new CaesarEncdec(), new CaesarEncdec());
		byte b = (byte) 10;
		//System.out.println(encdec.treatOverflow(10));
		System.out.println("b: "+b+" enc(b): "+encdec.enc(b)+" dec(enc(b)): "+encdec.dec(encdec.enc(b)));
	}


	public static void manualConfig() throws Exception{
		goal = (Goal) setValue(goal);
		scope = (Scope) setValue(scope);
		family = (Family) setValue(family);

		first = (Algo) setValue(first);

		if (!oneAlgo())
			second = (Algo) setValue(second);

		//if (goal==Goal.DECRYPTION && scope==Scope.FILE)
			//keyPath = setPath("file", "key");

		export= (Export) setValue(export);

		if (export==Export.EXPORT)
		{
			configPath = setPath("file", "configuration",false);
			Configuration config = new Configuration(goal, scope, family, first, second);
			Marshalling.exportXML(configPath, config);
		}

	}

	public static void main(String[] args) throws Exception{

		in = new Scanner(System.in);

		config = (Config) setValue(config);

		if (config ==Config.MANUAL)
			manualConfig();
		else if (config == Config.IMPORT){
			configPath = setPath("file", "configuration",true);
			Marshalling.importXML(configPath);
		}

		if (scope == Scope.FILE)
			filePath = setPath("file", "file",true);
		else
			dirPath = setPath("directory", "directory",true);
		
		
		call();

		in.close();

	}

}
