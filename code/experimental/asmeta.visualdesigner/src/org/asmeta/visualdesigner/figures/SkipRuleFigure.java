package org.asmeta.visualdesigner.figures;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.XYLayout;
import org.eclipse.draw2d.geometry.Rectangle;

public class SkipRuleFigure extends RuleFigure {

    public static final int CIRCLE_SIZE = 20;
    public static final int PREFERRED_WIDTH = 55;
    public static final int PREFERRED_HEIGHT = 20;

    public SkipRuleFigure() {
        super("skip");

        setLayoutManager(new XYLayout());

        label.setForegroundColor(ColorConstants.black);

        getLayoutManager().setConstraint(
                label,
                new Rectangle(
                        CIRCLE_SIZE + 5,
                        0,
                        PREFERRED_WIDTH - CIRCLE_SIZE - 5,
                        PREFERRED_HEIGHT
                )
        );
    }

    @Override
    protected void paintFigure(Graphics graphics) {
        Rectangle circle = new Rectangle(
                getBounds().x,
                getBounds().y,
                CIRCLE_SIZE - 1,
                CIRCLE_SIZE - 1
        );

        graphics.setBackgroundColor(ColorConstants.white);
        graphics.setForegroundColor(ColorConstants.black);

        graphics.fillOval(circle);
        graphics.drawOval(circle);
    }

    @Override
    public void setRuleText(String text) {
        label.setText("skip");
        revalidate();
        repaint();
    }
}