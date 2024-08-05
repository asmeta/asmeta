package org.asmeta.nusmv;

import java.util.List;
import java.util.Map;

import org.asmeta.nusmv.util.AsmNotSupportedException;
import org.asmeta.nusmv.util.AsmetaSMVOptions;
import org.asmeta.nusmv.util.Util;

import asmeta.terms.basicterms.FunctionTerm;
import asmeta.terms.basicterms.LocationTerm;
import asmeta.terms.basicterms.Term;
import asmeta.terms.furtherterms.MapTerm;

public class FunctionVisitor {

	protected Environment env;

	protected FunctionVisitor(Environment environment) {
		env = environment;
	}

	/**
	 * Checks if is not natively supported function.
	 * 
	 * @param function the function
	 * 
	 * @return true, if is not supported function
	 */
	protected boolean isNotNativelySupportedFunction(String function) {
		return function.equals("at") ||
		// function.equals("program") ||
				function.equals("isUndef") || function.equals("isDef") || function.equals("abs")
				|| function.equals("max") || function.equals("min") || function.equals("range")
				|| function.equals("axN") || function.equals("exN") || function.equals("ew") || function.equals("aw")
				|| function.equals("absenceG") || function.equals("absenceBefore") || function.equals("absenceAfter")
				|| function.equals("absenceBetween") || function.equals("absenceAfterUntil");
	}

	/**
	 * Checks for evaluate visitor.
	 * 
	 * @param function the function
	 * 
	 * @return true, if successful
	 */
	public static boolean hasEvaluateVisitor(String function) {
		return function.equals("<") || function.equals("<=") || function.equals(">") || function.equals(">=")
				|| function.equals("=") || function.equals("!=") || function.equals("-") || function.equals("!")
				|| function.equals("&") || function.equals("|") || function.equals("xor") || function.equals("->")
				|| function.equals("<->") || function.equals("mod") || function.equals("+") || function.equals("*")
				|| function.equals("idiv")
				// real division "div" is supported in nuxmsv
				|| function.equals("div") || function.equals("/");
	}

	/**
	 * Visit.
	 * 
	 * @param function the function
	 * @param argsTerm the args term
	 * 
	 * @return the string
	 * 
	 * @throws AsmNotSupportedException the asm not supported exception
	 */
	protected String visit(String function, List<Term> argsTerm) throws AsmNotSupportedException {
		if (function.equals("at")) {
			return visitAtFunction(argsTerm);
		}
		/*
		 * else if(function.equals("program")) { visitProgramFunction(argsTerm); return
		 * ""; }
		 */
		else if (function.equals("isUndef")) {
			return visitIsUndefFunction(argsTerm);
		} else if (function.equals("isDef")) {
			return visitIsDefFunction(argsTerm);
		} else if (function.equals("abs")) {
			return visitAbsFunction(argsTerm);
		} else if (function.equals("max")) {
			return visitMaxFunction(argsTerm);
		} else if (function.equals("min")) {
			return visitMinFunction(argsTerm);
		} else if (function.equals("range")) {
			return visitRangeFunction(argsTerm);
		} else if (function.equals("axN")) {
			return visitCtlOperatorN(argsTerm, "AX");
		} else if (function.equals("exN")) {
			return visitCtlOperatorN(argsTerm, "EX");
		} else if (function.equals("ew")) {
			return visitEW(argsTerm);
		} else if (function.equals("aw")) {
			return visitAW(argsTerm);
		} else if (function.equals("absenceG")) {
			return visitAbsenceG(argsTerm);
		} else if (function.equals("absenceBefore")) {
			return visitAbsenceBefore(argsTerm);
		} else if (function.equals("absenceAfter")) {
			return visitAbsenceAfter(argsTerm);
		} else if (function.equals("absenceBetween")) {
			return visitAbsenceBetween(argsTerm);
		} else if (function.equals("absenceAfterUntil")) {
			return visitAbsenceAfterUntil(argsTerm);
		} else {
			throw new AsmNotSupportedException("Function " + function + " is not supported.");
		}
	}

	/**
	 * Visit at function.
	 * 
	 * @param argsTerm the args term
	 * 
	 * @return the string
	 */
	private String visitAtFunction(List<Term> argsTerm) {
		assert argsTerm.size() == 2;
		Map<String, String> map = env.tv.visit((MapTerm) argsTerm.get(0));
		String position = env.tv.visit(argsTerm.get(1));
		return map.get(position);
	}

