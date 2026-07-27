/*******************************************************************************
 * Copyright (c) 2008, 2010 Angelo Gargantini.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Angelo Gargantini - initial API and implementation
 ******************************************************************************/
package tgtlib.definitions.expression.type;

/**
 * The Interface TypeVisitorI.
 */
public interface TypeVisitorI<T> {

	/**
	 * For enum type. (Bool is a subtype)
	 * 
	 * @param enumType
	 *            the enum type
	 * 
	 * @return the t
	 */
	public T forEnumType(EnumType enumType);


	/**
	 * For Bool type (it is not a subtype of enum !!)
	 * 
	 * @param enumType
	 *            the enum type
	 * 
	 * @return the t
	 */
	public T forBoolType(BoolType boolType);

	/**
	 * For bound type.
	 * 
	 * @param boundType
	 *            the bound type
	 * 
	 * @return the t
	 */
	public T forBoundType(BoundType boundType);
	
	
	/**
	 * For Integer type.
	 * 
	 * @param dummyType
	 *            the dummy type
	 * 
	 * @return the t
	 */
	public T forIntegerType(IntegerType intType);

}
