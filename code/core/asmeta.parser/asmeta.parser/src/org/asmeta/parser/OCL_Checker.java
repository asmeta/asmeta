/*******************************************************************************
 * Copyright (c) 2005, 2006 ASMETA group (http://asmeta.sourceforge.net)
 * License Information: http://asmeta.sourceforge.net/licensing/
 *
 *   This program is free software; you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License version 2 as
 *   published by the Free Software Foundation.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program; if not, write to the Free Software
 *   Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301
 *   USA
 *
 *   http://www.gnu.org/licenses/gpl.txt
 *
 *
 *******************************************************************************/
package org.asmeta.parser;

import java.io.PrintStream;
import java.math.BigInteger;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.asmeta.parser.util.AsmetaTermPrinter;

import asmeta.definitions.DerivedFunction;
import asmeta.definitions.DynamicFunction;
import asmeta.definitions.Function;
import asmeta.definitions.Invariant;
import asmeta.definitions.LocalFunction;
import asmeta.definitions.MonitoredFunction;
import asmeta.definitions.RuleDeclaration;
import asmeta.definitions.StaticFunction;
import asmeta.definitions.domains.AbstractTd;
import asmeta.definitions.domains.AgentDomain;
import asmeta.definitions.domains.AnyDomain;
import asmeta.definitions.domains.BagDomain;
import asmeta.definitions.domains.BasicTd;
import asmeta.definitions.domains.BooleanDomain;
import asmeta.definitions.domains.CharDomain;
import asmeta.definitions.domains.ComplexDomain;
import asmeta.definitions.domains.ConcreteDomain;
import asmeta.definitions.domains.Domain;
import asmeta.definitions.domains.EnumElement;
import asmeta.definitions.domains.EnumTd;
import asmeta.definitions.domains.IntegerDomain;
import asmeta.definitions.domains.MapDomain;
import asmeta.definitions.domains.NaturalDomain;
import asmeta.definitions.domains.PowersetDomain;
import asmeta.definitions.domains.ProductDomain;
import asmeta.definitions.domains.RealDomain;
import asmeta.definitions.domains.RuleDomain;
import asmeta.definitions.domains.SequenceDomain;
import asmeta.definitions.domains.StringDomain;
import asmeta.definitions.domains.StructuredTd;
import asmeta.definitions.domains.TypeDomain;
import asmeta.definitions.domains.UndefDomain;
import asmeta.structure.AgentInitialization;
import asmeta.structure.Asm;
import asmeta.structure.DomainDefinition;
import asmeta.structure.DomainInitialization;
import asmeta.structure.ExportClause;
import asmeta.structure.FunctionDefinition;
import asmeta.structure.FunctionInitialization;
import asmeta.structure.Initialization;
import asmeta.structure.Signature;
import asmeta.terms.basicterms.BooleanTerm;
import asmeta.terms.basicterms.DomainTerm;
import asmeta.terms.basicterms.FunctionTerm;
import asmeta.terms.basicterms.LocationTerm;
import asmeta.terms.basicterms.RuleAsTerm;
import asmeta.terms.basicterms.SetTerm;
import asmeta.terms.basicterms.Term;
import asmeta.terms.basicterms.TupleTerm;
import asmeta.terms.basicterms.UndefTerm;
import asmeta.terms.basicterms.VariableKind;
import asmeta.terms.basicterms.VariableTerm;
import asmeta.terms.furtherterms.BagCt;
import asmeta.terms.furtherterms.BagTerm;
import asmeta.terms.furtherterms.CaseTerm;
import asmeta.terms.furtherterms.CharTerm;
import asmeta.terms.furtherterms.ComplexTerm;
import asmeta.terms.furtherterms.ComprehensionTerm;
import asmeta.terms.furtherterms.ConditionalTerm;
import asmeta.terms.furtherterms.EnumTerm;
import asmeta.terms.furtherterms.FiniteQuantificationTerm;
import asmeta.terms.furtherterms.IntegerTerm;
import asmeta.terms.furtherterms.LetTerm;
import asmeta.terms.furtherterms.MapCt;
import asmeta.terms.furtherterms.MapTerm;
import asmeta.terms.furtherterms.NaturalTerm;
import asmeta.terms.furtherterms.RealTerm;
import asmeta.terms.furtherterms.SequenceCt;
import asmeta.terms.furtherterms.SequenceTerm;
import asmeta.terms.furtherterms.SetCt;
import asmeta.terms.furtherterms.StringTerm;
import asmeta.transitionrules.basictransitionrules.ChooseRule;
import asmeta.transitionrules.basictransitionrules.ConditionalRule;
import asmeta.transitionrules.basictransitionrules.ExtendRule;
import asmeta.transitionrules.basictransitionrules.ForallRule;
import asmeta.transitionrules.basictransitionrules.LetRule;
import asmeta.transitionrules.basictransitionrules.MacroCallRule;
import asmeta.transitionrules.basictransitionrules.TermAsRule;
import asmeta.transitionrules.basictransitionrules.UpdateRule;
import asmeta.transitionrules.derivedtransitionrules.CaseRule;
import asmeta.transitionrules.derivedtransitionrules.IterativeWhileRule;
import asmeta.transitionrules.derivedtransitionrules.RecursiveWhileRule;
import asmeta.transitionrules.turbotransitionrules.TryCatchRule;
import asmeta.transitionrules.turbotransitionrules.TurboCallRule;
import asmeta.transitionrules.turbotransitionrules.TurboLocalStateRule;
import asmeta.transitionrules.turbotransitionrules.TurboReturnRule;

/**
 *
 * @author Patrizia
 */
public class OCL_Checker {

	static Logger logger = Logger.getLogger(OCL_Checker.class);

	public static String SUPER_ANYDOMAIN = "Any";

	private static String MSG_ERR = "";

	// when the OCL checker returns false, to know the error use this method
	public static String getMsgErr() {
		return MSG_ERR;
	}

	public static boolean isBoolean(Term t) {
		return (t.getDomain() instanceof BooleanDomain);
	}

	// -----------------------------------------------------------------------//
	// ----------------- Package ASMStructure ---------------------//
	// -----------------------------------------------------------------------//

	// --- ASM --- //
	public static boolean checkAsm(Asm asm) {
		return A1(asm);
	}

	public static boolean A1(Asm asm) {
		// check constraint A1: mainrule->notEmpty() implies mainrule.arity=0
		RuleDeclaration mainRule = asm.getMainrule();
		if (mainRule != null)
			if (mainRule.getArity() != 0) {
				MSG_ERR = "Error: The arity of the main rule must be 0.";
				return false;
			}
		return true;
	}

	// --- EXPORT CLAUSE --- //
	public static boolean checkExportClause(ExportClause expCl) {
		return A2(expCl);
	}

	public static boolean A2(ExportClause expCl) {
		// check constraint A2: exportedDomain->notEmpty() or
		// exportedFunction->notEmpty() or exportedRule->notEmpty()
		if (expCl.getExportedDomain() == null
				&& expCl.getExportedFunction() == null
				&& expCl.getExportedRule() == null) {
			MSG_ERR = "Error: An export clause must contain at least one Domain or one function or one rule.";
			return false;
		}
		return true;
	}

	// --- SIGNATURE --- //
	public static boolean checkSignature(Signature s) {
		return U7(s);
	}

	public static boolean U7(Signature s) {
		// check constraint U7: function->forAll(f:Function |
		// not f.oclIsTypeOf(LocalFunction))
		Iterator<?> iter = s.getFunction().iterator();
		while (iter.hasNext()) {
			if (isLocal((Function) iter.next())) {
				MSG_ERR = "Error: An ASM signature can't declare local functions.";
				return false;
			}
		}
		return true;
	}

	public static boolean isLocal(Function f) {
		return (f instanceof LocalFunction);
	}

	// --- INITIALIZATION --- //
	public static boolean checkInitialization(Initialization i) {
		return I1(i) && I2(i) && I3(i);
	}

	public static boolean I1(Initialization i) {
		// check constraint I1/M4: I1: domainInitialization->notEmpty()
		// or functionInitialization->notEmpty() or
		// agentInitialization->notEmpty()
		if (!(i.getDomainInitialization() != null
				|| i.getFunctionInitialization() != null || i
				.getAgentInitialization() != null)) {
			MSG_ERR = "Error: An initial state must contain the initialization of at least one domain,"
					+ "function or agent.";
			return false;
		}
		return true;
	}

	public static boolean I2(Initialization i) {
		// check constraint I2:
		// domainInitialization->forAll(d1,d2:DomainInitialization |
		// d1<>d2 implies d1.initializedDomain <> d2.initializedDomain)
		return ((rightDomainInit(i.getDomainInitialization())) ? true : false);
	}

	public static boolean I3(Initialization i) {
		// check constraint I3:
		// functionInitialization->forAll(f1,f2:FunctionInitialization |
		// d1<>d2 implies d1.initializedFunction<>d2.initializedFunction)
		return ((rightFunctionInit(i.getFunctionInitialization())) ? true
				: false);
	}

	private static boolean rightDomainInit(Collection<?> domInitColl) {
		HashMap<Integer, String> domMap = new HashMap<Integer, String>();
		Iterator<?> iter = domInitColl.iterator();
		String domName;
		while (iter.hasNext()) {
			domName = ((DomainInitialization) iter.next())
					.getInitializedDomain().getName();
			if (domMap.containsKey(domName.hashCode())) {
				MSG_ERR = "Error: Within an initial state a concrete domain can be initialized only once. Domain " + domName;
				return false;
			} else
				domMap.put(domName.hashCode(), domName);
		}
		return true;
	}

	private static boolean rightFunctionInit(Collection<?> funInitColl) {
		HashMap<Integer, String> funMap = new HashMap<Integer, String>();
		Iterator<?> iter = funInitColl.iterator();
		String funName;
		DynamicFunction fun;
		while (iter.hasNext()) {
			fun = ((FunctionInitialization) iter.next())
					.getInitializedFunction();
			funName = fun.getName();
			if (fun.getDomain() != null)
				// To distinguish among overloaded function names
				funName = funName.concat("(").concat(fun.getDomain().getName())
						.concat(")");
			if (funMap.containsKey(funName.hashCode())) {
				MSG_ERR = "Error: Within an initial state a dynamic function can be initialized only once. Function " + funName;
				return false;
			} else
				funMap.put(funName.hashCode(), funName);
		}
		return true;
	}

	// ----------------- DOMAIN INITIALIZATION -------------- //
	public static boolean checkDomainInitialization(DomainInitialization d_init) {
		return I4(d_init) && I5(d_init);
	}

	public static boolean I4(DomainInitialization d_init) {
		// check constraint I4: initializedDomain.isDynamic = true
		if (!d_init.getInitializedDomain().getIsDynamic()) {
			MSG_ERR = "Error: Static domains can't be initialized.";
			return false;
		}
		return true;
	}

	public static boolean I5(DomainInitialization d_init) {
		// check constraint I5: body.domain.oclIsTypeOf(PowersetDomain) and
		// body.domain.oclAsType(PowersetDomain).baseDomain =
		// initializedDomain.typeDomain
		Domain body_td = d_init.getBody().getDomain();
		if (!((body_td instanceof PowersetDomain) && compatible(d_init
				.getInitializedDomain().getTypeDomain(),
				((PowersetDomain) body_td).getBaseDomain()))) {
			// TRICK:
			// If the names of the domains are equal, return true
			if (((PowersetDomain) body_td).getBaseDomain().getName().equals(
					d_init.getInitializedDomain().getTypeDomain().getName()))
				return true;
			MSG_ERR = "Error: The body of the domain initialization is ill formed.";
			return false;
		}
		return true;
	}

	// ----------------- DOMAIN DEFINITION -------------- //
	public static boolean checkDomainDefinition(DomainDefinition d_def) {
		return B1(d_def) && B2(d_def);
	}

	public static boolean B1(DomainDefinition d_def) {
		// check constraint B1: definedDomain.isDynamic = false
		if (d_def.getDefinedDomain().getIsDynamic()) {
			MSG_ERR = "Error: Dynamic domains must be initialized, not defined!";
			return false;
		}
		return true;
	}

	public static boolean B2(DomainDefinition d_def) {
		// check constraint B2: body.domain.oclIsTypeOf(PowersetDomain) and
		// body.domain.oclAsType(PowersetDomain).baseDomain =
		// definedDomain.typeDomain
		Domain body_td = d_def.getBody().getDomain();
		if (!(body_td instanceof PowersetDomain)) {
			MSG_ERR = "Error: The type-domain of the body of the domain definition must be a powerset."
					+ ((PowersetDomain) body_td).getBaseDomain().toString()
					+ "\n"
					+ d_def.getDefinedDomain().getTypeDomain().toString();
			return false;
		}
		if (!(compatible(d_def.getDefinedDomain().getTypeDomain(),
				((PowersetDomain) body_td).getBaseDomain())))
		// For testing:
		// Try with refMofId:
		// ((PowersetDomain) body_td).getBaseDomain().refMofId()
		// .equals(d_def.getDefinedDomain().getTypeDomain())))
		// Try with "compatible":
		// compatible(d_def.getDefinedDomain().getTypeDomain(),((PowersetDomain)
		// body_td).getBaseDomain())))
		{
			// TRICK:
			// If the names of the domains are equal, return true
			if (((PowersetDomain) body_td).getBaseDomain().getName().equals(
					d_def.getDefinedDomain().getTypeDomain().getName()))
				return true;

			MSG_ERR = "Error: The body of the domain definition is ill formed."
					+ ((PowersetDomain) body_td).getBaseDomain().toString()
					+ "\n"
					+ d_def.getDefinedDomain().getTypeDomain().toString();
			return false;
		}
		return true;
	}

	// ----------------- FUNCTION INITIALIZATION ---------- //
	public static boolean checkFunctionInitialization(
			FunctionInitialization f_init) {
		return I6(f_init) && I7_I8_I9(f_init) && I10(f_init);// && E20(f_init);
	}

	public static boolean I6(FunctionInitialization f_init) {
		// check constraint I6: variable->size() =
		// initializedFunction.arity.oclAsType(Integer)
		if (f_init.getVariable().size() != f_init.getInitializedFunction()
				.getArity()) {
			MSG_ERR = "Error: In a function initialization, the number of formal "
					+ "parameters must be equal to the arity of the function to initialize.";
			return false;
		}
		return true;
	}

