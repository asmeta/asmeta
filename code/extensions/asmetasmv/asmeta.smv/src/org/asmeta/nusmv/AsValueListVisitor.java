package org.asmeta.nusmv;

import static org.asmeta.nusmv.util.Util.getDomainName;

import java.util.List;
import java.util.Map;

import org.asmeta.nusmv.util.AsmNotSupportedException;
import org.asmeta.simulator.value.Value;

import asmeta.definitions.domains.AbstractTd;
import asmeta.definitions.domains.AgentDomain;
import asmeta.definitions.domains.BooleanDomain;
import asmeta.definitions.domains.ConcreteDomain;
import asmeta.definitions.domains.EnumTd;

/** visitor of domains, returns the values in the domain
 * 
 * @author garganti
 *
 */
class AsValueListVisitor extends org.asmeta.parser.util.ReflectiveVisitor<List<Value[]>>{
	
	private Map<String, List<Value[]>> domainValues;

	AsValueListVisitor(Map<String, List<Value[]>> domainValues){
		this.domainValues = domainValues;
	} 
	
	public List<Value[]> visit(BooleanDomain domain) {
		return domainValues.get("Boolean");
	}

	public List<Value[]> visit(ConcreteDomain domain) throws AsmNotSupportedException {
		return domainValues.get(getDomainName(domain));
	}

	public List<Value[]> visit(AgentDomain domain) throws AsmNotSupportedException {
		return domainValues.get("Agent");
	}

	public List<Value[]> visit(EnumTd domain) throws AsmNotSupportedException {
		return domainValues.get(getDomainName(domain));
	}

	public List<Value[]> visit(AbstractTd domain) throws AsmNotSupportedException {
		// System.out.println(domainValues.get(getDomainName(domain)));
		return domainValues.get(getDomainName(domain));
	}
	
}