package asmeta.visualizer.plugin.handlers;

import java.io.File;

import org.asmeta.eclipse.AsmetaConsole;
import org.asmeta.visualizer.graphViewer.AsmGraphViewerVisualizer;
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

	@Override
	protected void executeAction(File path) throws Exception {
		GraphEdgesAdder.detectSemanticPatterns = false;
		AsmGraphViewerVisualizer.showGraph(path.getAbsolutePath());		
	}

}
