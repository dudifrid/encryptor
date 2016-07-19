package DavidFrid.encryptor;
import java.io.Console;
import java.util.Scanner;
import java.io.File;


public class App 
{
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
            if (encryption)
            	System.out.println("encryption simulation of file"+path);
            else
            	System.out.println("decryption simulation of file"+path);
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
