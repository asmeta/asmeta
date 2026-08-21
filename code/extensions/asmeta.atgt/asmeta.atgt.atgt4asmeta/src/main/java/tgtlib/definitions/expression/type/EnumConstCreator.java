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
package tgtlib.definitions.expression.type;

import tgtlib.definitions.expression.IdExpression;
import tgtlib.definitions.expression.IdExpressionCreator;

/**
 * creates a new Enumconst keeps track of the EnumConst already created !!!
 */
public class EnumConstCreator extends IdExpressionCreator {

	/**
	 * Creates the enum const.
	 * 
	 * @param _id
	 *            the _id
	 * 
	 * @return the enum const
	 */
	public EnumConst createEnumConst(String _id) {
		// check if another expression already exists
		IdExpression idS = createdExprs.get(_id);
		if (idS == null) {
			idS = new EnumConst(_id);
			createdExprs.put(_id, idS);
			return (EnumConst) idS;
		} else {
			if (idS instanceof EnumConst) {
				return (EnumConst) idS;
			} else {
				// static expression sometimes are created as idexpressions with static type
				if (idS.getType() instanceof atgt.specification.type.AbstractType) {
					return new EnumConst(_id);
				}
				throw new RuntimeException("enum " + _id + " already created as IdExpression (not enum) of type " + idS.getType() + " of " + idS.getType().getClass().getName());
			}
		}
	}

	/**
	 * Creates the id expression.
	 * 
	 * @param _id
	 *            the _id
	 * @param _type
	 *            the domain of the ID
	 * 
	 * @return the id expression
	 */
	@Override
	public IdExpression createIdExpression(String _id, Type _type) {
		//assert _type != null : _id;
		if (_id.equalsIgnoreCase("true"))
			return BoolType.TRUE_CONST;
		if (_id.equalsIgnoreCase("false"))
			return BoolType.FALSE_CONST;
		if (_type instanceof EnumType) {
			return createEnumConst(_id);
		}
		return super.createIdExpression(_id, _type);
	}
}
