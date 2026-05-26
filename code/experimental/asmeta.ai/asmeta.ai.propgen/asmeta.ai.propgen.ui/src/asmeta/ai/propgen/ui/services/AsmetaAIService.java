package asmeta.ai.propgen.ui.services;

import java.io.File;

import org.eclipse.jface.preference.IPreferenceStore;

import asmeta.ai.propgen.AsmetaAIOperation;
import asmeta.ai.propgen.AsmetaAIOperationListener;
import asmeta.ai.propgen.AsmetaAIOperationRequest;
import asmeta.ai.propgen.ASMtoNLOperation;
import asmeta.ai.propgen.NLtoTLOperation;
import asmeta.ai.propgen.TLtoNLOperation;
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
		return execute(asmFile, selectedText, requestType, AsmetaAIOperationListener.NO_OP);
	}

	public String execute(File asmFile, String selectedText, AsmetaAIRequestType requestType,
			AsmetaAIOperationListener listener) {
		AsmetaAISettings settings = AsmetaAISettings.fromPreferenceStore(preferenceStore);
		LlmClient llmClient = llmClientFactory.create(settings);
		String asmPath = asmFile.getAbsolutePath();
		String input = selectedText == null ? "" : selectedText.trim();
		AsmetaAIOperationRequest request = new AsmetaAIOperationRequest(asmPath, input, settings.getPropertyType(),
				settings.getNumberOfProperties(), false, settings.getMaxRetries(), listener);
		return createOperation(requestType, llmClient).execute(request);
	}

	private AsmetaAIOperation createOperation(AsmetaAIRequestType requestType, LlmClient llmClient) {
		switch (requestType) {
		case ASM_TO_NL:
			return new ASMtoNLOperation(llmClient);
		case TL_TO_NL:
			return new TLtoNLOperation(llmClient);
		case NL_TO_TL:
			return new NLtoTLOperation(llmClient);
		default:
			throw new IllegalStateException("Unexpected request type: " + requestType);
		}
	}
}
