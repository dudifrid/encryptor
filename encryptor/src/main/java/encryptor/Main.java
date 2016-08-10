package encryptor;

import static encryptor.Enums.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Random;
import java.util.Scanner;
import java.lang.reflect.Field;
import java.lang.reflect.Method;


public class Main {

	//scanner
	private static Scanner in;
	
	//configuration
	private static Sync sync = Sync.SYNC;
	private static Type type = Type.FILE;
	private static Family family=Family.DOUBLE;
	private static Algo first=Algo.CAESAR;
	private static Algo second=Algo.CAESAR;
	private static Goal goal=Goal.ENCRYPTION;
	private static Config config =Config.DEFAULT;
	private static Export export = Export.EXPORT; 

	//paths
	private static String filePath;
	private static String dirPath;
	private static String keyPath;
	private static String configPath; 
	private static Encdec firstInstance;
	private static Encdec secondInstance;
	
	//keys
	private static byte firstKey;
	private static byte secondKey;

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
			//System.out.println("Please choose an option");
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
		
		System.out.println();//to seperate it from the next I/O
		
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
				System.out.println("The selected random key is : "+firstKey+".");
			else
				System.out.println("The selected random keys are: "+firstKey+" and "+secondKey+".");
			
			System.out.println();
		}
		

		String keys = oneAlgo() ? "key" : "keys";
		
		if (type==Type.FILE){
			if (goal==Goal.ENCRYPTION)
				System.out.println("Please enter the path to serialize the chosen "+keys+" to.");
			else
				System.out.println("Please enter the path to deserialize the "+keys+" from.");
				
			keyPath = setPath("file", "key",goal==Goal.DECRYPTION);
		}
			
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
				//System.out.println("Please enter a "+name+" path");
				path = in.next();
				System.out.println("path ="+path);
				File file = new File(path);
				
				if (shouldExist){
					if (!file.exists()){
						System.err.println("Error: "+Type+" doesn't exist.");
						continue;
					}

					Method isType = File.class.getMethod("is"+Type);//for example, isType="isFile"
					if (!(Boolean)isType.invoke(file)){
						System.err.println("Error: Not a "+type);
						continue;
					}
				}
				else
					new File(path).getParentFile().mkdirs();
					
				break;
			}
			System.out.println();
			return path;

		}
	


	public static Class<?> toClass(Object obj) throws Exception{
		if (obj==null)
			return null;
		String classNameInLowerCase = obj.toString().toLowerCase();
		String className = "encryptor." + classNameInLowerCase.substring(0, 1).toUpperCase() + classNameInLowerCase.substring(1)+"Encdec";
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

	public static void deserializeKey() throws Exception{
		FileInputStream fileInputStream = new FileInputStream(keyPath);
		ObjectInputStream in = new ObjectInputStream(fileInputStream);
		Key key = (Key) in.readObject();
		firstKey = key.getFirstKey();
		secondKey = key.getSecondKey();
		in.close();
		fileInputStream.close();
	}



	public static void call() throws Exception{
		boolean encryption = (goal == Goal.ENCRYPTION);


		setKeys();
		
		firstInstance = (Encdec) toClass(first).getConstructor(byte.class).newInstance(firstKey);
		
		if (!oneAlgo())
			secondInstance = (Encdec) toClass(second).getConstructor(byte.class).newInstance(secondKey);

		Encdec encdec;
		
		if (oneAlgo())
			encdec = (Encdec) toClass(family).getConstructor(Encdec.class).newInstance(firstInstance);
		else
			encdec = (Encdec) toClass(family).getConstructor(Encdec.class, Encdec.class).newInstance(firstInstance, secondInstance);
		EncdecFile encdecFile = new EncdecFile(encdec);



	if (type==Type.FILE)
		encdecFile.encdecFILE(encryption, filePath);
	else
		encdecFile.encdecDIR(sync == Sync.SYNC,encryption, dirPath);

	}

	



	public static void manualConfig() throws Exception{
		//System.out.println("Do you want to encrypt or decrypt?");
		System.out.println("Please choose between encryption and decryption:");
		goal = (Goal) setValue(goal);
		//System.out.println("Is it a file or a directory? Should it be done synchronously or asynchronously?");
		System.out.println("Please choose if your input is a file or a directory:");
		type = (Type) setValue(type);
		if (type == Type.DIR){
			System.out.println("Please choose if the operation should be done synchronously or asynchronously:");
			sync = (Sync) setValue(sync);
		}
		
		System.out.println("Please choose the required algorithm:");
		family = (Family) setValue(family);
		
		
		
		
		if (oneAlgo()){
			System.out.println("Please choose an algorithm to be based upon:");
			first = (Algo) setValue(first);
			second = null;
		}
		else{
			System.out.println("You've chosen an algorithm which is based upon two algorithms.");
			System.out.println();
			System.out.println("Please choose the first algorithm:");
			first = (Algo) setValue(first);
			System.out.println("Please choose the second algorithm:");
			second = (Algo) setValue(second);
		}
		
		/*
		first = (Algo) setValue(first);

		if (!oneAlgo())
			second = (Algo) setValue(second);
		*/

		//if (goal==Goal.DECRYPTION && scope==Scope.FILE)
			//keyPath = setPath("file", "key");

		System.out.println("Please choose if you want to export this configuration:");
		export= (Export) setValue(export);

		if (export==Export.EXPORT)
		{
			System.out.println("Please enter a path to export this configuration to.");
			configPath = setPath("file", "configuration",false);
			Configuration config = new Configuration(goal, type, sync, family, first, second);
			Marshalling.exportXML(configPath, config);
		}

	}

	

	
	public static void main(String[] args) throws Exception{

		in = new Scanner(System.in);
		
		
//		System.out.println("Please choose how to configuration for the algorithm");
//		System.out.println("Do you want to use the default configuration? Or to import the configuration from a file? Or specifying it manually?");
		//System.out.println("For default configuration, please choose DEFAULT");
		//System.out.println("For importing a configuration please choose IMPORT");
		//System.out.println("For specifying it manually, please choose MANUAL");
		System.out.println("Please choose a way to retrieve your configuration:");
		config = (Config) setValue(config);

		if (config ==Config.MANUAL)
			manualConfig();
		else if (config == Config.IMPORT){
			System.out.println("Please choose the path to import the configuration from:");
			configPath = setPath("file", "configuration",true);
			Configuration config = Marshalling.importXML(configPath);
			goal = config.getGoal();
			sync = config.getSync();
			type = config.getType();
			family = config.getFamily();
			first = config.getFirst();
			second = config.getSecond();
		}

		String goalString = goal==Goal.ENCRYPTION ? "encrypted" : "decrypted";
		
		//System.out.println("Please enter a path to "+goalString+" to.");
		
		if (type == Type.FILE){
			System.out.println("Please enter the path of the file to be "+goalString+".");
			//System.out.println("Please enter a path to "+goalString+" the file to.");
			//System.out.println("The file to "+goalString+"'s path");
			filePath = setPath("file", "file",true);
		}
		else{
			System.out.println("Please enter the path of the directory to be "+goalString+".");
			//System.out.println("Please enter a path to "+goalString+" the directory to.");
			//System.out.println("The directory to "+goalString+"'s path");
			dirPath = setPath("directory", "directory",true);
		}
			
		
		
		call();

		in.close();

	}

}
