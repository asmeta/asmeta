package asmeta.asmeta_zeromq;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class TestRunner {

    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(TestOrchandCor2.class);

        for (Failure failure : result.getFailures()) {
            System.err.println(failure);
        }

        System.out.println("Tests executed: " + result.getRunCount());
        System.out.println("Failures: " + result.getFailureCount());

        System.exit(result.wasSuccessful() ? 0 : 1);
    }
}