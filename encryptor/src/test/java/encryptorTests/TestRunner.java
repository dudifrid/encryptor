package encryptorTests;

import org.junit.runner.JUnitCore;

public class TestRunner {
   public static void main(String[] args) {
	   //prints if all the tests succeeded or not
	   System.out.println(JUnitCore.runClasses(JunitTestSuite.class).wasSuccessful());
	   System.exit(0);
   }
}  