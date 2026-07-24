package org.asmeta.visualdesigner.figures;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.geometry.Rectangle;

public class ChooseRuleFigure extends RuleFigure {

    public ChooseRuleFigure(String text) {
        super(text);
    }

    @Override
    protected void paintFigure(Graphics graphics) {
        Rectangle bounds = getBounds().getCopy().shrink(1, 1);

        graphics.setBackgroundColor(ColorConstants.tooltipBackground);
        graphics.setForegroundColor(ColorConstants.black);

        graphics.fillRoundRectangle(bounds, 20, 20);
        graphics.drawRoundRectangle(bounds, 20, 20);
    }
}