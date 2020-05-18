package org.asmeta.toyices;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import org.asmeta.parser.Defs;
import org.asmeta.parser.util.ReflectiveVisitor;

import asmeta.definitions.Function;
import asmeta.definitions.domains.ConcreteDomain;
import asmeta.definitions.domains.Domain;
import asmeta.definitions.domains.DomainsFactory;
import asmeta.definitions.domains.MapDomain;
import asmeta.structure.DomainDefinition;
import asmeta.structure.StructureFactory;
import asmeta.terms.basicterms.BooleanTerm;
import asmeta.terms.basicterms.DomainTerm;
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
import asmeta.terms.furtherterms.ForallTerm;
import asmeta.terms.furtherterms.IntegerTerm;
import asmeta.terms.furtherterms.MapTerm;
import asmeta.terms.furtherterms.NaturalTerm;

public class TermVisitor extends ReflectiveVisitor<String> {
	//private static Logger logger = LogManager.getLogger(TermVisitor.class.getName());
	private YicesModel yicesModel;
	boolean isCurrentState;
	Map<String, String> varValues;
	Map<String, Term> varValuesTerms;
	String selfAgent;

	public TermVisitor(YicesModel yicesModel) {
		this.yicesModel = yicesModel;
		isCurrentState = true;
		varValues = new HashMap<String, String>();
		varValuesTerms = new HashMap<String, Term>();
	}

	public String visit(Term term) {
		return visit((Object) term);
	}

	public String visit(VariableTerm variable) {
		String varName = variable.getName();
		//String varName = variable.getName() + stateNumber;
		if(varValues.containsKey(varName)) {
			return varValues.get(varName);
		}
		else {
			return varName;
		}
	}

	public String visit(FunctionTerm funcTerm) throws Exception {
		//System.err.println("funcTerm " + funcTerm.getFunction().getName());
		Function func = funcTerm.getFunction();
		String funcName = func.getName();
		TupleTerm tupleArgs = funcTerm.getArguments();
		List<Term> args;
		if(tupleArgs != null) {
			args = funcTerm.getArguments().getTerms();
		}
		else {
			args = new ArrayList<Term>();
		}
		if(Defs.isDerived(func)) {
			//System.err.println(funcName + stateNumber);
			List<String> argsStr = new ArrayList<String>();
			for(Term arg: args) {
				argsStr.add(visit(arg));
			}
			return Utils.location(Utils.getFuncName(func) + Utils.currentStateSymbol, argsStr);
		}
		if(Utils.isAgentConstant(func)) {
			return func.getName();
		}
		int numOfArgs = args.size();
		switch(funcName) {
			case "eq":
				assert numOfArgs == 2;
				return yicesModel.eq(visit(args.get(0)), visit(args.get(1)));
			case "neq":
				assert numOfArgs == 2;
				return yicesModel.neq(visit(args.get(0)), visit(args.get(1)));
			case "not":
				assert numOfArgs == 1;
				return yicesModel.not(visit(args.get(0)));
			case "and":
				assert numOfArgs == 2;
				return yicesModel.and(visit(args.get(0)), visit(args.get(1)));
			case "or":
				assert numOfArgs == 2;
				return yicesModel.or(visit(args.get(0)), visit(args.get(1)));
			case "implies":
				assert numOfArgs == 2;
				return yicesModel.implies(visit(args.get(0)), visit(args.get(1)));
			case "ge":
				assert numOfArgs == 2;
				return yicesModel.ge(visit(args.get(0)), visit(args.get(1)));
			case "gt":
				assert numOfArgs == 2;
				return yicesModel.gt(visit(args.get(0)), visit(args.get(1)));
			case "le":
				assert numOfArgs == 2;
				return yicesModel.le(visit(args.get(0)), visit(args.get(1)));
			case "lt":
				assert numOfArgs == 2;
				return yicesModel.lt(visit(args.get(0)), visit(args.get(1)));
			case "plus":
				assert numOfArgs == 2;
				return yicesModel.plus(visit(args.get(0)), visit(args.get(1)));
			case "minus":
				assert numOfArgs == 2 || numOfArgs == 1;
				if(numOfArgs == 2) {
					return yicesModel.minus(visit(args.get(0)), visit(args.get(1)));
				}
				else if(numOfArgs == 1) {
					return yicesModel.unaryMinus(visit(args.get(0)));
				}
			case "mult":
				assert numOfArgs == 2;
				return yicesModel.mult(visit(args.get(0)), visit(args.get(1)));
			case "idiv":
				assert numOfArgs == 2;
				return yicesModel.intDiv(visit(args.get(0)), visit(args.get(1)));
			case "mod":
				assert numOfArgs == 2;
				return yicesModel.mod(visit(args.get(0)), visit(args.get(1)));
			case "at":
				assert numOfArgs == 2;
				Term firstArg = args.get(0);
				Term secondArg = args.get(1);
				if(firstArg instanceof MapTerm && secondArg instanceof TupleTerm) {
					return visitAt((MapTerm)firstArg, (TupleTerm)secondArg);
				}
			case "isUndef":
				assert numOfArgs == 1;
				return yicesModel.eq(visit(args.get(0)), yicesModel.domainUndefValue.get(args.get(0).getDomain().getName()));
			case "isDef":
				assert numOfArgs == 1;
				Term arg = args.get(0);
				return yicesModel.neq(visit(arg), yicesModel.domainUndefValue.get(arg.getDomain().getName()));
			default:
				//throw new Error("function " + func.getName() + " unknown");
		}
		if(Defs.isStatic(func)) {
			//System.err.println(funcName + stateNumber);
			List<String> argsStr = new ArrayList<String>();
			for(Term arg: args) {
				argsStr.add(visit(arg));
			}
			//return Utils.location(funcName + stateNumber, argsStr);//several visits
			return Utils.location(Utils.getFuncName(func) + Utils.currentStateSymbol, argsStr);
		}
		throw new Error("function " + func.getName() + " unknown");
	}

