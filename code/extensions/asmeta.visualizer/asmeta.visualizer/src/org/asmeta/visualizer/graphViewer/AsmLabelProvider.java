package org.asmeta.visualizer.graphViewer;

import org.asmeta.visualizer.graphViewer.GraphEdgesAdder.Edge;
import org.eclipse.draw2d.BorderLayout;
import org.eclipse.draw2d.Ellipse;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.RectangleFigure;
import org.eclipse.draw2d.RoundedRectangle;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Color;
import org.eclipse.zest.core.viewers.IFigureProvider;

/**
 * It handles the shapes and the labels of nodes.
 *
 */
class AsmLabelProvider extends LabelProvider implements IFigureProvider {
	private static final Color NODE_COLOR = org.eclipse.draw2d.ColorConstants.button;
	private static final Color LABELED_POINT_NODE_COLOR = org.eclipse.draw2d.ColorConstants.yellow;
	private static final Color STATE_NODE_COLOR = org.eclipse.draw2d.ColorConstants.orange;
	private static final Color POINT_NODE_COLOR = org.eclipse.draw2d.ColorConstants.cyan;
	private static final Color FORALL_NODE_COLOR = org.eclipse.draw2d.ColorConstants.orange;
	private static final Color COND_NODE_COLOR = org.eclipse.draw2d.ColorConstants.green;

	/** label */
	@Override
	public String getText(Object element) {
		if (element instanceof PointNode) {
			return "";
		}
		if (element instanceof LabeledPointNode){
			return ((LabeledPointNode)element).label;
		}
		if (element instanceof LabeledNode) {
			return ((LabeledNode) element).label;
		} else {
			return ((Edge)element).getLabel();
			
		}
	}

	/** figure: a seconda del tipo di nodo costruisce figure diverse */
	@Override
	public IFigure getFigure(Object node) {
		Label l;
		if (node instanceof Edge){
			assert false;
			/*Edge edge = (Edge)node;
			PolylineConnection conn = new PolylineConnection();
			conn.setBackgroundColor(org.eclipse.draw2d.ColorConstants.black);
			return conn;*/
		}
		if (node instanceof ConditionalNode){
			//Triangle e = new Triangle();
			/*Polygon e = new Polygon();
		    for (int i = 0; i < 5; i++) {
		      e.addPoint(new Point((int) (100 + 50 * Math.cos(i * 2 * Math.PI/6)),
		          (int) (100 + 50 * Math.sin(i * 2 * Math.PI/6))));
		     }*/
			Hexagon e = new Hexagon();
			//e.setSize(50, 50);
			e.setLayoutManager(new BorderLayout());
			e.setBackgroundColor(COND_NODE_COLOR);
			l = new Label(((ConditionalNode)node).label);
			e.add(l, BorderLayout.CENTER);
			e.setSize(l.getPreferredSize().width+l.getPreferredSize().width*1/2, l.getPreferredSize().height+5);
			return e;
		}
		if (node instanceof CaseNode) {
			Hexagon e = new Hexagon();
			//e.setSize(50, 50);
			e.setLayoutManager(new BorderLayout());
			e.setBackgroundColor(COND_NODE_COLOR);
			l = new Label(((CaseNode)node).label);
			e.add(l, BorderLayout.CENTER);
			e.setSize(l.getPreferredSize().width+l.getPreferredSize().width*1/2, l.getPreferredSize().height+5);
			return e;
		}
		if (node instanceof PointNode){
			Ellipse e = new Ellipse();
			e.setSize(-0, 0);
			e.setBackgroundColor(POINT_NODE_COLOR);
			//e.setLayoutManager(new BorderLayout());
			//e.add(new Label(((Node)node).label), BorderLayout.CENTER);
			return e;
		}
		if (node instanceof ForallNode){
			/*LabeledFigure e = new LabeledFigure();
			e.setLayoutManager(new BorderLayout());
			//l = new Label(((ForallNode)node).label);
			//l.setText("F\n\t" + l.getText() + "\n");
			l = new Label(((ForallNode)node).label);
			e.add(l, BorderLayout.CENTER);
			e.setSize(l.getPreferredSize().width+l.getPreferredSize().width*1/2, l.getPreferredSize().height+5);
			//e.setSize(120, 50);
			return e;*/
			Hexagon e = new Hexagon();
			//e.setSize(50, 50);
			e.setLayoutManager(new BorderLayout());
			e.setBackgroundColor(FORALL_NODE_COLOR);
			l = new Label(((ForallNode)node).label);
			e.add(l, BorderLayout.CENTER);
			e.setSize(l.getPreferredSize().width+l.getPreferredSize().width*1/2, l.getPreferredSize().height+5);
			return e;
		}
		if (node instanceof StateNode){
			Ellipse e = new Ellipse();
			//e.setBackgroundColor();
			e.setLayoutManager(new BorderLayout());
			e.setBackgroundColor(STATE_NODE_COLOR);
			Label label = new Label(((StateNode)node).label);
			e.add(label, BorderLayout.CENTER);
			//e.setSize(100, 20);
			e.setSize(label.getPreferredSize().width+label.getPreferredSize().width*1/2, label.getPreferredSize().height+5);
			return e;
		}
		if(node instanceof LabeledPointNode) {
			RoundedRectangle e = new RoundedRectangle();
			//e.setSize(80, 20);
			e.setLayoutManager(new BorderLayout());
			e.setBackgroundColor(LABELED_POINT_NODE_COLOR);
			e.setForegroundColor(org.eclipse.draw2d.ColorConstants.white);
			l = new Label(((LabeledNode)node).label);
			l.setForegroundColor(org.eclipse.draw2d.ColorConstants.black);
			e.add(l, BorderLayout.CENTER);
			e.setSize(l.getPreferredSize().width+4, l.getPreferredSize().height+5);
			return e;
		}
		if (node instanceof Node) {			
			RoundedRectangle e = new RoundedRectangle();
			//e.setSize(80, 20);
			e.setLayoutManager(new BorderLayout());
			e.setBackgroundColor(NODE_COLOR);
			l = new Label(((LabeledNode)node).label);
			e.add(l, BorderLayout.CENTER);
			e.setSize(l.getPreferredSize().width+4, l.getPreferredSize().height+5);
			return e;
		} else{
			throw new RuntimeException();
		}
	}

}

