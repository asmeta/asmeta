package org.asmeta.framework.enforcer;
public abstract class Knowledge {
	
	public Knowledge() {
	}


	/** flag showing whether adaptation is required or not*/
	public boolean adaptationRequired = false;
    
	
	/** flag showing whether the observed part of the managed system has changed or not; default: true*/
	public boolean systemStateChanged() { 
		return true; 
	
	}
	
	
}


