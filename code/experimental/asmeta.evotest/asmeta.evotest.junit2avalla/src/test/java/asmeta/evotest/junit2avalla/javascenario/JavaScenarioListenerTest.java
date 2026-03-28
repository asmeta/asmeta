package asmeta.evotest.junit2avalla.javascenario;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import asmeta.evotest.junit2avalla.antlr.JavaScenarioLexer;
import asmeta.evotest.junit2avalla.antlr.JavaScenarioParser;
import asmeta.evotest.junit2avalla.model.Scenario;
import asmeta.evotest.junit2avalla.model.terms.AvallaCheckTerm;
import asmeta.evotest.junit2avalla.model.terms.AvallaHeaderTerm;
import asmeta.evotest.junit2avalla.model.terms.AvallaLoadTerm;
import asmeta.evotest.junit2avalla.model.terms.AvallaSetTerm;
import asmeta.evotest.junit2avalla.model.terms.AvallaStepTerm;
import asmeta.evotest.junit2avalla.model.terms.AvallaTerm;
import asmeta.evotest.junit2avalla.util.JavaScenarioUtil;


/**
 * Test the behavior of the antlr listener, analyze a junit file and create a
 * list with the avalla terms, then proceed to do some checks on these terms
 */
class JavaScenarioListenerTest {

	private static final String JUNITFILE = JavaScenarioUtil.getJavaFile_RegistroDiCassa();
	private static final JavaScenarioLexer javaScenarioLexer = new JavaScenarioLexer(CharStreams.fromString(JUNITFILE));
	private static final CommonTokenStream tokens = new CommonTokenStream(javaScenarioLexer);
	private static final JavaScenarioParser javaScenarioParser = new JavaScenarioParser(tokens);
	private static final ParseTreeWalker walker = new ParseTreeWalker();
	private static final CustomParserListener javaScenarioWalker = new CustomParserListener();
	private static List<Scenario> scenarioList;

	/**
	 * Parse the junit file and generates the scenario List, a list of avalla terms
	 */
	@BeforeAll
	static void setup() {
		Assertions.assertDoesNotThrow(() -> {
			// parse the junit file
			walker.walk(javaScenarioWalker, javaScenarioParser.start());
		}, "Expected no exception, but got: ");
		scenarioList = javaScenarioWalker.getScenarioList();
		assertFalse(scenarioList.isEmpty());
		assertTrue(javaScenarioWalker.getScenarioList().size() > 0);
	}

