package org.asmeta.visualdesigner.figures;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.Rectangle;

public class ForAllRuleFigure extends RuleFigure {

    public ForAllRuleFigure(String text) {
        super(text);
    }

    @Override
    protected void paintFigure(Graphics graphics) {
        Rectangle bounds = getBounds().getCopy().shrink(1, 1);

        PointList points = new PointList();
        points.addPoint(
                bounds.x + bounds.width / 2,
                bounds.y
        );
        points.addPoint(
                bounds.x + bounds.width,
                bounds.y + bounds.height / 2
        );
        points.addPoint(
                bounds.x + bounds.width / 2,
                bounds.y + bounds.height
        );
        points.addPoint(
                bounds.x,
                bounds.y + bounds.height / 2
        );

        graphics.setBackgroundColor(ColorConstants.lightGray);
        graphics.setForegroundColor(ColorConstants.black);

        graphics.fillPolygon(points);
        graphics.drawPolygon(points);
    }

    @Override
    public void setRuleText(String text) {
        label.setText("FORALL");
        revalidate();
        repaint();
    }
}