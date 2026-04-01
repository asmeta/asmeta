package org.asmeta.modeladvisor.plugin.handlers;

import java.io.File;

import org.asmeta.eclipse.AsmetaActionHandler;
import org.asmeta.modeladvisor.AsmetaMA;
import org.asmeta.modeladvisor.plugin.AsmetaMAActivator;
import org.asmeta.modeladvisor.plugin.AsmetaMAConsole;
import org.asmeta.modeladvisor.plugin.AsmetaMAPreferencePage;
import org.asmeta.nusmv.util.AsmetaSMVOptions;
import org.eclipse.jface.preference.IPreferenceStore;

/**
 * @see org.eclipse.core.commands.IHandler
 * @see org.eclipse.core.commands.AbstractHandler
 */
public class AsmetaMAHandler extends AsmetaActionHandler {

	public AsmetaMAHandler() {
		super(AsmetaMAConsole.class, "model advising", true);
	}

	@Override
	protected void executeAction(File path) throws Exception {
		// convert the IFile to path
		AsmetaMA asmetaMA = AsmetaMA.buildAsmetaMA(path.getAbsolutePath());
		IPreferenceStore store = AsmetaMAActivator.getDefault().getPreferenceStore();
		asmetaMA.setMetapropertiesExecution(store.getBoolean(AsmetaMAPreferencePage.MP1),
				store.getBoolean(AsmetaMAPreferencePage.MP2), store.getBoolean(AsmetaMAPreferencePage.MP3),
				store.getBoolean(AsmetaMAPreferencePage.MP4), store.getBoolean(AsmetaMAPreferencePage.MP5),
				store.getBoolean(AsmetaMAPreferencePage.MP6), store.getBoolean(AsmetaMAPreferencePage.MP7));
		AsmetaSMVOptions.setPrintNuSMVoutput(store.getBoolean(AsmetaMAPreferencePage.SHOW_SMV_OUTPUT));
		// run the asmeta
		asmetaMA.runCheck();
	}

	@Override
	protected void setUpLoggers() {
		
		// TODO Auto-generated method stub
		
	}
}