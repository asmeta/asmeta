package asmeta.ai.propgen;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.asmeta.parser.ASMParser;
import org.asmeta.parser.AsmetaParserUtility;

import asmeta.AsmCollection;
import asmeta.structure.ImportClause;

/**
 * Ensures that an ASM model can reference the temporal library it needs.
 */
public final class LibraryPreparer {

	private static final Logger logger = Logger.getLogger(LibraryPreparer.class);
	private static final String RESOURCE_FOLDER = "/STDL/";

	private LibraryPreparer() {
	}

	/**
	 * Adds the CTL/LTL library import and library file when the model needs them.
	 *
	 * @param asmPath  path of the ASM model to prepare
	 * @param type     temporal property type that determines the required library
	 * @param listener listener used for progress messages
	 */
	public static void prepare(String asmPath, PropertyType type, AsmetaAIOperationListener listener) {
		log(listener, "Checking temporal logic library imports...");
		String libraryName = libraryName(type);
		Path asmFile = Path.of(asmPath);
		Path asmDirectory = asmFile.toAbsolutePath().getParent();
		if (asmDirectory == null) {
			throw new RuntimeException("Could not determine parent directory for ASMETA model: " + asmPath);
		}

		AsmCollection asm = parseAsm(asmFile);
		if (hasImport(asm, libraryName)) {
			log(listener, "Temporal logic library import already present.");
			return;
		}

		addImport(asmFile, libraryName, listener);
		copyLibraryIfMissing(asmDirectory, libraryName, listener);
		parseAsm(asmFile);
	}

	private static String libraryName(PropertyType type) {
		return type == PropertyType.CTLPROP ? "CTLLibrary" : "LTLLibrary";
	}

	private static void copyLibraryIfMissing(Path asmDirectory, String libraryName, AsmetaAIOperationListener listener) {
		String fileName = libraryName + AsmetaParserUtility.ASM_EXTENSION;
		Path target = asmDirectory.resolve(fileName);
		if (Files.exists(target)) {
			log(listener, fileName + " already exists next to the ASMETA model.");
			return;
		}

		log(listener, "Copying " + fileName + " next to the ASMETA model...");
		try (InputStream input = LibraryPreparer.class.getResourceAsStream(RESOURCE_FOLDER + fileName)) {
			if (input == null) {
				throw new RuntimeException("Bundled temporal logic library not found: " + RESOURCE_FOLDER + fileName);
			}
			Files.copy(input, target);
		} catch (IOException e) {
			throw new RuntimeException("Could not copy " + fileName + " next to the ASMETA model: " + e.getMessage(),
					e);
		}
	}

	private static AsmCollection parseAsm(Path asmFile) {
		try {
			return ASMParser.setUpReadAsm(asmFile.toFile());
		} catch (Exception e) {
			throw new RuntimeException("Could not parse ASMETA model " + asmFile + ": " + e.getMessage(), e);
		}
	}

	// Import detection relies on the parsed AST, not on textual matching.
	private static boolean hasImport(AsmCollection asm, String libraryName) {
		for (ImportClause importClause : asm.getMain().getHeaderSection().getImportClause()) {
			logger.debug("Found import: " + importClause.getModuleName());
			if (importsLibrary(importClause.getModuleName(), libraryName)) {
				return true;
			}
		}
		return false;
	}

	private static boolean importsLibrary(String moduleName, String libraryName) {
		String normalized = moduleName.replace('\\', '/');
		if (normalized.endsWith(AsmetaParserUtility.ASM_EXTENSION)) {
			normalized = normalized.substring(0, normalized.length() - AsmetaParserUtility.ASM_EXTENSION.length());
		}
		int separatorIndex = normalized.lastIndexOf('/');
		String importedName = separatorIndex >= 0 ? normalized.substring(separatorIndex + 1) : normalized;
		return libraryName.equals(importedName);
	}

	// Preserve existing import grouping by inserting after the last import line.
	private static void addImport(Path asmFile, String libraryName, AsmetaAIOperationListener listener) {
		log(listener, "Adding import " + libraryName + " to " + asmFile.getFileName() + "...");
		try {
			List<String> lines = new ArrayList<>(Files.readAllLines(asmFile, StandardCharsets.UTF_8));
			int insertIndex = importInsertIndex(lines);
			lines.add(insertIndex, "import " + libraryName);
			Files.write(asmFile, lines, StandardCharsets.UTF_8);
		} catch (IOException e) {
			throw new RuntimeException("Could not add import " + libraryName + " to " + asmFile + ": "
					+ e.getMessage(), e);
		}
	}

	private static int importInsertIndex(List<String> lines) {
		int lastImportIndex = -1;
		int headerIndex = -1;
		for (int i = 0; i < lines.size(); i++) {
			String trimmed = lines.get(i).trim();
			if (isImportLine(trimmed)) {
				lastImportIndex = i;
			}
			if (headerIndex < 0 && isAsmLine(trimmed)) {
				headerIndex = i;
			}
		}
		if (lastImportIndex >= 0) {
			return lastImportIndex + 1;
		}
		if (headerIndex >= 0) {
			return headerIndex + 1;
		}
		throw new RuntimeException("Could not find Asmeta name declaration for import insertion.");
	}

	private static boolean isImportLine(String trimmedLine) {
		return trimmedLine.startsWith("import ");
	}

	private static boolean isAsmLine(String trimmedLine) {
		return trimmedLine.startsWith("asm ");
	}

	private static void log(AsmetaAIOperationListener listener, String message) {
		logger.info(message);
		listener.onProgress(message);
	}
}
