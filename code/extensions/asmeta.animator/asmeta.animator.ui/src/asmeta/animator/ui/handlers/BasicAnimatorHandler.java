package asmeta.animator.ui.handlers;

import org.asmeta.animator.VisualizationSimulation;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;

/**
 * Our sample handler extends AbstractHandler, an IHandler base class.
 * @see org.eclipse.core.commands.IHandler
 * @see org.eclipse.core.commands.AbstractHandler
 */
public abstract class BasicAnimatorHandler extends AbstractHandler {

	protected void loadModel(ExecutionEvent event) throws Exception {
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
		String path = null;
		IEditorInput editorInput = window.getActivePage().getActiveEditor().getEditorInput();
		if(editorInput instanceof org.eclipse.ui.part.FileEditorInput) {
			path = ((org.eclipse.ui.part.FileEditorInput)editorInput).getURI().getPath();
		}
		else if(editorInput instanceof org.eclipse.ui.ide.FileStoreEditorInput) {
			path = ((org.eclipse.ui.ide.FileStoreEditorInput)editorInput).getURI().getPath();
		}
		else {
			throw new Error("Unknown editor " + editorInput.getClass().getSimpleName());
		}
		VisualizationSimulation.showView(path);
	}
}
