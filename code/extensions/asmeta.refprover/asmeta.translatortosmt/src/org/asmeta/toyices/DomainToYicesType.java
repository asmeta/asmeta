package org.asmeta.toyices;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.asmeta.parser.Defs;
import org.asmeta.parser.util.ReflectiveVisitor;

import asmeta.definitions.Function;
import asmeta.definitions.domains.AbstractTd;
import asmeta.definitions.domains.AgentDomain;
import asmeta.definitions.domains.ConcreteDomain;
import asmeta.definitions.domains.EnumElement;
import asmeta.definitions.domains.EnumTd;
import asmeta.definitions.domains.TypeDomain;
import asmeta.structure.Signature;
import asmeta.terms.basicterms.SetTerm;
import asmeta.terms.basicterms.Term;
//
// it converts a domain to a yices type and performs auxialiary activities
//
public class DomainToYicesType extends ReflectiveVisitor<String> {

	private YicesModel yicesModel;
	private Map<String, String> domainWithoutUndef;
	private TermVisitor tv;
	private Signature signature;

	public DomainToYicesType(YicesModel yicesModel, Map<String, String> domainWithoutUndef, Signature signature) {
		this.yicesModel = yicesModel;
		this.domainWithoutUndef = domainWithoutUndef;
		tv = new TermVisitor(yicesModel);
		this.signature = signature;
	}

	public String visit(EnumTd domain) {
		String domainName = domain.getName();
		StringBuilder commandParts = new StringBuilder("(define-type " + domainName + "(scalar");
		List<EnumElement> elementTerms = ((EnumTd) domain).getElement();
		String[] enumValues = new String[elementTerms.size()];
		String symbol;
		for (int i = 0; i < elementTerms.size(); i++) {
			symbol = elementTerms.get(i).getSymbol();
			commandParts.append(" " + symbol);
			enumValues[i] = symbol;
			yicesModel.allEnumAndAgentsValues.add(symbol);
		}
		// commandParts.append("))");
		String undefValue = domainName + "UNDEF";
		yicesModel.domainUndefValue.put(domainName, undefValue);
		domainWithoutUndef.put(domainName, "(subtype (v::" + domainName + ") (/= v " + undefValue + "))");
		commandParts.append(" " + undefValue + "))");
		yicesModel.enumValues.put(domainName, enumValues);
		return commandParts.toString();
	}

	public String visit(ConcreteDomain domain) {
		String domainName = domain.getName();
		ConcreteDomain concrDom = (ConcreteDomain) domain;
		TypeDomain typeDomain = concrDom.getTypeDomain();
		if (typeDomain instanceof AgentDomain) {
			ArrayList<String> agents = new ArrayList<String>();
			for (Function func : signature.getFunction()) {
				String codomainName = func.getCodomain().getName();
				if (Defs.isStatic(func) && func.getArity() == 0 && codomainName.equals(domainName)) {
					agents.add(func.getName());
					yicesModel.allEnumAndAgentsValues.add(func.getName());
				}
			}
			if (agents.size() > 0) {
				StringBuilder domainDeclSB = new StringBuilder("(define-type " + domainName + "(scalar");
				for (int i = 0; i < agents.size(); i++) {
					domainDeclSB.append(" ").append(agents.get(i));
				}
				// domainDeclSB.append("))");
				String undefValue = domainName + "UNDEF";
				yicesModel.domainUndefValue.put(domainName, undefValue);
				domainWithoutUndef.put(domainName, "(subtype (v::" + domainName + ") (/= v " + undefValue + "))");
				domainDeclSB.append(" " + undefValue + "))");
				yicesModel.agentValues.put(domainName, agents);
				return domainDeclSB.toString();
			}
		} else {
			Term defDomainBody = concrDom.getDefinition().getBody();
			if (defDomainBody instanceof SetTerm) {
				SetTerm set = (SetTerm) defDomainBody;
				String setAsStr = tv.visit(set);
				yicesModel.intDomainsDeclaration.put(domainName, setAsStr);
				Integer[] setValues = TermVisitor.getSetValues(set);
				yicesModel.intValues.put(domainName, setValues);
				yicesModel.domainIntValues.put(domain, setValues);
				String undefValue = String.valueOf(Integer.MIN_VALUE);
				yicesModel.domainUndefValue.put(domainName, undefValue);
				// TODO
				return "";
			} else {
				throw new Error();
			}
		}
		throw new Error();		
	}
	// TODO: this part seems to work, but the traslation of abstract values does not 
	// AG 12/01/2023
	public String visit(AbstractTd domain) {
		String domainName = domain.getName();
		//
		ArrayList<String> abstractvalues = new ArrayList<>();
		for (Function func : signature.getFunction()) {
			String codomainName = func.getCodomain().getName();
			if (Defs.isStatic(func) && func.getArity() == 0 && codomainName.equals(domainName)) {
				abstractvalues.add(func.getName());
			}
		}
		// re use enumvalues to store the abstract values
		yicesModel.enumValues.put(domainName, abstractvalues.toArray(new String[abstractvalues.size()]));
		return "(define-type " + domainName + ")";
	}
}
