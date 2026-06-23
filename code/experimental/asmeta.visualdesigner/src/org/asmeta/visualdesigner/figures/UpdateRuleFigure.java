package org.asmeta.visualdesigner.figures;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.Rectangle;

public class UpdateRuleFigure extends RuleFigure {

    public UpdateRuleFigure(String text) {
        super(text);
    }

    @Override
    protected void paintFigure(Graphics graphics) {
        Rectangle r = getBounds().getCopy().shrink(1, 1);

        int skew = Math.max(12, r.width / 8);

        PointList points = new PointList();
        points.addPoint(r.x + skew, r.y);
        points.addPoint(r.x + r.width, r.y);
        points.addPoint(r.x + r.width - skew, r.y + r.height);
        points.addPoint(r.x, r.y + r.height);

        graphics.setBackgroundColor(ColorConstants.white);
        graphics.setForegroundColor(ColorConstants.black);

        graphics.fillPolygon(points);
        graphics.drawPolygon(points);
    }
}