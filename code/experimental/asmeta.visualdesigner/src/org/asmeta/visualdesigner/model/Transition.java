package org.asmeta.visualdesigner.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class Transition {

    public static final String PROPERTY_LABEL = "Transition.Label";

    private final PropertyChangeSupport listeners = new PropertyChangeSupport(this);

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

    public void setLabel(String label) {
        String oldValue = this.label;
        this.label = label;

        listeners.firePropertyChange(PROPERTY_LABEL, oldValue, label);
    }

    public boolean hasLabel() {
        return label != null && !label.isBlank();
    }

    public boolean isSwitchBranch() {
        return source instanceof RuleNode && ((RuleNode) source).getType() == RuleType.SWITCH;
    }

    public String getDisplayLabel() {
        return isSwitchBranch() && !hasLabel() ? "otherwise" : label;
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        listeners.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        listeners.removePropertyChangeListener(listener);
    }
}