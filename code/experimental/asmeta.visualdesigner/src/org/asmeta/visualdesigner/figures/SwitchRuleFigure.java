package org.asmeta.visualdesigner.figures;

import org.eclipse.draw2d.BorderLayout;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.FlowLayout;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.jface.resource.JFaceResources;

public class SwitchRuleFigure extends ConditionalRuleFigure {

    public SwitchRuleFigure(String expression) {
        super(expression);

        remove(label);

        Figure content = new Figure();

        FlowLayout layout = new FlowLayout() {

            @Override
            public void layout(IFigure parent) {
                super.layout(parent);

                Rectangle textBounds = null;

                for (Object childObject : parent.getChildren()) {
                    IFigure child = (IFigure) childObject;

                    if (textBounds == null) {
                        textBounds = child.getBounds().getCopy();
                    } else {
                        textBounds.union(child.getBounds());
                    }
                }

                if (textBounds != null) {
                    Rectangle area = parent.getClientArea();

                    int offsetY = area.y + (area.height - textBounds.height) / 2 - textBounds.y;

                    for (Object childObject : parent.getChildren()) {
                        IFigure child = (IFigure) childObject;

                        Rectangle bounds = child.getBounds().getCopy();
                        bounds.translate(0, offsetY);

                        child.setBounds(bounds);
                    }
                }
            }
        };

        layout.setMajorAlignment(FlowLayout.ALIGN_CENTER);
        layout.setMinorAlignment(FlowLayout.ALIGN_CENTER);
        layout.setMajorSpacing(5);

        content.setLayoutManager(layout);

        Label keywordLabel = new Label("switch");
        keywordLabel.setFont(JFaceResources.getFontRegistry().getBold(JFaceResources.DEFAULT_FONT));

        content.add(keywordLabel);
        content.add(label);

        add(content, BorderLayout.CENTER);
    }
}