	//TODO sistemare: aggiungere supporto dell'undef
	private String visitAt(MapTerm mapTerm, TupleTerm tupleTerm) {
		String targetDomainName = ((MapDomain)mapTerm.getDomain()).getTargetDomain().getName();
		ArrayList<String> args = new ArrayList<String>();
		for(Term t: tupleTerm.getTerms()) {
			//System.err.println(visit(t));
			args.add(visit(t));
		}
		ArrayList<String[]> couples = new ArrayList<String[]>();
		for(TupleTerm pair: mapTerm.getPair()) {
			List<Term> pairTerms = pair.getTerms();
			assert pairTerms.size() == 2;
			TupleTerm first = (TupleTerm)pairTerms.get(0);
			Term second = pairTerms.get(1);
			ArrayList<String> list = new ArrayList<String>();
			List<Term> termsList = first.getTerms();
			for(int i = 0; i < termsList.size(); i++) {
				//System.err.println(args.get(i) + "="  + visit(termsList.get(i)));
				list.add(yicesModel.eq(args.get(i), visit(termsList.get(i))));
			}
			couples.add(new String[]{yicesModel.and(list), visit(second)});
			//System.err.println(visit(second));
		}
		//funziona solo se l'at è completo
		/*String cond = couples.get(couples.size() - 1)[1];
		for(int i = couples.size() - 2; i >= 0; i--) {
			String[] couple = couples.get(i);
			//System.err.println(Arrays.toString(couple));
			cond = yicesModel.ifThenElse(couple[0], couple[1], cond);
		}*/
		//dovrebbe andare bene sempre
		//String cond = "undef";
		String cond = yicesModel.domainUndefValue.get(targetDomainName);
		for(int i = couples.size() - 1; i >= 0; i--) {
			String[] couple = couples.get(i);
			//System.err.println(Arrays.toString(couple));
			cond = yicesModel.ifThenElse(couple[0], couple[1], cond);
		}
		return cond;
	}

	public String visit(TupleTerm tupleTerm) {
		List<Term> terms = tupleTerm.getTerms();
		if(terms.size() == 1) {
			return visit(terms.get(0));
		}
		else {
			//n-ary functions with n > 1
			StringBuilder sb = new StringBuilder();
			if(Utils.nAryFunctionsAsTuples) {
				//using tuples
				sb.append("(mk-tuple");
				for(Term term: terms) {
					sb.append(" ").append(visit(terms));
				}
				sb.append(")");
			}
			else {
				//using curryfication
				sb.append(visit(terms.get(0)));
				for(int i = 1; i < terms.size(); i++) {
					sb.append(" ").append(visit(terms.get(i)));
				}
			}
			return sb.toString();
		}
	}

	public String visit(LocationTerm locTerm) {
		//System.err.println("locTerm " + locTerm.getFunction().getName());
		Function func = locTerm.getFunction();
		String funcName = Utils.getFuncName(func);
		if(!funcName.equals("self")) {
			if(isCurrentState) {
				funcName = funcName + Utils.currentStateSymbol;
			}
			else {
				funcName = funcName + Utils.nextStateSymbol;
			}
			if(func.getArity() == 0) {
				return funcName;
			}
			else {
				String locationYices = "(" + funcName + " " + visit(locTerm.getArguments()) + ")";
				return locationYices;
			}
		}
		else {
			return selfAgent;
		}
	}

	public String visitLocationUpdateRule(LocationTerm location) {
		Function func = location.getFunction();
		String funcName = Utils.getFuncName(func);
		funcName = funcName + Utils.nextStateSymbol;
		if(func.getArity() == 0) {
			return funcName;
		}
		else {
			String locationYices = "(" + funcName + " " + visit(location.getArguments()) + ")";
			return locationYices;
		}
	}

