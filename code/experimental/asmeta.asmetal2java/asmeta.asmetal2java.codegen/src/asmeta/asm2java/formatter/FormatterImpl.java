package asmeta.asm2java.formatter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.jdt.core.formatter.CodeFormatter;
import org.eclipse.jdt.core.formatter.DefaultCodeFormatterConstants;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.IDocument;
import org.eclipse.text.edits.MalformedTreeException;
import org.eclipse.text.edits.TextEdit;

public class FormatterImpl implements Formatter {

	private static final boolean REMOVE_DOUBLE_NEW_LINES = true;	
	static int initialIndent = 0;
	private static final ArrayList<String> TUPLE_NAMES = new ArrayList<>(Arrays.asList("Decade", "Ennead", "Octet", "Pair", "Quartet", "Quintet", "Septet", "Sextet", "Triplet"));
	private static final ArrayList<String> NAMES = new ArrayList<>(Arrays.asList("java.util.Collections", "java.util.Set", "java.util.Scanner", "java.util.List", "java.util.HashSet", "java.util.Arrays", "java.util.ArrayList", "org.apache.commons.collections4.bag.HashBag", "java.util.concurrent.ThreadLocalRandom", "java.util.function.Function", "java.util.stream.Collectors", "org.apache.commons.collections4.bag.Bag"));
	
	public FormatterImpl() { /* Empty Constructor */ }
	
	@Override
	public String formatCode(String code) {
		// first remove double new lines
		if (REMOVE_DOUBLE_NEW_LINES) {
			code = replaceDoubleNL(code);
		}
		
		// Remove useless imports for tuples
		for (String s : TUPLE_NAMES) {
			if (StringUtils.countMatches(code, s)==1)
				code = code.replace("import org.javatuples." + s + ";", "");
		}
		
		// Remove useless imports for other classes
		for (String s : NAMES) {
			if (StringUtils.countMatches(code, s.split("\\.")[s.split("\\.").length - 1])==1)
				code = code.replace("import " + s + ";", "");
		}
		
		// take default Eclipse formatting options
		Map<String, String> options = DefaultCodeFormatterConstants.getEclipseDefaultSettings();

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
			} catch (MalformedTreeException | BadLocationException e) {
				e.printStackTrace();
			} 
			return document.get();
		}
	}

	// replace some extra new lines or spaces that are no longer useful
	private static final Pattern SPACES_BETWEEN_NL = Pattern.compile("\n[\\s]*\n*");
	private static final Pattern DOUBLE_NL = Pattern.compile("[\r\n]+");

	String replaceDoubleNL(String code){
		code = code.trim();
		// ci potrebbe essere qualche spazio tra due a capo
		code = SPACES_BETWEEN_NL.matcher(code).replaceAll("\n");
		// remove double space
		code = DOUBLE_NL.matcher(code).replaceAll("\n");
		return code;
	}

}
