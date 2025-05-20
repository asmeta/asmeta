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
import static org.asmeta.atgt.generator.ui.AsmTSGeneratorTab.*;

/**
 * http://www.vogella.com/tutorials/EclipseLauncherFramework/article.html
 * 
 * @author garganti
 *
 */
public class AsmTSGeneratorLaunchConfiguration
		extends LaunchConfigurationDelegate /* implements ILaunchConfigurationDelegate */ {

	public boolean computeCoverage;
	public List<CriteriaEnum> coverageCriteria;
	public IPath asmetaSpecPath;
	public List<FormatsEnum> formats;

	// necessario perch� ha bisogno del costruttore senza parametri
	public AsmTSGeneratorLaunchConfiguration() {
	}

	public AsmTSGeneratorLaunchConfiguration(ILaunchConfiguration configuration) {
		try {
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
			boolean computeCoverageConfig = configuration.getAttribute(CONFIG_COMPUTE_COVERAGE,
					AsmTestGenerator.DEFAULT_COMPUTE_COVERAGE);
			System.out.println(computeCoverageConfig + " " + configuration.getAttribute(CONFIG_CRITERIA,
					CriteriaEnum.toListOfString(AsmTestGenerator.DEFAULT_CRITERIA)));
			coverageCriteria = CriteriaEnum.toListOfCriteriaEnum(configuration.getAttribute(
					AsmTSGeneratorTab.CONFIG_CRITERIA, CriteriaEnum.toListOfString(AsmTestGenerator.DEFAULT_CRITERIA)));
			computeCoverage = computeCoverageConfig;
			formats = FormatsEnum
					.toListOfFormatsEnum(configuration.getAttribute(CONFIG_FORMATS, AsmTestGenerator.DEFAULT_FORMATS));
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
		Job generation = new SafeGeneratorRunnable(AsmTSGeneratorLaunchConfiguration.this, window);
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
}