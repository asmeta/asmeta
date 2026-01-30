package asmeta.asmetal2java.codegen.application;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.asmeta.parser.ASMParser;
import org.eclipse.xtext.util.Files;

import asmeta.AsmCollection;
import asmeta.asmetal2java.codegen.config.Mode;
import asmeta.asmetal2java.codegen.config.TranslatorOptions;
import asmeta.asmetal2java.codegen.config.TranslatorOptionsImpl;
import asmeta.asmetal2java.codegen.evosuite.DomainNotSupportedException;

/**
 * The {@code TranslatorImpl} class implements the {@link Translator} interface.
 */
public class TranslatorImpl implements Translator {

	/** Logger */
	private final Logger logger = LogManager.getLogger(TranslatorImpl.class);

	/* Constants */
	private static final String ASM_EXTENSION = ASMParser.ASM_EXTENSION;

	/** Path to the ASM specification file. */
    private String asmspec;

    /** Translation options */
    private final TranslatorOptions translatorOptions;

    /** File manager instance for handling file operations. */
    private final FileManager fileManager;

    /**
     * Constructs a {@code TranslatorImpl} with the specified translator options.
     *
     * @param translatorOptionsImpl the options used to configure the translation process.
     */
	public TranslatorImpl(TranslatorOptionsImpl translatorOptionsImpl) {
		this.translatorOptions = translatorOptionsImpl;
		this.fileManager = new FileManager();
	}

    /**
     * Constructs a {@code TranslatorImpl} with default {@link TranslatorOptions}.
     */
	public TranslatorImpl() {
		this(new TranslatorOptionsImpl());
	}

	@Override
	public void setOptions(String propertyName, String propertyValue) {
		this.translatorOptions.setValue(propertyName, Boolean.parseBoolean(propertyValue));
	}

	@Override
	public void setWorkingDir(String workingDirPath) {
		logger.info("Setting a custom working directory: {}.", workingDirPath);
		fileManager.setInputFolder(workingDirPath);
	}

	@Override
	public void setInput(String value) throws AsmParsingException{
		logger.info("Setting the input file path: {}.", value);
		if(!value.endsWith(ASM_EXTENSION)) {
			throw new AsmParsingException("The Asmeta specification must have the " + ASM_EXTENSION + " extension.");
		}
		this.asmspec = value;
	}

	@Override
	public void setOutput(String value) throws SetupException {
		try {
			fileManager.setOutputDir(value);
		} catch (IOException e) {
			logger.error("Failed to set the output directory.");
			throw new SetupException("Unable to set the output directory", e);
		}
		if (!translatorOptions.getExport()) {
			logger.warn("Warning: you selected an output folder but the export option is disabled!");
			logger.warn("Warning: to enable the export option type -Dexport=true");
		}
	}

	@Override
	public void clean() {
		fileManager.cleanInputDir();
	}

	@Override
	public void setMode(String value) {
		Mode mode = Mode.CUSTOM_MODE;
		try {
			mode = Mode.fromValue(value);
		}
		catch(IllegalArgumentException e) {
			logger.error("Failed to parse the mode value: {}.", e.getMessage());
			logger.error("Using the custom mode as default.");
		}
		logger.info("Translator mode set to: {}.", mode.getValue());
		switch (mode) {
		case TRANSLATOR_MODE:
			translatorOptions.setValue(Mode.TRANSLATOR_MODE.getValue(), true);
			translatorOptions.setValue(Mode.TEST_GEN_MODE.getValue(), false);
			break;
		case GENERATE_EXE_MODE:
			translatorOptions.setValue(Mode.TRANSLATOR_MODE.getValue(), true);
			translatorOptions.setValue(Mode.GENERATE_EXE_MODE.getValue(), true);
			translatorOptions.setValue(Mode.TEST_GEN_MODE.getValue(), false);
			break;
		case GENERATE_WIN_MODE:
			translatorOptions.setValue(Mode.TRANSLATOR_MODE.getValue(), true);
			translatorOptions.setValue(Mode.GENERATE_WIN_MODE.getValue(), true);
			translatorOptions.setValue(Mode.TEST_GEN_MODE.getValue(), false);
			break;
		case TEST_GEN_MODE:
			translatorOptions.setValue(Mode.TRANSLATOR_MODE.getValue(), false);
			translatorOptions.setValue(Mode.GENERATE_EXE_MODE.getValue(), false);
			translatorOptions.setValue(Mode.GENERATE_WIN_MODE.getValue(), false);
			translatorOptions.setValue(Mode.TEST_GEN_MODE.getValue(), true);
			break;
		default:
			// CUSTOM mode, don't set any default options, leave the choice to the user.
			break;
		}
	}

