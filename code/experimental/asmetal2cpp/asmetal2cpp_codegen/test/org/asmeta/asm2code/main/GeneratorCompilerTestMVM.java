package org.asmeta.asm2code.main;

import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.asmeta.asm2code.main.CppGenerator;
import org.asmeta.asm2code.main.HeaderGenerator;
import org.asmeta.asm2code.main.TranslatorOptions.CompilerType;
import org.asmeta.asm2code.compiler.CompileResult;
import org.asmeta.asm2code.compiler.CppCompiler;
import org.asmeta.parser.ASMParser;
import org.junit.Test;

import asmeta.AsmCollection;

// to test with MVM 
public class GeneratorCompilerTestMVM extends GeneratorCompilerTest2 {

	@Test
	public void testMVM() throws IOException, Exception {
		TranslatorOptions options= new TranslatorOptions(false, true, true, true);
		//options.compilerType = CompilerType.DesktopCompiler;
		options.compilerType = CompilerType.ArduinoCompiler;
		String asmspec = "D:\\AgHome\\progettidaSVNGIT\\mvm-asmeta\\VentilatoreASM\\Ventilatore000.asm";
		if (!test(asmspec,options).success)
			fail();
	}
}
