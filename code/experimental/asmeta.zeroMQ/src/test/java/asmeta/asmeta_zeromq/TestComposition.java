package asmeta.asmeta_zeromq;


public class TestComposition {
	
	public static void main(String[] args) {
		
		ZeroMQWA producer = new ZeroMQWA("/producerconsumer/zmq_config_producer.properties");
		Thread thread1 = new Thread(producer);
		
		ZeroMQWA consumer = new ZeroMQWA("/producerconsumer/zmq_config_consumer.properties");
		Thread thread2 = new Thread(consumer);
		
		EnvironmentZMQ env = new EnvironmentZMQ("producerconsumer/zmq_config_environment.properties");
		Thread thread3 = new Thread(env);
		
		
		thread1.start();
		thread2.start();
		thread3.start();
	}
	


}
