package org.asmeta.animator.dialog;

import java.util.List;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import asmeta.definitions.domains.DomainsFactory;
import asmeta.definitions.domains.EnumElement;
import asmeta.definitions.domains.EnumTd;
import asmeta.definitions.domains.IntegerDomain;

public class AskMonDialogTest {

	public static void main(String[] args) {
		Display display = new Display();
		Shell shell = new Shell(display);
		shell.open();

		DialogGenerator dg = new DialogGenerator(shell, "DOMAIN TEST", "location");
		
		// EMUMERATIVE 
		EnumTd newDom = DomainsFactory.eINSTANCE.createEnumTd();
		newDom.setName("TEST ENUM");
		List<EnumElement> newDomElements = newDom.getElement();
		for (String symbol : new String[] { "AA", "BB", "CC" }) {
			EnumElement newEl = DomainsFactory.eINSTANCE.createEnumElement();
			newEl.setSymbol(symbol);
			newDomElements.add(newEl);
		}		
		AskMonDialog dialog = dg.doSwitch(newDom);
		System.out.println("choosen " + dialog.open());
		// OK
		
		// INTEGER 
		IntegerDomain intDom = DomainsFactory.eINSTANCE.createIntegerDomain();
		intDom.setName("TEST INT");
		dialog = dg.doSwitch(intDom);
		System.out.println("choosen " + dialog.open());
		// OK

		
		
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}
}
