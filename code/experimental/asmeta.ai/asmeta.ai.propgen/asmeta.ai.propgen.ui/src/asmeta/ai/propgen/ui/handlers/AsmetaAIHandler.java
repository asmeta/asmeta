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
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.TextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.texteditor.IDocumentProvider;
import org.eclipse.ui.texteditor.ITextEditor;

import asmeta.ai.propgen.AsmetaAIOperationListener;
import asmeta.ai.propgen.ui.Activator;
import asmeta.ai.propgen.ui.services.AsmetaAIRequestType;
import asmeta.ai.propgen.ui.services.AsmetaAIService;
import asmeta.ai.propgen.ui.services.AsmetaAISettings;
import asmeta.ai.propgen.util.AsmetaAIContentInserter;
import asmeta.ai.propgen.util.AsmetaAIContentInserter.InsertionEdit;

public class AsmetaAIHandler extends AsmetaActionHandler {

	/**
	 * Creates the Eclipse command handler for AsmetaAI.
	 */
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
		int selectionOffset = textSelection.getOffset();
		int selectionLength = textSelection.getLength();
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
					String out = service.execute(path, selectedText, requestType, new AsmetaAIOperationListener() {
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
					if (settings.isDebugOutput()) {
						console.writeMessage("\n" + requestType.successHeader() + "\n" + out + "\n");
					}
					insertGeneratedContent(editor, selectionOffset, selectionLength, requestType, settings, out, shell);
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

	private void insertGeneratedContent(ITextEditor editor, int selectionOffset, int selectionLength,
			AsmetaAIRequestType requestType, AsmetaAISettings settings, String generatedText, Shell shell) {
		Display.getDefault().asyncExec(() -> {
			try {
				IDocument document = getDocument(editor);
				InsertionEdit edit = insertionEdit(document.get(), selectionOffset, selectionLength, requestType, settings,
						generatedText);
				document.replace(edit.offset, 0, edit.text);
				restoreSelection(editor, document, selectionOffset, selectionLength, edit);
				console.writeMessage("\nAsmetaAI inserted generated content in the editor.\n");
			} catch (RuntimeException | BadLocationException e) {
				String errorMessage = e.getMessage() == null ? e.getClass().getSimpleName() : e.getMessage();
				console.writeMessage("\nAsmetaAI failed while inserting generated content.\n" + errorMessage + "\n");
				showError(shell, "inserting generated content", errorMessage);
			}
		});
	}

	private IDocument getDocument(ITextEditor editor) {
		IDocumentProvider documentProvider = editor.getDocumentProvider();
		IEditorInput editorInput = editor.getEditorInput();
		IDocument document = documentProvider == null ? null : documentProvider.getDocument(editorInput);
		if (document == null) {
			throw new RuntimeException("Could not access the open ASMETA editor document.");
		}
		return document;
	}

	private InsertionEdit insertionEdit(String currentText, int selectionOffset, int selectionLength,
			AsmetaAIRequestType requestType, AsmetaAISettings settings, String generatedText) {
		switch (requestType) {
		case ASM_TO_NL:
			return AsmetaAIContentInserter.asmToNlEdit(currentText, generatedText);
		case TL_TO_NL:
			return AsmetaAIContentInserter.tlToNlEdit(currentText, selectionOffset, generatedText);
		case NL_TO_TL:
			return AsmetaAIContentInserter.nlToTlEdit(currentText, selectionOffset, selectionLength, generatedText,
					settings.getPropertyType());
		default:
			throw new IllegalStateException("Unexpected request type: " + requestType);
		}
	}

	private void restoreSelection(ITextEditor editor, IDocument document, int selectionOffset, int selectionLength,
			InsertionEdit edit) {
		// Keep the user's original selection visible after inserting text before it.
		int restoredOffset = edit.offset <= selectionOffset ? selectionOffset + edit.text.length() : selectionOffset;
		editor.getSelectionProvider().setSelection(new TextSelection(document, restoredOffset, selectionLength));
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
