package org.asmeta.flattener.plugin;

import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
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

public class AsmetaFlatPreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {
	public static final String FORALL = "ForallRules";
	public static final String MACRO_CALL = "MacroCallRules";
	public static final String CONDITIONAL = "ConditionalRules";
	public static final String ARGS = "FunctionArguments";
	public static final String LET = "LetRule";
	public static final String CHOOSE = "ChooseRule";
	public static final String CASE = "CaseRule";

	public AsmetaFlatPreferencePage() {
		super(GRID);
		setPreferenceStore(AsmetaFlatActivator.getDefault().getPreferenceStore());
		setDescription("Preferences for " + AsmetaFlatActivator.NAME);
	}

	/**
	 * Creates the field editors. Field editors are abstractions of the common GUI
	 * blocks needed to manipulate various types of preferences. Each field editor
	 * knows how to save and restore itself.
	 */
	@Override
	public void createFieldEditors() {
		Composite fieldEditorParent = getFieldEditorParent();
		addField(new BooleanFieldEditor(MACRO_CALL, "Macro call rule", fieldEditorParent));
		addField(new BooleanFieldEditor(FORALL, "Forall rule", fieldEditorParent));
		addField(new BooleanFieldEditor(CHOOSE, "Choose rule", fieldEditorParent));
		addField(new BooleanFieldEditor(ARGS, "Function arguments", fieldEditorParent));
		addField(new BooleanFieldEditor(LET, "Let rule", fieldEditorParent));
		addField(new BooleanFieldEditor(CASE, "Case rule", fieldEditorParent));
		addField(new BooleanFieldEditor(CONDITIONAL, "Conditional rule", fieldEditorParent));
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
