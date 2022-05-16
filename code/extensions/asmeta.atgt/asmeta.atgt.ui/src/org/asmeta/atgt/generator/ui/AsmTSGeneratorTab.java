package org.asmeta.atgt.generator.ui;

import java.util.ArrayList;
import java.util.List;

import org.asmeta.atgt.generator.AsmTestGenerator;
import org.asmeta.atgt.generator.CriteriaEnum;
import org.asmeta.atgt.generator.FormatsEnum;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.ui.AbstractLaunchConfigurationTab;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

public class AsmTSGeneratorTab extends AbstractLaunchConfigurationTab {

	public static final String CONFIG_COMPUTE_COVERAGE = "computeCoverage";
	public static final String CONFIG_CRITERIA = "coverageCriteria";
	public static final String CONFIG_FORMATS = "coverageFormats";
	
	private Button[] cbs;
	private Button[] formats;

	protected Object result;
	private Button btnCoverageTp;
	
	@Override
	public void createControl(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);
		
		composite.setLayoutData(new RowData(SWT.DEFAULT, 589));

		SelectionListener defaultSelectionListener = new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				updateLaunchConfigurationDialog();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				updateLaunchConfigurationDialog();
			}
		};

		Label lblAlgorithmLabel = new Label(composite, SWT.NONE);
		lblAlgorithmLabel.setBounds(5, 10, 200, 25);
		lblAlgorithmLabel.setAlignment(SWT.CENTER);
		lblAlgorithmLabel.setText("Configurations");

		// Checkbox if to enable coverage
		Composite composite_uses = new Composite(composite, SWT.NONE);
		composite_uses.setBounds(30, 40, 200, 30);
		composite_uses.setLayout(null);
		btnCoverageTp = new Button(composite_uses, SWT.CHECK);
		btnCoverageTp.setBounds(3, 3, 200, 16);
		btnCoverageTp.setText("Enable coverage");
		btnCoverageTp.addSelectionListener(defaultSelectionListener);

		// Create the coverage criteria options
		Composite compositeCriteria = new Composite(composite, SWT.NONE);
		compositeCriteria.setBounds(30, 70, 200, 200);
		compositeCriteria.setLayout(null);
		Label lblGenerator = new Label(compositeCriteria, SWT.NONE);
		lblGenerator.setLocation(3, 5);
		lblGenerator.setSize(128, 20);
		lblGenerator.setText("Coverage Criteria:");
		
		cbs = new Button[CriteriaEnum.values().length];
		for (int i=0; i<cbs.length; i++) {
			cbs[i] = new Button(compositeCriteria,SWT.CHECK);
			cbs[i].setBounds(3, 30+i*18, 196, 16);
			cbs[i].setText(CriteriaEnum.values()[i].name);
			cbs[i].addSelectionListener(defaultSelectionListener);
			System.out.println("Adding cbs"+i + " " + CriteriaEnum.values()[i].name);
		}

		// Create the output format options
		Composite compositeFormats = new Composite(composite, SWT.NONE);
		compositeFormats.setBounds(230, 70, 200, 200);
		compositeFormats.setLayout(null);
		Label lblFormats = new Label(compositeFormats, SWT.NONE);
		lblFormats.setLocation(3, 5);
		lblFormats.setSize(128, 20);
		lblFormats.setText("Output formats:");
		
		formats = new Button[FormatsEnum.values().length];
		for (int i=0; i<formats.length; i++) {
			formats[i] = new Button(compositeFormats,SWT.CHECK);
			formats[i].setBounds(3, 30+i*18, 196, 16);
			formats[i].setText(FormatsEnum.values()[i].name);
			formats[i].addSelectionListener(defaultSelectionListener);
			System.out.println("Adding outputs"+i + " " + FormatsEnum.values()[i].name);
		}

		setControl(composite);
	}

	@Override
	public void setDefaults(ILaunchConfigurationWorkingCopy configuration) {
		System.out.println("Setting defaults...");
		configuration.setAttribute(CONFIG_COMPUTE_COVERAGE, true);
		configuration.setAttribute(CONFIG_CRITERIA, AsmTestGenerator.DEFAULT_COV_BUILDER);
		configuration.setAttribute(CONFIG_FORMATS, AsmTestGenerator.DEFAULT_FORMATS);
	}
	
	public List<CriteriaEnum> currentlySelectedCriteria() {
		List<CriteriaEnum> res = new ArrayList<>();
		for (int i=0; i<cbs.length; i++) {
			if (cbs[i].getSelection()) {
				res.add(CriteriaEnum.values()[i]);
			}
		}
		return res;
	}
	
	public List<FormatsEnum> currentlySelectedFormats() {
		List<FormatsEnum> res = new ArrayList<>();
		for (int i=0; i<formats.length; i++) {
			if (formats[i].getSelection()) {
				res.add(FormatsEnum.values()[i]);
			}
		}
		return res;
	}

	@Override
	public void initializeFrom(ILaunchConfiguration configuration) {
		try {
			btnCoverageTp.setSelection(configuration.getAttribute(CONFIG_COMPUTE_COVERAGE, btnCoverageTp.getSelection()));
			List<String> crit = configuration.getAttribute(CONFIG_CRITERIA, CriteriaEnum.toListOfString(AsmTestGenerator.DEFAULT_CRITERIA));
			for (int i=0; i<CriteriaEnum.values().length; i++) {
				cbs[i].setSelection(crit!=null && crit.size()>0 && crit.contains(CriteriaEnum.values()[i].name()));
			}
			List<String> form = configuration.getAttribute(CONFIG_FORMATS, AsmTestGenerator.DEFAULT_FORMATS);
			for (int i=0; i<FormatsEnum.values().length; i++) {
				formats[i].setSelection(form!=null && form.size()>0 && form.contains(FormatsEnum.values()[i].name()));
			}
		} catch (CoreException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void performApply(ILaunchConfigurationWorkingCopy configuration) {
		System.out.println("Performing apply...");
		configuration.setAttribute(CONFIG_CRITERIA, CriteriaEnum.toListOfString(currentlySelectedCriteria()));
		configuration.setAttribute(CONFIG_COMPUTE_COVERAGE, btnCoverageTp.getSelection());
		configuration.setAttribute(CONFIG_FORMATS, FormatsEnum.toListOfString(currentlySelectedFormats()));
	}

	@Override
	public String getName() {
		return "ATGT test generator";
	}

	@Override
	public boolean isValid(ILaunchConfiguration launchConfig) {
		return true;
	}

	@Override
	public Image getImage() {
		return null;
		//return new Image(this.getShell().getDisplay(),"icons/gears.png");
	}
}