package org.asmeta.flattener.term;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.asmeta.parser.util.Defs;
import org.asmeta.simulator.wrapper.RuleFactory;
import org.eclipse.emf.common.util.EList;

import asmeta.definitions.Function;
import asmeta.definitions.domains.AbstractTd;
import asmeta.definitions.domains.AgentDomain;
import asmeta.definitions.domains.ConcreteDomain;
import asmeta.definitions.domains.Domain;
import asmeta.definitions.domains.EnumElement;
import asmeta.definitions.domains.EnumTd;
import asmeta.structure.Asm;
import asmeta.structure.DomainDefinition;
import asmeta.structure.Signature;
import asmeta.terms.basicterms.BooleanTerm;
import asmeta.terms.basicterms.SetTerm;
import asmeta.terms.basicterms.Term;
import asmeta.terms.furtherterms.EnumTerm;

public class DomainVisitor {
	private Asm asm;
	// map of domains: it contains a pair (domain name - domains values)
	public HashMap<String, Set<Term>> domainSet;

	public DomainVisitor(Asm asm) {
		this.asm = asm;
		domainSet = new HashMap<String, Set<Term>>();
	}

	public void visitDomains() {
		// Boolean domain
		Set<Term> set = new HashSet<Term>();
		RuleFactory rf = new RuleFactory();
		BooleanTerm trueTerm = rf.createBooleanTerm();
		trueTerm.setSymbol("true");
		BooleanTerm falseTerm = rf.createBooleanTerm();
		falseTerm.setSymbol("false");
		set.add(trueTerm);
		set.add(falseTerm);
		domainSet.put("Boolean", set);

		// TODO � 0, quando ci sono agenti, quindi il controllo per agent?
		Integer i = asm.getBodySection().getDomainDefinition().size();

		for (DomainDefinition def : asm.getBodySection().getDomainDefinition()) {
			ConcreteDomain concreteDomain = def.getDefinedDomain();
			Term body = def.getBody();

			assert !(def.getDefinedDomain().getTypeDomain() instanceof AgentDomain);
			// if it's not an agent domain
			SetTerm st = null;
			if (body instanceof SetTerm) {
				st = (SetTerm) body;
			}
			assert st != null;
			set = new HashSet<Term>();
			for (Term t : st.getTerm()) {
				set.add(t);
			}
			domainSet.put(concreteDomain.getName(), set);
		}
		Signature signature = asm.getHeaderSection().getSignature();
		List<Function> functions = signature.getFunction();
		for (Domain domain : signature.getDomain()) {
			if (Defs.isConcreteDomain(domain) && !(((ConcreteDomain) domain).getTypeDomain() instanceof AgentDomain)) {
				continue;// concrete domains are visited in the definition section (except for agents)
			}
			if (Defs.isEnumDomain(domain)) {
				// all domains in this branch are Enum domain, so the casting is safe
				EnumTd enumDomain = (EnumTd) domain;
				// get the list of elements of enum domain
				EList<EnumElement> enumElementList = enumDomain.getElement();

				set = new HashSet<Term>();
				EnumTerm enumTerm = null;
				for (EnumElement ee : enumElementList) {
					// create the EnumTerm relative to the EnumElement
					enumTerm = rf.createEnumTerm();
					enumTerm.setSymbol(ee.getSymbol());
					enumTerm.setDomain(domain);
					// add the EnumDomain values to the set of terms
					set.add(enumTerm);
				}
				// add to the domains map, the name of domain and the values inside
				domainSet.put(domain.getName(), set);
			} else if (Defs.isAbstractDomain(domain)) {
				AbstractTd abstractDomain = (AbstractTd) domain;
				EnumTerm enumTerm = null;
				set = new HashSet<Term>();
				// find the abstract domain elements from the functions of the signature
				for (Function f : functions) {
					// the criterion is: static, without arguments, its codomain is the abstract
					// domain
					if (f.getCodomain().getName().equals(abstractDomain.getName()) && f.getArity() == 0
							&& Defs.isAbstractConst(f)) {
						// create the EnumTerm relative to the static function
						enumTerm = rf.createEnumTerm();
						enumTerm.setSymbol(f.getName());
						enumTerm.setDomain(f.getCodomain());
						// add the EnumDomain values to the set of terms
						set.add(enumTerm);
					}
				}
				// add to the domains map, the name of domain and the values inside
				domainSet.put(domain.getName(), set);
			} else if (Defs.isConcreteDomain(domain)
					&& ((ConcreteDomain) domain).getTypeDomain() instanceof AgentDomain) {
				// if it's an agent domain
				// AgentDomain agentDomain = ((ConcreteDomain)domain).getTypeDomain();
				EnumTerm enumTerm = null;
				set = new HashSet<Term>();

				// find the agents from the functions of the signature
				for (Function f : functions) {
					// the criterion is: .....
					if (f.getCodomain().getName().equals(domain.getName()) && f.getArity() == 0) {
						// create the EnumTerm relative to the agent
						enumTerm = rf.createEnumTerm();
						enumTerm.setSymbol(f.getName());
						enumTerm.setDomain(f.getCodomain());
						// add the EnumDomain values to the set of terms
						set.add(enumTerm);
					}
				}
				// add to the domains map, the name of domain and the values inside
				domainSet.put(domain.getName(), set);
			} else if (Defs.isConcreteDomain(domain)
					&& ((ConcreteDomain) domain).getTypeDomain() instanceof AbstractTd) {
				// if it's an agent domain
				// AgentDomain agentDomain = ((ConcreteDomain)domain).getTypeDomain();
				EnumTerm enumTerm = null;
				set = new HashSet<Term>();

				// find the agents from the functions of the signature
				for (Function f : functions) {
					// the criterion is: .....
					if (f.getCodomain().getName().equals(domain.getName()) && f.getArity() == 0) {
						// create the EnumTerm relative to the agent
						enumTerm = rf.createEnumTerm();
						enumTerm.setSymbol(f.getName());
						enumTerm.setDomain(f.getCodomain());
						// add the EnumDomain values to the set of terms
						set.add(enumTerm);
					}
				}
				// add to the domains map, the name of domain and the values inside
				domainSet.put(domain.getName(), set);
			}

			else {
				throw new Error("Domain " + domain.getClass().getSimpleName() + " not supported.");
			}
		}

		set = new HashSet<Term>();
		for (Function f : functions) {
			// the criterion is: static, without arguments, its codomain is the abstract
			// domain
			if (f.getCodomain().getName().equals("Agent") && f.getArity() == 0 && Defs.isAbstractConst(f)) {
				// create the EnumTerm relative to the static function
				EnumTerm enumTerm = rf.createEnumTerm();
				enumTerm.setSymbol(f.getName());
				enumTerm.setDomain(f.getCodomain());
				// add the EnumDomain values to the set of terms
				set.add(enumTerm);
			}
		}
		if (set.size() > 0) {
			domainSet.put("Agent", set);
		}
	}

	public Set<Term> getValue(String domainName) {
		return domainSet.get(domainName);
	}

	public Set<Term> getValues(Domain domain) {
		return getValue(domain.getName());
	}

	public HashMap<String, Set<Term>> getDomainSet() {
		return domainSet;
	}

}