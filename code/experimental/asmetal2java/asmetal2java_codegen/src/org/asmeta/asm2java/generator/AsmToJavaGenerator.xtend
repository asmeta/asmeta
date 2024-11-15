package org.asmeta.asm2java.generator;

import asmeta.structure.Asm
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.xtext.generator.IFileSystemAccess
import org.eclipse.xtext.generator.IGenerator
import org.asmeta.asm2java.config.TranslatorOptions
import org.asmeta.asm2java.formatter.Formatter
import org.asmeta.asm2java.formatter.FormatterImpl

/** 
 * Generators superclass.
 */
abstract class AsmToJavaGenerator implements IGenerator {

	protected TranslatorOptions options 
	
	protected Formatter formatter
	
	new(){
		this.formatter = new FormatterImpl();
	}
	
	override doGenerate(Resource input, IFileSystemAccess fsa) {
		throw new UnsupportedOperationException("TODO: auto-generated method stub")
	}


	def compileAndWrite(Asm asm, String writerPath, String msg, TranslatorOptions userOptions) {
		println(msg + " file generation in " + writerPath)
		val file = new FileWriter(writerPath) 
		val writer = new BufferedWriter(file)
		val javaCode = compileAsm(asm, userOptions)
		
		if (options.getFormatter()){
			writer.write(formatter.formatCode(javaCode));
		} else{
			writer.write(javaCode)			
		}
		
		writer.newLine
		writer.close
		println(msg + " file generation finished")
		if (!new File(writerPath).exists)
			throw new RuntimeException("file for "+ msg + "not produced")
	}

	// entry point to compile the Asm to a String
	protected abstract def String compileAsm(Asm asm)

	// compile with the standard options
	final def String compileAsm(Asm asm, TranslatorOptions options){
		this.options = options;
		compileAsm(asm)
	}


}
