package org.asmeta.asm2code.main;

import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.asmeta.asm2code.main.CppGenerator;
import org.asmeta.asm2code.main.HeaderGenerator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.asmeta.asm2code.compiler.CompileResult;
import org.asmeta.asm2code.compiler.CppCompiler;
import org.asmeta.parser.ASMParser;
import org.junit.Test;

import asmeta.AsmCollection;

public class GeneratorCompilerModules extends GeneratorCompilerTest2{

	@Test
	public void testIncluding() throws IOException, Exception {
		Logger.getLogger(CppCompiler.class).setLevel(Level.ALL);
		String asmspec = "examples/modules/IncludingAsm.asm";
		if (!test(asmspec,options).success)
			fail();
	}

	
	@Test
	public void testIncluded() throws IOException, Exception {
		// questa non dovrebbe essere necessaria
		Logger.getLogger(CppCompiler.class).setLevel(Level.ALL);
		String asmspec = "examples/modules/IncludedModule.asm";
		if (!test(asmspec,options).success)
			fail();
	}

}
