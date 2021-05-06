package org.asmeta.flattener.term;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;
import java.util.function.BiFunction;

import org.apache.log4j.Logger;
import org.asmeta.flattener.statistics.Statistics;
import org.asmeta.flattener.util.StdlFunction;
import org.asmeta.parser.util.ReflectiveVisitor;
import org.asmeta.parser.util.AsmetaTermPrinter;
import org.asmeta.simulator.wrapper.RuleFactory;

import asmeta.definitions.Function;
import asmeta.definitions.domains.Domain;
import asmeta.structure.Asm;
import asmeta.terms.basicterms.BooleanTerm;
import asmeta.terms.basicterms.ConstantTerm;
import asmeta.terms.basicterms.FunctionTerm;
import asmeta.terms.basicterms.LocationTerm;
import asmeta.terms.basicterms.SetTerm;
import asmeta.terms.basicterms.Term;
import asmeta.terms.basicterms.TupleTerm;
import asmeta.terms.basicterms.UndefTerm;
import asmeta.terms.basicterms.VariableTerm;
import asmeta.terms.furtherterms.CaseTerm;
import asmeta.terms.furtherterms.ConditionalTerm;
import asmeta.terms.furtherterms.EnumTerm;
import asmeta.terms.furtherterms.ExistTerm;
import asmeta.terms.furtherterms.ExistUniqueTerm;
import asmeta.terms.furtherterms.FiniteQuantificationTerm;
import asmeta.terms.furtherterms.ForallTerm;
import asmeta.terms.furtherterms.IntegerTerm;
import asmeta.terms.furtherterms.LetTerm;
import asmeta.terms.furtherterms.MapTerm;
import asmeta.terms.furtherterms.NaturalTerm;
import asmeta.terms.furtherterms.SetCt;
import asmeta.terms.furtherterms.StringTerm;

public class TermSimplifier extends ReflectiveVisitor<Term> {
	static final Logger logger = Logger.getLogger(TermSimplifier.class);
	private static RuleFactory ruleFact;
	public static BooleanTerm falseT;
	public static BooleanTerm trueT;
	private AsmetaTermPrinter tp;
	//public final static TermSimplifier TS = new TermSimplifier();
	private DomainVisitor dv;
	private StdlFunction sl;

	static {
		ruleFact = new RuleFactory();
		falseT = ruleFact.createBooleanTerm();
		falseT.setSymbol("false");
		trueT = ruleFact.createBooleanTerm();
		trueT.setSymbol("true");
	}

	public TermSimplifier(DomainVisitor dv, StdlFunction sl) {
		tp = AsmetaTermPrinter.getAsmetaTermPrinter(false);
		this.dv = dv;
		this.sl = sl;
	}

	public Term visit(VariableTerm varTerm) {
		return varTerm;
	}

	public Term visit(NaturalTerm natTerm) {
		return natTerm;
	}

	public Term visit(BooleanTerm boolTerm) {
		return boolTerm;
	}

	public Term visit(EnumTerm enumTerm) {
		return enumTerm;
	}

	public Term visit(IntegerTerm intTerm) {
		return intTerm;
	}

	public Term visit(UndefTerm undefTerm) {
		return undefTerm;
	}

	public Term visit(LetTerm letTerm) {
		return letTerm;
	}

	public Term visit(SetCt setCt) {
		return setCt;
	}

	public Term visit(ConditionalTerm condTerm) {
		Term guard = condTerm.getGuard();
		if (guard instanceof BooleanTerm) {
			String value = ((BooleanTerm) guard).getSymbol();
			if (value.equals("true")) {
				logger.debug("ConditionalTerm simplified");
				//Statistics.getInstance().increaseValue("TS");
				return visit(condTerm.getThenTerm());
			} else if (value.equals("false") && condTerm.getElseTerm() != null) {
				logger.debug("ConditionalTerm simplified");
				//Statistics.getInstance().increaseValue("TS");
				return visit(condTerm.getElseTerm());
			}
		}
		return condTerm;
	}

	public Term visit(CaseTerm caseTerm) {
		// TODO
		return caseTerm;
	}

	public Term visit(FiniteQuantificationTerm fqtTerm) {
		return fqtTerm;
	}

	public Term visit(ForallTerm forallTerm) throws Exception {
		//return visit((FiniteQuantificationTerm) forallTerm);
		List<Term> operands = getGuardWithAllValues(forallTerm.getVariable(), forallTerm.getRanges(), forallTerm.getGuard());
		return sl.and(operands);
	}
	