	public static boolean I7_I8_I9(FunctionInitialization f_init) {
		// check constraints I7,I8,I9:
		// let size:Integer = self.variable->size() in
		// let D:Domain = self.initializedFunction.domain in
		// I7: if size = 1 then variable->at(1).domain = D
		// else if size > 1 then
		// I8: (D.oclIsTypeOf(ProductDomain) and Sequence{1..size}->
		// forAll(i:Integer |
		// variable->at(i).domain = D.oclAsType(ProductDomain).domains->at(i) ))
		// or
		// I9: (D.oclIsTypeOf(ConcreteDomain) and
		// D.oclAsType(ConcreteDomain).typeDomain.oclIsTypeOf(ProductDomain)
		// and Sequence{1..size}->
		// forAll(i:Integer | variable->at(i).domain =
		// D.oclAsType(ConcreteDomain).typeDomain.
		// oclAsType(ProductDomain).domains->at(i)) ) endif endif endlet endlet
		int size = f_init.getVariable().size();
		Domain D = f_init.getInitializedFunction().getDomain();

		// I7
		if (size == 1)
			if (f_init.getVariable().get(0).getDomain() != D) {
				MSG_ERR = "Error: The domain where the formal parameter takes its value"
						+ "must be equal to the domain of the function.";
				return false;
			} else
				return true; // case size == 1
		if (size > 1) {
			// I8
			if (D instanceof ProductDomain)
				return I8_I9aux(f_init.getVariable(), (ProductDomain) D);
			// or I9
			else if ((D instanceof ConcreteDomain)
					&& (((ConcreteDomain) D).getTypeDomain() instanceof ProductDomain))
				return I8_I9aux(f_init.getVariable(),
						(ProductDomain) ((ConcreteDomain) D).getTypeDomain());
		}
		return true; // case size == 0
	}

	private static boolean I8_I9aux(List<?> variables, ProductDomain D) {
		// Check:
		// Sequence{1..size}-> forAll(i:Integer |
		// variable->at(i).domain = D.oclAsType(ProductDomain).domains->at(i) )

		Iterator<?> iter1 = variables.iterator();
		Iterator<?> iter2 = D.getDomains().iterator();
		Domain d1, d2;
		while (iter1.hasNext() && iter2.hasNext()) {
			d1 = ((VariableTerm) iter1.next()).getDomain();
			d2 = (Domain) iter2.next();
			if (!(d1.getName().equals(d2.getName()))) { // TRICK! OLD: d1 != d2
				MSG_ERR = "Error: the domain of the function is a product domain"
						+ "or a concrete domain whose type-domain is a product domain, but domain components are not equal to the parameters domains"
						+ "Type mismatch between "
						+ d1.getName()
						+ " and "
						+ d2.getName();
				return false;
			}
		}
		return true;
	}

	public static boolean I10(FunctionInitialization f_init) {
		// check constraint I10:
		// initializedFunction.codomain.compatible(body.domain)
		TypeDomain TD = getTypeDomain(f_init.getInitializedFunction()
				.getCodomain());
		//TypeDomain BTD = getTypeDomain(f_init.getBody().getDomain());

		// special case: check if the domain of the function body is a
		// MapDomain; in this case
		// the compatibility between the function codomain and the body domain
		// is extablished
		// according to E20
		/* Disabled!
		if (BTD instanceof MapDomain) {
			return E20(f_init);
		}
		else */
		// normal case: I10
		 if (!(compatible(TD, f_init.getBody().getDomain()))) {
			MSG_ERR = "Error: The domain of the function body must be compatible with the function codomain "
					+ TD.getName() + ".";
			return false;
		}
		return true;
	}

	//Disabled for FunctionInitialization/definition in order to allow its use for
	//0-ary functions. n-ary functions can be defined/initialized also with a map term
	//by the use of the predefined "at" function defined in the standard library!
	/*public static boolean E20(FunctionInitialization f_init) {
		// E20: let M:Domain = body.domain in
		// if (M.oclIsTypeOf(MapDomain)) then
		// initializedFunction.domain->notEmpty() and
		// M.oclAsType(MapDomain).sourceDomain.compatible(initializedFunction.domain)
		// and
		// M.oclAsType(MapDomain).targetDomain.compatible(initializedFunction.codomain)
		// endif
		if (!body_ok(f_init.getInitializedFunction(), f_init.getBody()))
			return false;
		return true;
	}

	public static boolean E21(FunctionDefinition f_def) {
		// E21: like in E20
		if (!body_ok(f_def.getDefinedFunction(), f_def.getBody()))
			return false;
		return true;
	}
*/
	public static boolean body_ok(Function f, Term body) { // See E20
		Domain dom = f.getDomain();
		Domain cod = f.getCodomain();
		Domain td_body = body.getDomain();
		if (td_body instanceof MapDomain) {
			if (dom == null) {
				MSG_ERR = "Error: A map term cannot be used as body.";
				return false;
			}

			Domain M1 = ((MapDomain) td_body).getSourceDomain();
			Domain M2 = ((MapDomain) td_body).getTargetDomain();
			if (!(compatible(M1, dom) && compatible(M2, cod))) {
				MSG_ERR = "Error: The map term representing the function body is ill formed.";
				return false;
			}
		}
		return true;
	}

	// ----------------- FUNCTION DEFINITION --------------//
	public static boolean checkFunctionDefinition(FunctionDefinition f_def) {
		return B3(f_def) && B6(f_def) && B7_B8_B9(f_def) && B10(f_def);
				//&& E21(f_def);
	}

	public static boolean B3(FunctionDefinition f_def) {
		// check constraint B3: definedFunction.oclIsTypeOf(StaticFunction) or
		// definedFunction.oclIsTypeOf(DerivedFunction)
		if (!((f_def.getDefinedFunction() instanceof StaticFunction) || (f_def
				.getDefinedFunction() instanceof DerivedFunction))) {
			MSG_ERR = "Error: Only static/derived functions already declared in the ASM signature can be defined.";
			return false;
		}
		return true;
	}

	public static boolean B6(FunctionDefinition f_def) {
		// check constraint B6: variable->size() =
		// definedFunction.arity.oclAsType(Integer)
		if (f_def.getVariable().size() != f_def.getDefinedFunction().getArity()) {
			MSG_ERR = "Error: In a function definition, the number of formal "
					+ "parameters must be equal to the arity of the function to define.";
			return false;
		}
		return true;
	}

	public static boolean B10(FunctionDefinition f_def) {
		// check constraint B10:
		// definedFunction.codomain.compatible(body.domain)

		TypeDomain TD = getTypeDomain(f_def.getDefinedFunction().getCodomain());
		//TypeDomain BTD = getTypeDomain(f_def.getBody().getDomain());

		// special case: check if the domain of the function body is a
		// MapDomain; in this case
		// the compatibility between the function codomain and the body domain
		// is extablished
		// according to E21
		//Disabled!
		/* if (BTD instanceof MapDomain) {
			return E21(f_def);
		}
		else */
		// normal case: B10
		if (!(compatible(TD, f_def.getBody().getDomain()))) {
			MSG_ERR = "Error: The domain of the function body must be compatible with the function codomain "
					+ TD.getName() + ".";
			return false;
		}
		return true;
	}

	public static boolean B7_B8_B9(FunctionDefinition f_def) {
		// see constraints I7,I8,I9 of FunctionInitialization
		int size = f_def.getVariable().size();
		Domain D = f_def.getDefinedFunction().getDomain();

		// B7
		if (size == 1)
			if (f_def.getVariable().get(0).getDomain() != D) {
				MSG_ERR = "Error: The domain where the formal parameter takes its value"
						+ "must be equal to the domain of the function.";
				return false;
			} else
				return true; // case size == 1
		if (size > 1) {
			// I8
			if (D instanceof ProductDomain)
				return I8_I9aux(f_def.getVariable(), (ProductDomain) D);
			// I9
			else if ((D instanceof ConcreteDomain)
					&& (((ConcreteDomain) D).getTypeDomain() instanceof ProductDomain))
				return I8_I9aux(f_def.getVariable(),
						(ProductDomain) ((ConcreteDomain) D).getTypeDomain());
		}
		return true; // case size == 0
	}

	// --- AGENT INITIALIZATION ------//
	public static boolean checkAgentInitialization(AgentInitialization a) {
		return M3(a);
	}

	public static boolean M3(AgentInitialization a) {
		// check constraint M3:
		// the program is not empty and
		// the domain is the Agent Domain or a subset of it:
		// program->notEmpty() and (domain.oclIsTypeOf(AgentDomain) or
		// domain.oclIsTypeOf(ConcreteDomain) and
		// domain.oclAsType(ConcreteDomain).typeDomain.oclIsTypeOf(AgentDomain))

		if (a.getProgram() == null) {
			MSG_ERR = "Error: No rule is associated as program to agents "
					+ a.getDomain().getName() + ".";
			return false;
		}

		if (a.getDomain() instanceof AgentDomain)
			return true;

		if ((a.getDomain() instanceof ConcreteDomain)
				&& (((ConcreteDomain) a.getDomain()).getTypeDomain() instanceof AgentDomain))
			return true;

		MSG_ERR = "Error: the domain " + a.getDomain().getName()
				+ " is not the Agent Domain or a subset of it.";
		return false;

	}

	// -----------------------------------------------------------------------//
	// ----------------- Package ASMDefinitions ---------------------//
	// -----------------------------------------------------------------------//

	// -------------------------- DOMAIN query/operations
	// -----------------------//

	/**
	 * Computes a number expressing the compatibility between two domains.
	 * Two domains are compatible if and only if they are the same one or
	 * one is a subset of the other (i.e. a concrete domain of the other).
	 * More depth is the chain of subset declarations between the domains,
	 * lower is the compatibility.
	 *
	 * <br><br>NOTE ON IMPLEMENTATION
	 * <br>See the method <i>rating(List<Domain>, List<Domain>)</i> for the
	 * maximum value of the returned rating.
	 *
	 * @param given 
	 * 		the given domain
	 * @param another 
	 * 		the possible compatible domain
	 * @return 
	 * 		0 if the domains are the same one, -1 if they are incompatible,
	 * 		otherwise a positive number expressing the compatibility (higher is the
	 * 		number, lower is the compatibility).
	 */
	private static int compatibleRating(Domain given, Domain another) {
		// FIXME sometimes the method getAsmName() doesn't work.
		// This check tries to catch such situations.
		if (given == another) {
			return 0;
		}
		String name1 = given.getName();
		String name2 = another.getName();
		String module1 = Defs.getAsmName(given);
		String module2 = Defs.getAsmName(another);

		//The comparison between basic type domains is based on their names.
		if (given instanceof BasicTd) {
			if (another instanceof BasicTd) {
				return name1.equals(name2)? 0: -1;
			}
			return -1;
		} else if (given instanceof StructuredTd) {
			if (another instanceof StructuredTd) {
				return Defs.equals(another, given)? 0: -1;
			}
			return -1;
		} else if (given instanceof EnumTd) {
			if (another instanceof EnumTd) {
				return module1.equals(module2) && name1.equals(name2)? 0: -1;
			}
			return -1;
		} else if (given instanceof AbstractTd) {
			if (another instanceof AbstractTd) {
				return module1.equals(module2) && name1.equals(name2)? 0: -1;
			}
			return -1;
		}
		else if (given instanceof ConcreteDomain) {
			if (another instanceof ConcreteDomain) {
				//If they are two concrete domains, it is enough to check the name.
				return module1.equals(module2) && name1.equals(name2)? 0: -1;
			} else /*if (another instanceof AbstractTd)*/ {
				//we must check that the type domain of the concrete domain "given" is
				//equal to "another"
				TypeDomain given2 = ((ConcreteDomain) given).getTypeDomain();
				name1 = given2.getName();
				module1 = Defs.getAsmName(given2);
				// NOTE: the distance between the domains is now incremented by 1
				return module1.equals(module2) && name1.equals(name2)? 1: -1;
			}
//			return -1;
		}
		else if ( (given instanceof AnyDomain && name1.equals(SUPER_ANYDOMAIN)) ||
				(another instanceof AnyDomain && name2.equals(SUPER_ANYDOMAIN)))
				return 2; //Added by Patrizia for domain Any
		                   //FIXME Similarly to function declarations (that not use a rating algorithm for compatibility), also the other concrete domains should be treated!
		
		throw new RuntimeException("Unable to compare domains: "
				+ module1 + "::" + name1 + ", " + module2 + "::" + name2);
	}

	/**
	 * @see compatibleRating()
	 * 
	 */
	static int rating(Domain given, Domain another) {
		int r = compatibleRating(given, another);
		if (r == -1) {
			logger.debug("In rating. Domains " + given.getName() + " and " + another.getName() + " are incompatible. So we see if " + another.getName() + " and " + given.getName() + " are compatible.");
			r = compatibleRating(another, given);//why? see definition of compatible
			r *= 10;
		}
		return r;
	}

	/**
	 * Computes a number expressing the compatibility between two list of domains
	 * (e.g. the domains of the formal parameters of a macro declaration and
	 * the domains of the actual parameters of a macro invocation).
	 *
	 * <br><br>NOTE ON IMPLEMENTATION
	 * <br>The current implementation assumes the rating returned by the
	 * <i>rating()</i> method is between 0 and 99. In general, if the
	 * rating returned is between 0 and <i>x</i>, the value of the local variable
	 * <i>big100</i> must be <i>x+1</i>.
	 *
	 * @param givenLst 
	 * 		a list of domains
	 * @param anotherLst 
	 * 		another list of domains
	 * @return 
	 * 		0 if the domains are the same ones, -1 if they are incompatible,
	 * 		otherwise a positive number expressing the compatibility (higher is the
	 * 		number, lower is the compatibility).
	 * 
	 */
	static BigInteger rating(List<Domain> givenLst, List<Domain> anotherLst) {
		if (givenLst.size() != anotherLst.size()) {
			logger.debug("In rating. The lists have different sizes.");
			return BigInteger.valueOf(-1);
		}
		BigInteger total = BigInteger.valueOf(0);
		// NOTE
		// make sure the rating returned by compatibleRating() is less than 100,
		// otherwise increase the value of big100 so that it is greater than the
		// maximum rating
		BigInteger big100 = BigInteger.valueOf(100);
		Iterator<Domain> givenIt = givenLst.iterator();
		Iterator<Domain> anotherIt = anotherLst.iterator();
		while (givenIt.hasNext()) {
			Domain given = givenIt.next();
			Domain another = anotherIt.next();
			int rating = rating(given, another);
			logger.debug("In rating. Rating between " + given.getName() + " and " + another.getName() + " is " + rating);
			assert rating < big100.intValue();
			if (rating == -1) {
				return BigInteger.valueOf(-1);
			}
			BigInteger bigRating = BigInteger.valueOf(rating);
			// total = total * big100 + bigRating
			total = total.multiply(big100).add(bigRating);
		}
		logger.debug("In rating. Total rating is " + total.intValue());
		return total;
	}

