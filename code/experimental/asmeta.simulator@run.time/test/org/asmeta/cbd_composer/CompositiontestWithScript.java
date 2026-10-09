package org.asmeta.cbd_composer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import org.junit.Test;

public class CompositiontestWithScript {

    private static final File LOG_DIRECTORY = new File("asmetacomp_outputsim");

    /**
     * Saves System.out and System.err for this test to test-logs/<testName>.txt,
     * while continuing to print everything to the original console.
     * The original streams are restored even if the simulation fails.
     */
    private static synchronized void runAndLog(String testName, String script, boolean option) {
        if (!LOG_DIRECTORY.exists() && !LOG_DIRECTORY.mkdirs()) {
            throw new IllegalStateException("Cannot create log directory: " + LOG_DIRECTORY);
        }

        PrintStream originalOut = System.out;
        PrintStream originalErr = System.err;
        File logFile = new File(LOG_DIRECTORY, testName + ".txt");

        try (FileOutputStream file = new FileOutputStream(logFile);
             PrintStream teeOut = new PrintStream(new TeeOutputStream(originalOut, file), true, "UTF-8");
             PrintStream teeErr = new PrintStream(new TeeOutputStream(originalErr, file), true, "UTF-8")) {

            System.setOut(teeOut);
            System.setErr(teeErr);
            try {
                ComposerCLI comp = new ComposerCLI(script, option);
                comp.runcomposition();
            } finally {
                System.out.flush();
                System.err.flush();
                System.setOut(originalOut);
                System.setErr(originalErr);
            }
        } catch (IOException e) {
            throw new IllegalStateException("Cannot write test log: " + logFile, e);
        }
    }

    /** Writes each byte to both the console and the log file. Never closes the console. */
    private static final class TeeOutputStream extends OutputStream {
        private final OutputStream console;
        private final OutputStream file;

        private TeeOutputStream(OutputStream console, OutputStream file) {
            this.console = console;
            this.file = file;
        }

        @Override
        public void write(int b) throws IOException {
            console.write(b);
            file.write(b);
        }

        @Override
        public void write(byte[] b, int off, int len) throws IOException {
            console.write(b, off, len);
            file.write(b, off, len);
        }

        @Override
        public void flush() throws IOException {
            console.flush();
            file.flush();
        }

        @Override
        public void close() throws IOException {
            flush();
            // The console and the file are managed by their respective owners.
        }
    }
/*
    @Test
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
        runAndLog("cross1", "examples/trafficLightCoSimCross/nopedestriannotram.asmsh", true);
    }

    @Test
    public void cross2() {
        runAndLog("cross2", "examples/trafficLightCoSimCross/pedestriannotram.asmsh", true);
    }
/*
    @Test
    public void cross3() {
        runAndLog("cross3", "examples/trafficLightCoSimCross/nopedestriantram.asmsh", true);
    }

    @Test
    public void cross4() {
        runAndLog("cross4", "examples/trafficLightCoSimCross/pedestrian2notram.asmsh", true);
    }

    @Test
    public void cross5() {
        runAndLog("cross5", "examples/trafficLightCoSimCross/pedestriantram.asmsh", true);
    }

    @Test
    public void cross6() {
        runAndLog("cross6", "examples/trafficLightCoSimCross/pedestriantramtogether.asmsh", true);
    }

    @Test
    public void cross7() {
        runAndLog("cross7", "examples/trafficLightCoSimCross/pedestrianbeforetram.asmsh", true);
    }

    @Test
    public void cross8() {
        runAndLog("cross8", "examples/trafficLightCoSimCross/pedestrianaftertram.asmsh", true);
    }

    @Test
    public void cross9() {
        runAndLog("cross9", "examples/trafficLightCoSimCross/nopedestriantram2.asmsh", true);
    }
    */
}
