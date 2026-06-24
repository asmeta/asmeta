package org.asmeta.visualdesigner.model;

public class Transition {

    private DiagramNode source;
    private DiagramNode target;
    private String label;

    public Transition() {
        this(null, null, null);
    }

    public Transition(DiagramNode source, DiagramNode target) {
        this(source, target, null);
    }

    public Transition(DiagramNode source, DiagramNode target, String label) {
        this.source = source;
        this.target = target;
        this.label = label;
    }

    public DiagramNode getSource() {
        return source;
    }

    public DiagramNode getTarget() {
        return target;
    }

    public String getLabel() {
        return label;
    }

    public boolean hasLabel() {
        return label != null && !label.isBlank();
    }
}