package org.asmeta.visualdesigner.figures;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.SWT;

public class SeqRuleFigure extends RuleFigure {

    public SeqRuleFigure(String text) {
        super(text);
    }

    @Override
    protected void paintFigure(Graphics graphics) {
        Rectangle r = getBounds().getCopy().shrink(1, 1);

        graphics.setBackgroundColor(ColorConstants.white);
        graphics.setForegroundColor(ColorConstants.black);

        graphics.fillRectangle(r);

        graphics.setLineStyle(SWT.LINE_DOT);
        graphics.drawRectangle(r);
        graphics.setLineStyle(SWT.LINE_SOLID);
    }
}