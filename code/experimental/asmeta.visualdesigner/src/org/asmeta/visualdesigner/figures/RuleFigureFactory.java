/*package org.asmeta.visualdesigner.figures;

import org.asmeta.visualdesigner.model.RuleNode;
import org.asmeta.visualdesigner.model.RuleType;
import org.eclipse.draw2d.Ellipse;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.PolygonShape;
import org.eclipse.draw2d.RectangleFigure;
import org.eclipse.draw2d.RoundedRectangle;
import org.eclipse.draw2d.XYLayout;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.Rectangle;

public class RuleFigureFactory {

    public static IFigure createFigure(RuleNode node) {
        RuleType type = node.getType();

        if (type == null) {
            type = RuleType.UNKNOWN;
        }


        
        switch (node.getType()) {
        case CONDITIONAL:
            return createConditionalFigure(node);

        case PAR:
        case SEQ:
        case CHOOSE:
        case CALL:
        case UNKNOWN:
        default:
            return createRectangleFigure(node);
    }
    }

    private static IFigure createRectangleFigure(RuleNode node) {
        Figure container = new Figure();
        container.setLayoutManager(new XYLayout());

        RoundedRectangle rectangle = new RoundedRectangle();
        rectangle.setCornerDimensions(new org.eclipse.draw2d.geometry.Dimension(12, 12));

        Label label = new Label(node.getName());

        int width = node.getWidth();
        int height = node.getHeight();

        container.add(rectangle, new Rectangle(0, 0, width, height));
        container.add(label, new Rectangle(5, 5, width - 10, height - 10));

        container.setBounds(new Rectangle(node.getX(), node.getY(), width, height));

        return container;
    }

    private static IFigure createConditionalFigure(RuleNode node) {
        Figure container = new Figure();
        container.setLayoutManager(new XYLayout());

        int width = node.getWidth();
        int height = node.getHeight();

        PolygonShape diamond = new PolygonShape();

        PointList points = new PointList();
        points.addPoint(width / 2, 0);
        points.addPoint(width, height / 2);
        points.addPoint(width / 2, height);
        points.addPoint(0, height / 2);

        diamond.setPoints(points);

        Label label = new Label(node.getName());

        container.add(diamond, new Rectangle(0, 0, width, height));
        container.add(label, new Rectangle(10, height / 2 - 10, width - 20, 20));

        container.setBounds(new Rectangle(node.getX(), node.getY(), width, height));

        return container;
    }

    private static IFigure createChooseFigure(RuleNode node) {
        Figure container = new Figure();
        container.setLayoutManager(new XYLayout());

        int width = node.getWidth();
        int height = node.getHeight();

        Ellipse ellipse = new Ellipse();

        Label label = new Label(node.getName());

        container.add(ellipse, new Rectangle(0, 0, width, height));
        container.add(label, new Rectangle(10, height / 2 - 10, width - 20, 20));

        container.setBounds(new Rectangle(node.getX(), node.getY(), width, height));

        return container;
    }
}*/

package org.asmeta.visualdesigner.figures;

import org.asmeta.visualdesigner.model.RuleNode;
import org.asmeta.visualdesigner.model.RuleType;

public class RuleFigureFactory {

	public static RuleFigure createFigure(RuleNode node) {
	    RuleType type = node.getType();

	    if (type == null) {
	        type = RuleType.UNKNOWN;
	    }

	    String text = node.getDisplayText();

	    switch (type) {
	        case CONDITIONAL:
	            return new ConditionalRuleFigure(text);

	        case SEQ:
	            return new SeqRuleFigure(text);

	        case PAR:
	            return new ParRuleFigure(text);

	        case CHOOSE:
	            return new ChooseRuleFigure(text);

	        case CALL:
	            return new RuleFigure(text);

	        case UPDATE:
	            return new UpdateRuleFigure(text);

	        case FORALL:
	            return new ForAllRuleFigure(text);

	        case SKIP:
	            return new SkipRuleFigure();

	        case UNKNOWN:
	        default:
	            return new UnknownRuleFigure(text);
	    }
	}
}