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
package tgtlib.definitions.expression.visitors;

/**
 * the evaluation is not supported still.
 */
public class EvaluationNotSupported extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new evaluation not supported.
	 * 
	 * @param s
	 *            the s
	 */
	public EvaluationNotSupported(final String s) {
		super(s);
	}

	/**
	 * Instantiates a new evaluation not supported.
	 * 
	 * @param string
	 *            the string
	 * @param ecc
	 *            the ecc
	 */
	public EvaluationNotSupported(final String string, final ClassCastException ecc) {
		super(string, ecc);
	}
}
