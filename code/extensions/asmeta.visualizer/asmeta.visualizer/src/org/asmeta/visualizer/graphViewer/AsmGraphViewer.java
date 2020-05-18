package org.asmeta.visualizer.graphViewer;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.zest.core.viewers.GraphViewer;
import org.eclipse.zest.core.widgets.ZestStyles;
import org.eclipse.zest.layouts.LayoutAlgorithm;
import org.eclipse.zest.layouts.LayoutStyles;
import org.eclipse.zest.layouts.algorithms.HorizontalTreeLayoutAlgorithm;

import asmeta.transitionrules.basictransitionrules.Rule;

/** extends graph viewer with things needed by ASMs - label, content, listener */
public class AsmGraphViewer extends GraphViewer {
	//public static LayoutAlgorithm LAYOUT = new HorizontalTreeLayoutAlgorithm(LayoutStyles.NO_LAYOUT_NODE_RESIZING);
	public static LayoutAlgorithm LAYOUT = new HorizontalTreeLayoutAlgorithm(LayoutStyles.ENFORCE_BOUNDS);

	public AsmGraphViewer(Shell shell, Rule ruleBody) {
		super(shell, SWT.NONE);
		setContentProvider(new AsmContentProvider());
		setLabelProvider(new AsmLabelProvider());
		setLayoutAlgorithm(LAYOUT);
		setConnectionStyle(ZestStyles.CONNECTIONS_DIRECTED);
		//setNodeStyle(ZestStyles.NODES_FISHEYE);
		setNodeStyle(ZestStyles.CONNECTIONS_DOT);
		addDoubleClickListener(new AsmGraphListener(shell));
		setInput(ruleBody);
		applyLayout();
	}
}