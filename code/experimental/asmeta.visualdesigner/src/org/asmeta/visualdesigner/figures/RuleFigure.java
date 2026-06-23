/*package org.asmeta.visualdesigner.figures;

import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.LineBorder;
import org.eclipse.draw2d.RectangleFigure;
import org.eclipse.draw2d.ToolbarLayout;

public class RuleFigure extends RectangleFigure {

    private String name;
    Label label;
    
    public RuleFigure(String name) {
        this.name = name;

        setBorder(new LineBorder(1));

        ToolbarLayout layout = new ToolbarLayout();
        layout.setStretchMinorAxis(true);
        layout.setMinorAlignment(ToolbarLayout.ALIGN_CENTER);
        layout.setSpacing(5);

        setLayoutManager(layout);

        label = new Label(name);
        add(label);
    }

    public void setSelected(boolean selected) {
        if (selected) {
            setBorder(new LineBorder(3));
        } else {
            setBorder(new LineBorder(1));
        }
        repaint();
    }

    public String getName() {
        return name;
    }
    
    public void setRuleName(String name) {
        label.setText(name);
        revalidate();
        repaint();
    }
}*/

package org.asmeta.visualdesigner.figures;

import org.eclipse.draw2d.BorderLayout;
import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.geometry.Rectangle;

public class RuleFigure extends Figure {

    protected Label label;

    public RuleFigure(String text) {
        setLayoutManager(new BorderLayout());
        setOpaque(false);

        label = new Label(text);
        add(label, BorderLayout.CENTER);
    }

    @Override
    protected void paintFigure(Graphics graphics) {
        super.paintFigure(graphics);

        Rectangle r = getBounds().getCopy().shrink(1, 1);

        graphics.setBackgroundColor(ColorConstants.white);
        graphics.setForegroundColor(ColorConstants.black);

        graphics.fillRectangle(r);
        graphics.drawRectangle(r);
    }
    
    public void setRuleText(String text) {
        label.setText(text != null ? text : "");
        revalidate();
        repaint();
    }

    public void setRuleName(String name) {
        setRuleText(name);
    }
}