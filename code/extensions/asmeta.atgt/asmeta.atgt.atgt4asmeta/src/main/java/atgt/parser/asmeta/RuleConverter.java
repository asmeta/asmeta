package atgt.parser.asmeta;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.asmeta.simulator.ForallRuleUnroller;
import org.asmeta.simulator.wrapper.RuleFactory;
import org.asmeta.parser.util.AsmPrinter;
import org.eclipse.emf.common.util.EList;

import asmeta.definitions.domains.Domain;
import asmeta.definitions.domains.PowersetDomain;
import asmeta.terms.basicterms.BooleanTerm;
import asmeta.terms.basicterms.ConstantTerm;
import asmeta.terms.basicterms.DomainTerm;
import asmeta.terms.basicterms.FunctionTerm;
import asmeta.terms.basicterms.LocationTerm;
import asmeta.terms.basicterms.SetTerm;
import asmeta.terms.basicterms.Term;
import asmeta.terms.basicterms.UndefTerm;
import asmeta.terms.basicterms.VariableTerm;
import asmeta.terms.furtherterms.CharTerm;
import asmeta.terms.furtherterms.ComplexTerm;
import asmeta.terms.furtherterms.EnumTerm;
import asmeta.terms.furtherterms.IntegerTerm;
import asmeta.terms.furtherterms.NaturalTerm;
import asmeta.terms.furtherterms.RealTerm;
import asmeta.terms.furtherterms.StringTerm;
import asmeta.transitionrules.basictransitionrules.Rule;
import asmeta.transitionrules.derivedtransitionrules.CaseRule;
import atgt.specification.ASMSpecification;
import atgt.specification.location.Function;
import atgt.specification.location.LogicalVariable;
import atgt.specification.location.Variable;
import atgt.specification.statement.BasicRule;
import atgt.specification.statement.CaseStatement;
import atgt.specification.statement.ChooseRule;
import atgt.specification.statement.ConditionalRule;
import atgt.specification.statement.DoStatement;
import atgt.specification.statement.RuleDeclaration;
import atgt.specification.statement.Skip;
import atgt.specification.statement.UpdateRule;
import tgtlib.definitions.expression.Expression;
import tgtlib.definitions.expression.IdExpression;
import tgtlib.definitions.expression.type.BoolType;
import tgtlib.definitions.expression.type.BoundType;
import tgtlib.definitions.expression.type.EnumConstCreator;
import tgtlib.definitions.expression.type.EnumType;
import tgtlib.definitions.expression.type.Type;

class RuleConverter {
	/** The logger : use the same as the AsmetLoader */
	private final static Logger logger = Logger.getLogger(AsmetaLLoader.class);

	private ASMSpecification SP;

	private EnumConstCreator ecc;

	private TermConverter termConv;

	private RuleFactory ruleFactory;

	// map the rules from asmeta to atgt
	// the name is not enough because of the overloading.
	private Map<asmeta.definitions.RuleDeclaration, RuleDeclaration> rulemap = new HashMap<>();

	/**
	 * the spec from which i can read the rules
	 * 
	 * @param spec
	 * @param ecc
	 */
	RuleConverter(ASMSpecification spec, EnumConstCreator ecc, TermConverter termConv) {
		SP = spec;
		this.ecc = ecc;
		this.termConv = termConv;
		ruleFactory = new RuleFactory();
		assert ruleFactory != null;
	}

