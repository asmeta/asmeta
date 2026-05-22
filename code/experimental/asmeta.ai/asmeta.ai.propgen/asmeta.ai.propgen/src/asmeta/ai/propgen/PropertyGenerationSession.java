package asmeta.ai.propgen;

import java.util.function.Consumer;

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
		return fromNLtoTLSession(asmPath, nlProperty, type, removeComments, maxRetries, message -> {}, message -> {});
	}

	public String fromNLtoTLSession(String asmPath, String nlProperty, PropertyType type, boolean removeComments,
			int maxRetries, Consumer<String> progressConsumer, Consumer<String> debugConsumer) {
		// First attempt: generate the property from the natural-language input.
		notify(progressConsumer, "Generating candidate temporal property...");
		String tlProperty = generator.fromNLtoTL(asmPath, nlProperty, type, removeComments);
		logDebug(debugConsumer, "Generated candidate property:\n" + tlProperty);
		notify(progressConsumer, "Validating generated property...");
		ParsingResult firstResult = PropertyValidator.validateProperty(asmPath, propertyBodyForValidator(tlProperty, type),
				type);
		if (firstResult.isValid()) {
			notify(progressConsumer, "Generated property is syntactically valid.");
			return tlProperty;
		}
		// Retry with repair prompts until the property becomes valid or until max
		// retries are reached
		int tries = 0;
		String oldProperty = tlProperty;
		String errMessage = firstResult.getErrorMessage();
		logDebug(debugConsumer, "Validation error:\n" + errMessage);
		notify(progressConsumer, "Generated property is not syntactically valid. Starting repair attempts...");
		while (tries < maxRetries) {
			int attemptNumber = tries + 1;
			notify(progressConsumer, "Repair attempt " + attemptNumber + " of " + maxRetries + "...");
			logger.debug("Repair prompt " + attemptNumber);
			String newProperty = generator.repairTL(asmPath, nlProperty, oldProperty, type, errMessage, removeComments);
			logDebug(debugConsumer, "Repair attempt " + attemptNumber + " candidate property:\n" + newProperty);
			notify(progressConsumer, "Validating repair attempt " + attemptNumber + "...");
			ParsingResult repairResult = PropertyValidator.validateProperty(asmPath, propertyBodyForValidator(newProperty, type),
					type);
			if (repairResult.isValid()) {
				notify(progressConsumer, "Repair attempt " + attemptNumber + " produced a syntactically valid property.");
				return newProperty;
			}
			oldProperty = newProperty;
			errMessage = repairResult.getErrorMessage();
			logDebug(debugConsumer, "Repair attempt " + attemptNumber + " validation error:\n" + errMessage);
			notify(progressConsumer, "Repair attempt " + attemptNumber + " is still not syntactically valid.");
			tries++;
		}
		throw new RuntimeException("Failed to generate a syntactically correct temporal logic property after "
				+ maxRetries + " repair attempts. Last error: " + errMessage);
	}

	private void notify(Consumer<String> consumer, String message) {
		if (consumer != null) {
			consumer.accept(message);
		}
	}

	private void logDebug(Consumer<String> debugConsumer, String message) {
		logger.debug(message);
		notify(debugConsumer, "[debug] " + message);
	}

	private String propertyBodyForValidator(String property, PropertyType type) {
		String trimmedProperty = property == null ? "" : property.trim();
		String keyword = type == PropertyType.CTLPROP ? "CTLSPEC" : "LTLSPEC";
		if (trimmedProperty.startsWith(keyword)) {
			return trimmedProperty.substring(keyword.length()).trim();
		}
		return trimmedProperty;
	}
}
