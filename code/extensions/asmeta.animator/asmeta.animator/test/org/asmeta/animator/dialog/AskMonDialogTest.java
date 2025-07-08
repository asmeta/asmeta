package org.asmeta.animator.dialog;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import asmeta.definitions.domains.DomainsFactory;
import asmeta.definitions.domains.IntegerDomain;

public class AskMonDialogTest {

	// for integr use the generic dialog
	public static void main(String[] args) {
		Display display = new Display();
		Shell shell = new Shell(display);
		shell.open();

		DialogGenerator dg = new DialogGenerator(shell, "INTEGER", "location");
		// INTEGER 
		IntegerDomain intDom = DomainsFactory.eINSTANCE.createIntegerDomain();
		AskMonDialog dialog = dg.doSwitch(intDom);
		System.out.println("choosen " + dialog.open());
	}
}
