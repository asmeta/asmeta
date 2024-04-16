package org.asmeta.toyices;

import java.util.SortedSet;
import java.util.TreeSet;

import org.asmeta.parser.util.ReflectiveVisitor;

import asmeta.definitions.domains.BooleanDomain;
import asmeta.definitions.domains.ConcreteDomain;
import asmeta.definitions.domains.Domain;
import asmeta.definitions.domains.EnumTd;
import asmeta.definitions.domains.IntegerDomain;
import asmeta.definitions.domains.NaturalDomain;
import asmeta.definitions.domains.TypeDomain;
import asmeta.terms.basicterms.SetTerm;
import asmeta.terms.basicterms.Term;
import asmeta.terms.furtherterms.IntegerTerm;

public class GetVariableRepresentationAsFormula extends ReflectiveVisitor<String> {
	private String varName;

	GetVariableRepresentationAsFormula(String varName) {
		this.varName = varName;
	}
	
	public String visit(Domain d) {
		return invokeMethod(d, "visit");
	}

	public String visit(ConcreteDomain d) {
		TypeDomain typeDomain = d.getTypeDomain();
		if(typeDomain instanceof IntegerDomain || typeDomain instanceof NaturalDomain) {
			SetTerm body = (SetTerm) d.getDefinition().getBody();
			SortedSet<String> set = new TreeSet<String>();
			for(Term term: body.getTerm()) {
				set.add(((IntegerTerm)term).getSymbol());
			}
			StringBuilder sb = new StringBuilder("(or");
			for(String s: set) {
				sb.append(" (= " + varName + " " + s + ")");
			}
			sb.append(")");
			return sb.toString();
		}
		throw new Error("");
	}

	public String visit(EnumTd d) {
		return "";
	}

	public String visit(BooleanDomain d) {
		return "";
	}
}