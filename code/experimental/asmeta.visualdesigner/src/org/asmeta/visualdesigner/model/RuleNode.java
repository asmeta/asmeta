package org.asmeta.visualdesigner.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

public class RuleNode implements DiagramNode {
	
    private String name;
    private int x;
    private int y;
    private int width;
    private int height;

    private RuleType type;
    
    public static final String PROPERTY_LAYOUT = DiagramNode.PROPERTY_LAYOUT;
    public static final String PROPERTY_SOURCE_CONNECTIONS = DiagramNode.PROPERTY_SOURCE_CONNECTIONS;
    public static final String PROPERTY_TARGET_CONNECTIONS = DiagramNode.PROPERTY_TARGET_CONNECTIONS;
    public static final String PROPERTY_RULE_DATA = "RuleNode.Data";
    
    private final List<Transition> sourceTransitions = new ArrayList<>();
    private final List<Transition> targetTransitions = new ArrayList<>();

    private final PropertyChangeSupport listeners = new PropertyChangeSupport(this);
    
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        listeners.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        listeners.removePropertyChangeListener(listener);
    }
    
    private String condition = "";
    private String calledRuleName = "";
    private String parameters = "";

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        String newValue = condition != null ? condition : "";

        if (!this.condition.equals(newValue)) {
            this.condition = newValue;
            listeners.firePropertyChange(PROPERTY_RULE_DATA, null, null);
        }
    }

    public String getCalledRuleName() {
        return calledRuleName;
    }

    public void setCalledRuleName(String calledRuleName) {
        String newValue = calledRuleName != null ? calledRuleName : "";

        if (!this.calledRuleName.equals(newValue)) {
            this.calledRuleName = newValue;
            listeners.firePropertyChange(PROPERTY_RULE_DATA, null, null);
        }
    }

    public String getParameters() {
        return parameters;
    }

    public void setParameters(String parameters) {
        String newValue = parameters != null ? parameters : "";

        if (!this.parameters.equals(newValue)) {
            this.parameters = newValue;
            listeners.firePropertyChange(PROPERTY_RULE_DATA, null, null);
        }
    }
    
    public void setLayout(int x, int y, int width, int height) {
        int oldX = this.x;
        int oldY = this.y;
        int oldWidth = this.width;
        int oldHeight = this.height;

        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        boolean changed =
            oldX != x ||
            oldY != y ||
            oldWidth != width ||
            oldHeight != height;

        if (changed) {
            listeners.firePropertyChange(PROPERTY_LAYOUT, null, null);
        }
    }
    
    
    public RuleNode(String name, int x, int y, int width, int height) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.type = RuleType.UNKNOWN;
    }

    public RuleNode(String name, int x, int y, int width, int height, RuleType type) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.type = type != null ? type : RuleType.UNKNOWN;
    }

    public String getName() {
        return name;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
    
    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
    
    public RuleType getType() {
        return type;
    }

    public void setType(RuleType type) {
        this.type = type;
    }
    
    public void setName(String name) {
        String newValue = name != null ? name : "";

        if (!newValue.equals(this.name)) {
            String oldValue = this.name;
            this.name = newValue;
            listeners.firePropertyChange(PROPERTY_RULE_DATA, oldValue, newValue);
        }
    }
    
    public List<Transition> getSourceTransitions() {
        return sourceTransitions;
    }

    public List<Transition> getTargetTransitions() {
        return targetTransitions;
    }

    public void addSourceTransition(Transition transition) {
        if (!sourceTransitions.contains(transition)) {
            sourceTransitions.add(transition);
            listeners.firePropertyChange(PROPERTY_SOURCE_CONNECTIONS, null, transition);
        }
    }

    public void removeSourceTransition(Transition transition) {
        if (sourceTransitions.remove(transition)) {
            listeners.firePropertyChange(PROPERTY_SOURCE_CONNECTIONS, transition, null);
        }
    }

    public void addTargetTransition(Transition transition) {
        if (!targetTransitions.contains(transition)) {
            targetTransitions.add(transition);
            listeners.firePropertyChange(PROPERTY_TARGET_CONNECTIONS, null, transition);
        }
    }

    public void removeTargetTransition(Transition transition) {
        if (targetTransitions.remove(transition)) {
            listeners.firePropertyChange(PROPERTY_TARGET_CONNECTIONS, transition, null);
        }
    }
    
    public String getDisplayText() {
        switch (type) {
            case CONDITIONAL:
                return isNonEmpty(condition) ? condition : "[condition]";

            case CALL:
                if (isNonEmpty(calledRuleName)) {
                    if (isNonEmpty(parameters)) {
                        return calledRuleName + "(" + parameters + ")";
                    }

                    return calledRuleName + "()";
                }

                return isNonEmpty(name) ? name : "call";

            case SKIP:
                return "";

            case UPDATE:
                return isNonEmpty(name) ? name : "update";

            case FORALL:
                return isNonEmpty(name) ? name : "forall";

            case SEQ:
                return isNonEmpty(name) ? name : "seq";

            case PAR:
                return isNonEmpty(name) ? name : "par";

            case CHOOSE:
                return isNonEmpty(name) ? name : "choose";

            case UNKNOWN:
            default:
                return isNonEmpty(name) ? name : "rule";
        }
    }

    private boolean isNonEmpty(String value) {
        return value != null && !value.trim().isEmpty();
    }
    
    
}