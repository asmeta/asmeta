package org.asmeta.visualdesigner.figures;

import org.eclipse.draw2d.BorderLayout;
import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.geometry.Rectangle;

public class LetRuleFigure extends RuleFigure {

    private static final int HEADER_HEIGHT = 22;

    public LetRuleFigure(String text) {
        super(text);

        Label header = new Label("LET");
        add(header, BorderLayout.TOP);
    }

    @Override
    protected void paintFigure(Graphics graphics) {
        Rectangle bounds = getBounds().getCopy().shrink(1, 1);

        graphics.setBackgroundColor(ColorConstants.white);
        graphics.setForegroundColor(ColorConstants.black);

        graphics.fillRectangle(bounds);
        graphics.drawRectangle(bounds);

        Rectangle headerBounds = new Rectangle(bounds.x, bounds.y, bounds.width,HEADER_HEIGHT);

        graphics.setBackgroundColor(ColorConstants.lightGray);
        graphics.fillRectangle(headerBounds);

        graphics.drawLine(bounds.x, bounds.y + HEADER_HEIGHT, bounds.x + bounds.width, bounds.y + HEADER_HEIGHT);

        graphics.drawRectangle(bounds);
    }
}