	/**
	 * Compatible.
	 *
	 * @param self  e' il dominio della funzione - parametro formale
	 * @param d e' il dominio degli argomenti della funzione - parametro attuale
	 *
	 * @return true, if successful
	 */
	public static boolean compatible(Domain self, Domain d) {

		logger.debug("checking compatibility between " + self.getName() + " and " + d.getName());
		/*
		 * OCL SYNTAX: context Domain def: let compatible(Domain d): boolean =
		 */
		/*
		 * OCL SYNTAX: -- self and d are equals or d is Undef self = d or
		 * d.oclIsTypeOf(UndefDomain) or
		 */
		if ((self == d)
				|| (d instanceof UndefDomain)
				|| (self instanceof UndefDomain)
// modified Angelo 29/10/21
// any AnyDomain is compatible
				|| (d instanceof AnyDomain) 
				|| (self instanceof AnyDomain) 				
//				|| ((d instanceof AnyDomain) && d.getName().equals(SUPER_ANYDOMAIN))
//				|| ((self instanceof AnyDomain) && self.getName().equals(SUPER_ANYDOMAIN))
						) 
			return true;
		/*
		 * OCL SYNTAX: -- one is a ConcreteDomain (
		 * self.oclIsTypeOf(ConcreteDomain) and d.oclIsTypeOf(TypeDomain) and d =
		 * self.oclAsType(ConcreteDomain).typeDomain ) or (
		 * d.oclIsTypeOf(ConcreteDomain) and self.oclIsTypeOf(TypeDomain) and
		 * self = d.oclAsType(ConcreteDomain).typeDomain ) or
		 */
		if (self instanceof ConcreteDomain && d instanceof TypeDomain) {
			return compatible(d, ((ConcreteDomain) self).getTypeDomain());
		}
		if (d instanceof ConcreteDomain && self instanceof TypeDomain) {
			return compatible(self,((ConcreteDomain) d).getTypeDomain());
		}
		/*
		 * OCL SYNTAX: -- two PowersetDomain ( self.oclIsTypeOf(PowersetDomain)
		 * and d.oclIsTypeOf(PowersetDomain) and
		 * self.oclAsType(PowersetDomain).baseDomain.compatible(d.oclAsType(PowersetDomain).baseDomain))
		 * or
		 */
		if (self instanceof PowersetDomain
				&& d instanceof PowersetDomain)
			return compatible(((PowersetDomain) self).getBaseDomain(),
						((PowersetDomain) d).getBaseDomain());
		/*
		 * OCL SYNTAX: -- two ProductDomain ( self.oclIsTypeOf(ProductDomain)
		 * and d.oclIsTypeOf(ProductDomain) and (let size:Integer =
		 * self.oclAsType(ProductDomain).domains->sise() in size =
		 * d.oclAsType(ProductDomain).domains->sise() and
		 * Sequence{1..size}->forAll(i:Integer |
		 * self.oclAsType(ProductDomain).domains->at(i).compatible(d.oclAsType(ProductDomain).domains->at(i))
		 * endlet) )
		 */
		if ((self instanceof ProductDomain)
				&& (d instanceof ProductDomain)
				&& compatibleSubDomains((ProductDomain) self, (ProductDomain) d))
			return true;
		/*
		 * OCL SYNTAX: -- two SequenceDomain (self.oclIsTypeOf(SequenceDomain)
		 * and d.oclIsTypeOf(SequenceDomain) and
		 * self.oclAsType(SequenceDomain).domain.compatible(d.oclAsType(SequenceDomain).domain))
		 * or
		 */
		if ((self instanceof SequenceDomain)
				&& (d instanceof SequenceDomain)
				&& compatible(((SequenceDomain) self).getDomain(),
						((SequenceDomain) d).getDomain()))
			return true;
		/*
		 * OCL SYNTAX: -- two BagDomain (self.oclIsTypeOf(BagDomain) and
		 * d.oclIsTypeOf(BagDomain) and
		 * self.oclAsType(BagDomain).domain.compatible(d.oclAsType(BagDomain).domain))
		 * or
		 */
		if ((self instanceof BagDomain)
				&& (d instanceof BagDomain)
				&& compatible(((BagDomain) self).getDomain(), ((BagDomain) d)
						.getDomain()))
			return true;
		/*
		 * OCL SYNTAX: -- two MapDomain (self.oclIsTypeOf(MapDomain) and
		 * d.oclIsTypeOf(MapDomain) and
		 * self.oclAsType(MapDomain).sourceDomain.compatible(d.oclAsType(MapDomain).sourceDomain)
		 * and
		 * self.oclAsType(MapDomain).targetDomain.compatible(d.oclAsType(MapDomain).targetDomain))
		 */
		if ((self instanceof MapDomain)
				&& (d instanceof MapDomain)
				&& compatible(((MapDomain) self).getSourceDomain(),
						((MapDomain) d).getSourceDomain())
				&& compatible(((MapDomain) self).getTargetDomain(),
						((MapDomain) d).getTargetDomain()))
			return true;
		// TRICK by Patrizia
		if (self.getName().equals(d.getName()))
			return true;

		return false;
	}

	private static boolean compatibleSubDomains(ProductDomain self,
			ProductDomain d) {
		int size = self.getDomains().size();
		if (size != d.getDomains().size())
			return false;
		Iterator<?> dom1_iter = self.getDomains().iterator();
		Iterator<?> dom2_iter = d.getDomains().iterator();
		while (dom1_iter.hasNext()) {
			if (!compatible((Domain) dom1_iter.next(), (Domain) dom2_iter
					.next()))
				return false;
		}
		return true;
	}

	/**
	 * resitituisce il type domain di un domain
	 * se è un concrete return the type domain altrimenti se stesso
	 */

	public static TypeDomain getTypeDomain(Domain d) {
		if (d instanceof ConcreteDomain)
			return ((ConcreteDomain) d).getTypeDomain();
		else
			return (TypeDomain) d;
	}



	public static void printListOfDomains(List<TypeDomain> list, PrintStream out) {
		for (Iterator<TypeDomain> i = list.iterator(); i.hasNext();)
			out.println(i.next().getName() + " ");
	}

	/*
	 * Modificata che prende due domini e non due type domain, un type domain
	 * puo' essere un product di domain confronta il dominio formale con
	 * l'attuale e nel caso sostituisce Any se necessario se i due sono non
	 * compatibile restituisce false
	 *
	 * vedi definizione su report
	 */
	public static boolean compareFixingAnyDomain(Domain formalDom,
			Domain actualDom, HashMap<String, Domain> genericDomValue) {
		logger.debug("comparing formal " + formalDom.getName() +  "(" +formalDom.getClass().getSimpleName() +")"
				   +" with actual " + actualDom.getName() +  "(" +actualDom.getClass().getSimpleName() + ")");
		// System.out.println("hash: " + genericDomValue.toString());
		if (formalDom == actualDom)
			return true;

		// one is a Concrete domain with type T an the other is TypeDomain T
		if (formalDom instanceof ConcreteDomain) {
			logger.debug(formalDom + " instanceof ConcreteDomain");
			ConcreteDomain cfd = (ConcreteDomain) formalDom;
			if (actualDom instanceof TypeDomain) {
				logger.debug(actualDom + " instanceof TypeDomain");
				return compareFixingAnyDomain(cfd.getTypeDomain(),actualDom,genericDomValue);
			}
			else
				return false;
		}
		// FIXME 1/4/2009 this code must be rewritten!!!!
		/*
		if (formalDom instanceof AnyDomain && actualDom instanceof ConcreteDomain) {
			genericDomValue.put(formalDom.getName(), actualDom);
			return true;
		}
		*/
		if (actualDom instanceof ConcreteDomain) {
			logger.debug(actualDom.getName() + " instanceof ConcreteDomain");
			ConcreteDomain cad = (ConcreteDomain) actualDom;
			//PA: 30/07/2014
			//TODO check
			if (cad.getTypeDomain() instanceof AgentDomain && formalDom instanceof AnyDomain) {
				genericDomValue.put(formalDom.getName(), actualDom);
				return true;
			}
			else if (formalDom instanceof TypeDomain) {
				logger.debug(formalDom.getName() + " instanceof TypeDomain");
				return compareFixingAnyDomain(formalDom,cad.getTypeDomain(),genericDomValue);
			}
			else
				return false;
		}

		// TWO PRODUCT DOMAINS
		if (formalDom instanceof ProductDomain && actualDom instanceof ProductDomain) {
			// both product domains
			List<Domain> formalDom_list = ((ProductDomain) formalDom).getDomains();
			List<Domain> actualDom_list = ((ProductDomain) actualDom).getDomains();
			// For debugging
			// System.out.println("Printing Prod lists ...");
			// printListOfDomains(formalDom_list,System.out);
			// printListOfDomains(actualDom_list,System.out);

			// mi assicuro che il num dei domini del product ??? uguale al num
			// degli elementi della tupla
			if (formalDom_list.size() != actualDom_list.size()){
				logger.debug("formal size "+ formalDom_list.size() + "!= actual size " +actualDom_list.size());
				return false;
			}
			Iterator<Domain> formalDom_iter = formalDom_list.listIterator();
			Iterator<Domain> actualDom_iter = actualDom_list.listIterator();
			while (formalDom_iter.hasNext()) {
				// domain
				Domain fd_i = formalDom_iter.next();
				Domain ad_i = actualDom_iter.next();
				if (!compareFixingAnyDomain(fd_i, ad_i, genericDomValue))
					return false;
			}
			logger.debug("product domain :"
					+ ((ProductDomain) formalDom).getName() + " found");
			return true;
		} else if (formalDom instanceof SequenceDomain
				&& actualDom instanceof SequenceDomain)
			return compareFixingAnyDomain(((SequenceDomain) formalDom)
					.getDomain(), ((SequenceDomain) actualDom).getDomain(),
					genericDomValue);

		else if (formalDom instanceof PowersetDomain
				&& actualDom instanceof PowersetDomain)
			return compareFixingAnyDomain(((PowersetDomain) formalDom)
					.getBaseDomain(), ((PowersetDomain) actualDom)
					.getBaseDomain(), genericDomValue);

		else if (formalDom instanceof BagDomain
				&& actualDom instanceof BagDomain)
			return compareFixingAnyDomain(((BagDomain) formalDom).getDomain(),
					((BagDomain) actualDom).getDomain(), genericDomValue);

		else if (formalDom instanceof MapDomain
				&& actualDom instanceof MapDomain)
			return (compareFixingAnyDomain(((MapDomain) formalDom)
					.getSourceDomain(), ((MapDomain) actualDom)
					.getSourceDomain(), genericDomValue) && (compareFixingAnyDomain(
					((MapDomain) formalDom).getTargetDomain(),
					((MapDomain) actualDom).getTargetDomain(), genericDomValue)));

		else if (actualDom instanceof AnyDomain)
			return true;

		else if (formalDom instanceof AnyDomain
				&& formalDom.getName().equals(SUPER_ANYDOMAIN))
			return true;

		else if (formalDom instanceof AnyDomain
				&& genericDomValue.get(formalDom.getName()) == null) {
			genericDomValue.put(formalDom.getName(), actualDom);
			return true;
		}

		else if (formalDom instanceof AnyDomain
				&& ((TypeDomain) genericDomValue.get(formalDom.getName())) != null) {
			// TRICK "compare by name"
			// NEW: and by "compatibility" for domains made of concrete domains which are
			// compatible in this context although they have different names
			//
			logger.debug("compare by name Any and TD");
			TypeDomain TD = (TypeDomain) genericDomValue.get(formalDom
					.getName());
			logger.debug("TD= " + TD.getName());
			logger.debug("ActualDom= " + actualDom.getName());
			return (actualDom.equals(TD) || actualDom.getName().equals(
					TD.getName())
					|| compatible(TD,actualDom));
		}

		else // formalDom e' un dominio ben preciso
		{ // TRICK "compare domains by name"
			if (actualDom.equals(formalDom)) {
				logger.debug("same domain");
				return true;
			} else if (actualDom.getName().equals(formalDom.getName())) {
				logger.debug("same name - to check");
				return true;
			} else {
				return false;
			}
		}
	}

