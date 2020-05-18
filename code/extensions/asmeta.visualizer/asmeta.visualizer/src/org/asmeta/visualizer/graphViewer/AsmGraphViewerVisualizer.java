package org.asmeta.visualizer.graphViewer;
import java.io.File;
import java.util.HashSet;
import java.util.List;

import org.asmeta.parser.ASMParser;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.zest.core.widgets.CGraphNode;
import org.eclipse.zest.core.widgets.GraphConnection;
import org.eclipse.zest.core.widgets.GraphNode;

import asmeta.AsmCollection;
import asmeta.transitionrules.basictransitionrules.MacroDeclaration;
import asmeta.transitionrules.basictransitionrules.Rule;

/**
 * 
 */
public class AsmGraphViewerVisualizer {
	public final static int VERTICAL_SCALING_FACTOR = 3;
	public final static int HORIZONTAL_SCALING_FACTOR = 5;

	public static void showGraph(String modelPath) {
		Display d = Display.getDefault();
		final Shell shell = new Shell(d);
		shell.setText(modelPath);
		shell.setLayout(new FillLayout(SWT.VERTICAL));
		File spec = new File(modelPath);
		AsmCollection x = null;
		try {
			x = ASMParser.setUpReadAsm(spec);
		} catch (Exception e1) {
			//e1.printStackTrace();
			MessageBox message = new MessageBox(shell);
			message.setMessage("Error while parsing " + modelPath + "\n\n" + e1.getLocalizedMessage());
			message.setText("Parsing error");
			message.open();
		}
		asmeta.structure.Asm asm = x.getMain();
		MacroDeclaration main = asm.getMainrule();
		Rule ruleBody = main.getRuleBody();
		AsmGraphListener.calledShells.put(shell, new HashSet<Shell>());
		AsmGraphListener.calledByShells.put(shell, new HashSet<Shell>());
		showGraph(ruleBody, shell);
	}

	public static void showGraph(Rule r, Shell shell) {
		AsmGraphViewer asg = new AsmGraphViewer(shell, r);
		asg.setLayoutAlgorithm(AsmGraphViewer.LAYOUT);
		//semantic visualization can produce graphs.
		//the current method is not able to detect the size
		//of graphs
		if(!GraphEdgesAdder.detectSemanticPatterns) {
			//TODO: find the correct way to resize the model
			//double[] size = getSize(asg);
			//shell.setSize((int)size[0], (int)size[1]);
		}
		shell.open();
		while (!shell.isDisposed()) {
			Display d = Display.getDefault();
			while (!d.readAndDispatch()) {
				d.sleep();
			}
		}
	}

	private static double[] getSize(AsmGraphViewer asg) {
		GraphNode root = null;
		for(Object n: asg.getGraphControl().getNodes()) {
			CGraphNode node = (CGraphNode)n;
			if(node.getTargetConnections().size() == 0) {
				root = node;
				break;
			}
		}
		return getSize(root);
	}

	private static double[] getSize(GraphNode node) {
		List<?> exitingConnections = node.getSourceConnections();
		org.eclipse.draw2d.geometry.Dimension nodeSize = node.getSize();
		System.out.println(node + "\t" + nodeSize);
		double[] size = new double[2];
		if(exitingConnections.size() != 0) {
			double height = 0;
			double maxWidth = -1;
			for(Object c: exitingConnections) {
				GraphConnection connection = (GraphConnection)c;
				double[] currNodeSize = getSize(connection.getDestination());
				if(maxWidth < currNodeSize[0]) {
					maxWidth = currNodeSize[0];
				}
				height += (currNodeSize[1]);
			}
			size[0] = maxWidth;
			size[1] = height;
		}
		else {
			size[0] = nodeSize.width*HORIZONTAL_SCALING_FACTOR;
			size[1] = nodeSize.height*VERTICAL_SCALING_FACTOR;
		}
		return new double[]{(size[0]), (size[1])};
	}
}