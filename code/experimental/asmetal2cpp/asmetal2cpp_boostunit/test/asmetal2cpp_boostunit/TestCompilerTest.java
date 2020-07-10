package asmetal2cpp_boostunit;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import org.asmeta.asm2code.compiler.CompileResult;
import org.asmeta.asm2code.compiler.CppCompiler;
import org.junit.Test;

public class TestCompilerTest {

	@Test
	public void compileBoostExample() throws IOException, InterruptedException {
		String string = "temp/test.cpp";
		PrintWriter writer = new PrintWriter(string);
		writer.println("#define BOOST_TEST_MODULE TestMyAsm\n" + "#include <boost/test/unit_test.hpp>\n"
				+ "#include <iostream>\n" + "//#include \"MyAsm.h\"\n" + "\n" + "BOOST_AUTO_TEST_CASE( my_test )\n"
				+ "{\n" + "}");
		writer.close();
		CompileResult result = CppCompiler.compile("test", "temp", false, false);
		System.out.println(result);
	}
	@Test
	public void compileMain() throws IOException, InterruptedException {
		String string = "temp/test.cpp";
		PrintWriter writer = new PrintWriter(string);
		writer.println("main(){int a;}");
		writer.close();
		CompileResult result = CppCompiler.compile("test", "temp", false, false);
		System.out.println(result);
	}

}
