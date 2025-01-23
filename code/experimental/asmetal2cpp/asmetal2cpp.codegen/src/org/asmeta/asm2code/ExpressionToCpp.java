package org.asmeta.asm2code;

import java.util.List;
import org.asmeta.asm2code.TermToCpp;
import org.asmeta.asm2code.Util;

import asmeta.structure.Asm;
import asmeta.terms.basicterms.Term;

/** translation of complex (not atomic) terms, like and and so on.
 * Used by the TermToCpp
 */
public class ExpressionToCpp {
	protected Asm asm;

	public ExpressionToCpp(Asm asm) {
		this.asm = asm;
	}

	/**
	 * Checks if is not natively supported function.
	 * 
	 * @param function
	 *            the function
	 * 
	 * @return true, if is not supported function
	 */
	/** TODO: DELETE FOR COVERAGE 	boolean isNotNativelySupportedFunction(String function) {
		return function.equals("at") ||
		// function.equals("program") ||
				function.equals("isUndef") || function.equals("isDef") || function.equals("abs")
				|| function.equals("max") || function.equals("min") || function.equals("range")
				|| function.equals("axN") || function.equals("exN") || function.equals("ew") || function.equals("aw")
				|| function.equals("absenceG") || function.equals("absenceBefore") || function.equals("absenceAfter")
				|| function.equals("absenceBetween") || function.equals("absenceAfterUntil");
	}*/

	/**
	 * Checks for evaluate visitor.
	 * 
	 * @param function
	 *            the function
	 * 
	 * @return true, if successful
	 */
	public static boolean hasEvaluateVisitor(String function) {
		return function.equals("<") || function.equals("<=") || function.equals(">") || function.equals(">=")
				|| function.equals("=") || function.equals("!=") || function.equals("-") || function.equals("!")
				|| function.equals("&") || function.equals("|") || function.equals("xor") || /*function.equals("->")
				||*/ function.equals("mod") || function.equals("+") || function.equals("*") || function.equals("/")|| function.equals("^");
	}


