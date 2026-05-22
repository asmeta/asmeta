package asmeta.ai.propgen;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;

import org.asmeta.parser.ASMParser;
import org.asmeta.parser.AsmetaParserUtility;
import org.asmeta.parser.util.AsmPrinter;

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
 * Validates temporal logic properties against an ASMETA model.
 */
public class PropertyValidator {

	private static final String TEMP_ASM_NAME = "TEMP_ASM_FOR_PROPERTY_VALIDATION";

	/**
	 * Represents the result of parsing an ASMETA specification.
	 */
	public static class ParsingResult {
		private final boolean valid;
		private final String errorMessage;
		private final AsmCollection asmCollection;

		/**
		 * Creates a new parsing result.
		 *
		 * @param valid         whether the operation completed successfully
		 * @param errorMessage  error message in case of failure, {@code null} otherwise
		 * @param asmCollection parsed ASM collection, {@code null} in case of failure
		 */
		public ParsingResult(boolean valid, String errorMessage, AsmCollection asmCollection) {
			this.valid = valid;
			this.errorMessage = errorMessage;
			this.asmCollection = asmCollection;
		}

		/**
		 * Returns whether the parsing was successful.
		 *
		 * @return {@code true} if the parsing was successful, {@code false} otherwise
		 */
		public boolean isValid() {
			return valid;
		}

		/**
		 * Returns the error message associated with the failed parsing.
		 *
		 * @return the error message, or {@code null} if the parsing was successful
		 */
		public String getErrorMessage() {
			return errorMessage;
		}

		/**
		 * Returns the parsed ASM collection.
		 *
		 * @return the parsed ASM collection, or {@code null} if the parsing failed
		 */
		public AsmCollection getAsmCollection() {
			return asmCollection;
		}
	}

	/**
	 * Parses an ASMETA model from the given file path.
	 *
	 * @param asmPath path to the ASMETA model file
	 * @return the result of the parsing operation, containing either the parsed ASM
	 *         collection or an error message
	 */
	private static ParsingResult parseAsm(String asmPath) {
		try {
			AsmCollection asm = ASMParser.setUpReadAsm(new File(asmPath));
			return new ParsingResult(true, null, asm);
		} catch (Throwable e) {
			return new ParsingResult(false, e.getMessage(), null);
		}
	}

	/**
	 * Validates a temporal property by adding it to an ASMETA model, writing the
	 * updated model to a temporary file, and parsing it again.
	 *
	 * @param asmPath  path to the ASMETA model file
	 * @param property temporal property to validate
	 * @param type     type of the property to validate
	 * @return the result of the validation operation, containing either the parsed
	 *         ASM collection with the property added or an error message
	 * @throws RuntimeException if the original ASMETA model cannot be parsed, if
	 *                          the temporary ASMETA file cannot be written, or if
	 *                          the updated model cannot be parsed
	 */
	public static ParsingResult validateProperty(String asmPath, String property, PropertyType type) {
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

		String newAsmContent = stringWriter.toString();
		Path tempFile = null;

		try {
			String parent = new File(asmPath).getParent();
			tempFile = Path.of(parent, TEMP_ASM_NAME + AsmetaParserUtility.ASM_EXTENSION);
			Files.deleteIfExists(tempFile);
			Files.createFile(tempFile);
			Files.writeString(tempFile, newAsmContent);

			ParsingResult validationResult = parseAsm(tempFile.toString());

			return validationResult;
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
}