	/**
	 * Convert rule declaration.
	 * 
	 * @param rd
	 *            the rule declaration in AsmM
	 * @return the atgt.specification.statement.rule declaration in ATGT nota
	 *         che ci potrebbero essere piu' regole con lo stesso nome e anche
	 *         con lo stesso numero di parametri (ma diverso tipo, che pero'; in
	 *         atgt non viene memorizzato)
	 */
	RuleDeclaration convertRuleDeclaration(asmeta.definitions.RuleDeclaration rd) {
		Rule rulbody = rd.getRuleBody();
		String name_rule = new String(rd.getName());
		RuleDeclaration result;
		// if without parameters
		if (rd.getVariable().size() == 0) {
			logger.debug("converting Declaration " + rd.getName() + " of type " + rulbody.getClass().getName()
					+ " without paramters");
			BasicRule convertRule = convertRule(rulbody);
			result = new atgt.specification.statement.RuleDeclaration(name_rule, convertRule);
			// there is no other rule with the same name
		} else {
			List<IdExpression> params = termConv.translateTerms(rd.getVariable());
			// must be done after translating the terms otherwise gives error
			BasicRule convertRule = convertRule(rulbody);
			logger.debug("converting Declaration " + rd.getName() + " of type " + rulbody.getClass().getName()
					+ " with paramters");
			// check if there is rule with the same name and numbers of
			// parameters
			result = new atgt.specification.statement.RuleDeclaration(name_rule, convertRule, params);
			// there is no other rule with the same name
		}
		rulemap.put(rd, result);
		return result;
	}

	/**
	 * convert a basic rule in its equivalent.
	 * 
	 * @param rulbody
	 *            the rulbody
	 * @return the basic rule
	 */
	atgt.specification.statement.BasicRule convertRule(Rule rulbody) {

		atgt.specification.statement.BasicRule stmt = null;

		if (rulbody instanceof asmeta.transitionrules.basictransitionrules.ConditionalRule) {

			stmt = computeConditionalRule((asmeta.transitionrules.basictransitionrules.ConditionalRule) rulbody);

		} else if (rulbody instanceof asmeta.transitionrules.basictransitionrules.BlockRule) {

			stmt = computeBlockRule((asmeta.transitionrules.basictransitionrules.BlockRule) rulbody);

		} else if (rulbody instanceof asmeta.transitionrules.basictransitionrules.UpdateRule) {
			asmeta.transitionrules.basictransitionrules.UpdateRule currule = (asmeta.transitionrules.basictransitionrules.UpdateRule) rulbody;
			// name_rule = currule.getRuleDeclaration().getName();
			return computeUpdateRule(currule);
		} else if (rulbody instanceof asmeta.transitionrules.basictransitionrules.ConditionalRule) {
			asmeta.transitionrules.basictransitionrules.ConditionalRule currule = (asmeta.transitionrules.basictransitionrules.ConditionalRule) rulbody;
			// name_rule = currule.getRuleDeclaration().getName();
			stmt = computeConditionalRule(currule);
		} else if (rulbody instanceof asmeta.transitionrules.derivedtransitionrules.CaseRule) {
			asmeta.transitionrules.derivedtransitionrules.CaseRule currule = (asmeta.transitionrules.derivedtransitionrules.CaseRule) rulbody;
			// name_rule = currule.getRuleDeclaration().getName();
			stmt = computeCaseRule(currule);
		} else if (rulbody instanceof asmeta.transitionrules.basictransitionrules.SkipRule) {
			return Skip.SKIP;
		} else if (rulbody instanceof asmeta.transitionrules.basictransitionrules.ChooseRule) {
			asmeta.transitionrules.basictransitionrules.ChooseRule currule = (asmeta.transitionrules.basictransitionrules.ChooseRule) rulbody;
			return computeChooseRule(currule);
		} else if (rulbody instanceof asmeta.transitionrules.basictransitionrules.MacroCallRule) {
			asmeta.transitionrules.basictransitionrules.MacroCallRule mcr = (asmeta.transitionrules.basictransitionrules.MacroCallRule) rulbody;
			// get the rule which is called
			asmeta.definitions.RuleDeclaration rde = mcr.getCalledMacro();
			String rname = rde.getName();
			// get the correspondign rule in atgt
			// note that the name is not enough since everal rules may have
			// several names
			RuleDeclaration ruleD = rulemap.get(rde);
			if (ruleD == null)
				logger.error("Macro call rule " + rname + " not found");
			assert ruleD.getName().equals(rde.getName());
			// assuming that the
			List<IdExpression> params = termConv.translateTerms(mcr.getParameters());
			stmt = new atgt.specification.statement.MacroCallRule(ruleD, params);
		} else if (rulbody instanceof asmeta.transitionrules.basictransitionrules.ForallRule) {
			// unroll the forall rule
			ForallRuleUnroller fru = new ForallRuleUnroller(ruleFactory);
			Rule newRule = fru.visit(rulbody);
			new AsmPrinter(new PrintWriter(System.out)).visit(newRule);
			return convertRule(newRule);
		} else {
			logger.error("Rule of class " + rulbody.getClass() + " not translated");
			throw new RuntimeException("Rule of class " + rulbody.getClass() + " not translated");
		}
		return stmt;
	}

