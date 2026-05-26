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
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import asmeta.ai.propgen.ASMtoNLOperation;
import asmeta.ai.propgen.NLtoTLOperation;
import asmeta.ai.propgen.PropertyType;
import asmeta.ai.propgen.TLtoNLOperation;
import asmeta.ai.propgen.llm.OllamaClient;

class TestOperationsOllama {

	private static final String JSON_PATH = "./resources/keys.json";
	static OllamaClient llm;

	@BeforeAll
	static void setup() throws Exception {
		BasicConfigurator.configure();
		Logger.getRootLogger().setLevel(Level.INFO);
		Logger.getLogger(ASMtoNLOperation.class).setLevel(Level.DEBUG);
		Logger.getLogger(NLtoTLOperation.class).setLevel(Level.DEBUG);
		Logger.getLogger(TLtoNLOperation.class).setLevel(Level.DEBUG);

		JSONObject jo = (JSONObject) new JSONParser().parse(new FileReader(JSON_PATH));
		String key = (String) jo.get("ollama");
		llm = new OllamaClient(key);
		System.out.println("===== OLLAMA =====");
	}

	@BeforeEach
	void setLlmModel() {
		llm.setModel("gpt-oss:20b");
	}

	@Test
	@Tag("TestToMavenSkip")
	void testPipelineForOneModel() throws Exception {
		String asmPath = "../../../../../asm_examples/examples/coffeeVendingMachine/coffeeVendingMachine.asm";
		List<String> res = new ASMtoNLOperation(llm).generate(asmPath, 2, true);
		for (String line : res) {
			System.out.println("--");
			System.out.println(line);
			String ctl = new NLtoTLOperation(llm).generate(asmPath, line, PropertyType.CTLPROP, true);
			System.out.println(ctl);
			String exp = new TLtoNLOperation(llm).explain(asmPath, ctl, true);
			System.out.println(exp);
		}
	}

	@Test
	@Tag("TestToMavenSkip")
	void testGenerationSession() throws Exception {
		// Scheduler does not import LTLLibrary, so the LLM will always fail to generate
		// a syntactically correct property.
		String asmPath = "../../../../../asmeta_models/tutorials/tutorial_FM26/Scheduler.asm";
		llm.setModel("ministral-3:3b");
		String property = "A job remains ready or running until (weak) fin becomes true";
		try {
			new NLtoTLOperation(llm).generateValidated(asmPath, property, PropertyType.LTLPROP, true, 2);
			fail();
		} catch (RuntimeException re) {
			// expected
		}
	}

	@Test
	@Tag("TestToMavenSkip")
	void formASMtoNL() throws Exception {
		OperationClockTestHelper.fromASMtoNLClock(llm);
	}

	@Test
	@Tag("TestToMavenSkip")
	void fromNLtoTL() throws Exception {
		OperationClockTestHelper.fromNLtoTLClock(llm);
	}

	@Test
	@Tag("TestToMavenSkip")
	void fromTLtoNL() throws Exception {
		OperationClockTestHelper.fromTLtoNLClock(llm);
	}

}
