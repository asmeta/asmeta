package org.asmeta.validator.ui.preferences;

import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.jface.preference.*;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.preferences.ScopedPreferenceStore;
import org.osgi.framework.FrameworkUtil;

/** preference page for the validator
 */

public class AsmetaVPreferencePage
	extends FieldEditorPreferencePage
	implements IWorkbenchPreferencePage {

	public AsmetaVPreferencePage() {
		super(GRID);
		ScopedPreferenceStore scopedPreferenceStore = new ScopedPreferenceStore(InstanceScope.INSTANCE, String.valueOf(FrameworkUtil.getBundle(getClass()).getBundleId()));
		setPreferenceStore(scopedPreferenceStore);
		setDescription("Preferences for the AsmetaV validator");
	}
	
	/**
	 * Creates the field editors. Field editors are abstractions of
	 * the common GUI blocks needed to manipulate various types
	 * of preferences. Each field editor knows how to save and
	 * restore itself.
	 */
	public void createFieldEditors() {
		addField(
			new BooleanFieldEditor(
				PreferenceConstants.P_COMPUTE_MUTATIONSCORE,
				"Compyte mitation score together with coverage",
				getFieldEditorParent()));
	}

	@Override
	public void init(IWorkbench workbench) {
	}
	
}