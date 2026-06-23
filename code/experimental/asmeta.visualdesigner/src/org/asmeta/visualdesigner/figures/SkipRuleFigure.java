package org.asmeta.visualdesigner.figures;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.geometry.Rectangle;

public class SkipRuleFigure extends RuleFigure {

    public SkipRuleFigure() {
        super("");
    }

    @Override
    protected void paintFigure(Graphics graphics) {
        Rectangle r = getBounds().getCopy().shrink(2, 2);

        graphics.setForegroundColor(ColorConstants.black);
        graphics.drawOval(r);
    }
}