package asmeta.junit2avalla.javascenario;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;
import asmeta.junit2avalla.model.terms.AvallaCheckTerm;
import asmeta.junit2avalla.model.terms.AvallaTerm;
import asmeta.junit2avalla.model.terms.AvallaHeaderTerm;
import asmeta.junit2avalla.model.terms.AvallaLoadTerm;
import asmeta.junit2avalla.javascenario.ScenarioManagerImpl;
import asmeta.junit2avalla.model.Scenario;
import asmeta.junit2avalla.model.terms.AvallaSetTerm;
import asmeta.junit2avalla.model.terms.AvallaStepTerm;
import asmeta.junit2avalla.model.terms.JavaVariableTerm;
import asmeta.junit2avalla.util.AssertionUtil;
import asmeta.junit2avalla.util.JavaScenarioUtil;
import asmeta.junit2avalla.util.VariableUtil;
import org.junit.Test;

public class ScenarioManagerImplTest {

  @Test
  public void whenSetHeaderTerm_HeaderTermIsCreatedAndAddedToQueue(){
    ScenarioManagerImpl scenarioManagerImpl = new ScenarioManagerImpl();
    Scenario scenario = new Scenario();
    scenarioManagerImpl.setHeaderTerm(scenario,"registroDiCassav3_ATG0",0);

    AvallaTerm avallaTerm = scenario.remove();
    assertTrue(avallaTerm instanceof AvallaHeaderTerm);
    assertEquals("registroDiCassav3_scenario0",((AvallaHeaderTerm) avallaTerm).getScenarioName());
  }

  @Test
  public void whenSetLoadTerm_HeaderTermIsCreatedAndAddedToQueue(){
    ScenarioManagerImpl scenarioManagerImpl = new ScenarioManagerImpl();
    Scenario scenario = new Scenario();
    scenarioManagerImpl.setLoadTerm(scenario,"registroDiCassav3_ATG0");

    AvallaTerm avallaTerm = scenario.remove();
    assertTrue(avallaTerm instanceof AvallaLoadTerm);
    assertEquals("registroDiCassav3",((AvallaLoadTerm) avallaTerm).getLoad());
  }

  @Test
  public void whenSetSetTerm_SetTermIsCreatedAndAddedToQueue(){
    ScenarioManagerImpl scenarioManagerImpl = new ScenarioManagerImpl();
    Scenario avallaScenario = new Scenario();
    scenarioManagerImpl.setSetTerm(avallaScenario, VariableUtil.getVariable0());
    scenarioManagerImpl.setSetTerm(avallaScenario, VariableUtil.getVariable1());
    scenarioManagerImpl.setSetTerm(avallaScenario, VariableUtil.getVariable2());
    scenarioManagerImpl.setSetTerm(avallaScenario, VariableUtil.getVariable3());
    scenarioManagerImpl.setSetTerm(avallaScenario, VariableUtil.getVariable4());
    scenarioManagerImpl.setSetTerm(avallaScenario, VariableUtil.getVariable5());
    assertFalse(avallaScenario.getScenario().isEmpty());

    AvallaTerm avallaTerm = avallaScenario.remove();
    assertTrue(avallaTerm instanceof AvallaSetTerm);
    assertEquals(JavaScenarioUtil.SERVIZIO_SELEZIONATO,((AvallaSetTerm) avallaTerm).getName());
    assertEquals("NEWORDINE",((AvallaSetTerm) avallaTerm).getValue());

    avallaTerm = avallaScenario.remove();
    assertTrue(avallaTerm instanceof AvallaSetTerm);
    assertEquals(JavaScenarioUtil.PIZZA_INSERITA,((AvallaSetTerm) avallaTerm).getName());
    assertEquals("margherita",((AvallaSetTerm) avallaTerm).getValue());

    avallaTerm = avallaScenario.remove();
    assertTrue(avallaTerm instanceof AvallaSetTerm);
    assertEquals(JavaScenarioUtil.SCELTA_DI_AGGIUNTA_PIZZA,((AvallaSetTerm) avallaTerm).getName());
    assertEquals("SI",((AvallaSetTerm) avallaTerm).getValue());

    avallaTerm = avallaScenario.remove();
    assertTrue(avallaTerm instanceof AvallaSetTerm);
    assertEquals(JavaScenarioUtil.SCELTA_DEL_TIPO_PIZZA,((AvallaSetTerm) avallaTerm).getName());
    assertEquals("STANDARD",((AvallaSetTerm) avallaTerm).getValue());

    avallaTerm = avallaScenario.remove();
    assertTrue(avallaTerm instanceof AvallaSetTerm);
    assertEquals(JavaScenarioUtil.INSERT_QUANTITA,((AvallaSetTerm) avallaTerm).getName());
    assertEquals("2",((AvallaSetTerm) avallaTerm).getValue());

    avallaTerm = avallaScenario.remove();
    assertTrue(avallaTerm instanceof AvallaSetTerm);
    assertEquals(JavaScenarioUtil.INSERT_PREZZO,((AvallaSetTerm) avallaTerm).getName());
    assertEquals("2",((AvallaSetTerm) avallaTerm).getValue());
  }

  @Test
  public void whenSetStepTerm_SetTermIsCreatedAndAddedToQueue(){
    ScenarioManagerImpl scenarioManagerImpl = new ScenarioManagerImpl();
    Scenario avallaScenario = new Scenario();
    scenarioManagerImpl.setStepTerm(avallaScenario);

    AvallaTerm avallaTerm = avallaScenario.remove();
    assertTrue(avallaTerm instanceof AvallaStepTerm);
  }

  @Test
  public void whenSetCheckTerm_CheckTermIsCreatedAndAddedToQueue(){
    ScenarioManagerImpl scenarioManagerImpl = new ScenarioManagerImpl();
    Scenario avallaScenario = new Scenario();
    scenarioManagerImpl.setCheckTerm(avallaScenario, AssertionUtil.getAssertion0());
    scenarioManagerImpl.setCheckTerm(avallaScenario, AssertionUtil.getAssertion1());
    scenarioManagerImpl.setCheckTerm(avallaScenario, AssertionUtil.getAssertion2());

    AvallaTerm avallaTerm = avallaScenario.remove();
    assertTrue(avallaTerm instanceof AvallaCheckTerm);
    assertEquals("\"Scegli il tipo di pizza desiderata:\"",((AvallaCheckTerm) avallaTerm).getRightTerm());
    assertEquals(JavaScenarioUtil.OUT_MESS,((AvallaCheckTerm) avallaTerm).getLeftTerm());

    avallaTerm = avallaScenario.remove();
    assertTrue(avallaTerm instanceof AvallaCheckTerm);
    assertEquals("0",((AvallaCheckTerm) avallaTerm).getRightTerm());
    assertEquals(JavaScenarioUtil.TOTALE,((AvallaCheckTerm) avallaTerm).getLeftTerm());

    avallaTerm = avallaScenario.remove();
    assertTrue(avallaTerm instanceof AvallaCheckTerm);
    assertEquals("SCEGLI_TIPO_DI_PIZZA",((AvallaCheckTerm) avallaTerm).getRightTerm());
    assertEquals(JavaScenarioUtil.STATO_CASSA,((AvallaCheckTerm) avallaTerm).getLeftTerm());
  }

}
