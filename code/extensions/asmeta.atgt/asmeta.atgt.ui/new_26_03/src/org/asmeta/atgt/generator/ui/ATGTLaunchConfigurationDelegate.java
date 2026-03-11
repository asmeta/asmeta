package org.asmeta.atgt.generator.ui;

import static org.asmeta.atgt.generator.ui.ATGTLaunchConfigurationTabMC.CONFIG_COMPUTE_COVERAGE;
import static org.asmeta.atgt.generator.ui.ATGTLaunchConfigurationTabMC.CONFIG_FORMATS;

import java.util.List;

import org.asmeta.atgt.generator.AsmTestGenerator;
import org.asmeta.atgt.generator.CriteriaEnum;
import org.asmeta.atgt.generator.FormatsEnum;
import org.asmeta.eclipse.AsmetaUtility;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.ISafeRunnable;
import org.eclipse.core.runtime.SafeRunner;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.model.LaunchConfigurationDelegate;
import org.eclipse.swt.custom.BusyIndicator;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

/**
 * http://www.vogella.com/tutorials/EclipseLauncherFramework/article.html
 * 
 * @author garganti
 *
 */
public class ATGTLaunchConfigurationDelegate
		extends LaunchConfigurationDelegate /* implements ILaunchConfigurationDelegate */ {

	// two generation modes
	// in the future this can be implements with 2 subclasses
	public enum GenerationMode {
		MODEL_CHECKER, RANDOM
	}
	private GenerationMode generationMode;
	// common
	public boolean computeCoverage;
	protected IPath asmetaSpecPath;
	public List<FormatsEnum> formats;
	// used only by the model checker
	public List<CriteriaEnum> coverageCriteria;
	// used by random only
	int nSteps, nTests;

	// it is necessary since it needs the constructor without parameters
	public ATGTLaunchConfigurationDelegate() {
		ATGTActivator.log.debug("calling launcher config with empty constuctor");
	}
	
	// si dovrebbe eliminare e usare quello di feault settando gli opportuni campi 
	ATGTLaunchConfigurationDelegate(ILaunchConfiguration configuration, GenerationMode mode, IPath filePath) {
		ATGTActivator.log.debug("calling launcher config with configuration");		
		try {
			setConfiguration(configuration);
			this.generationMode = mode;
			this.asmetaSpecPath = filePath;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void launch(ILaunchConfiguration configuration, String mode, ILaunch launch, IProgressMonitor monitor)
			throws CoreException {
		ATGTActivator.log.debug("ATGTLaunchConfigurationDelegate:launch");
		ATGTActivator.log.debug(configuration.getAttributes());
		ATGTActivator.log.debug(this.generationMode + " vs " + mode);
		// adesso come adesso questa è nulla (vuota) a meno che usi il costruttore con l'argomento
		ATGTActivator.log.debug(asmetaSpecPath);
		setConfiguration(configuration);
/*		// get the current ASM file if any - it must be in the config or in the field itself
		IWorkbench workbench = PlatformUI.getWorkbench();
		if (workbench == null) {
			ATGTActivator.log.error("Call generateTests with workbench null");
			return;
		}
		IWorkbenchWindow window = workbench.getActiveWorkbenchWindow();
		if (window != null) {
			// probably useless
			asmetaSpecPath = AsmetaUtility.getEditorIFile(window).getFullPath();			
		} else {
			// if a dialog is open, the active window is null
			// get the file . does not work !!! It gets the file of t3h configuration, which is not what we want 
			// window is null -> questo è il meno potrebbe trovare window in altro modo?
			// ma anche asmetaSpecPath potrebbe essere null
			System.err.println("window is null !!! ");
		}*/
		// generate the tests
		generateTests();
	}

	private void setConfiguration(ILaunchConfiguration configuration) {
		try {
			ATGTActivator.log.debug("Setting launch configuration: " + configuration);
			computeCoverage = configuration.getAttribute(CONFIG_COMPUTE_COVERAGE,
					AsmTestGenerator.DEFAULT_COMPUTE_COVERAGE);
			ATGTActivator.log.debug("compute coverage?" + computeCoverage);
			if (generationMode == GenerationMode.MODEL_CHECKER) {
				List<String> covCriteriaAttr = configuration.getAttribute(
						ATGTLaunchConfigurationTabMC.CONFIG_CRITERIA, CriteriaEnum.toListOfString(AsmTestGenerator.DEFAULT_CRITERIA));
				coverageCriteria = CriteriaEnum.toListOfCriteriaEnum(covCriteriaAttr);
				ATGTActivator.log.debug("criteria " + CriteriaEnum.toListOfString(coverageCriteria));
			} else 	if (generationMode == GenerationMode.RANDOM) {
				nSteps = configuration.getAttribute(
						ATGTLaunchConfigurationTabRnd.CONFIG_NSTEPS, ATGTLaunchConfigurationTabRnd.N_STEPS_DEFAULT);
				nTests = configuration.getAttribute(
						ATGTLaunchConfigurationTabRnd.CONFIG_NTESTS, ATGTLaunchConfigurationTabRnd.N_TESTS_DEFAULT);
			} else {
				ATGTActivator.log.error("mode not found");
			}
			List<String> attribute = configuration.getAttribute(CONFIG_FORMATS, AsmTestGenerator.DEFAULT_FORMATS);
			formats = FormatsEnum.toListOfFormatsEnum(attribute);
			// get the file from the configuration (if it is not empty)
			String filePath = configuration.getAttribute(ATGTLaunchConfigurationTab.ATTR_FILE_PATH, "");
			if (filePath.isEmpty()) {
				ATGTActivator.log.debug("asmeta file not set in the configuration");				
			} else {
				if (asmetaSpecPath == null) asmetaSpecPath = IPath.fromOSString(filePath);
				else ATGTActivator.log.debug("asmeta file already set (constructor?)");
			}
			ATGTActivator.log.error("asmetaSpecPath "+ asmetaSpecPath);
			// get the generation mode
			String genMode = configuration.getAttribute(ATGTLaunchConfigurationTab.GENERATION_MODE, "");
			if (genMode.isEmpty()) {
				ATGTActivator.log.debug("genmode not set in the configuration");								
			} else {
				if (generationMode == null) generationMode = GenerationMode.valueOf(genMode);
				else ATGTActivator.log.debug("generationMode already set (constructor?)");
			}
			return;
		} catch (CoreException e) {
			e.printStackTrace();
			return;
		}
	}
	
	// generate the tests
	void generateTests() throws Error, PartInitException {
		if (asmetaSpecPath == null) {
			ATGTActivator.log.error("Call generateTests with path null");
			return;
		}
		ATGTActivator.log.debug("Call generateTests:  path " + asmetaSpecPath);
		// build the job
		Job generation = getJob();
		// run as job
		BusyIndicator.showWhile(Display.getCurrent(), new Runnable() {
			@Override
			public void run() {
				ISafeRunnable runnable = new ISafeRunnable() {
					@Override
					public void handleException(Throwable exception) {
						System.out.println("Exception in client");
					}

					@Override
					public void run() throws Exception {
						generation.setPriority(Job.SHORT);
						generation.setUser(true);
						generation.schedule();
					}
				};
				SafeRunner.run(runnable);
			}
		});

	}

	private Job getJob() throws PartInitException {
		if (generationMode == GenerationMode.MODEL_CHECKER)
			return new SafeGeneratorRunnableMC(ATGTLaunchConfigurationDelegate.this);
		if (generationMode == GenerationMode.RANDOM)
			return new SafeGeneratorRunnableRnd(ATGTLaunchConfigurationDelegate.this);
		throw new RuntimeException("generationMode not found");
	}
}