	/**
	 * It visits the "abs" function of the StandardLibrary. If the argument is a
	 * number, it directly returns the absolute value.
	 * 
	 * @param argsTerm
	 * @return
	 */
	private String visitAbsFunction(List<Term> argsTerm) {
		assert argsTerm.size() == 1;
		String str = env.tv.visit(argsTerm.get(0));
		if (Util.isNumber(str)) {
			return String.valueOf(Math.abs(Integer.parseInt(str)));
		} else {
			return Util.setPars("case " + str + " >= 0: " + str + "; TRUE: -" + Util.setPars(str) + "; esac");
		}
	}

	private String visitMaxFunction(List<Term> argsTerm) {
		String str1 = env.tv.visit(argsTerm.get(0));
		String str2 = env.tv.visit(argsTerm.get(1));
		if (Util.isNumber(str1) && Util.isNumber(str2)) {
			return String.valueOf(Math.max(Integer.parseInt(str1), Integer.parseInt(str2)));
		} else {
			return Util.setPars("case " + str1 + " >= " + str2 + " : " + str1 + "; TRUE: (" + str2 + "); esac");
		}
	}

	private String visitMinFunction(List<Term> argsTerm) {
		String str1 = env.tv.visit(argsTerm.get(0));
		String str2 = env.tv.visit(argsTerm.get(1));
		if (Util.isNumber(str1) && Util.isNumber(str2)) {
			return String.valueOf(Math.min(Integer.parseInt(str1), Integer.parseInt(str2)));
		} else {
			return Util.setPars("case " + str1 + " < " + str2 + " : " + str1 + "; TRUE: (" + str2 + "); esac");
		}
	}

	private String visitRangeFunction(List<Term> argsTerm) throws AsmNotSupportedException {
		String str1 = env.tv.visit(argsTerm.get(0));
		String str2 = env.tv.visit(argsTerm.get(1));
		if (Util.isNumber(str1) && Util.isNumber(str2)) {
			if (Integer.parseInt(str1) <= Integer.parseInt(str2)) {
				return "{" + str1 + ".." + str2 + "}";
			} else {
				throw new AsmNotSupportedException("Empty range in range function.");
			}

		} else {
			throw new AsmNotSupportedException("The range function must be used only with numbers.");
		}
	}

	private String visitCtlOperatorN(List<Term> argsTerm, String ctlOperator) throws AsmNotSupportedException {
		String property = env.tv.visit(argsTerm.get(0));
		String nThState = env.tv.visit(argsTerm.get(1));
		if (Util.isNumber(nThState)) {
			int n = Integer.parseInt(nThState);
			if (n <= 0) {
				throw new AsmNotSupportedException("You must specify an integer number greater than 1.");
			} else if (n == 1) {
				throw new AsmNotSupportedException("You should use ex(property), insted of exN(property, 1).");
			} else {
				StringBuilder start = new StringBuilder();
				StringBuilder end = new StringBuilder();
				for (int i = 1; i <= n; i++) {
					start.append(ctlOperator + "(");
					end.append(")");
				}
				return start.toString() + property + end.toString();
			}
		} else {
			throw new AsmNotSupportedException("You must specify an integer number greater than 1.");
		}
	}

	// ew: Prod(Boolean, Boolean) -> Boolean //exists weak until -- E[p U q] | EGp.
	private String visitEW(List<Term> argsTerm) {
		String p = env.tv.visit(argsTerm.get(0));
		String q = env.tv.visit(argsTerm.get(1));
		return "E[" + p + " U " + q + "] | EG(" + p + ")";
	}

	// aw: Prod(Boolean, Boolean) -> Boolean //forall weak until -- !E[!q U !(p |
	// q)].
	private String visitAW(List<Term> argsTerm) {
		String p = env.tv.visit(argsTerm.get(0));
		String q = env.tv.visit(argsTerm.get(1));
		return visitAW(p, q);
	}

	private String visitAW(String p, String q) {
		return "! E[ !" + q + " U !(" + p + " | " + q + ")]";
	}

	// http://patterns.projects.cis.ksu.edu/documentation/patterns/ctl.shtml
	// Absence
	// P is false :
	// Globally - AG(!P)
	// absenceG: Boolean -> Boolean
	private String visitAbsenceG(List<Term> argsTerm) {
		String p = env.tv.visit(argsTerm.get(0));
		return "AG(! " + p + ")";
	}

