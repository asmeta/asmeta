/**
 * 
 */
package org.asmeta.simulator.value;

import java.util.HashMap;
import java.util.Map;

import org.asmeta.parser.Defs;

import asmeta.definitions.domains.Domain;

/**
 * A reserve value, i.e. an element extracted from the reserve domain.
 * 
 */
public class ReserveValue extends Value<String> {
	
	/**
	 * Suffix added to the name to obtain an unique value.
	 * 
	 */
	public static int nextSuffix = 0;

	public static Map<String, Integer> mapDomainNextSuffix = new HashMap<String, Integer>();
	
	/**
	 * Element's value.
	 * 
	 */
	protected String name;
	
	/**
	 * Extends a given dynamic domain by extracting an element from the
	 * reserve domain. These elements are pairwise different.
	 * 
	 * @param domainToExtend domain to extend
	 * @return a new reserve value
	 */
	public static ReserveValue getFromReserve(Domain domainToExtend) {
		if (Defs.isSubsetOf(domainToExtend, AgentValue.getAgentDomain())) {
			return AgentValue.extendDomain(domainToExtend);
		}
		//String valueName = mangleName(domainToExtend.getName());
		//modifica PA 4/5/2010
		String valueName = mangleName(domainToExtend);
		return new ReserveValue(valueName);
	}
	
	/**
	 * Mangles the agents' names so are pairwise different.
	 * 
	 * @param name the agent's name
	 * @return a new unique name
	 */
	protected static String mangleName(String name) {
		//old version: il suffisso e' incrementale, senza tenere conto di domini diversi.
		//Siano dati due domini DomA e DomB. Due extend rule sui due domini (prima su DomA
		//e poi su DomB) hanno come risultato la creazione di "DomA!1" e "DomB!2".
		//return name + "!" + ++nextSuffix;

		//new version(PA 4/5/2010): il suffisso e' incrementale su ogni dominio,
		//Siano dati due domini DomA e DomB. Due extend rule sui due domini (prima su DomA
		//e poi su DomB) hanno come risultato la creazione di "DomA!1" e "DomB!1".
		Integer suffix = mapDomainNextSuffix.get(name);
		if(suffix == null) {
			//suffix = new Integer(1);
			suffix = Integer.valueOf(1);
		}
		//mapDomainNextSuffix.put(name, new Integer(suffix + 1));
		mapDomainNextSuffix.put(name, Integer.valueOf(suffix + 1));
		return name + "!" + suffix;
	}

	//PA 4/5/2010
	protected static String mangleName(Domain domain) {
		//versione con il percorso completo del dominio: Modulo!NomeDominio
		/*return mangleName(domain.getSignature().getHeaderSection().getAsm().getName() + 
		"!" + domain.getName());*/

		//versione con solo il nome del dominio
		return mangleName(domain.getName());
	}

	/**
	 * Creates a reserve value. It should be used only during the
	 * initialization of the simulator, not to create new values.
	 * For this purpose, use method <i>getFromReserve()</i>.
	 * 
	 * @param name agent's name
	 */
	public ReserveValue(String name) {
		this.name = name;
	}
	
	/**
	 * Gets the agent's value.
	 * 
	 * @return the agent's value
	 */
	@Override
	public String getValue() {
		return name;
	}

	@Override
	public int hashCode() {
		return name.hashCode();
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof ReserveValue) {
			ReserveValue value = (ReserveValue) o;
			return getValue().equals(value.getValue());
		}
		return false;
	}
	
	@Override
	public String toString() {
		return name;
	}

	//PA: 14 giugno 10
	@Override
	public Value clone() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
