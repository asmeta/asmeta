package asmeta.junit2avalla.avallascenario;

import static org.junit.Assert.assertEquals;

import asmeta.junit2avalla.avallascenario.ScenarioWriterImpl;
import asmeta.junit2avalla.model.Scenario;
import asmeta.junit2avalla.model.ScenarioFile;
import asmeta.junit2avalla.util.ScenarioAvallaUtil;
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
