package org.asmeta.nusmv;

import static org.asmeta.nusmv.util.Util.getDomainName;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import org.asmeta.nusmv.util.AsmNotSupportedException;
import org.asmeta.simulator.value.Value;

import asmeta.definitions.domains.AbstractTd;
import asmeta.definitions.domains.AgentDomain;
import asmeta.definitions.domains.BooleanDomain;
import asmeta.definitions.domains.ConcreteDomain;
import asmeta.definitions.domains.Domain;
import asmeta.definitions.domains.EnumTd;
import asmeta.definitions.domains.ProductDomain;

/** visitor of domains, returns the values in the domain
 * 
 * @author garganti
 *
 */
public class AsValueListVisitor extends org.asmeta.parser.util.ReflectiveVisitor<List<Value[]>>{
	
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
	
	public List<Value[]> visit(ProductDomain domain) throws AsmNotSupportedException  {
		List<Domain> domains = domain.getDomains();
		ArrayList<Value[]> result = new ArrayList<Value[]>();
		combineValues(domains, 0, result, new Stack<Value>());
		return result;
	}
	
	/**
	 * Combine values.
	 * 
	 * @param domains the domains
	 * @param index   the index
	 * @param result  the result
	 * @param tupla   the tupla
	 */
	public void combineValues(List<Domain> domains, int index, ArrayList<Value[]> result, Stack<Value> tupla) {
		Domain domain = domains.get(index);
		AsValueListVisitor avls = new AsValueListVisitor(domainValues);
		List<Value[]> values = avls.visit(domain);
		for (Value[] value : values) {
			for (Value v : value) {
				tupla.push(v);
			}
			if (domains.size() == index + 1) {
				Value[] temp = new Value[tupla.size()];
				for (int i = 0; i < temp.length; i++) {
					temp[i] = tupla.get(i);
				}
				result.add(temp);
			} else {
				combineValues(domains, index + 1, result, tupla);
			}
			for (Value v : value) {
				tupla.pop();
			}
		}
	}
	
}