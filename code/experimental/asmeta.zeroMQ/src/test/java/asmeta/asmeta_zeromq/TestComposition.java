package asmeta.asmeta_zeromq;

import org.junit.Test;

public class TestComposition {

	@Test
	public void testProdCons() throws Exception {
		ZeroMQWA producer = new ZeroMQWA("/producerconsumer/zmq_config_producer.properties");
		producer.run();
		ZeroMQWA consumer = new ZeroMQWA("/producerconsumer/zmq_config_consumer.properties");
		consumer.run();
		EnvironmentZMQ env = new EnvironmentZMQ("producerconsumer/zmq_config_environment.properties");
		env.startExecution();
	}

}
