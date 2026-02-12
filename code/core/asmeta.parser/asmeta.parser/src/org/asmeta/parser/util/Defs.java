package org.asmeta.parser.util;


import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import asmeta.definitions.ControlledFunction;
import asmeta.definitions.DerivedFunction;
import asmeta.definitions.DynamicFunction;
import asmeta.definitions.Function;
import asmeta.definitions.MonitoredFunction;
import asmeta.definitions.OutFunction;
import asmeta.definitions.RuleDeclaration;
import asmeta.definitions.SharedFunction;
import asmeta.definitions.StaticFunction;
import asmeta.definitions.domains.AbstractTd;
import asmeta.definitions.domains.BagDomain;
import asmeta.definitions.domains.BooleanDomain;
import asmeta.definitions.domains.ConcreteDomain;
import asmeta.definitions.domains.Domain;
import asmeta.definitions.domains.EnumTd;
import asmeta.definitions.domains.MapDomain;
import asmeta.definitions.domains.PowersetDomain;
import asmeta.definitions.domains.ProductDomain;
import asmeta.definitions.domains.RuleDomain;
import asmeta.definitions.domains.SequenceDomain;
import asmeta.definitions.domains.StringDomain;
import asmeta.definitions.domains.StructuredTd;
import asmeta.structure.Asm;
import asmeta.structure.Signature;
import asmeta.transitionrules.basictransitionrules.BlockRule;
import asmeta.transitionrules.basictransitionrules.MacroCallRule;
import asmeta.transitionrules.basictransitionrules.Rule;
import asmeta.transitionrules.turbotransitionrules.SeqRule;

/**
 * added 7 Jan 2008 by acarioni
 * 
 * A collection of methods to compare declarations, classify functions,... 
 *
 */
public class Defs {
	
	/**
	 * Compares two domain declarations.
	 * 
	 * @param d1 a domain
	 * @param d2 another domain
	 * @return true if the declarations are equal, false otherwise
	 */
	public static boolean equals(Domain d1, Domain d2) {
		if (!(d1 instanceof StructuredTd)) {
			if (d2 instanceof StructuredTd) {
				return false;
			}
			// non-structured domains
			String name1 = d1.getName();
			String name2 = d2.getName();
			String moduleName1 = getAsmName(d1);
			String moduleName2 = getAsmName(d2);
			return name1.equals(name2) && moduleName1.equals(moduleName2);
			// structured domains
		} else if (d1 instanceof ProductDomain) {
			if (!(d2 instanceof ProductDomain)) {
				return false;
			}
			List<?> lst1 = ((ProductDomain) d1).getDomains();
			List<?> lst2 = ((ProductDomain) d2).getDomains();
			if (lst1.size() != lst2.size()) {
				return false;
			}
			Iterator<?> i1 = lst1.iterator();
			Iterator<?> i2 = lst2.iterator();
			while (i1.hasNext()) {
				Domain dd1 = (Domain) i1.next();
				Domain dd2 = (Domain) i2.next();
				if (!equals(dd1, dd2)) {
					return false;
				}
			}
			return true;
		} else if (d1 instanceof PowersetDomain) {
			if (!(d2 instanceof PowersetDomain)) {
				return false;
			}
			Domain dd1 = ((PowersetDomain) d1).getBaseDomain();
			Domain dd2 = ((PowersetDomain) d2).getBaseDomain();
			return equals(dd1, dd2);
		} else if (d1 instanceof SequenceDomain) {
			if (!(d2 instanceof SequenceDomain)) {
				return false;
			}
			Domain dd1 = ((SequenceDomain) d1).getDomain();
			Domain dd2 = ((SequenceDomain) d2).getDomain();
			return equals(dd1, dd2);
		} else if (d1 instanceof RuleDomain) {
			if (!(d2 instanceof RuleDomain)) {
				return false;
			}
			List<?> lst1 = ((RuleDomain) d1).getDomains();
			List<?> lst2 = ((RuleDomain) d2).getDomains();
			if (lst1.size() != lst2.size()) {
				return false;
			}
			Iterator<?> i1 = lst1.iterator();
			Iterator<?> i2 = lst2.iterator();
			while (i1.hasNext()) {
				Domain dd1 = (Domain) i1.next();
				Domain dd2 = (Domain) i2.next();
				if (!equals(dd1, dd2)) {
					return false;
				}
			}
			return true;			
		} else if (d1 instanceof BagDomain) {
			if (!(d2 instanceof BagDomain)) {
				return false;
			}
			Domain dd1 = ((BagDomain) d1).getDomain();
			Domain dd2 = ((BagDomain) d2).getDomain();
			return equals(dd1, dd2);
		} else if (d1 instanceof MapDomain) {
			if (!(d2 instanceof MapDomain)) {
				return false;
			}
			Domain sd1 = ((MapDomain) d1).getSourceDomain();
			Domain sd2 = ((MapDomain) d2).getSourceDomain();
			Domain td1 = ((MapDomain) d1).getTargetDomain();
			Domain td2 = ((MapDomain) d2).getTargetDomain();
			return equals(sd1, sd2) && equals(td1, td2);
		}
		throw new RuntimeException("Unable to compare domains: " 
				+ d1.getName() + ", " + d2.getName());
	}
		
