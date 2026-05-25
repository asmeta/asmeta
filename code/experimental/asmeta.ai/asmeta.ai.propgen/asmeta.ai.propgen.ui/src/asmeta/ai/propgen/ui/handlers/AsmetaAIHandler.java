package asmeta.ai.propgen.ui.handlers;

import java.io.File;
import java.util.Locale;

import org.asmeta.eclipse.AsmeeConsole;
import org.asmeta.eclipse.AsmetaActionHandler;
import org.asmeta.parser.AsmetaParserUtility;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.text.TextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.texteditor.ITextEditor;

import asmeta.ai.propgen.PropertyGenerationListener;
import asmeta.ai.propgen.ui.Activator;
import asmeta.ai.propgen.ui.services.AsmetaAIRequestType;
import asmeta.ai.propgen.ui.services.AsmetaAIService;
import asmeta.ai.propgen.ui.services.AsmetaAISettings;

public class AsmetaAIHandler extends AsmetaActionHandler {

	public AsmetaAIHandler() {
		super(AsmeeConsole.class, "scheduling AsmetaAI", true);
	}

	@Override
	protected void executeAction(File path, ExecutionEvent event) throws Exception {
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);

		IEditorPart part = window.getActivePage() == null ? null : window.getActivePage().getActiveEditor();
		if (!(part instanceof ITextEditor)) {
			MessageDialog.openWarning(window.getShell(), "AsmetaAI", "Open an ASMETA text editor before running AsmetaAI.");
			return;
		}

		if (!path.getName().toLowerCase(Locale.ROOT).endsWith(AsmetaParserUtility.ASM_EXTENSION)) {
			MessageDialog.openWarning(window.getShell(), "AsmetaAI",
					"Open an ASMETA model (" + AsmetaParserUtility.ASM_EXTENSION + ") before running AsmetaAI.");
			return;
		}

		ITextEditor editor = (ITextEditor) part;
		ISelection editorSelection = editor.getSelectionProvider().getSelection();
		if (!(editorSelection instanceof TextSelection)) {
			MessageDialog.openWarning(window.getShell(), "AsmetaAI", "The current editor selection is not textual.");
			return;
		}
		TextSelection textSelection = (TextSelection) editorSelection;
		String selectedText = textSelection.getText();
		IPreferenceStore preferenceStore = Activator.getDefault().getPreferenceStore();
		AsmetaAISettings settings = AsmetaAISettings.fromPreferenceStore(preferenceStore);
		AsmetaAIRequestType requestType = AsmetaAIRequestType.fromSelection(selectedText);
		Shell shell = window.getShell();

		console.writeMessage(requestType.startMessage(path, settings));
		Job job = new Job("AsmetaAI on " + path.getName()) {
			@Override
			protected IStatus run(IProgressMonitor monitor) {
				monitor.beginTask(requestType.operationDescription(settings), IProgressMonitor.UNKNOWN);
				if (monitor.isCanceled()) {
					return Status.CANCEL_STATUS;
				}
				try {
					AsmetaAIService service = new AsmetaAIService(preferenceStore);
					String out = service.execute(path, selectedText, requestType, new PropertyGenerationListener() {
						@Override
						public void onProgress(String message) {
							console.writeMessage(message);
						}

						@Override
						public void onDebug(String message) {
							if (settings.isDebugOutput()) {
								console.writeMessage("[debug] " + message);
							}
						}
					});
					console.writeMessage("\n" + requestType.successHeader() + "\n" + out + "\n");
					return Status.OK_STATUS;
				} catch (RuntimeException e) {
					String errorMessage = e.getMessage() == null ? e.getClass().getSimpleName() : e.getMessage();
					String operation = requestType.operationDescription(settings);
					console.writeMessage("\nAsmetaAI failed while " + operation + ".\n" + errorMessage + "\n");
					showError(shell, operation, errorMessage);
					return Status.OK_STATUS;
				} finally {
					monitor.done();
				}
			}
		};
		job.setUser(true);
		job.schedule();
	}

	private void showError(Shell shell, String operation, String errorMessage) {
		Display.getDefault().asyncExec(() -> {
			if (shell != null && !shell.isDisposed()) {
				MessageDialog.openError(shell, "AsmetaAI", "AsmetaAI failed while " + operation + ".\n\n" + errorMessage);
			}
		});
	}

	@Override
	protected void setUpLoggers() {
		// No dedicated logger setup for AsmetaAI.
	}

}
