package org.asmeta.runtime_container;
import java.util.Date;
import java.util.Map;
import java.util.TimerTask;


public class MyTimerTask extends TimerTask {
	int id, max;
	RunOutput rout = null;
	Map<String, String> locationValue;
	String modelPath;
	TypeMyTimerTask t;
	
    public MyTimerTask(int id, int max, Map<String, String> locationValue, String modelPath, TypeMyTimerTask t) {
		this.id = id;
		this.max = max;
		this.locationValue = locationValue;
		this.modelPath = modelPath;
		this.t = t;
    }
	
	@Override
    public void run() {
        System.out.println("Timer task started at:"+new Date());
        completeTask();
        //if (rout != null) //commentato per vedere gli errori di nullpointerexception in runE2
        	rout.setResult(true);
        System.out.println("Timer task finished at:"+new Date());
    }

    private void completeTask() {
        ContainerRT instance = ContainerRT.getInstance();
        if(this.t==TypeMyTimerTask.RUNUNTILEMPTY)
        	rout = instance.runUntilEmpty(this.id, this.locationValue, this.modelPath, this.max);
        else
        	rout = instance.runStep(this.id,this.locationValue,this.modelPath);
    }
    
   

}


	