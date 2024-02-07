package org.asmeta.asm2java;

import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import asmeta.definitions.domains.PowersetDomain;
import asmeta.structure.Asm;
import asmeta.terms.basicterms.SetTerm;
import asmeta.terms.basicterms.Term;

public class ExpressionToJava {
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
				|| function.equals("chooseone");
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
			return equals(argsTerm);
		case ("at"):
			return at(argsTerm);
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
			throw new RuntimeException(function + "not found");
		}

	}

	/**
	 * Executes the iton function.
	 * 
	 * @param argsTerm the args term
	 * 
	 * @return the string
	 */
	private String iton(List<Term> argsTerm) {
		String first = new TermToJavaSupportoConfronto(asm).visit(argsTerm.get(0));
		return first;
	}

	private String chooseone(List<Term> argsTerm) {
		SetTerm term = (SetTerm) argsTerm.get(0);
		String res = "Collections.unmodifiableList(Arrays.asList" + new TermToJava(asm).visit(term)
				+ ").get(ThreadLocalRandom.current().nextInt(0, Collections.unmodifiableList(Arrays.asList"
				+ new TermToJava(asm).visit(term) + ").size()))";
		return res;
	}

	private String or(List<Term> argsTerm) throws Exception {
		String first = new TermToJavaSupportoConfronto(asm).visit(argsTerm.get(0));
		String second = new TermToJavaSupportoConfronto(asm).visit(argsTerm.get(1));
		return first + " || " + second;
	}

	private String at(List<Term> argsTerm) throws Exception {
		String first = new TermToJavaSupportoConfronto(asm).visit(argsTerm.get(0));
		String second = new TermToJavaSupportoConfronto(asm).visit(argsTerm.get(1));
		return first + ".get(" + second + ")";
	}

	private String and(List<Term> argsTerm) throws Exception {
		String first = new TermToJavaSupportoConfronto(asm).visit(argsTerm.get(0));
		String second = new TermToJavaSupportoConfronto(asm).visit(argsTerm.get(1));
		return first + " && " + second;
	}

	private String not(List<Term> argsTerm) throws Exception {
		String arg = new TermToJavaSupportoConfronto(asm).visit(argsTerm.get(0));
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
		String left = new TermToJavaSupportoConfronto(asm).visit(argsTerm.get(0));
		String right = new TermToJavaSupportoConfronto(asm).visit(argsTerm.get(1));

		// return left + " < " + right;
		try {
			Integer.parseInt(left);
			Integer.parseInt(right);
		} catch (NumberFormatException e) {
		}
		return new Util().setPars(left + " < " + right);
	}

	/**
	 * Executes the less than or equal function.
	 * 
	 * @param argsTerm the args term
	 * 
	 * @return the string
	 */
	private String le(List<Term> argsTerm) {
		String left = new TermToJavaSupportoConfronto(asm).visit(argsTerm.get(0));
		String right = new TermToJavaSupportoConfronto(asm).visit(argsTerm.get(1));
		// return left + " <= " + right;
		try {
			Integer.parseInt(left);
			Integer.parseInt(right);
		} catch (NumberFormatException e) {
		}
		return new Util().setPars(left + " <= " + right);

	}

	/**
	 * Executes the greater than function.
	 * 
	 * @param argsTerm the args term
	 * 
	 * @return the string
	 */
	private String gt(List<Term> argsTerm) {
		String left = new TermToJavaSupportoConfronto(asm).visit(argsTerm.get(0));
		String right = new TermToJavaSupportoConfronto(asm).visit(argsTerm.get(1));
		// return left + " > " + right;
		try {
			Integer.parseInt(left);
			Integer.parseInt(right);
		} catch (NumberFormatException e) {
		}
		return new Util().setPars(left + " > " + right);

	}

	/**
	 * Executes the greater than or equal function.
	 * 
	 * @param argsTerm the args term
	 * 
	 * @return the string
	 */
	private String ge(List<Term> argsTerm) {
		String left = new TermToJavaSupportoConfronto(asm).visit(argsTerm.get(0));
		String right = new TermToJavaSupportoConfronto(asm).visit(argsTerm.get(1));
		// return left + " >= " + right;
		try {
			Integer.parseInt(left);
			Integer.parseInt(right);
		} catch (NumberFormatException e) {
		}
		return new Util().setPars(left + " >= " + right);

	}

	/**
	 * Executes the equal function.
	 * 
	 * @param argsTerm the args term
	 * 
	 * @return the string
	 */
	private String equals(List<Term> argsTerm) {
		String left = new TermToJavaSupportoConfronto(asm).visit(argsTerm.get(0));
		String right = new TermToJavaSupportoConfronto(asm).visit(argsTerm.get(1));
		// System.out.println(argsTerm.get(0) + " = " + argsTerm.get(1));
		// System.out.println(left + " = " + right);
		return new Util().equals(left, right);
	}

	/**
	 * Executes the not equal function.
	 * 
	 * @param argsTerm the args term
	 * 
	 * @return the string
	 */
	private String notEquals(List<Term> argsTerm) {
		String left = new TermToJavaSupportoConfronto(asm).visit(argsTerm.get(0));
		String right = new TermToJavaSupportoConfronto(asm).visit(argsTerm.get(1));
		return new Util().notEquals(left, right);
	}

	/**
	 * Executes the mod function.
	 * 
	 * @param argsTerm the args term
	 * 
	 * @return the string
	 */
	private String mod(List<Term> argsTerm) {
		String left = new TermToJavaSupportoConfronto(asm).visit(argsTerm.get(0));
		String right = new TermToJavaSupportoConfronto(asm).visit(argsTerm.get(1));
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
		String left = new TermToJavaSupportoConfronto(asm).visit(argsTerm.get(0));
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
		String str = new TermToJavaSupportoConfronto(asm).visit(argsTerm.get(0));
		if (new Util().isNumber(str)) {
			return String.valueOf(Integer.valueOf(str) * (-1));
		} else {
			return new TermToJavaSupportoConfronto(asm).visit(argsTerm.get(0));
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
		String left = new TermToJavaSupportoConfronto(asm).visit(argsTerm.get(0));
		String right = new TermToJavaSupportoConfronto(asm).visit(argsTerm.get(1));
		return new Util().setPars(left + " - " + right);
	}

	/**
	 * Plus unary.
	 * 
	 * @param argsTerm the args term
	 * 
	 * @return the string
	 */
	String plusUnary(List<Term> argsTerm) {
		return new TermToJavaSupportoConfronto(asm).visit(argsTerm.get(0));
	}

	/**
	 * Executes the sum function.
	 * 
	 * @param argsTerm the args term
	 * 
	 * @return the string
	 */
	String sum(List<Term> argsTerm) {
		String left = new TermToJavaSupportoConfronto(asm).visit(argsTerm.get(0));
		String right = new TermToJavaSupportoConfronto(asm).visit(argsTerm.get(1));
		return new Util().setPars(left + " + " + right);
	}

	/**
	 * Executes the multiply function.
	 * 
	 * @param argsTerm the args term
	 * 
	 * @return the string
	 */
	String mult(List<Term> argsTerm) {
		String left = new TermToJavaSupportoConfronto(asm).visit(argsTerm.get(0));
		String right = new TermToJavaSupportoConfronto(asm).visit(argsTerm.get(1));
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
		String left = new TermToJavaSupportoConfronto(asm).visit(argsTerm.get(0));
		String right = new TermToJavaSupportoConfronto(asm).visit(argsTerm.get(1));
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
		String left = new TermToJavaSupportoConfronto(asm).visit(argsTerm.get(0));
		String right = new TermToJavaSupportoConfronto(asm).visit(argsTerm.get(1));
		return new Util().setPars(left + " / " + right);
	}
}
