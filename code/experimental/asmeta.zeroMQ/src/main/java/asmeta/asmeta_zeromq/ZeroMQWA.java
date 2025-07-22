package asmeta.asmeta_zeromq;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.asmeta.runtime_container.Esit;
import org.asmeta.runtime_container.RunOutput;
import org.asmeta.runtime_container.SimulationContainer;
import org.asmeta.simulator.Environment;
import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class ZeroMQWA implements Runnable {

	// Add a static logger instance
	private static final Logger logger = LogManager.getLogger(ZeroMQWA.class);

	private final String CONFIG_FILE_PATH;
	private static final String RUNTIME_MODEL_PATH = "RUNTIME_MODEL_PATH";
	private static final String ZMQ_PUB_SOCKET = "ZMQ_PUB_SOCKET";
	private static final String ZMQ_SUB_CONNECT_ADDRESSES = "ZMQ_SUB_CONNECT_ADDRESSES";
	private static final String ASM_ENVIRONMENT_FUNCTIONS = "ASM_ENVIRONMENT_FUNCTIONS";
	private static final String ASM_ENVIRONMENT_ADDRESS = "ASM_ENVIRONMENT_ADDRESS";
	private static final String CONSOLE_INPUT_FUNCTIONS = "CONSOLE_INPUT_FUNCTIONS";

	private SimulationContainer sim;
	private ZMQ.Socket publisher;
	private final List<ZMQ.Socket> subscribers = new ArrayList<>();

	// Add instance variables to store config and asmId
	private int asmId;
	private String modelPath;
	private String pubAddress;
	private String subConnectAddresses;

	private String environmentAddress;
	private List<String> environmentTopics;
	private List<String> startingInputFunctions;
	private Set<String> requiredMonitored;
	private Map<String, String> currentMonitoredValues;
	private Gson gson;
	private Type mapStringStringType;

	public ZeroMQWA(String config_filepath) {
		requiredMonitored = new HashSet<>();
		gson = new Gson();
		currentMonitoredValues = new HashMap<>();
		mapStringStringType = new TypeToken<Map<String, String>>() {
		}.getType();
		CONFIG_FILE_PATH = config_filepath;
		logger.info("zeroMQW initialized for config: {}", config_filepath);

		try {
			// Configuration section
			Properties config = loadConfig();
			modelPath = config.getProperty(RUNTIME_MODEL_PATH);
			pubAddress = config.getProperty(ZMQ_PUB_SOCKET);
			subConnectAddresses = config.getProperty(ZMQ_SUB_CONNECT_ADDRESSES, "");
			environmentAddress = config.getProperty(ASM_ENVIRONMENT_ADDRESS);
			environmentTopics = Arrays.asList(config.getProperty(ASM_ENVIRONMENT_FUNCTIONS, "").split(","));

			if (config.getProperty(CONSOLE_INPUT_FUNCTIONS) != null) {
				startingInputFunctions = Arrays.asList(config.getProperty(CONSOLE_INPUT_FUNCTIONS, "").split(","));
			} else {
				startingInputFunctions = null;
			}

			// logger.info("Starting Input Functions Size: {}",
			// this.startingInputFunctions.size());
			// logger.info("Starting Input Functions: {}", this.startingInputFunctions);

			// if (this.startingInputFunctions.size() % 2 != 0) {
			// throw new Exception("CONSOLE_INPUT_FUNCTIONS must have an even number of
			// values (key, value pairs)");
			// }

			// Initialize ASM
			asmId = initializeAsm(modelPath);

			logger.info("zeroMQW instance configured successfully for {}", config_filepath);

		} catch (IOException | NullPointerException e) {
			logger.fatal("CRITICAL ERROR during zeroMQW initialization for {}: {}", config_filepath, e.getMessage(), e);
		} catch (Exception e) {
			logger.fatal("CRITICAL ERROR during initialization for {}: {}", config_filepath, e.getMessage(), e);
		}
	}

	private Properties loadConfig() throws IOException, NullPointerException {
		Properties properties = new Properties();
		try (InputStream input = ZeroMQWA.class.getResourceAsStream(CONFIG_FILE_PATH)) {
			if (input == null) {
				logger.error("Config file not found at path: {}", CONFIG_FILE_PATH);
				throw new IOException("Config file not found, path: " + CONFIG_FILE_PATH);
			}
			properties.load(input);
			logger.debug("Properties loaded from file.");
			// PUB E SUB COULD BE EMPTY
			if (properties.getProperty(RUNTIME_MODEL_PATH) == null) { // || properties.getProperty(ZMQ_PUB_SOCKET) ==
																		// null){
				// String missing = "";
				// if (properties.getProperty(RUNTIME_MODEL_PATH) == null)
				//missing += RUNTIME_MODEL_PATH + " ";
				/*
				 * if (properties.getProperty(ZMQ_PUB_SOCKET) == null) missing += ZMQ_PUB_SOCKET
				 * + " "; logger.error("Essential configuration parameters missing: {}",
				 * missing.trim()); throw new
				 * NullPointerException("ERROR Essential parameters missing: " +
				 * missing.trim());
				 */
				String missing = RUNTIME_MODEL_PATH;
				logger.error("ASMETA model path missed: {}", missing.trim());
				throw new NullPointerException("ERROR Essential parameters missing: " + missing.trim());
			}
		} catch (IOException e) {
			logger.error("ERROR loading config file '{}': {}", CONFIG_FILE_PATH, e.getMessage(), e);
			throw e;
		} catch (NullPointerException e) {
			logger.error("ERROR checking config properties: {}", e.getMessage(), e);
			throw e;
		}
		logger.info("Configuration Loaded Successfully!");
		logger.info(" * {} = {}", RUNTIME_MODEL_PATH, properties.getProperty(RUNTIME_MODEL_PATH));
		logger.info(" * {} = {}", ZMQ_PUB_SOCKET, properties.getProperty(ZMQ_PUB_SOCKET));
		logger.info(" * {} = {}", ZMQ_SUB_CONNECT_ADDRESSES, properties.getProperty(ZMQ_SUB_CONNECT_ADDRESSES));
		logger.info(" * {} = {}", ASM_ENVIRONMENT_ADDRESS, properties.getProperty(ASM_ENVIRONMENT_ADDRESS));
		return properties;
	}

	private int initializeAsm(String modelPath) throws Exception {
		logger.info("Initializing ASM simulation container...");
		sim = new SimulationContainer(Environment.TimeMngt.use_java_time);
		sim.init(1);
		logger.debug("Simulation container initialized.");
		logger.info("Starting ASM execution for model: {}", modelPath);
		int asmId = sim.startExecution(modelPath);
		if (asmId < 0) {
			logger.error("Starting ASM model failed: negative id received ({})", asmId);
			throw new Exception("Starting ASM model failed: negative id received ( " + asmId + " )");
		}
		logger.info("Started ASM Model successfully! Model path: {} with ID: {}", modelPath, asmId);
		this.requiredMonitored.addAll(sim.getMonitored(modelPath));
		//Get the list of monitored functions in the Asmeta model
		if (this.requiredMonitored.isEmpty()) {
			logger.warn("No required monitored vars specified in the model '{}'", modelPath);
		} else {
			logger.info("Required monitored vars for model ID {}: {}", asmId, this.requiredMonitored);
		}
		return asmId;
	}

	private void initializeZmqSockets(ZContext context, String pubBindAddress, String subConnectAddressesString) {
		logger.info("Initializing ZeroMQ sockets...");
		publisher = context.createSocket(SocketType.PUB);
		publisher.bind(pubBindAddress);
		logger.info("PUB Socket bound to Address: {}", pubBindAddress);

		String[] subAddresses = subConnectAddressesString.split(",");
		logger.info("Attempting to create and connect {} SUB socket(s)...", subAddresses.length);
		subscribers.clear();
		for (String addr : subAddresses) {
			String trimmedAddr = addr.trim();
			if (!trimmedAddr.isEmpty()) {
				try {
					ZMQ.Socket sub = context.createSocket(SocketType.SUB);
					sub.connect(trimmedAddr);
					sub.subscribe("".getBytes(ZMQ.CHARSET));
					subscribers.add(sub);
					logger.info("SUB socket connected and subscribed to address {}", trimmedAddr);
				} catch (Exception e) {
					logger.error("Failed to connect SUB socket to address '{}': {}", trimmedAddr, e.getMessage());
				}
			}
		}
		// Initialize environment socket
		if (environmentAddress != null) {
			ZMQ.Socket environmentSocket = context.createSocket(SocketType.SUB);
			environmentSocket.connect(environmentAddress);
			// Subscribe only to ASM_ENVIRONMENT_FUNCTIONS listed in the configuration file
			for (String topic : environmentTopics) {
				environmentSocket.subscribe(topic.getBytes(ZMQ.CHARSET));
			}
			subscribers.add(environmentSocket);
			logger.info("Environment socket connected to address {}", environmentAddress);
			System.out.println("Environment socket connected to address " + environmentAddress);
		}
		logger.info("ZeroMQ Socket initialization completed with {} SUB connections.", subscribers.size());
	}

	private void handleSubscriptionMessages() {
		boolean messageReceived = false;
		for (int i = 0; i < subscribers.size(); i++) {
			ZMQ.Socket sub = subscribers.get(i);
			String message;
			while ((message = sub.recvStr(ZMQ.DONTWAIT)) != null) {
				boolean topicReceived = false;
				for (String topic : this.environmentTopics) {
					if (message.equals(topic)) {
						System.out.println("Received message on SUB socket with topic: " + topic + " on SUB socket #"
								+ i + ": " + message);
						topicReceived = true;
						break;
					}
				}
				if (topicReceived) {
					continue;
				}
				messageReceived = true;
				message = message.trim();
				logger.debug("Received message on SUB socket #{}: {}", i, message);
				System.out.println("Received message on SUB socket #" + i + ": " + message);
				try {
					Map<String, String> receivedData = gson.fromJson(message, mapStringStringType);
					if (receivedData != null) {
						currentMonitoredValues.putAll(receivedData);
						logger.trace("Monitored values updated: {}", currentMonitoredValues);
					} else {
						logger.warn("Parsed JSON was null from SUB socket #{}, msg='{}'", i, message);
					}
				} catch (Exception e) {
					logger.error("Failed to parse incoming JSON on SUB socket #{}: {}", i, e.getMessage());
				}
			}
		}
		if (!messageReceived) {
			logger.trace("No messages received on any SUB socket in this check.");
		}
	}

	private void handlePublisherMessages(RunOutput output) {
		Map<String, Object> response = new HashMap<>();
		response.putAll(output.getOutvalues());
		response.put("asm_status", output.getEsit().toString());
		String jsonResponse = gson.toJson(response);
		logger.info("Publishing output: {}", jsonResponse);
		publisher.send(jsonResponse);
	}

	private void handleUnsafeState(Map<String, String> monitoredForStep) {
		logger.error("ASM state is UNSAFE after step with input: {}", monitoredForStep);
	}

	// private boolean areEnvironmentFunctionsReady() {
	// for (String topic : this.environmentTopics) {
	// if (!currentMonitoredValues.containsKey(topic)) {
	// return false;
	// }
	// }
	// return true;
	// }

	private void initializeStartingValues() {
		for (int i = 0; i < startingInputFunctions.size(); i += 2) {
			String key = startingInputFunctions.get(i);
			String value = startingInputFunctions.get(i + 1);
			currentMonitoredValues.put(key, value);
			logger.info("Initialized starting value for key: {} with value: {}", key, value);
		}
	}

	@Override
	public void run() {
		while (asmId == 0) {
			logger.info("Waiting for ASM ID to be set...");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				logger.error("Thread interrupted while waiting for ASM ID: {}", e.getMessage());
				Thread.currentThread().interrupt();
			}
		}
		try {
			logger.info("Starting zeroMQW run loop for config {}...", CONFIG_FILE_PATH);
			try (ZContext context = new ZContext()) {
				// Use instance variables for addresses
				initializeZmqSockets(context, pubAddress, subConnectAddresses);
				logger.info("Entering main loop for {}...", CONFIG_FILE_PATH);

				// Initialize starting values for the monitored variables
				if (startingInputFunctions != null) {
					initializeStartingValues();
					logger.info("Starting values initialized: {}", currentMonitoredValues);
				} else {
					logger.info("No starting values provided, using environment functions.");
				}

				// Start Loop
				while (true) {
					// Wait for 3 seconds before processing the next step
					Thread.sleep(3000);

					// 1. Handle listen section
					handleSubscriptionMessages();

					// 2. Prepare input for this step
					Map<String, String> monitoredForStep = new HashMap<>();

					// 3. Add the required monitored vars to the monitoredForStep
					for (String key : requiredMonitored) {
						monitoredForStep.put(key, currentMonitoredValues.get(key));
					}

					// if (!areEnvironmentFunctionsReady()) {
					// logger.info("Environment functions are not ready, waiting for them to be
					// ready...");
					// continue;
					// }

					logger.info("All required monitored vars received and non-null ({}), proceeding with ASM step.",
							requiredMonitored);
					logger.debug("Executing ASM step with monitored input: {}", monitoredForStep);

					// 3. Run a step
					RunOutput output = sim.runStep(asmId, monitoredForStep);

					// 4. Process result and publish
					if (output.getEsit() == Esit.SAFE) {
						logger.info("ASM step SAFE. Output: {}", output.getOutvalues());
						logger.info("ASM step SAFE. Controlled values: {}", output.getControlledvalues());

						handlePublisherMessages(output);
					} else {
						handleUnsafeState(monitoredForStep);
					}
					logger.debug("Step processing complete.");
				}

			}

		} catch (Exception e) {
			logger.fatal("CRITICAL ERROR in run loop: {}", e.getMessage(), e);
		} finally {
			logger.info("zeroMQW run method finished for {}.", CONFIG_FILE_PATH);
		}
	}

}