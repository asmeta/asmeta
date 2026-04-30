package asmeta.asmeta.ai.propgen;

import static org.junit.jupiter.api.Assertions.fail;

import java.io.FileReader;
import java.util.List;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Tag;

import asmeta.ai.propgen.PropertyGenerationSession;
import asmeta.ai.propgen.PropertyGenerator;
import asmeta.ai.propgen.PropertyType;
import asmeta.ai.propgen.llm.OllamaClient;

class TestPropertyGeneratorOllama {

	private static final String JSON_PATH = "./resources/keys.json";
	static PropertyGenerator pg;

	@BeforeAll
	static void setup() throws Exception {
		BasicConfigurator.configure();
		Logger.getRootLogger().setLevel(Level.INFO);
		Logger.getLogger(PropertyGenerator.class).setLevel(Level.DEBUG);
		Logger.getLogger(PropertyGenerationSession.class).setLevel(Level.DEBUG);

		JSONObject jo = (JSONObject) new JSONParser().parse(new FileReader(JSON_PATH));
		String key = (String) jo.get("ollama");
		OllamaClient llm = new OllamaClient(key);
		pg = new PropertyGenerator(llm);
		System.out.println("===== OLLAMA =====");
	}
	
	@BeforeEach
	void setLlmModel() {
		OllamaClient llm = (OllamaClient) pg.getClient();
		llm.setModel("gpt-oss:20b");
	}

	@Test
	@Tag("TestToMavenSkip")
	void testPipelineForOneModel() throws Exception {
		String asmPath = "../../../../../asm_examples/examples/coffeeVendingMachine/coffeeVendingMachine.asm";
		List<String> res = pg.fromASMtoNL(asmPath, 2, true);
		for (String line : res) {
			System.out.println("--");
			System.out.println(line);
			String ctl = pg.fromNLtoTL(asmPath, line, PropertyType.CTLPROP, true);
			System.out.println(ctl);
			String exp = pg.fromTLtoNL(asmPath, ctl, true);
			System.out.println(exp);
		}
	}

	@Test
	@Tag("TestToMavenSkip")
	void testGenerationSession() throws Exception {
		// Scheduler does not import LTLLibrary, so the LLM will always fail to generate
		// a syntactically correct property
		String asmPath = "../../../../../asmeta_models/tutorials/tutorial_FM26/Scheduler.asm";
		OllamaClient llm = (OllamaClient) pg.getClient();
		llm.setModel("ministral-3:3b");
		PropertyGenerationSession session = new PropertyGenerationSession(pg);
		String property = "A job remains ready or running until (weak) fin becomes true";
		try {
			session.fromNLtoTLSession(asmPath, property, PropertyType.LTLPROP, true, 2);
			fail();
		} catch (RuntimeException re) {
			// expected
		}
	}

	@Test
	@Tag("TestToMavenSkip")
	void formASMtoNL() throws Exception {
		PropertyGeneratorClockTestHelper.fromASMtoNLClock(pg);
	}

	@Test
	@Tag("TestToMavenSkip")
	void fromNLtoTL() throws Exception {
		PropertyGeneratorClockTestHelper.fromNLtoTLClock(pg);
	}

	@Test
	@Tag("TestToMavenSkip")
	void fromTLtoNL() throws Exception {
		PropertyGeneratorClockTestHelper.fromTLtoNLClock(pg);
	}

}