	// http://patterns.projects.cis.ksu.edu/documentation/patterns/ctl.shtml
	// Absence
	// P is false :
	// (*) Before R - A[(!P | AG(!R)) W R]
	// absenceBefore: Prod(Boolean, Boolean) -> Boolean // absenceBefore(P, R)
	private String visitAbsenceBefore(List<Term> argsTerm) {
		String p = env.tv.visit(argsTerm.get(0));
		String r = env.tv.visit(argsTerm.get(1));
		return visitAW("(!" + p + " | AG(!" + r + "))", r);
	}

	// http://patterns.projects.cis.ksu.edu/documentation/patterns/ctl.shtml
	// Absence
	// P is false :
	// After Q - AG(Q -> AG(!P))
	// absenceAfter: Prod(Boolean, Boolean) -> Boolean // absenceAfter(P, Q)
	private String visitAbsenceAfter(List<Term> argsTerm) {
		String p = env.tv.visit(argsTerm.get(0));
		String q = env.tv.visit(argsTerm.get(1));
		return "AG(" + q + " -> " + "AG(!" + p + "))";
	}

	// http://patterns.projects.cis.ksu.edu/documentation/patterns/ctl.shtml
	// Absence
	// P is false :
	// (*) Between Q and R - AG(Q & !R -> A[(!P | AG(!R)) W R])
	// absenceBetween: Prod(Boolean, Boolean, Boolean) -> Boolean //
	// absenceBetween(P, Q, R)
	private String visitAbsenceBetween(List<Term> argsTerm) {
		String p = env.tv.visit(argsTerm.get(0));
		String q = env.tv.visit(argsTerm.get(1));
		String r = env.tv.visit(argsTerm.get(2));
		return "AG((" + q + " & !" + r + ") -> " + visitAW("(!" + p + " | AG(!" + r + "))", r) + ")";
	}

	// http://patterns.projects.cis.ksu.edu/documentation/patterns/ctl.shtml
	// Absence
	// P is false :
	// (*) After Q until R - AG(Q & !R -> A[!P W R])
	// absenceAfterUntil: Prod(Boolean, Boolean, Boolean) -> Boolean //
	// absenceAfterUntil(P, Q, R)
	private String visitAbsenceAfterUntil(List<Term> argsTerm) {
		String p = env.tv.visit(argsTerm.get(0));
		String q = env.tv.visit(argsTerm.get(1));
		String r = env.tv.visit(argsTerm.get(2));
		return "AG((" + q + " & !" + r + ") -> " + visitAW("!" + p, r) + ")";
	}

	/**
	 * Visit program function.
	 * 
	 * @param argsTerm the args term
	 */
	/*
	 * private void visitProgramFunction(List<Term> argsTerm) { String currentAgent
	 * = env.tp.visit(argsTerm.get(0)); assert
	 * env.tp.mv.agentDomain.containsKey(currentAgent); String agentDomainName =
	 * env.tp.mv.agentDomain.get(currentAgent); env.self = currentAgent; //self is
	 * updated with the current agent
	 * env.tp.mv.rv.visit(env.agentRule.get(agentDomainName)); }
	 */

	private String visitIsUndefFunction(List<Term> argsTerm) {
		assert argsTerm.size() == 1;
		String str = env.tv.visit(argsTerm.get(0));
		String undefValue = getUndefValue(argsTerm.get(0));
		return Util.setPars(str + " = " + undefValue);
	}

	private String visitIsDefFunction(List<Term> argsTerm) {
		assert argsTerm.size() == 1;
		String str = env.tv.visit(argsTerm.get(0));
		String undefValue = getUndefValue(argsTerm.get(0));
		return Util.setPars(str + " != " + undefValue);
	}

