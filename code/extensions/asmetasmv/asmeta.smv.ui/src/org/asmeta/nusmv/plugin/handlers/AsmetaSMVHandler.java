package org.asmeta.nusmv.plugin.handlers;

import java.io.File;

import org.asmeta.eclipse.AsmetaActionHandler;
import org.asmeta.eclipse.AsmetaConsole;
import org.asmeta.nusmv.main.AsmetaSMV;
import org.asmeta.nusmv.plugin.AsmetaSMVActivator;
import org.asmeta.nusmv.plugin.AsmetaSMVConsole;
import org.asmeta.nusmv.plugin.AsmetaSMVPreferencePage;
import org.asmeta.nusmv.util.AsmetaSMVOptions;
import org.eclipse.jface.preference.IPreferenceStore;

/**
 * @see org.eclipse.core.commands.IHandler
 * @see org.eclipse.core.commands.AbstractHandler
 */
abstract class AsmetaSMVHandler extends AsmetaActionHandler {

	protected AsmetaSMVHandler(String action) {
		super(AsmetaSMVConsole.class, action, true);
	}

	abstract void exec(AsmetaSMV asmetaSMV) throws Exception;

	@Override
	protected void executeAction(File path) throws Exception {
		AsmetaSMV asmetaSMV = new AsmetaSMV(path);
		IPreferenceStore store = AsmetaSMVActivator.getDefault().getPreferenceStore();
		AsmetaSMVOptions.keepNuSMVfile = store.getBoolean(AsmetaSMVPreferencePage.P_KF);
		AsmetaSMVOptions.setCheckConcrete(!store.getBoolean(AsmetaSMVPreferencePage.P_NC));
		AsmetaSMVOptions.simplify = !store.getBoolean(AsmetaSMVPreferencePage.P_NS);
		AsmetaSMVOptions.setPrintCounterExample(!store.getBoolean(AsmetaSMVPreferencePage.P_DCX));
		AsmetaSMVOptions.setUseNuXmvTime(store.getBoolean(AsmetaSMVPreferencePage.P_NUXMVTIME));
		AsmetaSMVOptions.setUseNuXmv(store.getBoolean(AsmetaSMVPreferencePage.P_NUXMV));
		String nusmvPath = store.getString(AsmetaSMVPreferencePage.P_NUSMV_PROGRAM);
		if (!nusmvPath.equals("") && new File(nusmvPath).exists()) {
			AsmetaSMVOptions.setSolverPath(nusmvPath);
		}
		//
		AsmetaSMVOptions.FLATTEN = !store.getBoolean(AsmetaSMVPreferencePage.P_DONOTF);
		asmetaSMV.translation();
		asmetaSMV.createNuSMVfile();
		exec(asmetaSMV);
	}

	@Override
	protected void setUpLoggers() {	
		
	}
}