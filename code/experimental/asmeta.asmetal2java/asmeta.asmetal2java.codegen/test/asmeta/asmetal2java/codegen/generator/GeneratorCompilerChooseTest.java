package asmeta.asmetal2java.codegen.generator;

import static org.junit.Assert.assertTrue;

import java.nio.file.Paths;

import org.junit.Test;

import asmeta.asmetal2java.codegen.compiler.CompileResult;

public class GeneratorCompilerChooseTest extends GeneratorCompileTest{
	
	
	@Test
	public void testSingleExampleWithChooseFromDT() throws Exception {
		String fileName = Paths.get("examples", "chooseTest", "ChooseFromDT.asm").toString();
		CompileResult genandcompile = GeneratorCompilerUtil.genandcompile(fileName , options,
				GeneratorCompilerUtil.dirTraduzione, GeneratorCompilerUtil.dirCompilazione);
		assertTrue(genandcompile.getSuccess());
	}
	
	@Test
	public void testSingleExampleWithChooseFromSubset() throws Exception {
		String fileName = Paths.get("examples", "chooseTest", "ChooseFromSubset.asm").toString();
		CompileResult genandcompile = GeneratorCompilerUtil.genandcompile(fileName , options,
				GeneratorCompilerUtil.dirTraduzione, GeneratorCompilerUtil.dirCompilazione);
		assertTrue(genandcompile.getSuccess());
	}
	
	@Test
	public void testSingleExampleWithChooseFromSubset2() throws Exception {
		String fileName = Paths.get("examples", "chooseTest", "ChooseFromSubset2.asm").toString();
		CompileResult genandcompile = GeneratorCompilerUtil.genandcompile(fileName , options,
				GeneratorCompilerUtil.dirTraduzione, GeneratorCompilerUtil.dirCompilazione);
		assertTrue(genandcompile.getSuccess());
	}
	
	@Test
	public void testSingleExampleWithChooseFromRange() throws Exception {
		/**
		 * This pass but may the generated class may throw UnsupportedOperationException when executed
		 * because tries to modify an unmodifiable list:
		point0 = Collections.unmodifiableList(
				Arrays.asList(-10, -9, -8, -7, -6, -5, -4, -3, -2, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
		for (Integer _s : point0) {
			if (true) {
				point0.add(_s);
			}
		}*/
		String fileName = Paths.get("examples", "chooseTest", "ChooseFromRange.asm").toString();
		CompileResult genandcompile = GeneratorCompilerUtil.genandcompile(fileName , options,
				GeneratorCompilerUtil.dirTraduzione, GeneratorCompilerUtil.dirCompilazione);
		assertTrue(genandcompile.getSuccess());
	}
}

