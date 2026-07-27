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
package atgt.specification.location;

/**
 * The Interface LocationVisitorI.
 */
public interface LocationVisitorI<T> {

	/**
	 * For constant.
	 * 
	 * @param constant
	 *            the constant
	 * 
	 * @return the t
	 */
	public T forConstant(Constant constant);

	/**
	 * For variable.
	 * 
	 * @param variable
	 *            the variable
	 * 
	 * @return the t
	 */
	public T forVariable(Variable variable);

	/**
	 * For function.
	 * 
	 * @param function
	 *            the function
	 * 
	 * @return the t
	 */
	public T forFunction(Function function);

	/** for logical variables 
	 * @param logicalVariable */	
	public T forLogicalVariable(LogicalVariable logicalVariable);
}