	/**
	 * converts a conditional rule into a BasiCRule.
	 * 
	 * @param condrule
	 *            the condrule
	 * @return the basic rule
	 */
	private BasicRule computeConditionalRule(asmeta.transitionrules.basictransitionrules.ConditionalRule condrule) {
		// Recupero l'espressione della guardia
		BasicRule st;
		Term termGuard = condrule.getGuard();
		Expression guard = null;
		if (termGuard instanceof FunctionTerm) {
			guard = termConv.computeFunctionTerm((FunctionTerm) termGuard);
			logger.debug("compute guard as Function term " + guard);
		} else if (termGuard instanceof BooleanTerm) {
			guard = ecc.createIdExpression(((BooleanTerm) termGuard).getSymbol(), null);
			logger.debug("compute guard as BooleanTerm " + guard);
		} else if (termGuard instanceof VariableTerm) {
			guard = ecc.createIdExpression(((VariableTerm) termGuard).getName(), null);
			assert ((IdExpression) guard).getType() == BoolType.BOOLTYPE;
			logger.debug("compute guard as VariableTerm " + guard);
		} else {
			throw new RuntimeException(" " + termGuard.getClass());
		}
		// Devo trovare la then part
		BasicRule thenstmt = convertRule(condrule.getThenRule());
		if (condrule.getElseRule() != null) {
			BasicRule elsestmt = convertRule(condrule.getElseRule());
			st = new ConditionalRule(guard, thenstmt, elsestmt);
		} else {
			st = new ConditionalRule(guard, thenstmt);
		}
		return st;
	}

	private BasicRule computeCaseRule(CaseRule caseRule) {
		IdExpression idExp = (IdExpression) termConv.computeFunctionTerm((FunctionTerm) caseRule.getTerm());
		List<Rule> cases = caseRule.getCaseBranches();
		Rule otherwise = caseRule.getOtherwiseBranch();
		List<Term> values = caseRule.getCaseTerm();

		CaseStatement caseStmt = new CaseStatement(idExp);

		for (int i = 0; i < cases.size(); i++) {
			Expression value = termConv.computeTerm(values.get(i));
			BasicRule rule_i = convertRule(cases.get(i));
			caseStmt.addCase((IdExpression) value, rule_i);
		}
		if (otherwise != null) {
			caseStmt.addDefault(convertRule(otherwise));
		}
		return caseStmt;
	}

