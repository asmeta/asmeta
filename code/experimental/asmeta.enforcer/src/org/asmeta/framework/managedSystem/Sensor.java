package controller;

import java.io.IOException;

import controller.comms.Client;

public class Sensor {

	/** Communication handles*/
    private LocalManagedSystem client;			

    /** Command to send to managed system*/
    private String command = "SENSORS";
    
    /** Reply from managed system*/
    private String reply;
    
    /**
     * Constructor: create a new sensor
     */
	public Sensor(LocalManagedSystem client) {
		//assign client handler
		this.client = client;		
	}

	
	protected void run (){
		try {
			reply = client.write(command); 
			parseReply(reply);
		} 
		catch (IOException e) {
			e.printStackTrace();
			reply = "ERROR";
		}
	}	
	
	
	private void parseReply (String reply){
		//SENSOR1:RATE:#READINGS:#SUCC_READINGS,SENSOR2:RATE:#READINGS:#SUCC_READINGS,...
		String[] sensorsStr = reply.split(",");//multiple SENSOR1=RATE:#READINGS:#SUCC_READINGS
		
		for (String str : sensorsStr){
			String[] sensorData = str.split(":");
			//sensorData[0] --> sensor name
			//sensorData[1] --> sensor average reading rate
			//sensorData[2] --> readings since previous invocation
			//sensorData[3] --> accurate readings since previous invocation
			
			//sensor rate cannot be zero --> use a very small value 
			double rate = Double.parseDouble(sensorData[1]);
			if (rate == 0)
				rate =0.2;
				
			Knowledge.getInstance().setSensorRate(sensorData[0], rate);
			Knowledge.getInstance().setSensorReadings(sensorData[0], Integer.parseInt(sensorData[2]));
			Knowledge.getInstance().setSensorAccurateReadings(sensorData[0], Integer.parseInt(sensorData[3]));
			
		}
	}
	
	
	public String getReply(){
		return this.reply;
	}
}
