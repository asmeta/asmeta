package org.asmeta.atgt.ui.preferences;

import org.asmeta.atgt.generator.ConverterCounterExample;
import org.asmeta.atgt.generator.ui.ATGTActivator;
import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

/**
 * Class used to initialize default preference values.
 */
public class PreferenceInitializer extends AbstractPreferenceInitializer {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer#initializeDefaultPreferences()
	 */
	public void initializeDefaultPreferences() {
		IPreferenceStore store =  ATGTActivator. getDefault().getPreferenceStore();
		store.setDefault(PreferenceConstants.P_SHOW_ONLYCHANGES, !ConverterCounterExample.IncludeUnchangedVariables);
	}

}
