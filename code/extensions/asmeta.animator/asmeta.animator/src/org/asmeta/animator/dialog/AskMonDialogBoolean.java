package org.asmeta.animator.dialog;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

class AskMonDialogBoolean extends AskMonDialog {


	public AskMonDialogBoolean(Shell parent, String message) {
		super(parent, message);
	}

	@Override
	protected void createContents(final Shell shell) {
		GridData gd_btnFalse;
		GridData gd_btnTrue;
		shell.setLayout(new GridLayout(2, true));

		Label label = new Label(shell, SWT.NONE);
		label.setText(message);
		GridData data = new GridData();
		data.horizontalSpan = 2;
		label.setLayoutData(data);

		Button btnTrue = new Button(shell, SWT.PUSH);
		btnTrue.setText("true");
		gd_btnTrue = new GridData(GridData.FILL_HORIZONTAL);
		btnTrue.setLayoutData(gd_btnTrue);
		btnTrue.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent event) {
				input = "true";
				shell.close();
			}
		});

		Button btnFalse = new Button(shell, SWT.PUSH);
		btnFalse.setText("false");
		gd_btnFalse = new GridData(GridData.FILL_HORIZONTAL);
		btnFalse.setLayoutData(gd_btnFalse);
		btnFalse.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent event) {
				input = "false";
				shell.close();
			}
		});

		/*
		 * shell.addListener(SWT.Close, new Listener() { public void handleEvent(Event
		 * event) { shell.setVisible(false); } });
		 */
		shell.setDefaultButton(btnTrue);
	}

}
