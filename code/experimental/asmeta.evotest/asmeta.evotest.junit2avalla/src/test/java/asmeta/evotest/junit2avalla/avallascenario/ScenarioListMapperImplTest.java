package asmeta.evotest.junit2avalla.avallascenario;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

import asmeta.evotest.junit2avalla.model.Scenario;
import asmeta.evotest.junit2avalla.model.ScenarioFile;
import asmeta.evotest.junit2avalla.util.ScenarioAvallaUtil;

public class ScenarioListMapperImplTest {

  @Test
  public void mapScenarioListAndCheckResults(){

    Scenario scenario1 = ScenarioAvallaUtil.getScenarioAvalla();
    Scenario scenario2 = ScenarioAvallaUtil.getScenarioAvalla();

    List<Scenario> scenarioList = new LinkedList<>();

    scenarioList.add(scenario1);
    scenarioList.add(scenario2);

    ScenarioListMapperImpl scenarioListMapperImpl = new ScenarioListMapperImpl();

    List<ScenarioFile> scenarioFileList =
        scenarioListMapperImpl.mapScenarioListToFileList(scenarioList);

    assertFalse(scenarioFileList.isEmpty());
    assertEquals(2,scenarioFileList.size());

    ScenarioFile scenarioFile = scenarioFileList.remove(0);

    assertEquals(scenarioFile.getText(),ScenarioAvallaUtil.getAvallaScenario());
    assertEquals(scenarioFile.getName(),ScenarioAvallaUtil.avallaHeaderTerm().getScenarioName());

    scenarioFile = scenarioFileList.remove(0);

    assertEquals(scenarioFile.getText(),ScenarioAvallaUtil.getAvallaScenario());
    assertEquals(scenarioFile.getName(),ScenarioAvallaUtil.avallaHeaderTerm().getScenarioName());

    assertTrue(scenarioFileList.isEmpty());

  }

}