	/*
	 * public static boolean compareFixingAnyDomain(Domain formalDom, Domain
	 * actualDom,HashMap<String,Domain> genericDomValue) {
	 * System.out.println("comparing " + formalDom.getName() + " with "+
	 * actualDom.getName()); System.out.println("hash: " +
	 * genericDomValue.toString()); // TWO PRODUCT DOMAINS if(formalDom
	 * instanceof ProductDomain && actualDom instanceof ProductDomain) { // only
	 * for debugging Object o1 = null; Object o2 = null; // both product domains
	 * List<Domain> formalDom_list = ((ProductDomain)formalDom).getDomains();
	 * List<Domain> actualDom_list = ((ProductDomain)actualDom).getDomains();
	 * //mi assicuro che il num dei domini del product ??? uguale al num degli
	 * elementi della tupla if(formalDom_list.size() != actualDom_list.size())
	 * return false; Iterator<Domain> formalDom_iter =
	 * formalDom_list.listIterator(); Iterator<Domain> actualDom_iter =
	 * actualDom_list.listIterator(); while(formalDom_iter.hasNext()) { o1 =
	 * formalDom_iter.next(); o2 = actualDom_iter.next(); Domain fd_i; Domain
	 * ad_i; // this trick is necessary because I get many errors of type
	 * ClassCast Exception // I use now getByMofID to get the right class of the
	 * i-th domain try { fd_i = (Domain) o1; } catch
	 * (java.lang.ClassCastException cce){
	 * System.err.println("ClassCastException in compareFixingAnyDomain: " +
	 * cce.getMessage()); if (o1 != null) System.err.println("i th formal domain
	 * :"+ o1.toString() + " of type "+ o1.getClass()); else
	 * System.err.println("i th formal domain is null");
	 * //System.err.println("stack trace:"); //List stack =
	 * java.util.Arrays.asList(cce.getStackTrace()); //for (Object o: stack)
	 * System.err.println(o); // trick to build the right object fd_i =
	 * (Domain)org.netbeans.api.mdr.MDRManager.getDefault().getDefaultRepository().getByMofId(o1.toString());
	 * System.err.println("by mofid:"+fd_i.getClass()); } try { ad_i = (Domain)
	 * o2; } catch (java.lang.ClassCastException cce){
	 * System.err.println("ClassCastException in compareFixingAnyDomain: " +
	 * cce.getMessage()); if (o2 != null) System.err.println("i th actual domain
	 * :"+ o2.toString() + " of type "+ o2.getClass()); else
	 * System.err.println("i th actual domain is null");
	 * //System.err.println("stack trace:"); //List stack =
	 * java.util.Arrays.asList(cce.getStackTrace()); //for (Object o: stack)
	 * System.err.println(o); // trick to build the right object ad_i =
	 * (Domain)org.netbeans.api.mdr.MDRManager.getDefault().getDefaultRepository().getByMofId(o2.toString());
	 * System.err.println("by mofid:"+ad_i.getClass()); } if
	 * (!compareFixingAnyDomain(fd_i,ad_i,genericDomValue)) return false; }
	 * System.err.println("product domain :" +
	 * ((ProductDomain)formalDom).getName()+" found"); return true; } else
	 * if(formalDom instanceof SequenceDomain && actualDom instanceof
	 * SequenceDomain) return
	 * compareFixingAnyDomain(((SequenceDomain)formalDom).getDomain(),((SequenceDomain)actualDom).getDomain(),genericDomValue);
	 *
	 * else if(formalDom instanceof PowersetDomain && actualDom instanceof
	 * PowersetDomain) return
	 * compareFixingAnyDomain(((PowersetDomain)formalDom).getBaseDomain(),((PowersetDomain)actualDom).getBaseDomain(),genericDomValue);
	 *
	 * else if(formalDom instanceof BagDomain && actualDom instanceof BagDomain)
	 * return
	 * compareFixingAnyDomain(((BagDomain)formalDom).getDomain(),((BagDomain)actualDom).getDomain(),genericDomValue);
	 *
	 * else if(formalDom instanceof MapDomain && actualDom instanceof MapDomain)
	 * return
	 * (compareFixingAnyDomain(((MapDomain)formalDom).getSourceDomain(),((MapDomain)actualDom).getSourceDomain(),genericDomValue)&&
	 * (compareFixingAnyDomain(((MapDomain)formalDom).getTargetDomain(),((MapDomain)actualDom).getTargetDomain(),genericDomValue)));
	 *
	 * else if (actualDom instanceof AnyDomain) return true;
	 *
	 * else if (formalDom instanceof AnyDomain &&
	 * formalDom.getName().equals(SUPER_ANYDOMAIN)) return true;
	 *
	 * else if(formalDom instanceof AnyDomain &&
	 * genericDomValue.get(formalDom.getName())==null) {
	 * genericDomValue.put(formalDom.getName(),actualDom); return true; }
	 *
	 * else if(formalDom instanceof AnyDomain &&
	 * ((TypeDomain)genericDomValue.get(formalDom.getName()))!=null) return
	 * actualDom == (TypeDomain)genericDomValue.get(formalDom.getName());
	 *
	 * else //formalDom ??? un dominio ben preciso return
	 * actualDom.equals(formalDom);
	 *  }
	 */

	// ----------- FUNCTION -----------//
	public static boolean checkFunction(Function f) {
		// check function arity
		if (f.getArity() != evalArity(f)) {
			MSG_ERR = "Error: Wrong function arity.";
			return false;
		}
		return true;
	}

	// Compute the function arity according to the scheme:
	// F1: if domain->isEmpty() then arity.oclAsType(Integer)= 0
	// F2: else if domain.oclIsTypeOf(ProductDomain)
	// then arity.oclAsType(Integer)=
	// domain.oclAsType(ProductDomain).domain->size()
	// F3: else arity.oclAsType(Integer) = 1
	// endif endif endif
	public static int evalArity(Function func) {
		Domain d = func.getDomain();
		if (d == null)
			return 0;
		else if (d instanceof ProductDomain)
			return ((ProductDomain) d).getDomains().size();
		/*
		 * else if ((d instanceof ConcreteDomain) && (((ConcreteDomain)
		 * d).getTypeDomain() instanceof ProductDomain)) return ((ProductDomain)
		 * ((ConcreteDomain) d).getTypeDomain()).getDomains().size();
		 */
		else
			return 1;
	}

	// ----------------- RULE DECLARATION --------------//
	public static boolean checkRuleDeclaration(RuleDeclaration r) {
		// check constraint R16: arity.oclAsType(Integer) = variable->size()
		if (r.getArity() != r.getVariable().size()) {
			MSG_ERR = "Error: Wrong rule declaration arity.";
			return false;
		}
		return true;
	}

	// ----------------- AXIOM --------------//
	public static boolean checkAxiom(Invariant a) {
		return B4(a) && B5(a);
	}

	public static boolean B4(Invariant a) {
		// check constraint B4: body.domain.oclIsTypeOf(BooleanDomain)
		if (!isBoolean(a.getBody())) {
			MSG_ERR = "Error: The expression of an invariant must yield a boolean value, when valuated in a state of the ASM.";
			return false;
		}
		return true;
	}

	public static boolean B5(Invariant a) {
		// check constraint B5: constrainedRule->notEmpty() or
		// constrainedFunction->notEmpty() or constrainedDomain->notEmpty()
		if (!(a.getConstrainedRule() != null
				|| a.getConstrainedFunction() != null || a
				.getConstrainedDomain() != null)) {
			MSG_ERR = "Error: An invariant must refer to at least one rule, one function or one domain.";
			return false;
		}
		return true;
	}

	// -----------------------------------------------------------------------//
	// -------------------------- RULES -----------------------------//
	// --------------------------------------------------------------//

	// --------------------- BASIC RULES -----------------------//
	// ////////////////////////////////////////
	// --- UPDATE RULE--- //

	public static boolean checkUpdateRule(UpdateRule r) {
		return (R1(r) && R2(r));
	}

	public static boolean R1(UpdateRule r) {
		// Check constraint R1: location.compatible(updatingTerm)
		Term location = r.getLocation();
		Term updatingTerm = r.getUpdatingTerm();
		if (!(compatible(location, updatingTerm))) {
			MSG_ERR = "Error: In an update rule, the term on the right-hand side ("+ AsmetaTermPrinter.getAsmetaTermPrinter(false).visit(updatingTerm) + ") must be compatible "
					+ "with the one on the left-hand side (" + AsmetaTermPrinter.getAsmetaTermPrinter(false).visit(location) + ").";
			return false;
		}
		return true;
	}

	public static boolean R2(UpdateRule r) {
		// Check constraint R2:
		// (location.oclIsTypeOf(LocationTerm) and not
		// location.oclAsType(LocationTerm).function.oclIsTypeOf(MonitoredFunction))or
		// (location.oclIsTypeOf(VariableTerm) and
		// location.oclAsType(VariableTerm).kind = VariableKind::locationVar )
		if (((r.getLocation() instanceof LocationTerm) && (!(((LocationTerm) r
				.getLocation()).getFunction() instanceof MonitoredFunction)))
				|| ((r.getLocation() instanceof VariableTerm) && ((VariableTerm) r
						.getLocation()).getKind() == VariableKind.LOCATION_VAR))
			return true;
		MSG_ERR = "Error: In an update rule, the left-hand side must be either a location term (with a non monitored function) or "
				+ "a location variable term";
		return false;
	}

	// --- CONDITIONAL RULE --- //
	public static boolean checkConditionalRule(ConditionalRule r) {
		return R3(r);
	}

	public static boolean R3(ConditionalRule r) {
		// Check constraint R3: guard.domain.oclIsTypeOf(BooleanDomain)
		if (!isBoolean(r.getGuard())) {
			MSG_ERR = "Error: In a conditional rule, the guard is a term representing a boolean condition.";
			return false;
		}
		return true;
	}

	// --- EXTEND RULE --- //
	public static boolean checkExtendRule(ExtendRule r) {
		return R5(r) && R6(r);
	}

	public static boolean R5(ExtendRule r) {
		// Check constraint R5: The extended domain is a Concrete dynamic domain
		// with
		// type domain an abstract TD or a dynamic abstract TD
		// inv R5: (extendedDomain.oclIsTypeOf(ConcreteDomain) and
		// extendedDomain.oclAsType(ConcreteDomain).isDynamic = true and
		// extendedDomain.oclAsType(ConcreteDomain).typeDomain.oclIsTypeOf(AbstractTD))
		// or (extendedDomain.oclIsTypeOf(AbstractTD) and
		// extendedDomain.oclAsType(AbstractTD).isDynamic = true)
		if ((r.getExtendedDomain() instanceof ConcreteDomain)
				&& ((ConcreteDomain) r.getExtendedDomain()).getIsDynamic()
				&& (((ConcreteDomain) r.getExtendedDomain()).getTypeDomain() instanceof AbstractTd))
			return true;
		else if ((r.getExtendedDomain() instanceof AbstractTd)
				&& (((AbstractTd) r.getExtendedDomain()).getIsDynamic()))
			return true;
		else {
			MSG_ERR = "Error: In an extend rule, the domain to extend must be dynamic, and it must be a concrete domain  "
					+ "subsetting an abstract TD or an abstract TD.";
			return false;
		}
	}

	public static boolean R6(ExtendRule r) {
		// Check constraint R6: boundVar->forAll(v:VariableTerm | v.domain =
		// extendedDomain
		// and v.kind=VariableKind::logicalVar)
		Iterator<?> iter_var = r.getBoundVar().iterator();
		VariableTerm v;
		while (iter_var.hasNext()) {
			v = (VariableTerm) iter_var.next();
			if (v.getDomain() != r.getExtendedDomain()) {
				MSG_ERR = "Error: In an extend rule, variables must range in the extended domain.";
				return false;
			}
			if (!v.getKind().equals(VariableKind.LOGICAL_VAR)) {
				MSG_ERR = "Error: In a ExtendRule, variables must be logical.";
				return false;
			}
		}
		return true;
	}

	// --- CHOOSE RULE --- //
	public static boolean checkChooseRule(ChooseRule r) {
		return R10(r) && R9(r) && R7_R8(r);
	}

	public static boolean R7_R8(ChooseRule r) {
		// Check constraints R7, R8:
		// R7: Sequence{1..variable->size()}-> forAll(i:Integer |
		// ranges->at(i).domain.oclIsTypeOf(PowersetDomain)
		// R8: and variable->at(i).domain =
		// ranges->at(i).domain.oclAsType(PowersetDomain).baseDomain)
		Iterator<?> iter1 = r.getVariable().iterator();
		Iterator<?> iter2 = r.getRanges().iterator();
		VariableTerm v;
		Term t;
		while (iter1.hasNext()) {
			v = (VariableTerm) iter1.next();
			t = (Term) iter2.next();
			if (!(OCL_Checker.getTypeDomain(t.getDomain()) instanceof PowersetDomain)) {
				MSG_ERR = "Error: The domain of the variables ranges must be a PowersetDomain.";
				return false;
			}
			Domain bd = ((PowersetDomain) t.getDomain()).getBaseDomain();
			if (v.getDomain() != bd) {
				MSG_ERR = "Error: The domain of the variables ranges must be a PowersetDomain"
						+ "whose base domain must be equal to the variable type domain."
						+ "Type mismatch between"
						+ v.getDomain().getName()
						+ "and" + bd.getName();
				;
				return false;
			}
		}
		return true;
	}

	public static boolean R9(ChooseRule r) {
		// R9: guard.domain.oclIsTypeOf(BooleanDomain)
		if (!isBoolean(r.getGuard())) {
			MSG_ERR = "Error: In a choose rule, the guard is a term representing a boolean condition.";
			return false;
		}
		return true;
	}

	public static boolean R10(ChooseRule r) {
		// R10: variable->size() = ranges->size()
		if (r.getVariable().size() != r.getRanges().size()) {
			MSG_ERR = "Error: In a choose rule, the number of variables must be equal to the number of terms.";
			return false;
		}
		return true;
	}

	// --- FORALL RULE --- //
	public static boolean checkForallRule(ForallRule r) {
		return R13(r) && R14(r) && R11_R12(r);
	}

	public static boolean R11_R12(ForallRule r) {
		// Check constraint R11_R12: Sequence{1..variable->size()}->
		// forAll(i:Integer |ranges->at(i).domain.oclIsTypeOf(PowersetDomain)
		// and variable->at(i).domain =
		// ranges->at(i).domain.oclAsType(PowersetDomain).baseDomain )
		Iterator<VariableTerm> iter1 = r.getVariable().iterator();
		Iterator<Term> iter2 = r.getRanges().iterator();
		VariableTerm v;
		Term t;
		while (iter1.hasNext()) {
			v = iter1.next();
			t = iter2.next();
			if (!(t.getDomain() instanceof PowersetDomain)) {
				MSG_ERR = "Error: The domain of the variables ranges must be a PowersetDomain.";
				return false;
			}
			Domain bd = ((PowersetDomain) t.getDomain()).getBaseDomain();
			if (v.getDomain() != bd) {
				MSG_ERR = "Error: The domain of the variables ranges must be a PowersetDomain"
						+ "whose base domain must be equal to the variable type domain."
						+ "Type mismatch between"
						+ v.getDomain().getName()
						+ "and" + bd.getName();
				return false;
			}
		}
		return true;
	}

	public static boolean R13(ForallRule r) {
		// Check constraint R13: guard.domain.oclIsTypeOf(BooleanDomain)
		if (!isBoolean(r.getGuard())) {
			MSG_ERR = "Error: In a forall rule, the guard is a term representing a boolean condition.";
			return false;
		}
		return true;
	}

	public static boolean R14(ForallRule r) {
		// Check constraint R14: variable->size() = ranges->size()
		if (r.getVariable().size() != r.getRanges().size()) {
			MSG_ERR = "Error: In a forall rule, the number of variables must be equal to the number of terms.";
			return false;
		}
		return true;
	}

	// --- LET RULE --- //
	public static boolean checkLetRule(LetRule r) {
		return R15(r);
	}

