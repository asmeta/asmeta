package org.asmeta.visualdesigner.figures;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.SWT;

public class UnknownRuleFigure extends RuleFigure {

    public UnknownRuleFigure(String text) {
        super(text);
    }

    @Override
    protected void paintFigure(Graphics graphics) {
        Rectangle r = getBounds().getCopy().shrink(1, 1);

        graphics.setBackgroundColor(ColorConstants.white);
        graphics.setForegroundColor(ColorConstants.black);

        graphics.fillRectangle(r);

        graphics.setLineStyle(SWT.LINE_DASH);
        graphics.drawRectangle(r);
        graphics.setLineStyle(SWT.LINE_SOLID);
    }
}