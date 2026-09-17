package org.asmeta.visualdesigner.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

public class StartNode implements DiagramNode {

    private final PropertyChangeSupport listeners = new PropertyChangeSupport(this);

    private final List<Transition> sourceTransitions = new ArrayList<>();
    private final List<Transition> targetTransitions = new ArrayList<>();

    private String name;
    private int x;
    private int y;
    private int width;
    private int height;
    
    public static final String PROPERTY_START_DATA = "StartNode.Data";

    private String initialization = "";

    public StartNode(String name, int x, int y) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.width = 20;
        this.height = 20;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public void setLayout(int x, int y, int width, int height) {
        int oldX = this.x;
        int oldY = this.y;
        int oldWidth = this.width;
        int oldHeight = this.height;

        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        if (oldX != x || oldY != y || oldWidth != width || oldHeight != height) {
            listeners.firePropertyChange(PROPERTY_LAYOUT, null, null);
        }
    }

    @Override
    public List<Transition> getSourceTransitions() {
        return sourceTransitions;
    }

    @Override
    public List<Transition> getTargetTransitions() {
        return targetTransitions;
    }

    @Override
    public void addSourceTransition(Transition transition) {
        if (!sourceTransitions.contains(transition)) {
            sourceTransitions.add(transition);
            listeners.firePropertyChange(PROPERTY_SOURCE_CONNECTIONS, null, transition);
        }
    }

    @Override
    public void removeSourceTransition(Transition transition) {
        if (sourceTransitions.remove(transition)) {
            listeners.firePropertyChange(PROPERTY_SOURCE_CONNECTIONS, transition, null);
        }
    }

    @Override
    public void addTargetTransition(Transition transition) {
        if (!targetTransitions.contains(transition)) {
            targetTransitions.add(transition);
            listeners.firePropertyChange(PROPERTY_TARGET_CONNECTIONS, null, transition);
        }
    }

    @Override
    public void removeTargetTransition(Transition transition) {
        if (targetTransitions.remove(transition)) {
            listeners.firePropertyChange(PROPERTY_TARGET_CONNECTIONS, transition, null);
        }
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        listeners.addPropertyChangeListener(listener);
    }

    @Override
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        listeners.removePropertyChangeListener(listener);
    }
    
    public String getInitialization() {
        return initialization;
    }

    public void setInitialization(String initialization) {
        String newValue = initialization != null ? initialization : "";

        if (!this.initialization.equals(newValue)) {
            String oldValue = this.initialization;
            this.initialization = newValue;
            listeners.firePropertyChange(
                    PROPERTY_START_DATA,
                    oldValue,
                    newValue
            );
        }
    }
    
}