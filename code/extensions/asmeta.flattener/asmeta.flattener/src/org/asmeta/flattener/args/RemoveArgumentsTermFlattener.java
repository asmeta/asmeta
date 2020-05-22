package org.asmeta.flattener.args;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.asmeta.parser.util.ReflectiveVisitor;
import org.asmeta.simulator.value.Value;
import org.asmeta.simulator.wrapper.RuleFactory;

import asmeta.definitions.Function;
import asmeta.structure.Asm;
import asmeta.terms.basicterms.BooleanTerm;
import asmeta.terms.basicterms.FunctionTerm;
import asmeta.terms.basicterms.LocationTerm;
import asmeta.terms.basicterms.SetTerm;
import asmeta.terms.basicterms.Term;
import asmeta.terms.basicterms.TupleTerm;
import asmeta.terms.basicterms.UndefTerm;
import asmeta.terms.basicterms.VariableKind;
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

public class RemoveArgumentsTermFlattener extends ReflectiveVisitor<Term> {
	private static final Logger logger = Logger.getLogger(RemoveArgumentsTermFlattener.class);
	private boolean inLocationArguments;
	private RuleFactory ruleFact;
	private LinkedHashMap<VariableTerm, Term> mapForLet;
	private static int counterForLogicVars;
	public static ArrayList<String> stdlFuncs;
	public static boolean REUSE_VAR = true;

	static {
		stdlFuncs = new ArrayList<String>();
		stdlFuncs.add("abs");
		stdlFuncs.add("max");
		stdlFuncs.add("min");
		stdlFuncs.add("isDef");
		stdlFuncs.add("isUndef");
		stdlFuncs.add("self");
		stdlFuncs.add("program");
	}

	public RemoveArgumentsTermFlattener() {
		this(new RuleFactory());
	}

	public RemoveArgumentsTermFlattener(RuleFactory ruleFact) {
		this.ruleFact = ruleFact;
		mapForLet = new LinkedHashMap<VariableTerm, Term>();
		inLocationArguments = false;
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
		// TODO also the let term should be checked
		return letTerm;
	}
	
	public Term visit(SetCt setCt) {
		return setCt;
	}

	
	public Term visit(ConditionalTerm condTerm) {
		condTerm.setGuard(visit(condTerm.getGuard()));
		condTerm.setThenTerm(visit(condTerm.getThenTerm()));
		Term elseTerm = condTerm.getElseTerm();
		if (elseTerm != null) {
			condTerm.setElseTerm(visit(elseTerm));
		}
		return condTerm;
	}

	public Term visit(CaseTerm caseTerm) {
		caseTerm.setComparedTerm(visit(caseTerm.getComparedTerm()));
		Term oTerm = caseTerm.getOtherwiseTerm();
		if (oTerm != null) {
			caseTerm.setOtherwiseTerm(visit(oTerm));
		}
		List<Term> newResultTerms = new ArrayList<Term>();
		for (Term rt : caseTerm.getResultTerms()) {
			newResultTerms.add(visit(rt));
		}
		caseTerm.getResultTerms().clear();
		caseTerm.getResultTerms().addAll(newResultTerms);
		return caseTerm;
	}

	public Term visit(FiniteQuantificationTerm fqtTerm) {
		RemoveArgumentsTermFlattener tf = new RemoveArgumentsTermFlattener(ruleFact);
		Term newGuard = tf.visit(fqtTerm.getGuard());
		LinkedHashMap<VariableTerm, Term> guardMap = tf.getMapForLet();
		if (guardMap.size() > 0) {
			fqtTerm.setGuard(buildLetTerm(guardMap, newGuard));
		}
		return fqtTerm;
	}

	public LetTerm buildLetTerm(Map<VariableTerm, Term> map, Term term) {
		LetTerm letTerm = ruleFact.createLetTerm();
		letTerm.setBody(term);
		letTerm.setDomain(term.getDomain());
		letTerm.getVariable().addAll(map.keySet());
		letTerm.getAssignmentTerm().addAll(map.values());
		return letTerm;
	}

	public Term visit(ForallTerm forallTerm) {
		return visit((FiniteQuantificationTerm) forallTerm);
	}

	public Term visit(ExistTerm existTerm) {
		return visit((FiniteQuantificationTerm) existTerm);
	}

