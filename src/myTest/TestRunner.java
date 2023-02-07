package myTest;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import java.util.*;

/**
 * TestRunner class can be launched through command line to invoke all tests for myTest package. It is based on JUnit framework.
 * 
 * Main class to execute {@link myTest}
 * <p>
 * 
 * @version JUnit 4.13
 * @version Harmcrest: 1.3
 * @version JVM from JME CLDC 1.1
 * 
 * @author Zuech Riccardo
 */
public class TestRunner{
    private static int numberOfTests = 0;
    private static int numberOfSuccesses = 0;
    private static int numberOfFails = 0;
    private static long time = 0;
    private static List<Failure> failures;

    /**
     * Main method. TestRunner is designed to be launched through
     * command line. Starting point for all testing.
     * @param args - unused
     */
    public static void main(String[] args){
        // Contains the test result from calling JUnitCore.runClasses
        Result testResults;

        System.out.println("TestRunner started. Starting test executions.\n");
        
        testResults = JUnitCore.runClasses(TestListSuite.class, TestListIteratorSuite.class);

        System.out.println("\nTestRunner ended. All the tests have been completed.");

        elaborateResults(testResults);
        printStatistics();
    }

    /**
     * Elaborates an instance of Result returned from JUnitCore.runClasses.
     * 
     * @param result test results returned from JUnit.runClasses
     * @see JUnitCore#runClasses(Class...)
     * @see Result
     */
    private static void elaborateResults(Result result)
    {
        numberOfTests = result.getRunCount();
        numberOfFails = result.getFailureCount();
        numberOfSuccesses = numberOfTests - numberOfFails;
        failures = result.getFailures();
        time = result.getRunTime();
    }

    /**
     *  Prints test statistics and results after the tests suites has been run.
     *  <br>
     *  For each test suite shows how many test cases have been executed, how many failed and how many succeeded.
     */
    private static void printStatistics()
    {
        System.out.println("Number of tests runned: " + numberOfTests);
        System.out.println("Number of successfull tests: " + numberOfSuccesses + " (" + (float) numberOfSuccesses / (float) numberOfTests * 100 + "%)");
        System.out.println("Number of failed tests: " + numberOfFails + " (" + (float) numberOfFails / (float) numberOfTests * 100 + "%)");
        System.out.println("Time passed: " + time + "ms");
        System.out.println("Mean time per test case: " + (float) time / numberOfTests + "ms");

        // Prints all failed tests if any
        if (numberOfFails > 0){
            System.out.println("Failed tests are: ");
            for (Failure f : failures)
                System.out.println(" - " + f);
        }
        else{
            System.out.println("All " + numberOfTests + " tests were completed successfully.");
        }
    }
}