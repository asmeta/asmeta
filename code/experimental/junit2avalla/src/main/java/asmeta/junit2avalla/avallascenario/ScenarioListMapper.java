package asmeta.junit2avalla.avallascenario;

import asmeta.junit2avalla.model.Scenario;
import asmeta.junit2avalla.model.ScenarioFile;
import java.util.List;


/**
 * The {@code ScenarioListMapper} interface defines a contract for mapping a list of
 * {@link Scenario} objects to a list of {@link ScenarioFile} objects.
 */
public interface ScenarioListMapper {

  /**
   * Maps a list of {@link Scenario} objects to a list of {@link ScenarioFile} objects.
   *
   * @param scenarioList the list of {@link Scenario} objects to be converted.
   * @return a list of {@link ScenarioFile} objects corresponding to the scenarios.
   */
  List<ScenarioFile> mapScenarioListToFileList(List<Scenario> scenarioList);

}