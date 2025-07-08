package org.asmeta.atgt.generator.ui;

import java.util.List;

import org.asmeta.atgt.generator.AsmTestGenerator;
import org.asmeta.atgt.generator.CriteriaEnum;
import org.asmeta.atgt.generator.FormatsEnum;
import org.asmeta.eclipse.AsmetaUtility;
import org.eclipse.core.resources.IFile;
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
import static org.asmeta.atgt.generator.ui.AsmTSGeneratorTabMC.*;

/**
 * http://www.vogella.com/tutorials/EclipseLauncherFramework/article.html
 * 
 * @author garganti
 *
 */
public class AsmTSGeneratorLaunchConfiguration
		extends LaunchConfigurationDelegate /* implements ILaunchConfigurationDelegate */ {
	// two generation modes
	// in the future this can be implements with 2 subclasses
	public enum GenerationMode {
		MOCEL_CHECKER, RANDOM
	}
	private GenerationMode mode;
	// common
	public boolean computeCoverage;
	public IPath asmetaSpecPath;
	public List<FormatsEnum> formats;
	// used only by the model checker
	public List<CriteriaEnum> coverageCriteria;
	// used by random only
	int nSteps, nTests;

	// it is necessary since it needs the constructor without parameters
	public AsmTSGeneratorLaunchConfiguration() {}

	public AsmTSGeneratorLaunchConfiguration(ILaunchConfiguration configuration, GenerationMode mode) {
		try {
			this.mode = mode;
			setConfiguration(configuration);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void launch(ILaunchConfiguration configuration, String mode, ILaunch launch, IProgressMonitor monitor)
			throws CoreException {
		System.out.println("AsmTSGeneratorLaunchConfiguration:launch");
		setConfiguration(configuration);
		// get the current ASM file if any
		IWorkbench workbench = PlatformUI.getWorkbench();
		if (workbench == null) {
			System.err.println("Call generateTests with workbench null");
			return;
		}
		IWorkbenchWindow window = workbench.getActiveWorkbenchWindow();
		// get the file
		IFile path = AsmetaUtility.getEditorIFile(window);
		// open the simulator console
		// generate the tests
		generateTests(path.getFullPath(), window);
	}

	private AsmTSGeneratorLaunchConfiguration setConfiguration(ILaunchConfiguration configuration) {
		try {
			System.out.println("Setting launch configuration: " + configuration);
			computeCoverage = configuration.getAttribute(CONFIG_COMPUTE_COVERAGE,
					AsmTestGenerator.DEFAULT_COMPUTE_COVERAGE);
			System.out.println("compute coverage?" + computeCoverage);
			if (mode == GenerationMode.MOCEL_CHECKER) {
				List<String> covCriteriaAttr = configuration.getAttribute(
						AsmTSGeneratorTabMC.CONFIG_CRITERIA, CriteriaEnum.toListOfString(AsmTestGenerator.DEFAULT_CRITERIA));
				coverageCriteria = CriteriaEnum.toListOfCriteriaEnum(covCriteriaAttr);
				System.out.println("criteria " + CriteriaEnum.toListOfString(coverageCriteria));
			}
			if (mode == GenerationMode.RANDOM) {
				nSteps = configuration.getAttribute(
						AsmTSGeneratorTabRnd.CONFIG_NSTEPS, AsmTSGeneratorTabRnd.N_STEPS_DEFAULT);
				nTests = configuration.getAttribute(
						AsmTSGeneratorTabRnd.CONFIG_NTESTS, AsmTSGeneratorTabRnd.N_TESTS_DEFAULT);
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
			System.err.println("Call generateTests with path null");
			return;
		}
		System.err.println("Call generateTests");
		this.asmetaSpecPath = asmetaSpecPath;
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
				System.err.println("Call generateTests");
				// now refresh the project
				// window.ge
				// project.refreshLocal(IResource.DEPTH_INFINITE, new NullProgressMonitor());
			}
		});

	}

	protected Job getJob(IWorkbenchWindow window) throws PartInitException {
		if (mode == GenerationMode.MOCEL_CHECKER)
			return new SafeGeneratorRunnableMC(AsmTSGeneratorLaunchConfiguration.this, window);
		if (mode == GenerationMode.RANDOM)
			return new SafeGeneratorRunnableRnd(AsmTSGeneratorLaunchConfiguration.this, window);
		throw new RuntimeException("mode not found");
	}
}