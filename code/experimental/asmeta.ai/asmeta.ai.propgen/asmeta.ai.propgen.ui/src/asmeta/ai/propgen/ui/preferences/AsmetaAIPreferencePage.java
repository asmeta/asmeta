package asmeta.ai.propgen.ui.preferences;

import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

public class AsmetaAIPreferencePage extends PreferencePage implements IWorkbenchPreferencePage {

	public AsmetaAIPreferencePage() {
		setDescription("Asmeta AI preferences");
		noDefaultAndApplyButton();
	}

	@Override
	protected Control createContents(Composite parent) {
		Composite page = new Composite(parent, SWT.NONE);
		page.setLayout(new GridLayout(1, false));
		page.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		Label label = new Label(page, SWT.WRAP);
		label.setText("Expand this node to configure the available Asmeta AI preference pages.");
		label.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));
		return page;
	}

	@Override
	public void init(IWorkbench workbench) {
	}
}
