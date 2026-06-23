package org.asmeta.visualdesigner.factories;

import org.asmeta.visualdesigner.model.Transition;
import org.eclipse.gef.requests.CreationFactory;

public class TransitionCreationFactory implements CreationFactory {

    @Override
    public Object getNewObject() {
        return new Transition();
    }

    @Override
    public Object getObjectType() {
        return Transition.class;
    }
}