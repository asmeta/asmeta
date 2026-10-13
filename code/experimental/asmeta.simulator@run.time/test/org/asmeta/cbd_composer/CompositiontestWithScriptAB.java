package org.asmeta.cbd_composer;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

/**
 * Runs AsmetaComp scenarios sequentially and saves each run's console output.
 *
 * Output directory example:
 * test-logs/2026-10-10_21-30-00_123/cross2/cross2_run_1.txt
 *
 * Run count: default 20, overridable with -Dtest.runs=N, or per test using
 * runAndLog(testName, script, option, N).
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CompositiontestWithScriptAB {

    private static final int DEFAULT_RUNS = 20;
    private static final int RUNS = positiveRuns(System.getProperty("test.runs"), DEFAULT_RUNS);
    private static final String EXECUTION_TIMESTAMP = LocalDateTime.now()
            .format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss_SSS"));
    private static final Path LOG_DIRECTORY = Paths.get("test-asmetacomp", EXECUTION_TIMESTAMP);
    private static final Object LOG_LOCK = new Object();

    private static int positiveRuns(String configured, int fallback) {
        if (configured == null || configured.trim().isEmpty()) return fallback;
        try {
            int count = Integer.parseInt(configured.trim());
            if (count <= 0) throw new IllegalArgumentException("test.runs must be > 0");
            return count;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid test.runs: " + configured, e);
        }
    }

    private static void runAndLog(String testName, String script, boolean option) {
        runAndLog(testName, script, option, RUNS);
    }

    private static void runAndLog(String testName, String script, boolean option, int runs) {
        if (runs <= 0) throw new IllegalArgumentException("runs must be > 0");
        // System.out and System.err are global to this JVM: never capture two runs at once.
        synchronized (LOG_LOCK) {
            for (int run = 1; run <= runs; run++) {
                runOnce(testName, script, option, run, runs);
            }
        }
    }

    private static void runOnce(String testName, String script, boolean option,
                                int run, int totalRuns) {
        final Path logFile = LOG_DIRECTORY.resolve(testName)
                .resolve(testName + "_run_" + run + ".txt");
        try {
            Files.createDirectories(logFile.getParent());
        } catch (IOException e) {
            throw new IllegalStateException("Cannot create log directory: " + logFile.getParent(), e);
        }

        final PrintStream originalOut = System.out;
        final PrintStream originalErr = System.err;
        // A single file stream is shared by stdout and stderr to preserve their output
        // as closely as possible. Writes are synchronized by PrintStream/file locking.
        try (FileOutputStream file = new FileOutputStream(logFile.toFile());
             PrintStream teeOut = new PrintStream(new TeeOutputStream(originalOut, file), true, "UTF-8");
             PrintStream teeErr = new PrintStream(new TeeOutputStream(originalErr, file), true, "UTF-8")) {

            System.setOut(teeOut);
            System.setErr(teeErr);
            try {
                System.out.printf("[TEST] %s - run %d/%d%n", testName, run, totalRuns);
                ComposerCLI comp = new ComposerCLI(script, option);
                comp.runcomposition();
                System.out.printf("[TEST] %s - run %d/%d completed%n", testName, run, totalRuns);
            } finally {
                System.out.flush();
                System.err.flush();
                System.setOut(originalOut);
                System.setErr(originalErr);
            }
        } catch (IOException e) {
            throw new IllegalStateException("Cannot write test log: " + logFile, e);
        }
        originalOut.println("[TEST LOG] " + logFile.toAbsolutePath());
    }

    /** Forward writes to the original console and a file without closing either. */
    private static final class TeeOutputStream extends OutputStream {
        private final OutputStream console;
        private final OutputStream file;

        private TeeOutputStream(OutputStream console, OutputStream file) {
            this.console = console;
            this.file = file;
        }

        @Override
        public synchronized void write(int b) throws IOException {
            console.write(b);
            synchronized (file) { file.write(b); }
        }

        @Override
        public synchronized void write(byte[] bytes, int offset, int length) throws IOException {
            console.write(bytes, offset, length);
            synchronized (file) { file.write(bytes, offset, length); }
        }

        @Override
        public synchronized void flush() throws IOException {
            console.flush();
            synchronized (file) { file.flush(); }
        }

        @Override
        public void close() throws IOException {
            flush(); // Do not close System.out, System.err, or the shared file here.
        }
    }

 /*   @Test
    public void testSmartHome() {
        runAndLog("testSmartHome", "examples/SmartMultiHome/FireSystem.asmsh", true);
    }

    @Test
    public void testPillbox() {
        runAndLog("testPillbox", "examples/Pillbox_composition/pillboxComp2.asmsh", false);
    }
*/

    @Test
    public void cross1() {
        runAndLog("cross1", "examples/trafficLightCoSimCrossAB/nopedestriannotram.asmsh", true);
    }

    @Test
    public void cross2() {
        runAndLog("cross2", "examples/trafficLightCoSimCrossAB/pedestriannotram.asmsh", true);
    }

    @Test
    public void cross3() {
        runAndLog("cross3", "examples/trafficLightCoSimCrossAB/nopedestriantram.asmsh", true);
    }

    @Test
    public void cross4() {
        runAndLog("cross4", "examples/trafficLightCoSimCrossAB/pedestrian2notram.asmsh", true);
    }

    @Test
    public void cross5() {
        runAndLog("cross5", "examples/trafficLightCoSimCrossAB/pedestriantram.asmsh", true);
    }

    @Test
    public void cross6() {
        runAndLog("cross6", "examples/trafficLightCoSimCrossAB/pedestriantramtogether.asmsh", true);
    }

    @Test
    public void cross7() {
        runAndLog("cross7", "examples/trafficLightCoSimCrossAB/pedestrianbeforetram.asmsh", true);
    }

    @Test
    public void cross8() {
        runAndLog("cross8", "examples/trafficLightCoSimCrossAB/pedestrianaftertram.asmsh", true);
    }

    @Test
    public void cross9() {
        runAndLog("cross9", "examples/trafficLightCoSimCrossAB/nopedestriantram2.asmsh", true);
    }
      
}
