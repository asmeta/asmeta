package org.asmeta.nusmv.main;

import org.asmeta.nusmv.main.AsmetaSMV;
import org.asmeta.nusmv.main.AsmetaSMV.ModelCheckerMode;
import org.asmeta.nusmv.util.AsmetaSMVOptions;

public class ICSA2018 {

	public static void main(String[] args) throws Exception {
		AsmetaSMVOptions options = new AsmetaSMVOptions();
		options.keepNuSMVfile = true;
		AsmetaSMV.modelCheckerMode = ModelCheckerMode.CTL;
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