	public String visit(NaturalTerm number) {
		String symbol = number.getSymbol();
		//A natural number terminates with an "n".
		//In order to have the integer representation,
		//we must remove the "n".
		symbol = number.getSymbol().substring(0, symbol.length() - 1);
		return symbol;
	}

	public String visit(IntegerTerm number) {
		return number.getSymbol();
	}

	public String visit(EnumTerm term) {
		//String domainName = term.getDomain().getName();
		//Pointer enumDomain = yicesModel.enumDomains.get(domainName);
		String symbol = term.getSymbol();
		return symbol;
	}

	public String visit(BooleanTerm bool) {
		if(bool.getSymbol().equals("true")) {
			return "true";
		}
		else {
			return "false";
		}
	}

	public String visit(UndefTerm undef) {
		throw new Error("undef not supported yet.");
	}

	public String visit(ExistTerm existTerm) {
		ArrayList<String> operands = getGuardsList(existTerm.getGuard(), existTerm.getVariable(), existTerm.getRanges());
		return yicesModel.or(operands);
	}

	public String visit(ExistUniqueTerm existUniqueTerm) {
		ArrayList<String> operands = getGuardsList(existUniqueTerm.getGuard(), existUniqueTerm.getVariable(), existUniqueTerm.getRanges());
		ArrayList<String> notOperands = new ArrayList<String>();
		for(String operand: operands) {
			notOperands.add(yicesModel.not(operand));
		}
		ArrayList<String> ands = new ArrayList<String>();
		for(int i = 0; i < operands.size(); i++) {
			ArrayList<String> newNotOperands = new ArrayList<String>();
			for(String notOperand: notOperands) {
				newNotOperands.add(notOperand);
			}
			newNotOperands.set(i, operands.get(i));
			//newNotOperands.remove(i);
			//newNotOperands.add(i, operands.get(i));
			String and = yicesModel.and(newNotOperands);
			ands.add(and);
		}
		return yicesModel.or(ands);
	}

	public String visit(ForallTerm forallTerm) {
		ArrayList<String> operands = getGuardsList(forallTerm.getGuard(), forallTerm.getVariable(), forallTerm.getRanges());
		return yicesModel.and(operands);
	}

	private ArrayList<String> getGuardsList(Term guard, List<VariableTerm> vars, List<Term> rangeTerms) {
		List<Domain> domains = new ArrayList<Domain>();
		for(int i = 0; i < vars.size(); i++) {
			VariableTerm var = vars.get(i);
			Term rangeTerm = rangeTerms.get(i);
			if(rangeTerm instanceof DomainTerm) {
				domains.add(var.getDomain());
			}
			else if(rangeTerm instanceof SetTerm) {
				SetTerm set = (SetTerm)rangeTerm;
				DomainDefinition domDef = StructureFactory.eINSTANCE.createDomainDefinition();
				ConcreteDomain concrDom = DomainsFactory.eINSTANCE.createConcreteDomain();
				concrDom.setDefinition(domDef);
				domDef.setBody(set);
				domains.add(concrDom);
			}
			else {
				throw new Error("Range " + rangeTerm.getClass().getCanonicalName() + " not exptected.");
			}
		}
		ArrayList<String[]> valuesCombinations = new ArrayList<String[]>();
		Utils.combineValues(domains, 0, valuesCombinations , new Stack<String>(), yicesModel);
		ArrayList<String> operands = new ArrayList<String>();
		for(String[] values: valuesCombinations) {
			for(int i = 0; i < vars.size(); i++) {
				varValues.put(vars.get(i).getName(), values[i]);
			}
			operands.add(visit(guard));
		}
		return operands;
	}

	public String visit(SetTerm set) {
		Integer numDomElements = set.getSize();
		List<Term> elements = set.getTerm();
		Integer[] intValues = new Integer[numDomElements];
		int min = Integer.MAX_VALUE;
		int max = Integer.MIN_VALUE;
		for(int i = 0; i < numDomElements; i++) {
			Term element = elements.get(i);
			String termStr = visit(element);
			int value = Integer.parseInt(termStr);
			intValues[i] = value;
			if(value < min) {
				min = value;
			}
			if(value > max) {
				max = value;
			}
		}
		if((max - min) == (numDomElements  - 1)) {
			return "(subrange " + min + " " + max + ")";
		}
		else {
			assert (numDomElements  - 1) < (max - min);
			StringBuilder sb = new StringBuilder("(subtype (n::int) (or");
			for(Integer i: intValues) {
				sb.append(" ");
				sb.append(yicesModel.eq("n", String.valueOf(i)));
			}
			sb.append("))");
			return sb.toString();
		}
	}

