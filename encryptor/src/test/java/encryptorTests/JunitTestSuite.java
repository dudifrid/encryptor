package encryptorTests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
@RunWith(Suite.class)
@Suite.SuiteClasses({
   CaesarEncdecTest.class,
   MultEncdecTest.class,
   XorEncdecTest.class,
   
   DoubleEncdecTest.class,
   ReverseEncdecTest.class,
   SimpleEncdecTest.class,
   SplitEncdecTest.class,
   
   EncdecFileTest.class,
   
   MarshallingTest.class
})
public class JunitTestSuite {   
}  