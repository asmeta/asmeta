package org.asmeta.atgt.generator.ui;

import java.util.List;

import javax.inject.Inject;

import org.apache.log4j.Appender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.SimpleLayout;
import org.apache.log4j.WriterAppender;
import org.asmeta.atgt.generator.CriteriaEnum;
import org.asmeta.atgt.generator.NuSMVtestGenerator;
import org.asmeta.atgt.generator.SaveResults;
import org.asmeta.eclipse.AsmeeConsole;
import org.asmeta.eclipse.AsmetaUtility;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.ISafeRunnable;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.SafeRunner;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.console.IConsoleConstants;
import org.eclipse.ui.console.IConsoleView;
import org.eclipse.ui.console.IOConsoleOutputStream;

import atgt.coverage.AsmCoverageBuilder;
import atgt.coverage.AsmTestSuite;
import atgt.parser.asmeta.AsmetaLLoader;

public class SafeGeneratorRunnable extends Job {

	private String trace;
	private static AsmeeConsole mc;

	private AsmTSGeneratorLaunchConfiguration config;

	public SafeGeneratorRunnable(AsmTSGeneratorLaunchConfiguration config, IWorkbenchWindow window)
			throws PartInitException {
		super("Generation of the test suite");
		this.config = config;
		// build/open the console
		if (mc == null) {
			IConsoleView view = (IConsoleView) window.getActivePage().showView(IConsoleConstants.ID_CONSOLE_VIEW);			
			mc = AsmetaUtility.findDefaultConsole();
			view.display(mc);
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
					List<AsmCoverageBuilder> coverageCriteria = CriteriaEnum.getCoverageCriteria(config.coverageCriteria);
					System.out.println(config.asmetaSpecPath.toString() + "  -  " + config.computeCoverage + "  -  "
							+ coverageCriteria);
					NuSMVtestGenerator generator = new NuSMVtestGenerator(config.asmetaSpecPath.toString(),
							config.computeCoverage);
					mc.writeMessage("generating the test with coverage criteria " + coverageCriteria);
					// generate all the possible tests
					AsmTestSuite result = generator.generateAbstractTests(coverageCriteria, Integer.MAX_VALUE, ".*");
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
					SaveResults.saveResults(result, config.asmetaSpecPath.toString(), config.formats, SaveResults
							.toStringForFile(config.computeCoverage, config.coverageCriteria, config.formats));
				} catch (Exception e) {
					Logger.getLogger(NuSMVtestGenerator.class).error("ATGT error: "+ e.getMessage());
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
				if (trace != null) {
					MessageDialog.openError(shell, "ERROR", "Generation ERROR \n" + trace);
					throw new RuntimeException("Output null problem with citModel parsing");
				}
			}
		});

		return Status.OK_STATUS;
	}
}
