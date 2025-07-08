package org.asmeta.animator.dialog;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class AskBooleanMonDialogTest {

	public static void main(String[] args) {
		// build the parent
		Display display = new Display();
		Shell shell = new Shell(display);
		// open the main window
		shell.open();
		DialogGenerator dg = new DialogGenerator(shell, "BOOLEAN", "location");
		// boolean
		AskMonDialog dialog = dg.caseBooleanDomain(null);
		System.out.println("choosen " + dialog.open());
	}
}