	private List<Term> getGuardWithAllValues(List<VariableTerm> vars, List<Term> ranges, Term guard) throws Exception {
		List<Domain> domains = new ArrayList<Domain>();
		for(Term t: ranges) {
			domains.add(t.getDomain());
		}
		ArrayList<Term[]> result = new ArrayList<Term[]>();
		ValuesCombinator.combineValuesFromVars(vars, 0, result, new Stack<Term>(), dv.getDomainSet());
		List<Term> operands = new ArrayList<Term>();
		for(Term[] values: result) {
			ReplaceValueInTerm rvt = new ReplaceValueInTerm(values, vars);
			operands.add(rvt.visit(guard));
		}
		return operands;
	}

	public Term visit(ExistTerm existTerm) {
		return visit((FiniteQuantificationTerm) existTerm);
	}

	public Term visit(ExistUniqueTerm existUniqueTerm) {
		return visit((FiniteQuantificationTerm) existUniqueTerm);
	}

	public Term visit(TupleTerm tupleTerm) throws Exception {
		return tupleTerm;
	}

	public Term visit(LocationTerm locTerm) throws Exception {
		TupleTerm args = locTerm.getArguments();
		Term[] newArgs = null;
		if (args != null) {
			newArgs = new Term[args.getArity()];
			List<Term> terms = args.getTerms();
			for (int i = 0; i < terms.size(); i++) {
				newArgs[i] = visit(terms.get(i));
			}
		}
		return buildNewLocTerm(locTerm, newArgs);
		//return locTerm;
	}

	private Term buildNewLocTerm(LocationTerm locTerm, Term... args) {
		LocationTerm newLocTerm = ruleFact.createLocationTerm();
		newLocTerm.setFunction(locTerm.getFunction());
		newLocTerm.setDomain(locTerm.getDomain());
		if (args != null) {
			TupleTerm newTupleTerm = ruleFact.createTupleTerm();
			newTupleTerm.setArity(args.length);
			newTupleTerm.getTerms().addAll(Arrays.asList(args));
			newLocTerm.setArguments(newTupleTerm);
		}
		return newLocTerm;
	}

	public Term visit(FunctionTerm funcTerm) throws Exception {
		String funcName = funcTerm.getFunction().getName();
		TupleTerm args = funcTerm.getArguments();
		if (funcName.equals("not")) {
			return not(funcTerm);
		} else if (funcName.equals("eq")) {
			return eq(funcTerm);
		} else if (funcName.equals("neq")) {
			return neq(funcTerm);
		} else if (funcName.equals("and")) {
			return and(funcTerm);
		} else if (funcName.equals("or")) {
			return or(funcTerm);
		} else if (funcName.equals("le")) {
			return relationalOperator(funcTerm, (x, y) -> (x <= y));
		} else if (funcName.equals("lt")) {
			return relationalOperator(funcTerm, (x, y) -> (x < y));
		} else if (funcName.equals("ge")) {
			return relationalOperator(funcTerm, (x, y) -> (x >= y));
		} else if (funcName.equals("gt")) {
			return relationalOperator(funcTerm, (x, y) -> (x > y));
		} else if (funcName.equals("plus")) {
			return mathematicalOperator(funcTerm, (x, y) -> (x + y));
		} else if (funcName.equals("minus") && args.getArity() == 2) {
			return mathematicalOperator(funcTerm, (x, y) -> (x - y));
		} else if (funcName.equals("mult")) {
			return mathematicalOperator(funcTerm, (x, y) -> (x * y));
		} else if (funcName.equals("idiv")) {
			return mathematicalOperator(funcTerm, (x, y) -> (x / y));
		} else if (funcName.equals("mod")) {
			return mathematicalOperator(funcTerm, (x, y) -> (x % y));
		} else if (funcName.equals("max")) {
			return mathematicalOperator(funcTerm, (x, y) -> (Math.max(x, y)));
		} else if (funcName.equals("min")) {
			return mathematicalOperator(funcTerm, (x, y) -> (Math.min(x, y)));
		}
		Term[] newArgs = null;
		if (args != null) {
			newArgs = new Term[args.getArity()];
			List<Term> terms = args.getTerms();
			for (int i = 0; i < terms.size(); i++) {
				newArgs[i] = visit(terms.get(i));
			}
		}
		return buildNewFuncTerm(funcTerm, newArgs);
	}

