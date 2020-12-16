package org.asmeta.framework.enforcer;
public abstract class Knowledge {
	
	public Knowledge() {
	}


	/** flag showing whether adaptation is required or not*/
	public boolean adaptationRequired = false;
    
	
	
	public abstract boolean systemStateChanged();
	
	
}


