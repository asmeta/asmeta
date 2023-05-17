package org.asmeta.nusmv;

public class ICSA2018 {

	public static void main(String[] args) throws Exception {
		AsmetaSMVOptions options = new AsmetaSMVOptions();
		options.keepNuSMVfile = true;
		//Class<? extends Flattener>[] oldALL_FLATTENERS = MapVisitor.ALL_FLATTENERS;
		options.setRunNuSMV(true); //execute the NuSMV model after the mapping
		options.setPrintCounterExample(true);
		options.setPrintCounterExample(false);
		options.setUseNuXmv(false);
		AsmetaSMV as = new AsmetaSMV("examples/ComfortableHeating_ref_MC.asm");
		as.translation();
		as.createNuSMVfile();
		AsmetaSMVOptions.setPrintNuSMVoutput(true);
		as.executeNuSMV();
		
	}
}
