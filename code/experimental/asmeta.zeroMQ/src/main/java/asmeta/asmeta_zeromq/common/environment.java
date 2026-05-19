package asmeta.asmeta_zeromq.common;

import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicBoolean;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

import com.google.gson.Gson;

public class environment {
    private final static Gson gson = new Gson();

    private static final String ENVIRONMENT_FUNCTIONS = "env_functions";

    private static List<String> environmentFunctions;
    private static final Map<String, List<String>> environmentFunctionsValues = new java.util.HashMap<>();

    private static Properties extractSection(Properties all, String sectionPrefix) {
        Properties out = new Properties();
        String sec = sectionPrefix + ".";
        String common = "common.";
        for (String k : all.stringPropertyNames()) {
            String v = all.getProperty(k);
            if (k.startsWith(sec)) {
                out.put(k.substring(sec.length()), v);
            } else if (k.startsWith(common)) {
                out.put(k.substring(common.length()), v);
            }
        }
        return out;
    }

    public static void main(String[] args) {
        Properties env;
        try (InputStream in = environment.class.getClassLoader()
                .getResourceAsStream("configs/trafficLightSimCoSimCross-config/zmq_config_TrafficSimulation.properties")) {

            if (in == null) {
                throw new RuntimeException("zmq_config.properties not found in classpath.");
            }
            Properties all = new Properties();
            all.load(in);
            env = extractSection(all, "environment");

        } catch (Exception e) {
            throw new RuntimeException("Cannot load zmq_config.properties", e);
        }

        String address = env.getProperty("address");

        environmentFunctions = Arrays.asList(env.getProperty(ENVIRONMENT_FUNCTIONS).split(","));
        System.out.println("Environment functions: " + environmentFunctions);

        int maxLength = 0;
        for (String function : environmentFunctions) {
            environmentFunctionsValues.put(function, Arrays.asList(env.getProperty(function).split(",")));
            if (environmentFunctionsValues.get(function).size() > maxLength) {
                maxLength = environmentFunctionsValues.get(function).size();
            }
        }
        for (String function : environmentFunctions) {
            System.out.println("Function: " + function + " values: " + environmentFunctionsValues.get(function));
        }

        int pause = Integer.parseInt(env.getProperty("pause", "1000"));

        try (ZContext context = new ZContext()) {
            ZMQ.Socket pub = context.createSocket(SocketType.PUB);
            pub.bind(address);
            System.out.println("Environment PUB socket bound to " + address);

            Thread.sleep(1000);

            int i = 0;
            while (i < maxLength) {
                System.err.println("Step: " + i);

                for (String function : environmentFunctions) {
                    if (i < environmentFunctionsValues.get(function).size()) {
                        Map<String, String> payload = new HashMap<>();
                        payload.put(function, environmentFunctionsValues.get(function).get(i));
                        pub.sendMore(function);
                        pub.send(gson.toJson(payload));
                        System.out.println("Sent " + function + " value "
                                + environmentFunctionsValues.get(function).get(i)
                                + " to " + address + " at topic " + function);
                    }
                }

                Thread.sleep(pause);
               i++;
            }

        } catch (Exception e) {
            System.err.println("An error occurred in the environment: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
