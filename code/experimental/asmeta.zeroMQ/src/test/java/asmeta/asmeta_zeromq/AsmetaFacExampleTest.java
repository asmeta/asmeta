package asmeta.asmeta_zeromq;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map; // Not used directly, but Timeout uses it implicitly

import static org.junit.jupiter.api.Assertions.assertEquals; // Not used directly, but Timeout uses it implicitly
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout; // Import Timeout
import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;
import org.zeromq.ZMQ.Socket;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


// This test assumes the zeroMQW wrappers (likely via Starter) are running.
class AsmetaFacExampleTest {

    // --- Configuration Matching Your Properties Files ---
    // Address the test publishes 'myinput' TO (where asmMulti listens)
    private static final String MYINPUT_PUB_ADDRESS = "tcp://*:5554";
    // *** MODIFIED: Use a dedicated port for the test's initial funcDec publication ***
    private static final String INITIAL_DEC_TEST_PUB_ADDRESS = "tcp://*:5562"; // Test-specific port

    // Address the test subscribes FROM to get funcInc (where asmInc publishes)
    private static final String INC_SUB_ADDRESS = "tcp://localhost:5551";
    // Address the test subscribes FROM to get funcDec (where asmDec publishes)
    private static final String DEC_SUB_ADDRESS = "tcp://localhost:5552";
    // Address the test subscribes FROM to get funcMulti (where asmMulti publishes)
    private static final String MULTI_SUB_ADDRESS = "tcp://localhost:5553";

    private final Gson gson = new Gson();
    private final Type mapStringStringType = new TypeToken<Map<String, String>>(){}.getType();


