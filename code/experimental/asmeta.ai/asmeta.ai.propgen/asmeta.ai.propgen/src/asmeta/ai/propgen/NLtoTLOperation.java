package asmeta.ai.propgen;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import org.asmeta.parser.ASMParser;
import org.asmeta.parser.AsmetaParserUtility;
import org.asmeta.parser.util.AsmPrinter;
import org.apache.log4j.Logger;

import asmeta.ai.propgen.llm.LlmClient;
import asmeta.AsmCollection;
import asmeta.definitions.CtlSpec;
import asmeta.definitions.DefinitionsFactory;
import asmeta.definitions.LtlSpec;
import asmeta.definitions.Property;
import asmeta.definitions.impl.DefinitionsFactoryImpl;
import asmeta.structure.Asm;
import asmeta.terms.basicterms.BasictermsFactory;
import asmeta.terms.basicterms.BooleanTerm;
import asmeta.terms.basicterms.impl.BasictermsFactoryImpl;

/**
 * Generates and validates a temporal-logic property from natural language.
 */
public final class NLtoTLOperation implements AsmetaAIOperation {

	private static final String O2_PROMPT_TEMPLATE = RESOURCES + "/prompt_template_o2.txt";
	private static final String O2_REPAIR_PROMPT_TEMPLATE = RESOURCES + "/prompt_template_o2_repair.txt";
	private static final String TEMP_ASM_NAME = "TEMP_ASM_FOR_PROPERTY_VALIDATION";

	private static final Map<PropertyType, String[]> PROPERTY_CONFIG = Map.of(
			PropertyType.CTLPROP, new String[] {"CTL (Computation Tree Logic)", "ex, ef, eg, eu, ax, af, ag, au", "CTLSPEC"},
			PropertyType.LTLPROP, new String[] {"LTL (Linear Temporal Logic)", "x, f, g, u, v", "LTLSPEC"}
		);

	private static final Logger logger = Logger.getLogger(NLtoTLOperation.class);

	private final LlmClient llm;

	public NLtoTLOperation(LlmClient llm) {
		this.llm = llm;
	}

	@Override
	public String execute(AsmetaAIOperationRequest request) {
		return generateValidated(request.getAsmPath(), request.getInput(), request.getPropertyType(),
				request.isRemoveComments(), request.getMaxRetries(), request.getListener());
	}

	protected String generate(String asmPath, String property, PropertyType type, boolean removeComments) {
		Map<String, String> substitutions = propertySubstitutions(asmPath, property, type, removeComments);
		String prompt = applySubstitutions(getFileContent(O2_PROMPT_TEMPLATE), substitutions);
		logger.debug("Prompt: \n" + prompt);
		String response = llm.query(prompt);
		logger.debug("Full response: \n" + response);
		return response;
	}

	protected String repair(String asmPath, String nlProperty, String brokenProperty, PropertyType type,
			String compilerError, boolean removeComments) {
		Map<String, String> substitutions = propertySubstitutions(asmPath, nlProperty, type, removeComments);
		substitutions.put("BROKEN_PROPERTY_PLACEHOLDER", brokenProperty);
		substitutions.put("COMPILER_ERROR_PLACEHOLDER", compilerError);
		String prompt = applySubstitutions(getFileContent(O2_REPAIR_PROMPT_TEMPLATE), substitutions);
		logger.debug("Prompt: \n" + prompt);
		String response = llm.query(prompt);
		logger.debug("Full response: \n" + response);
		return response;
	}

	protected String generateValidated(String asmPath, String nlProperty, PropertyType type, boolean removeComments,
			int maxRetries) {
		return generateValidated(asmPath, nlProperty, type, removeComments, maxRetries,
				AsmetaAIOperationListener.NO_OP);
	}

	protected String generateValidated(String asmPath, String nlProperty, PropertyType type, boolean removeComments,
			int maxRetries, AsmetaAIOperationListener listener) {
		LibraryPreparer.prepare(asmPath, type, listener);
		listener.onProgress("Generating candidate temporal property...");
		String tlProperty = generate(asmPath, nlProperty, type, removeComments);
		logDebug(listener, "Generated candidate property:\n" + tlProperty);
		listener.onProgress("Validating generated property...");
		ParsingResult firstResult = validateProperty(asmPath, propertyBodyForValidator(tlProperty, type), type);
		if (firstResult.isValid()) {
			listener.onProgress("Generated property is syntactically valid.");
			return tlProperty;
		}

		int tries = 0;
		String oldProperty = tlProperty;
		String errMessage = firstResult.getErrorMessage();
		logDebug(listener, "Validation error:\n" + errMessage);
		listener.onProgress("Generated property is not syntactically valid. Starting repair attempts...");
		while (tries < maxRetries) {
			int attemptNumber = tries + 1;
			listener.onProgress("Repair attempt " + attemptNumber + " of " + maxRetries + "...");
			logger.debug("Repair prompt " + attemptNumber);
			String newProperty = repair(asmPath, nlProperty, oldProperty, type, errMessage, removeComments);
			logDebug(listener, "Repair attempt " + attemptNumber + " candidate property:\n" + newProperty);
			listener.onProgress("Validating repair attempt " + attemptNumber + "...");
			ParsingResult repairResult = validateProperty(asmPath, propertyBodyForValidator(newProperty, type), type);
			if (repairResult.isValid()) {
				listener.onProgress("Repair attempt " + attemptNumber + " produced a syntactically valid property.");
				return newProperty;
			}
			oldProperty = newProperty;
			errMessage = repairResult.getErrorMessage();
			logDebug(listener, "Repair attempt " + attemptNumber + " validation error:\n" + errMessage);
			listener.onProgress("Repair attempt " + attemptNumber + " is still not syntactically valid.");
			tries++;
		}
		throw new RuntimeException("Failed to generate a syntactically correct temporal logic property after "
				+ maxRetries + " repair attempts. Last error: " + errMessage);
	}

