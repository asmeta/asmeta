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
package atgt.translator;

import tgtlib.definitions.expression.CondExpression;
import tgtlib.definitions.expression.NextExpression;

/**
 * Implementa l'interfaccia AsmExpressionVisitor e fornisce i metodi per la
 * traduzione in SPIN delle espressioni della specifica.
 * 
 * @author Sax Rinzivillo
 */
public class ExpressionToSPINVisitor extends ExpressionToC {

	/** The SINGLETON. */
	public static ExpressionToSPINVisitor SINGLETON = new ExpressionToSPINVisitor();

	/**
	 * Instantiates a new expression to spin visitor.
	 */
	private ExpressionToSPINVisitor() {
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see atgt.specification.expression.AsmExpressionVisitor#forNextExpression(atgt.specification.expression.NextExpression)
	 */
	@Override
	public StringBuffer forNextExpression(NextExpression nextExpression) {
		throw new RuntimeException("Spin translation for next ???");
	}
	
	protected String getCfrOperator() {
		return " -> ";
	}	
	
}
