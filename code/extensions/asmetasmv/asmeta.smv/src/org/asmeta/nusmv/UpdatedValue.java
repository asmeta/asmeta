package org.asmeta.nusmv;

import java.util.List;

class UpdatedValue {
	private List<String> conds; 
	private String value;
	boolean noConds;

	public UpdatedValue(List<String> conds, String value, boolean noConds) {
		this.conds = conds; 
		this.value = value;
		this.noConds = noConds;
	}

	List<String> getConds() {
		return conds;
	}

	String getValue() {
		return value;
	}

	@Override
	public String toString(){
		return "Conds = " + conds + " value = " + value 
								+ " noConds = " + noConds;
	}
}
