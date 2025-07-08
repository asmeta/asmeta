package org.asmeta.atgt.generator.ui;

import org.apache.log4j.Appender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.SimpleLayout;
import org.apache.log4j.WriterAppender;
import org.asmeta.atgt.generator.NuSMVtestGenerator;
import org.asmeta.eclipse.AsmeeConsole;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.ISafeRunnable;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.SafeRunner;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.console.IOConsoleOutputStream;

import atgt.coverage.AsmTestSuite;
import atgt.parser.asmeta.AsmetaLLoader;
import jakarta.inject.Inject;

public abstract class SafeGeneratorRunnable extends Job {

	private String trace;
	protected static AsmeeConsole mc;

	// the AsmTSGeneratorLaunchConfiguration
	protected AsmTSGeneratorLaunchConfiguration config;

	public SafeGeneratorRunnable(String name, AsmTSGeneratorLaunchConfiguration config, IWorkbenchWindow window)
			throws PartInitException {
		super(name);
		this.config = config;
		// build/open the console
		if (mc == null) {
			//IConsoleView view = (IConsoleView) window.getActivePage().showView(IConsoleConstants.ID_CONSOLE_VIEW);	
			//view.display(mc);
			mc = org.asmeta.eclipse.AsmetaUtility.findDefaultConsole();
			mc.activate();
			// add the console as appender to the 
			// set the outstream
			IOConsoleOutputStream out = mc.newOutputStream();
			Appender consoleApp = new WriterAppender(new SimpleLayout(),out);
			Logger.getLogger(NuSMVtestGenerator.class).addAppender(consoleApp);
			Logger.getLogger(NuSMVtestGenerator.class).setLevel(Level.INFO);
			Logger.getLogger(AsmetaLLoader.class).setLevel(Level.ALL);
		}
	}

	@Override
	protected IStatus run(final IProgressMonitor monitor) {
		// wrap with safe runner
		ISafeRunnable runnable = new ISafeRunnable() {
			@Override
			public void handleException(Throwable exception) {
				trace = exception.getMessage();
				exception.printStackTrace();
			}

			@Override
			public void run() {
				// convert to path as string (do not use ospath)
				try {
					// generat the test suite
					AsmTestSuite result = generateTestSuite();
					mc.writeMessage("tests generated, saving to avalla files");
					// save the results
					/*
					 * print on the console ConsolePlugin plugin = ConsolePlugin.getDefault();
					 * IConsoleManager conMan = plugin.getConsoleManager(); MessageConsole myConsole
					 * = new MessageConsole("ATGT", null); conMan.addConsoles(new
					 * IConsole[]{myConsole}); MessageConsoleStream out =
					 * myConsole.newMessageStream();
					 */
					// compute where to save the results
					savetoavalla(result);					
				} catch (Exception e) {
					Logger.getLogger(NuSMVtestGenerator.class).error("ATGT error: "+ e.getMessage());
					e.printStackTrace();
				}
				// referesh the project to show the generated files
				System.out.println("refreshing project");
				try {
					IProject project = ResourcesPlugin.getWorkspace().getRoot().findFilesForLocation(config.asmetaSpecPath)[0].getProject();
					project.refreshLocal(IResource.DEPTH_INFINITE, new NullProgressMonitor());
				} catch (Exception e) {
					e.printStackTrace();
				}
				System.out.println("generation finished");
			}

		};
		monitor.beginTask("Generation", IProgressMonitor.UNKNOWN);
		IsStoppedPolling stopper = new IsStoppedPolling("", monitor, false, config);
		stopper.setSystem(true);
		stopper.schedule();
		SafeRunner.run(runnable);
		if (monitor.isCanceled())
			return Status.CANCEL_STATUS;
		monitor.done();
		monitor.setCanceled(true);
		Display.getDefault().syncExec(new Runnable() {
			@Inject
			Shell shell;

			@Override
			public void run() {
				if (trace != null) {
					MessageDialog.openError(shell, "ERROR", "Generation ERROR \n" + trace);
					throw new RuntimeException("Output null problem with asmeta parsing");
				}
			}
		});

		return Status.OK_STATUS;
	}

	abstract protected AsmTestSuite generateTestSuite() throws Exception;

	abstract protected void savetoavalla(AsmTestSuite result);
}