class Hexagon extends RectangleFigure {
	private int[] getVertexes(Rectangle rect) {
		int[] vertexes = new int[12];
		vertexes[0] = rect.x;
		vertexes[1] = rect.y + rect.height/2;
		vertexes[2] = rect.x + rect.width/4;
		vertexes[3] = rect.y + rect.height;
		vertexes[4] = rect.x + rect.width*3/4;
		vertexes[5] = rect.y + rect.height;
		vertexes[6] = rect.x + rect.width;
		vertexes[7] = rect.y + rect.height/2;
		vertexes[8] = rect.x + rect.width*3/4;
		vertexes[9] = rect.y;
		vertexes[10] = rect.x + rect.width/4;
		vertexes[11] = rect.y;
		return vertexes;
	}


	@Override
	protected void fillShape(Graphics graphics) {
		Rectangle rect = getBounds();
		graphics.fillPolygon(getVertexes(rect));
	}

	@Override
	protected void outlineShape(Graphics graphics) {
		float lineInset = Math.max(1.0f, getLineWidthFloat()) / 2.0f;
		int inset1 = (int) Math.floor(lineInset);
		int inset2 = (int) Math.ceil(lineInset);
		Rectangle r = Rectangle.SINGLETON.setBounds(getBounds());
		r.x += inset1;
		r.y += inset1;
		r.width -= inset1 + inset2;
		r.height -= inset1 + inset2;
		graphics.drawPolygon(getVertexes(r));
	}
}

class LabeledFigure extends RectangleFigure {
	private int[] getVertexes(Rectangle rect) {
		int[] vertexes = new int[12];
		vertexes[0] = rect.x;
		vertexes[1] = rect.y + rect.height/2;
		vertexes[2] = rect.x + rect.width/4;
		vertexes[3] = rect.y + rect.height;
		vertexes[4] = rect.x + rect.width*3/4;
		vertexes[5] = rect.y + rect.height;
		vertexes[6] = rect.x + rect.width;
		vertexes[7] = rect.y + rect.height/2;
		vertexes[8] = rect.x + rect.width*3/4;
		vertexes[9] = rect.y;
		vertexes[10] = rect.x + rect.width/4;
		vertexes[11] = rect.y;
		return vertexes;
	}


	@Override
	protected void fillShape(Graphics graphics) {
		Rectangle rect = getBounds();
		graphics.fillPolygon(getVertexes(rect));
	}

	@Override
	protected void outlineShape(Graphics graphics) {
		float lineInset = Math.max(1.0f, getLineWidthFloat()) / 2.0f;
		int inset1 = (int) Math.floor(lineInset);
		int inset2 = (int) Math.ceil(lineInset);
		Rectangle r = Rectangle.SINGLETON.setBounds(getBounds());
		r.x += inset1+20;
		r.y += inset1;
		r.width -= inset1 + inset2;
		r.height -= inset1 + inset2;
		graphics.drawPolygon(getVertexes(r));
//		Rectangle r2 = Rectangle.SINGLETON.setBounds(getBounds());
//		r2.x += inset1;
//		r2.y += inset1;
//		r2.width -= inset1 + inset2;
//		r2.height -= inset1 + inset2;
//		graphics.setBackgroundColor(new Color(255,0,0));
//		graphics.drawPolygon(getVertexes(r2));
	}
}