	public static boolean R15(LetRule r) {
		// R15: initExpression->size() = variable->size() and
		// Sequence{1..variable->size()}->
		// forAll(i:Integer| variable->at(i).domain =
		// initExpression->at(i).domain) and
		// -- every veriable must be a logical variable
		// variable->at(i).kind = VariableKind::logicalVar

		if (r.getInitExpression().size() != r.getVariable().size()) {
			MSG_ERR = "Error: In a let-rule, the number of variables must be equal to the number of initialization terms."+
					" variables: " + r.getVariable().size() + r.getVariable().get(0).getName()+ " init terms: "+ r.getInitExpression().size();
			return false;
		}
		Iterator<?> iter1 = r.getVariable().iterator();
		Iterator<?> iter2 = r.getInitExpression().iterator();
		VariableTerm v;
		Term expr;
		while (iter1.hasNext()) {
			v = (VariableTerm) iter1.next();
			expr = (Term) iter2.next();
			if (v.getDomain() != expr.getDomain()) {
				MSG_ERR = "Error: In a let-rule, the type-domain associated to each variable"
						+ "must be equal to the one associated to the corresponding initialization term."
						+ "Type mismatch between"
						+ v.getDomain().getName()
						+ "and" + expr.getDomain().getName();
				return false;
			}
			if (v.getKind() != VariableKind.LOGICAL_VAR 
					&& v.getKind() != VariableKind.LOCATION_VAR) {
				// FIXME
				// "&& v.getKind() != VariableKind.LOCATION_VAR" added
				// 1 August 2008 by acarioni
				MSG_ERR = "Error: In a let-rule, each variable"
						+ "must be logical." + "Variable " + v.getName()
						+ " isn't logical.";
				return false;
			}
		}
		return true;
	}

	// --- MACROCALL RULE --- //
	public static boolean checkMacroCallRule(MacroCallRule r) {
		return R17(r) && R18_R19(r);
	}

	public static boolean R17(MacroCallRule r) {
		// Check constraint R17: parameter->size() =
		// calledMacro.arity.oclAsType(Integer)
		if (r.getParameters().size() != r.getCalledMacro().getArity()) {
			MSG_ERR = "Error: In the application of a named macro rule, "
					+ "the number of actual parameters must be equal to the arity of the rule to apply.";
			return false;
		}
		return true;
	}

	public static boolean R18_R19(MacroCallRule r) {
		// Check constraint R18_R19:
		// let arity:Integer = calledMacro.arity.oclAsType(Integer) in
		// if arity > 0 then Sequence{1..arity}->forAll(i:Integer |
		// R18: calledMacro.variable->at(i).compatible(parameter->at(i))
		// R19: calledMacro.variable->at(i).kind=VariableKind::locationVar
		// implies
		// ( (parameters->at(i)->oclIsTypeOf(LocationTerm) and
		// parameters->at(i).oclAsType(LocationTerm).function.oclIsTypeOf(MonitoredFunction))
		// or (parameters->at(i)->oclIsTypeOf(VariableTerm) and
		// parameters->at(i)->oclAsType(VariableTerm).kind =
		// VariableKind::locationVar))
		if (r.getCalledMacro().getArity() > 0) {
			if (!compareParamTD_Rule(r.getParameters(), r.getCalledMacro()
					.getVariable()))
				return false;
		}
		return true;
	}

	// ---------------For MacroCallRule and TurboCallRule ---------------
	public static boolean compareParamTD_Rule(List<?> actualParamList,
			List<?> formalParamList) {
		Iterator<?> actualIter = actualParamList.iterator();
		Iterator<?> formalIter = formalParamList.iterator();
		Term actualPar;
		VariableTerm formalPar;
		while (formalIter.hasNext()) {
			actualPar = (Term) actualIter.next();
			formalPar = (VariableTerm) formalIter.next();
			// check compatibility
			if (!compatible(actualPar.getDomain(), formalPar.getDomain())) {
				MSG_ERR = "Error: Wrong type-domain of arguments."
						+ "Type mismatch between"
						+ actualPar.getDomain().getName() + "and"
						+ formalPar.getDomain().getName();
				return false;
			}
			// If a formal parameter of a macro[turbo] rule declaration is a
			// location variable,
			// then it can be replaced only by an actual parameter which is
			// either a location
			// term (with a non monitored function) or a location variable
			// There is an ** exception **! If the actual parameter is a fresh (logical) variable
			// it is turned in a location variable!!
			if (formalPar.getKind() == VariableKind.LOCATION_VAR)
				if (!((actualPar instanceof LocationTerm && !(((FunctionTerm) actualPar)
						.getFunction() instanceof MonitoredFunction))
					   || (actualPar instanceof VariableTerm && ((VariableTerm) actualPar)
						.getKind() == VariableKind.LOCATION_VAR)
						//The ** exception **!!!
					   || (actualPar instanceof VariableTerm && ((VariableTerm) actualPar)
								.getKind() == VariableKind.LOGICAL_VAR) )

				) {
					MSG_ERR = "Error: If a formal parameter of a macro [turbo] rule declaration is a location variable,"
							+ "it can be replaced only by an actual parameter which is either a location term or a location variable.";
					return false;
				  }
			    else if ( actualPar instanceof VariableTerm && ((VariableTerm) actualPar)
						.getKind() == VariableKind.LOGICAL_VAR )
			      {
                    //The ** exception **!!!
			    	//the actual logical variable is turned in a location variable!!
			    	((VariableTerm) actualPar).setKind(VariableKind.LOCATION_VAR);
			    	logger.debug("\t\t\t"+ ((VariableTerm) actualPar).getName()+" updated: TD=" + ((VariableTerm) actualPar).getDomain().getName()+", kind="+((VariableTerm) actualPar).getKind().toString());
			      }
		}
		return true;
	}

	// --------------------- TURBO RULES -----------------------//

	// --- TURBO CALL RULE --- //
	public static boolean checkTurboCallRule(TurboCallRule r) {
		return U1(r) && U2_U3(r);
	}

	public static boolean U1(TurboCallRule r) {
		// Check constraint U1: parameter->size() =
		// calledRule.arity.oclAsType(Integer)
		if (r.getParameters().size() != r.getCalledRule().getArity()) {
			MSG_ERR = "Error: In the application of a named turbo rule, "
					+ "the number of actual parameters must be equal to the arity of the rule to apply.";
			return false;
		}
		return true;
	}

	public static boolean U2_U3(TurboCallRule r) {
		// Check constraint U2_U3:
		// let arity:Integer = calledRule.arity.oclAsType(Integer) in
		// arity > 0 implies Sequence{1..arity}->forAll(i:Integer |
		// U2: calledRule.variable->at(i).compatible(parameters->at(i))) and
		// U3: (calledRule.variable->at(i).kind=VariableKind::locationVar
		// implies
		// (parameters->at(i)->oclIsTypeOf(LocationTerm) or (
		// parameters->at(i)->oclIsTypeOf(VariableTerm)
		// and parameters->at(i)->oclAsType(VariableTerm).kind =
		// VariableKind::locationVar)) )
		if (r.getCalledRule().getArity() > 0) {
			if (!compareParamTD_Rule(r.getParameters(), r.getCalledRule()
					.getVariable()))
				return false;
		}
		return true;
	}

	// --- TRY CATCH RULE ---
	public static boolean checkTryCatchRule(TryCatchRule r) {
		return U4(r);
	}

	public static boolean U4(TryCatchRule r) {

		// check U4: location->forAll(t:Term | t.oclIsTypeOf(LocationTerm) or
		// ( oclIsTypeOf(VariableTerm) and
		// t.oclAsTypeOf(VariableTerm).kind=VariableKind::locationVar))
		for (Term t : (Collection<Term>) r.getLocation()) {
			if (!((t instanceof LocationTerm)
					|| ((t instanceof FunctionTerm) && ((FunctionTerm) t)
							.getFunction() instanceof DynamicFunction) || ((t instanceof VariableTerm) && ((VariableTerm) t)
					.getKind().equals(VariableKind.LOCATION_VAR)))) {
				MSG_ERR = "Error: In a try-catch rule, the location set contains only location variables or location terms";
				return false;
			}
		}
		return true;
	}

	// --- TURBO RETURN RULE --- //
	public static boolean checkTurboReturnRule(TurboReturnRule r) {
		// !!! Ma perch� location � opzionale, lasciando la possibilit� che sia
		// "null"?
		// location->notEmpty() implies
		// U5: forAll(t:Term | t.oclIsTypeOf(LocationTerm) or
		// ( t.oclIsTypeOf(VariableTerm) and
		// t.oclAsTypeOf(VariableTerm).kind=VariableKind::locationVar)) )
		// U6: and
		// (location.domain.compatible(updateRule.calledRule.resultType))

		if (r.getLocation() != null)
			return U5(r) && U6(r);
		else
			return true;// !!!!!E' giusto???
	}

	public static boolean U5(TurboReturnRule r) {
		// location->notEmpty() implies
		// U5: location.oclIsTypeOf(LocationTerm) or
		// (location.oclIsTypeOf(VariableTerm) and
		// location.oclAsTypeOf(VariableTerm).kind=VariableKind::locationVar)) )
		if (!((r.getLocation() instanceof LocationTerm) || ((r.getLocation() instanceof VariableTerm) && ((VariableTerm) r
				.getLocation()).getKind().equals(
				VariableKind.LOCATION_VAR)))) {
			MSG_ERR = "Error: In a turbo rule with return value, the location in which to store the return value"
					+ "can be either a location variable term or a location term.";
			return false;
		}
		return true;
	}

	public static boolean U6(TurboReturnRule r) {
		// U6: (location.domain.compatible(updateRule.calledRule.resultType))
		if (!compatible(r.getLocation().getDomain(), r.getUpdateRule()
				.getCalledRule().getResultType())) {
			MSG_ERR = "Error: In a turbo rule with return value, the domain of the location in which to store the intended"
					+ "return value must be compatible with the domain of the returned value. "
					+ "Type mismatch between"
					+ r.getLocation().getDomain().getName()
					+ "and"
					+ r.getUpdateRule().getCalledRule().getResultType()
							.getName();
			return false;
		}
		return true;
	}

	// --- TURBO LOCAL STATE RULE ---
	public static boolean checkTurboLocalStateRule(TurboLocalStateRule r) {
		return U8(r);
	}

	public static boolean U8(TurboLocalStateRule r) {
		// check U8: localFunction->size() = init->size()
		if (r.getLocalFunction().size() != r.getInit().size()) {
			MSG_ERR = "Error: In a turbo rule with local state, The number of functions declared locally must be equal to the number of the initializing rules.";
			return false;
		}
		return true;
	}

	// --------------------- DERIVED RULES -----------------------//

	// --- CASE RULE --- //
	public static boolean checkCaseRule(CaseRule r) {
		return K1(r) && K2(r);
	}

	public static boolean K1(CaseRule r) {
		// Check constraint K1: caseTerm->size() = caseBranches->size()
		if (r.getCaseTerm().size() != r.getCaseBranches().size()) {
			MSG_ERR = "Error: In a case rule, the number of case terms must be equal to the number of case rules.";
			return false;
		}
		return true;
	}

	public static boolean K2(CaseRule r) {
		// Check constraint K2: caseTerm->forAll(t:Term |term.compatible(t))
		Iterator<?> iter = r.getCaseTerm().iterator();
		Term t;
		while (iter.hasNext()) {
			t = (Term) iter.next();
			if (!compatible(t, r.getTerm())) {
				MSG_ERR = "Error: In a case rule, every term of the case-clauses must be compatible to the main term.";
				return false;
			}
		}
		return true;
	}

	// --- ITERATIVE WHILE RULE --- //
	public static boolean checkIterativeWhileRule(IterativeWhileRule r) {
		return K3(r);
	}

	public static boolean K3(IterativeWhileRule r) {
		// Check constraint K3: guard.domain.oclIsTypeOf(BooleanDomain)
		if (!isBoolean(r.getGuard())) {
			MSG_ERR = "Error: In a iterative-while rule, the term representing a guard must be boolean.";
			return false;
		}
		return true;
	}

	// --- RECURSIVE WHILE RULE ---
	public static boolean checkRecursiveWhileRule(RecursiveWhileRule r) {
		return K4(r);
	}

	public static boolean K4(RecursiveWhileRule r) {
		// Check k4: guard.domain.oclIsTypeOf(BooleanDomain)
		if (!isBoolean(r.getGuard())) {
			MSG_ERR = "Error: In a recursive-while, rule the type-domain associated to the guard must be the Boolean domain.";
			return false;
		}
		return true;
	}

	// --- TERM AS RULE --- //
	public static boolean checkTermAsRule(TermAsRule r) {
		return K5(r);
	}

	/**
	 * modified 10 Jan 2008 by acarioni
	 *
	 * Checks if a TermAsRule is correctly invoked.
	 *
	 * @param r a TermAsRule
	 * @return true if ok, false otherwise
	 */
	public static boolean K5(TermAsRule r) {
		/* commented 10 Jan 2008 by acarioni
		// check constraint k5: term.domain.oclIsTypeOf(RuleDomain)
		if (!(r.getTerm().getDomain() instanceof RuleDomain)) {
			MSG_ERR = "Error: the association end term must be an actual rule.";
			return false;
		}
		return true;
		*/
		if (!(r.getTerm().getDomain() instanceof RuleDomain)) {
			MSG_ERR = "Expected a rule for term " + AsmetaTermPrinter.getAsmetaTermPrinter(true).visit(r.getTerm()) + " domain " + r.getTerm().getDomain();
			return false;
		}
		RuleDomain domain = (RuleDomain) r.getTerm().getDomain();
		List<Domain> expected = Utility.getList(domain);
		List<Domain> actual = Utility.buildDomains(r.getParameters());
		if (!compatible(expected, actual)) {
			MSG_ERR = "Expected a rule with parameters "
				+ Utility.toString(expected)
				+ " but found " + Utility.toString(actual);
			return false;
		}
		return true;
	}

	/**
	 * added 10 Jan 2008 by acarioni
	 *
	 * Compares two lists of domains.
	 *
	 * @param lst1 a list
	 * @param lst2 another list
	 * @return true if compatible, false otherwise
	 */
	static boolean compatible(List<? extends Domain> lst1, List<? extends Domain> lst2) {
		if (lst1.size() != lst2.size()) {
			return false;
		}
		for (int i = 0; i < lst1.size(); i++) {
			if (!compatible(lst1.get(i), lst2.get(i))) {
				return false;
			}
		}
		return true;
	}

	// -----------------------------------------------------------------------//
	// -------------------------- TERMS -----------------------------//
	// -----------------------------------------------------------------------//

