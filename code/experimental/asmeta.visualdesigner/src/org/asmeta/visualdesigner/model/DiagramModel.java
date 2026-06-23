package org.asmeta.visualdesigner.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

public class DiagramModel {

    public static final String PROPERTY_CHILDREN = "DiagramModel.Children";
    public static final String PROPERTY_CONNECTIONS = "DiagramModel.Connections";

    private final PropertyChangeSupport listeners = new PropertyChangeSupport(this);

    private List<RuleNode> rules = new ArrayList<>();
    private List<Transition> transitions = new ArrayList<>();
    private StartNode startNode = new StartNode("Starting point", 80, 100);

    public List<RuleNode> getRules() {
        return rules;
    }

    public List<Transition> getTransitions() {
        return transitions;
    }
    
    public StartNode getStartNode() {
        return startNode;
    }

    public List<DiagramNode> getNodes() {
        List<DiagramNode> nodes = new ArrayList<>();
        nodes.add(startNode);
        nodes.addAll(rules);
        return nodes;
    }

    public void addRule(RuleNode rule) {
        rules.add(rule);
        listeners.firePropertyChange(PROPERTY_CHILDREN, null, rule);
    }
    
    public void addRule(int index, RuleNode rule) {
        if (index < 0 || index > rules.size()) {
            rules.add(rule);
        } else {
            rules.add(index, rule);
        }

        listeners.firePropertyChange(PROPERTY_CHILDREN, null, rule);
    }

    public void removeRule(RuleNode rule) {
        rules.remove(rule);
        listeners.firePropertyChange(PROPERTY_CHILDREN, rule, null);
    }

    public void addTransition(Transition transition) {
        if (transition == null || transitions.contains(transition)) {
            return;
        }

        transitions.add(transition);

        if (transition.getSource() != null) {
            transition.getSource().addSourceTransition(transition);
        }

        if (transition.getTarget() != null) {
            transition.getTarget().addTargetTransition(transition);
        }

        listeners.firePropertyChange(PROPERTY_CONNECTIONS, null, transition);
    }

    public void addTransition(int index, Transition transition) {
        if (transition == null || transitions.contains(transition)) {
            return;
        }

        if (index < 0 || index > transitions.size()) {
            transitions.add(transition);
        } else {
            transitions.add(index, transition);
        }

        if (transition.getSource() != null) {
            transition.getSource().addSourceTransition(transition);
        }

        if (transition.getTarget() != null) {
            transition.getTarget().addTargetTransition(transition);
        }

        listeners.firePropertyChange(PROPERTY_CONNECTIONS, null, transition);
    }
    
    public void removeTransition(Transition transition) {
        if (transition == null || !transitions.remove(transition)) {
            return;
        }

        if (transition.getSource() != null) {
            transition.getSource().removeSourceTransition(transition);
        }

        if (transition.getTarget() != null) {
            transition.getTarget().removeTargetTransition(transition);
        }

        listeners.firePropertyChange(PROPERTY_CONNECTIONS, transition, null);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        listeners.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        listeners.removePropertyChangeListener(listener);
    }
}