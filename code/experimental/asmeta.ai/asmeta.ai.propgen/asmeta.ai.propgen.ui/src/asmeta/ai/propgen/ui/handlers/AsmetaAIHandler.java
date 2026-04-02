package asmeta.ai.propgen.ui.handlers;

import java.io.File;

import org.asmeta.eclipse.AsmeeConsole;
import org.asmeta.eclipse.AsmetaActionHandler;
import org.asmeta.eclipse.AsmetaConsole;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.ISelectionService;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.internal.WorkbenchWindow;
import org.eclipse.ui.texteditor.IDocumentProvider;
import org.eclipse.ui.texteditor.ITextEditor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

public class AsmetaAIHandler extends AsmetaActionHandler {

	public AsmetaAIHandler() {
		super(AsmeeConsole.class,"analysing with AI", true);
	}

	@Override
	protected void executeAction(File path, ExecutionEvent event) throws Exception {
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
		ISelectionService selection = window.getSelectionService();
		ISelection select = selection.getSelection();
		console.writeMessage("operating over " + path + " selection " + select.toString());
		IEditorPart part;

		part = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor();

		if(part instanceof ITextEditor){
		    ITextEditor editor = (ITextEditor)part;
		    IDocumentProvider provider = editor.getDocumentProvider();
		    IDocument document = provider.getDocument(editor.getEditorInput());
		    console.writeMessage("operating over " + path + " selection " + select.toString());
		    ISelection selection1 = editor.getSelectionProvider().getSelection();
		    console.writeMessage("selection: " + selection1);
		}
	}

	@Override
	protected void setUpLoggers() {
		// TODO Auto-generated method stub
		
	}
}
