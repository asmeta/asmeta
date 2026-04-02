package asmeta.asmeta.ai.propgen;

import java.io.FileReader;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Tag;

import asmeta.ai.propgen.PropertyGenerator;
import asmeta.ai.propgen.llm.OllamaClient;

class TestPropertyGeneratorOllama {

	private static final String JSON_PATH = "./src/test/resources/keys.json";
	static PropertyGenerator pg;

	@BeforeAll
	static void setup() throws Exception {
		JSONObject jo = (JSONObject) new JSONParser().parse(new FileReader(JSON_PATH));
		String key = (String) jo.get("ollama");
		OllamaClient llm = new OllamaClient(key);
		llm.setModel("ministral-3:14b");
		pg = new PropertyGenerator(llm);
		System.out.println("===== OLLAMA =====");
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
