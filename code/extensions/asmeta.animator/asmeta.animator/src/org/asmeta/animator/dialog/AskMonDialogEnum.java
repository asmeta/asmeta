package org.asmeta.animator.dialog;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

class AskMonDialogEnum extends AskMonDialog {
	
	private ArrayList<String> enumValues;

	public AskMonDialogEnum(Shell parent, ArrayList<String> enumValues, String message) {
		super(parent, message);
		this.enumValues = enumValues;
	}

	@Override
	protected void createContents(final Shell shell) {
		shell.setLayout(new GridLayout(2, true));
		Label label = new Label(shell, SWT.NONE);
		label.setText(message);
		GridData data = new GridData();
		data.horizontalSpan = 2;
		label.setLayoutData(data);

		Combo combo = new Combo(shell,SWT.DROP_DOWN |SWT.READ_ONLY);
		combo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		new Label(shell, SWT.NONE);
		for (String element : enumValues)
			combo.add(element);
		Button ok = new Button(shell, SWT.PUSH);
		ok.setText("OK");
		data = new GridData(GridData.FILL_HORIZONTAL);
		ok.setLayoutData(data);
		ok.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent event) {
				input = combo.getText();
				shell.close();
			}
		});

		/*Button cancel = new Button(shell, SWT.PUSH);
		cancel.setText("Cancel");
		data = new GridData(GridData.FILL_HORIZONTAL);
		cancel.setLayoutData(data);
		cancel.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent event) {
				//input = null;
				shell.close();
			}
		});*/

		/*
		 * shell.addListener(SWT.Close, new Listener() { public void handleEvent(Event
		 * event) { shell.setVisible(false); } });
		 */

		shell.setDefaultButton(ok);
	}

}
