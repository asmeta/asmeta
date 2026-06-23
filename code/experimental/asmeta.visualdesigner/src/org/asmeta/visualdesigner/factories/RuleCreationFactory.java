package org.asmeta.visualdesigner.factories;

import org.asmeta.visualdesigner.model.RuleNode;
import org.asmeta.visualdesigner.model.RuleType;
import org.eclipse.gef.requests.CreationFactory;

public class RuleCreationFactory implements CreationFactory {

    private static int counter = 1;

    private final RuleType type;
    private final String labelPrefix;

    public RuleCreationFactory(RuleType type, String labelPrefix) {
        this.type = type;
        this.labelPrefix = labelPrefix;
    }

    /*@Override
    public Object getNewObject() {
        RuleNode node = new RuleNode(labelPrefix + " " + counter, 0, 0, 130, 60, type);
        counter++;
        return node;
    }*/
    
    @Override
    public Object getNewObject() {
        int width = 130;
        int height = 60;

        if (type == RuleType.SKIP) {
            width = 20;
            height = 20;
        }

        RuleNode node = new RuleNode(labelPrefix + " " + counter, 0, 0, width, height, type);
        counter++;
        return node;
    }

    @Override
    public Object getObjectType() {
        return type;
    }
}