	private Term eq(FunctionTerm funcTerm) {
		List<Term> args = funcTerm.getArguments().getTerms();
		assert args.size() == 2;
		Term firstArg = visit(args.get(0));
		Term secondArg = visit(args.get(1));
		if (firstArg instanceof ConstantTerm && secondArg instanceof ConstantTerm) {
			String v1 = ((ConstantTerm) firstArg).getSymbol();
			String v2 = ((ConstantTerm) secondArg).getSymbol();
			logger.debug("eq simplified");
			Statistics.getInstance().increaseValue("TS");
			return v1.equals(v2) ? trueT : falseT;
		}
		if (firstArg instanceof BooleanTerm && ((BooleanTerm) firstArg).getSymbol().equals("true")) {
			logger.debug("eq simplified");
			Statistics.getInstance().increaseValue("TS");
			return secondArg;
		}
		if (secondArg instanceof BooleanTerm && ((BooleanTerm) secondArg).getSymbol().equals("true")) {
			logger.debug("eq simplified");
			Statistics.getInstance().increaseValue("TS");
			return firstArg;
		}
		if (tp.visit(firstArg).equals(tp.visit(secondArg))) {
			// System.out.println(tp.visit(firstArg) + "\t" + tp.visit(secondArg) + "\t" +
			// tp.visit(firstArg).equals(tp.visit(secondArg)));
			logger.debug("eq simplified");
			Statistics.getInstance().increaseValue("TS");
			return trueT;
		}
		return buildNewFuncTerm(funcTerm, firstArg, secondArg);
	}

	private Term neq(FunctionTerm funcTerm) {
		List<Term> args = funcTerm.getArguments().getTerms();
		assert args.size() == 2;
		Term firstArg = visit(args.get(0));
		Term secondArg = visit(args.get(1));
		if (firstArg instanceof ConstantTerm && secondArg instanceof ConstantTerm) {
			String v1 = ((ConstantTerm) firstArg).getSymbol();
			String v2 = ((ConstantTerm) secondArg).getSymbol();
			logger.debug("neq simplified");
			Statistics.getInstance().increaseValue("TS");
			return !v1.equals(v2) ? trueT : falseT;
		}
		if (firstArg instanceof BooleanTerm && ((BooleanTerm) firstArg).getSymbol().equals("false")) {
			logger.debug("neq simplified");
			Statistics.getInstance().increaseValue("TS");
			return secondArg;
		}
		if (secondArg instanceof BooleanTerm && ((BooleanTerm) secondArg).getSymbol().equals("false")) {
			logger.debug("neq simplified");
			//Statistics.getInstance().increaseValue("TS");
			return firstArg;
		}
		return buildNewFuncTerm(funcTerm, firstArg, secondArg);
	}

	private Term not(FunctionTerm funcTerm) {
		List<Term> args = funcTerm.getArguments().getTerms();
		assert args.size() == 1;
		Term arg = visit(args.get(0));
		if (arg instanceof BooleanTerm) {
			String v = ((BooleanTerm) arg).getSymbol();
			if (v.equals("false")) {
				logger.debug("not simplified");
				//Statistics.getInstance().increaseValue("TS");
				return trueT;
			} else if (v.equals("true")) {
				logger.debug("not simplified");
				//Statistics.getInstance().increaseValue("TS");
				return falseT;
			}
		}
		if(arg instanceof FunctionTerm) {
			if(((FunctionTerm) arg).getFunction().getName().equals("not")) {
				return ((FunctionTerm) arg).getArguments().getTerms().get(0);
			}
		}
		return funcTerm;
	}

	private Term and(FunctionTerm funcTerm) {
		List<Term> args = funcTerm.getArguments().getTerms();
		assert args.size() == 2;
		Term firstArg = visit(args.get(0));
		String v1 = null;
		Term secondArg = visit(args.get(1));
		String v2 = null;
		if (firstArg instanceof BooleanTerm) {
			v1 = ((BooleanTerm) firstArg).getSymbol();
		}
		if (secondArg instanceof BooleanTerm) {
			v2 = ((BooleanTerm) secondArg).getSymbol();
		}
		if (v1 != null) {
			if (v1.equals("false")) {
				logger.debug("and simplified");
				//Statistics.getInstance().increaseValue("TS");
				return falseT;
			} else if (v1.equals("true")) {
				logger.debug("and simplified");
				//Statistics.getInstance().increaseValue("TS");
				return secondArg;
			}
		} else if (v2 != null) {
			if (v2.equals("false")) {
				logger.debug("and simplified");
				//Statistics.getInstance().increaseValue("TS");
				return falseT;
			} else if (v2.equals("true")) {
				logger.debug("and simplified");
				//Statistics.getInstance().increaseValue("TS");
				return firstArg;
			}
		}
		return buildNewFuncTerm(funcTerm, firstArg, secondArg);
	}

