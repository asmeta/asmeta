package asmeta.visualizer.plugin.handlers;

import org.asmeta.eclipse.AsmeeConsole;
import org.asmeta.eclipse.AsmetaActionHandler;
import org.asmeta.eclipse.AsmetaConsole;
import org.asmeta.eclipse.AsmetaUtility;
import org.asmeta.visualizer.graphViewer.AsmGraphViewerVisualizer;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;

/**
 * Our sample handler extends AbstractHandler, an IHandler base class.
 * @see org.eclipse.core.commands.IHandler
 * @see org.eclipse.core.commands.AbstractHandler
 */
public abstract class VisualizerHandler extends AsmetaActionHandler {

	protected VisualizerHandler() {
		super(AsmeeConsole.class, "visualazing", false);
	}

	@Override
	protected void setUpLoggers() {
		// TODO Auto-generated method stub
	}

	
}
