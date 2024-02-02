package org.asmeta.asm2java;

import java.util.List;
import org.asmeta.asm2code.Util;
import asmeta.structure.Asm;
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
				|| function.equals("&") || function.equals("|") || function.equals("xor")
				|| /*
					 * function.equals("->") ||
					 */ function.equals("mod") || function.equals("+") || function.equals("*") || function.equals("/")
				|| function.equals("^");
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
		if (function.equals("mod")) {
			return mod(argsTerm);
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
		} else {
			return "";
		}
	}

	private String or(List<Term> argsTerm) throws Exception {
		String first = new TermToJavaSupportoConfronto(asm).visit(argsTerm.get(0));
		String second = new TermToJavaSupportoConfronto(asm).visit(argsTerm.get(1));
		return first + " || " + second;
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
