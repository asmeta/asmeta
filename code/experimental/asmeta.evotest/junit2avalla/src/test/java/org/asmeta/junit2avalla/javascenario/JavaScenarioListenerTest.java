package org.asmeta.junit2avalla.javascenario;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.asmeta.junit2avalla.model.Scenario;
import org.asmeta.junit2avalla.model.terms.AvallaCheckTerm;
import org.asmeta.junit2avalla.model.terms.AvallaHeaderTerm;
import org.asmeta.junit2avalla.model.terms.AvallaLoadTerm;
import org.asmeta.junit2avalla.model.terms.AvallaSetTerm;
import org.asmeta.junit2avalla.model.terms.AvallaStepTerm;
import org.asmeta.junit2avalla.model.terms.AvallaTerm;
import org.asmeta.junit2avalla.util.JavaScenarioUtil;

import org.asmeta.junit2avalla.antlr.JavaScenarioLexer;
import org.asmeta.junit2avalla.antlr.JavaScenarioParser;

import org.junit.Test;

public class JavaScenarioListenerTest {

	@Test
	public void whenAddNewScenario_ThenParseAndCreateScenarioAvalla() {

		String javaFile = JavaScenarioUtil.getJavaFile_RegistroDiCassa();

		JavaScenarioLexer javaScenarioLexer = new JavaScenarioLexer(CharStreams.fromString(javaFile));
		CommonTokenStream tokens = new CommonTokenStream(javaScenarioLexer);
		JavaScenarioParser javaScenarioParser = new JavaScenarioParser(tokens);
		ParseTreeWalker walker = new ParseTreeWalker();
		JavaScenarioListener javaScenarioWalker = new JavaScenarioListener();
		walker.walk(javaScenarioWalker, javaScenarioParser.start());

		List<Scenario> scenarioList = javaScenarioWalker.getScenarioList();
		assertFalse(scenarioList.isEmpty());
		assertTrue(javaScenarioWalker.getScenarioList().size() > 0);
		Scenario avallaScenario = scenarioList.get(0);
		assertTrue(avallaScenario.isValid());
		assertFalse(avallaScenario.getScenario().isEmpty());

		AvallaTerm avallaTerm = avallaScenario.remove();
		assertTrue(avallaTerm instanceof AvallaHeaderTerm);
		assertEquals(JavaScenarioUtil.SCENARIO_0, ((AvallaHeaderTerm) avallaTerm).getScenarioName());

		avallaTerm = avallaScenario.remove();
		assertTrue(avallaTerm instanceof AvallaLoadTerm);
		assertEquals(JavaScenarioUtil.NAME, ((AvallaLoadTerm) avallaTerm).getLoad());

		avallaTerm = avallaScenario.remove();
		assertTrue(avallaTerm instanceof AvallaCheckTerm);
		assertEquals(JavaScenarioUtil.OUT_MESS, ((AvallaCheckTerm) avallaTerm).getLeftTerm());
		assertEquals("\"\"", ((AvallaCheckTerm) avallaTerm).getRightTerm());

		avallaTerm = avallaScenario.remove();
		assertTrue(avallaTerm instanceof AvallaCheckTerm);
		assertEquals(JavaScenarioUtil.STATO_CASSA, ((AvallaCheckTerm) avallaTerm).getLeftTerm());
		assertEquals("ATTENDI_ORDINAZIONI", ((AvallaCheckTerm) avallaTerm).getRightTerm());

		avallaTerm = avallaScenario.remove();
		assertTrue(avallaTerm instanceof AvallaCheckTerm);
		assertEquals(JavaScenarioUtil.TOTALE, ((AvallaCheckTerm) avallaTerm).getLeftTerm());
		assertEquals("0", ((AvallaCheckTerm) avallaTerm).getRightTerm());

		avallaTerm = avallaScenario.remove();
		assertTrue(avallaTerm instanceof AvallaSetTerm);
		assertEquals(JavaScenarioUtil.SCELTA_DI_AGGIUNTA_PIZZA, ((AvallaSetTerm) avallaTerm).getName());
		assertEquals("NO", ((AvallaSetTerm) avallaTerm).getValue());

		avallaTerm = avallaScenario.remove();
		assertTrue(avallaTerm instanceof AvallaSetTerm);
		assertEquals(JavaScenarioUtil.SERVIZIO_SELEZIONATO, ((AvallaSetTerm) avallaTerm).getName());
		assertEquals("NEWORDINE", ((AvallaSetTerm) avallaTerm).getValue());

		avallaTerm = avallaScenario.remove();
		assertTrue(avallaTerm instanceof AvallaStepTerm);

		avallaTerm = avallaScenario.remove();
		assertTrue(avallaTerm instanceof AvallaCheckTerm);
		assertEquals(JavaScenarioUtil.OUT_MESS, ((AvallaCheckTerm) avallaTerm).getLeftTerm());
		assertEquals("\"prezzo totale aggiornato\"", ((AvallaCheckTerm) avallaTerm).getRightTerm());

		avallaTerm = avallaScenario.remove();
		assertTrue(avallaTerm instanceof AvallaCheckTerm);
		assertEquals(JavaScenarioUtil.STATO_CASSA, ((AvallaCheckTerm) avallaTerm).getLeftTerm());
		assertEquals("ATTENDI_ORDINAZIONI", ((AvallaCheckTerm) avallaTerm).getRightTerm());

		avallaTerm = avallaScenario.remove();
		assertTrue(avallaTerm instanceof AvallaCheckTerm);
		assertEquals(JavaScenarioUtil.TOTALE, ((AvallaCheckTerm) avallaTerm).getLeftTerm());
		assertEquals("0", ((AvallaCheckTerm) avallaTerm).getRightTerm());

		avallaTerm = avallaScenario.remove();
		assertTrue(avallaTerm instanceof AvallaStepTerm);

		avallaTerm = avallaScenario.remove();
		assertTrue(avallaTerm instanceof AvallaCheckTerm);
		assertEquals(JavaScenarioUtil.OUT_MESS, ((AvallaCheckTerm) avallaTerm).getLeftTerm());
		assertEquals("\"prezzo totale aggiornato\"", ((AvallaCheckTerm) avallaTerm).getRightTerm());

		avallaTerm = avallaScenario.remove();
		assertTrue(avallaTerm instanceof AvallaCheckTerm);
		assertEquals(JavaScenarioUtil.STATO_CASSA, ((AvallaCheckTerm) avallaTerm).getLeftTerm());
		assertEquals("ATTENDI_ORDINAZIONI", ((AvallaCheckTerm) avallaTerm).getRightTerm());

		avallaTerm = avallaScenario.remove();
		assertTrue(avallaTerm instanceof AvallaCheckTerm);
		assertEquals(JavaScenarioUtil.TOTALE, ((AvallaCheckTerm) avallaTerm).getLeftTerm());
		assertEquals("0", ((AvallaCheckTerm) avallaTerm).getRightTerm());

		assertTrue(avallaScenario.getScenario().isEmpty());

	}

