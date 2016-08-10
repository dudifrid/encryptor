The testers are run using TestRunner (this class contains a main method that should be run as usual).
Running the testers and\or the suite (JunitTestSuite) by themselves may lead to a failure test.
The failure occurs when running the class MarshallingTest is run not via TestRunner, since it consists 
of a stack trace which changes when we run the class not via the TestRunner.

Java 8 is required, since there's a use of lambda expressions. Though, most of the code also compatible with Java 7 and below
(for instance, no Generic type inference in the code). 

The required JARs are attached in a dedicated directory in the project.

****

The main function extensively exploit the benefits of Java Reflection, and thus makes the code simple and concise.

Encdec is a class, but behaves like an abstract class that contains the very basic methods of encryption and decryption
of bytes (hereinafter, enc and dec, respectively).

These methods are implemented (or, more precisely, overriden) by the classes CaesarEncdec, XorEncdec, and MultEncdec.

The reason why I decided not to make the class Encdec abstract is for testability reasons:
When I test the class EncdecFile, that will be discussed in detail below, I want some abstraction that enables me
to call the methods enc and dec and mock them, whenever they implement XOR or Mult or Caesar Encdec.

The classes SimpleEncdec, DoubleEncdec, ReverseEncdec, SplitEncdec are powrefull encdec algorithm that are based
upon the algorithms above.

Note: SimpleEncdec is nothing but the algorithm itself. For example, SimpleEncdec of Caesar is nothing but Caesar algorithm.

EncdecFile uses some algorithm and performs it on a given file or directory.
It supports both synchronous and asynchronous algorithm for directories.

Even handling is also supported in this class:

EncdecState saves the current state of the encdec process (whether it's before, during, or after the process).
Observers are instances of classes that extends the class StateObserver.

When encdec is performed on a file, it notifies the observers.

Also, encdecing a file is logged in the file "report.log".

****

TestRunner is a simple Java class that has a main method that runs the suit JunitTestSuit.

Every class has a corresponding test class that contains a unit test for (almost) every method.

Classes without methods (except for those created by Lombok) has no unit testing, of course.

The testers include, among others:

*tests for all the encdec algorithms
*testing the data written to the log file
*testing the notifications to the observers
*testing that data is actually read from the input file in the course of encdecing a file
*mocking the clock
*mocking the encdec algorithms
*testing the report file (also, in case of an error the stacktrace is checked)
*testing the configuration files (that are used in importing and exporting)