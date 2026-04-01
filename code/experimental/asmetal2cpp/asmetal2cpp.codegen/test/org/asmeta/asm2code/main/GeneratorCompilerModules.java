package org.asmeta.asm2code.main;


import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.asmeta.asm2code.compiler.CppCompiler;
import org.junit.jupiter.api.Test;

public class GeneratorCompilerModules extends GeneratorCompilerTest{

	@Test
	public void testIncluding() throws IOException, Exception {
		Logger.getLogger(CppCompiler.class).setLevel(Level.ALL);
		String asmspec = "examples/modules/IncludingAsm.asm";
		if (!AsmetaL2CppGeneratorMain.translate(asmspec,testOptions, false).success)
			fail();
	}

	
	@Test
	public void testIncluded() throws IOException, Exception {
		// questa non dovrebbe essere necessaria
		Logger.getLogger(CppCompiler.class).setLevel(Level.ALL);
		String asmspec = "examples/modules/IncludedModule.asm";
		if (!AsmetaL2CppGeneratorMain.translate(asmspec,testOptions, false).success)
			fail();
	}

}
