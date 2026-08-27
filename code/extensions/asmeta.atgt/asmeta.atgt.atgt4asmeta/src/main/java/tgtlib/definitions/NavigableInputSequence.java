/*******************************************************************************
 * Copyright (c) 2008, 2010 Angelo Gargantini.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Angelo Gargantini - initial API and implementation
 ******************************************************************************/
package tgtlib.definitions;

import java.util.List;

import tgtlib.util.Pair;


/** represent an Input Sequence as list of monitored variables
 * and values
 *
 * TODO: can be empty ??? has more elemnts???
 */
public interface NavigableInputSequence extends tgtlib.definitions.InputSequence{

	/** return is the Input Sequence is an empty InputSequence
     */
    public boolean isEmpty();

	/* reset to the first input*/ 
    public void reset();    
   
    /* get the current mon variable and its value that changes
     * null if all the variables that change have been already given (nextState = -1)
     * in SCR is just one
     * importante: non esistion pair con due first element uguali*/
    public List<Pair<String,String>> getInputs();
        
    /* go to the next state, return the number of the next state or
     * -1 if there is no next state*/
    public int nextState();

    
}    
    
