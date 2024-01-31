package org.asmeta.atgt.generator.ui;


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
// number of steps and numer of tests
//
public class AsmTGSimulatorGenTab extends AbstractLaunchConfigurationTab {

    private static final String RND_SIM_LAUNCH_TAB = "Random Simulation-based";

    @Override
    public void createControl(Composite parent) {

        Composite comp = new Group(parent, SWT.BORDER);
        setControl(comp);

        GridLayoutFactory.swtDefaults().numColumns(2).applyTo(comp);

        Label labelNtests = new Label(comp, SWT.NONE);
        labelNtests.setText("Number of tests:");
        //GridDataFactory.swtDefaults().applyTo(label);
        Spinner spinner = new Spinner (comp, SWT.BORDER);
		spinner.setMinimum(1);
		spinner.setMaximum(1000);
		//spinner.setSelection(500);
		spinner.setIncrement(1);
		spinner.setPageIncrement(100);

        //GridDataFactory.fillDefaults().grab(true, false).applyTo(text);
        Label labelNSteps = new Label(comp, SWT.NONE);
        labelNSteps.setText("Number of steps:");
        Spinner spinnerNsteps = new Spinner (comp, SWT.BORDER);
        spinnerNsteps.setMinimum(1);
        spinnerNsteps.setMaximum(1000);
		//spinner.setSelection(500);
        spinnerNsteps.setIncrement(1);
        spinnerNsteps.setPageIncrement(100);
        
        
    }

    @Override
    public void setDefaults(ILaunchConfigurationWorkingCopy configuration) {
    }

    @Override
    public void initializeFrom(ILaunchConfiguration configuration) {
/*        try {
            String consoleText = configuration.getAttribute(CONSOLE_TEXT,                //"Simon says \"RUN!\"");
            text.setText(consoleText);
        } catch (CoreException e) {
            // ignore here
        }*/
    }

    @Override
    public void performApply(ILaunchConfigurationWorkingCopy configuration) {
        // set the text value for the CONSOLE_TEXT key
        //configuration.setAttribute(CONSOLE_TEXT, text.getText());
    }

    @Override
    public String getName() {
        return RND_SIM_LAUNCH_TAB;
    }

}