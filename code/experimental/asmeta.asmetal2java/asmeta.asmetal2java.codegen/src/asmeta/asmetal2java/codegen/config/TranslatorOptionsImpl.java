package asmeta.asmetal2java.codegen.config;

import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/** 
 * contains the translation options used by the generators to decide the behaviors of the generators
 */
public class TranslatorOptionsImpl implements TranslatorOptions {

	/* Constants */
	private static final String FORMATTER_OPTION = "formatter";
	private static final String SHUFFLE_RANDOM_OPTION = "shuffleRandom";
	private static final String OPTIMIZE_SEQ_MACRO_RULE_OPTION = "optimizeSeqMacroRule";
	public static final String COVER_OUTPUTS_OPTION = "coverOutputs";
	public static final String COVER_RULES_OPTION = "coverRules";
	private static final String EXPORT_OPTION = "export";
	public static final String COPY_ASM_OPTION = "copyAsm";
	
	/** Logger */
	private final Logger logger = LogManager.getLogger(TranslatorOptionsImpl.class);

	/** Indicates whether the generated code should be formatted. */
	private boolean formatter;

	/** Indicates whether a random shuffle should be applied. */
	private boolean shuffleRandom;

	/** Indicates whether to optimize the sequence macro rule (remove unused seq rules). */
	private boolean optimizeSeqMacroRule;
		
	/** Indicates to translate the generated Java class. */
	private boolean translator;
	
	/** Indicates to compile the generated java class. */
	private boolean compiler;
	
	/** Indicates to generate an executable of the generated Java class. */
	private boolean generateExe;
	
	/** Indicates to generate an executable of the generated Java class with a Graphical User Interface (GUI). */
	private boolean generateWin;
	
	/** Indicates to generate a class designed for generating tests with Evosuite for the generated Java class. */
	private boolean testGen;
	
	/** Indicates to add methods to cover the outputs in the testgen class. */
	private boolean coverOutputs;
	
	/** Indicates to add methods to cover the rules in the testgen class. */
	private boolean coverRules;
	
	/** Indicates to export the generated Java files into the output folder. */
	private boolean export;
	
	/** Indicates to copy the asm specification files to another folder to be processed. */
	private boolean copyAsm;
	
	/**
	 * A map that associates property names with actions that modify the corresponding boolean fields.
	 *
	 * This map is used to replace a switch-case block, simplifying the logic for setting 
	 * property values by dynamically invoking the corresponding setter action.
	 */
	private HashMap<String,Consumer<Boolean>> propertyMapper;

	
	/**
	 * Constructs a {@code TranslatorOptions} instance with the default settings: <p>
	 * formatter, shuffleRandom, optimizeSeqRule, translator, coverRules, export = {@code true}.
	 * All the others = {@code false}.
	 */
	public TranslatorOptionsImpl(){
		this.formatter = true;
		this.shuffleRandom = true;
		this.optimizeSeqMacroRule = true;
		this.translator = true;
		this.generateExe = false;
		this.generateWin = false;
		this.compiler = false;
		this.testGen = false;
		this.coverOutputs = false;
		this.coverRules = true;
		this.export = true;
		this.copyAsm = false;
		mapperSetup();
	}

	/**
	 * Constructs a {@code TranslatorOptions} instance with the specified option values
	 * (Considering only the specific properties related to the translation process).
	 *
	 * @param formatter           whether the generated code should be formatted.
	 * @param shuffleRandom       whether a random shuffle should be applied.
	 * @param optimizeSeqRule     whether to optimize the sequence macro rule.
	 */
	public TranslatorOptionsImpl(boolean formatter,
			boolean shuffleRandom, 
			boolean optimizeSeqRule) {
		this();
		this.formatter = formatter;
		this.shuffleRandom = shuffleRandom;
		this.optimizeSeqMacroRule = optimizeSeqRule;
	}
	
    /**
     * Initializes the mapping between property names and their corresponding actions.
     */
	private void mapperSetup() {
		this.propertyMapper = new HashMap<>();
		this.propertyMapper.put(FORMATTER_OPTION, value -> this.formatter = value);
		this.propertyMapper.put(SHUFFLE_RANDOM_OPTION, value -> this.shuffleRandom = value);
		this.propertyMapper.put(OPTIMIZE_SEQ_MACRO_RULE_OPTION, value -> this.optimizeSeqMacroRule = value);
		this.propertyMapper.put(ModeConstantsConfig.TRANSLATOR, value -> this.translator = value);
		this.propertyMapper.put(ModeConstantsConfig.COMPILER, value -> this.compiler = value);
		this.propertyMapper.put(ModeConstantsConfig.GENERATE_EXE, value -> this.generateExe = value);
		this.propertyMapper.put(ModeConstantsConfig.GENERATE_WIN, value -> this.generateWin = value);
		this.propertyMapper.put(ModeConstantsConfig.TEST_GEN, value -> this.testGen = value);
		this.propertyMapper.put(COVER_OUTPUTS_OPTION, value -> this.coverOutputs = value);
		this.propertyMapper.put(COVER_RULES_OPTION, value -> this.coverRules = value);
		this.propertyMapper.put(EXPORT_OPTION, value -> this.export = value);
		this.propertyMapper.put(COPY_ASM_OPTION, value -> this.copyAsm = value);
	}

