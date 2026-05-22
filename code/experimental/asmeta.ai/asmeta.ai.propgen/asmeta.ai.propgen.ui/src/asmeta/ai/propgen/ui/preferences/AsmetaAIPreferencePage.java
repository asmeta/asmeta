package asmeta.ai.propgen.ui.preferences;

import org.eclipse.jface.preference.*;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import asmeta.ai.propgen.llm.OllamaClient;
import asmeta.ai.propgen.llm.OpenAiClient;
import asmeta.ai.propgen.ui.Activator;

public class AsmetaAIPreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {

	private RadioGroupFieldEditor llmChoiceEditor;
	private StringFieldEditor httpUrlEditor;
	private Label modelNameLabel;
	private Text modelNameText;
	private Combo openAiModelCombo;
	private Composite modelNameComposite;
	private StackLayout modelNameLayout;
	private StringFieldEditor apiKeyEditor;

	public AsmetaAIPreferencePage() {
		super(GRID);
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
		setDescription("AsmetaAI property page");
	}

	@Override
	protected Control createContents(Composite parent) {
		Control contents = super.createContents(parent);
		updateLLMFields(getPreferenceStore().getString(PreferenceConstants.P_LLM_CHOICE), true);
		return contents;
	}

	@Override
	public void createFieldEditors() {
		Composite parent = getFieldEditorParent();

		addField(new IntegerFieldEditor(
				PreferenceConstants.P_NUM_PROP,
				"&Number of properties:",
				parent));

		addField(new IntegerFieldEditor(
				PreferenceConstants.P_MAX_RETIRES,
				"&Maximum number of attempts to generate a parsable formula:",
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

		createModelNameControls(parent);

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

		addField(new BooleanFieldEditor(
				PreferenceConstants.P_DEBUG_OUTPUT,
				"Show AsmetaAI debug output in console",
				parent));

		// Initialize state
		updateLLMFields(getPreferenceStore().getString(PreferenceConstants.P_LLM_CHOICE), false);
	}

	@Override
	public void propertyChange(org.eclipse.jface.util.PropertyChangeEvent event) {
		super.propertyChange(event);

		if (event.getSource() == llmChoiceEditor && FieldEditor.VALUE.equals(event.getProperty())) {
			updateLLMFields(String.valueOf(event.getNewValue()), true);
		}
	}

	@Override
	protected void performDefaults() {
		super.performDefaults();
		String selectedLLM = getPreferenceStore().getDefaultString(PreferenceConstants.P_LLM_CHOICE);
		setModelName(getDefaultModelName(selectedLLM));
		updateLLMFields(selectedLLM, false);
	}

	@Override
	public boolean performOk() {
		boolean ok = super.performOk();
		if (ok) {
			storeModelName();
		}
		return ok;
	}

	@Override
	protected void performApply() {
		super.performApply();
		storeModelName();
	}

	private void updateLLMFields(String selectedLLM, boolean updateUrlDefault) {
		boolean isOpenAI = "openai".equals(selectedLLM);
		boolean isOllama = "ollama".equals(selectedLLM);
		boolean isHttp = "http".equals(selectedLLM);

		Composite parent = getFieldEditorParent();

		httpUrlEditor.setEnabled(isHttp || isOllama || isOpenAI, parent);
		modelNameLabel.setEnabled(isOpenAI || isOllama);
		modelNameText.setEnabled(isOllama);
		openAiModelCombo.setEnabled(isOpenAI);
		modelNameLayout.topControl = isOpenAI ? openAiModelCombo : modelNameText;
		apiKeyEditor.setEnabled(isOpenAI || isOllama, parent);
		if (updateUrlDefault && shouldUpdateUrlDefault(httpUrlEditor.getStringValue())) {
			httpUrlEditor.setStringValue(getDefaultUrl(selectedLLM));
		}
		if (updateUrlDefault) {
			updateModelNameDefault(selectedLLM);
		}

		modelNameComposite.layout(true, true);
		parent.layout(true, true); // Force immediate UI refresh
	}

	private boolean shouldUpdateUrlDefault(String currentUrl) {
		return currentUrl == null
				|| currentUrl.isBlank()
				|| OpenAiClient.DEFAULT_BASE_URL.equals(currentUrl)
				|| OllamaClient.DEFAULT_BASE_URL.equals(currentUrl);
	}

	private String getDefaultUrl(String selectedLLM) {
		if ("openai".equals(selectedLLM)) {
			return OpenAiClient.DEFAULT_BASE_URL;
		}
		if ("ollama".equals(selectedLLM)) {
			return OllamaClient.DEFAULT_BASE_URL;
		}
		return "";
	}

	private void createModelNameControls(Composite parent) {
		modelNameLabel = new Label(parent, SWT.NONE);
		modelNameLabel.setText("Model name:");

		modelNameComposite = new Composite(parent, SWT.NONE);
		modelNameLayout = new StackLayout();
		modelNameComposite.setLayout(modelNameLayout);
		modelNameComposite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		modelNameText = new Text(modelNameComposite, SWT.BORDER);

		openAiModelCombo = new Combo(modelNameComposite, SWT.DROP_DOWN | SWT.READ_ONLY);
		for (String modelName : OpenAiClient.getAvailableModelNames()) {
			openAiModelCombo.add(modelName);
		}

		setModelName(getPreferenceStore().getString(PreferenceConstants.P_MODEL_NAME));

		modelNameText.addModifyListener(event -> updateApplyButton());
		openAiModelCombo.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent event) {
				updateApplyButton();
			}
		});
	}

	private String getDefaultModelName(String selectedLLM) {
		if ("openai".equals(selectedLLM)) {
			return OpenAiClient.DEFAULT_MODEL_NAME;
		}
		if ("ollama".equals(selectedLLM)) {
			return OllamaClient.DEFAULT_MODEL_NAME;
		}
		return "";
	}

	private void updateModelNameDefault(String selectedLLM) {
		String currentModelName = getModelName();
		if ("openai".equals(selectedLLM)) {
			if (isOpenAiModelName(currentModelName)) {
				setModelName(currentModelName);
			} else {
				setModelName(OpenAiClient.DEFAULT_MODEL_NAME);
			}
			return;
		}
		if ("ollama".equals(selectedLLM)) {
			if (currentModelName == null || currentModelName.isBlank() || isOpenAiModelName(currentModelName)
					|| OllamaClient.DEFAULT_MODEL_NAME.equals(currentModelName)) {
				setModelName(OllamaClient.DEFAULT_MODEL_NAME);
			}
			return;
		}
		setModelName("");
	}

	private boolean isOpenAiModelName(String modelName) {
		for (String availableModelName : OpenAiClient.getAvailableModelNames()) {
			if (availableModelName.equals(modelName)) {
				return true;
			}
		}
		return false;
	}

	private String getModelName() {
		String selectedLLM = llmChoiceEditor.getSelectionValue();
		if ("openai".equals(selectedLLM)) {
			int selectionIndex = openAiModelCombo.getSelectionIndex();
			return selectionIndex >= 0 ? openAiModelCombo.getItem(selectionIndex) : OpenAiClient.DEFAULT_MODEL_NAME;
		}
		if ("ollama".equals(selectedLLM)) {
			return modelNameText.getText();
		}
		return "";
	}

	private void setModelName(String modelName) {
		String value = modelName == null ? "" : modelName;
		modelNameText.setText(value);
		int openAiIndex = openAiModelCombo.indexOf(value);
		if (openAiIndex < 0) {
			openAiIndex = openAiModelCombo.indexOf(OpenAiClient.DEFAULT_MODEL_NAME);
		}
		if (openAiIndex >= 0) {
			openAiModelCombo.select(openAiIndex);
		}
	}

	private void storeModelName() {
		getPreferenceStore().setValue(PreferenceConstants.P_MODEL_NAME, getModelName());
	}

	@Override
	public void init(IWorkbench workbench) {
	}
}
