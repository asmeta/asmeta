package asmeta.evotest.junit2avalla.javascenario;

import asmeta.evotest.junit2avalla.model.terms.JavaAssertionTerm;
import asmeta.evotest.junit2avalla.model.terms.JavaVariableTerm;

public class ScenarioParserUtil {

	/* Constants */
	static final String NATURAL_SUFFIX = "n";
	static final String GET_NATURAL_FLAG = "get_natural_";
	static final String NATURAL_FLAG = "natural_";
	static final String FROM_DOMAIN = "_fromDomain_";
	static final String ATG_CLASS_FLAG = "_ATG";
	static final String STEP = "step";
	static final String GET_FLAG = "get_";
	static final String SET_FLAG = "set_";
	static final String ASSERT_EQUALS = "assertEquals";
	static final String ASSERT_TRUE = "assertTrue";
	static final String ASSERT_FALSE = "assertFalse";
	static final String SET_NATURAL_FLAG = "set_natural_";
	static final String ABSTRACT_FLAG = "abstract_";
	static final String SEQUENCE_FLAG = "sequence_";
	static final String SET_SEQUENCE_FLAG = "set_sequence_";
	static final String SET_ABSTRACT_FLAG = "set_abstract_";
	static final String VALUE_OF = "valueOf";

	/** private constructor to prevent instantiation */
	private ScenarioParserUtil() {
		// NO ARGS CONSTRUCTOR
	}
	
	/**
	 * Handles the Asm declaration instruction, generating an Header and Load Term
	 * 
	 * @param asmName the name of the asmeta specification.
	 * @param context the context containing shared data for the visitor operations
	 */
	static void buildAsmDeclaration(String asmName, Context context) {
		context.getScenarioManager().setHeaderTerm(context.getCurrentScenario(), asmName, context.getScenarioIndex());
		context.getScenarioManager().setLoadTerm(context.getCurrentScenario(), asmName);
	}

	/**
	 * Build the name of a function with Domain -> Codomain with the avalla
	 * standard.
	 * 
	 * <p>
	 * example: set_function_fromDomain_STATE1 -> set_function(STATE1)
	 * <p>
	 * 
	 * @param name the current function name to be processed
	 * @return the processed name
	 */
	static String buildDomainCodomain(String name) {
		if (name.contains(ScenarioParserUtil.FROM_DOMAIN)) {
			// Building the Domain -> Codomain operator
			name = name.replace(ScenarioParserUtil.FROM_DOMAIN, "(").concat(")");
		}
		return name;
	}

	/**
	 * Build the getter in case of a natural type domain and set it to the actual
	 * term
	 * 
	 * <p>
	 * example: get_natural_function -> get_function<br>
	 * value: 3 -> 3n
	 * <p>
	 * 
	 * @param getter the current function name to be processed
	 */
	static void buildNaturalGetter(String getter, JavaAssertionTerm javaAssertionTerm) {
		if (getter.contains(GET_NATURAL_FLAG)) {
			// if it's a natural type domain
			// remove the natural_ flag and add the n suffix
			getter = getter.replaceFirst(NATURAL_FLAG, "");
			javaAssertionTerm.setExpected(javaAssertionTerm.getExpected() + NATURAL_SUFFIX);
		}
		javaAssertionTerm.setActual(getter);
	}

	/**
	 * Set the value of a primitive java variable.
	 * 
	 * @param value               value to set.
	 * @param currentJavaVariable the current javaVariableTerm
	 */
	static void setPrimitiveVariable(String value, JavaVariableTerm currentJavaVariable) {
		currentJavaVariable.setValue(value);
		currentJavaVariable.setPrimitive(true);
	}

	/**
	 * Set the value of a non primitive java variable.
	 * 
	 * @param value               value to set.
	 * @param currentJavaVariable the current javaVariableTerm
	 */
	static void setNotPrimitiveVariable(String value, JavaVariableTerm currentJavaVariable) {
		currentJavaVariable.setValue(value);
		currentJavaVariable.setPrimitive(false);
	}

	/**
	 * Sets the value field of a String variable and adds it to the
	 * currentJavaVariable
	 * 
	 * @param value               the argument of the setter
	 * @param currentJavaVariable the current javaVariableTerm
	 */
	static void setStringVariableValue(String value, JavaVariableTerm currentJavaVariable) {
		String setter = currentJavaVariable.getName();
		if (setter.contains(SET_ABSTRACT_FLAG)) {
			// it's an abstract type
			// Replace the abstract flag for the abstract type
			// remove the flag abstract_
			currentJavaVariable.setName(setter.replace(ABSTRACT_FLAG, ""));
			// set primitive to true to remove double quotes
			currentJavaVariable.setPrimitive(true);
		} else if (setter.contains(SET_SEQUENCE_FLAG)) {
			// it's a sequence domain
			// Replace the sequence flag for the abstract type
			// remove the flag sequence_
			currentJavaVariable.setName(setter.replace(SEQUENCE_FLAG, ""));
			// set primitive to true to remove double quotes
			currentJavaVariable.setPrimitive(true);
			// ensure the sequence domain value in Avalla is always delimited by square
			// brackets
			// removing existing square brackets and double quotes if present.
			value = "[" + value.replaceAll("[\\[\\]\"]", "") + "]";
		} else {
			// it's a String, set primitive to false to keep double quotes
			currentJavaVariable.setPrimitive(false);
		}
		currentJavaVariable.setValue(value);
	}

	/**
	 * Sets the value field of a Integer variable and adds it to the
	 * currentJavaVariable
	 * 
	 * @param value               the argument of the setter
	 * @param currentJavaVariable the current javaVariableTerm
	 */
	static void setIntegerVariableValue(String value, JavaVariableTerm currentJavaVariable) {
		String functionName = currentJavaVariable.getName();
		// Setting the primitive number value
		if (functionName.contains(SET_NATURAL_FLAG)) {
			setNaturalVariableValue(value, currentJavaVariable);
		} else {
			// integer, double
			currentJavaVariable.setValue(value);
		}
		currentJavaVariable.setPrimitive(true);
	}
	
	static void setNaturalVariableValue(String value, JavaVariableTerm currentJavaVariable) {
		String functionName = currentJavaVariable.getName();
		if (functionName.contains(SET_NATURAL_FLAG)) {
			// if it's a natural type domain
			// replace the natural_ flag
			currentJavaVariable.setName(functionName.replaceFirst(ScenarioParserUtil.NATURAL_FLAG, ""));
			// add the suffix 'n' to the number
			currentJavaVariable.setValue(value + ScenarioParserUtil.NATURAL_SUFFIX);
		}
	}

}
