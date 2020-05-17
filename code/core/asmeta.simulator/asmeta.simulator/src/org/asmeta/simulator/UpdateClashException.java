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

import org.asmeta.simulator.value.Value;

/**
 * Inconsistent update set exception.
 * 
 */
public class UpdateClashException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public Location loc;
	public Value c1,c2;
	
	/**
	 * Creates a new exception.
	 * 
	 * @param location a location with two values
	 * @param content1 the first value
	 * @param content2 the second value
	 */
	public UpdateClashException(Location location, Value content1, Value content2) {
		loc = location; 
		c1 = content1; 
		c2 = content2; 
	}

}