	/**
	 * parse a simple junit scenario and verify that the generated scenario list
	 * contains the correct avalla terms
	 */
	@Test void whenAddNewScenario_ThenParseAndCreateScenarioAvalla() {
		Scenario avallaScenario = scenarioList.get(0);
		assertTrue(avallaScenario.isValid());
		assertFalse(avallaScenario.getScenarioList().isEmpty());

		AvallaTerm avallaTerm = avallaScenario.remove();
		assertInstanceOf(AvallaHeaderTerm.class, avallaTerm);
		assertEquals(JavaScenarioUtil.SCENARIO_0, ((AvallaHeaderTerm) avallaTerm).getScenarioName());

		avallaTerm = avallaScenario.remove();
		assertInstanceOf(AvallaLoadTerm.class, avallaTerm);
		assertEquals(JavaScenarioUtil.NAME, ((AvallaLoadTerm) avallaTerm).getAsmName());

		avallaTerm = avallaScenario.remove();
		assertInstanceOf(AvallaCheckTerm.class, avallaTerm);
		assertEquals(JavaScenarioUtil.OUT_MESS, ((AvallaCheckTerm) avallaTerm).getLeftTerm());
		assertEquals("\"\"", ((AvallaCheckTerm) avallaTerm).getRightTerm());

		avallaTerm = avallaScenario.remove();
		assertInstanceOf(AvallaCheckTerm.class, avallaTerm);
		assertEquals(JavaScenarioUtil.STATO_CASSA, ((AvallaCheckTerm) avallaTerm).getLeftTerm());
		assertEquals("ATTENDI_ORDINAZIONI", ((AvallaCheckTerm) avallaTerm).getRightTerm());

		avallaTerm = avallaScenario.remove();
		assertInstanceOf(AvallaCheckTerm.class, avallaTerm);
		assertEquals(JavaScenarioUtil.TOTALE, ((AvallaCheckTerm) avallaTerm).getLeftTerm());
		assertEquals("0", ((AvallaCheckTerm) avallaTerm).getRightTerm());

		avallaTerm = avallaScenario.remove();
		assertInstanceOf(AvallaSetTerm.class, avallaTerm);
		assertEquals(JavaScenarioUtil.SCELTA_DI_AGGIUNTA_PIZZA, ((AvallaSetTerm) avallaTerm).getName());
		assertEquals("NO", ((AvallaSetTerm) avallaTerm).getValue());
		
		avallaTerm = avallaScenario.remove();
		assertInstanceOf(AvallaStepTerm.class, avallaTerm);

		avallaTerm = avallaScenario.remove();
		assertInstanceOf(AvallaSetTerm.class, avallaTerm);
		assertEquals(JavaScenarioUtil.SERVIZIO_SELEZIONATO, ((AvallaSetTerm) avallaTerm).getName());
		assertEquals("NEWORDINE", ((AvallaSetTerm) avallaTerm).getValue());

		assertTrue(avallaScenario.getScenarioList().isEmpty());

	}

	/**
	 * Test a junit scenario that handles an avalla abstract type
	 */
	@Test void parseAndCreateScenarioAvalla_TestSetAbstractDomainType() {
		Scenario avallaScenario = scenarioList.get(1);
		assertTrue(avallaScenario.isValid());
		assertFalse(avallaScenario.getScenarioList().isEmpty());

		AvallaTerm avallaTerm = avallaScenario.remove();
		assertInstanceOf(AvallaHeaderTerm.class, avallaTerm);
		assertEquals(JavaScenarioUtil.SCENARIO_1, ((AvallaHeaderTerm) avallaTerm).getScenarioName());

		avallaTerm = avallaScenario.remove();
		assertInstanceOf(AvallaLoadTerm.class, avallaTerm);
		assertEquals(JavaScenarioUtil.NAME, ((AvallaLoadTerm) avallaTerm).getAsmName());

		avallaScenario.remove();
		avallaScenario.remove();
		avallaScenario.remove();
		avallaScenario.remove();
		avallaScenario.remove();
		avallaScenario.remove();
		avallaScenario.remove();
		avallaScenario.remove();
		avallaScenario.remove();

		avallaTerm = avallaScenario.remove();
		assertInstanceOf(AvallaSetTerm.class, avallaTerm);
		assertEquals(JavaScenarioUtil.PIZZA_INSERITA, ((AvallaSetTerm) avallaTerm).getName());
		assertEquals("margherita", ((AvallaSetTerm) avallaTerm).getValue());

		avallaTerm = avallaScenario.remove();
		assertInstanceOf(AvallaSetTerm.class, avallaTerm);
		assertEquals(JavaScenarioUtil.SCELTA_DEL_TIPO_PIZZA, ((AvallaSetTerm) avallaTerm).getName());
		assertEquals("STANDARD", ((AvallaSetTerm) avallaTerm).getValue());

		avallaTerm = avallaScenario.remove();
		assertInstanceOf(AvallaStepTerm.class, avallaTerm);

		avallaTerm = avallaScenario.remove();
		assertInstanceOf(AvallaCheckTerm.class, avallaTerm);
		assertEquals(JavaScenarioUtil.STATO_CASSA, ((AvallaCheckTerm) avallaTerm).getLeftTerm());
		assertEquals("PIZZASTANDARD_SELEZIONATA", ((AvallaCheckTerm) avallaTerm).getRightTerm());

		avallaTerm = avallaScenario.remove();
		assertInstanceOf(AvallaCheckTerm.class, avallaTerm);
		assertEquals(JavaScenarioUtil.OUT_MESS, ((AvallaCheckTerm) avallaTerm).getLeftTerm());
		assertEquals("\"Inserisci il nome di una pizza dell'elenco:\"", ((AvallaCheckTerm) avallaTerm).getRightTerm());

		avallaTerm = avallaScenario.remove();
		assertInstanceOf(AvallaCheckTerm.class, avallaTerm);
		assertEquals(JavaScenarioUtil.TOTALE, ((AvallaCheckTerm) avallaTerm).getLeftTerm());
		assertEquals("0", ((AvallaCheckTerm) avallaTerm).getRightTerm());

		assertTrue(avallaScenario.getScenarioList().isEmpty());

	}

