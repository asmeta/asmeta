package org.asmeta.junit2avalla.avallascenario;

import static org.junit.Assert.assertEquals;

import org.asmeta.junit2avalla.avallascenario.ScenarioWriterImpl;
import org.asmeta.junit2avalla.model.Scenario;
import org.asmeta.junit2avalla.model.ScenarioFile;
import org.asmeta.junit2avalla.util.ScenarioAvallaUtil;
import org.junit.Test;

public class ScenarioWriterImplTest {

  @Test
  public void whenWriteScenario_thenScenarioFileIsCreated(){

    Scenario scenario = ScenarioAvallaUtil.getScenarioAvalla();

    ScenarioWriterImpl scenarioWriterImpl = new ScenarioWriterImpl();
    ScenarioFile scenarioFile = scenarioWriterImpl.write(scenario);

    assertEquals(scenarioFile.getText(),ScenarioAvallaUtil.getAvallaScenario());
    assertEquals(scenarioFile.getName(),ScenarioAvallaUtil.avallaHeaderTerm().getScenarioName());
  }

}
