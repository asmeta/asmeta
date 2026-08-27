/*******************************************************************************
 * Copyright (c) 2010 Angelo Gargantini.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Angelo Gargantini - initial API and implementation
 *******************************************************************************/
package tgtlib.definitions.expression;

/** in cas ethe visitor is not capable to visit an expression
 * 
 * @author garganti
 *
 */
public class VisitNotSupportedExc extends RuntimeException {

	public VisitNotSupportedExc(String string) {
		super(string);
	}

}
