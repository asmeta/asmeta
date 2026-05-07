package asmeta.ai.propgen.ui.preferences;

import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.jface.preference.*;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.preferences.ScopedPreferenceStore;
import org.osgi.framework.FrameworkUtil;

public class AsmetaAIPreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {

	private RadioGroupFieldEditor llmChoiceEditor;
	private StringFieldEditor httpUrlEditor;
	private StringFieldEditor modelNameEditor;
	private StringFieldEditor apiKeyEditor;

	public AsmetaAIPreferencePage() {
		super(GRID);
		ScopedPreferenceStore scopedPreferenceStore = new ScopedPreferenceStore(
				InstanceScope.INSTANCE,
				String.valueOf(FrameworkUtil.getBundle(getClass()).getBundleId()));
		setPreferenceStore(scopedPreferenceStore);
		setDescription("AsmetaAI property page");
	}

	@Override
	public void createFieldEditors() {
		Composite parent = getFieldEditorParent();

		addField(new IntegerFieldEditor(
				PreferenceConstants.P_NUM_PROP,
				"&Number of properties:",
				parent));

		llmChoiceEditor = new RadioGroupFieldEditor(
				PreferenceConstants.P_LLM_CHOICE,
				"LLM Client",
				1,
				new String[][] {
						{ "&General HTTP Client", "http" },
						{ "&Ollama", "ollama" },
						{ "Open&AI", "openai" }
				},
				parent);
		addField(llmChoiceEditor);

		httpUrlEditor = new StringFieldEditor(
				PreferenceConstants.P_LLM_HTTP_URL,
				"HTTP Client URL:",
				parent);
		addField(httpUrlEditor);

		modelNameEditor = new StringFieldEditor(
				PreferenceConstants.P_MODEL_NAME,
				"Model name:",
				parent);
		addField(modelNameEditor);

		apiKeyEditor = new StringFieldEditor(
				PreferenceConstants.P_API_KEY,
				"API Key:",
				parent);
		addField(apiKeyEditor);

		addField(new ComboFieldEditor(
				PreferenceConstants.P_PROPERTY_TYPE,
				"Temporal property type",
				new String[][] {
						{ "LTL", "ltl" },
						{ "CTL", "ctl" }
				},
				parent));

		// Initialize state
		updateLLMFields(getPreferenceStore().getString(PreferenceConstants.P_LLM_CHOICE));
	}

	@Override
	public void propertyChange(org.eclipse.jface.util.PropertyChangeEvent event) {
		super.propertyChange(event); // IMPORTANT: keep default behavior

		if (event.getSource() == llmChoiceEditor && FieldEditor.VALUE.equals(event.getProperty())) {
			updateLLMFields(String.valueOf(event.getNewValue()));
		}
	}

	private void updateLLMFields(String selectedLLM) {
		boolean isOpenAI = "openai".equals(selectedLLM);
		boolean isOllama = "ollama".equals(selectedLLM);
		boolean isHttp = "http".equals(selectedLLM);

		Composite parent = getFieldEditorParent();

		httpUrlEditor.setEnabled(isHttp || isOllama, parent);
		modelNameEditor.setEnabled(isOpenAI || isOllama, parent);
		apiKeyEditor.setEnabled(isOpenAI || isOllama, parent);

		parent.layout(true, true); // Force immediate UI refresh
	}

	@Override
	public void init(IWorkbench workbench) {
	}
}