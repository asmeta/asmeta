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
package tgtlib.specification;

import java.util.Collection;

import asmeta.definitions.domains.Domain;
import tgtlib.definitions.expression.type.EnumConstCreator;
import tgtlib.definitions.expression.type.Type;
import tgtlib.definitions.expression.type.Variable;
import tgtlib.specification.location.Constant;
import tgtlib.specification.location.DerivedFunction;
import tgtlib.specification.location.Function;


/** 
 * The Interface Specification for Abstract State Machines specifications.
 * TODO - an intermediate class Abstract State Machines specification could be useful (to allow SCR Specification)
 */
public interface Specification {
	
	String getName();
	
	/** the variables of the specification
	 * 
	 * @return
	 */
	public Collection<? extends Variable> getVariables();
	
	
	public Collection<Axiom> getAxiom();

	tgtlib.specification.location.Variable getVariable(String locationName);

	Function getFunction(String funName);

	Constant getConstantByName(String var);

	DerivedFunction getDerivedFunction(String var);

	Type getDomain(Domain dom, EnumConstCreator ecc);

	Type getTypeFor(String typeName);
	
}
