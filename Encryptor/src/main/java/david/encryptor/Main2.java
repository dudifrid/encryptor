/*package david.encryptor;

import java.io.File;
import java.util.Scanner;

import david.encryptor.Main2.scope;

public class Main2 {
	
	private static Scanner in;
	private static Scope scope;
	private static Family family;
	private static Algo algo;

	public static enum Scope{
		FILE, DIR_SYNC, DIR_ASYNC
	}
	
	public static enum Family{
		DOUBLE, SPLIT, REVERSE
	}
	
	public static enum Algo {
		CAESAR, XOR, MULT
	}

	
	public static void getScope(){
		int input;
		
		int n = Scope.values().length;
		do{
			System.out.println("Please choose an option");
			for (Scope s :Scope.values()){
				System.out.println((s.ordinal()+1) + ". "+s.toString());
			}
			input = in.nextInt(); 
	
			if (input <1 || input >n){
				System.err.println("Invalid input");
				continue;
			}
		
		} while (false);
		
		scope = Scope.values()[input-1];
	}

	
	public static void main(String[] args){
		
		//Scanner in = new Scanner(System.in);
		in = new Scanner(System.in);
		
		
		//input toEncrypt
		boolean toEncrypt=false;
		do{
			System.out.println("Please choose an option");
			System.out.println("1. encryption");
			System.out.println("2. decryption");
			switch (in.nextInt()){
			case 1:
				toEncrypt=true;
			case 2:
				toEncrypt=false;
			default:
				System.err.println("Invalid input");
				continue;
			}
		} while (false);
		
		

		//input path
		String path = null;
		do{
			System.out.println("Please enter a path");
			path = in.nextLine();
			File file = new File(path);
			
			if (toEncrypt)
				System.out.println("encryption simulation of file "+path);
			else
				System.out.println("decryption simulation of file "+path);
			
			if (!file.exists()){
				System.err.println("File doesn't exist.");
				continue;
			}
			if (!file.isFile()){
				System.err.println("Not a file");
				continue;
			}
		} while (false);
		
		
		
		System.out.println("Please select an option, and type its serial number");
		System.out.println("1. ");
	}

}
*/