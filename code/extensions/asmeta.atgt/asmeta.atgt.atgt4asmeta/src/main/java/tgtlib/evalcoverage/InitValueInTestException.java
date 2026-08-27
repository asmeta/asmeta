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
package tgtlib.evalcoverage;

/**
 * this exception is thrown when the init value from the spec and from the test
 * differ
 * 
 * @author garganti
 *
 */
@SuppressWarnings("serial")
public class InitValueInTestException extends RuntimeException {

	public InitValueInTestException(String string) {
		super(string);
	}
}
