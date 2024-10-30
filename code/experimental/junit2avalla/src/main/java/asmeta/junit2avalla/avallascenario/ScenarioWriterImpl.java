package asmeta.junit2avalla.avallascenario;

import asmeta.junit2avalla.model.Scenario;
import asmeta.junit2avalla.model.ScenarioFile;
import asmeta.junit2avalla.model.terms.AvallaCheckTerm;
import asmeta.junit2avalla.model.terms.AvallaHeaderTerm;
import asmeta.junit2avalla.model.terms.AvallaLoadTerm;
import asmeta.junit2avalla.model.terms.AvallaSetTerm;
import asmeta.junit2avalla.model.terms.AvallaStepTerm;
import asmeta.junit2avalla.model.terms.AvallaTerm;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The {@code ScenarioWriterImpl} class is responsible for converting a {@link Scenario} object into
 * a textual format and returning it as a {@link ScenarioFile}. It processes each {@link AvallaTerm}
 * in the {@link Scenario} to generate the appropriate text, including headers, loads, sets, steps,
 * and checks.
 */
public class ScenarioWriterImpl implements ScenarioWriter {

  private static final Logger log = LogManager.getLogger(ScenarioWriterImpl.class);

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

  private StringBuilder stringBuilder;

  /**
   * Default constructor.
   */
  public ScenarioWriterImpl() {
  }

  /**
   * {@inheritDoc}
   *
   * <p>
   * It iterates over the terms in the scenario and generates the appropriate textual representation
   * based on the type of {@link AvallaTerm}.
   * </p>
   */
  @Override
  public ScenarioFile write(Scenario scenario) {
    log.info("Writing the scenario...");
    this.stringBuilder = new StringBuilder();
    ScenarioFile scenarioFile = new ScenarioFile();

    for (AvallaTerm avallaTerm : scenario.getScenario()) {
      log.debug("Writing the term: {}", avallaTerm.getClass());
      if (avallaTerm instanceof AvallaHeaderTerm) {
        writeHeader((AvallaHeaderTerm) avallaTerm);
        scenarioFile.setName(((AvallaHeaderTerm) avallaTerm).getScenarioName());
      } else if (avallaTerm instanceof AvallaLoadTerm) {
        writeLoad((AvallaLoadTerm) avallaTerm);
      } else if (avallaTerm instanceof AvallaSetTerm) {
        writeSet((AvallaSetTerm) avallaTerm);
      } else if (avallaTerm instanceof AvallaStepTerm) {
        writeStep();
      } else if (avallaTerm instanceof AvallaCheckTerm) {
        writeCheck((AvallaCheckTerm) avallaTerm);
      }
    }

    scenarioFile.setText(stringBuilder.toString());
    log.info("Scenario file written successfully: {}", scenarioFile.getText());
    return scenarioFile;
  }

  /**
   * Writes the header term to the {@link StringBuilder} with the scenario name.
   *
   * @param headerTerm the {@link AvallaHeaderTerm} containing the scenario name.
   */
  private void writeHeader(AvallaHeaderTerm headerTerm) {
    this.stringBuilder
        .append(SCENARIO)
        .append(WS)
        .append(headerTerm.getScenarioName())
        .append(System.lineSeparator())
        .append(System.lineSeparator());
  }

  /**
   * Writes the load term to the {@link StringBuilder} with the name of the load file.
   *
   * @param avallaLoadTerm the {@link AvallaLoadTerm} containing the load information.
   */
  private void writeLoad(AvallaLoadTerm avallaLoadTerm) {
    this.stringBuilder
        .append(LOAD)
        .append(WS)
        .append(avallaLoadTerm.getLoad())
        .append(ASM_EXTENSION)
        .append(System.lineSeparator())
        .append(System.lineSeparator());
  }

  /**
   * Writes the set term to the {@link StringBuilder} with the variable name and value.
   *
   * @param avallaSetTerm the {@link AvallaSetTerm} containing the name and value of the variable.
   */
  private void writeSet(AvallaSetTerm avallaSetTerm) {
    this.stringBuilder
        .append(SET)
        .append(WS)
        .append(avallaSetTerm.getName())
        .append(WS)
        .append(LET)
        .append(WS)
        .append(avallaSetTerm.getValue())
        .append(SEMI)
        .append(System.lineSeparator());
  }

  /**
   * Writes the step term to the {@link StringBuilder}.
   */
  private void writeStep() {
    this.stringBuilder
        .append(System.lineSeparator())
        .append(STEP)
        .append(System.lineSeparator())
        .append(System.lineSeparator());
  }

  /**
   * Writes the check term to the {@link StringBuilder} with the left and right terms for
   * comparison.
   *
   * @param avallaCheckTerm the {@link AvallaCheckTerm} containing the terms to compare.
   */
  private void writeCheck(AvallaCheckTerm avallaCheckTerm) {
    this.stringBuilder
        .append(CHECK)
        .append(WS)
        .append(avallaCheckTerm.getLeftTerm())
        .append(WS)
        .append(EQ)
        .append(WS)
        .append(avallaCheckTerm.getRightTerm())
        .append(SEMI)
        .append(System.lineSeparator());
  }

}
