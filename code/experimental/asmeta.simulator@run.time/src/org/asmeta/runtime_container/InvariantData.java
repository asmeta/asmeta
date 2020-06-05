package org.asmeta.runtime_container;

import java.util.ArrayList;
import java.util.List;

public class InvariantData {
	List<String> variables = new ArrayList<String>();
	List<String> invarList = new ArrayList<String>();
	
	public List<String> getinvarList()
	{
		return invarList;
	}
	public List<String> getvariables()
	{
		return variables;
	}
	@Override
	public String toString() {
		return "InvariantData\nVariables: " + variables + "\nInvariants: " + invarList;
	}
}
