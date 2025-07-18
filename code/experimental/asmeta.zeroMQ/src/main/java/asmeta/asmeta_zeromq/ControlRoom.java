package asmeta.asmeta_zeromq;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

import com.google.gson.Gson;

public class ControlRoom {
    private static final Logger logger = LogManager.getLogger(ControlRoom.class);

    private static final String CONTROLROOM_CONFIG_PATH = "/controlroom_config.properties";
    private static final String listenAddress = "SUB_ADDRESSES";
    private Gson gson;
    private Type mapStringStringType;
    private Properties properties;

    private List<ZMQ.Socket> subscribers;

    private Properties loadConfig () throws IOException, NullPointerException {
        properties = new Properties();

        try (InputStream input = ControlRoom.class.getResourceAsStream(CONTROLROOM_CONFIG_PATH)){
            if ( input == null ) {
                logger.error("No load config found at {}", CONTROLROOM_CONFIG_PATH);
                System.out.println("No load config found at " + CONTROLROOM_CONFIG_PATH);
                throw new IOException("Config file not found at: " + CONTROLROOM_CONFIG_PATH);
            }
            properties.load(input);
            logger.debug("Config file loaded");

            if (properties.getProperty(listenAddress) == null) {
                logger.error("No listen address found");
                System.out.println("No listen address found");
                throw new NullPointerException("No listen address found");
            }

        } catch (IOException e) {
            logger.error("There was an error processing the configuration file: " + e.getMessage());
            throw e;
        } catch (NullPointerException e) {
            logger.error("There was an error processing the addresses connection configuration: " + e.getMessage());
            throw e;
        }

        logger.info("Configuration Loaded Successfully!");
        logger.info(" * {} = {}", listenAddress, properties.getProperty(listenAddress));
        return properties;
    }


    private void initializeSubSockets(ZContext context) {
        String subAddressesString = properties.getProperty(listenAddress);
        subscribers = new ArrayList<>();

        String[] subAddresses = subAddressesString.split(",");
        for (String address : subAddresses) {
            String trimmedAddress = address.trim();
            logger.info("Trying to connect to: {}", trimmedAddress);
            if (!trimmedAddress.isEmpty()){
                try {
                    ZMQ.Socket newSubscriber = context.createSocket(SocketType.SUB);
                    newSubscriber.connect(trimmedAddress);
                    logger.info("Connected to address '{}'", trimmedAddress);
                    newSubscriber.subscribe("".getBytes(ZMQ.CHARSET));
                    subscribers.add(newSubscriber);
                    logger.info("Subscribed to address '{}'", trimmedAddress);
                } catch (Exception e) {
                    logger.error("Failed to connect or subscribe to address '{}': {}", trimmedAddress, e.getMessage());
                }
            }
        }

        logger.info("Connection and subscription phase terminated for all addresses.");

        logger.info("ZeroMQ Sockets initialization completed.");
    }

    private void handleSubscriptionMessages() {
        for (ZMQ.Socket sub : subscribers) {
            String message = sub.recvStr(ZMQ.DONTWAIT);
            if (message != null) {
                message = message.trim();
                System.out.println("A message has arrived on one of the subscriptions: " + message);
                logger.debug("A message has arrived on one of the subscriptions: {}", message);

                // try {
                //     Map<String, String> receivedMessage = gson.fromJson(message, mapStringStringType);
                //     logger.info(message);

                // } catch (Exception e) {
                // }
            }
        }
    }

    private void listen() {


        try (ZContext context = new ZContext() ) {
            initializeSubSockets(context);

            logger.info("Starting listening...");
            System.out.println("Starting listening...");

            while(!Thread.currentThread().isInterrupted()) {
                handleSubscriptionMessages();
            }

        } catch (Exception e) {

        }
    }

    public static void main(String[] args) {
        System.out.println("Control Room started");
        logger.info("Control Room started");
        ControlRoom controlRoom = new ControlRoom();

        // load config
        try {
            controlRoom.loadConfig();
        } catch (IOException e) {
            System.out.println("There was an error processing the configuration file: " + e.getMessage());
            logger.error("There was an error processing the configuration file: " + e.getMessage());
        } catch (NullPointerException e) {
            System.out.println("There was an error processing the addresses connection configuration: " + e.getMessage());
            logger.error("There was an error processing the addresses connection configuration: " + e.getMessage());
        }

        
        // listen for messages
        controlRoom.listen();
    }
}