	// Term Compatibility Definition
	// context Term def:
	// T1: let compatible(Term t) : boolean = self.domain.compatible(t.domain)
	public static boolean compatible(Term self, Term t) {
		return compatible(self.getDomain(), t.getDomain());
	}

	// -------------------------- BASIC TERMS --------------------------- //

	// --- VARIABLE TERM --- //
	public static boolean checkVariableTerm(VariableTerm t) {
		return T11(t);
	}

	public static boolean T11(VariableTerm t) {
		// Check constraint T11: domain.oclIsTypeOf(RuleDomain) <=>
		// (kind = VariableKind::ruleVar)
		if ((t.getDomain() instanceof RuleDomain && !(t.getKind()
				.equals((VariableKind.RULE_VAR))))
				|| (!(t.getDomain() instanceof RuleDomain) && t.getKind()
						.equals((VariableKind.RULE_VAR)))) {
			MSG_ERR = "Error: if (and only if) the domain of a VariableTerm is the RuleDomain, then the variable is a rule variable.";
			return false;
		}
		return true;
	}

	// --- FUNCTION TERM --- //
	public static boolean checkFunctionTerm(FunctionTerm t) {
		return T8(t) && T9(t);
	}

	public static boolean T8(FunctionTerm t) {
		// check constraint T8: domain = function.codomain
		if (t.getDomain() != t.getFunction().getCodomain()) {
			MSG_ERR = "Error: the domain of the function term [location term] "
					+ "is not equal to the associated function codomain.";
			return false;
		}
		return true;
	}

	public static boolean T9(FunctionTerm t) {
		logger.debug("checking T9");
		Function f = t.getFunction();
		// check constraint T9:
		// if function.arity.oclAsType(Integer) = 0
		// then arguments->isEmpty()
		// else arguments->notEmpty() and
		// arguments.domain.compatible(function.domain)
		// endif
		if (f.getArity() == 0 && t.getArguments() != null) {
			MSG_ERR = "Error: the associated function arity is 0, but the function term"
					+ "specify arguments of the function application.";
			return false;
		}

		if (f.getArity() != 0
				&& !(t.getArguments() != null && compatible(t.getArguments()
						.getDomain(), f.getDomain()))) {
			MSG_ERR = "Error: the associated function arity is greater than 0, but the function term"
					+ "doesn't specify the actual arguments of the function application.";
			return false;
			}
		logger.debug("Checking applicability...");
		if (! applicable(t)) return false;
		return true;
	}



	/**
	 * Applicability criteria
	 *
	 * @param t a function term f(t1,..,tn)
	 *
	 * @return true, if the function is applicable with respect to the function term's arguments
	 */
	static boolean applicable(FunctionTerm t) {
		/*
		 * OCL SYNTAX: context FunctionTerm def:
		 * let applicable(FunctionTerm ft): boolean = applicable(function.domain,arguments.domain)
		 */

	if ((t.getArguments() != null)){
    		Function f = t.getFunction();
			String args = Utility.appendInKey(new StringBuffer(),t.getArguments()).toString();
			if( !args.contains("self")   //Special case: function self:Agent appears in the arguments list
			 && !applicable(f.getDomain(),t.getArguments().getDomain())
			) {
			MSG_ERR = "Error: the function defined on a concrete C subset of an abstract domain can be applied only on"
				+ " the same domain C ( C = "+ f.getDomain().getName()+" function: "+ f.getName()+" applied to "+t.getArguments().getDomain().getName()+")";
			return false;
		}
		}
		return true;
	}


    //Domain Applicability definition
	/**
	 * Applicability of f(t1,...,tn)
	 *
	 * @param self  is the function domain
	 * @param d is the domain of the arguments
	 * @return true, if successful
	 */
	public static boolean applicable(Domain self, Domain d) {

	    /*
		 * OCL SYNTAX: context Domain def: let applicable(Domain d): boolean =
		 */

		/*
		 * OCL SYNTAX: -- self is a ConcreteDomain subset of an Abstract Domain
		 * (self.oclIsTypeOf(ConcreteDomain) and
		    self.oclAsType(ConcreteDomain).typeDomain.oclIsTypeOf(AbstractTd))
		    implies self = d */
		if (self instanceof ConcreteDomain &&
			getTypeDomain(self) instanceof AbstractTd &&
			(self!= d || !self.getName().equals(d.getName())))
				return false;

		/*
		 * OCL SYNTAX: -- two PowersetDomain ( self.oclIsTypeOf(PowersetDomain)
		 * and d.oclIsTypeOf(PowersetDomain) and
		 * self.oclAsType(PowersetDomain).baseDomain.applicable(d.oclAsType(PowersetDomain).baseDomain))
		 * or
		 */
		if (self instanceof PowersetDomain
				&& d instanceof PowersetDomain)
			return applicable(((PowersetDomain) self).getBaseDomain(),
						((PowersetDomain) d).getBaseDomain());
		/*
		 * OCL SYNTAX: -- two ProductDomain ( self.oclIsTypeOf(ProductDomain)
		 * and d.oclIsTypeOf(ProductDomain) and (let size:Integer =
		 * self.oclAsType(ProductDomain).domains->sise() in size =
		 * d.oclAsType(ProductDomain).domains->sise() and
		 * Sequence{1..size}->forAll(i:Integer |
		 * self.oclAsType(ProductDomain).domains->at(i).applicable(d.oclAsType(ProductDomain).domains->at(i))
		 * endlet) )
		 */
		if ((self instanceof ProductDomain)
				&& (d instanceof ProductDomain))
				return applicableSubDomains((ProductDomain) self, (ProductDomain) d);

		/*
		 * OCL SYNTAX: -- two SequenceDomain (self.oclIsTypeOf(SequenceDomain)
		 * and d.oclIsTypeOf(SequenceDomain) and
		 * self.oclAsType(SequenceDomain).domain.applicable(d.oclAsType(SequenceDomain).domain))
		 * or
		 */
		if ((self instanceof SequenceDomain)
				&& (d instanceof SequenceDomain))
				return applicable(((SequenceDomain) self).getDomain(),
						((SequenceDomain) d).getDomain());

		/*
		 * OCL SYNTAX: -- two BagDomain (self.oclIsTypeOf(BagDomain) and
		 * d.oclIsTypeOf(BagDomain) and
		 * self.oclAsType(BagDomain).domain.applicable(d.oclAsType(BagDomain).domain))
		 * or
		 */
		if ((self instanceof BagDomain)
				&& (d instanceof BagDomain))
				return applicable(((BagDomain) self).getDomain(), ((BagDomain) d)
						.getDomain());

		/*
		 * OCL SYNTAX: -- two MapDomain (self.oclIsTypeOf(MapDomain) and
		 * d.oclIsTypeOf(MapDomain) and
		 * self.oclAsType(MapDomain).sourceDomain.applicable(d.oclAsType(MapDomain).sourceDomain)
		 * and
		 * self.oclAsType(MapDomain).targetDomain.applicable(d.oclAsType(MapDomain).targetDomain))
		 */
		if ((self instanceof MapDomain)
				&& (d instanceof MapDomain))
				return (applicable(((MapDomain) self).getSourceDomain(),((MapDomain) d).getSourceDomain())  &&
						applicable(((MapDomain) self).getTargetDomain(),((MapDomain) d).getTargetDomain()));

		return true;
	}

	private static boolean applicableSubDomains(ProductDomain self,
			ProductDomain d) {
		int size = self.getDomains().size();
		if (size != d.getDomains().size())
			return false;

		Object o1,o2;
		Iterator<?> dom1_iter = self.getDomains().listIterator();
		Iterator<?> dom2_iter = d.getDomains().listIterator();
		while (dom1_iter.hasNext()) {
			o1 = dom1_iter.next();
			o2 = dom2_iter.next();
			// this trick is necessary because I get many errors of type
			// ClassCast Exception
			// I use now getByMofID to get the right class of the i-th
			// domain
			Domain fd_i = (Domain) o1;
			Domain ad_i = (Domain) o2;
			if (!applicable(fd_i, ad_i))
				return false;
		}
		return true;
	}




	// --- LOCATION TERM --- //
	public static boolean checkLocationTerm(LocationTerm t) {
		return checkFunctionTerm(t) && T10(t);
	}

	public static boolean T10(LocationTerm t) {
		// Check constraint T10: function.oclIsTypeOf(DynamicFunction)
		if (!(t.getFunction() instanceof DynamicFunction)) {
			MSG_ERR = "Error: In a location term, the leftmost function must be dynamic.";
			return false;
		}
		return true;
	}

	// -------------- CONSTANT TERMS --------------- //

	// --- COMPLEX TERM --- //
	public static boolean checkComplexTerm(ComplexTerm t) {
		return T10(t);
	}

	public static boolean T10(ComplexTerm t) {
		// Check constraint T10: domain.oclIsTypeOf(ComplexDomain)
		if (!(t.getDomain() instanceof ComplexDomain)) {
			MSG_ERR = "Error:The type-domain associated to a complex term must be the complex domain.";
			return false;
		}
		return true;
	}

	// --- REAL TERM --- //
	public static boolean checkRealTerm(RealTerm t) {
		return T11(t);
	}

	public static boolean T11(RealTerm t) {
		// Check constraint T11: domain.oclIsTypeOf(RealDomain)
		if (!(t.getDomain() instanceof RealDomain)) {
			MSG_ERR = "Error:The type-domain associated to a real term must be the real domain.";
			return false;
		}
		return true;
	}

	// --- INTEGER TERM --- //
	public static boolean checkIntegerTerm(IntegerTerm t) {
		return T12(t);
	}

	public static boolean T12(IntegerTerm t) {
		// check constraint T12: domain.oclIsTypeOf(IntegerDomain)
		if (!(t.getDomain() instanceof IntegerDomain)) {
			MSG_ERR = "Error:The type-domain associated to an integer term must be the integer domain.";
			return false;
		}
		return true;
	}

	// --- NATURAL TERM --- //
	public static boolean checkNaturalTerm(NaturalTerm t) {
		return T13(t);
	}

	public static boolean T13(NaturalTerm t) {
		// check constraint T13: domain.oclIsTypeOf(NaturalDomain)
		if (!(t.getDomain() instanceof NaturalDomain)) {
			MSG_ERR = "Error:The type-domain associated to a natural term must be the natural domain.";
			return false;
		}
		return true;
	}

	// --- CHAR TERM --- //
	public static boolean checkCharTerm(CharTerm t) {
		// check constraint 1
		if (!(t.getDomain() instanceof CharDomain)) {
			MSG_ERR = "Error:The type-domain associated to a CharTerm must be the char domain.";
			return false;
		}
		return true;
	}

	// --- STRING TERM --- //
	public static boolean checkStringTerm(StringTerm t) {
		return T14(t);
	}

	public static boolean T14(StringTerm t) {
		// check constraint T14: domain.oclIsTypeOf(CharDomain)
		if (!(t.getDomain() instanceof StringDomain)) {
			MSG_ERR = "Error:The type-domain associated to a string term must be the string domain.";
			return false;
		}
		return true;
	}

	// --- BOOLEAN TERM --- //
	public static boolean checkBooleanTerm(BooleanTerm t) {
		return T2(t);
		// T3 and T4 can't be checked!!
		// T3: BooleanTerm.allInstances()->size()=2
		// T4: BooleanTerm.allInstances()->exist(t1,t2|
		// t1.symbol = 'true' and t2.symbol = 'false')
	}

	public static boolean T2(BooleanTerm t) {
		// check constraint T2: domain.oclIsTypeOf(BooleanDomain)
		if (!(t.getDomain() instanceof BooleanDomain)) {
			MSG_ERR = "Error:The type-domain associated to a boolean term must be the boolean domain.";
			return false;
		}
		return true;
	}

	// --- UNDEF TERM --- //
	public static boolean checkUndefTerm(UndefTerm t) {
		return T5(t);
		// T6: UndefTerm.allInstances()->size()=1 by MOF!
		// T7: UndefTerm.allInstances()->exist(t | t.symbol = 'undef') ??? How
		// to in Java ???
	}

	public static boolean T5(UndefTerm t) {
		// Check constraint T5: domain.oclIsTypeOf(UndefDomain)
		if (!(t.getDomain() instanceof UndefDomain)) {
			MSG_ERR = "Error:The type-domain associated to the undef term must be the undef domain.";
			return false;
		}
		return true;
	}

	// --- ENUM TERM --- //
	public static boolean checkEnumTerm(EnumTerm t) {
		return T16(t) && T17(t);
	}

	public static boolean T16(EnumTerm t) {
		// T16: domain.oclIsTypeOf(EnumTD)
		if (!(t.getDomain() instanceof EnumTd)) {
			MSG_ERR = "Error:The type-domain associated to an EnumTerm must be an an enumeration type-domain.";
			return false;
		}
		return true;
	}

	public static boolean T17(EnumTerm t) {
		// T17: domain.oclAsType(EnumTD).element ->
		// exist(e:EnumElement | e.symbol = symbol)

		Iterator<?> iter = ((EnumTd) t.getDomain()).getElement().iterator();
		EnumElement enumEl;
		boolean elemFind = false;
		while (iter.hasNext() && !elemFind) {
			enumEl = (EnumElement) iter.next();
			if (enumEl.getSymbol().equals(t.getSymbol()))
				elemFind = true;
		}
		if (!elemFind) {
			MSG_ERR = "Error: The symbol denoted by an enum constant term must be an element of"
					+ "the enumeration type-domain associated to the enum constant term.";
			return false;
		}
		return true;
	}

	// -------------------------- EXTENDED TERMS --------------------------- //

	// --- CONDITIONAL TERM --- //
	public static boolean checkConditionalTerm(ConditionalTerm t) {
		return T19(t) && T18(t);
	}

	public static boolean T18(ConditionalTerm t) {
		// Check constraint T18: self.compatible(thenTerm) and
		// (elseTerm -> notEmpty() implies self.compatible(elseTerm))
		Domain thenT_td = t.getThenTerm().getDomain();
		Domain conditional_td = t.getDomain();
		Term elseT = t.getElseTerm();

		if (!compatible(conditional_td, thenT_td)) {
			MSG_ERR = "Error: The conditional term isn't compatible to the 'then term'.";
			return false;
		}
		if (elseT != null && !compatible(conditional_td, elseT.getDomain())) {
			MSG_ERR = "Error: The conditional term isn't compatible to the 'else term'.";
			return false;
		}
		return true;
	}

