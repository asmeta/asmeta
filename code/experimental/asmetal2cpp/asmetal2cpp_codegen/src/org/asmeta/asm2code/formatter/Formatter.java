package org.asmeta.asm2code.formatter;

import org.eclipse.cdt.core.formatter.CodeFormatter;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.IDocument;
import org.eclipse.text.edits.MalformedTreeException;
import org.eclipse.text.edits.TextEdit;

/**
 * formats the C++ code according to the eclipse formatter
 */
public class Formatter {

	public static String formatCode(String source) {
		CodeFormatter codeFormatter = org.eclipse.cdt.core.ToolFactory.createDefaultCodeFormatter(null);
		TextEdit edit = codeFormatter.format(0, source, 0, source.length(), 0, null);
		IDocument document = new Document(source);
		try {
			edit.apply(document);
		} catch (MalformedTreeException e) {
			System.err.println("error " + e.getMessage());
			return source;
		} catch (BadLocationException e) {
			System.err.println("error " + e.getMessage());
			return source;
		}
		String formattedSource = document.get();
		return formattedSource;
	}

}