    @Test
    @Timeout(20) // Set a timeout (e.g., 20 seconds) to prevent the test hanging indefinitely
    void testFirstCycleExecutionWithInitialValues() throws InterruptedException {
        System.out.println("Starting testFirstCycleExecutionWithInitialValues...");
        try (ZContext context = new ZContext()) {
            // --- Setup Publishers for Test Inputs ---
            Socket myInputPublisher = context.createSocket(SocketType.PUB);
            System.out.println("TEST: Attempting to bind myinput PUB socket to " + MYINPUT_PUB_ADDRESS);
            // *** This bind will fail if port 5554 is still in use by another process! ***
            myInputPublisher.bind(MYINPUT_PUB_ADDRESS);
            System.out.println("TEST: myinput PUB socket bound to " + MYINPUT_PUB_ADDRESS);

            // *** MODIFIED: Bind initial funcDec publisher to the new test-specific port ***
            Socket initialDecPublisher = context.createSocket(SocketType.PUB);
            System.out.println("TEST: Attempting to bind initial funcDec PUB socket to " + INITIAL_DEC_TEST_PUB_ADDRESS);
            initialDecPublisher.bind(INITIAL_DEC_TEST_PUB_ADDRESS); // Bind to 5562
            System.out.println("TEST: initial funcDec PUB socket bound to " + INITIAL_DEC_TEST_PUB_ADDRESS);

            // --- Setup Subscribers to Listen for Results ---
            Socket incSubscriber = context.createSocket(SocketType.SUB);
            incSubscriber.connect(INC_SUB_ADDRESS);
            incSubscriber.subscribe("".getBytes(ZMQ.CHARSET));
            System.out.println("TEST: SUB socket connecting to " + INC_SUB_ADDRESS + " (for funcInc)");

            Socket decSubscriber = context.createSocket(SocketType.SUB);
            decSubscriber.connect(DEC_SUB_ADDRESS);
            decSubscriber.subscribe("".getBytes(ZMQ.CHARSET));
            System.out.println("TEST: SUB socket connecting to " + DEC_SUB_ADDRESS + " (for funcDec)");

            Socket multiSubscriber = context.createSocket(SocketType.SUB);
            multiSubscriber.connect(MULTI_SUB_ADDRESS);
            multiSubscriber.subscribe("".getBytes(ZMQ.CHARSET));
            System.out.println("TEST: SUB socket connecting to " + MULTI_SUB_ADDRESS + " (for funcMulti)");

            System.out.println("TEST: Waiting a moment for sockets to establish connections...");
            Thread.sleep(2000); // Allow time for PUB/SUB connections to establish

            // --- Send Initial Inputs ---
            // 1. Send myinput to trigger asmMulti
            Map<String, String> myInputMsg = new HashMap<>();
            myInputMsg.put("myinput", "1");
            String myInputJson = gson.toJson(myInputMsg);
            System.out.println("TEST: Sending myinput = 1 to " + MYINPUT_PUB_ADDRESS);
            myInputPublisher.send(myInputJson);

            // Give asmMulti time to process and publish funcMulti, and for others to receive it
            Thread.sleep(500); // Adjust if needed

            // 2. Send the initial funcDec=0 to kickstart asmInc
            Map<String, String> initialDecMsg = new HashMap<>();
            initialDecMsg.put("funcDec", "0"); // The kickstart value
            String initialDecJson = gson.toJson(initialDecMsg);
            // *** MODIFIED: Send on the new test-specific port ***
            System.out.println("TEST: Sending initial funcDec = 0 to " + INITIAL_DEC_TEST_PUB_ADDRESS);
            initialDecPublisher.send(initialDecJson); // Send on 5562

            // --- Wait for and Validate Results ---
            System.out.println("TEST: Waiting for expected results (funcMulti=2, funcInc=9, funcDec=10)...");

            String receivedFuncMulti = null;
            String receivedFuncInc = null;
            String receivedFuncDec = null; 

            long startTime = System.currentTimeMillis();
            // Max wait time slightly less than the @Timeout to allow for cleanup
            long maxWaitTimeMillis = 15000;

            // Loop until we get all expected outputs or timeout
            while (System.currentTimeMillis() - startTime < maxWaitTimeMillis) {
                boolean changed = false;

                // Check for funcMulti=2 from asmMulti
                if (receivedFuncMulti == null) {
                    String multiJson = multiSubscriber.recvStr(ZMQ.DONTWAIT);
                    if (multiJson != null) {
                        System.out.println("TEST: Received from multiSubscriber (" + MULTI_SUB_ADDRESS + "): " + multiJson);
                        Map<String, String> multiMap = gson.fromJson(multiJson, mapStringStringType);
                        // Expecting funcMulti to become 2 after the first cycle
                        if ("2".equals(multiMap.get("funcMulti"))) {
                             receivedFuncMulti = multiMap.get("funcMulti");
                             changed = true;
                        }
                    }
                }

                // Check for funcInc=9 from asmInc
                if (receivedFuncInc == null) {
                    String incJson = incSubscriber.recvStr(ZMQ.DONTWAIT);
                    if (incJson != null) {
                        System.out.println("TEST: Received from incSubscriber (" + INC_SUB_ADDRESS + "): " + incJson);
                        Map<String, String> incMap = gson.fromJson(incJson, mapStringStringType);
                        // Expecting funcInc to become 9 after the first cycle
                         if ("9".equals(incMap.get("funcInc"))) {
                            receivedFuncInc = incMap.get("funcInc");
                            changed = true;
                         }
                    }
                }

                // Check for funcDec=10 from asmDec
                 if (receivedFuncDec == null) {
                    String decJson = decSubscriber.recvStr(ZMQ.DONTWAIT);
                    if (decJson != null) {
                        System.out.println("TEST: Received from decSubscriber (" + DEC_SUB_ADDRESS + "): " + decJson);
                        Map<String, String> decMap = gson.fromJson(decJson, mapStringStringType);
                        // Expecting funcDec to become 10 after the first cycle
                        if ("10".equals(decMap.get("funcDec"))) {
                             receivedFuncDec = decMap.get("funcDec");
                             changed = true;
                        }
                    }
                 }

                 // Exit loop if all values received
                 if (receivedFuncMulti != null && receivedFuncInc != null && receivedFuncDec != null) {
                    System.out.println("TEST: All expected values received.");
                    break;
                 }

                // Avoid busy-waiting if nothing was received in this iteration
                if (!changed) {
                    Thread.sleep(100);
                }
            }

            System.out.println("TEST: Finished waiting loop.");
            System.out.println("TEST: Final values received: funcMulti=" + receivedFuncMulti + ", funcInc=" + receivedFuncInc + ", funcDec=" + receivedFuncDec);

            // --- Assertions ---
            assertNotNull(receivedFuncMulti, "Did not receive funcMulti=2 within timeout.");
            assertEquals("2", receivedFuncMulti, "Expected funcMulti=2");

            assertNotNull(receivedFuncInc, "Did not receive funcInc=9 within timeout.");
            assertEquals("9", receivedFuncInc, "Expected funcInc=9 after first cycle");

            assertNotNull(receivedFuncDec, "Did not receive calculated funcDec=10 within timeout.");
            assertEquals("10", receivedFuncDec, "Expected funcDec=10 after first cycle");

            System.out.println("TEST: Assertions passed.");

            // Sockets are closed automatically by try-with-resources for ZContext

        } catch (org.zeromq.ZMQException e) {
             System.err.println("TEST FAILED: ZMQException during setup/run: " + e.getMessage());
             e.printStackTrace();
             // Add specific check for the original error
             if (e.getErrorCode() == ZMQ.Error.EADDRINUSE.getCode()) {
                 fail("Test failed due to ZMQException: Address already in use (Code: " + e.getErrorCode() + "). Ensure port 5554 (and 5562) are free before running the test and that Starter isn't binding them.");
             } else {
                 fail("Test failed due to ZMQException: " + e.getMessage() + " (Code: " + e.getErrorCode() + ")");
             }
        } catch (Exception e) {
            System.err.println("Test failed with other exception:");
            e.printStackTrace();
            fail("Test threw an exception: " + e.getMessage());
        } finally {
             System.out.println("Finished testFirstCycleExecutionWithInitialValues.");
        }
    }

    

} // End of class AsmetaFacExampleTest 