	/**
	 * Checks if a domain is a subset of another domain.<br><br>
	 * Example:<br>
	 * domain A<br>
	 * domain B subsetof A<br>
	 * domain C subsetof B<br><br>
	 * Transitively, C is a subset of A.
	 * 
	 * @param dom1 a domain
	 * @param dom2 another domain
	 * @return true if dom1 is a subset of dom2, false otherwise
	 */
	public static boolean isSubsetOf(Domain dom1, Domain dom2) {
//		for (;;) {
//			if (Defs.equals(dom1, dom2)) {
//				// FIXME I assume that dom1 and dom2 have the same type domain
//				return true;
//			}			
//			if (dom1 instanceof ConcreteDomain) {
//				// climb the hierarchy
//				ConcreteDomain cdom = (ConcreteDomain) dom1;
//				dom1 = cdom.getTypeDomain();
//				continue;
//			}
//			// dom1 has not super set
//			return false;
//		}
		return subsetOfMetric(dom1, dom2) >= 0;
	}

	/**
	 * Checks if a domain is a subset of another domain.<br><br>
	 * Example:<br>
	 * domain A<br>
	 * domain B subsetof A<br>
	 * domain C subsetof B<br><br>
	 * Transitively, C is a subset of A.
	 * 
	 * @param dom1 a domain
	 * @param dom2 another domain
	 * @return true if dom1 is a subset of dom2, -1 otherwise
	 */
	public static int subsetOfMetric(Domain dom1, Domain dom2) {
		for (int i = 0;; i++) {
			if (Defs.equals(dom1, dom2)) {
				// FIXME I assume that dom1 and dom2 have the same type domain
				return i;
			}			
			if (dom1 instanceof ConcreteDomain) {
				// climb the hierarchy
				ConcreteDomain cdom = (ConcreteDomain) dom1;
				dom1 = cdom.getTypeDomain();
				continue;
			}
			// dom1 has not super set
			return -1;
		}
	}
	

	/**
	 * Compares two function  declarations.
	 * 
	 * @param f1 a function
	 * @param f2 another function
	 * @return true if the declarations are equal, false otherwise
	 */
	public static boolean equals(Function f1, Function f2) {
		String name1 = f1.getName();
		String name2 = f2.getName();
		String moduleName1 = getAsmName(f1);
		String moduleName2 = getAsmName(f2);
		if (name1.equals(name2) 
			&& f1.getArity() == f2.getArity() 
			&& moduleName1.equals(moduleName2)) {
			// check parameter types
			Domain d1 = f1.getDomain();
			Domain d2 = f2.getDomain();
			Domain cd1 = f1.getCodomain();
			Domain cd2 = f2.getCodomain();
			// 0-arity functions have a null domain
			if (d1 == null || d2 == null) {
				return d1 == null && d2 == null && equals(cd1, cd2);
			}
			return equals(d1, d2) && equals(cd1, cd2);
		}
		return false;
	}
	
	/**
	 * Returns the model of the given domain.
	 * 
	 * @param d a domain
	 * @return the model, null if the domain is built-in
	 */
	public static Asm getAsm(Domain d) {
		Signature sig = d.getSignature();
		if (sig == null) return null;
		Asm asm = sig.getHeaderSection().getAsm();
		return asm;
	}
	
