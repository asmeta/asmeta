package org.asmeta.animator.dialog;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

/**
 * Super class for dialog when asking the user to insert values in the animator
 * to be passed to the simulator
 */
public abstract class AskMonDialog extends Dialog {

	// inserted by the user
	protected String input = null;
	
	// message in the dialog
	protected String message;

	protected AskMonDialog(Shell parent, String message) {
		super(parent, SWT.DIALOG_TRIM); // not modal to allow to close | SWT.APPLICATION_MODAL);
		this.message = message;
	}

	// open the dialog and returns the inserted value 
	// if it is closed, the return null
	final public String open() {
		Shell newShell = new Shell(getParent(), getStyle());
		newShell.setText(message);
		newShell.addDisposeListener(new DisposeListener() {
			// the dialog is disposed
			public void widgetDisposed(DisposeEvent event) {
				// if input is not set, it will be equal to null
				newShell.close();
			}
		});
		createContents(newShell);
		newShell.pack();
		newShell.open();
		// wait for response
		Display display = getParent().getDisplay();
		while (!newShell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		return input;
	}

	// create the content
	// it also must set the value of input according to the widgets in the dialog
	abstract void createContents(final Shell shell);

}
