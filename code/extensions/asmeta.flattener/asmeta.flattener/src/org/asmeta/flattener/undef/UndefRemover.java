package org.asmeta.flattener.undef;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.asmeta.flattener.term.DomainVisitor;
import org.asmeta.parser.ASMParser;
import org.asmeta.parser.Defs;

import asmeta.definitions.ControlledFunction;
import asmeta.definitions.Function;
import asmeta.definitions.RuleDeclaration;
import asmeta.definitions.domains.ConcreteDomain;
import asmeta.definitions.domains.Domain;
import asmeta.definitions.domains.DomainsFactory;
import asmeta.definitions.domains.EnumElement;
import asmeta.definitions.domains.EnumTd;
import asmeta.definitions.domains.IntegerDomain;
import asmeta.definitions.domains.TypeDomain;
import asmeta.structure.Asm;
import asmeta.structure.FunctionInitialization;
import asmeta.structure.StructureFactory;
import asmeta.terms.TermsFactory;
import asmeta.terms.basicterms.SetTerm;
import asmeta.terms.basicterms.Term;
import asmeta.terms.furtherterms.EnumTerm;
import asmeta.terms.furtherterms.FurthertermsFactory;
import asmeta.terms.furtherterms.IntegerTerm;
import asmeta.transitionrules.basictransitionrules.Rule;

public class UndefRemover {
	private Asm asm;
	private List<Domain> asmDomains;
	private Set<Domain> fixedDomain = new HashSet<>();
	private Map<Domain, Object> mapDomainsUndef = new HashMap<>();
	private Map<Domain, Term> mapDomainsUndefTerm = new HashMap<>();

	public UndefRemover(String file) throws Exception {
		this(ASMParser.setUpReadAsm(new File(file)).getMain());
	}

	public UndefRemover(Asm asm) {
		this.asm = asm;
		asmDomains = asm.getHeaderSection().getSignature().getDomain();
	}

	public void fixAsm() {
		Set<Function> affectedFuncs = findUndef();
		fixDomains(asm, affectedFuncs);
		RewriteRules rr = new RewriteRules(asm, affectedFuncs, mapDomainsUndefTerm);
		for (RuleDeclaration rd : asm.getBodySection().getRuleDeclaration()) {
			Rule newBody = rr.visit(rd.getRuleBody());
			rd.setRuleBody(newBody);
		}
		UndefRemover.fixInitialState(asm, affectedFuncs, mapDomainsUndefTerm);
	}

