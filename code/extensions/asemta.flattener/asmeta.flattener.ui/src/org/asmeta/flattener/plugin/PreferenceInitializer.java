package org.asmeta.flattener.plugin;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

/**
 * Class used to initialize default preference values.
 */
public class PreferenceInitializer extends AbstractPreferenceInitializer {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer#
	 * initializeDefaultPreferences()
	 */
	@Override
	public void initializeDefaultPreferences() {
		IPreferenceStore store = AsmetaFlatActivator.getDefault().getPreferenceStore();
		store.setDefault(AsmetaFlatPreferencePage.MACRO_CALL, true);
		store.setDefault(AsmetaFlatPreferencePage.FORALL, true);
		store.setDefault(AsmetaFlatPreferencePage.CHOOSE, true);
		store.setDefault(AsmetaFlatPreferencePage.ARGS, true);
		store.setDefault(AsmetaFlatPreferencePage.LET, true);
		store.setDefault(AsmetaFlatPreferencePage.CASE, true);
		store.setDefault(AsmetaFlatPreferencePage.CONDITIONAL, true);
	}
}
