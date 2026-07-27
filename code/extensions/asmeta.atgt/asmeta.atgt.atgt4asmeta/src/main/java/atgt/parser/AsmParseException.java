/*******************************************************************************
 * Copyright (c) 2008 Angelo Gargantini.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Angelo Gargantini - initial API and implementation
 ******************************************************************************/
package atgt.parser;

import atgt.parser.asmgofer.ParseException;

// TODO: Auto-generated Javadoc
/**
 * The Class AsmParseException.
 */
public class AsmParseException extends tgtlib.specification.ParseException {

	/**
	 * Instantiates a new asm parse exception.
	 * 
	 * @param e
	 *            the e
	 */
	public AsmParseException(ParseException e) {
		super(e.getMessage());
	}

}
