package asmeta.asmetal2java.codegen.translator;

import java.util.List;

import asmeta.definitions.domains.ConcreteDomain;
import asmeta.definitions.domains.ProductDomain;
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
				|| function.equals("indexOf") || function.equals("chooseone") || function.equals("first")
				|| function.equals("second") || function.equals("third") || function.equals("fourth")
				|| function.equals("fifth") || function.equals("sixth") || function.equals("seventh")
				|| function.equals("eighth") || function.equals("ninth") || function.equals("length")
				|| function.equals("union");
	}

	/**
	 * Evaluate function.
	 *
	 * @param function the function
	 * @param argsTerm the args term
	 *
	 * @return the string
	 *
	 * @throws InvalidFunctionException the exception
	 */
	String evaluateFunction(String function, List<Term> argsTerm) throws InvalidFunctionException {
		switch (function) {
		case "<":
			return addOperator(argsTerm, "<");
		case ("<="):
			return addOperator(argsTerm, "<=");
		case (">"):
			return addOperator(argsTerm, ">");
		case (">="):
			return addOperator(argsTerm, ">=");
		case ("->"):
			return implies(argsTerm);
		case ("chooseone"):
			return chooseone(argsTerm);
		case ("iton"):
			return iton(argsTerm);
		case ("="):
			return addOperator(argsTerm, "==");
		case ("at"):
			return at(argsTerm);
		case ("indexOf"):
			return indexOf(argsTerm);
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
			return projection(argsTerm, 0);
		case ("second"):
			return projection(argsTerm, 1);
		case ("third"):
			return projection(argsTerm, 2);
		case ("fourth"):
			return projection(argsTerm, 3);
		case ("fifth"):
			return projection(argsTerm, 4);
		case ("sixth"):
			return projection(argsTerm, 5);
		case ("seventh"):
			return projection(argsTerm, 6);
		case ("eighth"):
			return projection(argsTerm, 7);
		case ("ninth"):
			return projection(argsTerm, 8);
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
	 * Translates first through ninth for products and preserves first for sequences.
	 *
	 * @param argsTerm the args term
	 *
	 * @return the string
	 */
	private String projection(List<Term> argsTerm, int index) {
		Term source = argsTerm.get(0);
		String value = new TermToJavaStandardLibrary(asm).visit(source);
		if (source.getDomain() instanceof ProductDomain) {
			return value + ".getValue" + index + "()";
		}
		if (index == 0) {
			// first is overloaded for sequences in the StandardLibrary.
			return value + ".get(0)";
		}
		throw new InvalidFunctionException("Projection " + (index + 1) + " requires a ProductDomain argument");
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
		String getter = argsTerm.get(0).getDomain() instanceof ProductDomain ? ".getValue(" : ".get(";
		return first + getter + second + ")";
	}

	private String indexOf(List<Term> argsTerm) {
		String product = new TermToJavaStandardLibrary(asm).visit(argsTerm.get(0));
		String value = new TermToJavaStandardLibrary(asm).visit(argsTerm.get(1));
		return product + ".indexOf(" + value + ")";
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


	private String addOperator(List<Term> argsTerm, String operator) {
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
		return new Util().setPars(left + " " +operator + " " + right);
	}


	/**
	 * Executes the not equal function.
	 *
	 * @param argsTerm the args term
	 *
	 * @return the string
	 */
	private String notEquals(List<Term> argsTerm) {
		return addOperator(argsTerm, "!=");
	}

	/**
	 * Executes the mod function.
	 *
	 * @param argsTerm the args term
	 *
	 * @return the string
	 */
	private String mod(List<Term> argsTerm) {
		return addOperator(argsTerm, "%");
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
	 */
	String minusUnary(List<Term> argsTerm) {
		String operand = new TermToJavaStandardLibrary(asm).visit(argsTerm.get(0));
		return "-(" + operand + ")";
	}

	/**
	 * Minus binary.
	 *
	 * @param argsTerm the args term
	 *
	 * @return the string
	 */
	String minusBinary(List<Term> argsTerm) {
		return addOperator(argsTerm, "-");
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
		return addOperator(argsTerm, "+");
	}

	/**
	 * Executes the multiply function.
	 *
	 * @param argsTerm the args term
	 *
	 * @return the string
	 */
	String mult(List<Term> argsTerm) {
		return addOperator(argsTerm, "*");
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
		return addOperator(argsTerm, "/");
	}
}
