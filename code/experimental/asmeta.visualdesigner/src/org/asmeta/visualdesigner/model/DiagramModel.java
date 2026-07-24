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
    
    private String asmetaCode = "";
    
    private List<DomainSignature> domains = new ArrayList<>();
    private List<String> customDomainTypes = new ArrayList<>();
    private List<FunctionSignature> functions = new ArrayList<>();

    public List<DomainSignature> getDomains() {
        return domains;
    }
    
    public List<String> getCustomDomainTypes() {
        return customDomainTypes;
    }

    public List<FunctionSignature> getFunctions() {
        return functions;
    }
    
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
    
    public List<Transition> getOutgoingTransitions(DiagramNode source) {
        List<Transition> outgoing = new ArrayList<>();

        for (Transition transition : transitions) {
            if (transition.getSource() == source) {
                outgoing.add(transition);
            }
        }

        return outgoing;
    }
    
    public boolean canAddTransitionFrom(DiagramNode source) {
        boolean canAdd = false;

        if (source != null) {
            int outgoingCount = getOutgoingTransitions(source).size();

            if (source instanceof RuleNode) {
                RuleNode rule = (RuleNode) source;

                if (rule.getType() == RuleType.CONDITIONAL || rule.getType() == RuleType.CHOOSE) {
                    canAdd = outgoingCount < 2;
                } else if (rule.getType() == RuleType.PAR) {
                    canAdd = true;
                } else {
                    canAdd = outgoingCount < 1;
                }
            } else {
                canAdd = outgoingCount < 1;
            }
        }

        return canAdd;
    }
    
    /*public String getNextConditionalLabel(RuleNode source) {
        if (source == null || source.getType() != RuleType.CONDITIONAL) {
            return null;
        }

        boolean hasTrue = false;
        boolean hasFalse = false;

        for (Transition transition : getOutgoingTransitions(source)) {
            if ("true".equals(transition.getLabel())) {
                hasTrue = true;
            }
            if ("false".equals(transition.getLabel())) {
                hasFalse = true;
            }
        }

        if (!hasTrue) {
            return "true";
        }

        if (!hasFalse) {
            return "false";
        }

        return null;
    }*/
    
        public String getNextConditionalLabel(RuleNode source) {
        String label = null;

        if (source != null && source.getType() == RuleType.CONDITIONAL) {
            label = getNextTransitionLabel(source);
        }

        return label;
    }

        public String getNextTransitionLabel(DiagramNode source) {
            String label = null;

            if (source instanceof RuleNode) {
                RuleNode rule = (RuleNode) source;

                if (rule.getType() == RuleType.CONDITIONAL) {
                    if (!hasOutgoingTransitionWithLabel(source, "true")) {
                        label = "true";
                    } else if (!hasOutgoingTransitionWithLabel(source, "false")) {
                        label = "false";
                    }
                } else if (rule.getType() == RuleType.CHOOSE) {
                    if (!hasOutgoingTransitionWithLabel(source, "do")) {
                        label = "do";
                    } else if (!hasOutgoingTransitionWithLabel(source, "ifnone")) {
                        label = "ifnone";
                    }
                }
            }

            return label;
        }

    private boolean hasOutgoingTransitionWithLabel(DiagramNode source, String label) {
        boolean found = false;

        for (Transition transition : getOutgoingTransitions(source)) {
            if (label.equals(transition.getLabel())) {
                found = true;
            }
        }

        return found;
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

   /* public void addTransition(Transition transition) {
        

        transitions.add(transition);

        if (transition.getSource() != null) {
            transition.getSource().addSourceTransition(transition);
        }

        if (transition.getTarget() != null) {
            transition.getTarget().addTargetTransition(transition);
        }

        listeners.firePropertyChange(PROPERTY_CONNECTIONS, null, transition);
    }*/
    
   
    
    
    /*public String getNextTransitionLabel(DiagramNode source) {
        if (!(source instanceof RuleNode)) {
            return null;
        }

        RuleNode rule = (RuleNode) source;

        if (rule.getType() != RuleType.CONDITIONAL) {
            return null;
        }

        boolean hasTrue = false;
        boolean hasFalse = false;

        for (Transition transition : getOutgoingTransitions(source)) {
            if ("true".equals(transition.getLabel())) {
                hasTrue = true;
            }

            if ("false".equals(transition.getLabel())) {
                hasFalse = true;
            }
        }

        if (!hasTrue) {
            return "true";
        }

        if (!hasFalse) {
            return "false";
        }

        return null;
    }*/
    

    /*public void addTransition(int index, Transition transition) {
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
    }*/
    public void addTransition(Transition transition) {
        if (transition != null && !transitions.contains(transition)) {

            if (canAddTransitionFrom(transition.getSource())) {
                transitions.add(transition);

                if (transition.getSource() != null) {
                    transition.getSource().addSourceTransition(transition);
                }

                if (transition.getTarget() != null) {
                    transition.getTarget().addTargetTransition(transition);
                }

                listeners.firePropertyChange(PROPERTY_CONNECTIONS, null, transition);
            }
        }
    }
    
    public void addTransitionAt(int index, Transition transition) {
        if (transition != null && !transitions.contains(transition)) {

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
    }
    
        
    
    
    public void removeTransition(Transition transition) {
        if (transition != null) {
            //boolean removed = transitions.remove(transition);

            if (transitions.remove(transition)) {
                if (transition.getSource() != null) {
                    transition.getSource().removeSourceTransition(transition);
                }

                if (transition.getTarget() != null) {
                    transition.getTarget().removeTargetTransition(transition);
                }

                listeners.firePropertyChange(PROPERTY_CONNECTIONS, transition, null);
            }
        }
    }
    
    

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        listeners.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        listeners.removePropertyChangeListener(listener);
    }
    
    public String getAsmetaCode() {
        return asmetaCode;
    }

    public void setAsmetaCode(String asmetaCode) {
        this.asmetaCode = asmetaCode;
    }
}