	public static boolean T19(ConditionalTerm t) {
		// Check constraint T19: guard.domain.oclIsTypeOf(BooleanDomain)
		if (!isBoolean(t.getGuard())) {
			MSG_ERR = "Error: The type-domain associated to the guard term must be the boolean domain.";
			return false;
		}
		return true;
	}

	// --- CASE TERM --- //
	public static boolean checkCaseTerm(CaseTerm t) {
		return T20(t) && T21(t) && T22(t);
	}

	public static boolean T20(CaseTerm t) {
		// T20: comparingTerm->size() = resultTerm->size()
		if (t.getComparingTerm().size() != t.getResultTerms().size()) {
			MSG_ERR = "Error:The number of left-hand side terms of the case-clauses must"
					+ "be equal to the number of right-hand side terms.";
			return false;
		}
		return true;
	}

	public static boolean T21(CaseTerm t) {
		// T21: resultTerms->forAll(t:Term|self.compatible(t))
		// and self.compatible(otherwiseTerm)

		Iterator<?> iter = t.getResultTerms().iterator();
		Term resultT;
		while (iter.hasNext()) {
			resultT = (Term) iter.next();
			if (!compatible(resultT, t)) {
				MSG_ERR = "Error: A case term must be compatible to the right-hand side terms of the case-clauses.";
				return false;
			}
		}
		Term otherwiseT = t.getOtherwiseTerm();
		if (otherwiseT != null && !compatible(otherwiseT, t)) {
			MSG_ERR = "Error: A case term must be compatible to the term of the otherwise-clause.";
			return false;
		}
		return true;
	}

	public static boolean T22(CaseTerm t) {
		// T22: comparingTerm->forAll(t:Term | comparedTerm.compatible(t))
		Iterator<?> iter = t.getComparingTerm().iterator();
		Term comparingT;
		while (iter.hasNext()) {
			comparingT = (Term) iter.next();
			if (!compatible(t.getComparedTerm(), comparingT)) {
				MSG_ERR = "Error: The term to match isn't compatible to the left-hand terms of the case-clauses.";
				return false;
			}
		}
		return true;
	}

	// --- DOMAIN TERM --- //
	public static boolean checkDomainTerm(DomainTerm t) {
		return E5(t);
	}

	public static boolean E5(DomainTerm t) {
		// Check constraint E5: domain.oclIsTypeOf(PowersetDomain)
		if (!((t.getDomain() instanceof PowersetDomain))) {
			MSG_ERR = "Error: the type-domain of a DomainTerm must be a powerset.";
			return false;
		}
		return true;
	}

	// --- RULE AS TERM --- //
	public static boolean checkRuleAsTerm(RuleAsTerm t) {
		return E4(t);
	}

	public static boolean E4(RuleAsTerm t) {
		// Check constraint E4: domain.oclIsTypeOf(RuleDomain)
		if (!(t.getDomain() instanceof RuleDomain)) {
			MSG_ERR = "Error:The type-domain associated to a RuleAsTerm must be the rule domain.";
			return false;
		}
		return true;
	}

	// --- TUPLE TERM --- //
	public static boolean checkTupleTerm(TupleTerm t) {
		// let size:Integer = arity.oclAsType(Integer) in
		// E1: size = terms->size() and
		// E2: if size = 1 then domain = term->at(1).domain
		// E3 else domain.oclIsTypeOf(ProductDomain) and
		// domain.oclAsType(ProductDomain).domains->size() = size and
		// Sequence{1..size}->forAll(i:Integer |
		// domain.oclAsType(ProductDomain).domains->at(i) = terms->at(i).domain)
		// endif
		return E1(t) && E2(t) && E3(t) && H12(t);
	}

	public static boolean E1(TupleTerm t) {
		// check constraint E1
		if (t.getArity() != t.getTerms().size()) {
			MSG_ERR = "Error:The tuple arity is not equal to the number of composing terms.";
			return false;
		}
		return true;
	}

	public static boolean E2(TupleTerm t) {
		// check constraint E2
		if (t.getArity() == 1
				&& t.getDomain() != t.getTerms().get(0).getDomain()) {
			MSG_ERR = "Error: If the arity of a tuple is 1 (this case), then the domain of the tuple"
					+ "must be that of the component term.";
			return false;
		}
		return true;
	}

	public static boolean E3(TupleTerm t) {
		if (t.getArity() > 1) {
			if (!(t.getDomain() instanceof ProductDomain)) {
				MSG_ERR = "Error:The type-domain associated to the tuple term must be a product domain.";
				return false;
			}
			Collection<?> subTDList = ((ProductDomain) t.getDomain()).getDomains();
			if (subTDList.size() != t.getTerms().size()) {
				MSG_ERR = "Error: The number of the component domains of the product domain associated to the tuple term must be equal to the tuple arity.";
				return false;
			}

			Iterator<?> subTDIter = subTDList.iterator();
			Iterator<?> elemIter = t.getTerms().iterator();
			while (subTDIter.hasNext()) {
				if (subTDIter.next() != ((Term) elemIter.next()).getDomain()) {
					MSG_ERR = "Error: the domain of the tuple is not the Cartesian product of the domains of the corresponding component terms. "
							+ "Type mismatch between"
							+ ((TypeDomain) subTDIter.next()).getName()
							+ "and"
							+ ((Term) elemIter.next()).getDomain().getName();
					return false;
				}
			}
		}
		return true;
	}

	public static boolean H12(TupleTerm t) {
		// now we allow COmprehension H12: to correct
		// H12: terms -> forAll( t: Term | not
		// t.domain.oclIsTypeOf(VariableBindingTerm) and
		// not t.domain.oclIsTypeOf(ConditionalTerm) and not
		// t.domain.oclIsTypeOf(CaseTerm)
		Iterator<?> elemIter = t.getTerms().iterator();
		while (elemIter.hasNext()) {
			Term elem = (Term) elemIter.next();
			if (!termAccepted(elem))
				return false;
		}
		return true;
	}

	/**
	 * The only terms allowed in tuple terms, map terms, sequence terms, set
	 * terms, bag terms, and comprehension terms (bags, sets, sequences, and
	 * maps) are basic terms, collection terms, and extended terms except
	 * ConditionalTerms, CaseTerms, and VariableBindingTerms To this purpose use
	 * the following method
	 */
	public static boolean termAccepted(Term t) {
		/* NOW WE ALLOW Comprehension */
		// July 22, 2008 - commented by acarioni
		// In my opinion, if they are terms, they can appear everywhere a term can
//		if (t instanceof FiniteQuantificationTerm) {
//			MSG_ERR = "Error: Inner terms cannot be instances of FiniteQuantificationTerm.";
//			return false;
//		}
//		if (t instanceof LetTerm) {
//			MSG_ERR = "Error: Inner terms cannot be instances of LetTerm.";
//			return false;
//		}
//		if (t instanceof ConditionalTerm) {
//			MSG_ERR = "Error: Inner terms cannot be instances of ConditionalTerm.";
//			return false;
//		}
//		if (t instanceof CaseTerm) {
//			MSG_ERR = "Error: Inner terms cannot be instances of CaseTerm.";
//			return false;
//		}
		return true;
	}

	// -----------COLLECTION TERMS ------------ //

	// --- SET TERM --- //
	public static boolean checkSetTerm(SetTerm t) {
		return E6(t) && E7(t) && H15(t);
	}

	public static boolean E6(SetTerm t) {
		// Check constraint E6: size.oclAsType(Integer) = term->size()
		if (t.getSize() != t.getTerm().size()) {
			MSG_ERR = "Error: The size of a set term must be the number of terms it contains.";
			return false;
		}
		return true;
	}

	public static boolean E7(SetTerm t) {
		// E7: domain.oclIsTypeOf(PowersetDomain) and
		// term->forAll(t:Term|domain.oclAsType(PowersetDomain).baseDomain =
		// t.domain)
		Domain d = t.getDomain();
		if (!(d instanceof PowersetDomain)) {
			MSG_ERR = "Error: the type-domain of a SetTerm must be a PowersetDomain.";
			return false;
		}
		Domain bd = ((PowersetDomain) d).getBaseDomain();
		// Note that, if the set is empty, then "bd" can be any type domain.
		// "bd"
		// is generally set during the creation of the empty set depending on
		// the type of the elements the set is going to contain.
		// To this purpose, we introduced a notion of "generic type-domain"
		// (represented by the class AnyDomain) i.e. a domain which stands for
		// any
		// other type-domain.
		// E7aux: if size = 0 then
		// domain.oclAsType(PowersetDomain).baseDomain.oclIsTypeOf(AnyDomain)
		if (t.getSize() == 0) {
			if (!(bd instanceof AnyDomain)) {
				MSG_ERR = "Error: the type-domain of an empty set term must be a PowersetDomain of AnyDomain.";
				return false;
			}
		} else {
			Iterator<?> iter = t.getTerm().iterator();
			Term elem;
			while (iter.hasNext()) {
				elem = (Term) iter.next();
				if (bd != elem.getDomain()) {
					MSG_ERR = "Error: wrong domain of some element of the SetTerm.";
					return false;
				}
			}
		}
		return true;
	}

	public static boolean H15(SetTerm t) {
		// H15: see H12 for TupleTerm
		Iterator<?> elemIter = t.getTerm().iterator();
		while (elemIter.hasNext()) {
			Term elem = (Term) elemIter.next();
			if (!termAccepted(elem)) {
				MSG_ERR = "Error [H15]: term not accepted in SetTerm";
				return false;
			}
		}
		return true;
	}

	// --- BAG TERM --- //
	public static boolean checkBagTerm(BagTerm t) {
		return E10(t) && E11(t) && E12(t) && H16(t);
	}

	public static boolean E10(BagTerm t) {
		// E10: size.oclAsType(Integer) = term->size()
		if (t.getSize() != t.getTerm().size()) {
			MSG_ERR = "Error: wrong size of the BagTerm.";
			return false;
		}
		return true;
	}

	public static boolean E11(BagTerm t) {
		// E11: domain.oclIsTypeOf(BagDomain)
		if (!(t.getDomain() instanceof BagDomain)) {
			MSG_ERR = "Error: the type-domain of a bag term must be a BagDomain.";
			return false;
		}
		return true;
	}

	public static boolean E12(BagTerm t) {
		// E12: term->forAll(t:Term |
		// t.domain.compatible(domain.oclAsType(BagDomain).domain) )
		Domain subDom = ((BagDomain) t.getDomain()).getDomain();

		// Empty bag
		// E12aux: if size = 0 then
		// domain.oclAsType(BagDomain).domain.oclIsTypeOf(AnyDomain)
		if (t.getSize() == 0) {
			if (!(subDom instanceof AnyDomain)) {
				MSG_ERR = "Error: the type-domain of the empty BagTerm must be a BagDomain of AnyDomain.";
				return false;
			}
		} else {
			Iterator<?> iter = t.getTerm().iterator();
			Term elem;
			while (iter.hasNext()) {
				elem = (Term) iter.next();
				if (!compatible(subDom, elem.getDomain())) {
					MSG_ERR = "Error: wrong type-domain of some element of the BagTerm.";
					return false;
				}
			}
		}
		return true;
	}

	public static boolean H16(BagTerm t) {
		// H16: see H12 for TupleTerm
		Iterator<?> elemIter = t.getTerm().iterator();
		while (elemIter.hasNext()) {
			Term elem = (Term) elemIter.next();
			if (!termAccepted(elem))
				return false;
		}
		return true;
	}

	// --- SEQUENCE TERM --- //
	public static boolean checkSequenceTerm(SequenceTerm t) {
		return E13(t) && E14(t) && E15(t) && H14(t);
	}

	public static boolean E13(SequenceTerm t) {
		// E13: size.oclAsType(Integer) = term->size()
		if (t.getSize() != t.getTerms().size()) {
			MSG_ERR = "Error: wrong size of the SequenceTerm.";
			return false;
		}
		return true;
	}

	public static boolean E14(SequenceTerm t) {
		// E14: domain.oclIsTypeOf(SequenceDomain)
		if (!(t.getDomain() instanceof SequenceDomain)) {
			MSG_ERR = "Error: the type-domain of a sequence term must be a SequenceDomain.";
			return false;
		}
		return true;
	}

	public static boolean E15(SequenceTerm t) {
		// E15: term->forAll(t:Term |
		// t.domain.compatible(domain.oclAsType(SequenceDomain).domain) )
		Domain subDom = ((SequenceDomain) t.getDomain()).getDomain();

		// Empty sequence
		// E15aux: if size = 0 then
		// domain.oclAsType(SequenceDomain).domain.oclIsTypeOf(AnyDomain)
		if (t.getSize() == 0) {
			if (!(subDom instanceof AnyDomain)) {
				MSG_ERR = "Error: the type-domain of the empty SequenceTerm must be a SequenceDomain of AnyDomain.";
				return false;
			}
		} else {
			Iterator<?> iter = t.getTerms().iterator();
			Term elem;
			while (iter.hasNext()) {
				elem = (Term) iter.next();
				if (!compatible(subDom, elem.getDomain())) {
					MSG_ERR = "Error: wrong type-domain of some element of the SequenceTerm.";
					return false;
				}
			}
		}
		return true;
	}

	public static boolean H14(SequenceTerm t) {
		// H14: see H12 for TupleTerm
		Iterator<?> elemIter = t.getTerms().iterator();
		while (elemIter.hasNext()) {
			Term elem = (Term) elemIter.next();
			if (!termAccepted(elem))
				return false;
		}
		return true;
	}

	// --- MAP TERM --- //
	public static boolean checkMapTerm(MapTerm t) {
		return E16(t) && E17(t) && E18(t) && H13(t);
	}

	public static boolean E16(MapTerm t) {
		// E16: pair->forAll(p:TupleTerm | p.term->size()= 2)
		Iterator<?> iter = t.getPair().iterator();
		while (iter.hasNext()) {
			if (((TupleTerm) iter.next()).getArity() != 2) {
				MSG_ERR = "Error: the elements of a map term must be pairs.";
				return false;
			}
		}
		return true;
	}

	public static boolean E17(MapTerm t) {
		// E17: size.oclAsType(Integer) = pair->size()
		if (t.getSize() != t.getPair().size()) {
			MSG_ERR = "Error: wrong size of the MapTerm.";
			return false;
		}
		return true;
	}

	public static boolean E18(MapTerm t) {
		// E18: domain.oclIsTypeOf(MapDomain)
		if (!(t.getDomain() instanceof MapDomain)) {
			MSG_ERR = "Error: the type-domain of a map term must be a MapDomain.";
			return false;
		}
		return true;
	}