	/**
	 * Returns the module name of a domain.
	 * 
	 * @param d a domain
	 * @return the module name, "" if the domain is built-in
	 */
	public static String getAsmName(Domain d) {
		Asm asm = getAsm(d);
		if (asm == null) return "";
		return asm.getName();
	}

	/**
	 * Returns the module of a function.
	 * 
	 * @param f a function
	 * @return the module
	 */
	public static Asm getAsm(Function f) {
		Signature sig = f.getSignature();
		if (sig!=null)
		  return sig.getHeaderSection().getAsm();
		else return null;
	}

	/**
	 * Returns the module name of a function.
	 * 
	 * @param f a function
	 * @return the module name
	 */
	public static String getAsmName(Function f) {
		Asm asm = getAsm(f);
		return asm.getName();
	}

	/**
	 * Returns the module of a rule.
	 * 
	 * @param rd a rule
	 * @return the module
	 */
	public static Asm getAsm(RuleDeclaration rd) {
		Asm asm = rd.getAsmBody().getAsm();
		return asm;
	}

	/**
	 * Returns the module name of a RuleDeclaration.
	 * 
	 * @param f a function
	 * @return the module name
	 */
	public static String getAsmName(RuleDeclaration rd) {		
		Asm asm = getAsm(rd);
		return asm.getName();
	}

    /**
     * A function is static if and only if it is a <i>StaticFunction</i>.
     * 
     * @param func a function
     * @return True if it is static
     */
    public static boolean isStatic(Function func) {
    	return func instanceof StaticFunction;
    }
    
    /**
     * A function is dynamic if and only if it is a <i>DynamicFunction</i>.
     * 
     * @param func a function
     * @return True if it is dynamic
     */
    public static boolean isDynamic(Function func) {
    	return func instanceof DynamicFunction;
    }

    /**
     * A function is controlled if and only if it is a <i>ControlledFunction</i>.
     * 
     * @param func a function
     * @return True if it is controlled
     */
    public static boolean isControlled(Function func) {
    	return func instanceof ControlledFunction;
    }

    /**
     * A function is controlled if and only if it is a <i>MonitoredFunction</i>.
     * 
     * @param func a function
     * @return True if it is monitored
     */
    public static boolean isMonitored(Function func) {
    	return func instanceof MonitoredFunction;
    }
    
    /**
     * A function is derived if and only if it is a <i>DerivedFunction</i>.
     * 
     * @param func a function
     * @return True if it is derived
     */
    public static boolean isDerived(Function func) {
    	return func instanceof DerivedFunction;
    }
    
    /**
     * A function is out if and only if it is a <i>OutFunction</i>.
     * 
     * @param func a function
     * @return True if it is out
     */
    public static boolean isOut(Function func) {
    	return func instanceof OutFunction;
    }

    /**
     * A function is shared if and only if it is a <i>SharedFunction</i>.
     * 
     * @param func a function
     * @return True if it is shared
     */
    public static boolean isShared(Function func) {
    	return func instanceof SharedFunction;
    }

    /**
     * A function is constant if and only if it is static and has arity 0.
     * 
     * @param func a function
     * @return True if it is constant
     */
    public static boolean isConst(Function func) {
    	return
    		isStatic(func) &&
    		func.getDomain() == null; 
    }

    /**
     * A function is an abstract constant if and only if it is constant and 
     * the codomain is abstract or an abstract subdomain.
     * 
     * @param func a function
     * @return True if it is an abstract constant
     */
    public static boolean isAbstractConst(Function func) {
    	return
    		isConst(func) &&
    		(func.getCodomain() instanceof AbstractTd ||
   			(func.getCodomain() instanceof ConcreteDomain &&
			((ConcreteDomain) func.getCodomain()).getTypeDomain() instanceof AbstractTd));
    }

    /**
     * A function is an agent constant if and only if it is constant and 
     * the codomain is <i>Agent</i> or an <i>Agent</i> subdomain.
     * 
     * @param func a function
     * @return True if it is an agent constant
     */
    public static boolean isAgentConst(Function func) {
    	return
		isConst(func) &&
		(func.getCodomain().getName().equals("Agent") ||
		(func.getCodomain() instanceof ConcreteDomain &&
		((ConcreteDomain) func.getCodomain()).getTypeDomain().getName().equals("Agent")));
    }
    
