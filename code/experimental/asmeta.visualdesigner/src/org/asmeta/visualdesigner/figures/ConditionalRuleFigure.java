package org.asmeta.visualdesigner.figures;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.Rectangle;

public class ConditionalRuleFigure extends RuleFigure {

    public ConditionalRuleFigure(String text) {
        super(text);
    }

    @Override
    protected void paintFigure(Graphics graphics) {
        Rectangle r = getBounds().getCopy().shrink(1, 1);

        int cut = Math.max(15, r.width / 6);

        PointList points = new PointList();
        points.addPoint(r.x + cut, r.y);
        points.addPoint(r.x + r.width - cut, r.y);
        points.addPoint(r.x + r.width, r.y + r.height / 2);
        points.addPoint(r.x + r.width - cut, r.y + r.height);
        points.addPoint(r.x + cut, r.y + r.height);
        points.addPoint(r.x, r.y + r.height / 2);

        graphics.setBackgroundColor(ColorConstants.white);
        graphics.setForegroundColor(ColorConstants.black);

        graphics.fillPolygon(points);
        graphics.drawPolygon(points);
    }
}