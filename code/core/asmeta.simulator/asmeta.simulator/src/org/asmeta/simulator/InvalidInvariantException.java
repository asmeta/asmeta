/*******************************************************************************
 * Copyright (c) 2009 .
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *      - initial API and implementation
 ******************************************************************************/
package org.asmeta.simulator;

import asmeta.definitions.Invariant;

/**
 * An invalid invariant has been found.
 * 
 */
public class InvalidInvariantException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	Invariant violated;

	public UpdateSet us;

	public InvalidInvariantException(Invariant invariant, UpdateSet u) {
		violated = invariant;
		us = u;
	}

	/** 
	 * Returns the invariant which has been violated.
	 *  
	 */
	public Invariant getInvariant() {
		return violated;
	}

}
