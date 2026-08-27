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
package tgtlib.generator;

/**
 * The model chcker has bene run but it has not completed the result (wrong PATH, interruption ...)
 *
 * @author  Angelo Gargantini
 */
public class ModelCheckerExecutionException extends java.lang.Exception {

    /**
	 * 
	 */
	private static final long serialVersionUID = 4404611005531966583L;

	/**
     * Constructs an instance of <code>ModelCheckerExecutionException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public ModelCheckerExecutionException(String msg) {
        super(msg);
    }

	public ModelCheckerExecutionException(Exception t) {
		super(t);
	}
}


