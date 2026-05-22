package asmeta.ai.propgen.ui.services;

import java.io.File;

import asmeta.ai.propgen.PropertyType;

public enum AsmetaAIRequestType {
	ASM_TO_NL,
	TL_TO_NL,
	NL_TO_TL;

	public static AsmetaAIRequestType fromSelection(String selectedText) {
		String input = normalize(selectedText);
		if (input.isEmpty()) {
			return ASM_TO_NL;
		}
		if (input.startsWith("CTLSPEC") || input.startsWith("LTLSPEC")) {
			return TL_TO_NL;
		}
		return NL_TO_TL;
	}

	public String startMessage(File asmFile, AsmetaAISettings settings) {
		String fileName = asmFile.getName();
		switch (this) {
		case ASM_TO_NL:
			return "Generating natural-language properties from " + fileName + "...";
		case TL_TO_NL:
			return "Explaining selected temporal property from " + fileName + "...";
		case NL_TO_TL:
			return "Generating " + propertyTypeLabel(settings) + " property from natural-language requirement in "
					+ fileName + "...";
		default:
			throw new IllegalStateException("Unexpected request type: " + this);
		}
	}

	public String successHeader() {
		switch (this) {
		case ASM_TO_NL:
			return "Natural-language properties:";
		case TL_TO_NL:
			return "Natural-language explanation:";
		case NL_TO_TL:
			return "Generated temporal property:";
		default:
			throw new IllegalStateException("Unexpected request type: " + this);
		}
	}

	public String operationDescription(AsmetaAISettings settings) {
		switch (this) {
		case ASM_TO_NL:
			return "generating natural-language properties";
		case TL_TO_NL:
			return "explaining the selected temporal property";
		case NL_TO_TL:
			return "generating a " + propertyTypeLabel(settings) + " property";
		default:
			throw new IllegalStateException("Unexpected request type: " + this);
		}
	}

	private static String normalize(String selectedText) {
		return selectedText == null ? "" : selectedText.trim();
	}

	private static String propertyTypeLabel(AsmetaAISettings settings) {
		return settings.getPropertyType() == PropertyType.CTLPROP ? "CTL" : "LTL";
	}
}
