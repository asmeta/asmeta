package asmeta.evotest.junit2avalla.avallascenario;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import asmeta.evotest.junit2avalla.model.Scenario;
import asmeta.evotest.junit2avalla.model.ScenarioFile;
import asmeta.evotest.junit2avalla.model.terms.AvallaCheckTerm;
import asmeta.evotest.junit2avalla.model.terms.AvallaHeaderTerm;
import asmeta.evotest.junit2avalla.model.terms.AvallaLoadTerm;
import asmeta.evotest.junit2avalla.model.terms.AvallaSetTerm;
import asmeta.evotest.junit2avalla.model.terms.AvallaStepTerm;
import asmeta.evotest.junit2avalla.model.terms.AvallaTerm;

/**
 * The {@code ScenarioWriter} class defines the operation for writing a
 * {@link Scenario} object into a {@link ScenarioFile}.
 */
class ScenarioWriter {

	/** Constants */
	private static final char EQ = '=';
	private static final char SEMI = ';';
	private static final char WS = ' ';
	private static final String LET = ":=";
	private static final String ASM_EXTENSION = ".asm";
	private static final String SCENARIO = "scenario";
	private static final String LOAD = "load";
	private static final String SET = "set";
	private static final String STEP = "step";
	private static final String CHECK = "check";

	/** Logger */
	private final Logger log = LogManager.getLogger(ScenarioWriter.class);

	/** String Builder */
	private StringBuilder stringBuilder;

	/**
	 * No args constructor.
	 */
	ScenarioWriter() {
		// Empty constructor
	}

	/**
	 * Converts a given {@link Scenario} object into a {@link ScenarioFile}.
	 *
	 * @param scenario the {@link Scenario} object to be written into a file.
	 * @return a {@link ScenarioFile} representing the written scenario.
	 */
	ScenarioFile write(Scenario scenario) {
		log.debug("Writing the scenario...");
		this.stringBuilder = new StringBuilder();
		ScenarioFile scenarioFile = new ScenarioFile();

		for (AvallaTerm avallaTerm : scenario.getScenarioList()) {
			log.debug("Writing the term: {}", avallaTerm.getClass());
			if (avallaTerm instanceof AvallaHeaderTerm avallaHeaderTerm) {
				writeHeader(avallaHeaderTerm);
				scenarioFile.setName(avallaHeaderTerm.getScenarioName());
			} else if (avallaTerm instanceof AvallaLoadTerm avallaLoadTerm) {
				writeLoad(avallaLoadTerm);
			} else if (avallaTerm instanceof AvallaSetTerm avallaSetTerm) {
				writeSet(avallaSetTerm);
			} else if (avallaTerm instanceof AvallaStepTerm) {
				writeStep();
			} else if (avallaTerm instanceof AvallaCheckTerm avallaCheckTerm) {
				writeCheck(avallaCheckTerm);
			}
		}

		scenarioFile.setText(stringBuilder.toString());
		log.debug("Scenario file written successfully.");
		log.debug("Avalla scenario: {}", scenarioFile.getText());
		return scenarioFile;
	}

	/**
	 * Writes the header term to the {@link StringBuilder} with the scenario name.
	 *
	 * @param headerTerm the {@link AvallaHeaderTerm} containing the scenario name.
	 */
	private void writeHeader(AvallaHeaderTerm headerTerm) {
		this.stringBuilder.append(SCENARIO).append(WS).append(headerTerm.getScenarioName())
				.append(System.lineSeparator()).append(System.lineSeparator());
	}

	/**
	 * Writes the load term to the {@link StringBuilder} with the name of the load
	 * file.
	 *
	 * @param avallaLoadTerm the {@link AvallaLoadTerm} containing the load
	 *                       information.
	 */
	private void writeLoad(AvallaLoadTerm avallaLoadTerm) {
		this.stringBuilder.append(LOAD).append(WS).append(avallaLoadTerm.getAsmName()).append(ASM_EXTENSION)
				.append(System.lineSeparator()).append(System.lineSeparator());
	}

	/**
	 * Writes the set term to the {@link StringBuilder} with the variable name and
	 * value.
	 *
	 * @param avallaSetTerm the {@link AvallaSetTerm} containing the name and value
	 *                      of the variable.
	 */
	private void writeSet(AvallaSetTerm avallaSetTerm) {
		this.stringBuilder.append(SET).append(WS).append(avallaSetTerm.getName()).append(WS).append(LET).append(WS)
				.append(avallaSetTerm.getValue()).append(SEMI).append(System.lineSeparator());
	}

	/**
	 * Writes the step term to the {@link StringBuilder}.
	 */
	private void writeStep() {
		this.stringBuilder.append(System.lineSeparator()).append(STEP).append(System.lineSeparator())
				.append(System.lineSeparator());
	}

	/**
	 * Writes the check term to the {@link StringBuilder} with the left and right
	 * terms for comparison.
	 *
	 * @param avallaCheckTerm the {@link AvallaCheckTerm} containing the terms to
	 *                        compare.
	 */
	private void writeCheck(AvallaCheckTerm avallaCheckTerm) {
		this.stringBuilder.append(CHECK).append(WS).append(avallaCheckTerm.getLeftTerm()).append(WS).append(EQ)
				.append(WS).append(avallaCheckTerm.getRightTerm()).append(SEMI).append(System.lineSeparator());
	}

}
