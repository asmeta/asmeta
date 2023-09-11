package org.asmeta.asm2code.main;

import asmeta.structure.Asm
import java.nio.file.Files
import java.nio.file.Paths
import org.asmeta.asm2code.formatter.Formatter
import org.eclipse.xtext.generator.IGenerator
import org.asmeta.parser.ASMParser

/** 
 * the real generator
 */
abstract class AsmToCGenerator implements IGenerator {
	protected TranslatorOptions options
	public static String Ext = ASMParser.ASM_EXTENSION

	new () {
		options = new TranslatorOptions(true, false, false,false)
	}

	new (TranslatorOptions options) {
		this.options = options
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
