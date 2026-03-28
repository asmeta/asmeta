package asmeta.evotest.junit2avalla.javascenario;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

import org.junit.jupiter.api.Test;

import asmeta.evotest.junit2avalla.model.Scenario;
import asmeta.evotest.junit2avalla.model.terms.AvallaCheckTerm;
import asmeta.evotest.junit2avalla.model.terms.AvallaHeaderTerm;
import asmeta.evotest.junit2avalla.model.terms.AvallaLoadTerm;
import asmeta.evotest.junit2avalla.model.terms.AvallaSetTerm;
import asmeta.evotest.junit2avalla.model.terms.AvallaStepTerm;
import asmeta.evotest.junit2avalla.model.terms.AvallaTerm;
import asmeta.evotest.junit2avalla.util.AssertionUtil;
import asmeta.evotest.junit2avalla.util.JavaScenarioUtil;
import asmeta.evotest.junit2avalla.util.VariableUtil;

class ScenarioManagerTest {

	@Test void whenSetHeaderTerm_HeaderTermIsCreatedAndAddedToQueue(){
    ScenarioManager scenarioManagerImpl = new ScenarioManager();
    Scenario scenario = new Scenario();
    scenarioManagerImpl.setHeaderTerm(scenario,"registroDiCassav3_ATG0",0);

    AvallaTerm avallaTerm = scenario.remove();
		assertInstanceOf(AvallaHeaderTerm.class, avallaTerm);
    assertEquals("registroDiCassav3_scenario0",((AvallaHeaderTerm) avallaTerm).getScenarioName());
  }

	@Test void whenSetLoadTerm_HeaderTermIsCreatedAndAddedToQueue(){
    ScenarioManager scenarioManagerImpl = new ScenarioManager();
    Scenario scenario = new Scenario();
    scenarioManagerImpl.setLoadTerm(scenario,"registroDiCassav3_ATG0");

    AvallaTerm avallaTerm = scenario.remove();
		assertInstanceOf(AvallaLoadTerm.class, avallaTerm);
    assertEquals("registroDiCassav3",((AvallaLoadTerm) avallaTerm).getAsmName());
  }

	@Test void whenSetSetTerm_SetTermIsCreatedAndAddedToQueue(){
    ScenarioManager scenarioManagerImpl = new ScenarioManager();
    Scenario avallaScenario = new Scenario();
    scenarioManagerImpl.setSetTerm(avallaScenario, VariableUtil.getVariable0());
    scenarioManagerImpl.setSetTerm(avallaScenario, VariableUtil.getVariable1());
    scenarioManagerImpl.setSetTerm(avallaScenario, VariableUtil.getVariable2());
    scenarioManagerImpl.setSetTerm(avallaScenario, VariableUtil.getVariable3());
    scenarioManagerImpl.setSetTerm(avallaScenario, VariableUtil.getVariable4());
    scenarioManagerImpl.setSetTerm(avallaScenario, VariableUtil.getVariable5());
    assertFalse(avallaScenario.getScenarioList().isEmpty());

    AvallaTerm avallaTerm = avallaScenario.remove();
		assertInstanceOf(AvallaSetTerm.class, avallaTerm);
    assertEquals(JavaScenarioUtil.SERVIZIO_SELEZIONATO,((AvallaSetTerm) avallaTerm).getName());
    assertEquals("NEWORDINE",((AvallaSetTerm) avallaTerm).getValue());

    avallaTerm = avallaScenario.remove();
		assertInstanceOf(AvallaSetTerm.class, avallaTerm);
    assertEquals(JavaScenarioUtil.PIZZA_INSERITA,((AvallaSetTerm) avallaTerm).getName());
    assertEquals("margherita",((AvallaSetTerm) avallaTerm).getValue());

    avallaTerm = avallaScenario.remove();
		assertInstanceOf(AvallaSetTerm.class, avallaTerm);
    assertEquals(JavaScenarioUtil.SCELTA_DI_AGGIUNTA_PIZZA,((AvallaSetTerm) avallaTerm).getName());
    assertEquals("SI",((AvallaSetTerm) avallaTerm).getValue());

    avallaTerm = avallaScenario.remove();
		assertInstanceOf(AvallaSetTerm.class, avallaTerm);
    assertEquals(JavaScenarioUtil.SCELTA_DEL_TIPO_PIZZA,((AvallaSetTerm) avallaTerm).getName());
    assertEquals("STANDARD",((AvallaSetTerm) avallaTerm).getValue());

    avallaTerm = avallaScenario.remove();
		assertInstanceOf(AvallaSetTerm.class, avallaTerm);
    assertEquals(JavaScenarioUtil.INSERT_QUANTITA,((AvallaSetTerm) avallaTerm).getName());
    assertEquals("2",((AvallaSetTerm) avallaTerm).getValue());

    avallaTerm = avallaScenario.remove();
		assertInstanceOf(AvallaSetTerm.class, avallaTerm);
    assertEquals(JavaScenarioUtil.INSERT_PREZZO,((AvallaSetTerm) avallaTerm).getName());
    assertEquals("2",((AvallaSetTerm) avallaTerm).getValue());
  }

	@Test void whenSetStepTerm_SetTermIsCreatedAndAddedToQueue(){
    ScenarioManager scenarioManagerImpl = new ScenarioManager();
    Scenario avallaScenario = new Scenario();
    scenarioManagerImpl.setStepTerm(avallaScenario);

    AvallaTerm avallaTerm = avallaScenario.remove();
		assertInstanceOf(AvallaStepTerm.class, avallaTerm);
  }

	@Test void whenSetCheckTerm_CheckTermIsCreatedAndAddedToQueue(){
    ScenarioManager scenarioManagerImpl = new ScenarioManager();
    Scenario avallaScenario = new Scenario();
    scenarioManagerImpl.setCheckTerm(avallaScenario, AssertionUtil.getAssertion0());
    scenarioManagerImpl.setCheckTerm(avallaScenario, AssertionUtil.getAssertion1());
    scenarioManagerImpl.setCheckTerm(avallaScenario, AssertionUtil.getAssertion2());

    AvallaTerm avallaTerm = avallaScenario.remove();
		assertInstanceOf(AvallaCheckTerm.class, avallaTerm);
    assertEquals("\"Scegli il tipo di pizza desiderata:\"",((AvallaCheckTerm) avallaTerm).getRightTerm());
    assertEquals(JavaScenarioUtil.OUT_MESS,((AvallaCheckTerm) avallaTerm).getLeftTerm());

    avallaTerm = avallaScenario.remove();
		assertInstanceOf(AvallaCheckTerm.class, avallaTerm);
    assertEquals("0",((AvallaCheckTerm) avallaTerm).getRightTerm());
    assertEquals(JavaScenarioUtil.TOTALE,((AvallaCheckTerm) avallaTerm).getLeftTerm());

    avallaTerm = avallaScenario.remove();
		assertInstanceOf(AvallaCheckTerm.class, avallaTerm);
    assertEquals("SCEGLI_TIPO_DI_PIZZA",((AvallaCheckTerm) avallaTerm).getRightTerm());
    assertEquals(JavaScenarioUtil.STATO_CASSA,((AvallaCheckTerm) avallaTerm).getLeftTerm());
  }

}
