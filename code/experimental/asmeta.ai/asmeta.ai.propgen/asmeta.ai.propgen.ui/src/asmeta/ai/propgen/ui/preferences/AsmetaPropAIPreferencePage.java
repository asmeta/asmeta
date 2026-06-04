package asmeta.ai.propgen.ui.preferences;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import asmeta.ai.propgen.llm.OllamaClient;
import asmeta.ai.propgen.llm.OpenAiClient;
import asmeta.ai.propgen.ui.Activator;

public class AsmetaPropAIPreferencePage extends PreferencePage implements IWorkbenchPreferencePage {

	private static final int OLLAMA_INDEX = 0;
	private static final int OPENAI_INDEX = 1;

	private String selectedLlm;
	private ProviderSettings ollamaSettings;
	private ProviderSettings openAiSettings;

	private Combo llmChoiceCombo;
	private Text baseUrlText;
	private Text modelNameText;
	private Combo openAiModelCombo;
	private Composite modelNameComposite;
	private StackLayout modelNameLayout;
	private Text apiKeyText;
	private Spinner numberOfPropertiesSpinner;
	private Spinner maxRetriesSpinner;
	private Spinner timeoutSpinner;
	private Combo propertyTypeCombo;
	private Button debugOutputButton;
	private boolean loadingControls;

	public AsmetaPropAIPreferencePage() {
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
		setDescription("Configure AsmetaPropAI LLM connection and generation defaults.");
	}

	@Override
	protected Control createContents(Composite parent) {
		IPreferenceStore store = getPreferenceStore();
		selectedLlm = LlmPreferenceSupport.normalizeLlmChoice(store.getString(PreferenceConstants.P_LLM_CHOICE));
		ollamaSettings = ProviderSettings.fromStore(store, LlmPreferenceSupport.LLM_OLLAMA);
		openAiSettings = ProviderSettings.fromStore(store, LlmPreferenceSupport.LLM_OPENAI);

		Composite page = new Composite(parent, SWT.NONE);
		page.setLayout(new GridLayout(1, false));
		page.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		createConnectionGroup(page);
		createGenerationGroup(page);
		createOutputGroup(page);

		loadGlobalSettings(store);
		loadProviderSelection();
		loadSelectedProviderSettings();
		return page;
	}

