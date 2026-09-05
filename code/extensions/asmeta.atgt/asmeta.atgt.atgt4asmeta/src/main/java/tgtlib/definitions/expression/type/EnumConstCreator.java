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

import org.apache.log4j.Logger;

import tgtlib.definitions.expression.IdExpression;
import tgtlib.definitions.expression.IdExpressionCreator;

/**
 * creates a new Enumconst keeps track of the EnumConst already created !!!
 */
public class EnumConstCreator extends IdExpressionCreator {

	private final static Logger LOGGER = Logger.getLogger(EnumConstCreator.class);

	/**
	 * Creates the enum const.
	 * 
	 * @param _id the _id
	 * @param t   type can be null, since the type can be set later. If not null, it
	 *            must be an EnumType
	 * 
	 * @return the enum const
	 */
	public EnumConst createEnumConst(String _id, Type _type) {
		// check if another expression already exists
		IdExpression idS = createdExprs.get(_id);
		if (idS == null) {
			idS = new EnumConst(_id);
			createdExprs.put(_id, idS);
			return (EnumConst) idS;
		} else {
			if (_type != null && idS.getType() != _type) {
				// check if the expression is already created with a different type
				// and a new type is requested
				// note that in case of null is ok
				if (idS.getType() == null) {
					LOGGER.debug("setting the type of " + _id + " to " + _type);
					idS.setType(_type);
				} else {
					throw new RuntimeException(_id + " already created with (not null) type " + idS.getType());
				}
			}
			LOGGER.debug("getting the same ID " + _id + " with " + toString());
			if (idS instanceof EnumConst) {
				return (EnumConst) idS;
			} else {
				// static expression sometimes are created as idexpressions with static type
				// now this is improper - use the functionterm is the right solution
				//if (idS.getType() instanceof atgt.specification.type.AbstractType) {
				//	return new EnumConst(_id);
				//}
				throw new RuntimeException("enum " + _id + " already created as IdExpression (not enum) of type "
						+ idS.getType() + " of " + idS.getType().getClass().getName());
			}
		}

	}
	/**
	 * Creates the enum const.
	 * 
	 * @param _id the _id - type can be set later, since the type can be set later. If not null, it
	 * 
	 * @return the enum const
	 */
	public EnumConst createEnumConst(String _id) {
		return createEnumConst(_id, null);
	}

	/**
	 * Creates the id expression.
	 * 
	 * @param _id   the _id
	 * @param _type the domain of the ID
	 * 
	 * @return the id expression
	 */
	@Override
	public IdExpression createIdExpression(String _id, Type _type) {
		// assert _type != null : _id;
		if (_id.equalsIgnoreCase("true"))
			return BoolType.TRUE_CONST;
		if (_id.equalsIgnoreCase("false"))
			return BoolType.FALSE_CONST;
		if (_type instanceof EnumType) {
			return createEnumConst(_id, _type);
		}
		return super.createIdExpression(_id, _type);
	}
}
