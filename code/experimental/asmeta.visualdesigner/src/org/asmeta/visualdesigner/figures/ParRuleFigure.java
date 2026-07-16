package org.asmeta.visualdesigner.figures;

import org.eclipse.draw2d.Graphics;

public class ParRuleFigure extends RuleFigure {

    public static final int PREFERRED_WIDTH = 35;
    public static final int PREFERRED_HEIGHT = 22;

    public ParRuleFigure(String text) {
        super("par");
    }

    public ParRuleFigure() {
        super("par");
    }

    @Override
    protected void paintFigure(Graphics graphics) {
        // Intentionally empty.
        // The PAR rule is represented only by the label "par",
        // following the visual notation from the paper ASM visualization paper
    }

    @Override
    public void setRuleText(String text) {
        super.setRuleText("par");
    }

    @Override
    public void setRuleName(String name) {
        setRuleText("par");
    }
}