package org.asmeta.visualdesigner.figures;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.geometry.Rectangle;

public class ForAllRuleFigure extends RuleFigure {

    public ForAllRuleFigure(String text) {
        super(text);
    }

    @Override
    protected void paintFigure(Graphics graphics) {
        Rectangle outer = getBounds().getCopy().shrink(1, 1);
        Rectangle inner = outer.getCopy().shrink(4, 4);

        graphics.setBackgroundColor(ColorConstants.white);
        graphics.setForegroundColor(ColorConstants.black);

        graphics.fillRectangle(outer);
        graphics.drawRectangle(outer);
        graphics.drawRectangle(inner);
    }
}