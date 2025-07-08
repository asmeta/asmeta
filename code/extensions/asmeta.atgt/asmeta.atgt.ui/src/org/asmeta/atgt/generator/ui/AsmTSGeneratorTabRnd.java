package org.asmeta.atgt.generator.ui;


import org.asmeta.atgt.generator.AsmTestGenerator;
import org.asmeta.atgt.generator.CriteriaEnum;
import org.asmeta.atgt.generator.FormatsEnum;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.ui.AbstractLaunchConfigurationTab;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Text;

//
// this set the variables for the simulation-based test generator
// number of steps and number of tests
//
public class AsmTSGeneratorTabRnd extends AbstractLaunchConfigurationTab {

	private static final String RND_SIM_LAUNCH_TAB = "Random Simulation-based";
    public static final String CONFIG_NSTEPS = "nSteps";
	public static final String CONFIG_NTESTS = "nTests";

	public static final int N_STEPS_DEFAULT = 10;
	public static final int N_TESTS_DEFAULT = 5;
	private Spinner nTests;
	private Spinner spinnerNsteps;

	@Override
    public void createControl(Composite parent) {

        Composite comp = new Group(parent, SWT.BORDER);
        setControl(comp);

        GridLayoutFactory.swtDefaults().numColumns(2).applyTo(comp);

        Label labelNtests = new Label(comp, SWT.NONE);
        labelNtests.setText("Number of tests:");
        nTests = new Spinner (comp, SWT.BORDER);
		nTests.setMinimum(1);
		nTests.setMaximum(1000);
		nTests.setSelection(N_TESTS_DEFAULT);
		nTests.setIncrement(1);
		nTests.setPageIncrement(100);

        //GridDataFactory.fillDefaults().grab(true, false).applyTo(text);
        Label labelNSteps = new Label(comp, SWT.NONE);
        labelNSteps.setText("Number of steps:");
        spinnerNsteps = new Spinner (comp, SWT.BORDER);
        spinnerNsteps.setMinimum(1);
        spinnerNsteps.setMaximum(1000);
        spinnerNsteps.setSelection(N_STEPS_DEFAULT);
		//spinner.setSelection(500);
        spinnerNsteps.setIncrement(1);
        spinnerNsteps.setPageIncrement(100);
    }

    
    @Override
	public void setDefaults(ILaunchConfigurationWorkingCopy configuration) {
		System.out.println("Setting defaults...");
		configuration.setAttribute(CONFIG_NSTEPS, N_STEPS_DEFAULT);
		configuration.setAttribute(CONFIG_NTESTS, N_TESTS_DEFAULT);
	}
    @Override
    public void initializeFrom(ILaunchConfiguration configuration) {
    	//TODO
/*        try {
            String consoleText = configuration.getAttribute(CONSOLE_TEXT,                //"Simon says \"RUN!\"");
            text.setText(consoleText);
        } catch (CoreException e) {
            // ignore here
        }*/
    }

    @Override
    public void performApply(ILaunchConfigurationWorkingCopy configuration) {
		System.out.println("Performing apply...");
		configuration.setAttribute(CONFIG_NSTEPS, nTests.getSelection());
		configuration.setAttribute(CONFIG_NTESTS, spinnerNsteps.getSelection());
    }

    @Override
    public String getName() {
        return RND_SIM_LAUNCH_TAB;
    }

}