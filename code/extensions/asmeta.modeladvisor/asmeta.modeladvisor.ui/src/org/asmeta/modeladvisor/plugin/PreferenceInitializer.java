package org.asmeta.modeladvisor.plugin;
  
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
	@Override
	public void initializeDefaultPreferences() {
		IPreferenceStore store = AsmetaMAActivator.getDefault().getPreferenceStore();
		store.setDefault(AsmetaMAPreferencePage.MP1, true);
		store.setDefault(AsmetaMAPreferencePage.MP2, true);
		store.setDefault(AsmetaMAPreferencePage.MP3, true);
		store.setDefault(AsmetaMAPreferencePage.MP4, true);
		store.setDefault(AsmetaMAPreferencePage.MP5, true);
		store.setDefault(AsmetaMAPreferencePage.MP6, true);
		store.setDefault(AsmetaMAPreferencePage.MP7, true);
		store.setDefault(AsmetaMAPreferencePage.SHOW_SMV_OUTPUT, false);
	}
}