	private Term buildNewFuncTerm(FunctionTerm funcTerm, Term... args) {
		FunctionTerm newFunctTerm = ruleFact.createFunctionTerm();
		newFunctTerm.setFunction(funcTerm.getFunction());
		newFunctTerm.setDomain(funcTerm.getDomain());
		if (args != null) {
			TupleTerm newTupleTerm = ruleFact.createTupleTerm();
			newTupleTerm.setArity(args.length);
			newTupleTerm.getTerms().addAll(Arrays.asList(args));
			newFunctTerm.setArguments(newTupleTerm);
		}
		return newFunctTerm;
	}

	private Term or(FunctionTerm funcTerm) {
		List<Term> args = funcTerm.getArguments().getTerms();
		assert args.size() == 2;
		Term firstArg = visit(args.get(0));
		String v1 = null;
		Term secondArg = visit(args.get(1));
		String v2 = null;
		if (firstArg instanceof BooleanTerm) {
			v1 = ((BooleanTerm) firstArg).getSymbol();
		}
		if (secondArg instanceof BooleanTerm) {
			v2 = ((BooleanTerm) secondArg).getSymbol();
		}
		if (v1 != null) {
			if (v1.equals("true")) {
				logger.debug("or simplified");
				Statistics.getInstance().increaseValue("TS");
				return trueT;
			} else if (v1.equals("false")) {
				if (v2 != null) {
					logger.debug("or simplified");
					Statistics.getInstance().increaseValue("TS");
					return secondArg;
				}
			}
		} else if (v2 != null) {
			if (v2.equals("true")) {
				logger.debug("or simplified");
				//Statistics.getInstance().increaseValue("TS");
				return trueT;
			} else if (v2.equals("false")) {
				logger.debug("or simplified");
				//Statistics.getInstance().increaseValue("TS");
				return firstArg;
			}
		}
		return buildNewFuncTerm(funcTerm, firstArg, secondArg);
	}

	private Term relationalOperator(FunctionTerm funcTerm, BiFunction<Integer, Integer, Boolean> intRelation) {
		List<Term> args = funcTerm.getArguments().getTerms();
		assert args.size() == 2;
		Term firstArg = visit(args.get(0));
		Term secondArg = visit(args.get(1));
		if ((firstArg instanceof IntegerTerm || firstArg instanceof NaturalTerm)
				&& (secondArg instanceof IntegerTerm || secondArg instanceof NaturalTerm)) {
			int v1 = Integer.parseInt(((ConstantTerm) firstArg).getSymbol());
			int v2 = Integer.parseInt(((ConstantTerm) secondArg).getSymbol());
			logger.debug("rel operator simplified");
			//Statistics.getInstance().increaseValue("TS");
			if (intRelation.apply(v1, v2)) {
				return trueT;
			} else {
				return falseT;
			}
		}
		return buildNewFuncTerm(funcTerm, firstArg, secondArg);
	}

	private Term mathematicalOperator(FunctionTerm funcTerm, BiFunction<Integer, Integer, Integer> intRelation) {
		List<Term> args = funcTerm.getArguments().getTerms();
		assert args.size() == 2;
		Term firstArg = visit(args.get(0));
		Term secondArg = visit(args.get(1));
		if ((firstArg instanceof IntegerTerm || firstArg instanceof NaturalTerm)
				&& (secondArg instanceof IntegerTerm || secondArg instanceof NaturalTerm)) {
			int v1 = Integer.parseInt(((ConstantTerm) firstArg).getSymbol());
			int v2 = Integer.parseInt(((ConstantTerm) secondArg).getSymbol());
			IntegerTerm intTerm = ruleFact.createIntegerTerm();
			intTerm.setSymbol(intRelation.apply(v1, v2).toString());
			logger.debug("math operator simplified");
			//Statistics.getInstance().increaseValue("TS");
			return intTerm;
		}
		return buildNewFuncTerm(funcTerm, firstArg, secondArg);
	}

	public Term visit(MapTerm mapTerm) {
		return mapTerm;
	}

	public Term visit(SetTerm setTerm) {
		return setTerm;
	}

	public Term visit(StringTerm strTerm) {
		return strTerm;
	}

	static Asm getAsm(Function function) {
		return function.getSignature().getHeaderSection().getAsm();
	}
}
