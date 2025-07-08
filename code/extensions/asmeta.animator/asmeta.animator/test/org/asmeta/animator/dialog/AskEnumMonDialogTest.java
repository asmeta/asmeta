package org.asmeta.animator.dialog;

import java.util.List;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import asmeta.definitions.domains.DomainsFactory;
import asmeta.definitions.domains.EnumElement;
import asmeta.definitions.domains.EnumTd;
import asmeta.definitions.domains.IntegerDomain;

public class AskEnumMonDialogTest {

	public static void main(String[] args) {
		Display display = new Display();
		Shell shell = new Shell(display);
		shell.open();

		DialogGenerator dg = new DialogGenerator(shell, "DOMAIN ENUM", "location");
		
		// EMUMERATIVE 
		EnumTd newDom = DomainsFactory.eINSTANCE.createEnumTd();
		newDom.setName("ENUM");
		List<EnumElement> newDomElements = newDom.getElement();
		for (String symbol : new String[] { "AA", "BB", "CC" }) {
			EnumElement newEl = DomainsFactory.eINSTANCE.createEnumElement();
			newEl.setSymbol(symbol);
			newDomElements.add(newEl);
		}		
		AskMonDialog dialog = dg.doSwitch(newDom);
		System.out.println("choosen " + dialog.open());
		// OK
	}
}