	public Term visit(ExistUniqueTerm existUniqueTerm) {
		return visit((FiniteQuantificationTerm) existUniqueTerm);
	}

	public Term visit(TupleTerm tupleTerm) throws Exception {
		List<Term> newTerms = new ArrayList<Term>();
		for (Term term : tupleTerm.getTerms()) {
			newTerms.add(visit(term));
		}
		TupleTerm newTupleTerm = ruleFact.createTupleTerm();
		newTupleTerm.setArity(tupleTerm.getArity());
		newTupleTerm.getTerms().addAll(newTerms);
		return newTupleTerm;
	}

	public Term visit(LocationTerm locTerm) throws Exception {
		String locName = getFunctionName(locTerm.getFunction());
		logger.debug("visit locTerm " + locName);
		String funcSymbol = parsename(locName);
		if (stdlFuncs.contains(locName) || hasEvaluateVisitor(funcSymbol)) {
			TupleTerm tt = locTerm.getArguments();
			if (tt != null && tt.getArity() > 0) {
				List<Term> args = new ArrayList<Term>();
				for (Term arg : tt.getTerms()) {
					args.add(visit(arg));
				}
				tt.getTerms().clear();
				tt.getTerms().addAll(args);
			}
			return locTerm;
		}
		logger.debug("visit locTerm " + locName);
		if (inLocationArguments) {
			if(REUSE_VAR) {
				if(mapForLet.values().contains(locTerm)) {
					for(VariableTerm v: mapForLet.keySet()) {
						if(mapForLet.get(v).equals(locTerm)) {
							return v;
						}
					}
				}
			}
			VariableTerm vt = ruleFact.createVariableTerm();
			vt.setName("$var_" + (counterForLogicVars++));
			vt.setKind(VariableKind.LOGICAL_VAR);
			vt.setDomain(locTerm.getFunction().getCodomain());
			TupleTerm tt = locTerm.getArguments();
			if (tt != null && tt.getArity() > 0) {
				List<Term> args = new ArrayList<Term>();
				for (Term arg : tt.getTerms()) {
					args.add(visit(arg));
				}
				tt.getTerms().clear();
				tt.getTerms().addAll(args);
			}
			logger.debug("var " + vt.getName() + " for " + locName);
			mapForLet.put(vt, locTerm);
			return vt;
		} else {
			TupleTerm tt = locTerm.getArguments();
			if (tt != null && tt.getArity() > 0) {
				List<Term> args = new ArrayList<Term>();
				inLocationArguments = true;
				for (Term arg : tt.getTerms()) {
					args.add(visit(arg));
				}
				inLocationArguments = false;
				tt.getTerms().clear();
				tt.getTerms().addAll(args);
			}
			return locTerm;
		}
	}

	public Term visit(FunctionTerm funcTerm) throws Exception {
		String funcName = getFunctionName(funcTerm.getFunction());
		logger.debug("visit funcTerm " + funcName);
		String funcSymbol = parsename(funcName);
		if (stdlFuncs.contains(funcName) || hasEvaluateVisitor(funcSymbol)) {
			TupleTerm tt = funcTerm.getArguments();
			if (tt != null && tt.getArity() > 0) {
				List<Term> args = new ArrayList<Term>();
				for (Term arg : tt.getTerms()) {
					args.add(visit(arg));
				}
				tt.getTerms().clear();
				tt.getTerms().addAll(args);
			}
			return funcTerm;
		}
		if (inLocationArguments) {
			if(REUSE_VAR) {
				if(mapForLet.values().contains(funcTerm)) {
					for(VariableTerm v: mapForLet.keySet()) {
						if(mapForLet.get(v).equals(funcTerm)) {
							return v;
						}
					}
				}
			}
			VariableTerm vt = ruleFact.createVariableTerm();
			vt.setName("$var_" + (counterForLogicVars++));
			vt.setKind(VariableKind.LOGICAL_VAR);
			vt.setDomain(funcTerm.getFunction().getCodomain());
			TupleTerm tt = funcTerm.getArguments();
			if (tt != null && tt.getArity() > 0) {
				List<Term> args = new ArrayList<Term>();
				for (Term arg : tt.getTerms()) {
					args.add(visit(arg));
				}
				tt.getTerms().clear();
				tt.getTerms().addAll(args);
			}
			logger.debug("var " + vt.getName() + " for " + funcTerm.getFunction().getName());
			mapForLet.put(vt, funcTerm);
			return vt;
		} else {
			TupleTerm tt = funcTerm.getArguments();
			if (tt != null && tt.getArity() > 0) {
				List<Term> args = new ArrayList<Term>();
				inLocationArguments = true;
				for (Term arg : tt.getTerms()) {
					args.add(visit(arg));
				}
				inLocationArguments = false;
				tt.getTerms().clear();
				tt.getTerms().addAll(args);
			}
			return funcTerm;
		}
	}

