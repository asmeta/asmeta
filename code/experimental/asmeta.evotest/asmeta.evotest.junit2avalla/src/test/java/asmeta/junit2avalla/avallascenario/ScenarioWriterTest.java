package asmeta.junit2avalla.avallascenario;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import asmeta.junit2avalla.avallascenario.ScenarioWriter;
import asmeta.junit2avalla.model.Scenario;
import asmeta.junit2avalla.model.ScenarioFile;
import asmeta.junit2avalla.util.ScenarioAvallaUtil;

public class ScenarioWriterTest {

  @Test
  public void whenWriteScenario_thenScenarioFileIsCreated(){

    Scenario scenario = ScenarioAvallaUtil.getScenarioAvalla();

    ScenarioWriter scenarioWriterImpl = new ScenarioWriter();
    ScenarioFile scenarioFile = scenarioWriterImpl.write(scenario);

    assertEquals(scenarioFile.getText(),ScenarioAvallaUtil.getAvallaScenario());
    assertEquals(scenarioFile.getName(),ScenarioAvallaUtil.avallaHeaderTerm().getScenarioName());
  }

}
