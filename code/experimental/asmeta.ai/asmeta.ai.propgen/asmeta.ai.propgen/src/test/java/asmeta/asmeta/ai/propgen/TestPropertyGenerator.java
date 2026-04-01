package asmeta.asmeta.ai.propgen;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;

import org.asmeta.parser.ASMParser;
import org.junit.jupiter.api.Test;

import asmeta.AsmCollection;

class TestPropertyGenerator {

	@Test
	void test() throws Exception {
		File spec = new File("./examples/Clock.asm");
		AsmCollection x = ASMParser.setUpReadAsm(spec);
		PropertyGeneratorFromText pGen = new PropertyGeneratorFromText(x.getMain());
		pGen.generatePropertiesFromText(
				"when the sec function reaches the value 59, it is set to 0 in the next state", 
				PropertyType.CTLPROP);
	}

}
