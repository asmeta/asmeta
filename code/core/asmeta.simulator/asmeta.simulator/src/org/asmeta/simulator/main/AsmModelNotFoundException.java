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
package org.asmeta.simulator.main;

/**
 * The model with the given name has not been found.
 *
 */
public class AsmModelNotFoundException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public AsmModelNotFoundException(String modelName) {
		super("No model with name " + modelName + " in the current file.");
	}
	
}
