package org.asmeta.asm2junit.main


import asmeta.structure.Asm
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter
//import org.eclipse.emf.ecore.resource.Resource
//import org.eclipse.xtext.generator.IFileSystemAccess
//import org.eclipse.xtext.generator.IGenerator
import org.asmeta.asm2junit.formatter.Formatter



/** the real generator
 */
 //implements IGenerator
abstract class AvallaToJUnitGenerator  {
	protected TranslatorOptions options
	
	/*override doGenerate(Resource input, IFileSystemAccess fsa) {
		throw new UnsupportedOperationException("TODO: auto-generated method stub")
	}*/


	protected def compileAndWrite(Asm asm, String writerPath, String msg, TranslatorOptions userOptions) {
		println(msg + " file generation in " + writerPath)
		val file = new FileWriter(writerPath) 
		val writer = new BufferedWriter(file)
		val javaCode = compileAsm(asm, userOptions)
		
		if (options.formatter){
			writer.write(Formatter.formatCode(javaCode));
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
