package org.asmeta.cbd_composer;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class TestRunnerAsmetaComp {

    public static void main(String[] args) {

        System.out.println("=================================");
        System.out.println(" AsmetaComp - Composition Tests");
        System.out.println("=================================");

        long startTime = System.currentTimeMillis();

        Result result = JUnitCore.runClasses(
            CompositiontestWithScriptAB2.class
        );

        long elapsedTime = System.currentTimeMillis() - startTime;

        System.out.println();
        System.out.println("=================================");
        System.out.println(" TEST RESULTS");
        System.out.println("=================================");
        System.out.println("Tests executed: " + result.getRunCount());
        System.out.println("Failures: " + result.getFailureCount());
        System.out.println("Successful: " + result.wasSuccessful());
        System.out.println("Total time: " + elapsedTime / 1000.0 + " s");

        for (Failure failure : result.getFailures()) {
            System.err.println(failure.toString());
            System.err.println(failure.getTrace());
        }

        System.exit(result.wasSuccessful() ? 0 : 1);
    }
}