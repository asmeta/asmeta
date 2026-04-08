package asmeta.ai.propgen;

import org.apache.log4j.Logger;

import asmeta.ai.propgen.PropertyValidator.ParsingResult;

public class PropertyGenerationSession {

	PropertyGenerator generator;

	private static Logger logger = Logger.getLogger(PropertyGenerationSession.class);

	public PropertyGenerationSession(PropertyGenerator generator) {
		this.generator = generator;
	}

	/**
	 * Generates a temporal-logic property from a natural-language description and
	 * validates it against the given ASMETA model. If the first generated property
	 * is invalid, the method retries generation through repair prompts until a
	 * valid property is obtained or the maximum number of retries is reached.
	 *
	 * @param asmPath        path to the ASMETA model file
	 * @param nlProperty     natural-language property to translate
	 * @param type           type of the property to generate
	 * @param removeComments whether comments should be removed from the ASMETA
	 *                       model before generation
	 * @param maxRetries     maximum number of repair attempts after the initial
	 *                       generation
	 * @return a syntactically valid temporal-logic property
	 * @throws RuntimeException if the LLM is unable to generate a syntactically
	 *                          correct property within the allowed number of
	 *                          attempts
	 */
	public String fromNLtoTLSession(String asmPath, String nlProperty, PropertyType type, boolean removeComments,
			int maxRetries) {
		// First attempt: generate the property from the natural-language input.
		String tlProperty = generator.fromNLtoTL(asmPath, nlProperty, type, removeComments);
		ParsingResult firstResult = PropertyValidator.validateProperty(asmPath, tlProperty, type);
		if (firstResult.isValid())
			return tlProperty;
		// Retry with repair prompts until the property becomes valid or until max
		// retries are reached
		int tries = 0;
		String oldProperty = tlProperty;
		String errMessage = firstResult.getErrorMessage();
		while (tries < maxRetries) {
			logger.debug("Repair prompt " + (tries + 1));
			String newProperty = generator.repairTL(asmPath, nlProperty, oldProperty, type, errMessage, removeComments);
			ParsingResult repairResult = PropertyValidator.validateProperty(asmPath, newProperty, type);
			if (repairResult.isValid())
				return newProperty;
			oldProperty = newProperty;
			errMessage = repairResult.getErrorMessage();
			tries++;
		}
		throw new RuntimeException("Failed to generate a syntactically correct temporal logic property after "
				+ maxRetries + " repair attempts. Last error: " + errMessage
				+ ". Ensure required libraries (LTL and CTL) are imported.");
	}

}
