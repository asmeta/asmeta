package org.asmeta.asm2java;

import java.util.List;

import asmeta.definitions.domains.ConcreteDomain;
import asmeta.structure.Asm;
import asmeta.terms.basicterms.SetTerm;
import asmeta.terms.basicterms.Term;

public class ExpressionToJava {
	private static final String VALUE_FIELD_NAME = ".value";
	protected Asm asm;

	public ExpressionToJava(Asm asm) {
		this.asm = asm;
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
				|| function.equals("&") || function.equals("|") || function.equals("xor") || function.equals("mod")
				|| function.equals("isDef") || function.equals("+") || function.equals("*") || function.equals("/")
				|| function.equals("^") || function.equals("iton") || function.equals("at")
				|| function.equals("chooseone") || function.equals("first") || function.equals("length") || function.equals("union");
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
	String evaluateFunction(String function, List<Term> argsTerm) throws InvalidFunctionException {
		switch (function) {
		case "<":
			return lt(argsTerm);
		case ("<="):
			return le(argsTerm);
		case (">"):
			return gt(argsTerm);
		case (">="):
			return ge(argsTerm);
		case ("->"):
			return implies(argsTerm);
		case ("chooseone"):
			return chooseone(argsTerm);
		case ("iton"):
			return iton(argsTerm);
		case ("="):
			return eq(argsTerm);
		case ("at"):
			return at(argsTerm);
		case ("length"):
			return length(argsTerm);
		case ("!="):
			return notEquals(argsTerm);
		case ("!"):
			return not(argsTerm);
		case ("&"):
			return and(argsTerm);
		case ("|"):
			return or(argsTerm);
		case ("mod"):
			return mod(argsTerm);
		case ("isDef"):
			return isDef(argsTerm);
		case ("first"):
			return first(argsTerm);
		case ("union"):
			return union(argsTerm);
		case ("+"):
			if (argsTerm.size() == 1) {
				return plusUnary(argsTerm);
			} else {
				return sum(argsTerm);
			}
		case ("*"):
			return mult(argsTerm);
		case ("-"):
			if (argsTerm.size() == 1) {
				return minusUnary(argsTerm);
			} else {
				return minusBinary(argsTerm);
			}
		default:
			throw new InvalidFunctionException(function + "not found");
		}

	}

	private String union(List<Term> argsTerm) {
		String first = new TermToJavaStandardLibrary(asm).visit(argsTerm.get(0));
		String second = new TermToJavaStandardLibrary(asm).visit(argsTerm.get(1));
		return first + ".addAll(" + second +")";
	}

	private String length(List<Term> argsTerm) {
		String first = new TermToJavaStandardLibrary(asm).visit(argsTerm.get(0));
		return first + ".size()";
	}

	/**
	 * Executes the iton function.
	 * 
	 * @param argsTerm the args term
	 * 
	 * @return the string
	 */
	private String iton(List<Term> argsTerm) {
		return new TermToJavaStandardLibrary(asm).visit(argsTerm.get(0));
	}
	
	/**
	 * Executes the first function.
	 * 
	 * @param argsTerm the args term
	 * 
	 * @return the string
	 */
	private String first(List<Term> argsTerm) {
		String first = new TermToJavaStandardLibrary(asm).visit(argsTerm.get(0));
		return first + ".get(0)";
	}

	private String chooseone(List<Term> argsTerm) {
		SetTerm term = (SetTerm) argsTerm.get(0);
		return "Collections.unmodifiableList(Arrays.asList" + new TermToJava(asm).visit(term)
				+ ").get(ThreadLocalRandom.current().nextInt(0, Collections.unmodifiableList(Arrays.asList"
				+ new TermToJava(asm).visit(term) + ").size()))";
	}

	private String or(List<Term> argsTerm) {
		String first = new TermToJavaStandardLibrary(asm).visit(argsTerm.get(0));
		String second = new TermToJavaStandardLibrary(asm).visit(argsTerm.get(1));
		return first + " || " + second;
	}

	private String at(List<Term> argsTerm) {
		String first = new TermToJavaStandardLibrary(asm).visit(argsTerm.get(0));
		String second = new TermToJavaStandardLibrary(asm).visit(argsTerm.get(1));
		return first + ".get(" + second + ")";
	}

	private String and(List<Term> argsTerm) {
		String first = new TermToJavaStandardLibrary(asm).visit(argsTerm.get(0));
		String second = new TermToJavaStandardLibrary(asm).visit(argsTerm.get(1));
		return first + " && " + second;
	}

	private String not(List<Term> argsTerm) {
		String arg = new TermToJavaStandardLibrary(asm).visit(argsTerm.get(0));
		return "! " + arg;
	}

	/**
	 * Executes the less than function.
	 * 
	 * @param argsTerm the args term
	 * 
	 * @return the string
	 */
	private String lt(List<Term> argsTerm) {
		return translateRelationalOp(argsTerm, "<");
	}

	private String translateRelationalOp(List<Term> argsTerm, String op) {
		assert argsTerm.size() == 2;
		String left = new TermToJavaStandardLibrary(asm).visit(argsTerm.get(0));
		String right = new TermToJavaStandardLibrary(asm).visit(argsTerm.get(1));
		try {
			Integer.parseInt(left);
			Integer.parseInt(right);
		} catch (NumberFormatException e) {
			// Ignore
		}

		// The two domains are different. In order to make them comparable, we need to
		// get the value of at least of them
		if (!argsTerm.get(0).getDomain().equals(argsTerm.get(1).getDomain())) {
			if (argsTerm.get(0).getDomain() instanceof ConcreteDomain) {
				left = left + VALUE_FIELD_NAME;
			}

			if (argsTerm.get(1).getDomain() instanceof ConcreteDomain) {
				right = right + VALUE_FIELD_NAME;
			}
		}
		if (op.equals("=")) return new Util().equals(left, right);
		if (op.equals("!=")) return new Util().notEquals(left, right);
		return new Util().setPars(left + " " + op + " " +right);
	}

	/**
	 * Executes the less than or equal function.
	 * 
	 * @param argsTerm the args term
	 * 
	 * @return the string
	 */
	private String le(List<Term> argsTerm) {
		return translateRelationalOp(argsTerm, "<=");
	}

	/**
	 * Executes the greater than function.
	 * 
	 * @param argsTerm the args term
	 * 
	 * @return the string
	 */
	private String gt(List<Term> argsTerm) {
		return translateRelationalOp(argsTerm, ">");
	}

	/**
	 * Executes the greater than or equal function.
	 * 
	 * @param argsTerm the args term
	 * 
	 * @return the string
	 */
	private String ge(List<Term> argsTerm) {
		return translateRelationalOp(argsTerm, ">=");		
	}

	/**
	 * Executes the equal function.
	 * 
	 * @param argsTerm the args term
	 * 
	 * @return the string
	 */
	private String eq(List<Term> argsTerm) {
		return translateRelationalOp(argsTerm, "=");		
	}

	/**
	 * Executes the not equal function.
	 * 
	 * @param argsTerm the args term
	 * 
	 * @return the string
	 */
	private String notEquals(List<Term> argsTerm) {
		return translateRelationalOp(argsTerm, "!=");		
	}

	/**
	 * Executes the mod function.
	 * 
	 * @param argsTerm the args term
	 * 
	 * @return the string
	 */
	private String mod(List<Term> argsTerm) {
		String left = new TermToJavaStandardLibrary(asm).visit(argsTerm.get(0));
		String right = new TermToJavaStandardLibrary(asm).visit(argsTerm.get(1));
		return new Util().setPars(left + " % " + right);
	}

	/**
	 * Executes the isDef function
	 * 
	 * @param argsTerm the args term
	 * 
	 * @return the string
	 */
	private String isDef(List<Term> argsTerm) {
		String left = new TermToJavaStandardLibrary(asm).visit(argsTerm.get(0));
		return new Util().setPars(left + " != null");
	}

	/**
	 * Minus unary.
	 * 
	 * @param argsTerm the args term
	 * 
	 * @return the string
	 * @throws AsmNotSupportedException
	 */
	String minusUnary(List<Term> argsTerm) {
		String str = new TermToJavaStandardLibrary(asm).visit(argsTerm.get(0));
		if (Boolean.TRUE.equals(new Util().isNumber(str))) {
			return String.valueOf(Integer.valueOf(str) * (-1));
		} else {
			return new TermToJavaStandardLibrary(asm).visit(argsTerm.get(0));
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
		return translateRelationalOp(argsTerm, "-");		
	}

	/**
	 * Plus unary.
	 * 
	 * @param argsTerm the args term
	 * 
	 * @return the string
	 */
	String plusUnary(List<Term> argsTerm) {
		return new TermToJavaStandardLibrary(asm).visit(argsTerm.get(0));
	}

	/**
	 * Executes the sum function.
	 * 
	 * @param argsTerm the args term
	 * 
	 * @return the string
	 */
	String sum(List<Term> argsTerm) {
		return translateRelationalOp(argsTerm, "+");		
	}

	/**
	 * Executes the multiply function.
	 * 
	 * @param argsTerm the args term
	 * 
	 * @return the string
	 */
	String mult(List<Term> argsTerm) {
		String left = new TermToJavaStandardLibrary(asm).visit(argsTerm.get(0));
		String right = new TermToJavaStandardLibrary(asm).visit(argsTerm.get(1));
		return new Util().setPars(left + " * " + right);
	}

	/**
	 * Executes the implies function.
	 * 
	 * @param argsTerm the args term
	 * 
	 * @return the string
	 */
	String implies(List<Term> argsTerm) {
		String left = new TermToJavaStandardLibrary(asm).visit(argsTerm.get(0));
		String right = new TermToJavaStandardLibrary(asm).visit(argsTerm.get(1));
		return new Util().setPars("(!" + left + " || " + right + ")");
	}

	/**
	 * Executes the divide function.
	 * 
	 * @param argsTerm the args term
	 * 
	 * @return the string
	 */
	String idiv(List<Term> argsTerm) {
		String left = new TermToJavaStandardLibrary(asm).visit(argsTerm.get(0));
		String right = new TermToJavaStandardLibrary(asm).visit(argsTerm.get(1));
		return new Util().setPars(left + " / " + right);
	}
}
