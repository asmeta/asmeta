package org.asmeta.visualdesigner.figures;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.geometry.Rectangle;

public class UpdateRuleFigure extends RuleFigure {

    public UpdateRuleFigure(String text) {
        super(text);
    }

    @Override
    protected void paintFigure(Graphics graphics) {
        Rectangle bounds = getBounds().getCopy().shrink(1, 1);

        graphics.setBackgroundColor(ColorConstants.white);
        graphics.setForegroundColor(ColorConstants.black);

        graphics.fillRoundRectangle(bounds, 10, 10);
        graphics.drawRoundRectangle(bounds, 10, 10);
    }
}