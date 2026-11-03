package org.asmeta.atgt.generator;

import java.io.File;

import org.asmeta.atgt.coverage.AsmetaAsSpec;
import org.asmeta.parser.ASMParser;

import asmeta.AsmCollection;
import atgt.parser.asmeta.AsmetaLLoader;
import atgt.specification.ASMSpecification;
import tgtlib.specification.ParseException;


// it read an ASM specifation - old format and new one
public abstract class SpecificationReader<S extends tgtlib.specification.Specification> {

	public abstract S read(String asmfile) throws ParseException, Exception;
}

class AsmetaSpecificationReader extends SpecificationReader<org.asmeta.atgt.coverage.AsmetaAsSpec> {
	@Override
	public AsmetaAsSpec read(String asmfile) throws Exception {
		AsmCollection asmCollection = ASMParser.setUpReadAsm(new File(asmfile));
		return new AsmetaAsSpec(asmCollection);
	}

}

// old format, using the old ASMSpecification class
class ASMSpecificationReader extends  SpecificationReader<ASMSpecification>{

	@Override
	public ASMSpecification read(String asmfile) throws ParseException {
		return new AsmetaLLoader().read(new File(asmfile));
	}
	
}