package asmeta.evotest.junit2avalla.javascenario;

import asmeta.evotest.junit2avalla.model.terms.JavaAssertionTerm;

public class ScenarioParserUtil {

	/* Constants */
	static final String NATURAL_SUFFIX = "n";
	static final String GET_NATURAL_FLAG = "get_natural_";
	static final String NATURAL_FLAG = "natural_";
	static final String FROM_DOMAIN = "_fromDomain_";
	static final String ATG_CLASS_FLAG = "_ATG";

	/** private constructor to prevent instantiation */
	private ScenarioParserUtil() {
		// NO ARGS CONSTRUCTOR
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
	 * Build the getter in case of a natural type domain
	 * 
	 * <p>
	 * example: get_natural_function -> get_function<br>
	 * value: 3 -> 3n
	 * <p>
	 * 
	 * @param getter the current function name to be processed
	 * @return the processed name
	 */
	static String buildNaturalGetter(String getter, JavaAssertionTerm javaAssertionTerm) {
		if (getter.contains(GET_NATURAL_FLAG)) {
			// if it's a natural type domain
			// remove the natural_ flag and add the n suffix
			getter = getter.replaceFirst(NATURAL_FLAG, "");
			javaAssertionTerm.setActual(javaAssertionTerm.getActual() + NATURAL_SUFFIX);
		}
		return getter;
	}

}
