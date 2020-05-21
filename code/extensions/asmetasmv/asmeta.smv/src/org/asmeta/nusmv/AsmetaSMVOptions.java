package org.asmeta.nusmv;

// all the options regarding translating and running AsmetaSMV
public class AsmetaSMVOptions {
	
	boolean execute, checkInteger, useNuXmv;


	// for AsmetaMA
	public static boolean doAsmetaMA = false;


	public static boolean keepNuSMVfile;


	private static boolean printCounterExample;

	
	//TODO move as non static
	public static boolean simplify;
	/**use cone of influence ? */
	public static boolean useCoi = true;
	// use flattener (default true, for test cases it can be also false)
	public static boolean FLATTEN = true;
	// 
	private static boolean checkConcrete;
	
	public AsmetaSMVOptions(boolean s, boolean execute, boolean checkInteger, boolean useNuXmv) {
		simplify= s; this.execute = execute; this.checkInteger = checkInteger; this.useNuXmv = useNuXmv;
	}

	public static boolean isCheckConcrete() {
		return checkConcrete;
	}

	public static void setCheckConcrete(boolean checkConcrete) {
		AsmetaSMVOptions.checkConcrete = checkConcrete;
	}

	public static void setPrintCounterExample(boolean printCounterExample) {
		AsmetaSMVOptions.printCounterExample = printCounterExample;
	}

	public static boolean isPrintCounterExample() {
		return AsmetaSMVOptions.printCounterExample;
	}

}