	/**
	 * Test that if the junit test has a try-catch structure the parser can generate
	 * a valid scenario up to the structure and when it encounters it stops
	 * generating instructions
	 */
	@Test void parseAndCreateScenarioAvalla_TestTryCatchBlock() {
		Scenario avallaScenario = scenarioList.get(6);
		assertTrue(avallaScenario.isValid());
		assertFalse(avallaScenario.getScenarioList().isEmpty());

		AvallaTerm avallaTerm = avallaScenario.remove();
		assertInstanceOf(AvallaHeaderTerm.class, avallaTerm);
		assertEquals(JavaScenarioUtil.SCENARIO_6, ((AvallaHeaderTerm) avallaTerm).getScenarioName());

		avallaTerm = avallaScenario.remove();
		assertInstanceOf(AvallaLoadTerm.class, avallaTerm);
		assertEquals(JavaScenarioUtil.NAME, ((AvallaLoadTerm) avallaTerm).getAsmName());

		avallaTerm = avallaScenario.remove();
		assertInstanceOf(AvallaCheckTerm.class, avallaTerm);
		assertEquals(JavaScenarioUtil.OUT_MESS, ((AvallaCheckTerm) avallaTerm).getLeftTerm());
		assertEquals("\"\"", ((AvallaCheckTerm) avallaTerm).getRightTerm());

		avallaTerm = avallaScenario.remove();
		assertInstanceOf(AvallaCheckTerm.class, avallaTerm);
		assertEquals(JavaScenarioUtil.TOTALE, ((AvallaCheckTerm) avallaTerm).getLeftTerm());
		assertEquals("0", ((AvallaCheckTerm) avallaTerm).getRightTerm());

		avallaTerm = avallaScenario.remove();
		assertInstanceOf(AvallaCheckTerm.class, avallaTerm);
		assertEquals(JavaScenarioUtil.STATO_CASSA, ((AvallaCheckTerm) avallaTerm).getLeftTerm());
		assertEquals("ATTENDI_ORDINAZIONI", ((AvallaCheckTerm) avallaTerm).getRightTerm());

		avallaTerm = avallaScenario.remove();
		assertInstanceOf(AvallaSetTerm.class, avallaTerm);
		assertEquals(JavaScenarioUtil.SCELTA_DEL_TIPO_PIZZA, ((AvallaSetTerm) avallaTerm).getName());
		assertEquals("OTHER", ((AvallaSetTerm) avallaTerm).getValue());

		avallaTerm = avallaScenario.remove();
		assertInstanceOf(AvallaSetTerm.class, avallaTerm);
		assertEquals(JavaScenarioUtil.SCELTA_DI_AGGIUNTA_PIZZA, ((AvallaSetTerm) avallaTerm).getName());
		assertEquals("SI", ((AvallaSetTerm) avallaTerm).getValue());
		
		avallaTerm = avallaScenario.remove();
		assertInstanceOf(AvallaStepTerm.class, avallaTerm);

		assertTrue(avallaScenario.getScenarioList().isEmpty());

	}

