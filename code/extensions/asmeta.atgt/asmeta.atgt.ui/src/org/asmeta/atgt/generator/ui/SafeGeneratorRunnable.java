package org.asmeta.atgt.generator.ui;

import javax.inject.Inject;

import org.asmeta.atgt.generator.CriteriaEnum;
import org.asmeta.atgt.generator.NuSMVtestGenerator;
import org.asmeta.atgt.generator.SaveResults;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.ISafeRunnable;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.SafeRunner;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import atgt.coverage.AsmTestSuite;

public class SafeGeneratorRunnable extends Job {
	private String trace;

	private SafeGeneratorRunnable() {
		super("Generation of the testuite");
	}
	
	private AsmTSGeneratorLaunchConfiguration config;

	public SafeGeneratorRunnable(String name, AsmTSGeneratorLaunchConfiguration config) {
		super(name);
		this.config = config;
	}

	@Override
	protected IStatus run(final IProgressMonitor monitor) {

		ISafeRunnable runnable = new ISafeRunnable() {

			@Override
			public void handleException(Throwable exception) {
				trace = exception.getMessage();
				exception.printStackTrace();		
			}

			@Override
			public void run() {
				System.out.println("In safe runner");
				// convert to path as string (do not use ospath)
				try {
					System.out.println(config.asmetaSpecPath.toString()+"  -  "+config.computeCoverage+"  -  "+CriteriaEnum.getCoverageCriteria(config.coverageCriteria));
					NuSMVtestGenerator generator = new NuSMVtestGenerator(config.asmetaSpecPath.toString(), config.computeCoverage, CriteriaEnum.getCoverageCriteria(config.coverageCriteria));
					// generate all the possible tests
					AsmTestSuite result = generator.generateAbstractTests(Integer.MAX_VALUE, ".*");
					// save the results
					/*
					 * print on the console ConsolePlugin plugin = ConsolePlugin.getDefault();
					 * IConsoleManager conMan = plugin.getConsoleManager(); MessageConsole myConsole
					 * = new MessageConsole("ATGT", null); conMan.addConsoles(new
					 * IConsole[]{myConsole}); MessageConsoleStream out =
					 * myConsole.newMessageStream();
					 */
					// compute where to save the results
					SaveResults.saveResults(result,config.asmetaSpecPath.toString(), config.formats, SaveResults.toStringForFile(config.computeCoverage, config.coverageCriteria, config.formats));
				} catch (Exception e) {
					e.printStackTrace();
				}
				System.out.println("End of safe runner ");
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
				if (trace!=null) {
					MessageDialog.openError(shell, "ERROR", "Generation ERROR \n" + trace);
					throw new RuntimeException("Output null problem with citModel parsing");
				}
			}
		});

		return Status.OK_STATUS;
	}
}
