package org.asmeta.modeladvisor.plugin;

import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.FieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
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

public class AsmetaMAPreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {
	public static final String MP1 = "MP1";
	public static final String MP2 = "MP2";
	public static final String MP3 = "MP3";
	public static final String MP4 = "MP4";
	public static final String MP5 = "MP5";
	public static final String MP6 = "MP6";
	public static final String MP7 = "MP7";
	
	public static final String SHOW_SMV_OUTPUT = "SHOW_SMV_OUTPUT";

	public AsmetaMAPreferencePage() {
		super(GRID);
		setPreferenceStore(AsmetaMAActivator.getDefault().getPreferenceStore());
		setDescription("Preferences for AsmetaMA");
	}

	/**
	 * Creates the field editors. Field editors are abstractions of the common
	 * GUI blocks needed to manipulate various types of preferences. Each field
	 * editor knows how to save and restore itself.
	 */
	@Override
	public void createFieldEditors() {
		Composite fieldEditorParent = getFieldEditorParent();
		addField(new BooleanFieldEditor(MP1, "MP1: No inconsistent update is ever performed", fieldEditorParent));
		addField(new BooleanFieldEditor(MP2, "MP2: Every conditional rule must be complete", fieldEditorParent));
		addField(new BooleanFieldEditor(MP3, "MP3: Every rule can eventually fire", fieldEditorParent));
		addField(new BooleanFieldEditor(MP4, "MP4: No assignment is always trivial", fieldEditorParent));
		addField(new BooleanFieldEditor(MP5,
				"MP5: For every domain element e there exists a location which has value e", fieldEditorParent));
		addField(new BooleanFieldEditor(MP6, "MP6: Every controlled function can take any value in its co-domain",
				fieldEditorParent));
		addField(new BooleanFieldEditor(MP7, "MP7: Every controlled location is updated and every location is read",
				fieldEditorParent));
		SpacerFieldEditor spacer1 = new SpacerFieldEditor(getFieldEditorParent());
			addField(spacer1);
		addField(new BooleanFieldEditor(SHOW_SMV_OUTPUT, "Show NuSMV output", fieldEditorParent));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
	 */
	@Override
	public void init(IWorkbench workbench) {
	}
}
/**
 * A field editor for adding space to a preference page.
 */
class SpacerFieldEditor extends FieldEditor {
	// Implemented as an empty label field editor.
	
	private Label label;
	
	public SpacerFieldEditor(Composite parent) {
		super("spacer","", parent);
	}

	protected void adjustForNumColumns(int numColumns) {
		((GridData) label.getLayoutData()).horizontalSpan = numColumns;
		
	}

	protected void doFillIntoGrid(Composite parent, int numColumns) {
		label = getLabelControl(parent);
		
		GridData gridData = new GridData();
		gridData.horizontalSpan = numColumns;
		gridData.horizontalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = false;
		gridData.verticalAlignment = GridData.CENTER;
		gridData.grabExcessVerticalSpace = false;
		
		label.setLayoutData(gridData);
	}

	protected void doLoad() {
	}

	protected void doLoadDefault() {		
	}

	protected void doStore() {		
	}

	public int getNumberOfControls() {
		return 1;
	}
}