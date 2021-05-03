package org.asmeta.nuxmv;

import java.util.ArrayList;

import org.asmeta.nusmv.GetFunctionDomains;

import asmeta.definitions.domains.AbstractTd;
import asmeta.definitions.domains.BooleanDomain;
import asmeta.definitions.domains.ConcreteDomain;
import asmeta.definitions.domains.Domain;
import asmeta.definitions.domains.EnumTd;
import asmeta.definitions.domains.ProductDomain;

//public class GetFunctionDomains extends ReflectiveVisitor {
public class GetFunctionDomains extends org.asmeta.parser.util.ReflectiveVisitor {
	private static ArrayList<Domain> domains;//TODO PA: why static?
	
	GetFunctionDomains() {
		domains = new ArrayList<Domain>();
	}
	
	public void visit(Domain domain) {
		invokeMethod(domain, "visit");
	}
	
	public void visit(AbstractTd domain) {
		domains.add(domain);
	}
	
	public void visit(BooleanDomain domain) {
		domains.add(domain);
	}

	public void visit(EnumTd domain) {
		domains.add(domain);
	}

	public void visit(ConcreteDomain domain) {
		domains.add(domain);
	}
	
	public void visit(ProductDomain productDomain) {
		for(Domain domain: productDomain.getDomains()) {
			visit(domain);
		}
	}
	
	/**
	 * Decompone il dominio di una funzione in una lista ordinata di domini.
	 * Vengono ritornati solo domini singoli, non Product domain.
	 * Prod(EnumDom, boolean) diventa [EnumDom, boolean].
	 * Prod(ConcrDom, EnumDom2, Prod(EnumDom, boolean)) diventa
	 * [ConcrDom, EnumDom2, EnumDom, boolean].
	 * 
	 * @param domain  Il dominio di una funzione.
	 * @return La lista dei singoli domini che compongono il dominio della 
	 * funzione.
	 */
	public static ArrayList<Domain> getFuncDomains(Domain domain) {
		if(domain != null) {
			(new GetFunctionDomains()).visit(domain);
		}
		return domains;
	}
}
