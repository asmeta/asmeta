package org.asmeta.runtime_container;

public class CommanderOutput {
	private CommanderStatus status;
	private int num;
	private RunOutput ro; 
	
	public CommanderOutput(CommanderStatus status, int num, RunOutput ro) {
		this.ro=ro;
		this.num=num;
		this.status=status;
	}
	
	public RunOutput getRunOutput() throws CommanderException{
		if (status!=CommanderStatus.RUNOUTPUT)
			throw new CommanderException("Parser output type not correct");
		return ro;
	}
	public int getStop() throws CommanderException{
		if (status!=CommanderStatus.STOP)
			throw new CommanderException("Parser output type not correct");
		return num;
	}
	public int getInstances() throws CommanderException{
		if (status!=CommanderStatus.INSTANCES)
			throw new CommanderException("Parser output type not correct");
		return num;
	}
	public int getID() throws CommanderException{
		if (status!=CommanderStatus.SIM_ID)
			throw new CommanderException("Parser output type not correct");
		return num;
	}
}
