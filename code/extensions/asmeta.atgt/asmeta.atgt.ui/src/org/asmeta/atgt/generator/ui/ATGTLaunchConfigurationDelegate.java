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
	public IPath asmetaSpecPath;
	public List<FormatsEnum> formats;
	// used only by the model checker
	public List<CriteriaEnum> coverageCriteria;
	// used by random only
	int nSteps, nTests;

	// it is necessary since it needs the constructor without parameters
	public ATGTLaunchConfigurationDelegate() {}

	public ATGTLaunchConfigurationDelegate(ILaunchConfiguration configuration, GenerationMode mode) {
		try {
			this.generationMode = mode;
			setConfiguration(configuration);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void launch(ILaunchConfiguration configuration, String mode, ILaunch launch, IProgressMonitor monitor)
			throws CoreException {
		ATGTActivator.log.debug("ATGTLaunchConfigurationDelegate:launch");
		ATGTActivator.log.debug("conf attributes " + configuration.getAttributes());
		ATGTActivator.log.debug(this.generationMode + " vs " + mode);
		// adesso come adesso questa è nulla (vuota) a meno che usi il costruttore con l'argomento
		ATGTActivator.log.debug("asmetaSpecPath " + asmetaSpecPath);
		// get the current ASM file if any
		IWorkbench workbench = PlatformUI.getWorkbench();
		if (workbench == null) {
			System.err.println("Call generateTests with workbench null");
			return;
		}
		// now get the file
		// mode 1. try to take the file open (it may wrong since you could select a file in the tree while having open another in the editor
		IWorkbenchWindow window = workbench.getActiveWorkbenchWindow();
		if (window != null) {
			// get the file from the editor (correct?)
			asmetaSpecPath = AsmetaUtility.getEditorIFile(window).getFullPath();
		} else {
			// mode 2.
			String filePath = configuration.getAttribute(ATGTLaunchConfigurationTab.ATTR_FILE_PATH, "");
			if (filePath.isEmpty()) {
				ATGTActivator.log.debug("asmeta file not set in the configuration");
				asmetaSpecPath = null;
			} else {
				asmetaSpecPath = IPath.fromOSString(filePath);
			}
		}
		ATGTActivator.log.error("new asmetaSpecPath "+ asmetaSpecPath);
		// get the generation mode
		String genMode = configuration.getAttribute(ATGTLaunchConfigurationTab.GENERATION_MODE, "");
		if (genMode.isEmpty()) {
			ATGTActivator.log.error("genmode not set in the configuration");
		} else {
//			if (generationMode == null) {
//				generationMode = GenerationMode.valueOf(genMode);
//			} else {
//				ATGTActivator.log.debug("generationMode already set (constructor?)");
//			}
			// sovrascrivi in ogni caso il mode - assuming that the mode is correctly set in the configuration
			generationMode = GenerationMode.valueOf(genMode);
		}
		ATGTActivator.log.debug("new generationMode "+ generationMode);
		// set other stuff that depens on the mode:
		setConfiguration(configuration);
		// open the simulator console
		// generate the tests
		generateTests(asmetaSpecPath, window);
	}

	private ATGTLaunchConfigurationDelegate setConfiguration(ILaunchConfiguration configuration) {
		try {
			ATGTActivator.log.debug("Setting launch configuration: " + configuration);
			computeCoverage = configuration.getAttribute(CONFIG_COMPUTE_COVERAGE,
					AsmTestGenerator.DEFAULT_COMPUTE_COVERAGE);
			ATGTActivator.log.debug("compute coverage?" + computeCoverage);
			assert generationMode != null;
			if (generationMode == GenerationMode.MODEL_CHECKER) {
				List<String> covCriteriaAttr = configuration.getAttribute(
						ATGTLaunchConfigurationTabMC.CONFIG_CRITERIA, CriteriaEnum.toListOfString(AsmTestGenerator.DEFAULT_CRITERIA));
				coverageCriteria = CriteriaEnum.toListOfCriteriaEnum(covCriteriaAttr);
				ATGTActivator.log.debug("criteria " + CriteriaEnum.toListOfString(coverageCriteria));
			}
			if (generationMode == GenerationMode.RANDOM) {
				nSteps = configuration.getAttribute(
						ATGTLaunchConfigurationTabRnd.CONFIG_NSTEPS, ATGTLaunchConfigurationTabRnd.N_STEPS_DEFAULT);
				nTests = configuration.getAttribute(
						ATGTLaunchConfigurationTabRnd.CONFIG_NTESTS, ATGTLaunchConfigurationTabRnd.N_TESTS_DEFAULT);
			}
			List<String> attribute = configuration.getAttribute(CONFIG_FORMATS, AsmTestGenerator.DEFAULT_FORMATS);
			formats = FormatsEnum.toListOfFormatsEnum(attribute);
			return this;
		} catch (CoreException e) {
			e.printStackTrace();
			return null;
		}
	}

	// generate the tests
	void generateTests(IPath asmetaSpecPath, IWorkbenchWindow window) throws Error, PartInitException {
		if (asmetaSpecPath == null) {
			ATGTActivator.log.error("Call generateTests with path null");
			return;
		}
		this.asmetaSpecPath = asmetaSpecPath;
		ATGTActivator.log.info("Call generateTests");
		// build the job
		Job generation = getJob(window);
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

	protected Job getJob(IWorkbenchWindow window) throws PartInitException {
		if (generationMode == GenerationMode.MODEL_CHECKER) {
			return new SafeGeneratorRunnableMC(ATGTLaunchConfigurationDelegate.this, window);
		}
		if (generationMode == GenerationMode.RANDOM) {
			return new SafeGeneratorRunnableRnd(ATGTLaunchConfigurationDelegate.this, window);
		}
		throw new RuntimeException("generationMode not found");
	}
}