	@Override
	public void generate() throws AsmParsingException, SetupException, TranslationException, DomainNotSupportedException {

		File asmFile;
		try {
			asmFile = fileManager.retrieveInput(this.asmspec, this.translatorOptions.getCopyAsm());
		} catch (IOException e) {
			logger.error("Failed to retrieve the input file");
			throw new SetupException("Unable to retrieve the input file", e);
		}
		String asmFileName = asmFile.getName();
		String asmName = asmFileName.substring(0, asmFileName.lastIndexOf("."));

		// Parse the specification using the Asmeta parser
		logger.info("Parsing the asmeta specification: {}", asmFile.getName());
		AsmCollection model = null;
		try {
			model = ASMParser.setUpReadAsm(asmFile);
		}
		catch (Exception e){
			logger.error("Failed to parse the asmeta specification: {}.", e.getMessage());
			throw new AsmParsingException("Error while parsing the asmeta specification: " + asmFile.getName(), e);
		}

		if (translatorOptions.getCompiler() && !translatorOptions.getTestGen()) {
			this.translateAndCompile(asmName, model);
		}

		if (translatorOptions.getTranslator()) {
			this.translate(asmName, model, Mode.TRANSLATOR_MODE);
		}

		if (translatorOptions.getExecutable()) {
			this.translate(asmName, model, Mode.GENERATE_EXE_MODE);
		}

		if (translatorOptions.getWindow()) {
			this.translate(asmName, model, Mode.GENERATE_WIN_MODE);
		}

		if (translatorOptions.getTestGen()) {
			this.testGen(asmName, model);
		}

	}

	@Override
	public List<String> getOptionNames() {
		return translatorOptions.getPropertyNames();
	}

	@Override
	public String getOptionsDescription() {
		return translatorOptions.getDescription();
	}

	@Override
	public String getModeDescription() {
		return Mode.getDescription();
	}

	@Override
	public void setCompilerVersion(String javaVersion) throws SetupException {
		fileManager.setCompilerVersion(javaVersion);
	}

    /**
     * Generates the Java translation.
     *
     * @param asmName the name of the ASM specification.
     * @param model the parsed ASM model.
     * @param mode the translation mode.
     * @return the generated Java file.
     * @throws DomainNotSupportedException if a domain is not supported by the testGen operation.
     */
	private File generateTranslation(String asmName, AsmCollection model, Mode mode) throws TranslationException, DomainNotSupportedException {
		File generatedFile;
		try {
			generatedFile = fileManager.generateFile(asmName, model, translatorOptions, mode);
		} catch (DomainNotSupportedException e) {
			logger.error("{} operation completed with errors: {}.", mode.getValue(), e.getMessage());
			throw new DomainNotSupportedException(e.getMessage());
		} catch (Exception e) {
			logger.error("{} operation completed with errors: {}.", mode.getValue(), e.getMessage());
			throw new TranslationException("Unable to translate the asmeta specification: " + asmName, e);
		}
		if(logger.isDebugEnabled()) {
			logger.debug("File generated:\n{}", Files.readFileIntoString(generatedFile.toString()));
		}
		return generatedFile;
	}

    /**
     * Translates the ASM model into a Java file and exports it if enabled
     * (handling possible exceptions).
     *
     * @param asmName the name of the ASM specification.
     * @param model the parsed ASM model.
     * @param mode the translation mode (e.g., TRANSLATOR, GENERATE_EXE).
     * @throws TranslationException if an error occurs during the translation process.
     */
	private void translate(String asmName, AsmCollection model, Mode mode) throws TranslationException {
		File javaFile = generateTranslation(asmName, model, mode);
		exportJavaFile(javaFile);
	}

    /**
     * Translate and compiles the ASM model to a Java file and exports it if enabled.
     *
     * @param asmName the name of the ASM specification.
     * @param model the parsed ASM model.
     * @throws TranslationException if an error occurs during the translation process.
     */
	private void translateAndCompile(String asmName, AsmCollection model) throws TranslationException {
		// translate
		File javaFile = null;
		javaFile = generateTranslation(asmName, model, Mode.COMPILER_MODE);
		// compile
		try {
			fileManager.compileFile(javaFile);
		} catch (Exception e) {
			logger.error("Failed to compile the translated asmeta specification.");
			throw new TranslationException("Unable to compile the translated asmeta specification: " + asmName, e) ;
		}
		// export (if the export option is enabled)
		exportJavaFile(javaFile);
	}

    /**
     * Generates the test java file (test-specific translation) and the _ATG file for the ASM model.
     *
     * @param asmName the name of the ASM specification.
     * @param model the parsed ASM model.
     * @throws TranslationException if an error occurs during the translation process.
     */
	private void testGen(String asmName, AsmCollection model) throws TranslationException, DomainNotSupportedException {
		// translate
		File testGenJavaFile = generateTranslation(asmName, model, Mode.TRANSLATOR_TEST_MODE);
		File atgJavaFile = generateTranslation(asmName, model, Mode.TEST_GEN_MODE);
		// export
		File testGenJavaFileExported = exportJavaFile(testGenJavaFile);
		File atgJavaFileExported = exportJavaFile(atgJavaFile);
		if(translatorOptions.getExport()) {
			// compile
			List<File> files = List.of(testGenJavaFileExported, atgJavaFileExported);
			try {
				fileManager.compileExportedFiles(files);
			} catch (Exception e) {
				logger.error("Failed to compile the translated asmeta specification.");
				throw new TranslationException("Unable to compile the translated asmeta specification: " + asmName, e) ;
			}
			logger.info("Compiled with success the files: {}.", files);
		}
	}

    /**
     * Exports the given Java file to the output directory if the export option is enabled.
     *
     * @param javaFile the Java file to export.
     * @throws TranslationException if an error occurs during the translation process.
     */
	private File exportJavaFile(File javaFile) throws TranslationException {
		if(translatorOptions.getExport()) {
			try {
				return fileManager.exportFile(javaFile);
			} catch (Exception e) {
				logger.error("Failed to export the file {}.", javaFile);
				throw new TranslationException("Unable to export the java file: " + javaFile, e) ;
			}
		}
		return null;
	}

}
