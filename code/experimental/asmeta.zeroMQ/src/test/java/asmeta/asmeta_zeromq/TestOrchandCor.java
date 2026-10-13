package asmeta.asmeta_zeromq;

import org.junit.Test;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Tests each scenario in its own JVM so persistent ZeroMQ workers cannot block
 * the following JUnit test. Output is streamed to timestamped per-test run logs.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestOrchandCor {
    // Fixed ZeroMQ ports: only one test may run at a time within this JVM.
    private static final Object PORT_LOCK = new Object();
    // Change DEFAULT_RUNS or pass -Dtest.runs=20 (or another positive integer).
    private static final int DEFAULT_RUNS = 20;
    private static final int RUNS = positiveRuns(System.getProperty("test.runs"), DEFAULT_RUNS);
    // One timestamped directory per JUnit suite execution (class loading).
    private static final String EXECUTION_TIMESTAMP = LocalDateTime.now()
            .format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss_SSS"));
    private static final Path LOG_DIRECTORY = Paths.get("test-orch-chor", EXECUTION_TIMESTAMP);

    private static int positiveRuns(String configured, int fallback) {
        if (configured == null || configured.trim().isEmpty()) return fallback;
        try {
            int value = Integer.parseInt(configured.trim());
            if (value <= 0) throw new IllegalArgumentException("test.runs must be > 0");
            return value;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid test.runs: " + configured, e);
        }
    }
    private static final long MAX_TEST_SECONDS = 240;
    private static final long CHOREOGRAPHED_QUIET_MILLIS = 5000;
    private static final long CHOREOGRAPHED_MIN_AFTER_LAST_INPUT_MILLIS = 8000;
    private static final Pattern VALUES = Pattern.compile("^Function: mCurrTimeSecs values: \\[([^]]*)\\].*$");
    private static final Pattern ORCH_STEP = Pattern.compile("^\\[ORCHESTRATED\\] Step (\\d+):.*$");
    private static final Pattern ENV_STEP = Pattern.compile("^Step: (\\d+)\\s*$");

    private static void runAndSave(String testName, String config, String mode) {
        runAndSave(testName, config, mode, RUNS);
    }

    // Number of repetitions can also be specified independently for each test.
    private static void runAndSave(String testName, String config, String mode, int runs) {
        if (runs <= 0) throw new IllegalArgumentException("runs must be > 0");
        synchronized (PORT_LOCK) {
            int timeouts = 0;
            Path testDirectory = LOG_DIRECTORY.resolve(testName);
            try {
                Files.createDirectories(testDirectory);
                Path summary = testDirectory.resolve(testName + "_summary.csv");
                try (BufferedWriter writer = Files.newBufferedWriter(summary, StandardCharsets.UTF_8,
                        StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)) {
                    writer.write("run,status,logfile");
                    writer.newLine();
                    for (int run = 1; run <= runs; run++) {
                        System.out.printf("[TEST] %s - run %d/%d%n", testName, run, runs);
                        boolean success = runAndSaveExclusive(testName, config, mode, run);
                        if (!success) timeouts++;
                        writer.write(run + "," + (success ? "COMPLETED" : "TIMEOUT") + ","
                                + testName + "_run_" + run + ".txt");
                        writer.newLine();
                        writer.flush();
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException("Cannot save summary for " + testName, e);
            }
            System.out.printf("[TEST SUMMARY] %s: completed=%d, timeout=%d, total=%d%n",
                    testName, runs - timeouts, timeouts, runs);
            // Report failure to JUnit only AFTER all repetitions have been attempted.
            if (timeouts > 0) {
                throw new AssertionError(testName + ": " + timeouts + " of " + runs
                        + " runs timed out. See " + testDirectory.toAbsolutePath());
            }
        }
    }

    // Returns false on timeout; cleanup always runs before the next repetition.
    private static boolean runAndSaveExclusive(String testName, String config, String mode, int run) {
        Process process = null;
        Thread logReader = null;
        List<ProcessHandle> seenWorkers = new ArrayList<>();
        Path logfile = null;
        boolean timedOut = false;
        try {
            Path testDirectory = LOG_DIRECTORY.resolve(testName);
            Files.createDirectories(testDirectory);
            logfile = testDirectory.resolve(testName + "_run_" + run + ".txt");
            final Path logFileForReader = logfile;
            String java = Paths.get(System.getProperty("java.home"), "bin", "java").toString();
            String classpath = System.getProperty("java.class.path");
            ProcessBuilder builder = new ProcessBuilder(java, "-cp", classpath,
                    "asmeta.asmeta_zeromq.registry.SimulationLauncher", config, mode);
            builder.redirectErrorStream(true);
            process = builder.start();
            final Process child = process;

            AtomicInteger stepCount = new AtomicInteger(-1);
            AtomicInteger lastOrchStep = new AtomicInteger(-1);
            AtomicInteger lastEnvStep = new AtomicInteger(-1);
            AtomicLong lastInputMillis = new AtomicLong(-1);
            AtomicLong lastOutputMillis = new AtomicLong(System.currentTimeMillis());
            AtomicBoolean readerFinished = new AtomicBoolean(false);
            AtomicBoolean lastEnvTimeSent = new AtomicBoolean(false);

            logReader = new Thread(() -> {
                try (BufferedReader input = new BufferedReader(new InputStreamReader(
                        child.getInputStream(), StandardCharsets.UTF_8));
                     BufferedWriter output = Files.newBufferedWriter(logFileForReader, StandardCharsets.UTF_8,
                             StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)) {
                    String line;
                    while ((line = input.readLine()) != null) {
                        System.out.println(line);
                        output.write(line);
                        output.newLine();
                        output.flush(); // file remains usable even while simulation is running
                        lastOutputMillis.set(System.currentTimeMillis());
                        Matcher m = VALUES.matcher(line);
                        if (m.matches()) {
                            String values = m.group(1).trim();
                            stepCount.set(values.isEmpty() ? 0 : values.split(",").length);
                        }
                        m = ORCH_STEP.matcher(line);
                        if (m.matches()) lastOrchStep.set(Integer.parseInt(m.group(1)));
                        m = ENV_STEP.matcher(line);
                        if (m.matches()) lastEnvStep.set(Integer.parseInt(m.group(1)));
                        if (stepCount.get() > 0 && lastEnvStep.get() >= stepCount.get() - 1
                                && line.startsWith("Sent mCurrTimeSecs value ")) {
                            lastInputMillis.compareAndSet(-1, System.currentTimeMillis());
                            lastEnvTimeSent.set(true);
                        }
                    }
                } catch (IOException e) {
                    if (child.isAlive()) System.err.println("Log read failed for " + testName + ": " + e);
                } finally {
                    readerFinished.set(true);
                }
            }, "log-reader-" + testName + "-" + run);
            logReader.setDaemon(true);
            logReader.start();

            long deadline = System.nanoTime() + TimeUnit.SECONDS.toNanos(MAX_TEST_SECONDS);
            boolean completed = false;
            while (System.nanoTime() < deadline) {
                // Remember workers while the launcher is alive, even if it exits early.
                process.toHandle().descendants().forEach(h -> {
                    if (!seenWorkers.contains(h)) seenWorkers.add(h);
                });
                if (!process.isAlive()) {
                    completed = true;
                    break;
                }
                int n = stepCount.get();
                if (n > 0) {
                    if ("ORCHESTRATED".equals(mode) && lastOrchStep.get() >= n) {
                        completed = true;
                        break;
                    }
                    if ("CHOREOGRAPHED".equals(mode) && lastEnvTimeSent.get()) {
                        long now = System.currentTimeMillis();
                        if (now - lastInputMillis.get() >= CHOREOGRAPHED_MIN_AFTER_LAST_INPUT_MILLIS
                                && now - lastOutputMillis.get() >= CHOREOGRAPHED_QUIET_MILLIS) {
                            completed = true;
                            break;
                        }
                    }
                }
                Thread.sleep(200);
            }
            if (!completed) {
                timedOut = true;
                System.err.println("[TEST TIMEOUT] " + testName + " run " + run + " after "
                        + MAX_TEST_SECONDS + " seconds; partial log: " + logfile.toAbsolutePath());
            }
            System.out.println("[TEST LOG] " + testName + " -> " + logfile.toAbsolutePath());
            return completed;
        } catch (IOException e) {
            throw new RuntimeException("Cannot run/log " + testName, e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Test interrupted: " + testName, e);
        } finally {
            if (process != null) {
                // Close every worker BEFORE the next test can claim the same ports.
                process.toHandle().descendants().forEach(h -> {
                    if (!seenWorkers.contains(h)) seenWorkers.add(h);
                });
                for (ProcessHandle h : seenWorkers) if (h.isAlive()) h.destroy();
                if (process.isAlive()) process.destroy();
                try {
                    if (process.isAlive() && !process.waitFor(3, TimeUnit.SECONDS))
                        process.destroyForcibly();
                    long stopDeadline = System.nanoTime() + TimeUnit.SECONDS.toNanos(3);
                    for (ProcessHandle h : seenWorkers) {
                        if (h.isAlive()) {
                            long left = stopDeadline - System.nanoTime();
                            if (left > 0) h.onExit().get(left, TimeUnit.NANOSECONDS);
                        }
                    }
                } catch (Exception e) {
                    // Continue cleanup even when interrupted or a worker does not exit.
                    if (e instanceof InterruptedException) Thread.currentThread().interrupt();
                }
                for (ProcessHandle h : seenWorkers) if (h.isAlive()) h.destroyForcibly();
                if (process.isAlive()) process.destroyForcibly();
                // Do not start another test while a known worker still occupies ports.
                for (ProcessHandle h : seenWorkers) {
                    try { if (h.isAlive()) h.onExit().get(3, TimeUnit.SECONDS); }
                    catch (Exception e) { if (e instanceof InterruptedException) Thread.currentThread().interrupt(); }
                }
                if (process.isAlive() || seenWorkers.stream().anyMatch(ProcessHandle::isAlive)) {
                    throw new IllegalStateException("Cannot release all worker processes for "
                            + testName + "; stopping the suite to avoid ZeroMQ port conflicts");
                }
            }
            if (logReader != null) {
                try { logReader.join(3000); }
                catch (InterruptedException e) { Thread.currentThread().interrupt(); }
            }
            // Append the diagnostic marker only after stopping the processes and
            // waiting for the reader to flush/close the partial log.
            if (timedOut && logfile != null) {
                try {
                    Files.write(logfile,
                            ("Interrupted due to timeout" + System.lineSeparator())
                                    .getBytes(StandardCharsets.UTF_8),
                            StandardOpenOption.CREATE, StandardOpenOption.APPEND);
                    System.err.println("[TEST TIMEOUT] Interrupted due to timeout -> "
                            + logfile.toAbsolutePath());
                } catch (IOException e) {
                    throw new RuntimeException("Cannot append timeout marker to " + logfile, e);
                }
            }
        }
    }

   /* @Test public void cross1_orch() { runAndSave("cross1_orch", "configs/trafficLight/nopedestriannotram.properties", "ORCHESTRATED"); }
    @Test public void cross2_orch() { runAndSave("cross2_orch", "configs/trafficLight/pedestriannotram.properties", "ORCHESTRATED"); }
    @Test public void cross3_orch() { runAndSave("cross3_orch", "configs/trafficLight/nopedestriantram.properties", "ORCHESTRATED"); }
    @Test public void cross4_orch() { runAndSave("cross4_orch", "configs/trafficLight/pedestrian2notram.properties", "ORCHESTRATED"); }
    @Test public void cross5_orch() { runAndSave("cross5_orch", "configs/trafficLight/pedestriantram.properties", "ORCHESTRATED"); }
    @Test public void cross6_orch() { runAndSave("cross6_orch", "configs/trafficLight/pedestriantramtogether.properties", "ORCHESTRATED"); }
    */@Test public void cross7_orch() { runAndSave("cross7_orch", "configs/trafficLight/pedestrianbeforetram.properties", "ORCHESTRATED"); }
    @Test public void cross8_orch() { runAndSave("cross8_orch", "configs/trafficLight/pedestrianaftertram.properties", "ORCHESTRATED"); }
    @Test public void cross9_orch() { runAndSave("cross9_orch", "configs/trafficLight/nopedestriantram2.properties", "ORCHESTRATED"); }
  
   /* @Test public void cross1_chor() { runAndSave("cross1_chor", "configs/trafficLight/nopedestriannotram.properties", "CHOREOGRAPHED"); }
    @Test public void cross2_chor() { runAndSave("cross2_chor", "configs/trafficLight/pedestriannotram.properties", "CHOREOGRAPHED"); }
    @Test public void cross3_chor() { runAndSave("cross3_chor", "configs/trafficLight/nopedestriantram.properties", "CHOREOGRAPHED"); }
    @Test public void cross4_chor() { runAndSave("cross4_chor", "configs/trafficLight/pedestrian2notram.properties", "CHOREOGRAPHED"); }
    @Test public void cross5_chor() { runAndSave("cross5_chor", "configs/trafficLight/pedestriantram.properties", "CHOREOGRAPHED"); }
    @Test public void cross6_chor() { runAndSave("cross6_chor", "configs/trafficLight/pedestriantramtogether.properties", "CHOREOGRAPHED"); }
    @Test public void cross7_chor() { runAndSave("cross7_chor", "configs/trafficLight/pedestrianbeforetram.properties", "CHOREOGRAPHED"); }
    */@Test public void cross8_chor() { runAndSave("cross8_chor", "configs/trafficLight/pedestrianaftertram.properties", "CHOREOGRAPHED"); }
    @Test public void cross9_chor() { runAndSave("cross9_chor", "configs/trafficLight/nopedestriantram2.properties", "CHOREOGRAPHED"); }
}
