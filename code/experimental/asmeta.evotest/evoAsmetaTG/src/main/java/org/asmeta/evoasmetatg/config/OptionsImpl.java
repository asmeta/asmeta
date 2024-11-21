package org.asmeta.evoasmetatg.config;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.asmeta.asm2java.config.TranslatorOptionsImpl;
import org.asmeta.asm2java.config.ModeConstantsConfig;

/**
 * The {@code OptionsImpl} class provides an implementation of the {@link Options} interface.
 */
public class OptionsImpl implements Options {

	/* Constants */
	private static final String OPTIONS_ID = "-D";

	/** Logger */
	private final Logger logger = LogManager.getLogger(OptionsImpl.class);

	/** Indicates to add methods to cover the rules in the testgen class. */
	private boolean coverRules;

	/** Indicates to add methods to cover the outputs in the testgen class. */
	private boolean coverOutput;

	/** Indicates to compile the generated java class. */
	private boolean compiler;

	/** A map that associates property names with actions that modify the corresponding boolean fields. */
	private Map<String, Consumer<Boolean>> optionMapper;

	/**
	 * Constructs a {@code OptionsImpl} instance with the default settings: <p>
	 * coverRules, compiler = {@code true}. <p>
	 * coverOutput = {@code false}.
	 */
	public OptionsImpl() {
		coverRules = true;
		coverOutput = false;
		compiler = true;
		mapperSetup();
	}

    /**
     * Initializes the mapping between options names and their corresponding actions.
     */
	private void mapperSetup() {
		optionMapper = new HashMap<>();
		optionMapper.put(TranslatorOptionsImpl.COVER_RULES_OPTION, value -> coverRules = value);
		optionMapper.put(TranslatorOptionsImpl.COVER_OUTPUTS_OPTION, value -> coverOutput = value);
		optionMapper.put(ModeConstantsConfig.COMPILER, value -> compiler = value);
	}

	@Override
	public void setValue(String optionName, Boolean optionValue) {
		Consumer<Boolean> action = optionMapper.get(optionName);

		if (action != null) {
			action.accept(optionValue);
			logger.info("Setting the translator option {} to {}.", optionName, optionValue );
		} else {
			logger.error("Failed to set the value: {} ", optionName);
			throw new IllegalArgumentException("Unexpected value: " + optionName);
		}
	}

	@Override
	public List<String> getOptionNames() {
		return List.of(
				TranslatorOptionsImpl.COVER_RULES_OPTION, 
				TranslatorOptionsImpl.COVER_OUTPUTS_OPTION,
				ModeConstantsConfig.COMPILER);
	}

	@Override
	public String getDescription() {
		return "use value for given translator property (the default value is in brackets):\n" 
				+ OPTIONS_ID + ModeConstantsConfig.COMPILER + " = (true)/false : translate and compile the generated java class.\n"
				+ OPTIONS_ID + TranslatorOptionsImpl.COVER_RULES_OPTION + " = (true)/false : cover the rules in the testGen class.\n" 
				+ OPTIONS_ID + TranslatorOptionsImpl.COVER_OUTPUTS_OPTION + " = true/(false) : cover the outputs in the testGen class.\n";
	}

	@Override
	public List<String> getCLIStringOptions() {
		return List.of( 
				OPTIONS_ID + ModeConstantsConfig.COMPILER, String.valueOf(compiler),
				OPTIONS_ID + TranslatorOptionsImpl.COVER_RULES_OPTION, String.valueOf(coverRules),
				OPTIONS_ID + TranslatorOptionsImpl.COVER_OUTPUTS_OPTION, String.valueOf(coverOutput)
				);
	}
	
	@Override
	public boolean isCoverRules() {
		return coverRules;
	}

	@Override
	public boolean isCoverOutput() {
		return coverOutput;
	}

	@Override
	public boolean isCompiler() {
		return compiler;
	}

}
