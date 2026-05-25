package asmeta.ai.propgen.ui.services;

import java.io.File;
import java.util.List;

import org.eclipse.jface.preference.IPreferenceStore;

import asmeta.ai.propgen.PropertyGenerationListener;
import asmeta.ai.propgen.PropertyGenerationSession;
import asmeta.ai.propgen.PropertyGenerator;
import asmeta.ai.propgen.llm.LlmClient;

public class AsmetaAIService {

	private final IPreferenceStore preferenceStore;
	private final LlmClientFactory llmClientFactory;

	public AsmetaAIService(IPreferenceStore preferenceStore) {
		this(preferenceStore, new LlmClientFactory());
	}

	AsmetaAIService(IPreferenceStore preferenceStore, LlmClientFactory llmClientFactory) {
		this.preferenceStore = preferenceStore;
		this.llmClientFactory = llmClientFactory;
	}

	public String execute(File asmFile, String selectedText) {
		return execute(asmFile, selectedText, AsmetaAIRequestType.fromSelection(selectedText));
	}

	public String execute(File asmFile, String selectedText, AsmetaAIRequestType requestType) {
		return execute(asmFile, selectedText, requestType, PropertyGenerationListener.NO_OP);
	}

	public String execute(File asmFile, String selectedText, AsmetaAIRequestType requestType,
			PropertyGenerationListener listener) {
		AsmetaAISettings settings = AsmetaAISettings.fromPreferenceStore(preferenceStore);
		LlmClient llmClient = llmClientFactory.create(settings);
		PropertyGenerator generator = new PropertyGenerator(llmClient);
		String asmPath = asmFile.getAbsolutePath();
		String input = selectedText == null ? "" : selectedText.trim();

		if (requestType == AsmetaAIRequestType.ASM_TO_NL) {
			List<String> properties = generator.fromASMtoNL(asmPath, settings.getNumberOfProperties(), true);
			return String.join(System.lineSeparator(), properties);
		}
		if (requestType == AsmetaAIRequestType.TL_TO_NL) {
			return generator.fromTLtoNL(asmPath, input, true);
		}
		PropertyGenerationSession session = new PropertyGenerationSession(generator);
		return session.fromNLtoTLSession(asmPath, input, settings.getPropertyType(), true, settings.getMaxRetries(),
				listener);
	}
}
