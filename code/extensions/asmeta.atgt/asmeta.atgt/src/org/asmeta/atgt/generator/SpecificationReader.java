package org.asmeta.atgt.generator;

import java.io.File;

import org.asmeta.atgt.coverage.AsmetaAsSpec;

import atgt.parser.asmeta.AsmetaLLoader;
import atgt.specification.ASMSpecification;
import tgtlib.specification.ParseException;

public abstract class SpecificationReader<S extends tgtlib.specification.Specification> {

	public abstract S read(String asmfile) throws ParseException;
}


class AsmetaSpecificationReader extends SpecificationReader<org.asmeta.atgt.coverage.AsmetaAsSpec> {

	@Override
	public AsmetaAsSpec read(String asmfile) {
		return null;
	}

}

// old format, using the old ASMSpecification class
class ASMSpecificationReader extends  SpecificationReader<ASMSpecification>{

	@Override
	public ASMSpecification read(String asmfile) throws ParseException {
		return new AsmetaLLoader().read(new File(asmfile));
	}
	
}