	/**
	 * Evaluate function.
	 * 
	 * @param function
	 *            the function
	 * @param argsTerm
	 *            the args term
	 * 
	 * @return the string
	 * 
	 * @throws Exception
	 *             the exception
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
		/** TODO: DELETE FOR COVERAGE if (function.equals("xor")) {
			return xor(argsTerm);
		}
		if (function.equals("^")) {
			return pow(argsTerm);
		}*/
		/*if (function.equals("->")) {
			return implies(argsTerm);
		}*/
		/*if (function.equals("<->")) {
			return iff(argsTerm);
		}*/
		if (function.equals("mod")) {
			return mod(argsTerm);
		}
		/** TODO: DELETE FOR COVERAGE 	if (function.equals("idiv")) {
			return idiv(argsTerm);
		}*/
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
		}if(function.equals("/")) {
				
			return div(argsTerm);
			
		}
		else {
			throw new RuntimeException("Function" + function + "not found.");
		}
	}

	/** TODO: DELETE FOR COVERAGE private String pow(List<Term> argsTerm) {
		// TODO Auto-generated method stub
		return null;
	}*/

	/**
	 * Executes the xor function.
	 * 
	 * @param argsTerm
	 *            the args term
	 * 
	 * @return the string
	 * 
	 * @throws Exception
	 *             the exception
	 */
	/** TODO: DELETE FOR COVERAGE 	private String xor(List<Term> argsTerm) throws Exception {
		String first = new TermToCpp(asm).visit(argsTerm.get(0));
		String second = new TermToCpp(asm).visit(argsTerm.get(1));
		return xor(updateVarName(first),updateVarName(second));
		//return first + " ^ " + second;
	}

	private String xor(String first, String second) {
		// TODO Auto-generated method stub
		return null;
	}*/

	/**
	 * Executes the or function.
	 * 
	 * @param argsTerm
	 *            the args term
	 * 
	 * @return the string
	 * 
	 * @throws Exception
	 *             the exception
	 */
	private String or(List<Term> argsTerm) throws Exception {
		String first = new TermToCpp(asm).visit(argsTerm.get(0));
		String second = new TermToCpp(asm).visit(argsTerm.get(1));
		return updateVarName(first) + " | " + updateVarName(second);
	}

	/**
	 * Executes the and function.
	 * 
	 * @param argsTerm
	 *            the args term
	 * 
	 * @return the string
	 * 
	 * @throws Exception
	 *             the exception
	 */
	private String and(List<Term> argsTerm) throws Exception {
		String first = new TermToCpp(asm).visit(argsTerm.get(0));
		String second = new TermToCpp(asm).visit(argsTerm.get(1));
		return updateVarName(first) + " & " + updateVarName(second);
	}

	/**
	 * Executes the not function.
	 * 
	 * @param argsTerm
	 *            the args term
	 * 
	 * @return the string
	 * 
	 * @throws Exception
	 *             the exception
	 */
	private String not(List<Term> argsTerm) throws Exception {
		String arg = new TermToCpp(asm).visit(argsTerm.get(0));
		return "! (" + updateVarName(arg) + ")";
	}

	/**
	 * Executes the implies function.
	 * 
	 * @param argsTerm
	 *            the args term
	 * 
	 * @return the string
	 * 
	 * @throws Exception
	 *             the exception
	 */
	/*private String implies(List<Term> argsTerm) throws Exception {
		String first = new TermToCpp(asm).visit(argsTerm.get(0));
		String second = new TermToCpp(asm).visit(argsTerm.get(1));
		return Util.implies(first, second);
	}*/



	/**
	 * Executes the less than function.
	 * 
	 * @param argsTerm
	 *            the args term
	 * 
	 * @return the string
	 */
	private String lt(List<Term> argsTerm) {
		String left = new TermToCpp(asm).visit(argsTerm.get(0));
		String right = new TermToCpp(asm).visit(argsTerm.get(1));
		//return left + " < " + right;
		try {
			Integer.parseInt(left);
			Integer.parseInt(right);
		}
		catch(NumberFormatException e) {}
		return new Util().setPars(updateVarName(left) + " < " + updateVarName(right));
	}

	/**
	 * Executes the less than or equal function.
	 * 
	 * @param argsTerm
	 *            the args term
	 * 
	 * @return the string
	 */
	private String le(List<Term> argsTerm) {
		String left = new TermToCpp(asm).visit(argsTerm.get(0));
		String right = new TermToCpp(asm).visit(argsTerm.get(1));
		//return left + " <= " + right;
		try {
			Integer.parseInt(left);
			Integer.parseInt(right);
		}
		catch(NumberFormatException e) {}
		return new Util().setPars(updateVarName(left) + " <= " + updateVarName(right));
	
	}

	/**
	 * Executes the greater than function.
	 * 
	 * @param argsTerm
	 *            the args term
	 * 
	 * @return the string
	 */
	private String gt(List<Term> argsTerm) {
		String left = new TermToCpp(asm).visit(argsTerm.get(0));
		String right = new TermToCpp(asm).visit(argsTerm.get(1));
		//return left + " > " + right;
		try {
			Integer.parseInt(left);
			Integer.parseInt(right);
		}
		catch(NumberFormatException e) {}
		return new Util().setPars(updateVarName(left) + " > " + updateVarName(right));
	
	}

	/**
	 * Executes the greater than or equal function.
	 * 
	 * @param argsTerm
	 *            the args term
	 * 
	 * @return the string
	 */
	private String ge(List<Term> argsTerm) {
		String left = new TermToCpp(asm).visit(argsTerm.get(0));
		String right = new TermToCpp(asm).visit(argsTerm.get(1));
		//return left + " >= " + right;
		try {
			Integer.parseInt(left);
			Integer.parseInt(right);
		}
		catch(NumberFormatException e) {}
		return new Util().setPars(updateVarName(left) + " >= " + updateVarName(right));
	
	}

	/**
	 * Executes the equal function.
	 * 
	 * @param argsTerm
	 *            the args term
	 * 
	 * @return the string
	 */
	private String equals(List<Term> argsTerm) {
		String left = new TermToCpp(asm).visit(argsTerm.get(0));
		String right = new TermToCpp(asm).visit(argsTerm.get(1));
		// System.out.println(argsTerm.get(0) + " = " + argsTerm.get(1));
		// System.out.println(left + " = " + right);
		return new Util().equals(updateVarName(left), updateVarName(right));
	}

	/**
	 * Executes the not equal function.
	 * 
	 * @param argsTerm
	 *            the args term
	 * 
	 * @return the string
	 */
	private String notEquals(List<Term> argsTerm) {
		String left = new TermToCpp(asm).visit(argsTerm.get(0));
		String right = new TermToCpp(asm).visit(argsTerm.get(1));
		return new Util().notEquals(updateVarName(left), updateVarName(right));
	}

	/**
	 * Executes the mod function.
	 * 
	 * @param argsTerm
	 *            the args term
	 * 
	 * @return the string
	 */
	private String mod(List<Term> argsTerm) {
		String left = new TermToCpp(asm).visit(argsTerm.get(0));
		String right = new TermToCpp(asm).visit(argsTerm.get(1));
		return new Util().setPars(updateVarName(left) + " % " + updateVarName(right));
	}

	private String updateVarName(String name) {
		return name;
	}

	/**
	 * Minus unary.
	 * 
	 * @param argsTerm
	 *            the args term
	 * 
	 * @return the string
	 * @throws AsmNotSupportedException
	 */
	String minusUnary(List<Term> argsTerm) {
		String str = new TermToCpp(asm).visit(argsTerm.get(0));
		if (new Util().isNumber(str)) {
			return String.valueOf(Integer.valueOf(str) * (-1));
		} else {
			return new TermToCpp(asm).visit(argsTerm.get(0));
		}
	}

	/**
	 * Minus binary.
	 * 
	 * @param argsTerm
	 *            the args term
	 * 
	 * @return the string
	 */
	String minusBinary(List<Term> argsTerm) {
		String left = new TermToCpp(asm).visit(argsTerm.get(0));
		String right = new TermToCpp(asm).visit(argsTerm.get(1));
		return new Util().setPars(updateVarName(left) + " - " + updateVarName(right));
	}

	/**
	 * Plus unary.
	 * 
	 * @param argsTerm
	 *            the args term
	 * 
	 * @return the string
	 */