	// term can be a location
	private String getUndefValue(Term term) {
		if (term instanceof LocationTerm) {
			String str = env.tv.visit(term);
			String dom = env.tv.mv.locationDomain.get(str);
			String undefValue = env.tv.mv.getUndefValue(dom);
			// continue in order to allow to get the output at least
			// ATTENZIONE 1/8/24: non funzion se ho un numero tipo una funzione
			// non riesce a ricavare il dominio
			// cannot find undef for 10 domain null
			// cannot find undef for landingSequence_11 domain null
			if (undefValue == null) {
				System.err.print("cannot find undef for " + term + " domain " + dom);
			}
			return undefValue;
		}
		if (term instanceof FunctionTerm) {
			String funName = ((FunctionTerm) term).getFunction().getName();
			String dom = env.tv.mv.functionDomain.get(funName);
			String undefValue = env.tv.mv.getUndefValue(dom);
			if (undefValue == null)
				System.err.print("cannot find undef for " + term + " domain " + dom);
			return undefValue;

		}
		System.err.print("cannot find undef for " + term + " class " + term.getClass());
		return "undef";
	}

	/**
	 * Evaluate function.
	 * 
	 * @param function the function
	 * @param argsTerm the args term
	 * 
	 * @return the string
	 * 
	 * @throws Exception the exception
	 */
	String evaluateFunction(String function, List<Term> argsTerm) throws Exception {
		if (function.equals("<")) {
			return lt(argsTerm);
		}
		if (function.equals("<=")) {
			return le(argsTerm);
		}
		if (function.equals(">")) {
			return gt(argsTerm);
		}
		if (function.equals(">=")) {
			return ge(argsTerm);
		}
		if (function.equals("=")) {
			return equals(argsTerm);
		}
		if (function.equals("!=")) {
			return notEquals(argsTerm);
		}
		if (function.equals("!")) {
			return not(argsTerm);
		}
		if (function.equals("&")) {
			return and(argsTerm);
		}
		if (function.equals("|")) {
			return or(argsTerm);
		}
		if (function.equals("xor")) {
			return xor(argsTerm);
		}
		if (function.equals("->")) {
			return implies(argsTerm);
		}
		if (function.equals("<->")) {
			return iff(argsTerm);
		}
		if (function.equals("mod")) {
			return mod(argsTerm);
		}
		if (function.equals("idiv")) {
			return idiv(argsTerm);
		}
		if (function.equals("+")) {
			if (argsTerm.size() == 1) {
				return plusUnary(argsTerm);
			} else {
				return sum(argsTerm);
			}
		}
		if (function.equals("*")) {
			return mult(argsTerm);
		}
		if (function.equals("-")) {
			if (argsTerm.size() == 1) {
				return minusUnary(argsTerm);
			} else {
				return minusBinary(argsTerm);
			}
		}
		if (function.equals("div") || function.equals("/")) {
			return div(argsTerm);
		}
		throw new RuntimeException(function + " not translable");
	}

	/**
	 * Executes the xor function.
	 * 
	 * @param argsTerm the args term
	 * 
	 * @return the string
	 * 
	 * @throws Exception the exception
	 */
	private String xor(List<Term> argsTerm) throws Exception {
		String first = env.tv.visit(argsTerm.get(0));
		String second = env.tv.visit(argsTerm.get(1));
		return Util.xor(first, second);
	}

	/**
	 * Executes the or function.
	 * 
	 * @param argsTerm the args term
	 * 
	 * @return the string
	 * 
	 * @throws Exception the exception
	 */
	private String or(List<Term> argsTerm) throws Exception {
		String first = env.tv.visit(argsTerm.get(0));
		String second = env.tv.visit(argsTerm.get(1));
		return Util.or(first, second);
	}

	/**
	 * Executes the and function.
	 * 
	 * @param argsTerm the args term
	 * 
	 * @return the string
	 * 
	 * @throws Exception the exception
	 */
	private String and(List<Term> argsTerm) throws Exception {
		String first = env.tv.visit(argsTerm.get(0));
		String second = env.tv.visit(argsTerm.get(1));
		return Util.and(first, second);
	}

	/**
	 * Executes the not function.
	 * 
	 * @param argsTerm the args term
	 * 
	 * @return the string
	 * 
	 * @throws Exception the exception
	 */
	private String not(List<Term> argsTerm) throws Exception {
		assert argsTerm.size() == 1;
		String arg = env.tv.visit(argsTerm.get(0));
		assert arg != null : argsTerm.get(0);
		return Util.not(arg);
	}

	/**
	 * Executes the implies function.
	 * 
	 * @param argsTerm the args term
	 * 
	 * @return the string
	 * 
	 * @throws Exception the exception
	 */
	private String implies(List<Term> argsTerm) throws Exception {
		String first = env.tv.visit(argsTerm.get(0));
		String second = env.tv.visit(argsTerm.get(1));
		return Util.implies(first, second);
	}