	private Map<String, String> propertySubstitutions(String asmPath, String property, PropertyType type,
			boolean removeComments) {
		Map<String, String> substitutions = new HashMap<>();
		String[] config = PROPERTY_CONFIG.get(type);
		substitutions.put("MODEL_PLACEHOLDER", readModel(asmPath, removeComments));
		substitutions.put("PROPERTY_TYPE_PLACEHOLDER", config[0]);
		substitutions.put("NL_PROPERTY_PLACEHOLDER", property);
		substitutions.put("OPERATORS_LIST_PLACEHOLDER", config[1]);
		substitutions.put("PROPERTY_KEYWORD_PLACEHOLDER", config[2]);
		return substitutions;
	}

	private void logDebug(AsmetaAIOperationListener listener, String message) {
		logger.debug(message);
		listener.onDebug(message);
	}

	private String propertyBodyForValidator(String property, PropertyType type) {
		String trimmedProperty = property == null ? "" : property.trim();
		String keyword = type == PropertyType.CTLPROP ? "CTLSPEC" : "LTLSPEC";
		if (trimmedProperty.startsWith(keyword)) {
			return trimmedProperty.substring(keyword.length()).trim();
		}
		return trimmedProperty;
	}

	private ParsingResult validateProperty(String asmPath, String property, PropertyType type) {
		ParsingResult originalParseResult = parseAsm(asmPath);
		if (!originalParseResult.isValid()) {
			throw new RuntimeException(
					"Could not parse the original ASMETA model: " + originalParseResult.getErrorMessage());
		}

		DefinitionsFactory definitionsFactory = new DefinitionsFactoryImpl();
		BasictermsFactory termFactory = new BasictermsFactoryImpl();

		BooleanTerm booleanTerm = termFactory.createBooleanTerm();
		booleanTerm.setSymbol(property);

		Property spec;
		if (type == PropertyType.CTLPROP) {
			CtlSpec ctlSpec = definitionsFactory.createCtlSpec();
			ctlSpec.setBody(booleanTerm);
			spec = ctlSpec;
		} else {
			LtlSpec ltlSpec = definitionsFactory.createLtlSpec();
			ltlSpec.setBody(booleanTerm);
			spec = ltlSpec;
		}

		Asm asm = originalParseResult.getAsmCollection().getMain();
		asm.getBodySection().getProperty().add(spec);
		asm.setName(TEMP_ASM_NAME);

		StringWriter stringWriter = new StringWriter();
		try (PrintWriter printWriter = new PrintWriter(stringWriter)) {
			AsmPrinter printer = new AsmPrinter(printWriter);
			printer.visit(asm);
		}

		Path tempFile = null;
		try {
			String parent = new File(asmPath).getParent();
			tempFile = Path.of(parent, TEMP_ASM_NAME + AsmetaParserUtility.ASM_EXTENSION);
			Files.deleteIfExists(tempFile);
			Files.createFile(tempFile);
			Files.writeString(tempFile, stringWriter.toString());
			return parseAsm(tempFile.toString());
		} catch (IOException e) {
			throw new RuntimeException("Could not write the temporary ASMETA file", e);
		} finally {
			if (tempFile != null) {
				try {
					Files.deleteIfExists(tempFile);
				} catch (IOException e) {
					throw new RuntimeException("Could not delete the temporary ASMETA file", e);
				}
			}
		}
	}

	private ParsingResult parseAsm(String asmPath) {
		try {
			AsmCollection asm = ASMParser.setUpReadAsm(new File(asmPath));
			return new ParsingResult(true, null, asm);
		} catch (Throwable e) {
			return new ParsingResult(false, e.getMessage(), null);
		}
	}

	private static final class ParsingResult {
		private final boolean valid;
		private final String errorMessage;
		private final AsmCollection asmCollection;

		private ParsingResult(boolean valid, String errorMessage, AsmCollection asmCollection) {
			this.valid = valid;
			this.errorMessage = errorMessage;
			this.asmCollection = asmCollection;
		}

		private boolean isValid() {
			return valid;
		}

		private String getErrorMessage() {
			return errorMessage;
		}

		private AsmCollection getAsmCollection() {
			return asmCollection;
		}
	}
}
