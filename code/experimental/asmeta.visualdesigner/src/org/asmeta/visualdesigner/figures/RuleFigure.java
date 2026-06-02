package org.asmeta.visualdesigner.figures;

import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.LineBorder;
import org.eclipse.draw2d.RectangleFigure;
import org.eclipse.draw2d.ToolbarLayout;

public class RuleFigure extends RectangleFigure {

    private String name;

    public RuleFigure(String name) {
        this.name = name;

        setBorder(new LineBorder(1));

        ToolbarLayout layout = new ToolbarLayout();
        layout.setStretchMinorAxis(true);
        layout.setMinorAlignment(ToolbarLayout.ALIGN_CENTER);
        layout.setSpacing(5);

        setLayoutManager(layout);

        Label label = new Label(name);
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
}