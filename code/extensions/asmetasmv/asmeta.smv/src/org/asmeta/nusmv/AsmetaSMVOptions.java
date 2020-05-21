package org.asmeta.nusmv;

// all the options regarding translating and running AsmetaSMV
public class AsmetaSMVOptions {
	
	boolean simplify, execute, checkInteger, useNuXmv;
	// use flattener (default true, for test cases it can be also false)
	public static boolean FLATTEN = true;
	
	public AsmetaSMVOptions(boolean simplify, boolean execute, boolean checkInteger, boolean useNuXmv) {
		this.simplify= simplify; this.execute = execute; this.checkInteger = checkInteger; this.useNuXmv = useNuXmv;
	}

}