	@Test
	public void parseAndCreateScenarioAvalla_TestSetAbstractDomainType() {

		String javaFile = JavaScenarioUtil.getJavaFile_RegistroDiCassa();

		JavaScenarioLexer javaScenarioLexer = new JavaScenarioLexer(CharStreams.fromString(javaFile));
		CommonTokenStream tokens = new CommonTokenStream(javaScenarioLexer);
		JavaScenarioParser javaScenarioParser = new JavaScenarioParser(tokens);
		ParseTreeWalker walker = new ParseTreeWalker();
		JavaScenarioListener javaScenarioWalker = new JavaScenarioListener();
		walker.walk(javaScenarioWalker, javaScenarioParser.start());

		List<Scenario> scenarioList = javaScenarioWalker.getScenarioList();
		assertFalse(scenarioList.isEmpty());
		assertTrue(javaScenarioWalker.getScenarioList().size() > 0);
		Scenario avallaScenario = scenarioList.get(1);
		assertTrue(avallaScenario.isValid());
		assertFalse(avallaScenario.getScenario().isEmpty());

		AvallaTerm avallaTerm = avallaScenario.remove();
		assertTrue(avallaTerm instanceof AvallaHeaderTerm);
		assertEquals(JavaScenarioUtil.SCENARIO_1, ((AvallaHeaderTerm) avallaTerm).getScenarioName());

		avallaTerm = avallaScenario.remove();
		assertTrue(avallaTerm instanceof AvallaLoadTerm);
		assertEquals(JavaScenarioUtil.NAME, ((AvallaLoadTerm) avallaTerm).getLoad());

		avallaTerm = avallaScenario.remove();
		assertTrue(avallaTerm instanceof AvallaCheckTerm);
		assertEquals(JavaScenarioUtil.OUT_MESS, ((AvallaCheckTerm) avallaTerm).getLeftTerm());
		assertEquals("\"\"", ((AvallaCheckTerm) avallaTerm).getRightTerm());

		avallaTerm = avallaScenario.remove();
		assertTrue(avallaTerm instanceof AvallaCheckTerm);
		assertEquals(JavaScenarioUtil.STATO_CASSA, ((AvallaCheckTerm) avallaTerm).getLeftTerm());
		assertEquals("ATTENDI_ORDINAZIONI", ((AvallaCheckTerm) avallaTerm).getRightTerm());

		avallaTerm = avallaScenario.remove();
		assertTrue(avallaTerm instanceof AvallaCheckTerm);
		assertEquals(JavaScenarioUtil.TOTALE, ((AvallaCheckTerm) avallaTerm).getLeftTerm());
		assertEquals("0", ((AvallaCheckTerm) avallaTerm).getRightTerm());

		avallaTerm = avallaScenario.remove();
		assertTrue(avallaTerm instanceof AvallaSetTerm);
		assertEquals(JavaScenarioUtil.SCELTA_DI_AGGIUNTA_PIZZA, ((AvallaSetTerm) avallaTerm).getName());
		assertEquals("SI", ((AvallaSetTerm) avallaTerm).getValue());

		avallaTerm = avallaScenario.remove();
		assertTrue(avallaTerm instanceof AvallaSetTerm);
		assertEquals(JavaScenarioUtil.SERVIZIO_SELEZIONATO, ((AvallaSetTerm) avallaTerm).getName());
		assertEquals("NEWORDINE", ((AvallaSetTerm) avallaTerm).getValue());

		avallaTerm = avallaScenario.remove();
		assertTrue(avallaTerm instanceof AvallaStepTerm);

		avallaTerm = avallaScenario.remove();
		assertTrue(avallaTerm instanceof AvallaCheckTerm);
		assertEquals(JavaScenarioUtil.STATO_CASSA, ((AvallaCheckTerm) avallaTerm).getLeftTerm());
		assertEquals("SCEGLI_TIPO_DI_PIZZA", ((AvallaCheckTerm) avallaTerm).getRightTerm());

		avallaTerm = avallaScenario.remove();
		assertTrue(avallaTerm instanceof AvallaCheckTerm);
		assertEquals(JavaScenarioUtil.TOTALE, ((AvallaCheckTerm) avallaTerm).getLeftTerm());
		assertEquals("0", ((AvallaCheckTerm) avallaTerm).getRightTerm());

		avallaTerm = avallaScenario.remove();
		assertTrue(avallaTerm instanceof AvallaCheckTerm);
		assertEquals(JavaScenarioUtil.OUT_MESS, ((AvallaCheckTerm) avallaTerm).getLeftTerm());
		assertEquals("\"Scegli il tipo di pizza desiderata:\"", ((AvallaCheckTerm) avallaTerm).getRightTerm());

		avallaTerm = avallaScenario.remove();
		assertTrue(avallaTerm instanceof AvallaSetTerm);
		assertEquals(JavaScenarioUtil.PIZZA_INSERITA, ((AvallaSetTerm) avallaTerm).getName());
		assertEquals("margherita", ((AvallaSetTerm) avallaTerm).getValue());

		avallaTerm = avallaScenario.remove();
		assertTrue(avallaTerm instanceof AvallaSetTerm);
		assertEquals(JavaScenarioUtil.INSERT_QUANTITA, ((AvallaSetTerm) avallaTerm).getName());
		assertEquals("0", ((AvallaSetTerm) avallaTerm).getValue());

		avallaTerm = avallaScenario.remove();
		assertTrue(avallaTerm instanceof AvallaSetTerm);
		assertEquals(JavaScenarioUtil.SCELTA_DEL_TIPO_PIZZA, ((AvallaSetTerm) avallaTerm).getName());
		assertEquals("STANDARD", ((AvallaSetTerm) avallaTerm).getValue());

		avallaTerm = avallaScenario.remove();
		assertTrue(avallaTerm instanceof AvallaStepTerm);

		avallaTerm = avallaScenario.remove();
		assertTrue(avallaTerm instanceof AvallaCheckTerm);
		assertEquals(JavaScenarioUtil.STATO_CASSA, ((AvallaCheckTerm) avallaTerm).getLeftTerm());
		assertEquals("PIZZASTANDARD_SELEZIONATA", ((AvallaCheckTerm) avallaTerm).getRightTerm());

		avallaTerm = avallaScenario.remove();
		assertTrue(avallaTerm instanceof AvallaCheckTerm);
		assertEquals(JavaScenarioUtil.OUT_MESS, ((AvallaCheckTerm) avallaTerm).getLeftTerm());
		assertEquals("\"Inserisci il nome di una pizza dell'elenco:\"", ((AvallaCheckTerm) avallaTerm).getRightTerm());

		avallaTerm = avallaScenario.remove();
		assertTrue(avallaTerm instanceof AvallaCheckTerm);
		assertEquals(JavaScenarioUtil.TOTALE, ((AvallaCheckTerm) avallaTerm).getLeftTerm());
		assertEquals("0", ((AvallaCheckTerm) avallaTerm).getRightTerm());

		avallaTerm = avallaScenario.remove();
		assertTrue(avallaTerm instanceof AvallaStepTerm);

		avallaTerm = avallaScenario.remove();
		assertTrue(avallaTerm instanceof AvallaCheckTerm);
		assertEquals(JavaScenarioUtil.OUT_MESS, ((AvallaCheckTerm) avallaTerm).getLeftTerm());
		assertEquals("\"prezzo totale aggiornato\"", ((AvallaCheckTerm) avallaTerm).getRightTerm());

		avallaTerm = avallaScenario.remove();
		assertTrue(avallaTerm instanceof AvallaCheckTerm);
		assertEquals(JavaScenarioUtil.TOTALE, ((AvallaCheckTerm) avallaTerm).getLeftTerm());
		assertEquals("0", ((AvallaCheckTerm) avallaTerm).getRightTerm());

		avallaTerm = avallaScenario.remove();
		assertTrue(avallaTerm instanceof AvallaCheckTerm);
		assertEquals(JavaScenarioUtil.STATO_CASSA, ((AvallaCheckTerm) avallaTerm).getLeftTerm());
		assertEquals("SCEGLI_SE_AGGIUNGERE_PIZZA", ((AvallaCheckTerm) avallaTerm).getRightTerm());

		avallaTerm = avallaScenario.remove();
		assertTrue(avallaTerm instanceof AvallaStepTerm);

		avallaTerm = avallaScenario.remove();
		assertTrue(avallaTerm instanceof AvallaCheckTerm);
		assertEquals(JavaScenarioUtil.STATO_CASSA, ((AvallaCheckTerm) avallaTerm).getLeftTerm());
		assertEquals("SCEGLI_TIPO_DI_PIZZA", ((AvallaCheckTerm) avallaTerm).getRightTerm());

		avallaTerm = avallaScenario.remove();
		assertTrue(avallaTerm instanceof AvallaCheckTerm);
		assertEquals(JavaScenarioUtil.TOTALE, ((AvallaCheckTerm) avallaTerm).getLeftTerm());
		assertEquals("0", ((AvallaCheckTerm) avallaTerm).getRightTerm());

		avallaTerm = avallaScenario.remove();
		assertTrue(avallaTerm instanceof AvallaCheckTerm);
		assertEquals(JavaScenarioUtil.OUT_MESS, ((AvallaCheckTerm) avallaTerm).getLeftTerm());
		assertEquals("\"Scegli il tipo di pizza desiderata:\"", ((AvallaCheckTerm) avallaTerm).getRightTerm());

		assertTrue(avallaScenario.getScenario().isEmpty());

	}

