package org.asmeta.animator.dialog;

import static org.junit.Assert.*;

import org.junit.Test;

import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

public class AskMonDialogTest {

public static void main(String[] args) {
	Display display = new Display();
	Shell shell = new Shell(display);
	shell.open();

	while (!shell.isDisposed()) { 
	    if (!display.readAndDispatch()) 
	     { display.sleep();} 
	}
}
}
