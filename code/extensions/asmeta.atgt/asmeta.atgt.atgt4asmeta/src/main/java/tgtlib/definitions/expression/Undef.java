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




/** the undef expression to be used instead of the null value when a value is undef.
 * 
 * @author garganti
 *
 * @version $Revision: 1.0 $
 */
public final class Undef implements Expression{
	
	
	public static final Undef UNDEF = new Undef();

	private Undef() {
	}

	/**
	 * Method toString.
	 * @return String
	 */
	@Override
	public String toString() {
		return "undef";
	};
	
	/**
	 * Method accept.
	 * @param ask ExpressionVisitor<T>
	 * @return T
	 * @see tgtlib.definitions.expression.Expression#accept(ExpressionVisitor<T>)
	 */
	@Override
	public <T> T accept(ExpressionVisitor<T> ask) {
		throw new RuntimeException("undef is not visitable yet");
	}

}