	/**
	 * Executes the iff function.
	 * 
	 * @param argsTerm the args term
	 * 
	 * @return the string
	 * 
	 * @throws Exception the exception
	 */
	private String iff(List<Term> argsTerm) throws Exception {
		String first = env.tv.visit(argsTerm.get(0));
		String second = env.tv.visit(argsTerm.get(1));
		return Util.iff(first, second);
	}

	/**
	 * Executes the less than function.
	 * 
	 * @param argsTerm the args term
	 * 
	 * @return the string
	 */
	private String lt(List<Term> argsTerm) {
		String left = env.tv.visit(argsTerm.get(0));
		String right = env.tv.visit(argsTerm.get(1));
		if (AsmetaSMVOptions.simplify) {
			try {
				int leftNumber = Integer.parseInt(left);
				int rightNumber = Integer.parseInt(right);
				if (leftNumber < rightNumber) {
					return Util.trueString;
				} else {
					return Util.falseString;
				}
			} catch (NumberFormatException e) {
			}
		}
		return Util.setPars(left + " < " + right);
	}

	/**
	 * Executes the less than or equal function.
	 * 
	 * @param argsTerm the args term
	 * 
	 * @return the string
	 */
	private String le(List<Term> argsTerm) {
		String left = env.tv.visit(argsTerm.get(0));
		String right = env.tv.visit(argsTerm.get(1));
		if (AsmetaSMVOptions.simplify) {
			try {
				int leftNumber = Integer.parseInt(left);
				int rightNumber = Integer.parseInt(right);
				if (leftNumber <= rightNumber) {
					return Util.trueString;
				} else {
					return Util.falseString;
				}
			} catch (NumberFormatException e) {
			}
		}
		return Util.setPars(left + " <= " + right);
	}

	/**
	 * Executes the greater than function.
	 * 
	 * @param argsTerm the args term
	 * 
	 * @return the string
	 */
	private String gt(List<Term> argsTerm) {
		String left = env.tv.visit(argsTerm.get(0));
		String right = env.tv.visit(argsTerm.get(1));
		if (AsmetaSMVOptions.simplify) {
			try {
				int leftNumber = Integer.parseInt(left);
				int rightNumber = Integer.parseInt(right);
				if (leftNumber > rightNumber) {
					return Util.trueString;
				} else {
					return Util.falseString;
				}
			} catch (NumberFormatException e) {
			}
		}
		return Util.setPars(left + " > " + right);
	}

	/**
	 * Executes the greater than or equal function.
	 * 
	 * @param argsTerm the args term
	 * 
	 * @return the string
	 */
	private String ge(List<Term> argsTerm) {
		String left = env.tv.visit(argsTerm.get(0));
		String right = env.tv.visit(argsTerm.get(1));
		if (AsmetaSMVOptions.simplify) {
			try {
				int leftNumber = Integer.parseInt(left);
				int rightNumber = Integer.parseInt(right);
				if (leftNumber >= rightNumber) {
					return Util.trueString;
				} else {
					return Util.falseString;
				}
			} catch (NumberFormatException e) {
			}
		}
		return Util.setPars(left + " >= " + right);
	}

	/**
	 * Executes the equal function.
	 * 
	 * @param argsTerm the args term
	 * 
	 * @return the string
	 */
	private String equals(List<Term> argsTerm) {
		String left = env.tv.visit(argsTerm.get(0));
		String right = env.tv.visit(argsTerm.get(1));
		// System.out.println(argsTerm.get(0) + " = " + argsTerm.get(1));
		// System.out.println(left + " = " + right);
		return Util.equals(left, right);
	}

	/**
	 * Executes the not equal function.
	 * 
	 * @param argsTerm the args term
	 * 
	 * @return the string
	 */
	private String notEquals(List<Term> argsTerm) {
		String left = env.tv.visit(argsTerm.get(0));
		String right = env.tv.visit(argsTerm.get(1));
		return Util.notEquals(left, right);
	}

	/**
	 * Executes the mod function.
	 * 
	 * @param argsTerm the args term
	 * 
	 * @return the string
	 */
	private String mod(List<Term> argsTerm) {
		String left = env.tv.visit(argsTerm.get(0));
		String right = env.tv.visit(argsTerm.get(1));
		if (AsmetaSMVOptions.simplify) {
			try {
				int leftNumber = Integer.parseInt(left);
				int rightNumber = Integer.parseInt(right);
				return String.valueOf(leftNumber % rightNumber);
			} catch (NumberFormatException e) {
			}
		}
		return Util.setPars(left + " mod " + right);
	}