    /**
     * A function belongs to the standard library if and only if it is static
     * and is not defined in the current model.<br>
     * NOTE: there is a problem if the user has forget the definition and the
     * function is not standard.
     * 
     * @param func a function
     * @return True if it belongs to the standard library
     */    
    public static boolean isBuiltIn(Function func) {
    	return 
    		isStatic(func) &&
    		func.getDefinition() == null;
    }
    
    /**
     * A function is user defined if and only if it is constant and 
     * is not defined in the standard library.<br>
     * NOTE: this method should be called after the discovery that the function
     * does not belong to the standard library.
     * 
     * @param func a function
     * @return True if it is user defined
     */
    public static boolean isUserDefined(Function func) {
    	return 
    		isStatic(func) &&
    		func.getDefinition() != null;
    }
    
    /**
	 * Checks if domain is an abstract domain.
	 * 
	 * @param domain a domain
	 * 
	 * @return true if domain is an abstract domain, false otherwise
	 */
	public static boolean isAbstractDomain(Domain domain) {
		return domain instanceof AbstractTd;
	}

	/**
	 * Checks if domain is a concrete domain.
	 * 
	 * @param domain a domain
	 * 
	 * @return true if domain is a concrete domain, false otherwise
	 */
	public static boolean isConcreteDomain(Domain domain) {
		return domain instanceof ConcreteDomain;
	}

	/**
	 * Checks if domain is an enum domain.
	 * 
	 * @param domain
	 *            the domain
	 * 
	 * @return true, if domain is an enum domain, false otherwise
	 */
	public static boolean isEnumDomain(Domain domain) {
		return domain instanceof EnumTd;
	}

	/**
	 * Checks if domain is a boolean domain.
	 * 
	 * @param domain a domain
	 * 
	 * @return true if domain is a boolean domain, false otherwise
	 */
	public static boolean isBooleanDomain(Domain domain) {
		return domain instanceof BooleanDomain;
	}

	public static boolean isStringDomain(Domain domain) {
		return domain instanceof StringDomain;
	}
	
	/**
	 * Checks if rule is a block rule.
	 * 
	 * @param rule a rule
	 * 
	 * @return true if rule is a block rule, false otherwise
	 */
	public static boolean isBlockRule(Rule rule) {
		return rule instanceof BlockRule;
	}

	/**
	 * Checks if rule is a seq rule.
	 * 
	 * @param rule a rule
	 * 
	 * @return true if rule is a seq rule, false otherwise
	 */
	public static boolean isSeqRule(Rule rule) {
		return rule instanceof SeqRule;
	}

	/**
	 * Checks if rule is a MacroCall rule.
	 * 
	 * @param rule a rule
	 * 
	 * @return true if rule is a seq rule, false otherwise
	 */
	public static boolean isMacroCallRule(Rule rule) {
		return rule instanceof MacroCallRule;
	}

	public static Function searchFunction(Asm asm, String name) {
		Collection<?> funcs = asm.getHeaderSection().getSignature().getFunction();
		for (Object o : funcs) {
			Function f = (Function) o;
			if (f.getName().equals(name)) {
				return f;
			}
		}
		throw new RuntimeException("Function " + name + " not found");
	}
	
	public static Domain searchDomain(Asm asm, String name) {
		Collection<?> doms = asm.getHeaderSection().getSignature().getDomain();
		for (Object o : doms) {
			Domain d = (Domain) o;
			if (d.getName().equals(name)) {
				return d;
			}
		}
		throw new RuntimeException("Domain " + name + " not found");
	}
	
	public static RuleDeclaration searchMacro(Asm asm, String name) {
		Collection<?> rules = asm.getBodySection().getRuleDeclaration();
		for (Object o : rules) {
			RuleDeclaration r = (RuleDeclaration) o;
			if (r.getName().equals(name)) {
				return r;
			}
		}
		throw new RuntimeException("Rule " + name + " not found");
	}

	public static boolean isSelf(Function function) {
		return function.getName().equals("self") && function.getArity() == 0 && isControlled(function);
	}
}
