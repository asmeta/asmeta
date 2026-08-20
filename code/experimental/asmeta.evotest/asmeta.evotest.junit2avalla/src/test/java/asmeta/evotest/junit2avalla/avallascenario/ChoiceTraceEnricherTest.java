package asmeta.evotest.junit2avalla.avallascenario;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import asmeta.evotest.junit2avalla.model.Scenario;
import asmeta.evotest.junit2avalla.model.ScenarioFile;
import asmeta.evotest.junit2avalla.model.terms.AvallaHeaderTerm;
import asmeta.evotest.junit2avalla.model.terms.AvallaLoadTerm;
import asmeta.evotest.junit2avalla.model.terms.AvallaPickTerm;
import asmeta.evotest.junit2avalla.model.terms.AvallaStepTerm;
import asmeta.evotest.junit2avalla.model.terms.AvallaTerm;

class ChoiceTraceEnricherTest {

	@TempDir
	Path tempDir;

	@Test
	void addsEachTestChoicesBeforeTheCorrespondingSteps() throws Exception {
		Scenario scenario0 = scenario("scenario0");
		Scenario scenario1 = scenario("scenario1");
		Path trace = writeTrace("""
				test.count=2
				test.0.name=test0
				test.0.choice.count=2
				test.0.choice.1.step=0
				test.0.choice.1.variable=$z
				test.0.choice.1.rule=r_Main
				test.0.choice.1.value=COFFEE
				test.0.choice.0.step=0
				test.0.choice.0.variable=$x
				test.0.choice.0.rule=r_Main
				test.0.choice.0.value=TEA
				test.0.choice.0.domain=Products
				test.1.name=test1
				test.1.choice.count=1
				test.1.choice.0.step=1
				test.1.choice.0.variable=$y
				test.1.choice.0.rule=r_Other
				test.1.choice.0.value=7
				test.1.choice.0.rndm=0
				""");

		new ChoiceTraceEnricher().enrich(List.of(scenario0, scenario1), trace);

		List<AvallaTerm> terms0 = new ArrayList<>(scenario0.getScenarioList());
		AvallaPickTerm firstPick = assertInstanceOf(AvallaPickTerm.class, terms0.get(2));
		assertEquals("$x", firstPick.getVariable());
		assertEquals("r_Main", firstPick.getRule());
		assertEquals("TEA", firstPick.getValue());
		AvallaPickTerm sameStepPick = assertInstanceOf(AvallaPickTerm.class, terms0.get(3));
		assertEquals("$z", sameStepPick.getVariable());
		assertEquals("r_Main", sameStepPick.getRule());
		assertEquals("COFFEE", sameStepPick.getValue());
		assertInstanceOf(AvallaStepTerm.class, terms0.get(4));

		List<AvallaTerm> terms1 = new ArrayList<>(scenario1.getScenarioList());
		assertInstanceOf(AvallaStepTerm.class, terms1.get(2));
		AvallaPickTerm secondPick = assertInstanceOf(AvallaPickTerm.class, terms1.get(3));
		assertEquals("$y", secondPick.getVariable());
		assertEquals("r_Other", secondPick.getRule());
		assertEquals("7", secondPick.getValue());
		assertInstanceOf(AvallaStepTerm.class, terms1.get(4));

		ScenarioFile scenarioFile = new ScenarioWriter().write(scenario0);
		String expectedFragment = "pick $x in r_Main := TEA;" + System.lineSeparator()
				+ "pick $z in r_Main := COFFEE;" + System.lineSeparator()
				+ System.lineSeparator() + "step";
		assertTrue(scenarioFile.getText().contains(expectedFragment));
	}

	@Test
	void rejectsAChoiceForAStepThatDoesNotExist() throws Exception {
		Scenario scenario = new Scenario();
		scenario.add(new AvallaHeaderTerm("scenario0"));
		scenario.add(new AvallaLoadTerm("Model"));
		scenario.add(new AvallaStepTerm());
		Path trace = writeTrace("""
				test.count=1
				test.0.choice.count=1
				test.0.choice.0.step=1
				test.0.choice.0.variable=$x
				test.0.choice.0.rule=r_Main
				test.0.choice.0.value=TEA
				""");

		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
				() -> new ChoiceTraceEnricher().enrich(List.of(scenario), trace));

		assertTrue(exception.getMessage().contains("test0"));
		assertTrue(exception.getMessage().contains("scenario0"));
	}

	private Scenario scenario(String name) {
		Scenario scenario = new Scenario();
		scenario.add(new AvallaHeaderTerm(name));
		scenario.add(new AvallaLoadTerm("Model"));
		scenario.add(new AvallaStepTerm());
		scenario.add(new AvallaStepTerm());
		return scenario;
	}

	private Path writeTrace(String contents) throws Exception {
		Path trace = tempDir.resolve("choices.properties");
		return Files.writeString(trace, contents);
	}
}
