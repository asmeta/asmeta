package org.asmeta.asm2java.application;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.asmeta.asm2java.config.Mode;
import org.asmeta.asm2java.config.TranslatorOptions;
import org.asmeta.asm2java.config.TranslatorOptionsImpl;
import org.asmeta.parser.ASMParser;

import asmeta.AsmCollection;

/**
 * The {@code TranslatorImpl} class implements the {@link Translator} interface.
 */
public class TranslatorImpl implements Translator {
	
	/** Logger */
	private static final Logger logger = Logger.getLogger(TranslatorImpl.class);

	/** Path to the ASM specification file. */
    private String asmspec;

    /** Translation options */
    private final TranslatorOptions translatorOptions;

    /** File manager instance for handling file operations. */
    private final FileManager fileManager;

    /**
     * Constructs a {@code TranslatorImpl} with the specified translator options.
     *
     * @param translatorOptions the options used to configure the translation process.
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
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setOptions(String propertyName, String propertyValue) {
		this.translatorOptions.setValue(propertyName, Boolean.parseBoolean(propertyValue));
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setInput(String value) {
		this.asmspec = value;
	}
	
	/**
	 * {@inheritDoc}
	 * @throws IOException 
	 */
	@Override
	public void setOutput(String value) throws IOException {
		fileManager.setOutputDir(value);
		if (!translatorOptions.getExport()) {
			logger.info("Warning: you selected an output folder but the export option is disabled!");
			logger.info("Warning: to enable the export option type -Dexport=true");
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void clean() {
		fileManager.cleanInputDir();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setMode(String value) {
		Mode mode = Mode.CUSTOM_MODE;
		try {
			mode = Mode.fromValue(value);
		}
		catch(IllegalArgumentException e) {
			logger.error("Failed to parse the mode value: " + e.getMessage());
			logger.error("Using the custom mode as default.");
		}
		logger.info("Translator mode setted to: " + mode.getValue());
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
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean generate() throws AsmParsingException, IOException {
		
		boolean result = true;
		File asmFile = fileManager.retrieveInput(this.asmspec);
		String asmFileName = asmFile.getName();
		String asmName = asmFileName.substring(0, asmFileName.lastIndexOf("."));
		
		// Parse the specification using the Asmeta parser
		AsmCollection model = null;
		try {
			model = ASMParser.setUpReadAsm(asmFile);
		}
		catch (Exception e){
			logger.error("Asm parsing failed: " + e.getMessage());
			throw new AsmParsingException("Error while parsing ASM file: " + asmFile.getName(), e);
		}
	
		if (translatorOptions.getCompiler() && !translatorOptions.getTestGen()) {
			result &= this.compileAndTranslate(asmName, model);
		}
	
		if (result && translatorOptions.getTranslator()) {
			result &= this.translate(asmName, model, Mode.TRANSLATOR_MODE);
		}
	
		if (result && translatorOptions.getExecutable()) {
			result &= this.translate(asmName, model, Mode.GENERATE_EXE_MODE);
		}
		
		if (result && translatorOptions.getWindow()) {
			result &= this.translate(asmName, model, Mode.GENERATE_WIN_MODE);
		}
		
		if (result && translatorOptions.getTestGen()) {
			result &= this.testGen(asmName, model);
		}

		return result;
	}
	
    /**
     * Generates the Java translation.
     * 
     * @param asmName the name of the ASM specification.
     * @param model the parsed ASM model.
     * @param mode the translation mode.
     * @return the generated Java file.
     * @throws IOException if an I/O error occurs during file generation.
     */
	private File generateTranslation(String asmName, AsmCollection model, Mode mode) throws IOException {
		 return fileManager.generateFile(asmName, model, translatorOptions, mode);
	}

    /**
     * Translates the ASM model into a Java file and exports it if enabled
     * (handling possible exceptions).
     * 
     * @param asmName the name of the ASM specification.
     * @param model the parsed ASM model.
     * @param mode the translation mode (e.g., TRANSLATOR, GENERATE_EXE).
     * @return {@code true} if the translation was successful, otherwise {@code false}.
     */
	private boolean translate(String asmName, AsmCollection model, Mode mode) {
		try {
			File javaFile = generateTranslation(asmName, model, mode);
			exportJavaFile(javaFile);
		} catch (IOException e) {
			logger.error(mode.getValue() + " operation completed with errors: " + e.getMessage());
			return false;
		}
		return true;
	}
	
    /**
     * Translate and compiles the ASM model to a Java file and exports it if enabled.
     * 
     * @param asmName the name of the ASM specification.
     * @param model the parsed ASM model.
     * @return {@code true} if the operation was successful, otherwise {@code false}.
     * @throws IOException if an I/O error occurs during file generation.
     */
	private boolean compileAndTranslate(String asmName, AsmCollection model) throws IOException {
		File javaFile = null;
		try {
			javaFile = generateTranslation(asmName, model, Mode.COMPILER_MODE);
		} catch (IOException e) {
			logger.error("Failed to generate the Java translation: " + e.getMessage());
			return false;
		}
		boolean result = fileManager.compileFile(asmName);
		if (result && javaFile != null) {
			exportJavaFile(javaFile);
		} else {
			logger.error("Failed to compile.");
			return false;
		}
		return true;
	}
	
    /**
     * Generates the test java file (test-specific translation) and the _ATG file for the ASM model.
     * 
     * @param asmName the name of the ASM specification.
     * @param model the parsed ASM model.
     * @return {@code true} if the test generation was successful, otherwise {@code false}.
     */
	private boolean testGen(String asmName, AsmCollection model) {
		try {				
			// translate
			File testGenJavaFile = generateTranslation(asmName, model, Mode.TRANSLATOR_TEST_MODE);			
			File atgJavaFile = generateTranslation(asmName, model, Mode.TEST_GEN_MODE);
			// export
			File testGenJavaFileExported = exportJavaFile(testGenJavaFile);
			File atgJavaFileExported = exportJavaFile(atgJavaFile);
			if(translatorOptions.getExport()) {
				// compile
				List<File> files = List.of(testGenJavaFileExported,atgJavaFileExported);
				boolean result = fileManager.compileExportedFiles(files);
				if(!result) {
					logger.error("Failed to compile the files: " + files.stream().map(File::getName).collect(Collectors.joining(", ")));
					return false;
				}
				logger.info("Compiled with success the files: " + files.stream().map(File::getName).collect(Collectors.joining(", ")));
			}
		} catch (IOException e) {
			logger.error("TestGen operation completed with errors: " + e.getMessage());
			return false;
		}
		return true;
	}
	
    /**
     * Exports the given Java file to the output directory if the export option is enabled.
     * 
     * @param javaFile the Java file to export.
     */
	private File exportJavaFile(File javaFile) {
		if(translatorOptions.getExport()) {
			return fileManager.exportFile(javaFile);
		}
		return null;
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
	public void setCompilerVersion(String javaVersion) {
		fileManager.setCompilerVersion(javaVersion);
	}


}
