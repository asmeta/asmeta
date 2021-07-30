package org.asmeta.nusmv;

// all the options regarding translating and running AsmetaSMV
public class AsmetaSMVOptions {

	// add the case construct when modifying an integer
	// e.g. x = e --> CASE e in Dom(x) : e ; TRUE: x ESAC
	static boolean checkConcrete;
	boolean execute; // check is it the same as runNusmv?
	private static boolean printNuSMVoutput;
	public static String solverPath;
	public static boolean simplifyDerived = true;
	// for AsmetaMA
	public static boolean doAsmetaMA = false;
	//
	public static boolean keepNuSMVfile;
	private static boolean printCounterExample;

	// TODO move as non static
	public static boolean simplify;
	/** use cone of influence ? */
	public static boolean useCoi = true;
	// use flattener (default true, for test cases it can be also false)
	public static boolean FLATTEN = true;
	
	private static boolean runNuSMV;
	private static boolean useNuXmv;
	private static boolean useNuXmvTime;

	public AsmetaSMVOptions(boolean s, boolean execute, boolean checkConcrete, boolean useNuXmv, boolean useNuXmvTime) {
		simplify = s;
		this.execute = execute;
		AsmetaSMVOptions.checkConcrete = checkConcrete;
		AsmetaSMVOptions.useNuXmv = useNuXmv;
		AsmetaSMVOptions.useNuXmvTime = useNuXmvTime;
	}

	public AsmetaSMVOptions() {
		this(true, false, true, false,false);
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

	public static String getSolverPath() {
		return AsmetaSMVOptions.solverPath;
	}

	public static boolean isPrintNuSMVoutput() {
		return AsmetaSMVOptions.printNuSMVoutput;
	}

	public static void setPrintNuSMVoutput(boolean printNuSMVoutput) {
		AsmetaSMVOptions.printNuSMVoutput = printNuSMVoutput;
	}

	public static void setSolverPath(String solverPath) {
		AsmetaSMVOptions.solverPath = solverPath;
	}
	public static boolean isRunNuSMV() {
		return runNuSMV;
	}

	public static void setRunNuSMV(boolean runNuSMV) {
		AsmetaSMVOptions.runNuSMV = runNuSMV;
	}

	public static boolean isUseNuXmv() {
		return useNuXmv;
	}

	public static void setUseNuXmv(boolean useNuXmv) {
		AsmetaSMVOptions.useNuXmv = useNuXmv;
	}
	
	public static boolean isUseNuXmvTime() {
		return useNuXmvTime;
	}

	public static void setUseNuXmvTime(boolean useNuXmvTime) {
		AsmetaSMVOptions.useNuXmvTime = useNuXmvTime;
	}

}