	@Override
	public void setValue(String propertyName, boolean propertyValue) {
		
		Consumer<Boolean> action = propertyMapper.get(propertyName);
       
		if (action != null) {
            action.accept(propertyValue);
            logger.info("Setting the translator option: {} to: {}.", propertyName, propertyValue);
        } else {
            logger.error("Failed to set the value: {}.", propertyName);
            throw new IllegalArgumentException("Unexpected value: " + propertyName);
        }
        
	}
	
	@Override
	public List<String> getPropertyNames() {
		return List.of(
				FORMATTER_OPTION, 
				SHUFFLE_RANDOM_OPTION, 
				OPTIMIZE_SEQ_MACRO_RULE_OPTION, 
				ModeConstantsConfig.TRANSLATOR, 
				ModeConstantsConfig.COMPILER, 
				ModeConstantsConfig.GENERATE_EXE,
				ModeConstantsConfig.GENERATE_WIN,
				ModeConstantsConfig.TEST_GEN,
				COVER_OUTPUTS_OPTION,
				COVER_RULES_OPTION,
				EXPORT_OPTION,
				COPY_ASM_OPTION
				);
	}
	
	@Override
	public String getDescription() {
		return "use value for given translator property (the default value is in brackets):\n"
				+ " -D" + FORMATTER_OPTION + " (true)/false : to format the generated code.\n"
				+ " -D" + SHUFFLE_RANDOM_OPTION + " = (true)/false : use random shuffle.\n"
				+ " -D" + OPTIMIZE_SEQ_MACRO_RULE_OPTION + " = (true)/false : remove unused seq rules.\n"
				+ " -D" + ModeConstantsConfig.TRANSLATOR + " = (true)/false : translate the asm file to a java class.\n"
				+ " -D" + ModeConstantsConfig.COMPILER + " = true/(false) : translate and compile the generated java class.\n"
				+ " -D" + ModeConstantsConfig.GENERATE_EXE + " = true/(false) : generate a java class for execution.\n"
				+ " -D" + ModeConstantsConfig.GENERATE_WIN + " = true/(false) : generate an executable java class with a GUI.\n"
				+ " -D" + ModeConstantsConfig.TEST_GEN + " = true/(false) : generate a specific java class designed for test generation with Evosuite.\n"
				+ " -D" + COVER_OUTPUTS_OPTION + " = true/(false) : cover the outputs in the testGen class.\n"
				+ " -D" + COVER_RULES_OPTION + " = (true)/false : cover the rules in the testGen class.\n"
				+ " -D" + EXPORT_OPTION + " = (true)/false : export the generated file into the output folder.\n"
				+ " -D" + COPY_ASM_OPTION + " = true/(false) : copy the amseta spec file to another folder for processing.\n"
				+ " Note: Please use " + ModeConstantsConfig.TRANSLATOR + ", " + ModeConstantsConfig.COMPILER + ", " + ModeConstantsConfig.GENERATE_EXE + ", " + ModeConstantsConfig.GENERATE_WIN + " and " + ModeConstantsConfig.TEST_GEN 
				+ " options only if you have selected the -mode custom option.";
	}

	@Override
	public boolean getShuffleRandom() {
		return shuffleRandom;
	}

	@Override
	public boolean getFormatter() {
		return formatter;
	}

	@Override
	public boolean getOptimizeSeqMacroRule() {
		return optimizeSeqMacroRule;
	}
	
	@Override
	public boolean getTranslator() {
		return translator;
	}
	
	@Override
	public boolean getCompiler() {
		return compiler;
	}
	
	@Override
	public boolean getExecutable() {
		return generateExe;
	}
	
	@Override
	public boolean getWindow() {
		return generateWin;
	}
	
	@Override
	public boolean getTestGen() {
		return testGen;
	}
	
	@Override
	public boolean getCoverOutputs() {
		return coverOutputs;
	}
	
	@Override
	public boolean getCoverRules() {
		return coverRules;
	}
	
	@Override
	public boolean getExport() {
		return export;
	}

	@Override
	public boolean getCopyAsm() {
		return copyAsm;
	}

}
