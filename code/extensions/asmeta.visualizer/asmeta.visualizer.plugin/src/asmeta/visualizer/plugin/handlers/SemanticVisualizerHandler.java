package asmeta.visualizer.plugin.handlers;

import java.io.File;

import org.asmeta.visualizer.graphViewer.AsmGraphViewerVisualizer;
import org.asmeta.visualizer.graphViewer.GraphEdgesAdder;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;

/**
 * Our sample handler extends AbstractHandler, an IHandler base class.
 * @see org.eclipse.core.commands.IHandler
 * @see org.eclipse.core.commands.AbstractHandler
 */
public class SemanticVisualizerHandler extends VisualizerHandler {
	/**
	 * The constructor.
	 */
	public SemanticVisualizerHandler() {
	}

	@Override
	protected void executeAction(File path) throws Exception {
		GraphEdgesAdder.detectSemanticPatterns = true;
		AsmGraphViewerVisualizer.showGraph(path.getAbsolutePath());		
	}

}