	/**
	 * Test that a scenario without a step function is labeled as invalid
	 */
	@Test void parseAndCreateScenarioAvalla_InvalidScenario() {
		Scenario avallaScenario = scenarioList.get(4);
		assertFalse(avallaScenario.isValid());
		assertFalse(avallaScenario.getScenarioList().isEmpty());
	}

	/**
	 * Test that the parser can retrieve the value of a variable and make the
	 * correct assertion
	 * <p>
	 * example: String string0 = registroDiCassav4_ATG0.get_outMess();
	 * assertEquals("", string0);
	 * <p>
	 */
	@Test void parseAndCreateScenarioAvalla_TestVariableOnEquals() {
		// Test this case:
		// String string0 = registroDiCassav4_ATG0.get_outMess()
		// assertEquals(\"\", string0)

		Scenario avallaScenario = scenarioList.get(5);
		assertFalse(avallaScenario.isValid());
		assertFalse(avallaScenario.getScenarioList().isEmpty());

		avallaScenario.remove();
		avallaScenario.remove();

		AvallaTerm avallaTerm = avallaScenario.remove();
		assertInstanceOf(AvallaCheckTerm.class, avallaTerm);
		assertEquals(JavaScenarioUtil.OUT_MESS, ((AvallaCheckTerm) avallaTerm).getLeftTerm());
		assertEquals("\"\"", ((AvallaCheckTerm) avallaTerm).getRightTerm());

		assertFalse(avallaScenario.getScenarioList().isEmpty());

		avallaTerm = avallaScenario.remove();
		assertInstanceOf(AvallaCheckTerm.class, avallaTerm);
		assertEquals(JavaScenarioUtil.OUT_MESS, ((AvallaCheckTerm) avallaTerm).getLeftTerm());
		assertEquals("\"\"", ((AvallaCheckTerm) avallaTerm).getRightTerm());

		assertTrue(avallaScenario.getScenarioList().isEmpty());
	}

	/**
	 * Test that when the parser encounters an unrecognized value it stops
	 * generating the instructions
	 */
	@Test void parseAndCreateScenarioAvalla_TestUnrecognizedValue() {
		Scenario avallaScenario = scenarioList.get(7);
		assertTrue(avallaScenario.isValid());
		assertFalse(avallaScenario.getScenarioList().isEmpty());

		AvallaTerm avallaTerm = avallaScenario.remove();
		assertInstanceOf(AvallaHeaderTerm.class, avallaTerm);
		assertEquals(JavaScenarioUtil.SCENARIO_7, ((AvallaHeaderTerm) avallaTerm).getScenarioName());

		avallaTerm = avallaScenario.remove();
		assertInstanceOf(AvallaLoadTerm.class, avallaTerm);
		assertEquals(JavaScenarioUtil.NAME, ((AvallaLoadTerm) avallaTerm).getAsmName());

		avallaTerm = avallaScenario.remove();
		assertInstanceOf(AvallaCheckTerm.class, avallaTerm);
		assertEquals(JavaScenarioUtil.OUT_MESS, ((AvallaCheckTerm) avallaTerm).getLeftTerm());
		assertEquals("\"\"", ((AvallaCheckTerm) avallaTerm).getRightTerm());

		avallaTerm = avallaScenario.remove();
		assertInstanceOf(AvallaCheckTerm.class, avallaTerm);
		assertEquals(JavaScenarioUtil.STATO_CASSA, ((AvallaCheckTerm) avallaTerm).getLeftTerm());
		assertEquals("ATTENDI_ORDINAZIONI", ((AvallaCheckTerm) avallaTerm).getRightTerm());

		avallaTerm = avallaScenario.remove();
		assertInstanceOf(AvallaStepTerm.class, avallaTerm);

		assertTrue(avallaScenario.getScenarioList().isEmpty());
	}
	
}