	@Test
	public void parseAndCreateScenarioAvalla_TestTryCatchBlock() {

		String javaFile = JavaScenarioUtil.getJavaFile_RegistroDiCassa();

		JavaScenarioLexer javaScenarioLexer = new JavaScenarioLexer(CharStreams.fromString(javaFile));
		CommonTokenStream tokens = new CommonTokenStream(javaScenarioLexer);
		JavaScenarioParser javaScenarioParser = new JavaScenarioParser(tokens);
		ParseTreeWalker walker = new ParseTreeWalker();
		JavaScenarioListener javaScenarioWalker = new JavaScenarioListener();
		walker.walk(javaScenarioWalker, javaScenarioParser.start());

		List<Scenario> scenarioList = javaScenarioWalker.getScenarioList();
		assertFalse(scenarioList.isEmpty());
		assertTrue(javaScenarioWalker.getScenarioList().size() > 0);
		Scenario avallaScenario = scenarioList.get(6);
		assertTrue(avallaScenario.isValid());
		assertFalse(avallaScenario.getScenario().isEmpty());

		AvallaTerm avallaTerm = avallaScenario.remove();
		assertTrue(avallaTerm instanceof AvallaHeaderTerm);
		assertEquals(JavaScenarioUtil.SCENARIO_6, ((AvallaHeaderTerm) avallaTerm).getScenarioName());

		avallaTerm = avallaScenario.remove();
		assertTrue(avallaTerm instanceof AvallaLoadTerm);
		assertEquals(JavaScenarioUtil.NAME, ((AvallaLoadTerm) avallaTerm).getLoad());

		avallaTerm = avallaScenario.remove();
		assertTrue(avallaTerm instanceof AvallaCheckTerm);
		assertEquals(JavaScenarioUtil.OUT_MESS, ((AvallaCheckTerm) avallaTerm).getLeftTerm());
		assertEquals("\"\"", ((AvallaCheckTerm) avallaTerm).getRightTerm());

		avallaTerm = avallaScenario.remove();
		assertTrue(avallaTerm instanceof AvallaCheckTerm);
		assertEquals(JavaScenarioUtil.TOTALE, ((AvallaCheckTerm) avallaTerm).getLeftTerm());
		assertEquals("0", ((AvallaCheckTerm) avallaTerm).getRightTerm());

		avallaTerm = avallaScenario.remove();
		assertTrue(avallaTerm instanceof AvallaCheckTerm);
		assertEquals(JavaScenarioUtil.STATO_CASSA, ((AvallaCheckTerm) avallaTerm).getLeftTerm());
		assertEquals("ATTENDI_ORDINAZIONI", ((AvallaCheckTerm) avallaTerm).getRightTerm());

		avallaTerm = avallaScenario.remove();
		assertTrue(avallaTerm instanceof AvallaSetTerm);
		assertEquals(JavaScenarioUtil.SCELTA_DEL_TIPO_PIZZA, ((AvallaSetTerm) avallaTerm).getName());
		assertEquals("OTHER", ((AvallaSetTerm) avallaTerm).getValue());

		avallaTerm = avallaScenario.remove();
		assertTrue(avallaTerm instanceof AvallaSetTerm);
		assertEquals(JavaScenarioUtil.SCELTA_DI_AGGIUNTA_PIZZA, ((AvallaSetTerm) avallaTerm).getName());
		assertEquals("SI", ((AvallaSetTerm) avallaTerm).getValue());

		avallaTerm = avallaScenario.remove();
		assertTrue(avallaTerm instanceof AvallaSetTerm);
		assertEquals(JavaScenarioUtil.SERVIZIO_SELEZIONATO, ((AvallaSetTerm) avallaTerm).getName());
		assertEquals("NEWORDINE", ((AvallaSetTerm) avallaTerm).getValue());

		avallaTerm = avallaScenario.remove();
		assertTrue(avallaTerm instanceof AvallaStepTerm);

		avallaTerm = avallaScenario.remove();
		assertTrue(avallaTerm instanceof AvallaCheckTerm);
		assertEquals(JavaScenarioUtil.STATO_CASSA, ((AvallaCheckTerm) avallaTerm).getLeftTerm());
		assertEquals("SCEGLI_TIPO_DI_PIZZA", ((AvallaCheckTerm) avallaTerm).getRightTerm());

		avallaTerm = avallaScenario.remove();
		assertTrue(avallaTerm instanceof AvallaCheckTerm);
		assertEquals(JavaScenarioUtil.OUT_MESS, ((AvallaCheckTerm) avallaTerm).getLeftTerm());
		assertEquals("\"Scegli il tipo di pizza desiderata:\"", ((AvallaCheckTerm) avallaTerm).getRightTerm());

		avallaTerm = avallaScenario.remove();
		assertTrue(avallaTerm instanceof AvallaCheckTerm);
		assertEquals(JavaScenarioUtil.TOTALE, ((AvallaCheckTerm) avallaTerm).getLeftTerm());
		assertEquals("0", ((AvallaCheckTerm) avallaTerm).getRightTerm());

		avallaTerm = avallaScenario.remove();
		assertTrue(avallaTerm instanceof AvallaStepTerm);

		avallaTerm = avallaScenario.remove();
		assertTrue(avallaTerm instanceof AvallaCheckTerm);
		assertEquals(JavaScenarioUtil.OUT_MESS, ((AvallaCheckTerm) avallaTerm).getLeftTerm());
		assertEquals("\"Inserisci il nome di una nuova pizza:\"", ((AvallaCheckTerm) avallaTerm).getRightTerm());

		avallaTerm = avallaScenario.remove();
		assertTrue(avallaTerm instanceof AvallaCheckTerm);
		assertEquals(JavaScenarioUtil.TOTALE, ((AvallaCheckTerm) avallaTerm).getLeftTerm());
		assertEquals("0", ((AvallaCheckTerm) avallaTerm).getRightTerm());

		avallaTerm = avallaScenario.remove();
		assertTrue(avallaTerm instanceof AvallaCheckTerm);
		assertEquals(JavaScenarioUtil.STATO_CASSA, ((AvallaCheckTerm) avallaTerm).getLeftTerm());
		assertEquals("ALTRAPIZZA_SELEZIONATA", ((AvallaCheckTerm) avallaTerm).getRightTerm());

		assertTrue(avallaScenario.getScenario().isEmpty());

	}