	public static boolean E19(MapTerm t) {
		// E19: pair->forAll(p:TupleTerm |
		// domain.oclAsType(MapDomain).sourceDomain.
		// compatible(p.domain.oclAsType(ProductDomain).domain->at(1)) and
		// domain.oclAsType(MapDomain).targetDomain.compatible(p.domain.oclAsType(ProductDomain).domain->at(2)))
		Domain sourceDom = ((MapDomain) t.getDomain()).getSourceDomain();
		Domain targetDom = ((MapDomain) t.getDomain()).getTargetDomain();

		// Empty map
		// E19_aux:
		// E15aux: if size = 0 then
		// domain.oclAsType(MapDomain).sourceDomain.oclIsTypeOf(AnyDomain) and
		// domain.oclAsType(MapDomain).targetDomain.oclIsTypeOf(AnyDomain) endif
		if (t.getSize() == 0) {
			if (!((sourceDom instanceof AnyDomain) && (targetDom instanceof AnyDomain))) {
				MSG_ERR = "Error: the source and target domain of an empty map term must be AnyDomain.";
				return false;
			}
		}

		else {
			Iterator<?> iter = t.getPair().iterator();
			while (iter.hasNext()) {
				List<?> subDomList = ((ProductDomain) ((TupleTerm) iter.next())
						.getDomain()).getDomains();
				if (!(compatible(sourceDom, (TypeDomain) subDomList.get(1)) && (compatible(
						targetDom, (TypeDomain) subDomList.get(2))))) {
					MSG_ERR = "Error: wrong type-domain of some pair of the MapTerm.";
					return false;
				}
			}
		}
		return true;
	}

	public static boolean H13(MapTerm t) {
		// H13: see H12 for TupleTerm
		Iterator<?> elemIter = t.getPair().iterator();
		while (elemIter.hasNext()) {
			Term elem = (Term) elemIter.next();
			if (!termAccepted(elem))
				return false;
		}
		return true;
	}

	// ------------------ VARIABLE BINDING TERMS ---------------------- //

	// --- LET TERM --- //
	public static boolean checkLetTerm(LetTerm t) {
		return L1(t) && L2(t) && L3(t);
	}

	public static boolean L1(LetTerm t) {
		// L1: domain = body.domain
		if (t.getDomain() != t.getBody().getDomain()) {
			MSG_ERR = "Error: The type-domain of a LetTerm must be equal to the one of its body.";
			return false;
		}
		return true;
	}

	public static boolean L2(LetTerm t) {
		// L2: assignmentTerm->size() = variable->size()
		if (t.getAssignmentTerm().size() != t.getVariable().size()) {
			MSG_ERR = "Error: In a LetTerm, the number of variables must be equal to the number of assignment terms.";
			return false;
		}
		return true;
	}

	public static boolean L3(LetTerm t) {
		// L3: Sequence1..variable->size()->forAll(i:Integer|
		// variable->at(i).compatible(assignmentTerm->at(i))
		Iterator<?> iter1 = t.getVariable().iterator();
		Iterator<?> iter2 = t.getAssignmentTerm().iterator();
		while (iter1.hasNext()) {
			if (!compatible((VariableTerm) iter1.next(), (Term) iter2.next())) {
				MSG_ERR = "Error: In a let-term, each variable must be compatible to its corresponding assignment term.";
				return false;
			}
		}
		return true;
	}

	// ----------------- FINITE QUANTIFICATION TERMS ----------------------- //

	public static boolean checkFiniteQuantificationTerm(
			FiniteQuantificationTerm t) {
		return Q1(t) && Q4(t) && Q2_Q3(t);
	}

	public static boolean Q1(FiniteQuantificationTerm t) {
		// Check Q1: domain.oclIsTypeOf(BooleanDomain)
		if (!(t.getDomain() instanceof BooleanDomain)) {
			MSG_ERR = "Error: The type-domain of a finite-quantification term must be the boolean domain.";
			return false;
		}
		return true;
	}

	public static boolean Q2_Q3(FiniteQuantificationTerm t) {
		// Check:
		// Sequence{1..variable->size()}->forAll(i:Integer|
		// Q2: ranges->at(i).domain.oclIsTypeOf(PowersetDomain) and
		// Q3: variable->at(i).domain =
		// ranges->at(i).domain.oclAsType(PowersetDomain).baseDomain)
		Iterator<?> iter1 = t.getVariable().iterator();
		Iterator<?> iter2 = t.getRanges().iterator();
		VariableTerm v;
		Term d;
		while (iter1.hasNext()) {
			d = (Term) iter2.next();
			if (!(d.getDomain() instanceof PowersetDomain)) {
				MSG_ERR = "Error: The domain of each term appearing in the variables ranges must be a powerset domain.";
				return false;
			}
			v = (VariableTerm) iter1.next();
			if (v.getDomain() != ((PowersetDomain) d.getDomain())
					.getBaseDomain()) {
				MSG_ERR = "Error: In a finite-quantification term, the domain of each variable must be equal to the base domain of the dorresponding powerset range.";
				return false;
			}
		}
		return true;
	}

	public static boolean Q4(FiniteQuantificationTerm t) {
		// Check Q4: if self.guard->notEmpty() then
		// guard.domain.oclIsTypeOf(BooleanDomain) endif
		if ((t.getGuard() != null) && (!isBoolean(t.getGuard()))) {
			MSG_ERR = "Error: The type-domain of the guard term must be the boolean domain.";
			return false;
		}
		return true;
	}

	// ----------------- COMPREHENSION TERMS ----------------------- //

	public static boolean checkComprehensionTerm(ComprehensionTerm t) {
		return H1(t) && H2(t) && H17(t);
	}

	public static boolean H17(ComprehensionTerm t) {
		// H17: see H12 for TupleTerm
		if (!termAccepted(t.getTerm()))
			return false;
		return true;
	}

	public static boolean H1(ComprehensionTerm t) {
		// H1: if guard->notEmpty() then guard.domain.oclIsTypeOf(BooleanDomain)
		// endif
		if ((t.getGuard() != null) && (!isBoolean(t.getGuard()))) {
			MSG_ERR = "Error: The type-domain of the guard term must be the boolean domain.";
			return false;
		}
		return true;
	}

	public static boolean H2(ComprehensionTerm t) {
		// H2: variable->size() = ranges->size()
		if (t.getVariable().size() != t.getRanges().size()) {
			MSG_ERR = "Error: In a comprehension term, the number of variables must be equal to the number of terms in ranges.";
			return false;
		}
		return true;
	}

	// --- SetCT --- //
	public static boolean checkSetCT(SetCt t) {
		// check constraints of the superclass
		if (!checkComprehensionTerm(t))
			return false;
		return H3(t) && H4(t);
	}

	public static boolean H3(SetCt t) {
		// H3: domain.oclIsTypeOf(PowersetDomain) and
		// domain.oclAsType(PowersetDomain).domain = term.domain
		if (!((t.getDomain() instanceof PowersetDomain) && ((PowersetDomain) t
				.getDomain()).getBaseDomain() == t.getTerm().getDomain())) {
			MSG_ERR = "Error: The type-domain of a set comprehension term must be a PowersetDomain over the type-domain of the main term.";
			return false;
		}
		return true;
	}

	public static boolean H4(SetCt t) {
		// H4: Sequence{1..variable->size()}-> forAll(i:Integer |
		// ranges->at(i).domain.oclIsTypeOf(PowersetDomain) and
		// variable->at(i).domain =
		// ranges->at(i).domain.oclAsType(PowersetDomain).baseDomain )

		Iterator<?> iter1 = t.getVariable().iterator();
		Iterator<?> iter2 = t.getRanges().iterator();
		VariableTerm v;
		Term r;
		while (iter1.hasNext()) {
			v = (VariableTerm) iter1.next();
			r = (Term) iter2.next();
			if (!(r.getDomain() instanceof PowersetDomain)) {
				MSG_ERR = "Error: the type of the collection terms in ranges, Di, must be equal to the type of"
						+ "the comprehension term. Di must be a set for a set comprehension term and its domain must be a Powerset.";
				return false;
			}
			if (v.getDomain() != ((PowersetDomain) r.getDomain())
					.getBaseDomain()) {
				MSG_ERR = "Error: In a SetCT, the domains of variables must be set accordingly.";
				return false;
			}
		}
		return true;
	}

	// --- MapCT --- //
	public static boolean checkMapCT(MapCt t) {
		// check constraints of the superclass
		if (!checkComprehensionTerm(t))
			return false;
		return H9(t) && H10(t) & H11(t);
	}

	public static boolean H9(MapCt t) {
		// H9: term.oclIsTypeOf(TupleTerm) and
		// term.oclAsType(TupleTerm).term->size()=2
		Term mainT = t.getTerm();
		if (!((mainT instanceof TupleTerm) && (((TupleTerm) mainT).getTerms()
				.size() == 2))) {
			MSG_ERR = "Error: The main term of a map comprehension term must be a pair.";
			return false;
		}
		return true;
	}

	public static boolean H10(MapCt t) {
		// H10: domain.oclIsTypeOf(MapDomain) and
		// domain.oclAsType(MapDomain).sourceDomain =
		// term.domain.oclAsType(ProductDomain).domains->at(1) and
		// domain.oclAsType(MapDomain).targetDomain =
		// term.domain.oclAsType(ProductDomain).domains->at(2)
		ProductDomain mainD = (ProductDomain) t.getTerm().getDomain();
		TypeDomain firstDom = (TypeDomain) mainD.getDomains().get(0);
		TypeDomain secondDom = (TypeDomain) mainD.getDomains().get(1);
		if (!((t.getDomain() instanceof MapDomain)
				&& (((MapDomain) t.getDomain()).getSourceDomain() == firstDom) && (((MapDomain) t
				.getDomain()).getTargetDomain() == secondDom))) {
			MSG_ERR = "Error: Wrong type-domain of the map comprehension.";
			return false;
		}
		return true;
	}

	public static boolean H11(MapCt t) {
		// H11: Sequence{1..variable->size()}-> forAll(i:Integer |
		// ranges->at(i).domain.oclIsTypeOf(PowersetDomain) and
		// variable->at(i).domain =
		// ranges->at(i).domain.oclAsType(PowersetDomain).baseDomain )
		Iterator<?> iter1 = t.getVariable().iterator();
		Iterator<?> iter2 = t.getRanges().iterator();
		VariableTerm v;
		Term r;
		while (iter1.hasNext()) {
			v = (VariableTerm) iter1.next();
			r = (Term) iter2.next();
			if (!(r.getDomain() instanceof PowersetDomain)) {
				MSG_ERR = "Error: In a MapCT, terms in ranges, Di, representing where variables vary, must be powersets.";
				return false;
			}
			if (v.getDomain() != ((PowersetDomain) r.getDomain())
					.getBaseDomain()) {
				MSG_ERR = "Error: In a MapCT, the domains of variables must be set accordingly.";
				return false;
			}
		}
		return true;
	}

	// --- BagCT --- //
	public static boolean checkBagCT(BagCt t) {
		// check constraints of the superclass
		if (!checkComprehensionTerm(t))
			return false;
		return H5(t) && H6(t);
	}

	public static boolean H5(BagCt t) {
		// H5: domain.oclIsTypeOf(BagDomain) and
		// domain.oclAsType(BagDomain).domain = term.domain
		if (!((t.getDomain() instanceof BagDomain) && ((BagDomain) t
				.getDomain()).getDomain() == t.getTerm().getDomain())) {
			MSG_ERR = "Error: The type-domain of a bag comprehension term must be a BagDomain over the type-domain of the main term.";
			return false;
		}
		return true;
	}

	public static boolean H6(BagCt t) {
		// H6: Sequence{1..variable->size()}-> forAll(i:Integer |
		// ranges->at(i).domain.oclIsTypeOf(BagDomain) and
		// variable->at(i).domain =
		// ranges->at(i).domain.oclAsType(BagDomain).domain )
		Iterator<?> iter1 = t.getVariable().iterator();
		Iterator<?> iter2 = t.getRanges().iterator();
		VariableTerm v;
		Term r;
		while (iter1.hasNext()) {
			v = (VariableTerm) iter1.next();
			r = (Term) iter2.next();
			if (!(r.getDomain() instanceof BagDomain)) {
				MSG_ERR = "Error: In a BagCT, terms in ranges, Bi, representing where variables vary, must be bags.";
				return false;
			}
			if (v.getDomain() != ((BagDomain) r.getDomain()).getDomain()) {
				MSG_ERR = "Error: In a BagCT, the domains of variables must be set accordingly.";
				return false;
			}
		}
		return true;
	}

	// --- SequenceCT --- //
	public static boolean checkSequenceCT(SequenceCt t) {
		// check constraints of the superclass
		if (!checkComprehensionTerm(t))
			return false;
		return H7(t) && H8(t);
	}

	public static boolean H7(SequenceCt t) {
		// H7: domain.oclIsTypeOf(SequenceDomain) and
		// domain.oclAsType(SequenceDomain).domain = term.domain
		if (!((t.getDomain() instanceof SequenceDomain) && ((SequenceDomain) t
				.getDomain()).getDomain() == t.getTerm().getDomain())) {
			MSG_ERR = "Error: The type-domain of a sequence comprehension term must be a SequenceDomain over the type-domain of the main term.";
			return false;
		}
		return true;
	}

	public static boolean H8(SequenceCt t) {
		// H8: Sequence{1..variable->size()}-> forAll(i:Integer |
		// ranges->at(i).domain.oclIsTypeOf(SequenceDomain) and
		// variable->at(i).domain =
		// ranges->at(i).domain.oclAsType(SequenceDomain).domain )
		Iterator<?> iter1 = t.getVariable().iterator();
		Iterator<?> iter2 = t.getRanges().iterator();
		VariableTerm v;
		Term r;
		while (iter1.hasNext()) {
			v = (VariableTerm) iter1.next();
			r = (Term) iter2.next();
			if (!(r.getDomain() instanceof SequenceDomain)) {
				MSG_ERR = "Error: In a SequenceCT, terms in ranges, Bi, representing where variables vary, must be sequences.";
				return false;
			}
			if (v.getDomain() != ((SequenceDomain) r.getDomain()).getDomain()) {
				MSG_ERR = "Error: In a SequenceCT, the domains of variables must be set accordingly.";
				return false;
			}
		}
		return true;
	}
}// Class end
