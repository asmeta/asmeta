package org.asmeta.eclipse.editor.preferences;

import static org.asmeta.eclipse.editor.preferences.PreferenceConstants.*;

import java.time.temporal.ChronoUnit;

import org.apache.log4j.Level;
import org.asmeta.eclipse.AsmeeActivator;
import org.asmeta.simulator.Environment;
import org.asmeta.simulator.Environment.TimeMngt;
import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.ColorFieldEditor;
import org.eclipse.jface.preference.ComboFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.FileFieldEditor;
import org.eclipse.jface.preference.RadioGroupFieldEditor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

/**
 * This class represents a preference page that
 * is contributed to the Preferences dialog. By 
 * subclassing <samp>FieldEditorPreferencePage</samp>, we
 * can use the field support built into JFace that allows
 * us to create a page that is small and knows how to 
 * save, restore and apply itself.
 * <p>
 * This page is used to modify preferences only. They
 * are stored in the preference store that belongs to
 * the main plug-in class. That way, preferences can
 * be accessed directly via the preference store.
 */

public class SimulatorPreferencePage
	extends FieldEditorPreferencePage
	implements IWorkbenchPreferencePage {

	public SimulatorPreferencePage() {
		super(GRID);
		setPreferenceStore(AsmeeActivator.getDefault().getPreferenceStore());
		setDescription("Preferences for asmeta simulator");
	}
	
	/**
	 * Creates the field editors. Field editors are abstractions of
	 * the common GUI blocks needed to manipulate various types
	 * of preferences. Each field editor knows how to save and
	 * restore itself.
	 */
	@Override
	public void createFieldEditors() {
/*		addField(new DirectoryFieldEditor(PreferenceConstants.P_PATH, 
				"&Directory preference:", getFieldEditorParent()));
		addField(
			new BooleanFieldEditor(
				PreferenceConstants.P_BOOLEAN,
				"&An example of a boolean preference",
				getFieldEditorParent()));

		addField(new RadioGroupFieldEditor(
				PreferenceConstants.P_CHOICE,
			"An example of a multiple-choice preference",
			1,
			new String[][] { { "&Choice 1", "choice1" }, {
				"C&hoice 2", "choice2" }
		}, getFieldEditorParent()));
		addField(
			new StringFieldEditor(PreferenceConstants.P_STRING, "A &text preference:", getFieldEditorParent()));
		/// ^^^ from wizard
		 * 
		 */
		Composite fieldEdtrPrnt = getFieldEditorParent();
		addField(new BooleanFieldEditor(P_SHUFFLE, "Shuffle choose rule", fieldEdtrPrnt));
		addField(new BooleanFieldEditor(P_CHECK_AXIOMS, "Check invariants", fieldEdtrPrnt));
		addField(new BooleanFieldEditor(P_STOP_UPDATESET_EMPTY, "Stop simulation if the update set is empty", fieldEdtrPrnt));
		addField(new BooleanFieldEditor(P_STOP_UPDATESET_TRIVIAL, "Stop simulation if the update set is trivial", fieldEdtrPrnt));
		// 
		//addField(new RadioGroupFieldEditor(P_TIME_MNGT, "Which time for monitored ASM time", null, fieldEdtrPrnt));
		addField(new RadioGroupFieldEditor(PreferenceConstants.P_TIME_MNGT,
	        "Which time for monitored ASM time:",
	        3,
	        new String[][] {
	          { "ask user", TimeMngt.ask_user.toString()},
	          { "use java time", TimeMngt.use_java_time.toString()},
	          { "auto increment", TimeMngt.auto_increment.toString()}
	        }, fieldEdtrPrnt, true));
		addField(new ComboFieldEditor(P_TIME_UNIT, "Preferred time unit", getTimeUnits(), fieldEdtrPrnt));
		// per logger
		addField(new BooleanFieldEditor(P_DEBUG_USE_EXTERNAL_FILE, "Use prop file for log4j", fieldEdtrPrnt));
		addField(new FileFieldEditor(P_DEBUG_EXTERNAL_FILE, "Use prop file for log4j", fieldEdtrPrnt));		
		//
		addField(new ComboFieldEditor(P_DEBUG_PARSER, "Debug level for parser", getLog4Jlevels(), fieldEdtrPrnt));
		addField(new ComboFieldEditor(P_DEBUG_SIMULATOR, "Debug level for simulator", getLog4Jlevels(), fieldEdtrPrnt));
	}

	private String[][] getTimeUnits() {
		return new String[][] {
			{AUTO, AUTO},
			{MILLIS_STRING,MILLIS_STRING},
			{SECONDS_STRING,SECONDS_STRING}};
	}

	private String[][] getLog4Jlevels() {
		return new String[][] {{Level.DEBUG.toString(),Level.DEBUG.toString()},{Level.INFO.toString(),Level.INFO.toString()},{Level.OFF.toString(),Level.OFF.toString()}};
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
	 */
	@Override
	public void init(IWorkbench workbench) {
	}
}