	@Test
	public void parseAndCreateScenarioAvalla_InvalidScenario() {

		String javaFile = JavaScenarioUtil.getJavaFile_RegistroDiCassa();

		JavaScenarioLexer javaScenarioLexer = new JavaScenarioLexer(CharStreams.fromString(javaFile));
		CommonTokenStream tokens = new CommonTokenStream(javaScenarioLexer);
		JavaScenarioParser javaScenarioParser = new JavaScenarioParser(tokens);
		ParseTreeWalker walker = new ParseTreeWalker();
		JavaScenarioListener javaScenarioWalker = new JavaScenarioListener();
		walker.walk(javaScenarioWalker, javaScenarioParser.start());

		List<Scenario> scenarioList = javaScenarioWalker.getScenarioList();
		assertFalse(scenarioList.isEmpty());
		assertTrue(javaScenarioWalker.getScenarioList().size() > 0);
		Scenario avallaScenario = scenarioList.get(4);
		assertFalse(avallaScenario.isValid());
		assertFalse(avallaScenario.getScenario().isEmpty());
	}

	@Test
	public void parseAndCreateScenarioAvalla_TestVariableOnEquals() {
		// Test this case:
		// String string0 = registroDiCassav4_ATG0.get_outMess();
		// assertEquals(\"\", string0);

		String javaFile = JavaScenarioUtil.getJavaFile_RegistroDiCassa();

		JavaScenarioLexer javaScenarioLexer = new JavaScenarioLexer(CharStreams.fromString(javaFile));
		CommonTokenStream tokens = new CommonTokenStream(javaScenarioLexer);
		JavaScenarioParser javaScenarioParser = new JavaScenarioParser(tokens);
		ParseTreeWalker walker = new ParseTreeWalker();
		JavaScenarioListener javaScenarioWalker = new JavaScenarioListener();
		walker.walk(javaScenarioWalker, javaScenarioParser.start());

		List<Scenario> scenarioList = javaScenarioWalker.getScenarioList();
		assertFalse(scenarioList.isEmpty());
		assertTrue(javaScenarioWalker.getScenarioList().size() > 0);
		Scenario avallaScenario = scenarioList.get(5);
		assertFalse(avallaScenario.isValid());
		assertFalse(avallaScenario.getScenario().isEmpty());

		AvallaTerm avallaTerm = avallaScenario.remove();
		assertTrue(avallaTerm instanceof AvallaHeaderTerm);
		assertEquals(JavaScenarioUtil.SCENARIO_5, ((AvallaHeaderTerm) avallaTerm).getScenarioName());

		avallaTerm = avallaScenario.remove();
		assertTrue(avallaTerm instanceof AvallaLoadTerm);
		assertEquals(JavaScenarioUtil.NAME, ((AvallaLoadTerm) avallaTerm).getLoad());

		avallaTerm = avallaScenario.remove();
		assertTrue(avallaTerm instanceof AvallaCheckTerm);
		assertEquals(JavaScenarioUtil.TOTALE, ((AvallaCheckTerm) avallaTerm).getLeftTerm());
		assertEquals("0", ((AvallaCheckTerm) avallaTerm).getRightTerm());

		avallaTerm = avallaScenario.remove();
		assertTrue(avallaTerm instanceof AvallaCheckTerm);
		assertEquals(JavaScenarioUtil.OUT_MESS, ((AvallaCheckTerm) avallaTerm).getLeftTerm());
		assertEquals("\"\"", ((AvallaCheckTerm) avallaTerm).getRightTerm());

		avallaTerm = avallaScenario.remove();
		assertTrue(avallaTerm instanceof AvallaCheckTerm);
		assertEquals(JavaScenarioUtil.STATO_CASSA, ((AvallaCheckTerm) avallaTerm).getLeftTerm());
		assertEquals("ATTENDI_ORDINAZIONI", ((AvallaCheckTerm) avallaTerm).getRightTerm());
		
		avallaTerm = avallaScenario.remove();
		assertTrue(avallaTerm instanceof AvallaCheckTerm);
		assertEquals(JavaScenarioUtil.TOTALE, ((AvallaCheckTerm) avallaTerm).getLeftTerm());
		assertEquals("0", ((AvallaCheckTerm) avallaTerm).getRightTerm());

		avallaTerm = avallaScenario.remove();
		assertTrue(avallaTerm instanceof AvallaCheckTerm);
		assertEquals(JavaScenarioUtil.OUT_MESS, ((AvallaCheckTerm) avallaTerm).getLeftTerm());
		assertEquals("\"\"", ((AvallaCheckTerm) avallaTerm).getRightTerm());

		avallaTerm = avallaScenario.remove();
		assertTrue(avallaTerm instanceof AvallaCheckTerm);
		assertEquals(JavaScenarioUtil.STATO_CASSA, ((AvallaCheckTerm) avallaTerm).getLeftTerm());
		assertEquals("ATTENDI_ORDINAZIONI", ((AvallaCheckTerm) avallaTerm).getRightTerm());
		
		assertFalse(avallaScenario.getScenario().isEmpty());
		
		avallaTerm = avallaScenario.remove();
		assertTrue(avallaTerm instanceof AvallaCheckTerm);
		assertEquals(JavaScenarioUtil.OUT_MESS, ((AvallaCheckTerm) avallaTerm).getLeftTerm());
		assertEquals("\"\"", ((AvallaCheckTerm) avallaTerm).getRightTerm());
		
		assertTrue(avallaScenario.getScenario().isEmpty());


	}

}