	// TODO check whether it is correct. should we also visit the map term?
	public Term visit(MapTerm mapTerm) {
		return mapTerm;
	}

	public Term visit(SetTerm setTerm) {
		return setTerm;
	}

	public Term visit(StringTerm strTerm) {
		return strTerm;
	}

	public LinkedHashMap<VariableTerm, Term> getMapForLet() {
		return mapForLet;
	}

	public void setInLocationArguments(boolean inLocationArguments) {
		this.inLocationArguments = inLocationArguments;
	}

	public static String getLocationName(Function function, Value[] values) {
		StringBuilder locationName = new StringBuilder();
		locationName.append(getFunctionName(function));
		for (Value value : values) {
			locationName.append("_" + value.toString().toUpperCase());
		}
		return locationName.toString();
	}

	public static String getFunctionName(Function function) {
		String functionName = function.getName();
		/*
		 * String asmName = getAsmName(function); if (!belongsToMainAsm(function) &&
		 * !asmName.equals("StandardLibrary") && !asmName.equals("CTLlibrary") &&
		 * !asmName.equals("LTLlibrary")) { functionName = asmName + "_" + functionName;
		 * }
		 */
		return functionName;
	}

	static String getAsmName(Function function) {
		return getAsm(function).getName();
	}

	static Asm getAsm(Function function) {
		return function.getSignature().getHeaderSection().getAsm();
	}

	/*
	 * static boolean belongsToMainAsm(Function function) { return
	 * getAsmName(function).equals(getMainAsmName()); }
	 */

	public static String parsename(String s) throws Exception {
		if (s.equals("and")) {
			return "&";
		} else if (s.equals("or")) {
			return "|";
		} else if (s.equals("not")) {
			return "!";
		} else if (s.equals("plus")) {
			return "+";
		} else if (s.equals("minus")) {
			return "-";
		} else if (s.equals("mult")) {
			return "*";
		} else if (s.equals("div")) {
			return "/";
		} else if (s.equals("gt")) {
			return ">";
		} else if (s.equals("ge")) {
			return ">=";
		} else if (s.equals("lt")) {
			return "<";
		} else if (s.equals("le")) {
			return "<=";
		} else if (s.equals("eq")) {
			return "=";
		} else if (s.equals("neq")) {
			return "!=";
		} else if (s.equals("implies")) {
			return "->";
		} else if (s.equals("iff")) {
			return "<->";
		} else if (s.equals("eg") || s.equals("ex") || s.equals("ef") || s.equals("ag") || s.equals("ax")
				|| s.equals("af")) {
			return s.toUpperCase();
		} else if (s.equals("e") || s.equals("a")) {
			return "U";
		} else if (s.equals("x") || s.equals("g") || s.equals("f") || s.equals("u") || s.equals("v") || s.equals("y")
				|| s.equals("z") || s.equals("h") || s.equals("o") || s.equals("s") || s.equals("t")) {
			return s.toUpperCase();
		} else if (s.equals("pwr")) {
			throw new Exception("Function " + s + " is not supported.");
		} else {
			return s;
		}
	}

	public static boolean hasEvaluateVisitor(String function) {
		return function.equals("<") || function.equals("<=") || function.equals(">") || function.equals(">=")
				|| function.equals("=") || function.equals("!=") || function.equals("-") || function.equals("!")
				|| function.equals("&") || function.equals("|") || function.equals("xor") || function.equals("->")
				|| function.equals("<->") || function.equals("mod") || function.equals("+") || function.equals("*")
				|| function.equals("idiv") || function.equals("isDef");
	}
}
