package asmeta.asmeta.ai.propgen;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import asmeta.ai.propgen.PropertyGenerator;
import asmeta.ai.propgen.PropertyGenerator.PropertyType;
import asmeta.ai.propgen.llm.LlmClient;
import asmeta.ai.propgen.llm.OllamaClient;
import asmeta.ai.propgen.llm.OpenAiClient;

class TestPropertyGenerator {

	@Test
	void testOllama() throws Exception {
		OllamaClient llm = new OllamaClient("KEY");
		llm.setModel("ministral-3:3b");
		PropertyGenerator pg = new PropertyGenerator(llm);
		List<String> properties = pg.fromASMtoNL("./src/test/resources/AdvancedClock.asm", 3);
		System.out.println("===== O1 =====");
		for (String prop : properties) {
			System.out.println(prop);
		}
		
		String ctl = pg.fromNLtoTL("./src/test/resources/AdvancedClock.asm",
				"when the sec function reaches the value 59, it is set to 0 in the next state", PropertyType.CTLPROP);
		System.out.println("===== O2 =====");
		System.out.println(ctl);
		
		List<String> res = pg.fromTLtoNL("./src/test/resources/AdvancedClock.asm", "ctlspec ag (eq(min,59) implies ax(eq(min,0)))");
		System.out.println("===== O3 =====");
		System.out.println(res.get(0));
		System.out.println(res.get(1));
	}
	
	@Test
	void testOpenai() throws Exception {
		LlmClient llm = new OpenAiClient("KEY");
		PropertyGenerator pg = new PropertyGenerator(llm);
		List<String> properties = pg.fromASMtoNL("./src/test/resources/AdvancedClock.asm", 3);
		for (String prop : properties) {
			System.out.println(prop);
		}
	}

}