	public void fixDomains(Asm asm, Set<Function> affectedFuncs) {
		DomainVisitor dv = new DomainVisitor(asm);
		dv.visitDomains();

		for (Function f : affectedFuncs) {
			Domain codomain = f.getCodomain();
			// TODO we should also check whether it is used as domain of some function. In
			// this case, we should create a new domain (otherwise, we would introduce new
			// locations)
			if (!fixedDomain.contains(codomain)) {
				boolean usedAsFuncDomain = usedAsFuncDomain(asm, codomain);
				if (Defs.isEnumDomain(codomain)) {
					EnumTd e = ((EnumTd) codomain);
					List<EnumElement> elements = e.getElement();
					EnumElement undefEnum = DomainsFactory.eINSTANCE.createEnumElement();
					String symbol = e.getName().toUpperCase() + "_UNDEF";
					undefEnum.setSymbol(symbol);
					EnumTerm enumTerm = TermsFactory.eINSTANCE.getFurtherTerms().createEnumTerm();
					enumTerm.setDomain(codomain);
					enumTerm.setSymbol(symbol);
					EnumTd newDom = null;
					/*
					 * if (usedAsFuncDomain) { newDom = DomainsFactory.eINSTANCE.createEnumTd();
					 * newDom.setName(e.getName() + "_forUndef");
					 * newDom.getElement().addAll(elements); newDom.getElement().add(undefEnum);
					 * asm.getHeaderSection().getSignature().getDomain().add(newDom); } else {
					 */
					elements.add(undefEnum);
					newDom = e;
					// }
					fixedDomain.add(newDom);
					mapDomainsUndef.put(newDom, undefEnum);
					mapDomainsUndefTerm.put(newDom, enumTerm);
				} else if (Defs.isConcreteDomain(codomain)) {
					ConcreteDomain cd = (ConcreteDomain) codomain;
					TypeDomain td = cd.getTypeDomain();
					Set<Term> values = dv.getValues(codomain);
					if (td instanceof IntegerDomain) {
						Integer max = null;
						Integer min = null;

						for (Term v : values) {
							int value = Integer.parseInt(((IntegerTerm) v).getSymbol());
							if (max == null) {
								max = value;
								min = value;
							} else {
								if (value < min) {
									min = value;
								}
								if (value > max) {
									max = value;
								}
							}
						}
						Term domainBody = cd.getDefinition().getBody();
						if (domainBody instanceof SetTerm) {
							SetTerm st = (SetTerm) domainBody;
							IntegerTerm it = FurthertermsFactory.eINSTANCE.createIntegerTerm();
							// add now a new value, for instance max + 1234
							String symbol = Integer.toString(max + 1234);
							it.setSymbol(symbol);
							st.getTerm().add(it);
							fixedDomain.add(codomain);
							mapDomainsUndefTerm.put(codomain, it);
						}
					}
				} else if (Defs.isBooleanDomain(codomain)) {
					EnumTd newDom = DomainsFactory.eINSTANCE.createEnumTd();
					newDom.setName("Boolean_forUndef");
					List<EnumElement> newDomElements = newDom.getElement();
					for (String symbol : new String[] { "TRUE", "FALSE", "BOOL_UNDEF" }) {
						EnumElement newEl = DomainsFactory.eINSTANCE.createEnumElement();
						newEl.setSymbol(symbol);
						newDomElements.add(newEl);
					}
					asmDomains.add(newDom);
					f.setCodomain(newDom);
					fixedDomain.add(codomain);
					EnumTerm enumTerm = TermsFactory.eINSTANCE.getFurtherTerms().createEnumTerm();
					enumTerm.setDomain(codomain);
					enumTerm.setSymbol("BOOL_UNDEF");
					mapDomainsUndefTerm.put(codomain, enumTerm);
					mapDomainsUndefTerm.put(newDom, enumTerm);
				}
			}
		}
	}

	public Set<Function> findUndef() {
		Map<Function, Set<Function>> deps = GetDependencies.getDependencies(asm);
		Set<Function> undefSources = GetUndefSource.getUndefSources(asm);
		Set<Function> affectedFuncs = new HashSet<>(undefSources);
		for (Function us : undefSources) {
			// TODO improve getDependencies in order to avoid this loop
			for (Entry<Function, Set<Function>> es : deps.entrySet()) {
				if (es.getValue().contains(us)) {
					affectedFuncs.add(es.getKey());
				}
			}
		}
		return affectedFuncs;
	}

	public static void fixInitialState(Asm asm, Set<Function> funcsWithUndef, Map<Domain, Term> mapDomainsUndefTerm) {
		List<FunctionInitialization> is = asm.getDefaultInitialState().getFunctionInitialization();
		List<Function> funcs = asm.getHeaderSection().getSignature().getFunction();
		List<Function> initFuncs = new ArrayList<>();
		for(FunctionInitialization fi: is) {
			initFuncs.add(fi.getInitializedFunction());
		}
		for(Function f: funcs) {
			if(Defs.isControlled(f)) {
				if (!initFuncs.contains(f)) {
					FunctionInitialization newFi = StructureFactory.eINSTANCE.createFunctionInitialization();
					newFi.setInitializedFunction((ControlledFunction) f);
					Domain codomain = f.getCodomain();
					Term value = mapDomainsUndefTerm.get(codomain);
					newFi.setBody(value);
					is.add(newFi);
				}
			}
		}
	}

	private static boolean usedAsFuncDomain(Asm asm, Domain domain) {
		for (Function func : asm.getHeaderSection().getSignature().getFunction()) {
			if (func.getDomain() == domain) {
				return true;
			}
		}
		return false;
	}

	public static Asm buildNewAsm(String file) throws Exception {
		UndefRemover uf = new UndefRemover(file);
		uf.fixAsm();
		return uf.asm;
	}
}
