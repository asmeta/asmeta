package org.asmeta.visualdesigner.model;

import java.beans.PropertyChangeListener;
import java.util.List;

public interface DiagramNode {

    String PROPERTY_LAYOUT = "DiagramNode.Layout";
    String PROPERTY_SOURCE_CONNECTIONS = "DiagramNode.SourceConnections";
    String PROPERTY_TARGET_CONNECTIONS = "DiagramNode.TargetConnections";

    String getName();

    int getX();
    int getY();
    int getWidth();
    int getHeight();

    void setLayout(int x, int y, int width, int height);

    List<Transition> getSourceTransitions();
    List<Transition> getTargetTransitions();

    void addSourceTransition(Transition transition);
    void removeSourceTransition(Transition transition);

    void addTargetTransition(Transition transition);
    void removeTargetTransition(Transition transition);

    void addPropertyChangeListener(PropertyChangeListener listener);
    void removePropertyChangeListener(PropertyChangeListener listener);
}