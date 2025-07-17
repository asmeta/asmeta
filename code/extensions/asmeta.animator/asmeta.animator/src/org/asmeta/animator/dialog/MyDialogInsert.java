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
import org.eclipse.swt.widgets.Text;

class MyDialogInsert extends MyDialog {
	  private String message;

	  private String input;


	  public MyDialogInsert(Shell parent) {
	    super(parent);
	    setText("Insert value of monitor function");
	    setMessage("Please enter a value:");
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
	    Shell shell = new Shell(getParent(), getStyle());
	    shell.setText(getText());
	   /* shell.addDisposeListener(new DisposeListener() {
			public void widgetDisposed(DisposeEvent event) {
				System.out.println("CHIUDI");
				shell.close();
			}
		});*/
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

	  
	  
	  @Override
	void createContents(final Shell shell) {
	    shell.setLayout(new GridLayout(2, true));

	    Label label = new Label(shell, SWT.NONE);
	    label.setText(message);
	    GridData data = new GridData();
	    data.horizontalSpan = 2;
	    label.setLayoutData(data);

	    final Text text = new Text(shell, SWT.BORDER);
	    data = new GridData(GridData.FILL_HORIZONTAL);
	    data.horizontalSpan = 2;
	    text.setLayoutData(data);

	    Button ok = new Button(shell, SWT.PUSH);
	    ok.setText("OK");
	    data = new GridData(GridData.FILL_HORIZONTAL);
	    ok.setLayoutData(data);
	    ok.addSelectionListener(new SelectionAdapter() {
	      @Override
		public void widgetSelected(SelectionEvent event) {
	        input = text.getText();
	        shell.close();
	      }
	    });

	    Button cancel = new Button(shell, SWT.PUSH);
	    cancel.setText("Clean");
	    data = new GridData(GridData.FILL_HORIZONTAL);
	    cancel.setLayoutData(data);
	    cancel.addSelectionListener(new SelectionAdapter() {
	      @Override
		public void widgetSelected(SelectionEvent event) {
	        input = null;
	        shell.close();
	      }
	    });
	    shell.setDefaultButton(ok);
	  }

		
	}
