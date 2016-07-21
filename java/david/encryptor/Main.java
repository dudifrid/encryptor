package david.encryptor;
import java.io.Console;
import java.util.Random;
import java.util.Scanner;
import java.io.File;


public class Main
{
	
	public static Encdec callEncdec(byte key, String input){
		switch (input.charAt(0)){
		case 'c':
			return new CaesarEncdec(key);
		case 'x':
			 return new XOREncdec(key);
		case 'm':
			return new MultEncdec(key);
		default:
		{
			System.err.println("ERROR: Invalid input");
		}
		return null;
		}
	}
	
	public static void main( String[] args )
	{
		Scanner in = new Scanner(System.in);

		System.out.println("Please press 'e' to use encryption, and press 'd' for decryption");
		String input=null;
		input = in.nextLine();

		boolean encryption=false;
		if (input.equals("e"))
			encryption = true;
		else if (input.equals("d"))
			encryption = false;
		else
		{
			System.err.println("Error: invalid input");
			System.exit(1);
		}

		String path = null;

		while (true){
			System.out.println("Please enter source path");
			path = input;
			Encdec encdec = null;
			System.out.println("For Caeser algorithm press 'c'");
			System.out.println("For XOR algorithm press 'x'");
			System.out.println("For Multiplication algorithm press 'm'"); 
			
			input = in.nextLine();
			if (input.length()>1)
			{
				System.err.println("ERROR: too long input");

			}

			
			//byte key = (byte)new Random().nextInt();
			
			//define a key that will be used
			byte[] keys = new byte[1];
			new Random().nextBytes(keys);
			byte key = keys[0];
			
			if ((encdec=callEncdec(key, input))==null)
				continue;

			if (encryption)
			{
				System.out.println("encryption simulation of file"+path);
				encdec.enc();
			}

			else
			{
				System.out.println("decryption simulation of file"+path);

			}

			File f = new File(path);
			if (!f.exists())
			{
				System.err.println("Error: File doesn't exist");
				continue;
			}
			if (!f.isFile())
			{
				System.err.println("Error: not a file path");
				continue;
			}
			break;
		}


	}

}
