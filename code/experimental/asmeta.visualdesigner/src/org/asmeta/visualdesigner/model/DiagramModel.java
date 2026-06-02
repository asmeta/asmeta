package org.asmeta.visualdesigner.model;

import java.util.ArrayList;
import java.util.List;

public class DiagramModel {

    private List<RuleNode> rules = new ArrayList<>();
    private List<Transition> transitions = new ArrayList<>();

    public List<RuleNode> getRules() {
        return rules;
    }

    public List<Transition> getTransitions() {
        return transitions;
    }
}