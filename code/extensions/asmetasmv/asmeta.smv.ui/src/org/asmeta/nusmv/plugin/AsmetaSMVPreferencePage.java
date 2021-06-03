package org.asmeta.nusmv.plugin;

import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.FileFieldEditor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

/**
 * This class represents a preference page that is contributed to the
 * Preferences dialog. By subclassing <samp>FieldEditorPreferencePage</samp>, we
 * can use the field support built into JFace that allows us to create a page
 * that is small and knows how to save, restore and apply itself.
 * <p>
 * This page is used to modify preferences only. They are stored in the
 * preference store that belongs to the main plug-in class. That way,
 * preferences can be accessed directly via the preference store.
 */

public class AsmetaSMVPreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {

	public static final String P_NUSMV_PROGRAM = "P_NUSMV_PROGRAM";
	public static final String P_DCX = "P_DCX";
	public static final String P_NC = "P_NC";
	public static final String P_NS = "P_NS";
	public static final String P_KF = "P_KF";
	public static final String P_DONOTF = "P_DONOTF";
	public static final String P_NUXMVTIME = "P_NUXMVTIME";
	public static final String P_NUXMV = "P_NUXMV";
	
	public AsmetaSMVPreferencePage() {
		super(GRID);
		setPreferenceStore(AsmetaSMVActivator.getDefault().getPreferenceStore());
		setDescription("Preferences for AsmetaSMV");
	}

	/**
	 * Creates the field editors. Field editors are abstractions of the common GUI
	 * blocks needed to manipulate various types of preferences. Each field editor
	 * knows how to save and restore itself.
	 */
	@Override
	public void createFieldEditors() {
		Composite fieldEditorParent = getFieldEditorParent();
		// addField(new BooleanFieldEditor(P_DEBUG, "set level to DEBUG",
		// fieldEditorParent));
		addField(new BooleanFieldEditor(P_NC, "do not add the check on integer enum", fieldEditorParent));
		addField(new BooleanFieldEditor(P_NS, "do not simplify the boolean conditions in NuSMV code",fieldEditorParent));
		addField(new BooleanFieldEditor(P_DONOTF, "do not flatten the ASM model before translation", fieldEditorParent));
		addField(new BooleanFieldEditor(P_KF, "keep the NuSMV file", fieldEditorParent));
		addField(new BooleanFieldEditor(P_DCX, "do not show counterexamples", fieldEditorParent));
		addField(new BooleanFieldEditor(P_NUXMV, "use NuXmv", fieldEditorParent));
		addField(new BooleanFieldEditor(P_NUXMVTIME, "use NuXmv with time", fieldEditorParent));
		addField(new FileFieldEditor(P_NUSMV_PROGRAM, "NuSMV executable program", fieldEditorParent));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
	 */
	@Override
	public void init(IWorkbench workbench) {
	}
}
