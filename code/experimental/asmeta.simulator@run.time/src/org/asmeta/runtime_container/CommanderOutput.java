package org.asmeta.runtime_container;

import java.util.List;

public class CommanderOutput {
	private CommanderStatus status; // gives out how this result should be interpreted
	private int num; // integer result
	private RunOutput ro; // run output result
	private List<String> invl; // list of strings result
	private boolean success; // result of add/update/remove operations on invariants
	private String errorMessage;
	
	public CommanderOutput(CommanderStatus status, int num) {
		this(status,num,null,null,false,null);
	}
	
	public CommanderOutput(CommanderStatus status, List<String> invl) {
		this(status,0,null,invl,false,null);
	}
	
	public CommanderOutput(CommanderStatus status, RunOutput ro) {
		this(status,0,ro,null,false,null);
	}
	public CommanderOutput(CommanderStatus status, String errorMessage) {
		this(status,0,null,null,false,errorMessage);
	}
	
	public CommanderOutput(CommanderStatus status, boolean success) {
		this(status,0,null,null,success,null);
	}
	
	public CommanderOutput(CommanderStatus status, int num, RunOutput ro, List<String> invl, boolean success, String errorMessage) {
		this.ro=ro;
		this.num=num;
		this.status=status;
		this.invl=invl;
		this.success=success;
		this.errorMessage=errorMessage;
	}
	
	public RunOutput getRunOutput() throws CommanderException{
		if (status!=CommanderStatus.RUNOUTPUT)
			throw new CommanderException("Parser output type not correct", status);
		return ro;
	}
	public int getStop() throws CommanderException{
		if (status!=CommanderStatus.STOP)
			throw new CommanderException("Parser output type not correct", status);
		return num;
	}
	public int getInstances() throws CommanderException{
		if (status!=CommanderStatus.INSTANCES)
			throw new CommanderException("Parser output type not correct", status);
		return num;
	}
	public int getID() throws CommanderException{
		if (status!=CommanderStatus.SIM_ID)
			throw new CommanderException("Parser output type not correct", status);
		return num;
	}
	public List<String> getInvList() throws CommanderException{
		if (status!=CommanderStatus.VIEWINV)
			throw new CommanderException("Parser output type not correct", status);
		return invl;
	}
	public boolean getSuccess() throws CommanderException{
		if (status!=CommanderStatus.BOOLRES)
			throw new CommanderException("Parser output type not correct", status);
		return success;
	}
	public String getErrorMessage() throws CommanderException{
		if (status!=CommanderStatus.FAILURE)
			throw new CommanderException("Parser output type not correct", status);
		return errorMessage;
	}
	public CommanderStatus getStatus() {
		return status;
	}
}
