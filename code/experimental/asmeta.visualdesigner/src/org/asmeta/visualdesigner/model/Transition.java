package org.asmeta.visualdesigner.model;

public class Transition {

    private DiagramNode source;
    private DiagramNode target;

    public Transition() {
    }

    public Transition(DiagramNode source, DiagramNode target) {
        this.source = source;
        this.target = target;
    }

    public DiagramNode getSource() {
        return source;
    }

    public void setSource(DiagramNode source) {
        this.source = source;
    }

    public DiagramNode getTarget() {
        return target;
    }

    public void setTarget(DiagramNode target) {
        this.target = target;
    }
}