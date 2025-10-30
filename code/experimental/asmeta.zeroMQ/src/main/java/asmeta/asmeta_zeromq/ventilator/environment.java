package asmeta.asmeta_zeromq.ventilator;

import java.io.InputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

import com.google.gson.Gson;

public class environment {
    private final static Gson gson = new Gson();

    // Configuration key constants (lowercase to match file)
    private static final String ADDRESS = "ADDRESS";
    private static final String PAUSE = "PAUSE";
    private static final String ENV_FUNCTIONS = "ENV_FUNCTIONS";

    // Variables holding configuration values
    private static List<String> env_functions;
    private static final Map<String, List<String>> function_values = new HashMap<>();

    public static void main(String[] args) {
        // 1) Load configuration properties
        Properties props = new Properties();
        try (InputStream in = environment.class.getClassLoader()
                .getResourceAsStream("/configs/ventilator/zmq_config_environment.properties")) {
            if (in == null) {
                throw new RuntimeException("zmq_config_environment.properties not found in classpath under ventilator/");
            }
            props.load(in);

        } catch (Exception e) {
            throw new RuntimeException("Cannot load zmq_config_environment.properties", e);
        }
        String address = props.getProperty(ADDRESS);

        // 2) Load environment function names (with check for empty/missing property)
        String envFunctionsString = props.getProperty(ENV_FUNCTIONS, "").trim();
        if (envFunctionsString.isEmpty()) {
            env_functions = Collections.emptyList();
            System.out.println("Warning: 'env_functions' is empty or not defined. The environment will not send any data.");
        } else {
            env_functions = Arrays.asList(envFunctionsString.split(","));
        }
        System.out.println("Environment functions: " + env_functions);

        // 3) Load the value sequences for each function
        int maxLength = 0;
        for (String function : env_functions) {
            String functionValuesString = props.getProperty(function, "");
            List<String> values = Arrays.asList(functionValuesString.split(","));
            function_values.put(function, values);
            if (values.size() > maxLength) {
                maxLength = values.size();
            }
        }
        
        // Print loaded values for verification
        for (String function : env_functions) {
            System.out.println("Function: " + function + " values: " + function_values.get(function));
        }

        // 4) Load pause interval
        int pause = Integer.parseInt(props.getProperty(PAUSE, "1000"));

        try (ZContext context = new ZContext()) {
            // Create & bind the single PUB socket
            ZMQ.Socket pub = context.createSocket(SocketType.PUB);
            pub.bind(address);
            System.out.println("Environment PUB socket bound to " + address);

            // 5) Main loop to publish data
            for (int i = 0; i < maxLength; i++) {
                Thread.sleep(pause);
                System.err.println("Step: " + i);

                // For each function, send the i-th value if it exists
                for (String function : env_functions) {
                    List<String> values = function_values.get(function);
                    if (i < values.size()) {
                        Map<String, String> payload = new HashMap<>();
                        payload.put(function, values.get(i));

                        // Send the topic
                        pub.sendMore(function);
                        // Send the JSON payload
                        pub.send(gson.toJson(payload));

                        System.out.println("Sent " + function + " value " + values.get(i) + " at topic " + function);
                    }
                }
            }
            System.out.println("Finished sending all environment data.");
        } catch (InterruptedException e) {
            System.err.println("Environment thread interrupted.");
            Thread.currentThread().interrupt();
        }
        catch (Exception e) {
            System.err.println("An error occurred in the environment application: " + e.getMessage());
            e.printStackTrace();
        }
    }
}