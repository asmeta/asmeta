package org.asmeta.asm2code.main;

import asmeta.structure.Asm
import java.nio.file.Files
import java.nio.file.Paths
import org.asmeta.asm2code.formatter.Formatter
import org.eclipse.xtext.generator.IGenerator

/** 
 * the real generator
 */
abstract class AsmToCGenerator implements IGenerator {
	public TranslatorOptions options
	public static String Ext = ".asm"

	new () {
		options = new TranslatorOptions(true, false, false,false)
	}

	// compile and write to a file
	def generate(Asm model, String path) {
		if (options === null) throw new Exception("TranslationOptions not inizialized")
		var compiled = compileAsm(model)
		if (options.formatter)
			compiled = Formatter.formatCode(compiled)
		Files.write(Paths.get(path), compiled.getBytes())
	}

// entry point to compile the Asm to a String
	protected abstract def String compileAsm(Asm asm)

}
