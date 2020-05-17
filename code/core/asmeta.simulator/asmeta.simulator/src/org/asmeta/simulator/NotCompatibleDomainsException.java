/*******************************************************************************
 * Copyright (c) 2011 .
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *      - initial API and implementation
 ******************************************************************************/
package org.asmeta.simulator;

import asmeta.definitions.domains.Domain;

public class NotCompatibleDomainsException extends Exception {
	
	private static final long serialVersionUID = 1L;

	protected NotCompatibleDomainsException(Domain actual, Domain formal) {
		super("The domain " + actual.getName() + " can not be used as actual parameter of a formal parameter with domain " + formal.getName());
	}
	
}