	/* transform an UpdateRule in a BasicRule */
	/**
	 * Compute update rule.
	 * 
	 * @param rl
	 *            the rl
	 * @param SP
	 * 
	 * @return the basic rule
	 */
	private BasicRule computeUpdateRule(asmeta.transitionrules.basictransitionrules.UpdateRule rl) {
		logger.debug("translating Update Rule " + rl.toString());
		BasicRule st = Skip.SKIP;
		String varS = null;
		List<? extends Expression> arg = null;
		Expression exp = null;
		Expression vr = null;

		Term lt = rl.getLocation();
		Term ut = rl.getUpdatingTerm();

		// l'originale e' nella forma lt := ut
		// The left-hand side of the update can be a location
		// term, or variable term
		if (lt instanceof LocationTerm) {
			vr = termConv.computeFunctionTerm((LocationTerm) lt);
			assert vr != null : ((LocationTerm) lt).getFunction().getName() + "(" + ((LocationTerm) lt).getArguments()
					+ ") is null ";
			// get the name
			varS = ((LocationTerm) lt).getFunction().getName();
			if (vr instanceof tgtlib.definitions.expression.FunctionTerm) {
				tgtlib.definitions.expression.FunctionTerm ft = (tgtlib.definitions.expression.FunctionTerm) vr;
				arg = ft.getArguments();
			} else {
				assert vr instanceof tgtlib.definitions.expression.IdExpression;
				arg = null;
			}
		} else {
			assert (lt instanceof VariableTerm);
			varS = ((VariableTerm) lt).getName();
		}
		// transform ut
		if (ut instanceof FunctionTerm) {
			exp = termConv.computeFunctionTerm((FunctionTerm) ut);
		}
		if (ut instanceof LocationTerm) {
			exp = termConv.computeFunctionTerm((FunctionTerm) ut);
		}
		if ((ut instanceof ConstantTerm) || (ut instanceof BooleanTerm) || (ut instanceof CharTerm)
				|| (ut instanceof IntegerTerm) || (ut instanceof NaturalTerm) || (ut instanceof RealTerm)
				|| (ut instanceof ComplexTerm) || (ut instanceof UndefTerm) || (ut instanceof EnumTerm)
				|| (ut instanceof StringTerm)) {
			exp = termConv.computeConstantTerm((ConstantTerm) ut);
		}
		if ((ut instanceof VariableTerm))// ||
		// (ut instanceof LogicalVariableTerm) ||
		// (ut instanceof LocationVariableTerm))
		{
			exp = termConv.translateTerm(ut);
			assert exp != null;
		}
		// get the variable
		if (arg == null) {
			IdExpression id = ecc.createIdExpression(varS, null);
			assert id != null : varS + " not found";
			Variable var = SP.getVariable(varS);
			if (var != null)
				st = new UpdateRule(var, exp);
			else {
				// logical variable
				LogicalVariable lvar = new LogicalVariable(id);
				st = new UpdateRule(lvar, exp);
			}
		} else {
			Function var = SP.getFunction(varS);
			assert var != null : varS + " not found";
			logger.debug("update rule function " + var + " exp " + exp + " args " + arg);
			st = new UpdateRule(var, exp, arg);
		}
		return st;
	}

	/**
	 * Compute block rule.
	 * 
	 * @param br
	 *            the br
	 * 
	 * @return the basic rule
	 */
	private BasicRule computeBlockRule(asmeta.transitionrules.basictransitionrules.BlockRule br) {
		DoStatement st = new DoStatement();
		for (Rule o : br.getRules()) {
			st.addStatement(convertRule(o));
		}
		return st;
	}

	/**
	 * 
	 * @param br
	 *            the br
	 * 
	 * @return the basic rule
	 */
	private BasicRule computeChooseRule(asmeta.transitionrules.basictransitionrules.ChooseRule br) {
		assert br.getIfnone() == null;
		BasicRule r = convertRule(br.getDoRule());
		// variable
		EList<VariableTerm> vars = br.getVariable();
		assert vars.size() == 1;
		IdExpression var = (IdExpression) termConv.computeTerm(vars.get(0));
		assert var.getType() != null;
		// condition
		Expression guard = termConv.translateTerm(br.getGuard());
		assert guard != null;
		// ranges
		EList<Term> ranges = br.getRanges();
		assert ranges.size() == 1;
		if (ranges.get(0) instanceof SetTerm) {
			SetTerm set = (SetTerm) ranges.get(0);
			List<Expression> terms = termConv.translateTerms(set.getTerm());
			return new ChooseRule(var, terms, guard, r);
		} else {
			DomainTerm domain = (DomainTerm) ranges.get(0);
			assert domain != null;
			Domain domain2 = ((PowersetDomain) domain.getDomain()).getBaseDomain();
			Type t = SP.getTypeFor(domain2.getName());
			assert t != null : domain2.getName() + " not found";
			// build the list of expressions in type t
			List<Expression> in = new ArrayList<Expression>();
			if (t instanceof BoundType) {
				BoundType bt = (BoundType) t;
				assert bt.getDelta() == null || bt.getDelta() == 1;
				for (int i = bt.getLow(); i <= bt.getUp(); i++) {
					in.add(ecc.createIdExpression(String.valueOf(i), null));
				}
			} else {
				assert t instanceof EnumType;
				EnumType et = (EnumType) t;
				in.addAll(et.allElements());
			}
			return new ChooseRule(var, in, guard, r);
		}
	}
}