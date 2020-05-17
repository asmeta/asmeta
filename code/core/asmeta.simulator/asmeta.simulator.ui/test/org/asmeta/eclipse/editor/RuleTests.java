package org.asmeta.eclipse.editor;

import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.text.rules.ICharacterScanner;
import org.eclipse.jface.text.rules.IRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.RuleBasedScanner;
import org.eclipse.jface.text.rules.WordRule;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Display;

public class RuleTests {

	public static void main(String[] args) {
		Display display = Display.getDefault();
		Color color = new Color(display, new RGB(0, 0, 0));
		System.out.println(color);
		WordRule rule = AsmScanner.getKeywordsToken(color);
		RuleBasedScanner scanner = new RuleBasedScanner();
		scanner.setRules(new IRule[] {rule});
		//
		IDocument document= new Document("main");
		scanner.setRange(document, 0, document.getLength());		
		IToken token= scanner.nextToken();
		System.out.println(((TextAttribute)token.getData()).getForeground());
		
	}
	
}