String plusUnary(List<Term> argsTerm) {
		return new TermToCpp(asm).visit(argsTerm.get(0));
	}

	/**
	 * Executes the sum function.
	 * 
	 * @param argsTerm
	 *            the args term
	 * 
	 * @return the string
	 */
	String sum(List<Term> argsTerm) {
		// System.out.println("Terms " + argsTerm.size());
		String left = new TermToCpp(asm).visit(argsTerm.get(0));
		String right = new TermToCpp(asm).visit(argsTerm.get(1));
		return new Util().setPars(updateVarName(left) + " + " + updateVarName(right));
	}

	/**
	 * Executes the multiply function.
	 * 
	 * @param argsTerm
	 *            the args term
	 * 
	 * @return the string
	 */
	String mult(List<Term> argsTerm) {
		String left = new TermToCpp(asm).visit(argsTerm.get(0));
		String right = new TermToCpp(asm).visit(argsTerm.get(1));
		return new Util().setPars(updateVarName(left) + " * " + updateVarName(right));
	}

	/** TODO: DELETE FOR COVERAGE 	String idiv(List<Term> argsTerm) {
		String left = new TermToCpp(asm).visit(argsTerm.get(0));
		String right = new TermToCpp(asm).visit(argsTerm.get(1));
		return new Util().setPars(updateVarName(left) + " / " + updateVarName(right));
	}*/
	
	String div(List<Term> argsTerm) {
		String left = new TermToCpp(asm).visit(argsTerm.get(0));
		String right = new TermToCpp(asm).visit(argsTerm.get(1));
		return new Util().setPars(updateVarName(left) + " / " + updateVarName(right));
	}
}
