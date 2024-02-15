package org.asmeta.asm2java.formatter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.jdt.core.formatter.CodeFormatter;
import org.eclipse.jdt.core.formatter.DefaultCodeFormatterConstants;
//import org.eclipse.cdt.core.formatter.CodeFormatter;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.IDocument;
import org.eclipse.text.edits.MalformedTreeException;
import org.eclipse.text.edits.TextEdit;

public class Formatter {

	private static final boolean REMOVE_DOUBLE_NEW_LINES = true;	
	static int initialIndent = 0;
	private static final ArrayList<String> TUPLE_NAMES = new ArrayList<>(Arrays.asList("Decade", "Ennead", "Octet", "Pair", "Quartet", "Quintet", "Septet", "Sextet", "Triplet"));
	
	public static String formatCode(String code) {
		// first remove double new lines
		if (REMOVE_DOUBLE_NEW_LINES) {
			code = replaceDoubleNL(code);
		}
		
		// Remove useless imports for tuples
		for (String s : TUPLE_NAMES)
			if (StringUtils.countMatches(s, code)==1)
				code = code.replace("import org.javatuples." + s + ";", "");
		
		// take default Eclipse formatting options
		Map<String, String> options = DefaultCodeFormatterConstants.getEclipseDefaultSettings();

		// initialize the compiler settings to be able to format 1.5 code
//		options.put( JavaCore.COMPILER_COMPLIANCE, JavaCore.VERSION_1_5 );
//		options.put( JavaCore.COMPILER_CODEGEN_TARGET_PLATFORM,	JavaCore.VERSION_1_5 );
//		options.put( JavaCore.COMPILER_SOURCE, JavaCore.VERSION_1_5 );

		// instantiate the default code formatter with the given options
		final CodeFormatter codeFormatter = org.eclipse.jdt.core.ToolFactory.createCodeFormatter(options);

		// retrieve the source to format
		final TextEdit edit = codeFormatter.format(CodeFormatter.K_UNKNOWN, code, 0, code.length(), initialIndent,
				System.getProperty("line.separator")); //$NON-NLS-1$
		if (edit == null) {
			new IllegalArgumentException("cannot format this: " + code).printStackTrace(); // $NON-NLS-1$
			return code;
		} else {
			// apply the format edit
			IDocument document = new Document(code);
			try {
				edit.apply(document);
			} catch (MalformedTreeException e) {
				e.printStackTrace();
			} catch (BadLocationException e) {
				e.printStackTrace();
			}
			return document.get();
		}
	}

	// replace some extra new lines or spaces that are no longer useful
	private static final Pattern SPACES_BETWEEN_NL = Pattern.compile("\n[\\s]*\n*");
	private static final Pattern DOUBLE_NL = Pattern.compile("[\r\n]+");
	static String replaceDoubleNL(String code){
		code = code.trim();
		// ci potrebbe essere qualche spazio tra due a capo
		code = SPACES_BETWEEN_NL.matcher(code).replaceAll("\n");
		// remove double space
		code = DOUBLE_NL.matcher(code).replaceAll("\n");
		return code;
	}

}
