/*******************************************************************************
 * Copyright (c) 2009 .
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *      - initial API and implementation
 ******************************************************************************/
package org.asmeta.simulator.util;

import java.util.ArrayList;
import java.util.List;

import org.asmeta.parser.util.ReflectiveVisitor;

import asmeta.definitions.domains.AbstractTd;
import asmeta.definitions.domains.BooleanDomain;
import asmeta.definitions.domains.ConcreteDomain;
import asmeta.definitions.domains.Domain;
import asmeta.definitions.domains.EnumElement;
import asmeta.definitions.domains.EnumTd;
import asmeta.definitions.domains.IntegerDomain;
import asmeta.definitions.domains.NaturalDomain;
import asmeta.definitions.domains.ProductDomain;
import asmeta.definitions.domains.RealDomain;
import asmeta.definitions.domains.SequenceDomain;
import asmeta.definitions.domains.StringDomain;

/**
 * returns a brief explanation of the domain if possible also the list of values
 * 
 * insert a "<returned by this class>"
 * 
 * @author garganti
 * 
 */
public class DomainPrinter extends ReflectiveVisitor<String> {

	public String visit(IntegerDomain domain) {
		return "integer constant";
	}

	public String visit(NaturalDomain domain) {
		return "natural constant";
	}

	public String visit(RealDomain domain) {
		return "real constant";
	}

	public String visit(BooleanDomain domain) {
		return "boolean constant";
	}

	public String visit(SequenceDomain domain) {
		return "sequence [] of " + visit(domain.getDomain());
	}

	public String visit(AbstractTd domain) {
		return "abstract constant in " + domain.getName();
	}

	public String visit(ProductDomain domain) {
		//String result = "tuple of (";
		StringBuilder result = new StringBuilder("tuple of (");
		boolean first = true;
		for (Object o : domain.getDomains()) {
			Domain d = (Domain) o;
			//result += (!first ? "," : "") + this.visit(d);
			result.append((!first ? "," : "") + this.visit(d));
			first = false;
		}
		//result += ")";
		result.append(")");
		//return result;
		return result.toString();
	}

	public String visit(ConcreteDomain domain) {
		return "constant in " + domain.getName() + " of type "
				+ domain.getTypeDomain().getName();
	}

	public String visit(EnumTd domain) {
		StringBuilder elSB = new StringBuilder("symbol of " + domain.getName());
		elSB.append(" in ");
		elSB.append(asList(domain).toString());
		elSB.append("");
		return elSB.toString();
	}
	
	public List<String> asList(EnumTd domain) {
		List<String> result = new ArrayList<String>(domain.getElement().size());
		for (Object element : domain.getElement()){
			EnumElement el = (EnumElement) element;
			result.add(el.getSymbol());
		} 
		return result;
	}

	public String visit(StringDomain domain) {
		return "quoted string";
	}
	public String visit(asmeta.definitions.domains.PowersetDomain pD){
		return "set {} of "+ this.visit(pD.getBaseDomain());
	}
}
