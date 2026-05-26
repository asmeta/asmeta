package asmeta.asmeta.ai.propgen;

import java.io.FileReader;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import asmeta.ai.propgen.llm.OpenAiClient;

class TestOperationsOpenai {

	private static final String JSON_PATH = "./resources/keys.json";
	static OpenAiClient llm;

	@BeforeAll
	static void setup() throws Exception {
		JSONObject jo = (JSONObject) new JSONParser().parse(new FileReader(JSON_PATH));
		String key = (String) jo.get("openai");
		llm = new OpenAiClient(key);
		System.out.println("===== OPENAI =====");
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
