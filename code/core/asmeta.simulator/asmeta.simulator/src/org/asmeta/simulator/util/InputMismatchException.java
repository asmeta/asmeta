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
 * Wrong read input exception. 
 *
 */
public class InputMismatchException extends Exception {

	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructor.
	 * 
	 * @param string a message
	 */
	public InputMismatchException(String string) {
		super(string);
	}

}
