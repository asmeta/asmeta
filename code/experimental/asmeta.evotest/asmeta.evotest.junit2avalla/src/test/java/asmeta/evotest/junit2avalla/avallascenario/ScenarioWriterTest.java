package asmeta.evotest.junit2avalla.avallascenario;

import static org.junit.jupiter.api.Assertions.assertEquals;


import org.junit.jupiter.api.Test;

import asmeta.evotest.junit2avalla.model.Scenario;
import asmeta.evotest.junit2avalla.model.ScenarioFile;
import asmeta.evotest.junit2avalla.util.ScenarioAvallaUtil;

class ScenarioWriterTest {

	@Test void whenWriteScenario_thenScenarioFileIsCreated(){

    Scenario scenario = ScenarioAvallaUtil.getScenarioAvalla();

    ScenarioWriter scenarioWriterImpl = new ScenarioWriter();
    ScenarioFile scenarioFile = scenarioWriterImpl.write(scenario);

    assertEquals(scenarioFile.getText(),ScenarioAvallaUtil.getAvallaScenario());
    assertEquals(scenarioFile.getName(),ScenarioAvallaUtil.avallaHeaderTerm().getScenarioName());
  }

}
