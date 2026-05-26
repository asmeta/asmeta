package asmeta.ai.propgen.util;

import java.util.Objects;

import asmeta.ai.propgen.PropertyType;

/**
 * Builds small text insertions for AsmetaAI-generated comments and properties.
 */
public final class AsmetaAIContentInserter {

	private static final String AIGEN_PREFIX = "// AIGEN: ";
	private static final String NEW_LINE = "\n";

	/**
	 * Text to insert and the document offset where it should be inserted.
	 */
	public static final class InsertionEdit {
		public final int offset;
		public final String text;

		private InsertionEdit(int offset, String text) {
			this.offset = offset;
			this.text = text;
		}
	}

	private AsmetaAIContentInserter() {
	}

	/**
	 * Creates the edit that adds generated NL descriptions above the main rule.
	 *
	 * @param documentText current ASM document text
	 * @param llmOutput    generated natural-language descriptions
	 * @return insertion edit to apply to the document
	 */
	public static InsertionEdit asmToNlEdit(String documentText, String llmOutput) {
		String text = Objects.requireNonNull(documentText, "documentText");
		int offset = mainRuleOffset(text);
		String indent = lineIndent(text, offset);
		return new InsertionEdit(offset, commentsFor(llmOutput, indent) + NEW_LINE + NEW_LINE);
	}

	/**
	 * Creates the edit that explains the selected temporal property.
	 *
	 * @param documentText    current ASM document text
	 * @param selectionOffset offset of the selected property
	 * @param llmOutput       generated explanation
	 * @return insertion edit to apply above the selected property
	 */
	public static InsertionEdit tlToNlEdit(String documentText, int selectionOffset, String llmOutput) {
		String text = Objects.requireNonNull(documentText, "documentText");
		validateOffset(text, selectionOffset);
		int offset = lineStart(text, selectionOffset);
		String indent = lineIndent(text, offset);
		return new InsertionEdit(offset, commentsFor(llmOutput, indent) + NEW_LINE);
	}

	/**
	 * Creates the edit that adds a generated CTL/LTL property below the selection.
	 *
	 * @param documentText    current ASM document text
	 * @param selectionOffset offset of the selected natural-language text
	 * @param selectionLength length of the selected natural-language text
	 * @param llmOutput       generated temporal property
	 * @param type            generated property type
	 * @return insertion edit to apply below the selected text
	 */
	public static InsertionEdit nlToTlEdit(String documentText, int selectionOffset, int selectionLength,
			String llmOutput, PropertyType type) {
		String text = Objects.requireNonNull(documentText, "documentText");
		validateSelection(text, selectionOffset, selectionLength);
		int offset = afterSelectedLines(text, selectionOffset, selectionLength);
		String indent = lineIndent(text, lineStart(text, selectionOffset));
		String propertyType = type == PropertyType.CTLPROP ? "CTL" : "LTL";
		String insertion = indent + AIGEN_PREFIX + propertyType + " property" + NEW_LINE
				+ indentedTextFor(llmOutput, indent) + NEW_LINE;
		if (offset == text.length() && !text.isEmpty() && !text.endsWith(NEW_LINE)) {
			insertion = NEW_LINE + insertion;
		}
		return new InsertionEdit(offset, insertion);
	}

	private static String commentsFor(String llmOutput, String indent) {
		return formatGeneratedLines(llmOutput, indent, AIGEN_PREFIX);
	}

	private static String indentedTextFor(String llmOutput, String indent) {
		return formatGeneratedLines(llmOutput, indent, "");
	}

	private static String formatGeneratedLines(String llmOutput, String indent, String prefix) {
		StringBuilder result = new StringBuilder();
		for (String line : Objects.requireNonNull(llmOutput, "llmOutput").trim().split(NEW_LINE)) {
			String trimmedLine = line.trim();
			if (trimmedLine.isEmpty()) {
				continue;
			}
			if (result.length() > 0) {
				result.append(NEW_LINE);
			}
			result.append(indent).append(prefix).append(trimmedLine);
		}
		if (result.length() == 0) {
			throw new RuntimeException("AsmetaAI produced no content to insert.");
		}
		return result.toString();
	}

	// ASM-to-NL has no selection, so the main rule is the stable anchor.
	private static int mainRuleOffset(String text) {
		int offset = 0;
		for (String line : text.split(NEW_LINE, -1)) {
			if (line.trim().startsWith("main rule ")) {
				return offset;
			}
			offset += line.length() + NEW_LINE.length();
		}
		throw new RuntimeException("Could not insert AsmetaAI comments: main rule declaration not found.");
	}

	private static int lineStart(String text, int offset) {
		validateOffset(text, offset);
		if (offset == 0) {
			return 0;
		}
		int lineFeedIndex = text.lastIndexOf(NEW_LINE, offset - 1);
		return lineFeedIndex >= 0 ? lineFeedIndex + NEW_LINE.length() : 0;
	}

	private static int lineEnd(String text, int offset) {
		int lineFeedIndex = text.indexOf(NEW_LINE, offset);
		return lineFeedIndex >= 0 ? lineFeedIndex : text.length();
	}

	// The selected NL requirement can span multiple lines; insert after the last one.
	private static int afterSelectedLines(String text, int selectionOffset, int selectionLength) {
		if (text.isEmpty()) {
			return 0;
		}
		int lastSelectedOffset = selectionLength > 0 ? selectionOffset + selectionLength - 1 : selectionOffset;
		if (lastSelectedOffset >= text.length()) {
			return text.length();
		}
		int lineEnd = lineEnd(text, lastSelectedOffset);
		return lineEnd == text.length() ? text.length() : lineEnd + NEW_LINE.length();
	}

	private static String lineIndent(String text, int lineStart) {
		StringBuilder indent = new StringBuilder();
		for (int i = lineStart; i < text.length(); i++) {
			char c = text.charAt(i);
			if (c == ' ' || c == '\t') {
				indent.append(c);
				continue;
			}
			break;
		}
		return indent.toString();
	}

	private static void validateSelection(String text, int selectionOffset, int selectionLength) {
		validateOffset(text, selectionOffset);
		if (selectionLength < 0 || selectionOffset + selectionLength > text.length()) {
			throw new IllegalArgumentException("Invalid selection length: " + selectionLength);
		}
	}

	private static void validateOffset(String text, int offset) {
		if (offset < 0 || offset > text.length()) {
			throw new IllegalArgumentException("Invalid selection offset: " + offset);
		}
	}

}