	/**
	 * Minus unary.
	 * 
	 * @param argsTerm the args term
	 * 
	 * @return the string
	 * @throws AsmNotSupportedException
	 */
	String minusUnary(List<Term> argsTerm) throws AsmNotSupportedException {
		// System.out.println("argsTerm "+argsTerm);
		// System.out.println("argsTerm.get(0) "+argsTerm.get(0));
		/*
		 * String str = env.tp.visit(argsTerm.get(0)); System.out.println("str "+str);
		 * if(Util.simplify){ try{ int number = Integer.parseInt(str); return
		 * String.valueOf((-1)*number); } catch(NumberFormatException e){ throw new
		 * AsmNotSupportedException("Conversione errata"); } } return "-" + str;
		 */
		// System.out.println("str "+argsTerm.get(0)+" "+env.tp.visit(argsTerm.get(0)));
		String str = env.tv.visit(argsTerm.get(0));
		if (Util.isNumber(str)) {
			return String.valueOf(Integer.valueOf(str) * (-1));
		} else {
			return env.tv.visit(argsTerm.get(0));
		}
	}

	/**
	 * Minus binary.
	 * 
	 * @param argsTerm the args term
	 * 
	 * @return the string
	 */
	String minusBinary(List<Term> argsTerm) {
		String left = env.tv.visit(argsTerm.get(0));
		String right = env.tv.visit(argsTerm.get(1));
		if (AsmetaSMVOptions.simplify) {
			try {
				int leftNumber = Integer.parseInt(left);
				int rightNumber = Integer.parseInt(right);
				return String.valueOf(leftNumber - rightNumber);
			} catch (NumberFormatException e) {
			}
		}
		return Util.setPars(left + " - " + right);
	}

	/**
	 * Plus unary.
	 * 
	 * @param argsTerm the args term
	 * 
	 * @return the string
	 */
	String plusUnary(List<Term> argsTerm) {
		return env.tv.visit(argsTerm.get(0));
	}

	/**
	 * Executes the sum function.
	 * 
	 * @param argsTerm the args term
	 * 
	 * @return the string
	 */
	String sum(List<Term> argsTerm) {
		String left = env.tv.visit(argsTerm.get(0));
		String right = env.tv.visit(argsTerm.get(1));
		if (AsmetaSMVOptions.simplify) {
			try {
				int leftNumber = Integer.parseInt(left);
				int rightNumber = Integer.parseInt(right);
				return String.valueOf(leftNumber + rightNumber);
			} catch (NumberFormatException e) {
			}
		}
		return Util.setPars(left + " + " + right);
	}

	/**
	 * Executes the implies function.
	 * 
	 * @param argsTerm the args term
	 * 
	 * @return the string
	 * 
	 * @throws Exception the exception
	 */
	private String div(List<Term> argsTerm) throws Exception {
		String left = env.tv.visit(argsTerm.get(0));
		String right = env.tv.visit(argsTerm.get(1));
		return Util.setPars(left + " / " + right);
	}

	/**
	 * Executes the multiply function.
	 * 
	 * @param argsTerm the args term
	 * 
	 * @return the string
	 */
	String mult(List<Term> argsTerm) {
		String left = env.tv.visit(argsTerm.get(0));
		String right = env.tv.visit(argsTerm.get(1));
		if (AsmetaSMVOptions.simplify) {
			try {
				int leftNumber = Integer.parseInt(left);
				int rightNumber = Integer.parseInt(right);
				return String.valueOf(leftNumber * rightNumber);
			} catch (NumberFormatException e) {
			}
		}
		return Util.setPars(left + " * " + right);
	}

	String idiv(List<Term> argsTerm) {
		String left = env.tv.visit(argsTerm.get(0));
		String right = env.tv.visit(argsTerm.get(1));
		try {
			int leftNumber = Integer.parseInt(left);
			int rightNumber = Integer.parseInt(right);
			return String.valueOf(leftNumber / rightNumber);
		} catch (NumberFormatException e) {
			return Util.setPars(left + " / " + right);
		}
	}
}
