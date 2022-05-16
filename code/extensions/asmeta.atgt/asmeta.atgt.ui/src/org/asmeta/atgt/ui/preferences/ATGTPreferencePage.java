package org.asmeta.atgt.ui.preferences;

import org.asmeta.atgt.generator.ui.ATGTActivator;
import org.eclipse.jface.preference.*;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.IWorkbench;

public class ATGTPreferencePage
	extends FieldEditorPreferencePage
	implements IWorkbenchPreferencePage {

	public ATGTPreferencePage() {
		super(GRID);
		setPreferenceStore(ATGTActivator.getDefault().getPreferenceStore());
		setDescription("ATGT preference pages");
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
				PreferenceConstants.P_SHOW_ONLYCHANGES,
				"save only changes in the avalla test",
				getFieldEditorParent()));
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
	 */
	public void init(IWorkbench workbench) {
	}
	
}