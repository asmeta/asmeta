package asmetal2cpp_boostunit;


import java.io.File;

import org.asmeta.parser.ASMParser;
import org.junit.Test;

import asmeta.AsmCollection;

public class ExampleTaker {
	
	@Test
	public void takeBareExample() throws Exception{
		String asmspec = "examples/byhand/Arduino/Bare/Bare.asm";
		getExample(asmspec);
	}

	static public AsmCollection getExample(String asmspec) throws Exception {
		File asmFile = getAsmFile(asmspec);		
		final AsmCollection model = ASMParser.setUpReadAsm(asmFile);
		return model;		
	}

	static public File getAsmFile(String asmspec) {
		String example = asmspec; 
		// PARSE THE SPECIFICATION (ASM)
		// parse using the asmeta parser
		File asmFile = new File(example);
		assert asmFile.exists() : asmFile.getAbsolutePath();
		String asmname = asmFile.getName();
		String name = asmname.substring(0,asmname.lastIndexOf("."));
		return asmFile;
	}
	
	/*
	 * static public File getAsmFile(String asmspec) {
		String example = "../asmetal2cpp_codegen/"+asmspec; 
		// PARSE THE SPECIFICATION (ASM)
		// parse using the asmeta parser
		File asmFile = new File(example);
		assert asmFile.exists();
		String asmname = asmFile.getName();
		String name = asmname.substring(0,asmname.lastIndexOf("."));
		return asmFile;
	}
	 */
}
