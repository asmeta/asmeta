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

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import tgtlib.definitions.expression.type.Type;

/**
 * used to create new IdExpressions It keeps track of the Id already created to
 * avoid duplications. Also it returns for true and false the right EnumConst of
 * the Boolean Type (for this reason it is in this package)
 * */
public class IdExpressionCreator {

	private final static Logger LOGGER = Logger.getLogger(IdExpressionCreator.class);
	
	/**
	 * all the ID (constants, variables, EnumConsts..) present in the spec
	 */
	protected Map<String, IdExpression> createdExprs = new HashMap<String, IdExpression>();

	public IdExpressionCreator(){
		LOGGER.debug("creating a new IDExpresson creator");		
	}

	/**
	 * Return a fresh new Id.
	 * 
	 * @deprecated: Get the id from the spec or instantiate a new
	 *              IdExpressionCreator instead
	 * 
	 * @param _id
	 * @return
	 */
	@Deprecated
	public static IdExpression createNewIdExpression(String _id) {
		assert false;
		LOGGER.warn("creating _id with a generic IDexpresson creator");
		return new IdExpressionCreator().createIdExpression(_id, null);
	}

	/**
	 * Creates the id expression (not enum expressions). If already exists return that.
	 * 
	 * @param _id
	 *            the _id
	 * @param _type TODO
	 * 
	 * @return the id expression (new or already created)
	 */
	public IdExpression createIdExpression(final String _id, Type _type) {
		//System.out.println(_id + "\t" + _type);
		assert !_id.equalsIgnoreCase("true");
		assert !_id.equalsIgnoreCase("false");
		// check if another expression already exists
		IdExpression idS = createdExprs.get(_id);
		if (idS == null) {
			// _id is not already created
			// check if a number
			Number number = parse(_id);
			if (number != null){
				idS = new NumericLiteral(number);
			} else {
				// this assert can be relaxed to allow null types
				//assert _type != null : " creating id " + _id + " with null type";
				idS = new IdExpression(_id, _type);
			}
			LOGGER.debug("creating a new ID " + _id + " with " + toString());	
			// set the is creator for the id
			// idS.icc = this;
			createdExprs.put(_id, idS);			
		} else if (_type != null && idS.getType() != _type) {
			// check if the expression is already created with a different type
			// and a new type is requested 
			// note that in case of null is ok
			throw new RuntimeException(_id + " already created with type " + idS.getType());
		}		
		LOGGER.debug("getting the same ID " + _id + " with " + toString());	
		return idS;
	}

	// given a string return the number
	// starts with the simplest
	// return null if not a number
	public static Number parse(String str) {
	    Number number = null;
	    try {
            number = Integer.parseInt(str);
	    } catch(NumberFormatException e) {
	        try {
                number = Long.parseLong(str);
	        } catch(NumberFormatException e1) {
	            try {
		            number = Float.parseFloat(str);
	            } catch(NumberFormatException e2) {
	                try {
	        	        number = Double.parseDouble(str);
	                } catch(NumberFormatException e3) {
	                    return null ;
	                }       
	            }       
	        }       
	    }
	    return number;
	}
}
