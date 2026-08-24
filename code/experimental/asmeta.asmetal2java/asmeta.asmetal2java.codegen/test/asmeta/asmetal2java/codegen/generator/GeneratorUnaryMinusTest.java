package asmeta.asmetal2java.codegen.generator;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;

import asmeta.asmetal2java.codegen.compiler.CompileResult;

public class GeneratorUnaryMinusTest extends GeneratorCompileTest {

	@Test
	public void testUnaryMinusTranslation() throws Exception {
		Path asmFile = path.resolve("UnaryMinus.asm");
		CompileResult result = GeneratorCompilerUtil.genandcompile(asmFile.toString(), options,
				GeneratorCompilerUtil.dirTraduzione, GeneratorCompilerUtil.dirCompilazione);

		assertTrue(result.getSuccess());

		Path javaFile = GeneratorCompilerUtil.dirTraduzione.resolve("UnaryMinus.java");
		String javaCode = Files.readString(javaFile);
		assertTrue(javaCode.contains("negatedMonitored.set(-(mon.get()));"));
		assertTrue(javaCode.contains("negatedLiteral.set(-(5));"));
		assertTrue(javaCode.contains("negatedExpression.set(-((mon.get() + 1)));"));
	}
}
