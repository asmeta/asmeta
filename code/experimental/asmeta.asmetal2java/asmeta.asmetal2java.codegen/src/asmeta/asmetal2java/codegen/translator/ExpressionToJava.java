package asmeta.asmetal2java.codegen.translator;

import java.util.List;

import asmeta.definitions.domains.ConcreteDomain;
import asmeta.definitions.domains.Domain;
import asmeta.definitions.domains.PowersetDomain;
import asmeta.definitions.domains.ProductDomain;
import asmeta.definitions.domains.SequenceDomain;
import asmeta.structure.Asm;
import asmeta.terms.basicterms.SetTerm;
import asmeta.terms.basicterms.Term;
import asmeta.terms.furtherterms.SequenceTerm;

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
				|| function.equals("^") || function.equals("iton") || function.equals("rton")
				|| function.equals("toNatural") || function.equals("at")
				|| function.equals("indexOf") || function.equals("chooseone") || function.equals("first")
				|| function.equals("second") || function.equals("third") || function.equals("fourth")
				|| function.equals("fifth") || function.equals("sixth") || function.equals("seventh")
				|| function.equals("eighth") || function.equals("ninth") || function.equals("length")
				|| function.equals("union") || function.equals("append") || function.equals("prepend")
				|| function.equals("tail") || function.equals("contains") || function.equals("including")
				|| function.equals("excluding") || function.equals("isEmpty") || function.equals("replaceAt")
				|| function.equals("asSequence") || function.equals("asSet") || function.equals("last")
				|| function.equals("count") || function.equals("insertAt") || function.equals("subSequence")
				|| function.equals("abs") || function.equals("floor") || function.equals("round")
				|| function.equals("sqrt") || function.equals("min") || function.equals("max")
				|| function.equals("idiv") || function.equals("concat") || function.equals("intersection")
				|| function.equals("difference") || function.equals("symmetricDifference");
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
		case ("rton"):
			return rton(argsTerm);
		case ("toNatural"):
			return toNatural(argsTerm);
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
		case ("append"):
			return append(argsTerm);
		case ("prepend"):
			return prepend(argsTerm);
		case ("tail"):
			return tail(argsTerm);
		case ("contains"):
			return contains(argsTerm);
		case ("including"):
			return including(argsTerm);
		case ("excluding"):
			return excluding(argsTerm);
		case ("isEmpty"):
			return isEmpty(argsTerm);
		case ("replaceAt"):
			return replaceAt(argsTerm);
		case ("asSequence"):
			return asSequence(argsTerm);
		case ("asSet"):
			return asSet(argsTerm);
		case ("last"):
			return last(argsTerm);
		case ("count"):
			return count(argsTerm);
		case ("insertAt"):
			return insertAt(argsTerm);
		case ("subSequence"):
			return subSequence(argsTerm);
		case ("abs"):
			return numericUnary(argsTerm, "Math.abs");
		case ("floor"):
			return floor(argsTerm);
		case ("round"):
			return round(argsTerm);
		case ("sqrt"):
			return numericUnary(argsTerm, "Math.sqrt");
		case ("min"):
			return numericBinary(argsTerm, "Math.min");
		case ("max"):
			return numericBinary(argsTerm, "Math.max");
		case ("idiv"):
			return idiv(argsTerm);
		case ("concat"):
			return concat(argsTerm);
		case ("intersection"):
			return intersection(argsTerm);
		case ("difference"):
			return difference(argsTerm);
		case ("symmetricDifference"):
			return symmetricDifference(argsTerm);
		case ("^"):
			return numericBinary(argsTerm, "Math.pow");
		case ("/"):
			return divide(argsTerm);
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

	private String append(List<Term> argsTerm) {
		String sequence = new TermToJavaStandardLibrary(asm).visit(argsTerm.get(0));
		String elementStream = streamOf(argsTerm.get(1));
		return "java.util.stream.Stream.concat(" + sequence
				+ ".stream(), " + elementStream + ").collect(java.util.stream.Collectors.toList())";
	}

	private String streamOf(Term elementTerm) {
		String element = new TermToJavaStandardLibrary(asm).visit(elementTerm);
		if (elementTerm instanceof SequenceTerm && elementTerm.getDomain() instanceof SequenceDomain sequenceDomain
				&& sequenceDomain.getDomain() instanceof SequenceDomain) {
			String elementType = "List" + new DomainToJavaString(asm).visit(sequenceDomain).trim();
			return "java.util.stream.Stream.<" + elementType + ">of(" + element + ")";
		}
		return "java.util.stream.Stream.of(" + element + ")";
	}

	private String prepend(List<Term> argsTerm) {
		String elementStream = streamOf(argsTerm.get(0));
		String sequence = new TermToJavaStandardLibrary(asm).visit(argsTerm.get(1));
		return "java.util.stream.Stream.concat(" + elementStream + ", " + sequence
				+ ".stream()).collect(java.util.stream.Collectors.toList())";
	}

	private String tail(List<Term> argsTerm) {
		String sequence = new TermToJavaStandardLibrary(asm).visit(argsTerm.get(0));
		return "new ArrayList<>(" + sequence + ".subList(1, " + sequence + ".size()))";
	}

	private String contains(List<Term> argsTerm) {
		String sequence = new TermToJavaStandardLibrary(asm).visit(argsTerm.get(0));
		String element = new TermToJavaStandardLibrary(asm).visit(argsTerm.get(1));
		return sequence + ".contains(" + element + ")";
	}

	private String including(List<Term> argsTerm) {
		String set = new TermToJavaStandardLibrary(asm).visit(argsTerm.get(0));
		String element = new TermToJavaStandardLibrary(asm).visit(argsTerm.get(1));
		return "java.util.stream.Stream.concat(" + set + ".stream(), java.util.stream.Stream.of(" + element
				+ ")).collect(java.util.stream.Collectors.toSet())";
	}

	private String excluding(List<Term> argsTerm) {
		String collection = new TermToJavaStandardLibrary(asm).visit(argsTerm.get(0));
		String element = new TermToJavaStandardLibrary(asm).visit(argsTerm.get(1));
		Domain domain = argsTerm.get(0).getDomain();
		if (domain instanceof ConcreteDomain)
			domain = ((ConcreteDomain) domain).getTypeDomain();
		if (domain instanceof SequenceDomain)
			return "new java.util.ArrayList<>(" + collection + ") {{ remove(" + element + "); }}";
		return collection + ".stream().filter(e -> !java.util.Objects.equals(e, " + element
				+ ")).collect(java.util.stream.Collectors.toSet())";
	}

	private String union(List<Term> argsTerm) {
		String first = collectionOperand(argsTerm.get(0));
		String second = collectionOperand(argsTerm.get(1));
		Domain domain = argsTerm.get(0).getDomain();
		if (domain instanceof ConcreteDomain)
			domain = ((ConcreteDomain) domain).getTypeDomain();
		String collector = domain instanceof PowersetDomain ? "toSet()" : "toList()";
		return "java.util.stream.Stream.concat(" + first + ".stream(), " + second
				+ ".stream()).collect(java.util.stream.Collectors." + collector + ")";
	}

	private String intersection(List<Term> argsTerm) {
		requirePowerset("intersection", argsTerm.get(0));
		String first = collectionOperand(argsTerm.get(0));
		String second = collectionOperand(argsTerm.get(1));
		return first + ".stream().filter(" + second
				+ "::contains).collect(java.util.stream.Collectors.toSet())";
	}

	private String difference(List<Term> argsTerm) {
		requirePowerset("difference", argsTerm.get(0));
		String first = collectionOperand(argsTerm.get(0));
		String second = collectionOperand(argsTerm.get(1));
		return first + ".stream().filter(e -> !" + second
				+ ".contains(e)).collect(java.util.stream.Collectors.toSet())";
	}

	private String symmetricDifference(List<Term> argsTerm) {
		requirePowerset("symmetricDifference", argsTerm.get(0));
		String first = collectionOperand(argsTerm.get(0));
		String second = collectionOperand(argsTerm.get(1));
		return "java.util.stream.Stream.concat(" + first + ".stream().filter(e -> !" + second
				+ ".contains(e)), " + second + ".stream().filter(e -> !" + first
				+ ".contains(e))).collect(java.util.stream.Collectors.toSet())";
	}

	private void requirePowerset(String function, Term term) {
		Domain domain = term.getDomain();
		if (domain instanceof ConcreteDomain)
			domain = ((ConcreteDomain) domain).getTypeDomain();
		if (!(domain instanceof PowersetDomain))
			throw new InvalidFunctionException("StandardLibrary function '" + function
					+ "' is supported only for Powerset arguments by the Java generator");
	}

	private String numericUnary(List<Term> argsTerm, String javaFunction) {
		return javaFunction + "(" + new TermToJavaStandardLibrary(asm).visit(argsTerm.get(0)) + ")";
	}

	private String numericBinary(List<Term> argsTerm, String javaFunction) {
		String first = new TermToJavaStandardLibrary(asm).visit(argsTerm.get(0));
		String second = new TermToJavaStandardLibrary(asm).visit(argsTerm.get(1));
		return javaFunction + "(" + first + ", " + second + ")";
	}

	private String floor(List<Term> argsTerm) {
		return "(int) Math.floor(" + new TermToJavaStandardLibrary(asm).visit(argsTerm.get(0)) + ")";
	}

	private String round(List<Term> argsTerm) {
		return "(int) Math.round(" + new TermToJavaStandardLibrary(asm).visit(argsTerm.get(0)) + ")";
	}

	private String divide(List<Term> argsTerm) {
		String first = new TermToJavaStandardLibrary(asm).visit(argsTerm.get(0));
		String second = new TermToJavaStandardLibrary(asm).visit(argsTerm.get(1));
		return new Util().setPars(first + " / (double) " + second);
	}

	private String concat(List<Term> argsTerm) {
		return addOperator(argsTerm, "+");
	}

	private String isEmpty(List<Term> argsTerm) {
		return new TermToJavaStandardLibrary(asm).visit(argsTerm.get(0)) + ".isEmpty()";
	}

	private String replaceAt(List<Term> argsTerm) {
		String sequence = new TermToJavaStandardLibrary(asm).visit(argsTerm.get(0));
		String index = new TermToJavaStandardLibrary(asm).visit(argsTerm.get(1));
		String element = new TermToJavaStandardLibrary(asm).visit(argsTerm.get(2));
		return "java.util.stream.IntStream.range(0, " + sequence + ".size())"
				+ ".mapToObj(i -> i == " + index + " ? " + element + " : " + sequence + ".get(i))"
				+ ".collect(java.util.stream.Collectors.toList())";
	}

	private String asSequence(List<Term> argsTerm) {
		return "new java.util.ArrayList<>(" + new TermToJavaStandardLibrary(asm).visit(argsTerm.get(0)) + ")";
	}

	private String asSet(List<Term> argsTerm) {
		return "new java.util.HashSet<>(" + new TermToJavaStandardLibrary(asm).visit(argsTerm.get(0)) + ")";
	}

	private String last(List<Term> argsTerm) {
		String sequence = new TermToJavaStandardLibrary(asm).visit(argsTerm.get(0));
		return sequence + ".get(" + sequence + ".size() - 1)";
	}

	private String count(List<Term> argsTerm) {
		String collection = new TermToJavaStandardLibrary(asm).visit(argsTerm.get(0));
		String element = new TermToJavaStandardLibrary(asm).visit(argsTerm.get(1));
		return "(int) " + collection + ".stream().filter(e -> java.util.Objects.equals(e, " + element
				+ ")).count()";
	}

	private String insertAt(List<Term> argsTerm) {
		String sequence = new TermToJavaStandardLibrary(asm).visit(argsTerm.get(0));
		String index = new TermToJavaStandardLibrary(asm).visit(argsTerm.get(1));
		String element = new TermToJavaStandardLibrary(asm).visit(argsTerm.get(2));
		return "java.util.stream.Stream.concat(java.util.stream.Stream.concat(" + sequence + ".subList(0, "
				+ index + ").stream(), java.util.stream.Stream.of(" + element + ")), " + sequence + ".subList("
				+ index + ", " + sequence + ".size()).stream()).collect(java.util.stream.Collectors.toList())";
	}

	private String subSequence(List<Term> argsTerm) {
		String sequence = new TermToJavaStandardLibrary(asm).visit(argsTerm.get(0));
		String from = new TermToJavaStandardLibrary(asm).visit(argsTerm.get(1));
		String to = new TermToJavaStandardLibrary(asm).visit(argsTerm.get(2));
		return "new java.util.ArrayList<>(" + sequence + ".subList(" + from + ", " + to + "))";
	}

	private String collectionOperand(Term term) {
		String translated = new TermToJavaStandardLibrary(asm).visit(term);
		if (term instanceof SetTerm)
			return "new HashSet<>(Arrays.asList" + translated + ")";
		return translated;
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
		return "toNatural(" + new TermToJavaStandardLibrary(asm).visit(argsTerm.get(0)) + ")";
	}

	/**
	 * Executes the rton function.
	 *
	 * @param argsTerm the args term
	 *
	 * @return the string
	 */
	private String rton(List<Term> argsTerm) {
		return "toNatural((int) (" + new TermToJavaStandardLibrary(asm).visit(argsTerm.get(0)) + "))";
	}

	/**
	 * Executes the toNatural function.
	 *
	 * @param argsTerm the args term
	 *
	 * @return the string
	 */
	private String toNatural(List<Term> argsTerm) {
		return "toNatural(Integer.parseInt(" + new TermToJavaStandardLibrary(asm).visit(argsTerm.get(0)) + "))";
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
		String left = operatorOperand(argsTerm.get(0));
		String right = operatorOperand(argsTerm.get(1));
		return new Util().setPars(left + " " +operator + " " + right);
	}

	/**
	 * Returns the Java value on which a binary operator must operate.
	 *
	 * <p>Concrete domains are represented by generated wrapper classes. Java
	 * operators, however, must receive the wrapped value regardless of whether the
	 * other operand belongs to the same concrete domain, to a different concrete
	 * domain, or to its base domain.</p>
	 */
	private String operatorOperand(Term term) {
		String operand = new TermToJavaStandardLibrary(asm).visit(term);
		if (term.getDomain() instanceof ConcreteDomain && !operand.endsWith(VALUE_FIELD_NAME)) {
			operand += VALUE_FIELD_NAME;
		}
		return operand;
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