	private void createConnectionGroup(Composite parent) {
		Group group = createGroup(parent, "LLM connection");

		createLabel(group, "Client:");
		llmChoiceCombo = new Combo(group, SWT.READ_ONLY);
		llmChoiceCombo.setItems(new String[] { "Ollama", "OpenAI" });
		llmChoiceCombo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		llmChoiceCombo.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent event) {
				saveCurrentProviderSettings();
				selectedLlm = llmChoiceCombo.getSelectionIndex() == OPENAI_INDEX
						? LlmPreferenceSupport.LLM_OPENAI
						: LlmPreferenceSupport.LLM_OLLAMA;
				loadSelectedProviderSettings();
				markDirty();
			}
		});

		createLabel(group, "Base URL:");
		baseUrlText = createText(group, SWT.BORDER);

		createLabel(group, "Model name:");
		modelNameComposite = new Composite(group, SWT.NONE);
		modelNameLayout = new StackLayout();
		modelNameComposite.setLayout(modelNameLayout);
		modelNameComposite.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

		modelNameText = new Text(modelNameComposite, SWT.BORDER);
		modelNameText.addModifyListener(dirtyModifyListener());

		openAiModelCombo = new Combo(modelNameComposite, SWT.DROP_DOWN);
		openAiModelCombo.setItems(OpenAiClient.getAvailableModelNames());
		openAiModelCombo.addModifyListener(dirtyModifyListener());
		openAiModelCombo.addSelectionListener(dirtySelectionListener());

		createLabel(group, "API key:");
		apiKeyText = createText(group, SWT.BORDER | SWT.PASSWORD);

		new Label(group, SWT.NONE);
		Button restoreProviderDefaultsButton = new Button(group, SWT.PUSH);
		restoreProviderDefaultsButton.setText("Restore defaults");
		restoreProviderDefaultsButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent event) {
				currentSettings().copyFrom(ProviderSettings.defaultsFor(selectedLlm));
				loadSelectedProviderSettings();
				markDirty();
			}
		});
	}

	private void createGenerationGroup(Composite parent) {
		Group group = createGroup(parent, "Generation");

		createLabel(group, "Temporal property type:");
		propertyTypeCombo = new Combo(group, SWT.READ_ONLY);
		propertyTypeCombo.setItems(new String[] { "LTL", "CTL" });
		propertyTypeCombo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		propertyTypeCombo.addSelectionListener(dirtySelectionListener());

		createLabel(group, "Number of properties:");
		numberOfPropertiesSpinner = createPositiveSpinner(group, 1, 100);

		createLabel(group, "Maximum repair attempts:");
		maxRetriesSpinner = createPositiveSpinner(group, 1, 100);

		createLabel(group, "LLM request timeout (seconds):");
		timeoutSpinner = createPositiveSpinner(group, 1, 3600);
	}

	private void createOutputGroup(Composite parent) {
		Group group = createGroup(parent, "Output");
		group.setLayout(new GridLayout(1, false));

		debugOutputButton = new Button(group, SWT.CHECK);
		debugOutputButton.setText("Show AsmetaPropAI debug output in console");
		debugOutputButton.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		debugOutputButton.addSelectionListener(dirtySelectionListener());
	}

	private Group createGroup(Composite parent, String title) {
		Group group = new Group(parent, SWT.NONE);
		group.setText(title);
		group.setLayout(new GridLayout(2, false));
		group.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));
		return group;
	}

	private Label createLabel(Composite parent, String text) {
		Label label = new Label(parent, SWT.NONE);
		label.setText(text);
		label.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false));
		return label;
	}

	private Text createText(Composite parent, int style) {
		Text text = new Text(parent, style);
		text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		text.addModifyListener(dirtyModifyListener());
		return text;
	}

	private Spinner createPositiveSpinner(Composite parent, int minimum, int maximum) {
		Spinner spinner = new Spinner(parent, SWT.BORDER);
		spinner.setMinimum(minimum);
		spinner.setMaximum(maximum);
		spinner.setIncrement(1);
		spinner.setPageIncrement(5);
		spinner.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false));
		spinner.addModifyListener(dirtyModifyListener());
		spinner.addSelectionListener(dirtySelectionListener());
		return spinner;
	}

	private ModifyListener dirtyModifyListener() {
		return event -> markDirty();
	}

	private SelectionAdapter dirtySelectionListener() {
		return new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent event) {
				markDirty();
			}
		};
	}

	private void loadGlobalSettings(IPreferenceStore store) {
		loadingControls = true;
		try {
			propertyTypeCombo.select("ctl".equals(store.getString(PreferenceConstants.P_PROPERTY_TYPE)) ? 1 : 0);
			numberOfPropertiesSpinner.setSelection(valueOrDefault(store.getInt(PreferenceConstants.P_NUM_PROP), 3));
			maxRetriesSpinner.setSelection(valueOrDefault(store.getInt(PreferenceConstants.P_MAX_RETIRES), 3));
			timeoutSpinner.setSelection(valueOrDefault(store.getInt(PreferenceConstants.P_LLM_TIMEOUT_SECONDS), 300));
			debugOutputButton.setSelection(store.getBoolean(PreferenceConstants.P_DEBUG_OUTPUT));
		} finally {
			loadingControls = false;
		}
	}

	private int valueOrDefault(int value, int defaultValue) {
		return value > 0 ? value : defaultValue;
	}

	private void loadProviderSelection() {
		llmChoiceCombo.select(LlmPreferenceSupport.LLM_OPENAI.equals(selectedLlm) ? OPENAI_INDEX : OLLAMA_INDEX);
	}

	private void loadSelectedProviderSettings() {
		loadingControls = true;
		try {
			ProviderSettings settings = currentSettings();
			baseUrlText.setText(settings.baseUrl);
			modelNameText.setText(settings.modelName);
			openAiModelCombo.setText(settings.modelName);
			apiKeyText.setText(settings.apiKey);

			boolean isOpenAi = LlmPreferenceSupport.LLM_OPENAI.equals(selectedLlm);
			modelNameLayout.topControl = isOpenAi ? openAiModelCombo : modelNameText;
			modelNameComposite.layout(true, true);
		} finally {
			loadingControls = false;
		}
	}

	private void saveCurrentProviderSettings() {
		ProviderSettings settings = currentSettings();
		settings.baseUrl = baseUrlText.getText().trim();
		settings.modelName = getDisplayedModelName().trim();
		settings.apiKey = apiKeyText.getText().trim();
	}

	private String getDisplayedModelName() {
		return LlmPreferenceSupport.LLM_OPENAI.equals(selectedLlm)
				? openAiModelCombo.getText()
				: modelNameText.getText();
	}

	private ProviderSettings currentSettings() {
		return LlmPreferenceSupport.LLM_OPENAI.equals(selectedLlm) ? openAiSettings : ollamaSettings;
	}

	@Override
	public boolean performOk() {
		saveCurrentProviderSettings();
		saveSettings();
		return super.performOk();
	}

	@Override
	protected void performApply() {
		saveCurrentProviderSettings();
		saveSettings();
		super.performApply();
	}

	private void saveSettings() {
		IPreferenceStore store = getPreferenceStore();
		store.setValue(PreferenceConstants.P_LLM_CHOICE, selectedLlm);
		ollamaSettings.saveTo(store, LlmPreferenceSupport.LLM_OLLAMA);
		openAiSettings.saveTo(store, LlmPreferenceSupport.LLM_OPENAI);
		store.setValue(PreferenceConstants.P_PROPERTY_TYPE, propertyTypeCombo.getSelectionIndex() == 1 ? "ctl" : "ltl");
		store.setValue(PreferenceConstants.P_NUM_PROP, numberOfPropertiesSpinner.getSelection());
		store.setValue(PreferenceConstants.P_MAX_RETIRES, maxRetriesSpinner.getSelection());
		store.setValue(PreferenceConstants.P_LLM_TIMEOUT_SECONDS, timeoutSpinner.getSelection());
		store.setValue(PreferenceConstants.P_DEBUG_OUTPUT, debugOutputButton.getSelection());
	}

	@Override
	protected void performDefaults() {
		selectedLlm = LlmPreferenceSupport.normalizeLlmChoice(
				getPreferenceStore().getDefaultString(PreferenceConstants.P_LLM_CHOICE));
		ollamaSettings = ProviderSettings.defaultsFor(LlmPreferenceSupport.LLM_OLLAMA);
		openAiSettings = ProviderSettings.defaultsFor(LlmPreferenceSupport.LLM_OPENAI);
		loadProviderSelection();
		loadSelectedProviderSettings();

		IPreferenceStore store = getPreferenceStore();
		propertyTypeCombo.select("ctl".equals(store.getDefaultString(PreferenceConstants.P_PROPERTY_TYPE)) ? 1 : 0);
		numberOfPropertiesSpinner.setSelection(valueOrDefault(store.getDefaultInt(PreferenceConstants.P_NUM_PROP), 3));
		maxRetriesSpinner.setSelection(valueOrDefault(store.getDefaultInt(PreferenceConstants.P_MAX_RETIRES), 3));
		timeoutSpinner.setSelection(valueOrDefault(store.getDefaultInt(PreferenceConstants.P_LLM_TIMEOUT_SECONDS), 300));
		debugOutputButton.setSelection(store.getDefaultBoolean(PreferenceConstants.P_DEBUG_OUTPUT));
		markDirty();
		super.performDefaults();
	}

	private void markDirty() {
		if (loadingControls) {
			return;
		}
		setValid(true);
		if (getContainer() != null) {
			getContainer().updateButtons();
		}
	}

	@Override
	public void init(IWorkbench workbench) {
	}

	private static final class ProviderSettings {
		private String baseUrl;
		private String modelName;
		private String apiKey;

		private ProviderSettings(String baseUrl, String modelName, String apiKey) {
			this.baseUrl = emptyIfNull(baseUrl);
			this.modelName = emptyIfNull(modelName);
			this.apiKey = emptyIfNull(apiKey);
		}

		private static ProviderSettings fromStore(IPreferenceStore store, String llmChoice) {
			return new ProviderSettings(
					store.getString(LlmPreferenceSupport.baseUrlPreferenceFor(llmChoice)),
					store.getString(LlmPreferenceSupport.modelNamePreferenceFor(llmChoice)),
					store.getString(LlmPreferenceSupport.apiKeyPreferenceFor(llmChoice)));
		}

		private static ProviderSettings defaultsFor(String llmChoice) {
			if (LlmPreferenceSupport.LLM_OPENAI.equals(llmChoice)) {
				return new ProviderSettings(OpenAiClient.DEFAULT_BASE_URL, OpenAiClient.DEFAULT_MODEL_NAME, "");
			}
			return new ProviderSettings(OllamaClient.DEFAULT_BASE_URL, OllamaClient.DEFAULT_MODEL, "");
		}

		private void saveTo(IPreferenceStore store, String llmChoice) {
			store.setValue(LlmPreferenceSupport.baseUrlPreferenceFor(llmChoice), baseUrl);
			store.setValue(LlmPreferenceSupport.modelNamePreferenceFor(llmChoice), modelName);
			store.setValue(LlmPreferenceSupport.apiKeyPreferenceFor(llmChoice), apiKey);
		}

		private void copyFrom(ProviderSettings other) {
			baseUrl = other.baseUrl;
			modelName = other.modelName;
			apiKey = other.apiKey;
		}

		private static String emptyIfNull(String value) {
			return value == null ? "" : value.trim();
		}
	}
}