	public String visit(ConditionalTerm condTerm) {
		String condYices = visit(condTerm.getGuard());
		String thenYices = visit(condTerm.getThenTerm());
		Term elseTerm = condTerm.getElseTerm();
		String elseYices;
		if(elseTerm != null) {
			elseYices = visit(elseTerm);
		}
		else {
			elseYices = yicesModel.domainUndefValue.get(condTerm.getDomain().getName());
		}
		return yicesModel.ifThenElse(condYices, thenYices, elseYices);
	}

	public String visit(CaseTerm caseTerm) {
		String comparedTermYices = visit(caseTerm.getComparedTerm());
		List<Term> comparingTerms = caseTerm.getComparingTerm();
		List<Term> resultTerms = caseTerm.getResultTerms();
		Term otherwiseTerm = caseTerm.getOtherwiseTerm();
		String caseTermYices; 
		if(otherwiseTerm != null) {
			caseTermYices = visit(otherwiseTerm);
		}
		else {
			caseTermYices = yicesModel.domainUndefValue.get(caseTerm.getDomain().getName());
			//caseTermYices = visit(resultTerms.get(comparingTerms.size() - 1));//this should be never taken!!!
		}
		for(int i = comparingTerms.size() - 1; i >= 0; i--) {
			String eq = yicesModel.eq(comparedTermYices, visit(comparingTerms.get(i)));
			String resultTermYices = visit(resultTerms.get(i));
			caseTermYices = yicesModel.ifThenElse(eq, resultTermYices, caseTermYices);
		}
		return caseTermYices;
	}

	public String visitFunctionDefinition(String funcName, List<VariableTerm> varsTerms, Term defBody) {
		String body = visit(defBody);
		String funcDefYices;
		if(varsTerms.size() > 0) {
			List<String> vars = new ArrayList<String>();
			//List<String> domains = new ArrayList<String>();
			List<Domain> doms = new ArrayList<Domain>();
			for(VariableTerm varTerm: varsTerms) {
				vars.add(visit(varTerm));
				//domains.add(Utils.parseDomainName(varTerm.getDomain()));
				doms.add(varTerm.getDomain());
			}
			return forallEq(funcName, defBody, vars, doms);
		}
		else {
			funcDefYices = yicesModel.eq(funcName, body);
		}
		return funcDefYices;
	}

	private String forallEq(String funcName, Term defBody, List<String> vars, List<Domain> doms) {
		String loc;
		//with forall
		//funcDefYices = yicesModel.forall(vars, domains, yicesModel.eq(loc, body));
		ArrayList<String[]> values = new ArrayList<String[]>();
		ArrayList<String> operands = new ArrayList<String>();
		Utils.combineValues(doms, 0, values, new Stack<String>(), yicesModel);
		for(String[] value: values) {
			for(int i = 0; i < vars.size(); i++) {
				varValues.put(vars.get(i), value[i]);
			}
			loc = Utils.location(funcName, value);
			operands.add(yicesModel.eq(loc, visit(defBody)));
			for(int i = 0; i < vars.size(); i++) {
				varValues.remove(vars.get(i));
			}
		}
		return yicesModel.and(operands);
	}

	public String forallEqConst(String funcName, String constant, List<Domain> doms) {
		String loc;
		ArrayList<String[]> values = new ArrayList<String[]>();
		ArrayList<String> operands = new ArrayList<String>();
		Utils.combineValues(doms, 0, values, new Stack<String>(), yicesModel);
		for(String[] value: values) {
			loc = Utils.location(funcName, value);
			operands.add(yicesModel.eq(loc, constant));
		}
		return yicesModel.and(operands);
	}

	public static Integer[] getSetValues(SetTerm set) {
		Integer numDomElements = set.getSize();
		List<Term> elements = set.getTerm();
		Integer[] intValues = new Integer[numDomElements];
		int min = Integer.MAX_VALUE;
		int max = Integer.MIN_VALUE;
		for(int i = 0; i < numDomElements; i++) {
			Term element = elements.get(i);
			assert (element instanceof IntegerTerm) || (element instanceof FunctionTerm && ((FunctionTerm)element).getFunction().getName().equals("minus")): element;
			//String termStr = visit(element);
			String termStr = null;
			if(element instanceof IntegerTerm) {
				termStr = ((IntegerTerm)element).getSymbol();
			}
			else if (element instanceof FunctionTerm) {
				termStr = "-" + ((IntegerTerm)(((FunctionTerm)element).getArguments().getTerms().get(0))).getSymbol();
			}
			else {
				throw new Error("It should be a number");
			}
			int value = Integer.parseInt(termStr);
			intValues[i] = value;
			if(value < min) {
				min = value;
			}
			if(value > max) {
				max = value;
			}
		}
		return intValues;
	}
}