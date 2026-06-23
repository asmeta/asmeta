package org.asmeta.visualdesigner.figures;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.geometry.Rectangle;

public class ParRuleFigure extends RuleFigure {

    public ParRuleFigure(String text) {
        super(text);
    }

    @Override
    protected void paintFigure(Graphics graphics) {
        Rectangle r = getBounds().getCopy().shrink(1, 1);

        graphics.setForegroundColor(ColorConstants.black);
        graphics.setBackgroundColor(ColorConstants.white);

        int offset = 6;

        Rectangle front = new Rectangle(
            r.x,
            r.y + 2 * offset,
            r.width - 2 * offset,
            r.height - 2 * offset
        );

        Rectangle middle = new Rectangle(
            front.x + offset,
            front.y - offset,
            front.width,
            front.height
        );

        Rectangle back = new Rectangle(
            front.x + 2 * offset,
            front.y - 2 * offset,
            front.width,
            front.height
        );

        graphics.fillRectangle(back);
        graphics.drawRectangle(back);

        graphics.fillRectangle(middle);
        graphics.drawRectangle(middle);

        graphics.fillRectangle(front);
        graphics.drawRectangle(front);
    }
}