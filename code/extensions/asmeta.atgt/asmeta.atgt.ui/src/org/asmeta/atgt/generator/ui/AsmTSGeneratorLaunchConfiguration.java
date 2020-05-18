package org.asmeta.atgt.generator.ui;

import java.util.List;

import org.asmeta.atgt.generator.AsmTestGenerator;
import org.asmeta.atgt.generator.CriteriaEnum;
import org.asmeta.atgt.generator.FormatsEnum;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.ISafeRunnable;
import org.eclipse.core.runtime.SafeRunner;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.model.ILaunchConfigurationDelegate;
import org.eclipse.swt.custom.BusyIndicator;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IPathEditorInput;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.FileEditorInput;

/**
 * http://www.vogella.com/tutorials/EclipseLauncherFramework/article.html
 * 
 * @author garganti
 *
 */
public class AsmTSGeneratorLaunchConfiguration implements ILaunchConfigurationDelegate {
	
	public boolean computeCoverage;
	public List<CriteriaEnum> coverageCriteria;
	public IPath asmetaSpecPath;
	public List<FormatsEnum> formats;
	
	public AsmTSGeneratorLaunchConfiguration() {}
	public AsmTSGeneratorLaunchConfiguration(ILaunchConfiguration configuration) {
		try {
			setConfiguration(configuration);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void launch(ILaunchConfiguration configuration, String mode, ILaunch launch, IProgressMonitor monitor) 
			throws CoreException {
		System.out.println("AsmTSGeneratorLaunchConfiguration:launch");
		setConfiguration(configuration);		
		// get the current ASM file if any
		IEditorPart editor = getCurrentActiveFilePath();
		/*
		 * if(editorInput instanceof org.eclipse.ui.ide.FileStoreEditorInput) { path =
		 * ((org.eclipse.ui.ide.FileStoreEditorInput)editorInput).getURI().getPath();
		 */
		generateTests(editor);
	}
	
	public AsmTSGeneratorLaunchConfiguration setConfiguration(ILaunchConfiguration configuration) throws CoreException {
		System.out.println("Setting launch configuration: "+configuration);
		System.out.println(configuration.getAttribute(AsmTSGeneratorTab.CONFIG_COMPUTE_COVERAGE, AsmTestGenerator.DEFAULT_COMPUTE_COVERAGE) + " " + configuration.getAttribute(AsmTSGeneratorTab.CONFIG_CRITERIA, CriteriaEnum.toListOfString(AsmTestGenerator.DEFAULT_CRITERIA)));
		coverageCriteria = CriteriaEnum.toListOfCriteriaEnum(configuration.getAttribute(AsmTSGeneratorTab.CONFIG_CRITERIA,  CriteriaEnum.toListOfString(AsmTestGenerator.DEFAULT_CRITERIA)));
		computeCoverage = configuration.getAttribute(AsmTSGeneratorTab.CONFIG_COMPUTE_COVERAGE,AsmTestGenerator.DEFAULT_COMPUTE_COVERAGE);
		formats = FormatsEnum.toListOfFormatsEnum(configuration.getAttribute(AsmTSGeneratorTab.CONFIG_FORMATS, AsmTestGenerator.DEFAULT_FORMATS));
		return this;
	}
	
	private IEditorPart getCurrentActiveFilePath() {
		IWorkbench workbench = PlatformUI.getWorkbench();
		IWorkbenchWindow window = workbench == null ? null : workbench.getActiveWorkbenchWindow();
		IWorkbenchPage activePage = window == null ? null : window.getActivePage();
		IEditorPart editor = activePage == null ? null : activePage.getActiveEditor();				
		return editor;
	}

	void generateTests(IEditorPart editor) throws Error {
		IEditorInput input = editor == null ? null : editor.getEditorInput();
		IPath ipath = input instanceof IPathEditorInput ? ((IPathEditorInput) input).getPath() : null;
		if (ipath == null) {
			System.err.println("path not found");
//			throw new Error("Unknown editor " + input.getClass().getSimpleName());
			System.err.println("Unknown editor " + editor);
		}
		generateTests(ipath);
	}

	// 
	void generateTests(IPath asmetaSpecPath) throws Error {
		if (asmetaSpecPath==null) {
			System.out.println("Call generateTests with path null");
			return;
		}
		System.out.println("Call generateTests");
		this.asmetaSpecPath = asmetaSpecPath;
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
						Job generation = new SafeGeneratorRunnable("Generation of the test suite", AsmTSGeneratorLaunchConfiguration.this);
						generation.setPriority(Job.SHORT);
						generation.setUser(true);
						generation.schedule();
					
					}
				};
				SafeRunner.run(runnable);
			}
		});		
		
	}
}