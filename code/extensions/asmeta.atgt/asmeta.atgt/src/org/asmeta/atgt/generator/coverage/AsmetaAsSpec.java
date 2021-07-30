package org.asmeta.atgt.generator.coverage;

import asmeta.structure.Asm;
import atgt.specification.ASMSpecification;

// should implement Specification
class AsmetaAsSpec extends ASMSpecification{
	
	
	Asm asm;

	public AsmetaAsSpec(Asm asm) {
		this.asm = asm;
	}
	
}