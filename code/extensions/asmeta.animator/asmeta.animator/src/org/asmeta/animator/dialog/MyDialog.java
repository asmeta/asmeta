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
public abstract class MyDialog extends Dialog {

	Shell shell;

	protected MyDialog(Shell parent) {
		super(parent, SWT.DIALOG_TRIM); // not modal to allow to close | SWT.APPLICATION_MODAL);
	}

	protected String input;

	public String open() {
		shell = new Shell(getParent(), getStyle());
		shell.setText(getText());
		shell.addDisposeListener(new DisposeListener() {
			public void widgetDisposed(DisposeEvent event) {
				System.out.println("CHIUDI");
				shell.close();
			}
		});
		createContents(shell);
		shell.pack();
		shell.open();
		Display display = getParent().getDisplay();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		return input;
	}

	abstract void createContents(final Shell shell);

}
