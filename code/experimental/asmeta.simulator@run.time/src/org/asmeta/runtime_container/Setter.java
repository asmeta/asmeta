package org.asmeta.runtime_container;

public class Setter implements ResultSetter {
	boolean flag;
	 
	@Override
	public void setResult(boolean result) {
		flag = result;
	}

	@Override
	public boolean getResult() {
		return flag;
	}
	 	  
 }