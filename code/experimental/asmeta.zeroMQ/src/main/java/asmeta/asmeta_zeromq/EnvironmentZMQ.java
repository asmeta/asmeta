package asmeta.asmeta_zeromq;

import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

import com.google.gson.Gson;

import asmeta.asmeta_zeromq.producerconsumer.environment;

public class EnvironmentZMQ implements Runnable{
	
	   private final static Gson gson = new Gson();

	    private static final String ENVIRONMENT_FUNCTIONS = "env_functions";

	    private static List<String> environmentFunctions;
	    private static final Map<String, List<String>> environmentFunctionsValues = new java.util.HashMap<>();

	    String address;
	    Properties props;
	    String propertiesPath;
	    int pause;
	    int lenghtOfEnvFuncValues;
	    
	public EnvironmentZMQ(String propertiesPath) {
		this.propertiesPath=propertiesPath;
		props = new Properties();
		pause = 0;
		lenghtOfEnvFuncValues = 0;
	}

	public void loadFromProperties() {
		// 1) Load bind address & pause interval
       try (InputStream in = environment.class.getClassLoader().getResourceAsStream(propertiesPath)){
                                         //.getResourceAsStream("producerconsumer/zmq_config_environment.properties")) {
           if (in == null) throw new RuntimeException(propertiesPath + " not found");
           props.load(in);
       } catch (Exception e) {
           throw new RuntimeException("Cannot load " + propertiesPath, e);
       }
       address = props.getProperty("address");
    // 2) Load environment functions in a list
       environmentFunctions = Arrays.asList(props.getProperty(ENVIRONMENT_FUNCTIONS).split(","));
       System.out.println("Environment functions: " + environmentFunctions);

       // 3) Load environment functions values in a map referencing the function name
       for (String function : environmentFunctions) {
           environmentFunctionsValues.put(function, Arrays.asList(props.getProperty(function).split(",")));
           if (environmentFunctionsValues.get(function).size() > lenghtOfEnvFuncValues) {
               lenghtOfEnvFuncValues = environmentFunctionsValues.get(function).size();
           }
       }

       for (String function : environmentFunctions) {
           System.out.println("Function: " + function + " values: " + environmentFunctionsValues.get(function));
       }

       // 4) Load pause interval
       pause = Integer.parseInt(props.getProperty("pause", "1000"));

	}
	
	 @Override 
	public void run() {
		loadFromProperties();
        try (ZContext context = new ZContext()) {
            // Create & bind the single PUB socket
            ZMQ.Socket pub = context.createSocket(SocketType.PUB);
            pub.bind(address);
            System.out.println("Environment PUB socket bound to " + address);

            for (int i = 0; i < lenghtOfEnvFuncValues; i++) {

                System.err.println("Step: " + i);
                // For each function, send the i value if it exists
                for (String function : environmentFunctions) {

                    if (i < environmentFunctionsValues.get(function).size()) {
                        Map<String, String> payload = new HashMap<>();
                        payload.put(function, environmentFunctionsValues.get(function).get(i));

                        // Send to the right topic
                        pub.sendMore(function);
                        // Send the value
                        pub.send(gson.toJson(payload));
                        // Print the message
                        System.out.println("Sent " + function + " value " + environmentFunctionsValues.get(function).get(i) + " to " + address + " at topic " + function);
                    }
                }
                Thread.sleep(pause);
            }
        } catch (Exception e) {
            System.err.println("An error occurred in the environment application: " + e.getMessage());
            e.printStackTrace();
        }
		
	}
	
	

}
