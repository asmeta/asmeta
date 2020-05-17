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
package org.asmeta.simulator.util;

/**
 * A name misses a definition exception.
 *
 */
public class UnresolvedReferenceException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public UnresolvedReferenceException() {
		super();
	}

	public UnresolvedReferenceException(String message, Throwable cause) {
		super(message, cause);
	}

	public UnresolvedReferenceException(String message) {
		super(message);
	}

	public UnresolvedReferenceException(Throwable cause) {
		super(cause);
	}

}
