package asmeta.visualizer.plugin.handlers;

import org.asmeta.visualizer.graphViewer.GraphEdgesAdder;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;

/**
 * Our sample handler extends AbstractHandler, an IHandler base class.
 * @see org.eclipse.core.commands.IHandler
 * @see org.eclipse.core.commands.AbstractHandler
 */
public class BasicVisualizerHandler extends VisualizerHandler {
	/**
	 * The constructor.
	 */
	public BasicVisualizerHandler() {
	}

	/**
	 * the command has been executed, so extract extract the needed information
	 * from the application context.
	 */
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		GraphEdgesAdder.detectSemanticPatterns = false;
		loadModel(event);
		return null;
	}
}
