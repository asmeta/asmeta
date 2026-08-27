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
package atgt.coverage;

import java.util.List;
import java.util.Map;

import atgt.specification.location.Function;
import atgt.specification.location.Location;
import atgt.specification.location.Variable;
import tgtlib.definitions.expression.IdExpression;
import tgtlib.util.TestSeqContentInterface;

/**
 * represents the content of a ASM TEST Sequence*.
 */
public abstract class AsmTestSeqContentInterface implements TestSeqContentInterface {

	/**
	 * All instructions.
	 * 
	 * @return the list< map< string, string>>
	 */
	public abstract List<Map<Location, String>> allInstructions();
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see tgtlib.util.TestSeqContentInterface#addEvent(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public final void addAssignment(String varS, String value) { 
		throw new RuntimeException("please declare variable type");
	}

	
	/**
	 * 
	 */
	public abstract void addAssignment(Variable var, String value);

	/**
	 * 
	 */
	public abstract void addAssignment(Function var, List<IdExpression> args, String value);


	public final void addAssignment(Location var, String value){
		throw new RuntimeException("please declare if variable or a function application");		
	}

	
	/**
	 * To video.
	 * 
	 * @param out
	 *            the out
	 */
	public abstract StringBuffer toVideo();

	/**
	 * To string buffer.
	 * 
	 * @return the string buffer
	 */
	public abstract StringBuffer toStringBuffer();
	

}
