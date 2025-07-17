package org.asmeta.animator.dialog;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

public class MyDialogError extends MyDialog {
	private String message;

	private String input;
	private Shell shell_1;
	private GridData gd_btnTrue;
	private GridData gd_btnFalse;
	

	public MyDialogError(Shell parent, String error) {
		super(parent);
		setMessage(error);
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getInput() {
		return input;
	}

	public void setInput(String input) {
		this.input = input;
	}

	@Override
	public String open() {
		shell_1 = new Shell(getParent(), getStyle());
		shell_1.setText(getText());
		createContents(shell_1);
		shell_1.pack();
		shell_1.open();
		Display display = getParent().getDisplay();
		while (!shell_1.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		return input;
	}

	@Override
	protected void createContents(final Shell shell) {
		shell.setLayout(new GridLayout(2, true));

		Label label = new Label(shell, SWT.NONE);
		label.setText(message);
		GridData data = new GridData();
		data.horizontalSpan = 2;
		label.setLayoutData(data);

		Button ok = new Button(shell, SWT.PUSH);
		ok.setText("OK");
		data = new GridData(GridData.FILL_HORIZONTAL);
		ok.setLayoutData(data);
		ok.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent event) {
				shell.close();
			}
		});

		/*
		 * shell.addListener(SWT.Close, new Listener() { public void handleEvent(Event
		 * event) { shell.setVisible(false); } });
		 */
		shell.setDefaultButton(ok);
	}


}
